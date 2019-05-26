package pacman;

import java.util.ArrayList;

public class Ghosts {

	private ArrayList<Ghost> ghosts;

	public void initGhosts(Maze maze) {
		ghosts = new ArrayList<Ghost>();
		
		Ghost g; 
		g = new Ghost("green", maze);
		g.setLocation(new Point(20,1));
		ghosts.add(g);

		g = new Ghost("orange", maze);
		g.setLocation(new Point(1,13));
		ghosts.add(g);

		g = new Ghost("blue", maze);
		g.setLocation(new Point(20,13));
		ghosts.add(g);

		g = new Ghost("red", maze);
		g.setLocation(new Point(11,7));
		ghosts.add(g);
	}
	
	public void move() {
		for (Ghost g: ghosts) {
			g.move();
		}
	}
	
	public ArrayList<Ghost> getGhosts() {
		return ghosts;
	}

}
