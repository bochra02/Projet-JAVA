package authentification.ui.components;

import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;

/**
 * ModernButton - Bouton moderne avec effets hover
 * Classe fille de JButton
 */
public class ModernButton extends JButton {
    private Color hoverColor;
    private Color pressedColor;
    private Color normalColor;
    private boolean isHovered = false;
    private boolean isPressed = false;

    public ModernButton(String text, Color normal, Color hover, Color pressed) {
        super(text);
        this.normalColor = normal;
        this.hoverColor = hover;
        this.pressedColor = pressed;

        setFont(new Font("Segoe UI", Font.BOLD, 16));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isPressed) {
            g2.setColor(pressedColor);
        } else if (isHovered) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(normalColor);
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        g2.dispose();

        super.paintComponent(g);
    }
}
