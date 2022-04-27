package GUI;

import java.awt.*;
import javax.swing.*;

public class MainMenuFrame extends JFrame {

    private ConfigPanel configPanel;
    private ActionsPanel actionsPanel;

    public MainMenuFrame(String defaultDifficulty, String defaultShape) {
        // Add logo label
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(new ImageIcon("resources/logo.png"));
        add(logoLabel, BorderLayout.NORTH);
        // Add configuration dropdown menus
        configPanel = new ConfigPanel(defaultDifficulty,defaultShape);
        add(configPanel, BorderLayout.CENTER);
        // Add action buttons
        actionsPanel = new ActionsPanel(this,configPanel);
        add(actionsPanel, BorderLayout.SOUTH);

        setResizable(false);
        pack();
        setIconImage(new ImageIcon("resources/icon.png").getImage());
        setTitle("Minesweeper+");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        var app = new MainMenuFrame("Easy","Square");
        app.setVisible(true);
    }
}
