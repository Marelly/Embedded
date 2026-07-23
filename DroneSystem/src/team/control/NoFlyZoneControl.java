package team.control;

import shared.ui_ports.DroneUiPort;
import team.model.Drone;
import team.model.NoFlyZone;
import team.model.NoFlyZones;
import team.model.Vec2;

public class NoFlyZoneControl {

    private final NoFlyZones zones;

    public NoFlyZoneControl(NoFlyZones zones) {
        this.zones = zones;
    }

    public void start(DroneUiPort ui) {
        zones.spawnAllToUi(ui);
    }

    public boolean contains(Vec2 dronePosition) {
        return zones.isInsideAny(dronePosition);
    }

    public boolean handleEntry(Drone drone, DroneUiPort ui) {
        if (!contains(drone.getPos()))
            return false;

        drone.setState(team.model.DroneState.CRASHED);
        ui.setStatus("CRASH: entered no-fly zone.");
        ui.explode(drone.getPos().x, drone.getPos().y);
        ui.showRestartButton();
        return true;
    }

    public void add(double x, double y, double radius, DroneUiPort ui) {
        NoFlyZone zone = new NoFlyZone(NoFlyZone.nextId, new Vec2(x, y), radius);
        zones.add(zone);
        ui.spawnNoFlyZone(zone.getId(), zone.getPos().x, zone.getPos().y, zone.getRadius());
    }
}