package my_base;

import java.awt.Color;

import base.GameContent;
import my_game.Maze;
import my_game.Pacman;
import my_game.Board;
import my_game.GameControl;
import my_game.Ghosts;
import my_game.HistoryIndication;
import my_game.HistoryPlayer;
import my_game.HistoryRecorder;
import my_game.Score;
import my_game.StatusLine;

public class MyContent extends GameContent{
	private Maze maze;
	private Pacman pacman;
	private Ghosts ghosts;
	private Score score;
	private StatusLine statusLine;
	private Board board;
	private GameControl gameControl;
	private HistoryRecorder historyRecorder;
	private HistoryPlayer historyPlayer;
	private HistoryIndication historyIndication;

	@Override
	public void initContent() {
		board = new Board();
		board.setContent(this);
		maze = new Maze(board);
		pacman = new Pacman(maze);
		ghosts = new Ghosts();
		ghosts.initGhosts(maze);
		score = new Score();
		statusLine = new StatusLine();
		statusLine.showText("Good Luck!", Color.GREEN, 3000);
		historyRecorder = new HistoryRecorder(this);
		historyPlayer = new HistoryPlayer(this);
		historyIndication = new HistoryIndication(this);
		gameControl = new GameControl(this);
	}	
	
	public Maze maze() {
		return maze;
	}

	public Pacman pacman() {
		return pacman;
	}
	
	public Ghosts ghosts() {
		return ghosts;
	}
	
	public Score score() {
		return score;
	}

	public StatusLine statusLine() {
		return statusLine;
	}

	public Board getBoard() {
		return this.board;
	}

	public GameControl gameControl() {
		return this.gameControl;
	}

	public HistoryRecorder historyRecorder() {
		return this.historyRecorder;
	}

	public HistoryPlayer historyPlayer() {
		return this.historyPlayer;
	}

	public HistoryIndication historyIndication() {
		return this.historyIndication;
	}
}
