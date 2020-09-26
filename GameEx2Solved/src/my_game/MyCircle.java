package my_game;

public class MyCircle {
    private MyPoint center;
    private int radius;

    public MyCircle() {
        this.center = new MyPoint(50,50);
        this.radius = 10;
    }
    public MyCircle(int x, int y, int radius) {
        this.center = new MyPoint(x,y);
        this.radius = radius;
    }
    public MyCircle(MyPoint center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * @return the center
     */
    public MyPoint getCenter() {
        return center;
    }

    /**
     * @return the radius
     */
    public int getRadius() {
        return radius;
    }

    /**
     * @param center the center to set
     */
    public void setCenter(MyPoint center) {
        this.center = center;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    public boolean isPointInside(MyPoint p) {
        return (center.distance(p) < radius);
    }

    public boolean intersects(MyCircle c) {
        return (center.distance(c.getCenter()) < radius + c.getRadius());
    }

    public String toString() {
        return "[" + center + ", " + radius +  " ]";
    }

}