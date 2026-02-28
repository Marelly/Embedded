package my_base;

import team.control.DroneBackend;
import team.model.*;

public class AppContent {

    private Drone drone;
    private Targets targets;
    private NoFlyZones noFlyZones;
	// Instantiate wind and score here so they are available during content init.
	private Wind wind = new Wind(new Vec2(0, 0)); // no wind
	private Score score = new Score();

    // Control layer (uses the model above)
    private DroneBackend droneBackend;

    public void initContent() {

        // 1) Model objects
        drone = new Drone(1, new Vec2(120, 520));

        targets = new Targets();
        targets.add(new Target(1, new Vec2(700, 140), 28));
        targets.add(new Target(2, new Vec2(820, 420), 28));

        noFlyZones = new NoFlyZones();
        noFlyZones.add(new NoFlyZone(1, new Vec2(560, 320), 80));

        // 2) Control layer
        droneBackend = new DroneBackend();
    }

    public Drone drone() { return drone; }

    public Targets targets() { return targets; }

    public NoFlyZones noFlyZones() { return noFlyZones; }

    public DroneBackend droneBackend() { return droneBackend; }

	public Wind wind() { return wind; }

	public Score score() { return score; }
}