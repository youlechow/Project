import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class effect extends JFrame {

    public effect() {
        this.setTitle("Dynamic Aperture Effect Demo");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new AperturePanel());
        this.setSize(400, 400);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new effect();
    }

    class AperturePanel extends JPanel {
        private Point2D center = new Point2D.Float(200, 200);
        private float radius = 0;
        private Timer timer;

        public AperturePanel() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    center.setLocation(e.getX(), e.getY());
                    radius = 0;
                    if (timer != null) {
                        timer.stop();
                    }
                    timer = new Timer(10, ae -> {
                        radius += 1;
                        repaint();
                        
                    });
                    
                    timer.start();
                    if(radius>20){
                        timer.stop();
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Enable anti-aliasing
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Create gradient paint
            float[] dist = { 0.0f, 1.0f };
            Color[] colors = { Color.YELLOW, Color.WHITE };
            RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);

            // Draw circle
            g2d.setPaint(p);
            g2d.fillOval((int) center.getX() - (int) radius, (int) center.getY() - (int) radius, (int) radius * 2,
                    (int) radius * 2);
        }
    }
}
