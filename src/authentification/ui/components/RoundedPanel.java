package authentification.ui.components;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * RoundedPanel - Panneau avec coins arrondis
 * Classe fille de JPanel
 */
public class RoundedPanel extends JPanel {
    private Color backgroundColor;
    private int cornerRadius;

    public RoundedPanel(int radius, Color bgColor) {
        super();
        cornerRadius = radius;
        backgroundColor = bgColor;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(backgroundColor);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
        g2.dispose();
    }
}