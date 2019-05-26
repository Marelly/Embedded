package main;

import java.awt.Color;

import game.PeriodicLoop;
import pacman.Board;
import pacman.Ghost;
import pacman.Ghosts;
import pacman.Lollipop;
import pacman.Pacman;
import pacman.Point;

public class MyPeriodicLoop extends PeriodicLoop {

	private MyContent content;
	private Board board;

	public void setContent(MyContent content) {
		this.content = content;
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	@Override
	public void execute() {
		// Let the super class do its work first
		super.execute();
		
		Lollipop lp = eatCurrentLolly(content.pacman().getLocation());
		content.pacman().move();
		board.updatePacman();
		if (lp != null) {
			board.updateLolly(lp);
		}
		content.ghosts().move();
		board.updateGhosts();
		checkCollisions();
		content.statusLine().refresh();
		board.updateScore();
		board.updateStatusLine();
		//Game.UI().canvas().moveShape("pacman", Board.BOARD_SCALE, 0);
		
	}

	private void checkCollisions() {
		Pacman pacman = content.pacman();
		Ghosts ghosts = content.ghosts();
		
		for (Ghost g: ghosts.getGhosts()) {
			if (g.getLocation().x == pacman.getLocation().x &&
					g.getLocation().y == pacman.getLocation().y) {
				content.score().reset();
				content.statusLine().showText("Oops ...", Color.RED, 2000);
				return;
			}
		}
	}

	public Lollipop eatCurrentLolly(Point location) {
		Lollipop lp = content.maze().lollipops()[location.x][location.y];
		if (lp.isShown()) {
			lp.hide();
			content.score().add(1);
			content.maze().decreaseLollis();
			if (content.maze().currentLollies() == 0) {
				content.statusLine().showText("Great JOB !!!", Color.YELLOW, 5000);
			}
			return lp; 
		}
		return null;
	}

}
