import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuPage extends JPanel implements ActionListener, MouseListener {
    private int frameWidth;
    private int frameHeight;
    static JFrame frame;
    private JButton start;
    private JButton exit;
    private ImageIcon icon;
    private Image image;
    private Dimension screenSize;

    protected MenuPage(Dimension screenSize, int frameHeight, int frameWidth) {
        this.screenSize = screenSize;
        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;
    }

    public MenuPage() {
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.frameHeight = screenSize.height;
        this.frameWidth = screenSize.width;
        this.initializeBackground();
        this.setLayout(null);
        this.initializeButtons();
        this.addButtonsToPanel();
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, this);
    }

    public Dimension getScreenSize() {
        return screenSize;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public void setScreenSize(Dimension screenSize) {
        this.screenSize = screenSize;
    }

    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    private void initializeButtons() {
        start = createStyledButton("Start");
        exit = createStyledButton("Exit");
        start.setBounds(frameWidth / 2 - frameWidth * 2 / 10 / 2, frameHeight * 4 / 10, frameWidth * 2 / 10,
                frameHeight * 1 / 10);
        exit.setBounds(frameWidth / 2 - frameWidth * 2 / 10 / 2, frameHeight * 5 / 10, frameWidth * 2 / 10,
                frameHeight * 1 / 10);
    }

    private JButton createStyledButton(String label) {
        JButton button = new JButton(label);
        Font font = new Font("Algerian", Font.BOLD, 50);
        Color color = new Color(88, 217, 246);
        button.setFont(font);
        button.setBackground(Color.white);
        button.setForeground(color);
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.addActionListener(this);
        button.addMouseListener(this);
        return button;
    }

    private void addButtonsToPanel() {
        add(start);
        add(exit);
    }

    // ActionListener method
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit) {

            System.out.println();
            System.exit(0);
        } else if (e.getSource() == start) {
            selectSong selectSong = new selectSong();

            frame.setContentPane(selectSong);
            // Refresh the frame to show the new content
            frame.revalidate();
            frame.repaint();
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

    public void initializeFrame() {
        frame = new JFrame("Menu Page");
        MenuPage menu = new MenuPage();

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.add(menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void initializeBackground() {
        icon = new ImageIcon(".img/background1.png");
        Image getImage = icon.getImage();
        image = getImage.getScaledInstance(frameWidth, frameHeight, Image.SCALE_SMOOTH);
    }

}
