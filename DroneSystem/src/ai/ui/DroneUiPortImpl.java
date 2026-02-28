package ai.ui;

import javax.imageio.ImageIO;

import shared.ui_ports.DroneUiPort;
import shared.AssetRegistry;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * DroneUiPortImpl implements the UI communication port for the drone control
 * system.
 * It manages the MapPanel and coordinates graphical rendering based on backend
 * events.
 */
public class DroneUiPortImpl extends DroneUiPort {

    private MapPanel mapPanel;

    // Image cache to avoid reloading the same image multiple times
    private final Map<String, BufferedImage> imageCache = new HashMap<>();

    // Track target positions for the markTargetReached method
    private final Map<Integer, TargetData> targetData = new HashMap<>();

    private static class TargetData {
        double x, y, radius;
        boolean reached;

        TargetData(double x, double y, double radius, boolean reached) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.reached = reached;
        }
    }

    public DroneUiPortImpl() {
    }

    /**
     * Set the MapPanel that this implementation will control
     */
    public void setMapPanel(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
    }

    /**
     * Load an image from the asset registry and cache it
     */
    private BufferedImage loadImage(String key) {
        if (imageCache.containsKey(key)) {
            return imageCache.get(key);
        }

        try {
            String filePath = AssetRegistry.get(key);
            BufferedImage image = ImageIO.read(new File(filePath));
            imageCache.put(key, image);
            return image;
        } catch (Exception e) {
            System.err.println("Failed to load image for key: " + key);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void log(String message) {
        System.out.println("[DroneUI] " + message);
    }

    @Override
    public void setBackground(String key) {
        if (mapPanel == null) {
            System.err.println("MapPanel not initialized");
            return;
        }

        try {
            String filePath = AssetRegistry.get(key);
            mapPanel.setBackgroundImage(filePath);
            log("Background set to: " + key);
        } catch (Exception e) {
            System.err.println("Failed to set background: " + key);
            e.printStackTrace();
        }
    }

    @Override
    public void spawnDrone(int id, double x, double y, double headingDeg) {
        if (mapPanel == null)
            return;

        BufferedImage droneImage = loadImage(AssetRegistry.DRONE);
        mapPanel.setDrone(id, x, y, headingDeg, droneImage);
        log("Drone spawned: id=" + id + ", x=" + x + ", y=" + y + ", heading=" + headingDeg);
    }

    @Override
    public void updateDrone(int id, double x, double y, double headingDeg) {
        if (mapPanel == null)
            return;

        BufferedImage droneImage = loadImage(AssetRegistry.DRONE);
        mapPanel.setDrone(id, x, y, headingDeg, droneImage);
    }

    @Override
    public void spawnTarget(int id, double x, double y, double radius) {
        if (mapPanel == null)
            return;

        BufferedImage targetImage = loadImage(AssetRegistry.TARGET);
        mapPanel.setTarget(id, x, y, radius, targetImage, false);
        targetData.put(id, new TargetData(x, y, radius, false));
        log("Target spawned: id=" + id + ", x=" + x + ", y=" + y + ", radius=" + radius);
    }

    @Override
    public void markTargetReached(int id, boolean reached) {
        if (mapPanel == null)
            return;

        TargetData target = targetData.get(id);
        if (target != null) {
            target.reached = reached;
            String key = reached ? AssetRegistry.TARGET_REACHED : AssetRegistry.TARGET;
            BufferedImage targetImage = loadImage(key);
            mapPanel.setTarget(id, target.x, target.y, target.radius, targetImage, reached);
        }
        log("Target " + id + " marked as reached: " + reached);
    }

    @Override
    public void spawnNoFlyZone(int id, double x, double y, double radius) {
        if (mapPanel == null)
            return;

        mapPanel.setNoFlyZone(id, x, y, radius);
        log("No-fly zone spawned: id=" + id + ", x=" + x + ", y=" + y + ", radius=" + radius);
    }

    @Override
    public void setTelemetry(double speed, double battery01, int score) {
        if (mapPanel == null)
            return;

        mapPanel.setTelemetry(speed, battery01, score);
    }

    @Override
    public void setStatus(String text) {
        if (mapPanel == null)
            return;

        mapPanel.setStatus(text);
        log("Status: " + text);
    }

    @Override
    public void explode(double x, double y) {
        if (mapPanel == null)
            return;

        BufferedImage explosionImage = loadImage(AssetRegistry.EXPLOSION);
        mapPanel.addExplosion(x, y, explosionImage);
        log("Explosion at: x=" + x + ", y=" + y);
    }
}