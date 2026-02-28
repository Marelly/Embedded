package shared.ui_ports;

public abstract class DroneUiPort {

    private static DroneUiPort instance;

    public static void setInstance(DroneUiPort ui) {
        if (ui == null) throw new IllegalArgumentException("DroneUiPort instance cannot be null");
        if (instance != null) throw new IllegalStateException("DroneUiPort instance already set");
        instance = ui;
    }

    public static DroneUiPort getInstance() {
        if (instance == null) throw new IllegalStateException("DroneUiPort instance not set yet");
        return instance;
    }

    // Rendering and UI feedback
    public abstract void setBackground(String key);

    public abstract void spawnDrone(int id, double x, double y, double headingDeg);
    public abstract void updateDrone(int id, double x, double y, double headingDeg);

    public abstract void spawnTarget(int id, double x, double y, double radius);
    public abstract void markTargetReached(int id, boolean reached);

    public abstract void spawnNoFlyZone(int id, double x, double y, double radius);

    public abstract void setTelemetry(double speed, double battery01, int score);
    public abstract void setStatus(String text);

    public abstract void explode(double x, double y);
    public abstract void log(String message);

    // ask UI to display a restart button which will call reset route
    public abstract void showRestartButton();
    public abstract void hideRestartButton();
}