package team.control;

import shared.ui_ports.DroneUiPort;
import team.model.Drone;
import team.model.DroneState;
import team.model.Vec2;

public class DroneControl {

    private static final double MAX_SPEED = 220;
    private static final double MAX_TURN_DEG_PER_SEC = 120;
    private static final double ACCEL_PER_SEC = 220;

    private final Drone drone;
    private final SceneControl scene;
    private double throttle01;
    private double steerMinus1To1;

    public DroneControl(Drone drone, SceneControl scene) {
        this.drone = drone;
        this.scene = scene;
    }

    public void start() {
        drone.setState(DroneState.FLYING);
        drone.setHeadingDeg(0);
        drone.setSpeed(0);
        throttle01 = 0;
        steerMinus1To1 = 0;
    }

    public void reset() {
        drone.getPos().x = 120;
        drone.getPos().y = 520;
        drone.setHeadingDeg(0);
        drone.setSpeed(0);
        drone.setBattery01(1.0);
        drone.setState(DroneState.FLYING);
        throttle01 = 0;
        steerMinus1To1 = 0;
    }

    public void setThrottle(double value01) {
        throttle01 = clamp(value01, 0, 1);
    }

    public void setSteer(double valueMinus1To1) {
        steerMinus1To1 = clamp(valueMinus1To1, -1, 1);
    }

    public boolean isFlying() {
        return drone.getState() == DroneState.FLYING;
    }

    public boolean handleStopped(DroneUiPort ui, int score) {
        if (isFlying())
            return false;

        publish(ui, score);
        if (drone.getState() == DroneState.CRASHED || drone.getState() == DroneState.COMPLETED)
            ui.showRestartButton();
        return true;
    }

    public void complete(DroneUiPort ui, int score) {
        drone.setState(DroneState.COMPLETED);
        ui.setStatus("Mission completed! Final score: " + score);
        ui.showRestartButton();
    }

    public boolean tick(double dtSec, DroneUiPort ui) {
        double newBattery = drone.getBattery01() - dtSec * (0.012 + 0.02 * throttle01);
        drone.setBattery01(clamp(newBattery, 0, 1));
        if (drone.getBattery01() <= 0.0001) {
            drone.setState(DroneState.CRASHED);
            ui.setStatus("Battery empty. Forced landing.");
            ui.explode(drone.getPos().x, drone.getPos().y);
            ui.showRestartButton();
            return false;
        }

        drone.setHeadingDeg(normalizeDeg(drone.getHeadingDeg()
                + steerMinus1To1 * MAX_TURN_DEG_PER_SEC * dtSec));

        double targetSpeed = throttle01 * MAX_SPEED;
        double speed = drone.getSpeed();
        if (speed < targetSpeed)
            speed = Math.min(targetSpeed, speed + ACCEL_PER_SEC * dtSec);
        else
            speed = Math.max(targetSpeed, speed - ACCEL_PER_SEC * dtSec);
        drone.setSpeed(speed);

        double radians = Math.toRadians(drone.getHeadingDeg());
        Vec2 velocity = new Vec2(Math.cos(radians), -Math.sin(radians)).mul(drone.getSpeed());
        Vec2 totalVelocity = scene.applyWind(velocity);
        drone.getPos().x += totalVelocity.x * dtSec;
        drone.getPos().y += totalVelocity.y * dtSec;
        scene.clampToWorld(drone.getPos());
        return true;
    }

    public Drone drone() {
        return drone;
    }

    public void publish(DroneUiPort ui, int score) {
        ui.updateDrone(drone.getId(), drone.getPos().x, drone.getPos().y, drone.getHeadingDeg());
        ui.setTelemetry(drone.getSpeed(), drone.getBattery01(), score);
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private double normalizeDeg(double degrees) {
        double normalized = degrees % 360.0;
        if (normalized < 0)
            normalized += 360.0;
        return normalized;
    }
}