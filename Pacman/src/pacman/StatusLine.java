package pacman;

import java.awt.Color;

import game.PeriodicLoop;

public class StatusLine {
	private String text;
	private long expirationTime;
	private Color color = Color.GREEN;
	
	public Color getColor() {
		return color;
	}
	
	public String guid() {
		return "status";
	}
	
	public String getText() {
		return text;		
	}
	
	public void showText(String text, Color color, long miliseconds) {
		this.text = text;
		this.color = color;
		expirationTime = PeriodicLoop.elapsedTime() + miliseconds;
	}
	
	public void refresh() {
		if (PeriodicLoop.elapsedTime() > expirationTime) {
			text = "";
		}
	}

}
