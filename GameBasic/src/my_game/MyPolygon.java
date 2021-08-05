package my_game;

import java.awt.Color;

import game.ShapeListener;
import game.Game;
import shapes.Circle;
import shapes.Polyline;

public class MyPolygon implements ShapeListener {
	
	private Polyline visPolygon;
	private Circle[] points;
	private Circle[] midPoints;
	private boolean isEditMode = false;
	private int movingPointIndex;

	
	//TODO
	//Add your character properties

	public MyPolygon(Point[] points) {
		visPolygon = new Polyline("Polygon", points);
		visPolygon.setIsFilled(true);
		visPolygon.setFillColor(Color.GRAY);
		visPolygon.setWeight(3);
		visPolygon.setShapeListener(this);
	}

	public Polyline getVisualPolygon() {
		return visPolygon;
	}

	public boolean isEditMode() {
		return isEditMode;
	}

	public void enterEditMode() {
		Point[] polyPoints = visPolygon.getPoints();
		points = new Circle[polyPoints.length];
		for (int i = 0; i < polyPoints.length; i++) {
			points[i] = new Circle("p"+i, polyPoints[i].x, polyPoints[i].y, 6);
			points[i].setFillColor(Color.BLUE);
			points[i].setIsFilled(true);
			points[i].setzOrder(3);
			points[i].setShapeListener(this);
			Game.UI().canvas().addShape(points[i]);
		}
		midPoints = new Circle[polyPoints.length];
		int x, y;
		for (int i = 0; i < polyPoints.length; i++) {
			x = (polyPoints[i].x + polyPoints[(i+1) % polyPoints.length].x) / 2;
			y = (polyPoints[i].y + polyPoints[(i+1) % polyPoints.length].y) / 2;
			midPoints[i] = new Circle("m"+i, x, y, 4);
			midPoints[i].setFillColor(Color.GREEN);
			midPoints[i].setIsFilled(true);
			midPoints[i].setzOrder(3);
			midPoints[i].setShapeListener(this);
			Game.UI().canvas().addShape(midPoints[i]);
		}

		isEditMode = true;
	}

	public void exitEditMode() {
		if (!isEditMode) {
			return;
		}
		for (int i = 0; i < points.length; i++) {
			Game.UI().canvas().deleteShape("p"+i);
			Game.UI().canvas().deleteShape("m"+i);
		}
		isEditMode = false;
	}

	@Override
	public void shapeMoved(String shapeID, int dx, int dy) {
		if (shapeID.equals("Polygon")) {
			return;
		}
		Point p = visPolygon.getPoints()[movingPointIndex];
		visPolygon.setPoint(movingPointIndex, new Point(p.x + dx, p.y + dy));
	}

	@Override
	public void shapeStartDrag(String shapeID) {
		if (shapeID.equals("Polygon")) {
			exitEditMode();
			visPolygon.setIsFilled(false);
			return;
		} 
		int index = Integer.valueOf(shapeID.substring(1)); // remove the first letter and get the index
		// if mid point, create a new point
		if (shapeID.substring(0, 1).equals("m")) {
			movingPointIndex = (index+1) % midPoints.length;
			visPolygon.addPoint(new Point(midPoints[index].getPosX(), midPoints[index].getPosY()), movingPointIndex);
		} else {
			movingPointIndex = index;
		}
	}

	@Override
	public void shapeEndDrag(String shapeID) {
		if (shapeID.equals("Polygon")) {
			visPolygon.setIsFilled(true);;
		} else {
			refreshMarkers();
		}
	}

	private void refreshMarkers() {
		exitEditMode();
		enterEditMode();
	}

	@Override
	public void shapeClicked(String shapeID, int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void shapeRightClicked(String shapeID, int x, int y) {
		// if end point, delete the point
		if (shapeID.substring(0, 1).equals("p")) {
			int index = Integer.valueOf(shapeID.substring(1)); // remove the first letter and get the index
			visPolygon.deletePoint(index);
			refreshMarkers();
		}
	}

	@Override
	public void mouseEnterShape(String shapeID, int x, int y) {
		if (shapeID.equals("Polygon")) {
			visPolygon.setFillColor(Color.ORANGE);
			Game.UI().canvas().repaint();
		}

	}

	@Override
	public void mouseExitShape(String shapeID, int x, int y) {
		if (shapeID.equals("Polygon")) {
			visPolygon.setFillColor(Color.GRAY);
			Game.UI().canvas().repaint();
		}

	}
}	

