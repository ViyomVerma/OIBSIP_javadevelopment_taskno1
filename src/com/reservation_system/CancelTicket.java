package com.reservation_system;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

public class CancelTicket extends JFrame {

    private int userId;
    private String username;
    private String fullname;

    public CancelTicket() {
        // ✅ Get user info from session
        this.userId = UserSession.getUserId();
        this.username = UserSession.getUsername();
        this.fullname = UserSession.getFullname();

        setTitle("Cancel Ticket - " + fullname);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel title = new JLabel("CANCEL YOUR TICKET");
        title.setBounds(60, 20, 300, 30);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(25, 166, 93));
        add(title);

        JLabel pnrLbl = new JLabel("Enter PNR Number:");
        pnrLbl.setBounds(50, 80, 150, 25);
        add(pnrLbl);

        JTextField pnrField = new JTextField();
        pnrField.setBounds(180, 80, 150, 25);
        add(pnrField);

        JButton cancelBtn = new JButton("Cancel Ticket");
        cancelBtn.setBounds(120, 130, 140, 35);
        cancelBtn.setBackground(new Color(7, 60, 61));
        cancelBtn.setForeground(Color.WHITE);
        add(cancelBtn);

        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pnrText = pnrField.getText().trim();
                if (pnrText.isEmpty()) {
                    JOptionPane.showMessageDialog(CancelTicket.this, "Please enter a PNR number.");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(
                    CancelTicket.this,
                    "Are you sure you want to cancel this ticket?",
                    "Confirm Cancellation",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirm != JOptionPane.YES_OPTION) return;

                try {

                    try (Connection conn = DBConnection.getConnection()) {
                        String query = "SELECT * FROM reservations WHERE pnr = ? AND username = ?";
                        PreparedStatement ps = conn.prepareStatement(query);
                        ps.setString(1, pnrText);
                        ps.setString(2, username);

                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {
                            // Insert into cancellations table
                            String insertQuery = "INSERT INTO cancellations(pnr) VALUES(?)";
                            PreparedStatement insertPs = conn.prepareStatement(insertQuery);
                            insertPs.setString(1, pnrText);
                            insertPs.executeUpdate();

                            // Delete from reservations table
                            String deleteQuery = "DELETE FROM reservations WHERE pnr = ?";
                            PreparedStatement deletePs = conn.prepareStatement(deleteQuery);
                            deletePs.setString(1, pnrText);
                            deletePs.executeUpdate();

                            JOptionPane.showMessageDialog(CancelTicket.this,
                                    " Ticket with PNR " + pnrText + " has been successfully cancelled.");
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(CancelTicket.this,
                                    " PNR not found or not booked by you.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(CancelTicket.this, "PNR must be a valid number.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(CancelTicket.this, "Database Error: " + ex.getMessage());
                }
            }
        });

        setVisible(true);
    }

   
}
