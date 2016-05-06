package java_sqlite_3005;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GUI extends JFrame implements DialogClient{

	public int GUI_DISPLAY_LIMIT = 100;

	Connection databaseConnection;
	Statement stat;

	ArrayList<Recipe>     recipeList = new ArrayList<Recipe>();
	ArrayList<Action>     actionList = new ArrayList<Action>();
	ArrayList<Ingredient> ingList    = new ArrayList<Ingredient>();

	//private FakeBookSong    selectedSong; //song currently selected in the GUI list
	private Recipe	selectedRecipe = null; //book currently selected in the GUI list
	private Action  selectedAction = null;
	private Ingredient selectedIng = null;

	private User    currUser = new User();
	//private FakeBookSong songBeingEdited; //song being edited in a dialog

	// Store the view that contains the components
	ListPanel 	view; //panel of GUI components for the main window
	GUI    thisFrame;

	// Here are the component listeners
	ActionListener			theSearchButtonListener;
	ListSelectionListener	ingListSelectionListener;
	ListSelectionListener	actionListSelectionListener;
	ListSelectionListener	recipeListSelectionListener;
	MouseListener			doubleClickSongListListener;
	KeyListener             keyListener;

	// Here is the default constructor
	public GUI(String title, Connection aDB, Statement aStatement) {
		super(title);
		databaseConnection = aDB;
		stat = aStatement;
		//selectedSong = null;

		thisFrame = this;

		addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					System.out.println("Closing Database Connection");
					databaseConnection.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		// Make the main window view panel
		add(view = new ListPanel());
		// Add a listener for the add button
		theSearchButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				search();
			}
		};
		ingListSelectionListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				selectIng();
			}
		};
		// Add a listener to allow selection of buddies from the list
		actionListSelectionListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				selectAction();
			}
		};
		// Add a listener to allow selection of buddies from the list
		recipeListSelectionListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				selectRecipe();
			}
		};
		// Add a listener to allow double click selections from the list for editing
		doubleClickSongListListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					System.out.println("double click event");
					JList theList = (JList) event.getSource();
					int index = theList.locationToIndex(event.getPoint());
					//songBeingEdited = (FakeBookSong) theList.getModel().getElementAt(index);
					//System.out.println("Double Click on: " + songBeingEdited);
					//SongDetailsDialog dialog = new SongDetailsDialog(thisFrame, thisFrame, "Song Details Dialog", true,
					//												 songBeingEdited,databaseConnection,stat);     
					//dialog.setVisible(true);
				} 
			}
		};
		keyListener = new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
				int keyChar = arg0.getKeyChar();
				if (keyChar == KeyEvent.VK_ENTER)  search();
			}
		};
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600,500);

		do{
			LogIn log = new LogIn(thisFrame, thisFrame, "Song Details Dialog", true,currUser);
			log.setVisible(true);

			try {
				String queryPrep = "SELECT * FROM user Where username = ? AND password = ?;";
				databaseConnection.setAutoCommit(false);
				PreparedStatement prep = databaseConnection.prepareStatement(queryPrep);
				prep.setString(1,currUser.getUsername());
				prep.setString(2,currUser.getPassword());
				ResultSet rs;
				rs = prep.executeQuery();

				if(rs.next()){
					currUser.setUserID(rs.getInt("user_ID"));
					break;
				}
				rs.close();
				databaseConnection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}while(true);

		updateFromDatabase();
		//exit();
		// Start off with everything updated properly to reflect the model state
		update();
	}

	private void updateFromDatabase(){
		try {
			String queryPrep = "SELECT * FROM recipe Where user_ID = ?;";
			databaseConnection.setAutoCommit(false);
			PreparedStatement prep = databaseConnection.prepareStatement(queryPrep);
			prep.setInt(1, currUser.getUserId());
			ResultSet rs;
			rs = prep.executeQuery();
			while(rs.next()){
				Recipe temp = new Recipe(rs.getInt("recipe_ID"),rs.getString("name"),rs.getInt("difficulty"),rs.getInt("size"));
				recipeList.add(temp);
			}
			rs.close();
			databaseConnection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Enable all listeners
	private void enableListeners() {
		view.getSearchButton().addActionListener(theSearchButtonListener);

		view.getRecipeList().addListSelectionListener(recipeListSelectionListener);
		view.getIngList().addListSelectionListener(ingListSelectionListener);
		view.getActionList().addListSelectionListener(actionListSelectionListener);

		//view.getRecipeList().addMouseListener(doubleClickSongListListener);
		view.getSearchText().addKeyListener(keyListener);
	}

	// Disable all listeners
	private void disableListeners() {
		view.getSearchButton().removeActionListener(theSearchButtonListener);

		view.getRecipeList().removeListSelectionListener(recipeListSelectionListener);
		view.getIngList().removeListSelectionListener(ingListSelectionListener);
		view.getActionList().removeListSelectionListener(actionListSelectionListener);

		//view.getRecipeList().addMouseListener(doubleClickSongListListener);
		view.getSearchText().removeKeyListener(keyListener);
	}

	// This is called when the user clicks the add button
	private void search() {
		String searchPrototype = view.getSearchText().getText().trim();

		//databaseConnection.prepareStatement("insert into people values (?, ?);");
		String sqlQueryString = "select * from recipe WHERE user_ID = ? and name like ? order by name asc ";
		try {
			databaseConnection.setAutoCommit(false);
			PreparedStatement prepStat = databaseConnection.prepareStatement(sqlQueryString);
			prepStat.setInt(1,currUser.getUserId());
			prepStat.setString(2, "%"+searchPrototype+"%");
			ResultSet rs =prepStat.executeQuery();
			databaseConnection.setAutoCommit(true);

			recipeList = new ArrayList<Recipe>();

			int count = 0;
			while (rs.next()){
				Recipe temp = new Recipe(
						rs.getInt("recipe_ID"),
						rs.getString("name"),
						rs.getInt("difficulty"),
						rs.getInt("size")
						);

				recipeList.add(temp);
			}
			rs.close(); //close the query result table

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}

		System.out.println("Search clicked");

		update();

	}

	// This is called when the user selects a book from the list
	private void selectRecipe() {
		selectedRecipe = (Recipe)(view.getRecipeList().getSelectedValue());
		System.out.println("Recipe Selected: " + selectedRecipe);

		updateSelectedRecipe();
		update();
	}

	private void selectAction() {
		selectedAction = (Action)(view.getActionList().getSelectedValue());
		System.out.println("Action Selected: " + selectedAction);

		updateSelectedAction();
		update();
	}

	private void selectIng() {
		selectedIng = (Ingredient)(view.getIngList().getSelectedValue());
		System.out.println("Ingredient Selected: " + selectedIng);

		updateSelectedIng();
		update();
	}

	private void updateSelectedAction(){
		ActionDetailsDialog ac = new ActionDetailsDialog(thisFrame, thisFrame, "Song Details Dialog", true,selectedAction);
		ac.setVisible(true);
	}

	private void updateSelectedIng(){
		IngDetailsDialog ac = new IngDetailsDialog(thisFrame, thisFrame, "Song Details Dialog", true,selectedIng);
		ac.setVisible(true);
	}

	private void updateSelectedRecipe(){
		actionList= new ArrayList<Action>();
		ingList=    new ArrayList<Ingredient>(); 
		try {
			String queryPrep = "Select action.action_ID, action,duration From action Inner Join recipe_action on recipe_action.recipe_ID= ? AND recipe_action.action_ID = action.action_ID";
			databaseConnection.setAutoCommit(false);
			PreparedStatement prep = databaseConnection.prepareStatement(queryPrep);
			prep.setInt(1, selectedRecipe.getRecipe_ID());
			ResultSet rs;
			rs = prep.executeQuery();
			while(rs.next()){
				Action temp = new Action(rs.getInt("action_ID"),rs.getString("Action"),rs.getInt("duration"));
				actionList.add(temp);
			}
			rs.close();
			databaseConnection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			String queryPrep = "Select ingredient.ing_ID, ing_Name,food_Group,shelf_Life, calories From ingredient Inner Join recipe_ingredient on recipe_ingredient.recipe_ID= ? AND recipe_ingredient.ing_ID = ingredient.ing_ID";
			databaseConnection.setAutoCommit(false);
			PreparedStatement prep = databaseConnection.prepareStatement(queryPrep);
			prep.setInt(1, selectedRecipe.getRecipe_ID());
			ResultSet rs;
			rs = prep.executeQuery();
			while(rs.next()){
				Ingredient temp = new Ingredient(rs.getInt("ing_ID"),rs.getString("ing_Name"),rs.getString("food_Group"),rs.getInt("shelf_Life"),rs.getInt("calories"));
				ingList.add(temp);
			}
			rs.close();
			databaseConnection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	// Update the remove button
	private void updateSearchButton() {
		view.getSearchButton().setEnabled(true);
	}

	private void updateList() {
		//boolean		foundSelected = false;

		Recipe bookArray[] = new Recipe[1]; //just to establish array type
		view.getRecipeList().setListData(recipeList.toArray());

		Action actionArray[] = new Action[1]; //just to establish array type
		view.getActionList().setListData(actionList.toArray());

		Ingredient ingArray[] = new Ingredient[1]; //just to establish array type
		view.getIngList().setListData(ingList.toArray());

		if (selectedRecipe != null)
			view.getRecipeList().setSelectedValue(selectedRecipe, true);
		if (selectedAction != null)
			view.getActionList().setSelectedValue(selectedAction, true);
		if (selectedIng != null)
			view.getIngList().setSelectedValue(selectedIng, true);
	}

	// Update the components
	private void update() {
		//updateListTotal();
		disableListeners();
		updateList();
		updateSearchButton();
		enableListeners();
	}

	@Override
	public void dialogFinished(DialogClient.operation requestedOperation) {

		if(requestedOperation == DialogClient.operation.UPDATEACTION){
			String sqlUpdateString = "Update action Set duration = ?, action= ? Where action_ID = ? ;";
			System.out.println(sqlUpdateString);
			try {
				databaseConnection.setAutoCommit(false);
				PreparedStatement prep = databaseConnection.prepareStatement(sqlUpdateString);
				prep.setInt(1,selectedAction.getDuration());	
				prep.setString(2,selectedAction.getAction());
				prep.setInt(3, selectedAction.getActionID());
				prep.execute();
				databaseConnection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if(requestedOperation == DialogClient.operation.UPDATEING){
			String sqlUpdateString = "Update ingredient Set ing_Name = ?, food_Group= ?, shelf_Life= ?, calories= ? Where ing_ID = ? ;";
			System.out.println(sqlUpdateString);
			try {
				databaseConnection.setAutoCommit(false);
				PreparedStatement prep = databaseConnection.prepareStatement(sqlUpdateString);
				prep.setString(1,selectedIng.getName());	
				prep.setString(2,selectedIng.getFoodGroup());
				prep.setInt(3, selectedIng.getShelfLife());
				prep.setInt(4, selectedIng.getCalories());
				prep.setInt(5, selectedIng.getIngID());
				prep.execute();
				databaseConnection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if(requestedOperation == DialogClient.operation.DELETEING){
			String sqlUpdateString = "Delete From ingredient Where ing_ID = ? ;";
			System.out.println(sqlUpdateString);
			try {
				databaseConnection.setAutoCommit(false);
				PreparedStatement prep = databaseConnection.prepareStatement(sqlUpdateString);
				prep.setInt(1, selectedIng.getIngID());
				prep.execute();
				databaseConnection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(requestedOperation == DialogClient.operation.DELETEACTION){
			String sqlUpdateString = "Delete From action Where action_ID = ? ;";
			System.out.println(sqlUpdateString);
			try {
				databaseConnection.setAutoCommit(false);
				PreparedStatement prep = databaseConnection.prepareStatement(sqlUpdateString);
				prep.setInt(1, selectedAction.getActionID());
				prep.execute();
				databaseConnection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		updateSelectedRecipe();
		update();
	}

	@Override
	public void dialogCancelled() {
		update();
	}

	public void exit(){
		System.exit(0);
	}
}