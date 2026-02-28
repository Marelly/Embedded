package ai.ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Responsible for rendering a single drone instance.
 * Also tracks the maximum drawn dimensions so explosions can size themselves.
 */
public class DroneRenderer {
    private double x, y, headingDeg;
    private BufferedImage image;

    // keep track of maximum drawn size (after scaling /20) seen so far
    private static double maxDrawWidth = 0;
    private static double maxDrawHeight = 0;

    public DroneRenderer(double x, double y, double headingDeg, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.headingDeg = headingDeg;
        this.image = image;
        updateMaxSize();
    }

    private void updateMaxSize() {
        if (image != null) {
            double w = image.getWidth() / 20.0;
            double h = image.getHeight() / 20.0;
            maxDrawWidth = Math.max(maxDrawWidth, w);
            maxDrawHeight = Math.max(maxDrawHeight, h);
        }
    }

    public void paint(Graphics2D g2d) {
        if (image == null)
            return;

        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();
        int scaledWidth = Math.max(1, imgWidth / 10);
        int scaledHeight = Math.max(1, imgHeight / 10);

        var old = g2d.getTransform();
        g2d.translate(x, y);
        // rotate so that 0° points to the right; use negative heading so
        // decreasing heading (turning right) produces a clockwise rotation
        g2d.rotate(Math.toRadians(-headingDeg + 90));
        g2d.drawImage(image, -scaledWidth / 2, -scaledHeight / 2, scaledWidth, scaledHeight, null);
        g2d.setTransform(old);
    }

    public static double getMaxDrawWidth() {
        return maxDrawWidth;
    }

    public static double getMaxDrawHeight() {
        return maxDrawHeight;
    }
}
