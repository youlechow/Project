import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class SelectSong extends GamePanel implements  ActionListener {
    //ui button
    private JButton next;
    private JButton previous;
    private JButton back;
    private JButton start;

    //Image
    private ImageIcon backgroundIcon = new ImageIcon(".img/background1.png");
    private ImageIcon MozartIcon = new ImageIcon(".img/Mozart.jpeg");
    private ImageIcon SpiritedAwayIcon = new ImageIcon(".img/SpiritedAway.jpg");
    private Image background;
    private Image MozartImage;
    private Image SpiritedAwayImage;
    private int iconWidthPropotion;
    private int iconHeightPropotion;

    //game state
    private int songNumber;
    private int maxCombo;
    private int highScore;
    private MenuPage menuPage = new MenuPage();

    //constructor
    public SelectSong(int iconHeightPropotion, int iconWidthPropotion, int songNumber, int maxCombo, int highScore) {
        this.iconHeightPropotion = iconHeightPropotion;
        this.iconWidthPropotion = iconWidthPropotion;
        this.songNumber = songNumber;
        this.maxCombo = maxCombo;
        this.highScore = highScore;
    }

    public SelectSong() {
        this.frameHeight = getFrameHeight();
        this.frameWidth = getFrameWidth();
        readData();
        loadImage();
        setLayout(null);
        initializeComponents();

    }

    //use the super class Abstract to set button
    public void initializeComponents() {
        next = createStyledButton();
        previous = createStyledButton();
        back = createStyledButton("Back");
        start = createStyledButton("Start");

        // set back & start bounds
        back.setBounds(frameWidth * 1 / 100, frameHeight * 9 / 10, frameWidth * 15 / 100, frameHeight * 1 / 10);
        start.setBounds(frameWidth * 84 / 100, frameHeight * 9 / 10, frameWidth * 15 / 100, frameHeight * 1 / 10);
        back.addActionListener(this);
        start.addActionListener(this);
        // load icon
        ImageIcon iconNext = new ImageIcon(".img/next.png");
        ImageIcon iconPrevious = new ImageIcon(".img/back.png");

        previous.setIcon(iconPrevious);
        previous.setContentAreaFilled(false);
        previous.setAlignmentX(RIGHT_ALIGNMENT);
        previous.setBorderPainted(false);
        previous.setBounds(frameWidth * 1 / 100, (frameHeight / 2) - iconPrevious.getIconHeight() / 2,
                iconPrevious.getIconWidth(), iconPrevious.getIconHeight());

        next.setIcon(iconNext);
        next.setContentAreaFilled(false);
        next.setAlignmentX(RIGHT_ALIGNMENT);
        next.setBorderPainted(false);
        next.setBounds(frameWidth - (iconNext.getIconWidth() + frameWidth * 1 / 100),
                (frameHeight / 2) - iconPrevious.getIconHeight() / 2,
                iconNext.getIconWidth(), iconNext.getIconHeight());

        add(next);
        add(previous);
        add(back);
        add(start);
    }

    //draw the object
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, frameWidth, frameHeight);

        //set the transparent
        setTransparent((Graphics2D) g);
        // draw background
        g.drawImage(background, 0, 0, this);

        //set font and color
        Font scoreFont = new Font("Algerian", Font.BOLD, 30);
        g.setFont(scoreFont);
        g.setColor(Color.WHITE);

        // reset transparent
        resetTransparent((Graphics2D) g);

        //use switch to change the songList
        switch (songNumber) {
            case 0:
                g.drawImage(MozartImage, frameWidth / 2 - iconWidthPropotion / 2,
                        frameHeight / 2 - iconHeightPropotion / 2,
                        this);
                g.drawString("High Score: " + highScore, frameWidth / 2 - iconWidthPropotion / 2,
                        frameHeight * 19 / 100);
                g.drawString("Max Combo: " + maxCombo, frameWidth * 6 / 10,
                        frameHeight * 19 / 100);
                repaint();
                revalidate();
                break;
            case 1:
                g.drawImage(SpiritedAwayImage, frameWidth / 2 - iconWidthPropotion / 2,
                        frameHeight / 2 - iconHeightPropotion / 2,
                        this);
                g.drawString("High Score: " + highScore, frameWidth / 2 - iconWidthPropotion / 2,
                        frameHeight * 19 / 100);
                g.drawString("Max Combo: " + maxCombo, frameWidth * 6 / 10,
                        frameHeight * 19 / 100);
                repaint();
                revalidate();
                break;
        }

    }

    //set the transparent need
    private void setTransparent(Graphics2D g2d) {
        float alpha = 0.5f;
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alphaComposite);
    }

    //reset the transparent
    private void resetTransparent(Graphics2D g2d) {
        float alphaOriginal = 1.0f;
        AlphaComposite alphaCompositeOriginal = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaOriginal);
        g2d.setComposite(alphaCompositeOriginal);
    }

    //set up button without label
    private JButton createStyledButton() {
        JButton button = new JButton();
        button.addActionListener(this);
        return button;
    }

    // ActionListener method
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            MenuPage.frame.setContentPane(menuPage);
            // Refresh the frame to show the new content
            MenuPage.frame.revalidate();
            MenuPage.frame.repaint();
        } else if (e.getSource() == start) {
            Play play = new Play(songNumber);
            MenuPage.frame.setContentPane(play);
            MenuPage.frame.revalidate();
            MenuPage.frame.repaint();
            play.requestFocusInWindow();
        }

        if (e.getSource() == next && songNumber != 1) {
            songNumber++;
            readData();
            repaint();
            revalidate();
        } else if (e.getSource() == next) {
            songNumber = songNumber - songNumber;
            readData();
            readData();
            repaint();
            revalidate();
        }

        if (e.getSource() == previous && songNumber != 0) {
            songNumber--;
            readData();
            repaint();
            revalidate();
        } else if (e.getSource() == previous) {
            songNumber = 1;
            readData();
            repaint();
            revalidate();
        }

    }

    //load all the image and set size
    private void loadImage() {
        Image getImage = backgroundIcon.getImage();
        background = getImage.getScaledInstance(frameWidth, frameHeight, Image.SCALE_SMOOTH);

        int iconHeight = SpiritedAwayIcon.getIconHeight();
        int iconWidth = SpiritedAwayIcon.getIconWidth();

        iconHeightPropotion = frameHeight * 6 / 10;
        iconWidthPropotion = (int) Math.round((double) iconHeightPropotion / iconHeight * iconWidth);

        getImage = SpiritedAwayIcon.getImage();
        SpiritedAwayImage = getImage.getScaledInstance(iconWidthPropotion, iconHeightPropotion, Image.SCALE_SMOOTH);

        iconHeight = MozartIcon.getIconHeight();
        iconWidth = MozartIcon.getIconWidth();
        iconWidthPropotion = (int) Math.round((double) iconHeightPropotion / iconHeight * iconWidth);
        getImage = MozartIcon.getImage();
        MozartImage = getImage.getScaledInstance(iconWidthPropotion, iconHeightPropotion, Image.SCALE_SMOOTH);
    }

    //to get the history data with higher score and higher combo
    public void readData() {
        highScore = 0;
        maxCombo = 0;
        switch (songNumber) {
            case 0:
                try (BufferedReader reader = new BufferedReader(new FileReader(".htr/mozart.txt"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] data = line.split(",");
                        int resultScore = Integer.parseInt(data[1]);
                        int combo = Integer.parseInt(data[7]);

                        if (resultScore > highScore) {
                            highScore = resultScore;
                        }
                        if (combo > maxCombo) {
                            maxCombo = combo;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 1:
                try (BufferedReader reader = new BufferedReader(new FileReader(".htr/spiritedAway.txt"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] data = line.split(",");
                        int resultScore = Integer.parseInt(data[1]);
                        int combo = Integer.parseInt(data[7]);

                        if (resultScore > highScore) {
                            highScore = resultScore;
                        }
                        if (combo > maxCombo) {
                            maxCombo = combo;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

        repaint();
        revalidate();
    }
}
