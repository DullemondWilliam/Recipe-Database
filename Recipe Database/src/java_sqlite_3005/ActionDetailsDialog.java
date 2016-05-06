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

public class ActionDetailsDialog extends JDialog {
	private Action theAction;
	
	DialogClient theDialogClient;

	private JLabel					aLabel; //reuseable label variable
	
	private JTextField				actionField; //name of the song
	private JTextField				durationField; //artist of the song
	
	private JButton					updateButton;
	private JButton					deleteButton;
	private JButton					cancelButton;
	
	Font UIFont = new Font("Courier New", Font.BOLD, 16);
	
	public ActionDetailsDialog(Frame owner, DialogClient aClient, String title, boolean modal, Action aAction){
		super(owner,title,modal);
		
		//Store the client and model variables
		theDialogClient = aClient;
		theAction = aAction;

		// Put all the components onto the window and given them initial values
		buildDialogWindow(theAction);

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
	private void buildDialogWindow(Action aAction) {
		
   		GridBagLayout layout = new GridBagLayout();
        GridBagConstraints lc = new GridBagConstraints();
        getContentPane().setLayout(layout);

 
        lc.anchor = GridBagConstraints.EAST;
        lc.insets = new Insets(5, 5, 5, 5);

        aLabel = new JLabel("Action");
        lc.gridx = 0; lc.gridy = 0;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(aLabel, lc);
        getContentPane().add(aLabel);

        aLabel = new JLabel("duration");
        lc.gridx = 0; lc.gridy = 1;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(aLabel, lc);
        getContentPane().add(aLabel);

   		// Add the name field
		actionField = new JTextField(aAction.getAction());
		actionField.setFont(UIFont);
        lc.gridx = 1; lc.gridy = 0;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.fill = GridBagConstraints.BOTH;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(actionField, lc);
   		getContentPane().add(actionField);

		// Add the address field
		durationField = new JTextField(""+aAction.getDuration());
		durationField.setFont(UIFont);
        lc.gridx = 1; lc.gridy = 1;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.fill = GridBagConstraints.BOTH;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(durationField, lc);
   		getContentPane().add(durationField);

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
		theAction.setAction(actionField.getText());
		theAction.setDuration(Integer.parseInt(durationField.getText()));
	
		//Inform the dialog client that the dialog finished
		if (theDialogClient != null) theDialogClient.dialogFinished(DialogClient.operation.UPDATEACTION);
		dispose();

		
	}
	
	private void deleteButtonClicked(){
		//Inform the dialog client that the dialog finished
		if (theDialogClient != null) theDialogClient.dialogFinished(DialogClient.operation.DELETEACTION);
		
		dispose();
	}

	private void cancelButtonClicked(){
		if (theDialogClient != null) theDialogClient.dialogCancelled();
		dispose();
	}
}