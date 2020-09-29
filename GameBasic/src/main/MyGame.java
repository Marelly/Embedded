package main;

import java.awt.Color;

import DB.ExcelDB;
import game.Game;
import game.GameContent;
import gui.GameCanvas;
import gui.GameDashboard;
import my_game.Pokimon.Direction;
import my_game.AddButton;
import my_game.ChangeButton;
import my_game.Pokimon;
import shapes.Image;
import shapes.Rectangle;

public class MyGame extends Game {
	
	private MyContent content;

	@Override
	protected void initCanvas() {
		
		GameCanvas canvas = gameUI.canvas();
		canvas.setBackground(Color.WHITE);
		
		Rectangle rect = new Rectangle("rect", 100, 100, 500, 500);
		rect.setIsFilled(true);
		rect.setzOrder(1);
		canvas.addShape(rect);
		Rectangle rect2 = new Rectangle("rect2", 150, 150, 300, 300);
		rect2.setColor(Color.BLUE);
		rect2.setIsFilled(true);
		rect2.setzOrder(2);
		canvas.addShape(rect);
		canvas.addShape(rect2);

		Pokimon pokimon = content.pokimon();
		Image image = new Image(pokimon.getImageID(), pokimon.getImageName(), 220,200, 100, 100);
		image.setzOrder(3);
		canvas.addShape(image);
	
	}
	
	@Override
	protected void initDashboard() {
		super.initDashboard();
		GameDashboard dashboard = gameUI.dashboard();
		
		dashboard.setBackground(Color.BLACK);
		
		// Add a the AddButton button
		dashboard.addButton(new AddButton("addButton", "Add", 200, 40));
		
		//TODO
		// Add the ChangeButton button to the dashboard
		dashboard.addButton(new ChangeButton("changeButton", "Change", 400, 40));

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
