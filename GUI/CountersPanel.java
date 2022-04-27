package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CountersPanel extends JPanel {
    private JLabel flagsLeftLabel100;
    private JLabel flagsLeftLabel10;
    private JLabel flagsLeftLabel1;
    private JLabel statusFaceLabel;
    private JLabel timeMinLabel10;
    private JLabel timeMinLabel1;
    private JLabel timeSecLabel10;
    private JLabel timeSecLabel1;
    private Timer timer;
    private int minute, second;
    private String gameShape, gameDifficulty;
    DecimalFormat dFormat00 = new DecimalFormat("00");
    DecimalFormat dFormat000 = new DecimalFormat("000");

    public CountersPanel(String gameDifficulty, String gameShape) {
        this.gameShape = gameShape;
        this.gameDifficulty = gameDifficulty;
        flagsLeftLabel100 = new JLabel();
        flagsLeftLabel10 = new JLabel();
        flagsLeftLabel1 = new JLabel();
        statusFaceLabel = new JLabel();
        timeMinLabel10 = new JLabel();
        timeMinLabel1 = new JLabel();
        timeSecLabel10 = new JLabel();
        timeSecLabel1 = new JLabel();
        flagsLeftLabel100.setHorizontalAlignment(JLabel.LEFT);
        flagsLeftLabel10.setHorizontalAlignment(JLabel.LEFT);
        flagsLeftLabel1.setHorizontalAlignment(JLabel.LEFT);
        timeMinLabel10.setHorizontalAlignment(JLabel.RIGHT);
        timeMinLabel1.setHorizontalAlignment(JLabel.RIGHT);
        timeSecLabel10.setHorizontalAlignment(JLabel.RIGHT);
        timeSecLabel1.setHorizontalAlignment(JLabel.RIGHT);
        add(flagsLeftLabel100, BorderLayout.WEST);
        add(flagsLeftLabel10, BorderLayout.WEST);
        add(flagsLeftLabel1, BorderLayout.WEST);
        add(statusFaceLabel, BorderLayout.CENTER);
        add(timeMinLabel10, BorderLayout.EAST);
        add(timeMinLabel1, BorderLayout.EAST);
        add(timeSecLabel10, BorderLayout.EAST);
        add(timeSecLabel1, BorderLayout.EAST);
        this.second = 0;
        timerClock();
    }

    public void updateFlags(int flagsLeft) {
        String flagsStr = dFormat000.format(flagsLeft);
        flagsLeftLabel1.setIcon(new ImageIcon("resources/moves"+ flagsStr.charAt(2) +".png"));
        flagsLeftLabel10.setIcon(new ImageIcon("resources/moves"+ flagsStr.charAt(1) +".png"));
        flagsLeftLabel100.setIcon(new ImageIcon("resources/moves"+ flagsStr.charAt(0) +".png"));
    }

    // 0 for ongoing, 1 for won, 2 for lost
    public void updateStatusFace(int status) {
        switch (status) {
            case 0:
                statusFaceLabel.setIcon(new ImageIcon("resources/facesmile.png"));
                break;
            case 1:
                statusFaceLabel.setIcon(new ImageIcon("resources/faceooh.png"));
                break;
            case 2:
                statusFaceLabel.setIcon(new ImageIcon("resources/facewin.png"));
                break;
            case 3:
                statusFaceLabel.setIcon(new ImageIcon("resources/facedead.png"));
                break;
        }
    }

    public void stopTimer() {
        timer.stop();
    }

    public void startTimer() {
        timer.start();
    }

    public void resetTimer() {
        timer.stop();
        this.second = 0;
        this.minute = 0;
        renderTimerImgs();
    }

    private void timerClock() {
        renderTimerImgs();
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                second++;
                if (second==60) {
                    second=0;
                    minute++;
                }
                renderTimerImgs();
            }
        });
    }

    public void renderTimerImgs() {
        String minStr = dFormat00.format(minute);
        timeMinLabel1.setIcon(new ImageIcon("resources/time"+ minStr.charAt(1) +".png"));
        timeMinLabel10.setIcon(new ImageIcon("resources/time"+ minStr.charAt(0) +".png"));
        String secStr = dFormat00.format(second);
        timeSecLabel1.setIcon(new ImageIcon("resources/time"+ secStr.charAt(1) +".png"));
        timeSecLabel10.setIcon(new ImageIcon("resources/time"+ secStr.charAt(0) +".png"));
    }
    
    public void checkForNewBestSolveTime() {
        String currBestSolveTime = getBestSolveTimeFromDat();
        int newSolveTimeSeconds = 60*this.minute + this.second;
        
        if (currBestSolveTime == "N/A" || newSolveTimeSeconds < convertSolveTimeStringToSeconds(currBestSolveTime)) {
            String newBestSolveTime = dFormat00.format(minute) + ":" + dFormat00.format(second);

            File solveTimeFile = new File("Leaderboard/" + gameShape + gameDifficulty.replaceAll("\\s","") + ".dat");
            if (!solveTimeFile.exists()) {
                try {
                    solveTimeFile.createNewFile();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            FileWriter writeFile = null;
            BufferedWriter writer = null;
            try {
                writeFile = new FileWriter(solveTimeFile);
                writer = new BufferedWriter(writeFile);
                writer.write(newBestSolveTime);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getBestSolveTimeFromDat() {
        FileReader readFile = null;
        BufferedReader reader = null;
        try {
            readFile = new FileReader("Leaderboard/" + gameShape + gameDifficulty.replaceAll("\\s","") + ".dat");
            reader = new BufferedReader(readFile);
            return reader.readLine();
        }
        catch (Exception e) {
            return "N/A";
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

    private int convertSolveTimeStringToSeconds(String solveTimeString) {
        String[] tokens = solveTimeString.split(":"); // solveTimeString format --> mm:ss
        int solveTimeMins = Integer.parseInt(tokens[0]);
        int solveTimeSecs = Integer.parseInt(tokens[1]);
        return solveTimeMins*60 + solveTimeSecs;
    }

}
