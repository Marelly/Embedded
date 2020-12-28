package shapes;


import java.awt.Graphics2D;
import java.awt.Polygon;

import game.Game;
import my_game.Point;

public class Polyline extends FilledShape{
	private Polygon polygon;
	private Point[] points;

	public Polyline(String id, Point[] points) {
		super(id);
		this.points = new Point[points.length];
		for (int i = 0; i < points.length; i++) {
			this.points[i] = new Point(points[i].x, points[i].y);
		}
		updatePolygon();
	}

	public Point[] getPoints() {
		return this.points;
	}

	public void updatePolygon() {
		polygon = new Polygon();
		for (int i = 0; i < points.length; i++) {
			polygon.addPoint(points[i].x, points[i].y);
		}
		if (Game.UI() != null) { // Check that UI exists to support cases where a polygon is created before the canvas
			Game.UI().canvas().repaint();
		}
	}

	public void addPoint(Point point, int atIndex) {
		Point[] tempPoints = points;
		// increase number of points by 1
		points = new Point[tempPoints.length + 1];

		// copy all points before index
		for (int i = 0; i < atIndex; i++) {
			points[i] = tempPoints[i];
		}
		// place new point at index
		points[atIndex] = point;
		// copy all points after index
		for (int i = atIndex; i < tempPoints.length; i++) {
			points[i+1] = tempPoints[i];
		}
		updatePolygon();
	}

	public void deletePoint(int atIndex) {
		Point[] tempPoints = points;
		// increase number of points by 1
		points = new Point[tempPoints.length - 1];

		// copy all points before index
		for (int i = 0; i < atIndex; i++) {
			points[i] = tempPoints[i];
		}
		// copy all points after index
		for (int i = atIndex+1; i < tempPoints.length; i++) {
			points[i-1] = tempPoints[i];
		}
		updatePolygon();
	}


	public void setPoint (int index, Point point) {
		points[index].x = point.x;
		points[index].y = point.y;
		updatePolygon();
	}

	public void rotate(int degAngle) {
		double radAngle = Math.toRadians(degAngle);
		Point center = new Point(0,0); // Find the center of the polygon to rotate around
		for (int i = 0; i < points.length; i++) {
			center.x += points[i].x;
			center.y += points[i].y;
		}
		center.x = center.x / points.length;
		center.y = center.y / points.length;
		for (int i=0; i <points.length; i++) {
			points[i] = rotatePoint(points[i], center, radAngle);
		}
		updatePolygon();
	}
	
	// Rotate a point around a given center with a specified angle
	private Point rotatePoint(Point point, Point center, double radAngle) {

		double rotatedX = center.x + (point.x - center.x) * Math.cos(radAngle) - (point.y - center.y) * Math.sin(radAngle);
		double rotatedY = center.y + (point.x - center.x) * Math.sin(radAngle) + (point.y - center.y) * Math.cos(radAngle);
		return new Point((int) rotatedX, (int) rotatedY);
	}

	@Override
	public void draw(Graphics2D g) {
		if (isFilled()) {
			g.setColor(getFillColor());
			g.fillPolygon(polygon);
		}

		super.draw(g);
        g.drawPolygon(polygon);	
	}
	
	@Override
	public boolean isInArea(int x, int y) {
		return (polygon.contains(x, y));
	}
	
	@Override
	public void move(int dx, int dy) {
		for (int i = 0; i < points.length; i++) {
			points[i].x += dx;
			points[i].y += dy;
		}
		updatePolygon();
	}
	
	@Override
	public void moveToLocation(int x, int y) {
		// Moving a polygon to a new location means moving its first point there.
		int dx = x - points[0].x;
		int dy = y - points[0].y;
		move(dx, dy);
	}	

}