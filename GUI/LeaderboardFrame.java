package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LeaderboardFrame extends JFrame {

    public LeaderboardFrame(String difficulty, String shape) {

        JLabel titleLabel = new JLabel();
        titleLabel.setIcon(new ImageIcon("resources/leaderboard.png"));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new GridLayout(4,5,25,25));
        leaderboardPanel.add(new JLabel(""));
        leaderboardPanel.add(new JLabel("Easy"));
        leaderboardPanel.add(new JLabel("Medium"));
        leaderboardPanel.add(new JLabel("Hard"));
        leaderboardPanel.add(new JLabel("Very Hard"));
        leaderboardPanel.add(new JLabel("Square:"));
        leaderboardPanel.add(new JLabel(getBestSolveTimeFromDat("Easy", "Square")));
        leaderboardPanel.add(new JLabel(getBestSolveTimeFromDat("Medium", "Square")));
        leaderboardPanel.add(new JLabel(getBestSolveTimeFromDat("Hard", "Square")));
        leaderboardPanel.add(new JLabel(getBestSolveTimeFromDat("Very Hard", "Square")));
        leaderboardPanel.add(new JLabel("Triangle:"));
        leaderboardPanel.add(new JLabel(getBestSolveTimeFromDat("Easy", "Triangle")));
        leaderboardPanel.add(new JLabel(getBestSolveTimeFromDat("Medium", "Triangle")));
        leaderboardPanel.add(new JLabel(getBestSolveTimeFromDat("Hard", "Triangle")));
        leaderboardPanel.add(new JLabel(getBestSolveTimeFromDat("Very Hard", "Triangle")));
        leaderboardPanel.add(new JLabel("Hexagon:"));
        leaderboardPanel.add(new JLabel(getBestSolveTimeFromDat("Easy", "Hexagon")));
        leaderboardPanel.add(new JLabel(getBestSolveTimeFromDat("Medium", "Hexagon")));
        leaderboardPanel.add(new JLabel(getBestSolveTimeFromDat("Hard", "Hexagon")));
        leaderboardPanel.add(new JLabel(getBestSolveTimeFromDat("Very Hard", "Hexagon")));
        leaderboardPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
        add(leaderboardPanel, BorderLayout.CENTER);

        setResizable(false);
        pack();
        setIconImage(new ImageIcon("resources/icon.png").getImage());
        setTitle("Leaderboard");
        setLocationRelativeTo(null);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainMenuFrame app = new MainMenuFrame(difficulty,shape);
                app.setVisible(true);
            }
        });
    }

    public String getBestSolveTimeFromDat(String difficulty, String shape) {
        FileReader readFile = null;
        BufferedReader reader = null;
        try {
            readFile = new FileReader("Leaderboard/" + shape + difficulty.replaceAll("\\s","") + ".dat");
            reader = new BufferedReader(readFile);
            return reader.readLine();
        }
        catch (Exception e) {
            return "--:--";
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
