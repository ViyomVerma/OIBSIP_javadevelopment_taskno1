package com.reservation_system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class loginform extends JFrame {
	private JTextField usernameField;
	private JPasswordField passwordField;
	
	public loginform()
	{
		setTitle("Online Reservation - Login");
        setSize(550, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        
        JPanel jPanel=new JPanel();
        jPanel.setBounds(0,0,200,400);
        jPanel.setBackground(new Color(25, 166, 93));
        
        JLabel jLabel = new JLabel("WELCOME !");
        jLabel.setBounds(0,0,100,30);
        jLabel.setFont(new Font("Arial", Font.BOLD, 26));
        jLabel.setForeground(Color.WHITE);
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);  
        jLabel.setVerticalAlignment(SwingConstants.CENTER);
        jPanel.add(jLabel);
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/login_bg.png"));
        JLabel imageLabel = new JLabel(icon);
        jPanel.add(imageLabel);

        add(jPanel);

       
        //CREATE ACCOUNT HEADING
        JLabel titleLabel = new JLabel("CREATE ACCOUNT");
        titleLabel.setBounds(220, 20, 300, 50);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(new Color(25, 166, 93));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);  
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(titleLabel);
        
        
        //USERNAME HEADING 
        JLabel userLabel=new JLabel("Username:");
        userLabel.setBounds(270,80,100,30);
        userLabel.setFont(new Font("Arial", Font.BOLD, 15));
        userLabel.setForeground(new Color(7, 60, 61));
        add(userLabel);
        
        
        //USERNAME FIELD
        usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createCompoundBorder
        (
        		usernameField.getBorder(),
        	    new EmptyBorder(5, 10, 5, 10) 
        ));
        usernameField.setForeground(Color.GRAY);
        usernameField.setFont(new Font("Arial", Font.BOLD, 15));
        usernameField.setBounds(270, 110, 190, 30);
        add(usernameField);

        //PASSWORD HEADING 
        JLabel passLabel=new JLabel("Password:");
        passLabel.setBounds(270,150,100,30);
        passLabel.setFont(new Font("Arial", Font.BOLD, 15));
        passLabel.setForeground(new Color(7, 60, 61));
        add(passLabel);
        
        passwordField = new JPasswordField();
        passwordField.setForeground(Color.GRAY);
        passwordField.setFont(new Font("Arial", Font.BOLD, 15));

        passwordField.setBorder(BorderFactory.createCompoundBorder
                (
                		passwordField.getBorder(),
                	    new EmptyBorder(5, 10, 5, 10) 
                ));
        passwordField.setBounds(270, 180, 190, 30);
        add(passwordField);

        JButton sign_inBtn = new JButton("SIGN IN");
        sign_inBtn.setBounds(250, 230, 230, 40);
        sign_inBtn.setBackground(new Color(25, 166, 93));
        sign_inBtn.setForeground(Color.WHITE); 
        add(sign_inBtn);
        
        JLabel or=new JLabel("OR");
        or.setBounds(350,270,100,30);
        or.setFont(new Font("Arial", Font.BOLD, 15));
        or.setForeground(new Color(7, 60, 61));
        add(or);
        
        JButton sign_upBtn = new JButton("SIGN UP");
        sign_upBtn.setBounds(250, 300, 230, 40);
        sign_upBtn.setBackground(new Color(25, 166, 93));
        sign_upBtn.setForeground(Color.WHITE); 
        add(sign_upBtn);

        sign_inBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin(); 
            }
        });
        
        sign_upBtn.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // closes LoginForm
                new RegisterForm().setVisible(true); 
            }
        });

    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id");
                System.out.println(userId);
                String fullname = rs.getString("fullname");
                JOptionPane.showMessageDialog(this, "Welcome, " + fullname + "!");
                dispose();
                UserSession.setUser(userId, username, fullname); 
                new Dashboard().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new loginform().setVisible(true);
    }
}
