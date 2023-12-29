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

    // Other common methods can be added here
    public int getFrameHeight(){
        return frameHeight;
    }

    public int getFrameWidth(){
        return frameWidth;
    }
    // Abstract method example (must be implemented in subclasses)
    protected abstract void initializeComponents();
}
