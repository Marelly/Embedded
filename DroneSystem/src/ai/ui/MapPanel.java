package ai.ui;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

import ai.ui.DroneRenderer;
import ai.ui.TargetRenderer;
import ai.ui.NoFlyZoneRenderer;
import ai.ui.ExplosionRenderer;
import ai.ui.JoystickRenderer;
import ai.ui.MapRenderer;

/**
 * MapPanel is a custom JPanel that renders the drone control map.
 * It displays the background, drones, targets, no-fly zones, and explosions.
 */
public class MapPanel extends JPanel {

    private final Map<Integer, DroneRenderer> drones = new HashMap<>();
    private final Map<Integer, TargetRenderer> targets = new HashMap<>();
    private final Map<Integer, NoFlyZoneRenderer> noFlyZones = new HashMap<>();
    private final java.util.List<ExplosionRenderer> explosions = new ArrayList<>();

    private final MapRenderer mapRenderer = new MapRenderer();
    private final JoystickRenderer joystickRenderer = new JoystickRenderer();

    private double telemetrySpeed = 0;
    private double telemetryBattery = 1.0;
    private int telemetryScore = 0;
    private String statusText = "Ready";

    public MapPanel() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1200, 800));
    }

    /**
     * Load and set the background image
     */
    public void setBackgroundImage(String filePath) {
        try {
            BufferedImage img = ImageIO.read(new File(filePath));
            mapRenderer.setBackgroundImage(img);
            repaint();
        } catch (Exception e) {
            System.err.println("Failed to load background image: " + filePath);
            e.printStackTrace();
        }
    }

    /**
     * Add or update a drone
     */
    public void setDrone(int id, double x, double y, double headingDeg, BufferedImage image) {
        drones.put(id, new DroneRenderer(x, y, headingDeg, image));
        repaint();
    }

    /**
     * Add or update a target
     */
    public void setTarget(int id, double x, double y, double radius, BufferedImage image, boolean reached) {
        targets.put(id, new TargetRenderer(x, y, radius, image, reached));
        repaint();
    }

    /**
     * Add or update a no-fly zone
     */
    public void setNoFlyZone(int id, double x, double y, double radius) {
        noFlyZones.put(id, new NoFlyZoneRenderer(x, y, radius));
        repaint();
    }

    /**
     * Add an explosion effect
     */
    public void addExplosion(double x, double y, BufferedImage image) {
        explosions.add(new ExplosionRenderer(x, y, image, System.currentTimeMillis()));
        repaint();
    }

    /**
     * Update telemetry data
     */
    public void setTelemetry(double speed, double battery01, int score) {
        this.telemetrySpeed = speed;
        this.telemetryBattery = battery01;
        this.telemetryScore = score;
        repaint();
    }

    /**
     * Update status text
     */
    public void setStatus(String text) {
        this.statusText = text;
        repaint();
    }

    /**
     * Update joystick visual state (-1..1 for both)
     */
    public void setJoystick(double throttle, double steer) {
        joystickRenderer.setState(throttle, steer);
        repaint();
    }

    /**
     * Clear all explosions that have expired
     */
    private void updateExplosions() {
        explosions.removeIf(ExplosionRenderer::isExpired);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Draw background
        mapRenderer.paint(g2d, width, height);

        // Draw no-fly zones
        for (NoFlyZoneRenderer nfz : noFlyZones.values()) {
            nfz.paint(g2d);
        }

        // Draw targets
        for (TargetRenderer target : targets.values()) {
            target.paint(g2d);
        }

        // Draw drones
        for (DroneRenderer drone : drones.values()) {
            drone.paint(g2d);
        }

        // Draw explosions
        updateExplosions();
        for (ExplosionRenderer explosion : explosions) {
            explosion.paint(g2d);
        }

        // Draw HUD (telemetry and status)
        drawHUD(g2d);

        // Draw joystick indicator
        joystickRenderer.paint(g2d, width, height);
    }

    private void drawHUD(Graphics2D g2d) {
        int width = getWidth();
        g2d.setColor(new Color(0, 0, 0, 200)); // Semi-transparent black
        g2d.fillRect(0, 0, width, 60);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));

        int yOffset = 20;
        g2d.drawString("Speed: " + String.format("%.2f", telemetrySpeed), 10, yOffset);
        g2d.drawString("Battery: " + String.format("%.0f%%", telemetryBattery * 100), 200, yOffset);
        g2d.drawString("Score: " + telemetryScore, 400, yOffset);
        g2d.drawString("Status: " + statusText, 550, yOffset);
    }

}
