package com.perisic.tomato.peripherals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRegistration extends JFrame {
    private JTextField firstname;
    private JTextField lastname;
    private JTextField email;
    private JTextField username;
    private JTextField mob;
    private JPasswordField passwordField;
    private JButton registerButton;

    public UserRegistration() {
        setTitle("User Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null); // Center the frame on the screen

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(8, 2, 10, 10));

        JLabel lblFirstName = new JLabel("First Name:");
        contentPane.add(lblFirstName);
        firstname = new JTextField();
        contentPane.add(firstname);

        JLabel lblLastName = new JLabel("Last Name:");
        contentPane.add(lblLastName);
        lastname = new JTextField();
        contentPane.add(lastname);

        JLabel lblEmail = new JLabel("Email Address:");
        contentPane.add(lblEmail);
        email = new JTextField();
        contentPane.add(email);

        JLabel lblUsername = new JLabel("Username:");
        contentPane.add(lblUsername);
        username = new JTextField();
        contentPane.add(username);

        JLabel lblPassword = new JLabel("Password:");
        contentPane.add(lblPassword);
        passwordField = new JPasswordField();
        contentPane.add(passwordField);

        JLabel lblMobileNumber = new JLabel("Mobile Number:");
        contentPane.add(lblMobileNumber);
        mob = new JTextField();
        contentPane.add(mob);

        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(70, 130, 180)); // Steel Blue
        registerButton.setForeground(Color.WHITE);
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        contentPane.add(new JLabel()); // Empty cell for spacing
        contentPane.add(registerButton);

        setContentPane(contentPane);
    }

    private void registerUser() {
        int score = 0; // Initialize the score to 0

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_management_system",
                    "root", "Zujanshan83@");

            String query = "INSERT INTO account1 (first_name, last_name, user_name, password, email_id, mobile_number, score) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, firstname.getText());
                preparedStatement.setString(2, lastname.getText());
                preparedStatement.setString(3, username.getText());
                preparedStatement.setString(4, new String(passwordField.getPassword()));
                preparedStatement.setString(5, email.getText());
                preparedStatement.setString(6, mob.getText());
                preparedStatement.setInt(7, score);

                int x = preparedStatement.executeUpdate();
                if (x == 0) {
                    JOptionPane.showMessageDialog(registerButton, "This username is already taken");
                } else {
                    JOptionPane.showMessageDialog(registerButton,
                            "Welcome, " + firstname.getText() + "! Your account is successfully created");
                    dispose();
                   UserLogin ah = new UserLogin();
                    ah.setTitle("Welcome");
                    ah.setVisible(true);
                }
            }
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(registerButton, "Error in registration. Please try again.");
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UserRegistration frame = new UserRegistration();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
