package team.ex3;

import java.util.HashMap;
import java.util.Map;

import shared.ui_ports.Ex3UiPort;

public class Ex3Backend {

    private final Map<Integer, Circle> circles = new HashMap<>();
    private final Map<Integer, Point> points = new HashMap<>();

    /**
     * Use ex3UiPort() as a function and not a variableto get the UI port
     * to avoid trying to get it before it was set up by the UI 
     * (which happens at UI startup, but this backend is constructed at app startup).
     */
    private Ex3UiPort ex3UiPort() { return Ex3UiPort.getInstance(); }

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
        ex3UiPort().addPoint(p1.getId(), p1.getX(), p1.getY());
        ex3UiPort().addPoint(p2.getId(), p2.getX(), p2.getY());

        ex3UiPort().addCircle(c1.getId(), c1.getCenter().getX(), c1.getCenter().getY(), c1.getR());
        ex3UiPort().addCircle(c2.getId(), c2.getCenter().getX(), c2.getCenter().getY(), c2.getR());
        ex3UiPort().addCircle(c3.getId(), c3.getCenter().getX(), c3.getCenter().getY(), c3.getR());

        ex3UiPort().log("Scenario started: 3 circles + 2 points.");
        evaluateAndCommandUi();
    }

    // UI input events call these via router
    public void movePoint(int pointId, double x, double y) {
        Point p = requirePoint(pointId);
        p.setX(x);
        p.setY(y);
        ex3UiPort().updatePoint(pointId, x, y);
        evaluateAndCommandUi();
    }

    public void moveCircle(int circleId, double cx, double cy) {
        Circle c = requireCircle(circleId);
        c.getCenter().setX(cx);
        c.getCenter().setY(cy);
        ex3UiPort().updateCircle(circleId, cx, cy, c.getR());
        evaluateAndCommandUi();
    }

    public void setCircleRadius(int circleId, double r) {
        Circle c = requireCircle(circleId);
        c.setR(r);
        ex3UiPort().updateCircle(circleId, c.getCenter().getX(), c.getCenter().getY(), c.getR());
        evaluateAndCommandUi();
    }

    // Business logic: checks + UI commands
    private void evaluateAndCommandUi() {
        Circle c1 = requireCircle(1);
        Circle c2 = requireCircle(2);
        Circle c3 = requireCircle(3);

        Point p1 = requirePoint(1);
        Point p2 = requirePoint(2);

        boolean c1InC2 = c1.intersects(c2); // expected true initially
        boolean c1InC3 = c1.intersects(c3); // expected false initially

        boolean p1Inside = c1.contains(p1); // expected true initially
        boolean p2Inside = c1.contains(p2); // expected false initially

        // Student → UI commands (IDs only)
        ex3UiPort().paintPoint(1, p1Inside ? "red" : "black");
        ex3UiPort().paintPoint(2, p2Inside ? "red" : "black");

        if (c1InC2) ex3UiPort().blinkCircle(2, 2);
        if (c1InC3) ex3UiPort().blinkCircle(3, 2);
        //ex3UiPort().log("Checks: c1∩c2=" + c1InC2 + " c1∩c3=" + c1InC3 + " p1∈c1=" + p1Inside + " p2∈c1=" + p2Inside);
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