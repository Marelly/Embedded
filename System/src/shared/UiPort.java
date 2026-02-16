package shared;

public interface UiPort {
    // World objects (rendering)
    void addPoint(int pointId, double x, double y);
    void updatePoint(int pointId, double x, double y);

    void addCircle(int circleId, double cx, double cy, double r);
    void updateCircle(int circleId, double cx, double cy, double r);

    // Visual effects
    void paintPoint(int pointId, String colorName);    // "red", "black", ...
    void blinkCircle(int circleId, int times);         // UI decides how to blink
    void log(String message);
}