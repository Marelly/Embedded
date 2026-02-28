package team.model;

import base.IdentifiedObject;

public class NoFlyZone extends IdentifiedObject {
    private final Vec2 pos;
    private final double radius;

    public NoFlyZone(int id, Vec2 pos, double radius) {
        super(id);
        this.pos = pos;
        this.radius = radius;
    }

    public Vec2 getPos() { return pos; }
    public double getRadius() { return radius; }
}