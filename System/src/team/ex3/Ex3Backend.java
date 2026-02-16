package team.ex3;

import shared.UiPort;
import java.util.HashMap;
import java.util.Map;

public class Ex3Backend {

    private final UiPort ui ;

    private final Map<Integer, Circle> circles = new HashMap<>();
    private final Map<Integer, Point> points = new HashMap<>();


    // Called once at UI startup
    public void startScenario() {
        // Points (IDs 1..2)
        Point p1 = new Point(1, 110, 110); // inside circle1 (expected true)
        Point p2 = new Point(2, 200, 200); // outside circle1 (expected false)
        points.put(1, p1);
        points.put(2, p2);

        // Circle centers (internal objects, but still ID'd)
        Point c1Center = new Point(101, 100, 100);
        Point c2Center = new Point(102, 140, 100);
        Point c3Center = new Point(103, 250, 100);

        // Circles (IDs 1..3)
        Circle c1 = new Circle(1, c1Center, 50);
        Circle c2 = new Circle(2, c2Center, 30);
        Circle c3 = new Circle(3, c3Center, 20);

        circles.put(1, c1);
        circles.put(2, c2);
        circles.put(3, c3);

        // Tell UI to render objects (IDs only)
        ui.addPoint(p1.getId(), p1.getX(), p1.getY());
        ui.addPoint(p2.getId(), p2.getX(), p2.getY());

        ui.addCircle(c1.getId(), c1.getCenter().getX(), c1.getCenter().getY(), c1.getR());
        ui.addCircle(c2.getId(), c2.getCenter().getX(), c2.getCenter().getY(), c2.getR());
        ui.addCircle(c3.getId(), c3.getCenter().getX(), c3.getCenter().getY(), c3.getR());

        ui.log("Scenario started: 3 circles + 2 points.");
        evaluateAndCommandUi();
    }

    // UI input events call these via router
    public void movePoint(int pointId, double x, double y) {
        Point p = requirePoint(pointId);
        p.setX(x);
        p.setY(y);
        ui.updatePoint(pointId, x, y);
        evaluateAndCommandUi();
    }

    public void moveCircle(int circleId, double cx, double cy) {
        Circle c = requireCircle(circleId);
        c.getCenter().setX(cx);
        c.getCenter().setY(cy);
        ui.updateCircle(circleId, cx, cy, c.getR());
        evaluateAndCommandUi();
    }

    public void setCircleRadius(int circleId, double r) {
        Circle c = requireCircle(circleId);
        c.setR(r);
        ui.updateCircle(circleId, c.getCenter().getX(), c.getCenter().getY(), c.getR());
        evaluateAndCommandUi();
    }

    // Business logic: checks + UI commands
    private void evaluateAndCommandUi() {
        Circle c1 = requireCircle(1);
        Circle c2 = requireCircle(2);
        Circle c3 = requireCircle(3);

        Point p1 = requirePoint(1);
        Point p2 = requirePoint(2);

        boolean i12 = c1.intersects(c2); // expected true initially
        boolean i13 = c1.intersects(c3); // expected false initially

        boolean p1Inside = c1.contains(p1); // expected true initially
        boolean p2Inside = c1.contains(p2); // expected false initially

        // Student → UI commands (IDs only)
        ui.paintPoint(1, p1Inside ? "red" : "black");
        ui.paintPoint(2, p2Inside ? "red" : "black");

        if (i12) ui.blinkCircle(2, 2);
        if (i13) ui.blinkCircle(3, 2);

        ui.log("Checks: c1∩c2=" + i12 + " c1∩c3=" + i13 + " p1∈c1=" + p1Inside + " p2∈c1=" + p2Inside);
    }

    private Circle requireCircle(int id) {
        Circle c = circles.get(id);
        if (c == null) throw new RuntimeException("Missing circle id=" + id);
        return c;
    }

    private Point requirePoint(int id) {
        Point p = points.get(id);
        if (p == null) throw new RuntimeException("Missing point id=" + id);
        return p;
    }
}