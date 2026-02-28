package team.model;

import base.IdentifiedObject;

public class Drone extends IdentifiedObject{
    private final Vec2 pos;
    private double headingDeg;   // 0 = right, 90 = up
    private double speed;        // units/sec
    private double battery01;    // 0..1
    private DroneState state;

    public Drone(int id, Vec2 startPos) {
        super(id);
        this.pos = startPos;
        this.headingDeg = 0.0;
        this.speed = 0.0;
        this.battery01 = 1.0;
        this.state = DroneState.READY;
    }

    public Vec2 getPos() { return pos; }
    public double getHeadingDeg() { return headingDeg; }
    public double getSpeed() { return speed; }
    public double getBattery01() { return battery01; }
    public DroneState getState() { return state; }

    public void setHeadingDeg(double headingDeg) { this.headingDeg = headingDeg; }
    public void setSpeed(double speed) { this.speed = speed; }
    public void setBattery01(double battery01) { this.battery01 = battery01; }
    public void setState(DroneState state) { this.state = state; }
}