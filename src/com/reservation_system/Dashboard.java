package com.reservation_system;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame {

	  private int userId;
	    private String username;
	    private String fullName;
	
    public Dashboard() {
    	 
    	  this.userId = UserSession.getUserId();
          this.username = UserSession.getUsername();
          this.fullName = UserSession.getFullname();
          
        setTitle("Dashboard - Reservation System");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        //  Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome, " + fullName + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBounds(100, 20, 250, 30);
        welcomeLabel.setForeground(new Color(10, 43, 69));
        add(welcomeLabel);

        //  Book Ticket Button
        JButton bookBtn = new JButton(" Book Ticket");
        bookBtn.setBounds(100, 70, 200, 40);
        bookBtn.setBackground(new Color(25, 166, 93));
        bookBtn.setForeground(Color.WHITE);
        add(bookBtn);

        //  Cancel Ticket Button
        JButton cancelBtn = new JButton(" Cancel Ticket");
        cancelBtn.setBounds(100, 120, 200, 40);
        cancelBtn.setBackground(new Color(25, 166, 93));
        cancelBtn.setForeground(Color.WHITE);
        add(cancelBtn);

        //  View Bookings Button
        JButton viewBtn = new JButton(" View Bookings");
        viewBtn.setBounds(100, 170, 200, 40);
        viewBtn.setBackground(new Color(25, 166, 93));
        viewBtn.setForeground(Color.WHITE);
        add(viewBtn);

        //  Logout Button
        JButton logoutBtn = new JButton(" Logout");
        logoutBtn.setBounds(100, 230, 200, 40);
        logoutBtn.setBackground(new Color(25, 166, 93));
        logoutBtn.setForeground(Color.WHITE);
        add(logoutBtn);

        
        bookBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e)
        	{
        		dispose();
        		new BookTicket();
        	}
        });

        cancelBtn.addActionListener(new ActionListener(){
       
            public void actionPerformed(ActionEvent e)
        	{
        		dispose();
        		new CancelTicket();
        		

        	}
        });

        viewBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) 
        	{
        	  dispose();
              new ViewBookings(); 
        	}
        });

        logoutBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Do you want to logout?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                dispose();
                new loginform().setVisible(true); 
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}
