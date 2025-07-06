package com.reservation_system;

import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.sql.*;


public class RegisterForm extends JFrame{
	
	private JTextField fullnameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;


	public RegisterForm() {
        setTitle("Online Reservation - Register");
        setSize(420, 330);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        JLabel heading = new JLabel("Register a New Account");
        heading.setBounds(80, 10, 300, 30);
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setForeground(new Color(25, 166, 93));
        add(heading);

        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(50, 50, 100, 25);
        nameLabel.setForeground(new Color(10, 43, 69));
        add(nameLabel);

        fullnameField = new JTextField();
        fullnameField.setBounds(150, 50, 200, 25);
        add(fullnameField);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 90, 100, 25);
        userLabel.setForeground(new Color(10, 43, 69));
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 90, 200, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 130, 100, 25);
        passLabel.setForeground(new Color(10, 43, 69));
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 130, 200, 25);
        add(passwordField);

        JLabel confirmPassLabel = new JLabel("Confirm Password:");
        confirmPassLabel.setBounds(20, 170, 120, 25);
        confirmPassLabel.setForeground(new Color(10, 43, 69));
        add(confirmPassLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(150, 170, 200, 25);
        add(confirmPasswordField);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(150, 210, 200, 30);
        registerBtn.setBackground(new Color(25, 166, 93));
        registerBtn.setForeground(Color.WHITE);
        add(registerBtn);


        // Button Actions
        registerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });
    }

    private void handleRegister() {
        String fullname = fullnameField.getText();
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String confirmPassword = String.valueOf(confirmPasswordField.getPassword());

        if (fullname.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required");
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO users (username, password, fullname) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, fullname);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Account created successfully!");
                dispose();
                new loginform().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed.");
            }

        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new RegisterForm().setVisible(true);
    }
}
	
