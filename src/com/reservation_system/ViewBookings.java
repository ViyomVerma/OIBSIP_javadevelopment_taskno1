package com.reservation_system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ViewBookings extends JFrame{
	private int userId;
    private String username;
    private String fullname;

    public ViewBookings() {
        this.userId = UserSession.getUserId();
        this.username = UserSession.getUsername();
        this.fullname = UserSession.getFullname();

        setTitle("Your Bookings - " + fullname);
        setSize(1000, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JLabel heading = new JLabel("VIEW TICKET BOOKINGS", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setForeground(new Color(7, 60, 61));
        heading.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(heading, BorderLayout.NORTH);

        String[] columns = {
            "PNR", "Passenger Name", "Train No.", "Class", 
            "Journey Date", "From", "To", "Booking Time"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable bookingsTable = new JTable(tableModel);
        bookingsTable.setBounds(0,40,900,400);
        bookingsTable.setRowHeight(25);
        bookingsTable.setBackground(new Color(7, 60, 61));
        bookingsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        bookingsTable.setBackground(new Color(240, 255, 250));            
        bookingsTable.setForeground(new Color(0, 51, 102));                
        bookingsTable.setGridColor(new Color(180, 180, 180));             
        bookingsTable.setRowHeight(25);
        
        
        JButton Back=new JButton("Back");
        Back.setBounds(20,300,100,40);
        Back.setBackground(Color.RED);
        Back.setForeground(Color.WHITE);
        Back.setFont(new Font("Arial", Font.BOLD, 14));
        add(Back);
        
        Back.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		new Dashboard().setVisible(true);
        	}
        });
        
        JTableHeader header = bookingsTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(25, 166, 93));                    
        header.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        add(scrollPane, BorderLayout.CENTER);

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM reservations WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getLong("pnr"),
                    rs.getString("passenger_name"),
                    rs.getString("train_number"),
                    rs.getString("class_type"),
                    rs.getDate("journey_date"),
                    rs.getString("from_station"),
                    rs.getString("to_station"),
                    rs.getTimestamp("booking_time")
                };
                tableModel.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching bookings: " + e.getMessage());
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        new ViewBookings();
    }

}
