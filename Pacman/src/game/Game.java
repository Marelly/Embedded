package game;

import db.DBHandler;
import gui.GameUI;

public abstract class Game {
	protected static GameContent gameContent;
	protected static GameUI gameUI;
	private static DBHandler gameDB;
	private static MouseHandler mouseHandler;
	private static KeyboardListener keyboardListener;
	private static PeriodicScheduler scheduler;
	
	public Game() {
		initDB();
		scheduler = new PeriodicScheduler();
	}

	/*
	 * Make  sure to set the game content and periodic loop before calling initGame, since it is probable
	 * that you will need the content to initialize the UI and the scheduler should know what loop
	 * to run.
	 */
	public void setGameContent(GameContent content) {
		gameContent = content;
	}
	
	public void setPeriodicLoop(PeriodicLoop periodicLoop) {
		scheduler.setPeriodicLoop(periodicLoop);
	}
	
	public void setMouseHandler(MouseHandler myMouseHandler) {
		mouseHandler = myMouseHandler;
	}
	
	public void setKeyboardListener(KeyboardListener myKeyboardListener) {
		keyboardListener = myKeyboardListener;
	}
	
	public void initGame() {
		initContent();
		initUI();
		if (keyboardListener != null)
			gameUI.frame().addKeyListener(keyboardListener.keyListener);
		scheduler.start();
	}
	
	public void initUI() {
		gameUI = new GameUI("My Game", 1000, 1000);
		initCanvas();
		initDashboard();
		gameUI.setFocusable(true);
		gameUI.setVisible(true);
	}
	
	public void initDB() {
		gameDB = new DBHandler();
		gameDB.connect();
	}
	
	public static void endGame() {
		scheduler.end();
		gameDB.close();
	}
	
	protected abstract void initCanvas();
	
	protected void initContent() {
		gameContent.initContent();
	};
	
	protected void initDashboard() {
		// Add end button to terminate game
		gameUI.dashboard().addButton(new EndButton("btnEND", "END", 100, 60, 850, 50));
	}
	
	//You can refer to the game UI from anywhere by Game.UI()
	public static GameUI UI() {
		return gameUI;
	}

	//You can refer to the game database from anywhere by Game.DB()
	public static DBHandler DB() {
		return gameDB;
	}
	
	public static MouseHandler MouseHandler() {
		return mouseHandler;
	}
	
	public static KeyboardListener keybListener() {
		return keyboardListener;
	}
	
	public static GameContent Content() {
		return gameContent;
	}


}
