package pacman;

import java.awt.Color;

import gui.GameCanvas;
import main.MyContent;
import shapes.Circle;
import shapes.Image;
import shapes.Rectangle;
import shapes.Text;

public class Board {
	
	private final int BOARD_X_OFFSET = 80;
	private final int BOARD_Y_OFFSET = 150;
	public static final int BOARD_SCALE = 40;
	private GameCanvas canvas;
	private MyContent content;
	
	public void initBoard(GameCanvas canvas, MyContent content) {
		this.canvas = canvas;
		this.content = content;
		Maze maze = content.maze();
		canvas.setBackground(Color.BLACK);
		canvas.addShape(new Image("title", "resources/PacmanTitle.png", 372, 123, 314, 10));
		addScore(content.score());
		int i = 0;
		for (MazeLine line: maze.lines()) {
			addLine(line, ++i);
		}
		for (int y = 1; y < Maze.MAZE_Y_SIZE; y++) {
			for (int x = 1; x < Maze.MAZE_X_SIZE; x++) {
				if (maze.lollipops()[x][y] != null) {
					addLollipop(maze.lollipops()[x][y]);
				}
			}
		}
		
		addPacman(content.pacman());
		addGhosts();
		addStatusLine();
	}

	private void addLine(MazeLine line, int lineIndex) {
		int minX = Math.min(line.p1.x, line.p2.x);
		int maxX = Math.max(line.p1.x, line.p2.x);
		int minY = Math.min(line.p1.y, line.p2.y);
		int maxY = Math.max(line.p1.y, line.p2.y);

		Rectangle rect = new Rectangle("ml"+lineIndex, transX(minX) - 2, transY(minY) - 2, BOARD_SCALE*(maxX-minX) + 4, BOARD_SCALE*(maxY-minY)+4);
		rect.setColor(Color.BLUE);
		rect.setWeight(2);
		canvas.addShape(rect);
	}
	
	private void addLollipop(Lollipop lp) {
		Circle circle = new Circle(lp.getGuid(), transX(lp.getLocation().x), transY(lp.getLocation().y), 3);
		circle.setColor(Color.WHITE);
		circle.setFillColor(Color.WHITE);
		circle.setIsFilled(true);
		canvas.addShape(circle);
	}
	
	private void addPacman(Pacman pacman) {
		Image image = new Image("pacman", "resources/pacman_right.png", 48,48, transX(pacman.getLocation().x)-24, transY(pacman.getLocation().y)-24);
		canvas.addShape(image);
		Text t2 = new Text("policy", pacman.getPolicy().toString() , BOARD_X_OFFSET,90);
		t2.setColor(Color.YELLOW);
		t2.setFontSize(40);
		canvas.addShape(t2);
	}
	
	private void addGhosts() {
		Image image;
		for (Ghost g: content.ghosts().getGhosts()) {
			image = new Image(g.name(), "resources/"+g.name()+".png", 48,48, transX(g.getLocation().x)-24, transY(g.getLocation().y)-24);
			canvas.addShape(image);
		}
	}

	private void addScore(Score score) {
		Text t2 = new Text(score.guid(), score.getText() , 730,90);
		t2.setColor(Color.YELLOW);
		t2.setFontSize(40);
		canvas.addShape(t2);
	}

	private void addStatusLine() {
		StatusLine status = content.statusLine();
		Text t2 = new Text(status.guid(), status.getText() , BOARD_X_OFFSET + 400, 760);
		t2.setColor(status.getColor());
		t2.setFontSize(30);
		canvas.addShape(t2);
	}

	
	public void updatePacman() {
		Pacman pacman = content.pacman();
		if (pacman.changedDirection()) {
			switch (pacman.getDirection()) {
			case RIGHT:
				canvas.changeImage("pacman", "resources/pacman_right.png", 48,48);
				break;
			case LEFT:
				canvas.changeImage("pacman", "resources/pacman_left.png", 48,48);
				break;
			case UP:
				canvas.changeImage("pacman", "resources/pacman_up.png", 48,48);
				break;
			case DOWN:
				canvas.changeImage("pacman", "resources/pacman_down.png", 48,48);
				break;
			}
		}
		
		canvas.moveToLocation("pacman", transX(content.pacman().getLocation().x)-24, transY(content.pacman().getLocation().y)-24);
		Text t1 = (Text) canvas.getShape("policy");
		t1.setText(pacman.getPolicy().toString());
	}
	
	public void updateGhosts() {
		for (Ghost g: content.ghosts().getGhosts()) {
			canvas.moveToLocation(g.name(), transX(g.getLocation().x)-24, transY(g.getLocation().y)-24);
		}
		
	}

	public void updateLolly(Lollipop lp) {
		canvas.deleteShape(lp.getGuid());
	}
	
	public void updateScore() {
		Text t1 = (Text) canvas.getShape(content.score().guid());
		t1.setText(content.score().getText());
	}

	public void updateStatusLine() {
		Text t1 = (Text) canvas.getShape(content.statusLine().guid());
		t1.setText(content.statusLine().getText());
		t1.setColor(content.statusLine().getColor());
	}

	//transform an X coordinate from the maze coordinates to the canvas coordinates
	private int transX(int x) {
		return BOARD_X_OFFSET + x*BOARD_SCALE;
	}
	//transform a Y coordinate from the maze coordinates to the canvas coordinates
	private int transY(int y) {
		return BOARD_Y_OFFSET + y*BOARD_SCALE;
	}

}
