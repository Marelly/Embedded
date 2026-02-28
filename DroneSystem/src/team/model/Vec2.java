package team.model;

public class Vec2 {
    public double x;
    public double y;

    public Vec2(double x, double y) { this.x = x; this.y = y; }

    public Vec2 add(Vec2 o) { return new Vec2(this.x + o.x, this.y + o.y); }
    public Vec2 mul(double k) { return new Vec2(this.x * k, this.y * k); }

    public double dist2(Vec2 o) {
        double dx = this.x - o.x;
        double dy = this.y - o.y;
        return dx*dx + dy*dy;
    }
}