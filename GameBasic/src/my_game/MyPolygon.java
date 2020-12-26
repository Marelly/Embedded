package my_game;

import shapes.Polyline;

public class MyPolygon {
	
	private Polyline visPolygon;

	
	//TODO
	//Add your character properties

	public MyPolygon(Point[] points) {
		visPolygon = new Polyline("polygon", points);
	}

	public Polyline getVisualPolygon() {
		return visPolygon;
	}
	
}	

