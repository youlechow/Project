import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class MenuPage extends GamePanel implements ActionListener {
    //frame
    public static JFrame frame;
    //button
    private JButton start;
    private JButton exit;
    //image
    private ImageIcon icon;
    private Image image;

    public MenuPage() {
        super();
        this.initializeBackground();
        this.setLayout(null);
        this.initializeComponents();
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    // draw the background image
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, this);
    }

    // use the super class Abstract to set button
    protected void initializeComponents() {
        start = createStyledButton("Start");
        exit = createStyledButton("Exit");
        start.setBounds(frameWidth / 2 - frameWidth * 2 / 10 / 2, frameHeight * 4 / 10, frameWidth * 2 / 10,
                frameHeight * 1 / 10);
        exit.setBounds(frameWidth / 2 - frameWidth * 2 / 10 / 2, frameHeight * 5 / 10, frameWidth * 2 / 10,
                frameHeight * 1 / 10);

        start.addActionListener(this);
        exit.addActionListener(this);

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
            SelectSong selectSong = new SelectSong();

            frame.setContentPane(selectSong);
            // Refresh the frame to show the new content
            frame.revalidate();
            frame.repaint();
        }
    }

    // set up the frame
    public void initializeFrame() {
        frame = new JFrame("Menu Page");
        MenuPage menu = new MenuPage();

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.add(menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // load the background image and set size
    public void initializeBackground() {
        icon = new ImageIcon(".img/background1.png");
        Image getImage = icon.getImage();
        image = getImage.getScaledInstance(frameWidth, frameHeight, Image.SCALE_SMOOTH);
    }

}
