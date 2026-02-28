package ai.ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class MapRenderer {
    private BufferedImage backgroundImage;

    public void setBackgroundImage(BufferedImage img) {
        this.backgroundImage = img;
    }

    public void paint(Graphics2D g2d, int width, int height) {
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, width, height, null);
        }
    }
}