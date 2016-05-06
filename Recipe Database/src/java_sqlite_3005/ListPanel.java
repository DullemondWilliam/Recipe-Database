package java_sqlite_3005;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// This is the Panel that contains represents the view of the
// Music Store

public class ListPanel extends JPanel {

	// These are the components
	private JButton		searchButton;
	private JButton     addIngButton;
	private JButton     addActionButton;
	
	private JTextField  searchText;
	private JList		recipeList;
	private JList		actionList;
	private JList		ingList;
	
	private Font UIFont = new Font("Courier New", Font.BOLD, 16);


	// These are the get methods that are used to access the components
	public JButton getSearchButton() { return searchButton; }
	public JList getRecipeList() { return recipeList; }
	public JList getActionList() { return actionList; }
	public JList getIngList()    { return ingList;    }
	public JTextField getSearchText() { return searchText; }
	
	


	// This is the default constructor
	public ListPanel(){
		super();

		// Use a GridBagLayout (lotsa fun)
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints layoutConstraints = new GridBagConstraints();
		setLayout(layout);

		// Add the recipeList list
		recipeList = new JList();
		recipeList.setFont(UIFont);
		recipeList.setPrototypeCellValue("xxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		JScrollPane scrollPane = new JScrollPane( recipeList,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 5;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.NORTHWEST;
		layoutConstraints.weightx = 1.0;
		layoutConstraints.weighty = 1.0;
		layout.setConstraints(scrollPane, layoutConstraints);
		add(scrollPane);

		// Add the Add button
		searchText = new JTextField("");
		searchText.setFont(UIFont);

		layoutConstraints.gridx = 1;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 1.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(searchText, layoutConstraints);
		add(searchText);

		// Add the Remove button
		searchButton = new JButton("Search");
		layoutConstraints.gridx = 2;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(searchButton, layoutConstraints);
		add(searchButton);

		// Add the actionList list
		actionList = new JList();
		actionList.setFont(UIFont);
		actionList.setPrototypeCellValue("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		scrollPane = new JScrollPane( actionList,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		layoutConstraints.gridx = 1;
		layoutConstraints.gridy = 1;
		layoutConstraints.gridwidth = 2;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.NORTHWEST;
		layoutConstraints.weightx = 2.0;
		layoutConstraints.weighty = 2.0;
		layout.setConstraints(scrollPane, layoutConstraints);
		add(scrollPane);

	/*	// Add the Remove button
		addIngButton = new JButton("Add Ingredient");
		layoutConstraints.gridx = 1;
		layoutConstraints.gridy = 2;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.WEST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(addIngButton, layoutConstraints);
		add(addIngButton);
		
		// Add the Remove button
		addActionButton = new JButton("Add Action");
		layoutConstraints.gridx = 2;
		layoutConstraints.gridy = 2;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(addActionButton, layoutConstraints);
		add(addActionButton);
		*/
		
		ingList = new JList();
		ingList.setFont(UIFont);
		ingList.setPrototypeCellValue("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		scrollPane = new JScrollPane( ingList,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		layoutConstraints.gridx = 1;
		layoutConstraints.gridy = 3;
		layoutConstraints.gridwidth = 2;
		layoutConstraints.gridheight = 2;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.NORTHWEST;
		layoutConstraints.weightx = 2.0;
		layoutConstraints.weighty = 2.0;
		layout.setConstraints(scrollPane, layoutConstraints);
		add(scrollPane);

	}
}