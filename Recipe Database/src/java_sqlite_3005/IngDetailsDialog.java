package java_sqlite_3005;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

public class IngDetailsDialog extends JDialog {
	private Ingredient theIng;
	
	DialogClient theDialogClient;

	private JLabel					aLabel; //reuseable label variable
	
	private JTextField				nameField; //name of the song
	private JTextField				foodGroupField; //name of the song
	private JTextField				shelfLifeField; //name of the song
	private JTextField				caloriesField; //name of the song
	
	private JButton					updateButton;
	private JButton					deleteButton;
	private JButton					cancelButton;
	
	Font UIFont = new Font("Courier New", Font.BOLD, 16);
	
	public IngDetailsDialog(Frame owner, DialogClient aClient, String title, boolean modal, Ingredient aIng){
		super(owner,title,modal);
		
		//Store the client and model variables
		theDialogClient = aClient;
		theIng = aIng;

		// Put all the components onto the window and given them initial values
		buildDialogWindow(theIng);

		// Add listeners for the Ok and Cancel buttons as well as window closing
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				updateButtonClicked();
			}});
		
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				deleteButtonClicked();
			}});


		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				cancelButtonClicked();
			}});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				cancelButtonClicked();
			}});

		setSize(400, 250);
		
	}

	// This code adds the necessary components to the interface
	private void buildDialogWindow(Ingredient aIng) {
		
   		GridBagLayout layout = new GridBagLayout();
        GridBagConstraints lc = new GridBagConstraints();
        getContentPane().setLayout(layout);

 
        lc.anchor = GridBagConstraints.EAST;
        lc.insets = new Insets(5, 5, 5, 5);

        aLabel = new JLabel("Name");
        lc.gridx = 0; lc.gridy = 0;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(aLabel, lc);
        getContentPane().add(aLabel);

        aLabel = new JLabel("Food Group");
        lc.gridx = 0; lc.gridy = 1;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(aLabel, lc);
        getContentPane().add(aLabel);
        
        aLabel = new JLabel("Shelf Life");
        lc.gridx = 0; lc.gridy = 2;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(aLabel, lc);
        getContentPane().add(aLabel);

        aLabel = new JLabel("Calories");
        lc.gridx = 0; lc.gridy = 3;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(aLabel, lc);
        getContentPane().add(aLabel);

   		// Add the name field
		nameField = new JTextField(aIng.getName());
		nameField.setFont(UIFont);
        lc.gridx = 1; lc.gridy = 0;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.fill = GridBagConstraints.BOTH;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(nameField, lc);
   		getContentPane().add(nameField);

		// Add the address field
		foodGroupField = new JTextField(aIng.getFoodGroup());
		foodGroupField.setFont(UIFont);
        lc.gridx = 1; lc.gridy = 1;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.fill = GridBagConstraints.BOTH;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(foodGroupField, lc);
   		getContentPane().add(foodGroupField);

   		shelfLifeField = new JTextField(""+aIng.getShelfLife());
   		shelfLifeField.setFont(UIFont);
        lc.gridx = 1; lc.gridy = 2;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.fill = GridBagConstraints.BOTH;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(shelfLifeField, lc);
   		getContentPane().add(shelfLifeField);
   		
   		caloriesField = new JTextField(""+aIng.getCalories());
   		caloriesField.setFont(UIFont);
        lc.gridx = 1; lc.gridy = 3;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.fill = GridBagConstraints.BOTH;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(caloriesField, lc);
   		getContentPane().add(caloriesField);
   		
		// Add the Update button
		updateButton = new JButton("UPDATE");
        lc.gridx = 1; lc.gridy = 6;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(updateButton, lc);
   		getContentPane().add(updateButton);
        
		// Add the Delete button
		deleteButton = new JButton("DELETE");
        lc.gridx = 2; lc.gridy = 6;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(deleteButton, lc);
   		getContentPane().add(deleteButton);

  		// Add the Cancel button
		cancelButton = new JButton("CANCEL");
        
        lc.gridx = 3; lc.gridy = 6;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(cancelButton, lc);
   		getContentPane().add(cancelButton);
		
		
	}


	private void updateButtonClicked(){
		theIng.setName(nameField.getText());
		theIng.setFoodGroup(foodGroupField.getText());
		theIng.setCalories(Integer.parseInt(caloriesField.getText()));
		theIng.setShelfLife(Integer.parseInt(shelfLifeField.getText()));
		
		//Inform the dialog client that the dialog finished
		if (theDialogClient != null) theDialogClient.dialogFinished(DialogClient.operation.UPDATEING);
		dispose();

	}
	
	private void deleteButtonClicked(){
		//Inform the dialog client that the dialog finished
		if (theDialogClient != null) theDialogClient.dialogFinished(DialogClient.operation.DELETEING);
		dispose();
	}

	private void cancelButtonClicked(){
		if (theDialogClient != null) theDialogClient.dialogCancelled();
		dispose();
	}
}