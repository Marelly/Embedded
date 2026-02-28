package ai.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class NoFlyZoneRenderer {
    private double x, y, radius;

    public NoFlyZoneRenderer(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void paint(Graphics2D g2d) {
        g2d.setColor(new Color(255, 0, 0, 80));
        g2d.fillOval((int) (x - radius), (int) (y - radius), (int) (radius * 2), (int) (radius * 2));

        g2d.setColor(new Color(255, 0, 0, 200));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval((int) (x - radius), (int) (y - radius), (int) (radius * 2), (int) (radius * 2));
    }
}
