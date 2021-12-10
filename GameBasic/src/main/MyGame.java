package main;

import java.awt.Color;

import game.Game;
import game.GameContent;
import gui.GameCanvas;
import gui.GameDashboard;
import buttons.AddButton;
import buttons.ChangeButton;
import buttons.CircleDragButton;
import buttons.EditPolygonButton;
import buttons.MusicButton;
import buttons.RotatePolygonButton;
import my_game.Pokimon;
import shapes.Circle;
import shapes.Image;

public class MyGame extends Game {
	
	private MyContent content;

	@Override
	protected void initCanvas() {
		
		GameCanvas canvas = gameUI.canvas();
		canvas.setBackground(Color.WHITE);
		canvas.setBackgroundImage("resources/background1.jpg");

		Pokimon pokimon = content.pokimon();
		pokimon.addToCanvas();
		canvas.addShape(content.polygon().getVisualPolygon());
		Circle c = new Circle("circle", 300, 300, 50);
		c.setDraggable(false);
		canvas.addShape(c);
	}
	
	@Override
	protected void initDashboard() {
		super.initDashboard();
		GameDashboard dashboard = gameUI.dashboard();
		
		dashboard.setBackground(Color.BLACK);
		
		// Add a the Polygon buttons
		dashboard.addButton(new EditPolygonButton("editButton", "Edit", 60, 40));
		dashboard.addButton(new RotatePolygonButton("rotateButton", "Rotate", 60, 100));

		// Add a the Circle drag button
		dashboard.addButton(new CircleDragButton("dragButton", "Drag", 280, 40));


		// Add a the AddButton button
		dashboard.addButton(new AddButton("addButton", "Add", 540, 40));
		
		//TODO
		// Add the ChangeButton button to the dashboard
		dashboard.addButton(new ChangeButton("changeButton", "Change", 540, 100));
		dashboard.addButton(new MusicButton("musicButton", "Play", 700, 40));

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
		game.setKeyboardListener(new MyKeyboardListener());
		game.initGame();
	}


}
