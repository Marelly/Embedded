package my_base;

import base.Game;
import base.GameContent;
import base.GameCanvas;
import base.GameDashboard;
import my_game.Maze.Direction;
import my_ui_elements.DirectionButton;
import my_ui_elements.PlayButton;
import my_ui_elements.RecordCB;
import java.awt.Color;


public class MyGame extends Game {
	
	private MyContent content;

	@Override
	protected void initCanvas() {
		/**
		 * DO NOT TOUCH FIRST 2 ROWS !!!
		 */
		GameCanvas canvas = gameUI.canvas();
		canvas.setMouseHandler(Game.MouseHandler());

		content.getBoard().setCanvas(canvas);
		content.getBoard().initBoard();
	}
	
	@Override
	protected void initDashboard() {
		super.initDashboard();
		GameDashboard dashboard = gameUI.dashboard();
		dashboard.setBackground(Color.BLACK);
				
		// Add direction policy buttons
		dashboard.addUIElement(new DirectionButton("btnUp", "UP", 460, 10, Direction.UP));
		dashboard.addUIElement(new DirectionButton("btnDown", "DOWN", 460, 100, Direction.DOWN));
		dashboard.addUIElement(new DirectionButton("btnRight", "RIGHT", 520, 55, Direction.RIGHT));
		dashboard.addUIElement(new DirectionButton("btnLeft", "LEFT", 400, 55, Direction.LEFT));
		// dashboard.addUIElement(new RecordCB("recordCB", "Recording", 200, 55, 200, 40, true));
		// dashboard.addUIElement(new PlayButton("btnPlay", "PLAY", 100, 40, 200, 110));
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
