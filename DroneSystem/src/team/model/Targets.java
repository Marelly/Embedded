package team.model;

import shared.ui_ports.DroneUiPort;
import java.util.ArrayList;
import java.util.List;

public class Targets {

    private final List<Target> items = new ArrayList<>();

    public void add(Target t) {
        items.add(t);
    }

    public List<Target> all() {
        return items;
    }

    public void spawnAllToUi(DroneUiPort ui) {
        for (Target t : items) {
            ui.spawnTarget(t.getId(), t.getPos().x, t.getPos().y, t.getRadius());
            ui.markTargetReached(t.getId(), t.isReached());
        }
    }

    public void resetReached(DroneUiPort ui) {
        for (Target t : items) {
            t.setReached(false);
            ui.markTargetReached(t.getId(), false);
        }
    }

    // Returns how many targets became reached in this call
    public int checkAndUpdateReached(Vec2 dronePos, DroneUiPort ui) {
        int newlyReached = 0;

        for (Target t : items) {
            if (!t.isReached()) {
                double r = t.getRadius();
                if (dronePos.dist2(t.getPos()) <= r * r) {
                    t.setReached(true);
                    newlyReached++;
                    ui.markTargetReached(t.getId(), true);
                    ui.log("Reached target " + t.getId());
                }
            }
        }

        return newlyReached;
    }

    public boolean allReached() {
        for (Target t : items) {
            if (!t.isReached()) return false;
        }
        return true;
    }
}