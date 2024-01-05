package com.perisic.tomato.peripherals;



import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import com.perisic.tomato.engine.GameEngine;

public class Game extends JFrame implements ActionListener {

    private static final long serialVersionUID = -107785653906635L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/employee_management_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Zujanshan83@";

    private int gamesPlayed = 0;

    JLabel questArea = null;
    GameEngine myGame = null;
    BufferedImage currentGame = null;
    JTextArea infoArea = null;

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    private void initGame(String player) {
        setSize(690, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("What is the missing value?");
        JPanel panel = new JPanel(new FlowLayout());

        myGame = new GameEngine(player);
        currentGame = myGame.nextGame();

        infoArea = new JTextArea(1, 40);
        infoArea.setEditable(false);
        infoArea.setText("What is the value of the tomato?   Score: 0");

        JScrollPane infoPane = new JScrollPane(infoArea);
        panel.add(infoPane);

        ImageIcon ii = new ImageIcon(currentGame);
        questArea = new JLabel(ii);
        questArea.setSize(330, 600);

        JScrollPane questPane = new JScrollPane(questArea);
        panel.add(questPane);

        for (int i = 0; i < 10; i++) {
            JButton btn = new JButton(String.valueOf(i));
            panel.add(btn);
            btn.addActionListener(this);
        }

        getContentPane().add(panel);
        panel.repaint();
    }

    private void showScoreboard() {
        JFrame scoreboardFrame = new JFrame("Scoreboard");
        scoreboardFrame.setSize(400, 300);
        scoreboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Fetch scoreboard data from the database
        String scoreboardData = getScoreboardData();

        JTextArea scoreboardTextArea = new JTextArea(scoreboardData);
        scoreboardTextArea.setEditable(false);

        JScrollPane scoreboardScrollPane = new JScrollPane(scoreboardTextArea);

        scoreboardFrame.add(scoreboardScrollPane);
        scoreboardFrame.setVisible(true);
    }

    private String getScoreboardData() {
        StringBuilder scoreboardData = new StringBuilder();

        try (Connection conn = getConnection()) {
            String selectQuery = "SELECT user_name, score FROM account1 ORDER BY score DESC";

            try (PreparedStatement preparedStatement = conn.prepareStatement(selectQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                scoreboardData.append("User Name\tScore\n");
                while (resultSet.next()) {
                    String playerName = resultSet.getString("user_name");
                    int score = resultSet.getInt("score");
                    scoreboardData.append(playerName).append("\t\t\t").append(score).append("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scoreboardData.toString();
    }

    private void updateScoreToDatabase(String userName, int score) {
        try (Connection conn = getConnection()) {
            String updateQuery = "UPDATE account1 SET score = ? WHERE user_name = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, score);
                preparedStatement.setString(2, userName);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Game() {
        super();
        initGame(null);
    }

    public Game(String player) {
        super();
        initGame(player);
    }

    public static void main(String[] args) {
        Game myGUI = new Game();
        myGUI.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int solution = Integer.parseInt(e.getActionCommand());
        boolean correct = myGame.checkSolution(solution);
        int score = myGame.getScore();

        if (correct) {
            System.out.println("Correct solution entered!");
            // Use the actual player's name instead of "YourPlayerName"
            updateScoreToDatabase("princy", score);
            currentGame = myGame.nextGame();
            ImageIcon ii = new ImageIcon(currentGame);
            questArea.setIcon(ii);
            infoArea.setText("Good!  Score: " + score);
        } else {
            System.out.println("Not Correct");
            infoArea.setText("Oops. Try again!  Score: " + score);
        }

        // Increment gamesPlayed and show the scoreboard after 5 games
        gamesPlayed++;
        if (gamesPlayed >= 5) {
            showScoreboard();
            gamesPlayed = 0; // Reset gamesPlayed for the next set of games
        }
    }
}
