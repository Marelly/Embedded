package ai.ui;

import java.awt.BasicStroke;
import java.awt.Color;
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

        // Draw four rotating rotors at the corners of the drone image.
        // Rotor animation uses system time so it continuously spins.
        long now = System.currentTimeMillis();
        double spinDeg = (now / 6.0) % 360.0; // speed of rotation

        double halfW = scaledWidth / 2.0;
        double halfH = scaledHeight / 2.0;
        // place rotors slightly inset from corners
        double cornerInsetX = halfW * 0.5;
        double cornerInsetY = halfH * 0.5;

        // rotor visual size (radius)
        double rotorRadius = Math.max(4, Math.min(scaledWidth, scaledHeight) * 0.12);

        // rotor offsets: top-left, top-right, bottom-left, bottom-right
        double[][] offsets = new double[][] {
                { -cornerInsetX, -cornerInsetY },
                { cornerInsetX, -cornerInsetY },
                { -cornerInsetX, cornerInsetY },
                { cornerInsetX, cornerInsetY }
        };

        // draw rotor discs and blades
        g2d.setStroke(new BasicStroke((float) Math.max(1f, rotorRadius * 0.12f)));
        for (int i = 0; i < offsets.length; i++) {
            double ox = offsets[i][0];
            double oy = offsets[i][1];

            // draw hub
            g2d.setColor(new Color(20, 20, 20, 200));
            int hubX = (int) Math.round(ox - rotorRadius / 2);
            int hubY = (int) Math.round(oy - rotorRadius / 2);
            int hubSize = (int) Math.round(rotorRadius);
            g2d.fillOval(hubX, hubY, hubSize, hubSize);

            // draw blades as two perpendicular lines rotated by spinDeg
            double angle = Math.toRadians(spinDeg + i * 45); // phase offset per rotor
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);

            int x1 = (int) Math.round(ox + cos * rotorRadius);
            int y1 = (int) Math.round(oy + sin * rotorRadius);
            int x2 = (int) Math.round(ox - cos * rotorRadius);
            int y2 = (int) Math.round(oy - sin * rotorRadius);

            g2d.setColor(new Color(60, 60, 60, 220));
            g2d.drawLine(x1, y1, x2, y2);

            // second blade perpendicular
            double angle2 = angle + Math.PI / 2.0;
            double cos2 = Math.cos(angle2);
            double sin2 = Math.sin(angle2);
            int x3 = (int) Math.round(ox + cos2 * rotorRadius);
            int y3 = (int) Math.round(oy + sin2 * rotorRadius);
            int x4 = (int) Math.round(ox - cos2 * rotorRadius);
            int y4 = (int) Math.round(oy - sin2 * rotorRadius);
            g2d.drawLine(x3, y3, x4, y4);
        }

        g2d.setTransform(old);
    }

    public static double getMaxDrawWidth() {
        return maxDrawWidth;
    }

    public static double getMaxDrawHeight() {
        return maxDrawHeight;
    }
}
