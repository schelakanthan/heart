package com.perisic.tomato.peripherals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLogin extends JFrame {

    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnNewButton;
    private JLabel label;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UserLogin frame = new UserLogin();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public UserLogin() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Game Login");
        lblNewLabel.setForeground(new Color(70, 130, 180));
        lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblNewLabel.setBounds(400, 30, 200, 50);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        textField.setBounds(400, 100, 250, 40);
        contentPane.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        passwordField.setBounds(400, 160, 250, 40);
        contentPane.add(passwordField);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setForeground(new Color(70, 130, 180));
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        lblUsername.setBounds(230, 100, 150, 40);
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(new Color(70, 130, 180));
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        lblPassword.setBounds(230, 160, 150, 40);
        contentPane.add(lblPassword);

        btnNewButton = new JButton("Login");
        btnNewButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        btnNewButton.setBounds(430, 240, 150, 50);
        btnNewButton.setBackground(new Color(70, 130, 180));
        btnNewButton.setForeground(Color.WHITE);
        btnNewButton.addActionListener(e -> {
            String userName = textField.getText();
            String password = new String(passwordField.getPassword());

            try {
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/employee_management_system", "root", "Zujanshan83@");

                PreparedStatement st = connection.prepareStatement(
                        "SELECT user_name, password FROM account1 WHERE user_name=? AND password=?");

                st.setString(1, userName);
                st.setString(2, password);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    dispose();
                    Game ah = new Game(userName);
                    ah.setTitle("Welcome");
                    ah.setVisible(true);
                    JOptionPane.showMessageDialog(btnNewButton, "You have successfully logged in");
                } else {
                    JOptionPane.showMessageDialog(btnNewButton, "Wrong Username & Password");
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });
        contentPane.add(btnNewButton);

        label = new JLabel("");
        label.setBounds(61, -12, 1138, 562);
        contentPane.add(label);
    }
}

