import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class selectSong extends JPanel implements MouseListener, ActionListener {
    private int frameHeight;
    private int frameWidth;
    private JButton next;
    private JButton previous;
    private JButton back;
    private JButton start;
    private ImageIcon backgroundIcon = new ImageIcon(".img/background1.png");
    private ImageIcon SongImageIcon = new ImageIcon(".img/Mozart.jpeg");
    private Image background;
    private Image songImage;
    private int iconWidthPropotion;
    private int iconHeightPropotion;
    private MenuPage menuPage = new MenuPage();

    public selectSong() {
        this.frameHeight = menuPage.getFrameHeight();
        this.frameWidth = menuPage.getFrameWidth();
        loadImage();
        setLayout(null);
        initializeButtons();
        addToPanel();
    }

    public void initializeButtons() {
        next = createStyledButton();
        previous = createStyledButton();
        back = createStyledButton1("Back");
        start = createStyledButton1("Start");

        // set back & start bounds
        back.setBounds(frameWidth * 2 / 100, frameHeight * 9 / 10, frameWidth * 1 / 10, frameHeight * 1 / 10);
        start.setBounds(frameWidth * 86 / 100, frameHeight * 9 / 10, frameWidth * 12 / 100, frameHeight * 1 / 10);

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
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, frameWidth, frameHeight);

        // draw background
        // draw background
        drawBackground((Graphics2D) g);
        g.drawImage(background, 0, 0, this);

        // reset transparent
        resetTransparent((Graphics2D) g);

        g.drawImage(songImage, frameWidth / 2 - iconWidthPropotion / 2, frameHeight / 2 - iconHeightPropotion / 2,
                this);
    }

    private void drawBackground(Graphics2D g2d) {
        float alpha = 0.5f;
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alphaComposite);
    }

    private void resetTransparent(Graphics2D g2d) {
        float alphaOriginal = 1.0f;
        AlphaComposite alphaCompositeOriginal = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaOriginal);
        g2d.setComposite(alphaCompositeOriginal);
    }

    private JButton createStyledButton() {
        JButton button = new JButton();
        button.addMouseListener(this);
        return button;
    }

    private JButton createStyledButton1(String label) {
        JButton button = new JButton(label);
        Font font = new Font("Algerian", Font.BOLD, 50);
        Color color = new Color(88, 217, 246);
        button.setFont(font);
        button.setBackground(Color.WHITE);
        button.setOpaque(false);
        button.setForeground(color);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.addActionListener(this);
        button.addMouseListener(this);
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
            Play play = new Play();
            MenuPage.frame.setContentPane(play);
            MenuPage.frame.revalidate();
            MenuPage.frame.repaint();
            play.requestFocusInWindow();
        }
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

    private void addToPanel() {
        add(next);
        add(previous);
        add(back);
        add(start);
    }

    private void loadImage() {
        Image getImage = backgroundIcon.getImage();
        background = getImage.getScaledInstance(frameWidth, frameHeight, Image.SCALE_SMOOTH);

        int iconHeight = SongImageIcon.getIconHeight();
        int iconWidth = SongImageIcon.getIconWidth();

        iconHeightPropotion = frameHeight * 6 / 10;
        iconWidthPropotion = (int) Math.round((double) iconHeightPropotion / iconHeight * iconWidth);

        getImage = SongImageIcon.getImage();
        songImage = getImage.getScaledInstance(iconWidthPropotion, iconHeightPropotion, Image.SCALE_SMOOTH);
    }
}