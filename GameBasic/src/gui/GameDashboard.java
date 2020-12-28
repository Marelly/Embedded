package gui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import buttons.GameButton;
import shapes.Shape.STATUS;
/**
 * A 2D screen that displays graphical entities and enables to set their location at runtime, causing an animation effect.
 * 
 * <h1>Properties:</h1>
 * <ul>
 * <li>int width - the screen width, in pixels<br>
 * <li>int height - the screen hight, in pixels<br>
 * <li>String backgroundColor - the screen background color, from a list of given colors [red, black, yellow, gray, white]<br>
 * <li>borderColor - the color of the screen border, from a list of given colors [red, black, yellow, gray, white]<br>
 * <li>borderWidth - the width of the screen border, in pixels (0 if no border)<br>
 * <li>int positionX, positionY - the screen position, in pixels<br>
 * </ul>
 * 
 * <h1>Methods:</h1>
 * <ul>
 * 	<li>void addButton(id, name, width, height, posX, posY) - adds a Button, identified by id, to the screen, where
 * 		name is the text written on the button.<br>
 * 	<li>void delObject(id) - permanently removes a Button identified by id from the screen.<br>
 * 	<li>void flipStatus(id) - changes the status of a Button and shows or hide it accordingly.<br>
 *  <il>void show(id) - shows a Button identified by id.<br>
 *  <il>void hide(id) - hides a Button identified by id.<br>
 *  <il>void showAll() shows all buttons.<br>
 *  <il>void hideAll() - hides all buttons.<br>
 *  <il>void deleteAll() - deletes all buttons from the screen.<br>
 * </ul>
 * 
 */
public class GameDashboard extends JPanel  {
	
	private static final long serialVersionUID = 1L;
	
	private final Map<String, GameButton> buttons;

	String borderColor;
	int borderWidth;
	
	int width = 100;	
	int height = 100;
	
	int positionX;
	int positionY;
		
	public GameDashboard() {
		super();
		this.buttons = new HashMap<>();
		this.setLayout(null);
	}
	/*
	 * Add a basic button according to a set of parameters
	 */
	public void addButton(String id, String name, int width, int height,
			int posX, int posY) {
		GameButton button = new GameButton(id, name, width, height, posX, posY);
		buttons.put(id, button);
		this.add(button.getJButton());
		this.updateUI();
	}

	/*
	 * Add a specific button that is derived from GameButton and is created before.
	 */
	public void addButton(GameButton gameButton) {
		buttons.put(gameButton.getId(), gameButton);
		this.add(gameButton.getJButton());
		this.updateUI();
	}
	
	public GameButton getButton(String id) {
		return buttons.get(id);
	}
	
	public void delButton(String id) {
		GameButton button = buttons.get(id);
		if (button != null) {
			this.remove(button.getJButton());
			buttons.remove(id);
		}
		this.updateUI();
	}

	public void hideAll() {
		for (GameButton button : buttons.values()) {
			button.setStatus(STATUS.HIDE);
			this.remove(button.getJButton());
		}
		this.updateUI();
	}

	public void showAll() {
		for (GameButton button : buttons.values()) {
			button.setStatus(STATUS.SHOW);
			this.add(button.getJButton());
		}
		this.updateUI();
	}

	public void deleteAll() {
		for (String id : buttons.keySet()) {
			delButton(id);
		}
		this.updateUI();
	}

	public void flipStatus(String id) {
		GameButton button = buttons.get(id);
		if (button != null) {
			if (button.getStatus().equals(STATUS.HIDE)) {
				button.setStatus(STATUS.SHOW);
				this.add(button.getJButton());
			} else if (button.getStatus().equals(STATUS.SHOW)) {
				button.setStatus(STATUS.HIDE);
				this.remove(button.getJButton());
			}
		}
		this.updateUI();
	}

	public void show(String id) {
		GameButton button = buttons.get(id);
		if (button != null) {
			button.setStatus(STATUS.SHOW);
				this.add(button.getJButton());
		}
		this.updateUI();
	}
	
	public void hide(String id) {
		GameButton button = buttons.get(id);
		if (button != null) {
			button.setStatus(STATUS.HIDE);
				this.remove(button.getJButton());
		}
		this.updateUI();
	}


	public static void main(String[] args) {
		//Create a frame window and set its name, size and behavior when clicking the X
		JFrame frame = new JFrame("My Screen");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		
		//Create a dashboard
		GameDashboard dashboard = new GameDashboard();
		
		//Add a button to the dashboard
		dashboard.addButton("b1", "button1", 100, 60, 10, 10);
		
		//Set the dashboard background color to red
		dashboard.setBackground(Color.RED);
		
		//Add the dashboard to the frame window
		frame.getContentPane().add(dashboard);
		frame.setVisible(true);
		
		//Flip the visibility status of the button 10 times.
		for (int i = 0; i< 10; i++) {
			dashboard.flipStatus("b1");
			Sleeper.sleep(200);
		}
	}

}
