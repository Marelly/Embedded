package team.model;

public class Wind {
    private final Vec2 v;

    public Wind(Vec2 v) { this.v = v; }

    public Vec2 velocity() {
        return v;   // constant everywhere
    }
}