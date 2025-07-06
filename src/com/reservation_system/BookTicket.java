package com.reservation_system;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class BookTicket extends JFrame {
	  private int userId;
	  private String username;
	  private String fullname;

	    public BookTicket() {
	    	  this.userId = UserSession.getUserId();
	          this.username = UserSession.getUsername();
	          this.fullname = UserSession.getFullname();
	          
	    	setTitle("Book Your Ticket");
	    	setSize(400,400);
	    	setLayout(null);
	    	setLocationRelativeTo(null);
	    	setDefaultCloseOperation(EXIT_ON_CLOSE);
	    	
	    	
	    	
	    	 JLabel title = new JLabel("Book Ticket");
	         title.setFont(new Font("Arial", Font.BOLD, 25));
	         title.setBounds(130, 20, 200, 30);
	         title.setForeground(new Color(7, 60, 61));
	         add(title);

	         // Labels and Fields
	         JLabel trainLbl = new JLabel("Train Number:");
	         trainLbl.setFont(new Font("Arial", Font.BOLD, 15));
	         trainLbl.setBounds(50, 70, 120, 25);
	         add(trainLbl);

	         JTextField trainField = new JTextField();
	         trainField.setBounds(180, 70, 150, 25);
	         add(trainField);

	         JLabel classLbl = new JLabel("Class:");
	         classLbl.setFont(new Font("Arial", Font.BOLD, 15));
	         classLbl.setBounds(50, 110, 120, 25);
	         add(classLbl);
	         
	         String[] classes = {"Sleeper", "AC", "First Class"};
	         JComboBox<String> classBox = new JComboBox<>(classes);
	         classBox.setBounds(180, 110, 150, 25);
	         add(classBox);

	         JLabel dateLbl = new JLabel("Journey Date:");
	         dateLbl.setFont(new Font("Arial", Font.BOLD, 15));
	         dateLbl.setBounds(50, 150, 120, 25);
	         add(dateLbl);

	         JTextField dateField = new JTextField("YYYY-MM-DD");
	         dateField.setBounds(180, 150, 150, 25);
	         add(dateField);
	         
	         dateField.addFocusListener(new FocusAdapter() {
	        	    @Override
	        	    public void focusGained(FocusEvent e) {
	        	        if (dateField.getText().equals("YYYY-MM-DD")) {
	        	            dateField.setText("");
	        	            dateField.setForeground(Color.BLACK); // User text color
	        	        }
	        	    }

	        	    @Override
	        	    public void focusLost(FocusEvent e) {
	        	        if (dateField.getText().isEmpty()) {
	        	            dateField.setForeground(Color.GRAY);
	        	            dateField.setText("YYYY-MM-DD"); // Reset placeholder if left empty
	        	        }
	        	    }
	        	});
	         
	         
	         JLabel fromLbl = new JLabel("From Station:");
	         fromLbl.setFont(new Font("Arial", Font.BOLD, 15));
	         fromLbl.setBounds(50, 190, 120, 25);
	         add(fromLbl);

	         JTextField fromField = new JTextField();
	         fromField.setBounds(180, 190, 150, 25);
	         add(fromField);

	         JLabel toLbl = new JLabel("To Station:");
	         toLbl.setFont(new Font("Arial", Font.BOLD, 15));
	         toLbl.setBounds(50, 230, 120, 25);
	         add(toLbl);

	         JTextField toField = new JTextField();
	         toField.setBounds(180, 230, 150, 25);
	         add(toField);

	         JButton bookBtn = new JButton("Confirm Booking");
	         bookBtn.setFont(new Font("Arial", Font.BOLD, 15));
	         bookBtn.setBackground(new Color(25, 166, 93));
	         bookBtn.setForeground(Color.WHITE);
	         bookBtn.setBounds(100, 300, 180, 35);
	         add(bookBtn);
	         
	         bookBtn.addActionListener(new ActionListener() {
	        	 @Override
	        	 public void actionPerformed(ActionEvent e) {
	        		 String train = trainField.getText();
	                 String classType = (String) classBox.getSelectedItem();
	                 String date = dateField.getText();
	                 String from = fromField.getText();
	                 String to = toField.getText();
	                 
	                 if(train.isEmpty() || classType.isEmpty() || date.isEmpty() || from.isEmpty() || to.isEmpty()) {
	                	 JOptionPane.showMessageDialog(BookTicket.this, "All fields are required");
	                     return;
	                 }
	                 
	                 
	                 long pnr=generatePNR();
	                 
	                 try(Connection conn=DBConnection.getConnection()){
	                	 String query="INSERT INTO reservations (pnr, username, passenger_name, train_number, class_type, journey_date, from_station, to_station) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	                	 PreparedStatement ps=conn.prepareStatement(query);
	                	 ps.setLong(1, pnr);
	                     ps.setString(2, username);
	                     ps.setString(3, fullname);
	                     ps.setString(4, train);
	                     ps.setString(5, classType);
	                     ps.setDate(6, Date.valueOf(date));
	                     ps.setString(7, from);
	                     ps.setString(8, to); 
	                     
	                     int rows = ps.executeUpdate();
	                     if (rows > 0) {
	                         JOptionPane.showMessageDialog(BookTicket.this, "✅ Ticket Booked! Your PNR: " + pnr);
	                         dispose();
	                         new Dashboard().setVisible(true);
	                     } else {
	                         JOptionPane.showMessageDialog(BookTicket.this, "❌ Booking Failed!");
	                     }
	                 }
	                 catch (SQLException ex) {
	                     JOptionPane.showMessageDialog(BookTicket.this, "DB Error: " + ex.getMessage());
	                 } catch (IllegalArgumentException ex) {
	                     JOptionPane.showMessageDialog(BookTicket.this, "Date format must be YYYY-MM-DD");
	                 }
	        	 }
	         });

	         setVisible(true);  
	    }
	    private long generatePNR() {
	        return new Random().nextInt(501); 
	    }

	    public static void main(String[] args) {
	        new BookTicket();
	    }

}
