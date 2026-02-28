package ai.ui;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ExplosionRenderer {
    private double x, y;
    private BufferedImage image;
    private long startTime;

    public ExplosionRenderer(double x, double y, BufferedImage image, long startTime) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.startTime = startTime;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - startTime > 2000;
    }

    public void paint(Graphics2D g2d) {
        if (image == null)
            return;

        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();

        double targetW = DroneRenderer.getMaxDrawWidth() > 0 ? DroneRenderer.getMaxDrawWidth() * 2 : imgWidth;
        double targetH = DroneRenderer.getMaxDrawHeight() > 0 ? DroneRenderer.getMaxDrawHeight() * 2 : imgHeight;
        double scale = Math.min(targetW / imgWidth, targetH / imgHeight);
        int drawW = (int) (imgWidth * scale);
        int drawH = (int) (imgHeight * scale);

        long age = System.currentTimeMillis() - startTime;
        float alpha = Math.max(0, 1.0f - (age / 2000.0f));

        var old = g2d.getComposite();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawImage(image, (int) (x - drawW / 2), (int) (y - drawH / 2), drawW, drawH, null);
        g2d.setComposite(old);
    }
}