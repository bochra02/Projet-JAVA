package authentification.ui_authentification;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * ModernTextField - Champ de texte moderne
 * Classe fille de JTextField
 */
public class ModernTextField extends JTextField {
    private Shape shape;

    public ModernTextField(int size) {
        super(size);
        setOpaque(false);
        setFont(new Font("Segoe UI", Font.PLAIN, 15));
        setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        setBackground(new Color(255, 255, 255, 200));
        setForeground(new Color(40, 40, 40));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(200, 200, 200, 100));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        }
        return shape.contains(x, y);
    }
}
