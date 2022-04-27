package GUI;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;
import javax.swing.*;

public class GameFrame extends JFrame {

    private CountersPanel countersPanel;
    private Hashtable<String, int[]> configs_mapping = new Hashtable<String, int[]>();

    public GameFrame(String difficulty, String shape) {
        configs_mapping.put("Easy",new int[]{10,8,10});
        configs_mapping.put("Medium",new int[]{40,14,18});
        configs_mapping.put("Hard",new int[]{100,20,24});
        configs_mapping.put("Very Hard",new int[]{200,24,34});
        int[] configs = configs_mapping.get(difficulty);

        // Add counters panel
        countersPanel = new CountersPanel(difficulty, shape);
        add(countersPanel, BorderLayout.NORTH);

        // Add suitable board panel
        switch (shape) {
            case "Square":
                add(new SquareBoardPanel(configs[0], configs[1], configs[2], this.countersPanel), BorderLayout.SOUTH);
                break;
            case "Hexagon":
                add(new HexagonBoardPanel(configs[0], configs[1], configs[2], this.countersPanel), BorderLayout.SOUTH);
                break;
            case "Triangle":
                add(new TriangleBoardPanel(configs[0], configs[1], configs[2], this.countersPanel), BorderLayout.SOUTH);
                break;
        }

        setResizable(false);
        pack();
        setIconImage(new ImageIcon("resources/icon.png").getImage());
        setTitle(shape + " - " + difficulty);
        setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainMenuFrame app = new MainMenuFrame(difficulty,shape);
                app.setVisible(true);
            }
        });
    }
}
