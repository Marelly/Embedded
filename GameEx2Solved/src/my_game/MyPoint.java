package my_game;

public class MyPoint {
    private int x, y;

    public MyPoint() {
        this.x = 0;
        this.y = 0;
    }
    public MyPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    public double distance(MyPoint p2) {
        return Math.sqrt(Math.pow(p2.getY() - y,2) + Math.pow(p2.getX() - x,2));
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

}