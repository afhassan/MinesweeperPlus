package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ConfigPanel extends JPanel {
    private String[] difficulties = {"Easy", "Medium", "Hard", "Very Hard"};
    private String[] shapes = { "Square", "Triangle", "Hexagon"};
    private JLabel difficultyLabel;
    private JComboBox difficultyBox;
    private JLabel shapeLabel;
    private JComboBox shapeBox;

    public ConfigPanel(String defaultDifficulty, String defaultShape) {
        difficultyBox = new JComboBox(difficulties);
        shapeBox = new JComboBox(shapes);
        difficultyBox.setSelectedItem(defaultDifficulty);
        shapeBox.setSelectedItem(defaultShape);
        difficultyLabel = new JLabel("Difficulty: ");
        shapeLabel = new JLabel("Shape: ");
        add(difficultyLabel, BorderLayout.WEST);
        add(difficultyBox, BorderLayout.WEST);
        add(shapeLabel, BorderLayout.EAST);
        add(shapeBox, BorderLayout.EAST);
        setBorder(new EmptyBorder(10, 0, 10, 0));
    }

    public String getDifficultyValue() {
        return (String) difficultyBox.getSelectedItem();
    }

    public String getShapeValue() {
        return (String) shapeBox.getSelectedItem();
    }

}
