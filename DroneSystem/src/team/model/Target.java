package team.model;

import base.IdentifiedObject;

public class Target extends IdentifiedObject {
    private final Vec2 pos;
    private final double radius;
    private boolean reached;

    public Target(int id, Vec2 pos, double radius) {
        super(id);
        this.pos = pos;
        this.radius = radius;
        this.reached = false;
    }

    public Vec2 getPos() { return pos; }
    public double getRadius() { return radius; }
    public boolean isReached() { return reached; }
    public void setReached(boolean reached) { this.reached = reached; }
}