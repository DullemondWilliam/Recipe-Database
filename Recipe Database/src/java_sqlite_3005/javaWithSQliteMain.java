package java_sqlite_3005;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class javaWithSQliteMain {

	public static void main(String[] args) {
		GUI frame = null;
		try {
			Class.forName("org.sqlite.JDBC");
			
			Connection database = DriverManager.getConnection("jdbc:sqlite:db_recipe.db");
			Statement stat = database.createStatement();
			
			frame =  new GUI("3005 Recipes", database, stat); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		frame.setVisible(true);
	}

}
