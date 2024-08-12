package my_game;

public class Ghosts {

	Ghost[] ghosts = new Ghost[4];

	public void initGhosts(Maze maze) {
		
		Ghost g; 
		g = new Ghost("red", maze);
		g.setLocation(new BoardPoint(11,7));
		ghosts[0] = g;;

		g = new Ghost("blue", maze);
		g.setLocation(new BoardPoint(20,13));
		ghosts[1] = g;;

		g = new Ghost("orange", maze);
		g.setLocation(new BoardPoint(1,13));
		ghosts[2] = g;;

		g = new Ghost("pink", maze);
		g.setLocation(new BoardPoint(20,1));
		ghosts[3] = g;;
	}
	
	public void move() {
		for (Ghost g: ghosts) {
			g.move();
		}
	}
	
	public Ghost[] getGhosts() {
		return ghosts;
	}

	public Ghost redGhost() {
		return ghosts[0];
	}

	public Ghost blueGhost() {
		return ghosts[1];
	}

	public Ghost orangeGhost() {
		return ghosts[2];
	}

	public Ghost pinkGhost() {
		return ghosts[3];
	}
}
