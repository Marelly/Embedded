package ai.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TargetRenderer {
    private double x, y, radius;
    private BufferedImage image;
    private boolean reached;

    public TargetRenderer(double x, double y, double radius, BufferedImage image, boolean reached) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.image = image;
        this.reached = reached;
    }

    public void setReached(boolean reached) {
        this.reached = reached;
    }

    public boolean isReached() {
        return reached;
    }

    public void paint(Graphics2D g2d) {
        if (image == null)
            return;

        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();
        int scaledWidth = Math.max(1, imgWidth / 10);
        int scaledHeight = Math.max(1, imgHeight / 10);

        if (reached) {
            g2d.setColor(new Color(0, 255, 0, 100));
            g2d.fillOval((int) (x - radius), (int) (y - radius), (int) (radius * 2), (int) (radius * 2));
        }

        g2d.drawImage(image, (int) (x - scaledWidth / 2), (int) (y - scaledHeight / 2), scaledWidth, scaledHeight,
                null);
    }
}
