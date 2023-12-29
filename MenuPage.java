import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class MenuPage extends GamePanel implements ActionListener, MouseListener {
    static JFrame frame;
    private JButton start;
    private JButton exit;
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, this);
    }

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
