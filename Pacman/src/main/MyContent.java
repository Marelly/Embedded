package main;

import java.awt.Color;

import game.GameContent;
import pacman.Ghosts;
import pacman.Maze;
import pacman.Pacman;
import pacman.Point;
import pacman.Score;
import pacman.StatusLine;

public class MyContent extends GameContent{
	private Maze maze;
	private Pacman pacman;
	private Ghosts ghosts;
	private Score score;
	private StatusLine statusLine;

	@Override
	public void initContent() {
		maze = new Maze();
		pacman = new Pacman(maze);
		pacman.setLocation(new Point(1,1));
		ghosts = new Ghosts();
		ghosts.initGhosts(maze);
		score = new Score();
		statusLine = new StatusLine();
		statusLine.showText("Good Luck!", Color.GREEN, 3000);
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

}
