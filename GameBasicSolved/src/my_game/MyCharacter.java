package my_game;

import java.awt.Color;

public class MyCharacter {
	
	private Point location;
	private String imageID;
	
	//TODO
	//Add your character properties
	private int radius;
	private Color color;
	private boolean isFilled = false;
	
	public Point getLocation() {
		return this.location;
	}
	
	public void setLocation(Point location) {
		this.location = location;
	}
	
	public void setImageID(String id) {
		this.imageID = id;
	}
	
	public String getImageID() {
		return this.imageID;
	}
	
	//TODO
	//Add setters, getters and other methods that you need for your character
	
	public void setRadius(int r) {
		radius = r;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setIsFilled(boolean isFilled) {
		this.isFilled = isFilled;
	}
	
	public boolean getIsFilled() {
		return isFilled;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public  Color getColor() {
		return this.color;
	}
}
