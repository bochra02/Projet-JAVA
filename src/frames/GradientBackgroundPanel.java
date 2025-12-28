package frames;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Reusable background panel that caches a gradient into a BufferedImage
 * to avoid expensive repaints and provide a consistent theme.
 */
public class GradientBackgroundPanel extends JPanel {
    private Color color1 = new Color(0, 51, 102);
    private Color color2 = new Color(0, 102, 204);
    private BufferedImage cache;

    public GradientBackgroundPanel() {
        setOpaque(true);
    }

    public GradientBackgroundPanel(Color c1, Color c2) {
        this();
        this.color1 = c1;
        this.color2 = c2;
    }

    private void ensureCache(int w, int h) {
        if (cache == null || cache.getWidth() != w || cache.getHeight() != h) {
            if (w <= 0 || h <= 0) return;
            cache = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = cache.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
            g2.setPaint(gp);
            g2.fillRect(0, 0, w, h);
            g2.dispose();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();
        ensureCache(w, h);
        if (cache != null) {
            g.drawImage(cache, 0, 0, null);
        }
    }
}
