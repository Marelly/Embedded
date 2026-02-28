package ai.ui;

import java.awt.Color;
import java.awt.Graphics2D;

public class JoystickRenderer {
    private double throttle;
    private double steer;

    public void setState(double throttle, double steer) {
        this.throttle = throttle;
        this.steer = steer;
    }

    public void paint(Graphics2D g2d, int panelWidth, int panelHeight) {
        int size = 100;
        int padding = 20;
        int x0 = panelWidth - size - padding;
        int y0 = panelHeight - size - padding;

        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(x0, y0, size, size);
        g2d.setColor(Color.WHITE);
        g2d.drawRect(x0, y0, size, size);

        int cx = x0 + size / 2;
        int cy = y0 + size / 2;

        int dx = (int) (steer * (size / 2 - 10));
        int dy = (int) (-throttle * (size / 2 - 10));
        int r = 10;
        g2d.setColor(Color.GREEN);
        g2d.fillOval(cx + dx - r, cy + dy - r, r * 2, r * 2);
    }
}