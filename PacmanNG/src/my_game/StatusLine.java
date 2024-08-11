package my_game;

import java.awt.Color;
import base.PeriodicLoop;

/**
 * This class demonstrates how you can show something for a specific time period.
 */
public class StatusLine {
	private String text;
	// expiration time will store the time when the text should stop showing.
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
		/**
		 * When we start showing the text, we set the expiration time to be 
		 * the current time + a delay period (miliseconds)
		 **/ 
		expirationTime = PeriodicLoop.elapsedTime() + miliseconds;
	}
	
	public void refresh() {
		/**
		 * Every cycle of the periodic loop we check if the current time is later
		 * than the expiration time. If it is, we stop showing the text.
		 **/ 

		if (PeriodicLoop.elapsedTime() > expirationTime) {
			text = "";
		}
	}

}
