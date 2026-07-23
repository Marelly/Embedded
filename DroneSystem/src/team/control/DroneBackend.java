package team.control;

import my_base.App;
import shared.ui_ports.DroneUiPort;
import team.model.Drone;

public class DroneBackend {

    private final SceneControl sceneControl;
    private final DroneControl droneControl;
    private final TargetsControl targetsControl;
    private final NoFlyZoneControl noFlyZoneControl;

    public DroneBackend() {
        sceneControl = new SceneControl(App.content().wind());
        droneControl = new DroneControl(App.content().drone(), sceneControl);
        targetsControl = new TargetsControl(App.content().targets(), App.content().score());
        noFlyZoneControl = new NoFlyZoneControl(App.content().noFlyZones());
    }

    private DroneUiPort ui() {
        return DroneUiPort.getInstance();
    }

    public void start() {
        DroneUiPort ui = ui();
        Drone drone = droneControl.drone();

        droneControl.start();
        targetsControl.reset(ui);
        sceneControl.start(ui);
        ui.spawnDrone(drone.getId(), drone.getPos().x, drone.getPos().y, drone.getHeadingDeg());
        targetsControl.start(ui);
        noFlyZoneControl.start(ui);

        ui.setStatus("Flying. Reach both targets. Avoid the red zone.");
        droneControl.publish(ui, targetsControl.score());
        ui.log("Drone scenario started.");
        ui.hideRestartButton();
    }

    public void reset() {
        DroneUiPort ui = ui();
        droneControl.reset();
        targetsControl.reset(ui);

        droneControl.publish(ui, targetsControl.score());
        ui.setStatus("Reset.");
        ui.hideRestartButton();
    }

    public void setThrottle(double value01) {
        droneControl.setThrottle(value01);
    }

    public void setSteer(double valueMinus1To1) {
        droneControl.setSteer(valueMinus1To1);
    }

    public void tick(double dtSec) {
        if (dtSec <= 0)
            return;

        DroneUiPort ui = ui();
        Drone drone = droneControl.drone();
        if (droneControl.handleStopped(ui, targetsControl.score()))
            return;

        if (!droneControl.tick(dtSec, ui))
            return;

        if (noFlyZoneControl.handleEntry(drone, ui)) {
            return;
        }

        int reachedNow = targetsControl.updateReached(drone.getPos(), ui);
        if (reachedNow > 0) {
            targetsControl.onTargetReached(reachedNow);
            ui.setStatus("Target reached! Score: " + targetsControl.score());
        }

        if (targetsControl.allReached()) {
            droneControl.complete(ui, targetsControl.score());
        }

        droneControl.publish(ui, targetsControl.score());
    }

    public void addTarget(double x, double y) {
        targetsControl.add(x, y, ui());
    }

    public void addNoFlyZone(double x, double y, double radius) {
        noFlyZoneControl.add(x, y, radius, ui());
    }

}