package main;

import java.awt.Color;

import examples.ExampleButton;
import game.Game;
import game.GameContent;
import gui.GameCanvas;
import gui.GameDashboard;
import my_game.DirectionButton;
import my_game.Pokimon.Direction;
import shapes.Image;

public class MyGame extends Game {
	
	private MyContent content;

	@Override
	protected void initCanvas() {
		
		GameCanvas canvas = gameUI.canvas();
		canvas.setBackground(Color.WHITE);
		Image image = new Image("pokimon", "resources/Poki.jpg", 220,200, 100, 100);
		canvas.addShape(image);

	}
	
	@Override
	protected void initDashboard() {
		super.initDashboard();
		GameDashboard dashboard = gameUI.dashboard();
		
		dashboard.setBackground(Color.BLACK);
		// Add a specific button with desired behavior
		dashboard.addButton(new ExampleButton("e1", "DB Example", 150, 40, 10, 55));
		
		// Add direction policy buttons
		dashboard.addButton(new DirectionButton("btnUp", "UP", 460, 10, Direction.UP));
		dashboard.addButton(new DirectionButton("btnDown", "DOWN", 460, 100, Direction.DOWN));
		dashboard.addButton(new DirectionButton("btnRight", "RIGHT", 520, 55, Direction.RIGHT));
		dashboard.addButton(new DirectionButton("btnLeft", "LEFT", 400, 55, Direction.LEFT));
	}
	
	@Override
	public void setGameContent(GameContent content) {
		// Call the Game superclass to set its content 
		super.setGameContent(content);
		// point to the content with a variable of type MyContent so we have access to all
		// our game specific data
		this.content = (MyContent) content;
	}
	
	public MyContent getContent() {
		return this.content;
	}
	
	public static void main(String[] args) {
		MyGame game = new MyGame();
		game.setGameContent(new MyContent());
		MyPeriodicLoop periodicLoop = new MyPeriodicLoop();
		periodicLoop.setContent(game.getContent());
		game.setPeriodicLoop(periodicLoop);
		game.setMouseHandler(new MyMouseHandler());
		game.initGame();
	}


}
