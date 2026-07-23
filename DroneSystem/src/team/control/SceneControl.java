package team.control;

import shared.ui_ports.DroneUiPort;
import team.model.Vec2;
import team.model.Wind;

public class SceneControl {

    private static final double WORLD_W = 900;
    private static final double WORLD_H = 600;

    private final Wind wind;

    public SceneControl(Wind wind) {
        this.wind = wind;
    }

    public void start(DroneUiPort ui) {
        ui.setBackground("map_desert");
    }

    public Vec2 applyWind(Vec2 velocity) {
        return velocity.add(wind.velocity());
    }

    public void clampToWorld(Vec2 position) {
        position.x = clamp(position.x, 0, WORLD_W);
        position.y = clamp(position.y, 0, WORLD_H);
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}