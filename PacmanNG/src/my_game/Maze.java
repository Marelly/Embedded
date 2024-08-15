package my_game;

import java.util.ArrayList;

public class Maze {
	
	public enum Direction{
		RIGHT (1,0),
		LEFT(-1,0),
		UP (0,-1),
		DOWN(0,1);
		
		private final int xVec, yVec;
		private Direction(int xVec, int yVec) {
			this.xVec = xVec;
			this.yVec = yVec;
		}
		public int xVec() {
			return xVec;
		}
		public int yVec() {
			return yVec;
		}
	}
	
	public static final int MAZE_X_SIZE = 21;
	public static final int MAZE_Y_SIZE = 14;	
	
	private Board board;
	private ArrayList<MazeLine> lines = new ArrayList<MazeLine>();
	private Lollipop[][] lollipops = new Lollipop[MAZE_X_SIZE][MAZE_Y_SIZE];
	private int numLollis = 0;
	
	public Maze(Board board) {
		this.board = board;
		initMazeLines();
		initLollipops();
	}

	private void initMazeLines() {
		// Frame
		lines.add(new MazeLine(0,0,MAZE_X_SIZE,0));
		lines.add(new MazeLine(0,0,0,MAZE_Y_SIZE));
		lines.add(new MazeLine(0,MAZE_Y_SIZE,MAZE_X_SIZE,MAZE_Y_SIZE));
		lines.add(new MazeLine(MAZE_X_SIZE,0,MAZE_X_SIZE,MAZE_Y_SIZE));
		
		// Inner lines
		
		//2 lines on 2nd row and 2 symmetric from bottom
		lines.add(new MazeLine(2,2,10,2));
		lines.add(new MazeLine(12,2,19,2));
		lines.add(new MazeLine(2,MAZE_Y_SIZE - 2,10, MAZE_Y_SIZE- 2));
		lines.add(new MazeLine(12,MAZE_Y_SIZE- 2,19,MAZE_Y_SIZE - 2));
		
		// 1 line on 4th row and one symmetric from bottom
		lines.add(new MazeLine(2,4,19,4));
		lines.add(new MazeLine(2,MAZE_Y_SIZE - 4,19, MAZE_Y_SIZE- 4));

		// 2 lines in the middle to create a 1 unit wide passage
		lines.add(new MazeLine(11,5,11,6));
		lines.add(new MazeLine(11,MAZE_Y_SIZE - 5,11, MAZE_Y_SIZE- 6));

		//2 lines on 6th row and 2 symmetric from bottom
		lines.add(new MazeLine(2,6,9,6));
		lines.add(new MazeLine(13,6,19,6));
		lines.add(new MazeLine(2,MAZE_Y_SIZE - 6,9, MAZE_Y_SIZE- 6));
		lines.add(new MazeLine(13,MAZE_Y_SIZE- 6,19,MAZE_Y_SIZE - 6));

	}
	
	public void initLollipops() {
		numLollis = 0;
		for (int y = 1; y < MAZE_Y_SIZE; y++) {
			for (int x = 1; x < MAZE_X_SIZE; x++) {
				if (!isOnMazeLine(x, y)) {
					lollipops[x][y] = new Lollipop(x,y);
					numLollis++;
				}
			}
		}
		
	}

	public void addMazeToBoard() {
		int i = 0;
		for (MazeLine line: lines()) {
			board.addLine(line, ++i);
		}
		for (int y = 1; y < MAZE_Y_SIZE; y++) {
			for (int x = 1; x < MAZE_X_SIZE; x++) {
				if (lollipops()[x][y] != null) {
					board.addLollipop(lollipops()[x][y]);
				}
			}
		}
	}

	public boolean blocksMove(BoardPoint p1, BoardPoint p2) {
		
		//Check if any of the lines blocks the move and if so, return true
		for (MazeLine line: lines) {
			if (line.blocksMove(p1, p2)) {
				return true;
			}
		}
		//If reached here, there is no blocking line
		return false;
	}

	public boolean isOnMazeLine(int x, int y) {
		
		//Check if the point is on any of the lines and if so, return true
		for (MazeLine line: lines) {
			if (line.isOnLine(x, y)) {
				return true;
			}
		}
		//If reached here, it is not on any line
		return false;
	}

	public int currentLollies( ) {
		return numLollis;
	}

	public void decreaseLollis() {
		numLollis--;
	}
	
	public ArrayList<MazeLine> lines() {
		return this.lines;
	}

	public Lollipop[][] lollipops() {
		return this.lollipops;
	}

}
