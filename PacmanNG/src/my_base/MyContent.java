package my_base;

import java.awt.Color;

import base.GameContent;
import my_game.Maze;
import my_game.Pacman;
import my_game.Board;
import my_game.Ghosts;
import my_game.Score;
import my_game.StatusLine;

public class MyContent extends GameContent{
	private Maze maze;
	private Pacman pacman;
	private Ghosts ghosts;
	private Score score;
	private StatusLine statusLine;
	private Board board;

	@Override
	public void initContent() {
		System.out.println("Start init content");
		board = new Board();
		System.out.println("Board created");
		board.setContent(this);
		maze = new Maze();
		pacman = new Pacman(maze);
		ghosts = new Ghosts();
		ghosts.initGhosts(maze);
		score = new Score();
		statusLine = new StatusLine();
		statusLine.showText("Good Luck!", Color.GREEN, 3000);
		System.out.println("End init content");

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

}
