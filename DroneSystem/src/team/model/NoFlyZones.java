package team.model;

import shared.ui_ports.DroneUiPort;
import java.util.ArrayList;
import java.util.List;

public class NoFlyZones {

    private final List<NoFlyZone> items = new ArrayList<>();

    public void add(NoFlyZone z) {
        items.add(z);
    }

    public List<NoFlyZone> all() {
        return items;
    }

    public void spawnAllToUi(DroneUiPort ui) {
        for (NoFlyZone z : items) {
            ui.spawnNoFlyZone(z.getId(), z.getPos().x, z.getPos().y, z.getRadius());
        }
    }

    public boolean isInsideAny(Vec2 pos) {
        for (NoFlyZone z : items) {
            double r = z.getRadius();
            if (pos.dist2(z.getPos()) <= r * r) return true;
        }
        return false;
    }

    // Optional: if you want to report which zone
    public NoFlyZone firstZoneContaining(Vec2 pos) {
        for (NoFlyZone z : items) {
            double r = z.getRadius();
            if (pos.dist2(z.getPos()) <= r * r) return z;
        }
        return null;
    }
}