package team.model;

import base.IdentifiedObject;

public class NoFlyZone extends IdentifiedObject {
    private final Vec2 pos;
    private final double radius;
    public static int nextId = 1;

    public NoFlyZone(int id, Vec2 pos, double radius) {
        super(id);
        nextId = id + 1; //storing nextId for dynamically added no-fly zones.
        this.pos = pos;
        this.radius = radius;
    }

    public Vec2 getPos() { return pos; }
    public double getRadius() { return radius; }
}