package team.control;

import shared.ui_ports.DroneUiPort;
import team.model.*;
import my_base.App;   // update this import to your actual App package name

public class DroneBackend {

    // World bounds in "world units" that match your UI canvas
    private static final double WORLD_W = 900;
    private static final double WORLD_H = 600;

    // Control limits
    private static final double MAX_SPEED = 220;          // units/sec
    private static final double MAX_TURN_DEG_PER_SEC = 120;
    private static final double ACCEL_PER_SEC = 220;

    // Inputs
    private double throttle01 = 0.0;       // 0..1
    private double steerMinus1To1 = 0.0;   // -1..1

    // Environment
    private Wind wind = App.content().wind(); 

    // Score can live here (control concern)
    private Score score = App.content().score();

    private DroneUiPort ui() { return DroneUiPort.getInstance(); }

    // Convenient accessors into content
    private Drone drone() { return App.content().drone(); }
    private Targets targets() { return App.content().targets(); }
    private NoFlyZones zones() { return App.content().noFlyZones(); }

    public void start() {
        Drone d = drone();

        d.setState(DroneState.FLYING);
        d.setHeadingDeg(0);
        d.setSpeed(0);

        score.reset();
        throttle01 = 0;
        steerMinus1To1 = 0;

        ui().setBackground("map_desert");
        ui().spawnDrone(d.getId(), d.getPos().x, d.getPos().y, d.getHeadingDeg());

        targets().spawnAllToUi(ui());
        zones().spawnAllToUi(ui());

        ui().setStatus("Flying. Reach both targets. Avoid the red zone.");
        ui().setTelemetry(d.getSpeed(), d.getBattery01(), score.getValue());
        ui().log("Drone scenario started.");
    }

    public void reset() {
        Drone d = drone();

        d.getPos().x = 120;
        d.getPos().y = 520;
        d.setHeadingDeg(0);
        d.setSpeed(0);
        d.setBattery01(1.0);
        d.setState(DroneState.FLYING);

        score.reset();
        throttle01 = 0;
        steerMinus1To1 = 0;

        ui().updateDrone(d.getId(), d.getPos().x, d.getPos().y, d.getHeadingDeg());
        targets().resetReached(ui());

        ui().setStatus("Reset.");
        ui().setTelemetry(d.getSpeed(), d.getBattery01(), score.getValue());
    }

    public void setThrottle(double value01) {
        throttle01 = clamp(value01, 0, 1);
    }

    public void setSteer(double valueMinus1To1) {
        steerMinus1To1 = clamp(valueMinus1To1, -1, 1);
    }

    public void tick(double dtSec) {
        if (dtSec <= 0) return;

        Drone d = drone();

        if (d.getState() != DroneState.FLYING) {
            ui().updateDrone(d.getId(), d.getPos().x, d.getPos().y, d.getHeadingDeg());
            ui().setTelemetry(d.getSpeed(), d.getBattery01(), score.getValue());
            return;
        }

        // Battery drain
        double newBat = d.getBattery01() - dtSec * (0.012 + 0.02 * throttle01);
        d.setBattery01(clamp(newBat, 0, 1));
        if (d.getBattery01() <= 0.0001) {
            d.setState(DroneState.CRASHED);
            ui().setStatus("Battery empty. Forced landing.");
            ui().explode(d.getPos().x, d.getPos().y);
            return;
        }

        // Heading update (steer)
        d.setHeadingDeg(normalizeDeg(d.getHeadingDeg() + steerMinus1To1 * MAX_TURN_DEG_PER_SEC * dtSec));
        System.out.println("Heading updated to " + d.getHeadingDeg() + " degrees.");

        // Speed update toward throttle target
        double targetSpeed = throttle01 * MAX_SPEED;
        double s = d.getSpeed();
        if (s < targetSpeed) s = Math.min(targetSpeed, s + ACCEL_PER_SEC * dtSec);
        else s = Math.max(targetSpeed, s - ACCEL_PER_SEC * dtSec);
        d.setSpeed(s);

        // Velocity from heading + wind
        double rad = Math.toRadians(d.getHeadingDeg());
        Vec2 v = new Vec2(Math.cos(rad), -Math.sin(rad)).mul(d.getSpeed()); // y inverted for screen coords
        Vec2 totalV = v.add(wind.velocity());

        // Position update
        d.getPos().x += totalV.x * dtSec;
        d.getPos().y += totalV.y * dtSec;

        // Clamp to world
        d.getPos().x = clamp(d.getPos().x, 0, WORLD_W);
        d.getPos().y = clamp(d.getPos().y, 0, WORLD_H);

        // Crash if inside no-fly zone
        if (zones().isInsideAny(d.getPos())) {
            d.setState(DroneState.CRASHED);
            ui().setStatus("CRASH: entered no-fly zone.");
            ui().explode(d.getPos().x, d.getPos().y);
            return;
        }

        // Check targets
        int reachedNow = targets().checkAndUpdateReached(d.getPos(), ui());
        if (reachedNow > 0) {
            score.onTargetReached(reachedNow);
            ui().setStatus("Target reached! Score: " + score.getValue());
        }

        // Completed mission
        if (targets().allReached()) {
            d.setState(DroneState.COMPLETED);
            ui().setStatus("Mission completed! Final score: " + score.getValue());
        }

        // Push state to UI
        ui().updateDrone(d.getId(), d.getPos().x, d.getPos().y, d.getHeadingDeg());
        ui().setTelemetry(d.getSpeed(), d.getBattery01(), score.getValue());
    }

    private double clamp(double v, double lo, double hi) {
        return Math.max(lo, Math.min(hi, v));
    }

    private double normalizeDeg(double deg) {
        double d = deg % 360.0;
        if (d < 0) d += 360.0;
        return d;
    }
}