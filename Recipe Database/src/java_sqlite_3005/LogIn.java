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

public class LogIn extends JDialog {

	// This is a pointer to the email buddy that is being edited
	private User theUser;
	
	DialogClient theDialogClient;

	private JLabel			aLabel;
	private JTextField		usernameField;
	private JTextField		passwordField;
	private JButton			logInButton;
	private JButton			cancelButton;
	
	Font UIFont = new Font("Courier New", Font.BOLD, 16);

	public LogIn(Frame owner, DialogClient aClient, String title, boolean modal, User auser){
		super(owner,title,modal);
		
		theDialogClient = aClient;
		theUser = auser;

		buildDialogWindow(theUser);

		// Add listeners for the Ok and Cancel buttons as well as window closing
		logInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				logInButtonClicked();
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
	private void buildDialogWindow(User theUser) {
		
   		GridBagLayout layout = new GridBagLayout();
        GridBagConstraints lc = new GridBagConstraints();
        getContentPane().setLayout(layout);

        lc.anchor = GridBagConstraints.EAST;
        lc.insets = new Insets(5, 5, 5, 5);

        aLabel = new JLabel("User");
        lc.gridx = 0; lc.gridy = 0;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(aLabel, lc);
        getContentPane().add(aLabel);

        aLabel = new JLabel("Password");
        lc.gridx = 0; lc.gridy = 1;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(aLabel, lc);
        getContentPane().add(aLabel);
		
   		// Add the name field
		usernameField = new JTextField(theUser.getUsername());
		usernameField.setFont(UIFont);
        lc.gridx = 1; lc.gridy = 0;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.fill = GridBagConstraints.BOTH;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(usernameField, lc);
   		getContentPane().add(usernameField);

		// Add the address field
		passwordField = new JPasswordField(theUser.getPassword());
		passwordField.setFont(UIFont);
        lc.gridx = 1; lc.gridy = 1;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.fill = GridBagConstraints.BOTH;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(passwordField, lc);
   		getContentPane().add(passwordField);

		// Add the Update button
		logInButton = new JButton("UPDATE");
        lc.gridx = 1; lc.gridy = 6;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(logInButton, lc);
   		getContentPane().add(logInButton);

   		// Add the Cancel button
		cancelButton = new JButton("CANCEL");
        lc.gridx = 3; lc.gridy = 6;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(cancelButton, lc);
   		getContentPane().add(cancelButton);
	}

	private void logInButtonClicked(){
		theUser.setUsername(usernameField.getText());
		theUser.setPassword(passwordField.getText());

		dispose();
	}
	
	private void cancelButtonClicked(){
		if (theDialogClient != null) theDialogClient.exit();
		
		dispose();
	}
}