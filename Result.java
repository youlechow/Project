import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Result extends JPanel implements MouseListener, ActionListener {
    private int resultScore;
    private int perfect;
    private int good;
    private int bad;
    private int miss;
    private int frameHeight;
    private int frameWidth;
    private JButton restart;
    private JButton selectSong;
    private MenuPage menuPage = new MenuPage();
    private Play play = new Play();
    private selectSong SelectSong = new selectSong();

    public Result(int resultScore, int perfect, int good, int bad, int miss) {
        play.stop();
        this.resultScore = resultScore;
        this.frameHeight = menuPage.getFrameHeight();
        this.frameWidth = menuPage.getFrameWidth();
        this.perfect = perfect;
        this.good = good;
        this.bad = bad;
        this.miss = miss;
        resultScore = play.getScore();
        setLayout(null);
        initializeButtons();
        addToPanel();
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
    }

    public void initializeButtons() {
        restart = createStyledButton("Restart");
        selectSong = createStyledButton("Continue");
        restart.setBounds(frameWidth * 7 / 10, frameHeight * 4 / 10, frameWidth * 2 / 10, frameHeight * 1 / 10);
        selectSong.setBounds(frameWidth * 7 / 10, frameHeight * 5 / 10, frameWidth * 2 / 10, frameHeight * 1 / 10);

    }

    public JButton createStyledButton(String label) {
        JButton button = new JButton(label);
        Font font = new Font("Algerian", Font.BOLD, 50);
        Color color = new Color(88, 217, 246);
        button.setFont(font);
        button.addMouseListener(this);
        button.addActionListener(this);
        button.setForeground(color);
        button.setBackground(color);
        button.setOpaque(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        return button;
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

    public void addToPanel() {
        add(restart);
        add(selectSong);
    }

    // ActionListener method
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restart) {
            play = new Play();
            MenuPage.frame.setContentPane(play);
            MenuPage.frame.revalidate();
            MenuPage.frame.repaint();
            play.requestFocusInWindow();
        } else if (e.getSource() == selectSong) {
            MenuPage.frame.setContentPane(SelectSong);
            MenuPage.frame.revalidate();
            MenuPage.frame.repaint();
        }
    }

}
