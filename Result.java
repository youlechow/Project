import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import java.util.Date;
import java.io.*;
import java.text.SimpleDateFormat;

public class Result extends GamePanel implements MouseListener, ActionListener {
    private int resultScore;
    private int perfect;
    private int good;
    private int bad;
    private int miss;
    private int maxCombo;
    private int songNumber;
    private JButton restart;
    private JButton selectSong;
    private Play play = new Play(songNumber);
    private selectSong SelectSong = new selectSong();

    public Result(int resultScore, int perfect, int good, int bad, int miss, int songNumber,int maxCombo) {
        play.stop();
        this.resultScore = resultScore;
        this.perfect = perfect;
        this.good = good;
        this.bad = bad;
        this.miss = miss;
        this.maxCombo = maxCombo;
        this.songNumber = songNumber;
        resultScore = play.getScore();
        setLayout(null);
        initializeComponents();
        save();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, frameWidth, frameHeight);
        g.setColor(Color.WHITE);
        Font scoreFont = new Font("Algerian", Font.BOLD, 50);
        g.setFont(scoreFont);
        g.drawString("Score : " + resultScore, frameWidth * 2 / 10, frameHeight * 3 / 10);
        g.setColor(Color.GREEN);
        g.drawString("Perfect : " + perfect, frameWidth * 2 / 10, frameHeight * 4 / 10);
        g.setColor(Color.BLUE);
        g.drawString("Good : " + good, frameWidth * 2 / 10, frameHeight * 5 / 10);
        g.setColor(Color.YELLOW);
        g.drawString("Bad : " + bad, frameWidth * 2 / 10, frameHeight * 6 / 10);
        g.setColor(Color.RED);
        g.drawString("Miss : " + miss, frameWidth * 2 / 10, frameHeight * 7 / 10);
        g.drawString("Max Combo :" + maxCombo, frameWidth * 2 / 10, frameHeight * 8 / 10);
    }

    public void initializeComponents() {
        restart = createStyledButton("Restart");
        selectSong = createStyledButton("Continue");
        restart.setBounds(frameWidth * 7 / 10, frameHeight * 4 / 10, frameWidth * 2 / 10, frameHeight * 1 / 10);
        selectSong.setBounds(frameWidth * 7 / 10, frameHeight * 5 / 10, frameWidth * 2 / 10, frameHeight * 1 / 10);

        restart.addActionListener(this);
        selectSong.addActionListener(this);
        
        add(restart);
        add(selectSong);
    }

    // MouseListener methods
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    // ActionListener method
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restart) {
            play = new Play(songNumber);
            MenuPage.frame.setContentPane(play);
            MenuPage.frame.revalidate();
            MenuPage.frame.repaint();
            play.requestFocusInWindow();
        } else if (e.getSource() == selectSong) {
            SelectSong = new selectSong();
            MenuPage.frame.setContentPane(SelectSong);
            MenuPage.frame.revalidate();
            MenuPage.frame.repaint();
        }
    }

    private void save(){
        switch (songNumber) {
            case 0:
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(".htr/mozart.txt", true))) {
                    // Append data to the file
                    writer.write(getCurrentDateTime() + "," + resultScore + "," + perfect + "," + good + "," + bad + "," + miss + "," + songNumber + "," + maxCombo);
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        
            case 1:
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(".htr/spiritedAway.txt", true))) {
                    // Append data to the file
                    writer.write(getCurrentDateTime() + "," + resultScore + "," + perfect + "," + good + "," + bad + "," + miss + "," + songNumber + "," + maxCombo);
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        
    }

    private String getCurrentDateTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
