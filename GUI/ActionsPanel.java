package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class ActionsPanel extends JPanel {
    private JButton quitButton;
    private JButton leaderboardButton;
    private JButton playButton;
    private ConfigPanel configPanel;

    public ActionsPanel(MainMenuFrame app, ConfigPanel configPanel) {
        this.configPanel = configPanel;
        quitButton = new JButton("Quit");
        leaderboardButton = new JButton("Leaderboard");
        playButton = new JButton("Play");
        quitButton.setPreferredSize(new Dimension(130, 35));
        leaderboardButton.setPreferredSize(new Dimension(130, 35));
        playButton.setPreferredSize(new Dimension(130, 35));
        add(quitButton, BorderLayout.WEST);
        add(leaderboardButton, BorderLayout.CENTER);
        add(playButton, BorderLayout.EAST);
        setBorder(new EmptyBorder(15, 0, 15, 0));

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String difficulty = configPanel.getDifficultyValue();
                String shape = configPanel.getShapeValue();
                app.setVisible(false);
                app.dispose();
                GameFrame game = new GameFrame(difficulty,shape);
                game.setVisible(true);
            }
        } );

        leaderboardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String difficulty = configPanel.getDifficultyValue();
                String shape = configPanel.getShapeValue();
                app.setVisible(false);
                app.dispose();
                LeaderboardFrame leaderboard = new LeaderboardFrame(difficulty,shape);
                leaderboard.setVisible(true);
            }
        } );

        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.dispatchEvent(new WindowEvent(app, WindowEvent.WINDOW_CLOSING));
            }
        } );
    }
}
