package my_game;

import java.awt.Color;

import my_base.MyContent;

public class GameControl {
    private MyContent content;
	private Board board;

    public GameControl(MyContent content) {
        this.content = content;
        this.board = content.getBoard();
    }

	public void gameStep() {
		
		Lollipop lp = eatCurrentLolly(content.pacman().getLocation());
		// If history is played, restore locations from history.
		// Otherwise, move pacman and ghosts according to
		if (content.historyPlayer().isPlaying()) {
			content.historyPlayer().playState();
		} else {
			content.pacman().move();
			content.ghosts().move();
		}
		board.updatePacman();
		board.updateGhosts();
		if (lp != null) {
			board.updateLolly(lp);
		}
		handleCollisions();
		board.updateScore();
		content.statusLine().refresh();
		board.updateStatusLine();
		content.historyRecorder().recordState();
        checkGameOver();
		
	}

	private void handleCollisions() {
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

	public Lollipop eatCurrentLolly(BoardPoint location) {
		Lollipop lp = content.maze().lollipops()[location.x][location.y];
		if (lp.isShown()) {
			lp.hide();
			content.score().add(1);
			content.maze().decreaseLollis();
			// if (content.maze().currentLollies() == 0) {
			// 	content.statusLine().showText("Great JOB !!!", Color.YELLOW, 5000);
			// }
			return lp; 
		}
		return null;
	}


    public void checkGameOver() {
        if (content.maze().currentLollies() == 0) {
            content.statusLine().showText("Great JOB !!!", Color.YELLOW, 5000);
        }
    }

}
