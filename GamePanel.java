import javax.swing.*;
import java.awt.*;

public abstract class GamePanel extends JPanel {
    protected int frameWidth;
    protected int frameHeight;

    public GamePanel() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.frameHeight = screenSize.height;
        this.frameWidth = screenSize.width;
        setLayout(null);
    }

    //set the button style for all
    protected JButton createStyledButton(String label) {
        JButton button = new JButton(label);
        Font font = new Font("Algerian", Font.BOLD, 50);
        Color color = new Color(88, 217, 246);
        button.setFont(font);
        button.setBackground(Color.WHITE);
        button.setForeground(color);
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        return button;
    }

    // get set method
    public int getFrameHeight(){
        return frameHeight;
    }

    public int getFrameWidth(){
        return frameWidth;
    }

    public void setFrameHeight(int frameHeight){
        this.frameHeight = frameHeight;
    }

    public void setFrameWidth(int frameWidth){
        this.frameWidth = frameWidth;
    }
    // Abstract method
    protected abstract void initializeComponents();
}
