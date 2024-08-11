package my_game;

public class MazeLine {
	public BoardPoint p1, p2;
	
	public MazeLine(BoardPoint p1, BoardPoint p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public MazeLine(int x1, int y1, int x2, int y2) {
		this.p1 = new BoardPoint(x1,y1);
		this.p2 = new BoardPoint(x2,y2);
	}

	public boolean blocksMove(BoardPoint p1, BoardPoint p2) {
		int minX = Math.min(this.p1.x, this.p2.x);
		int maxX = Math.max(this.p1.x, this.p2.x);
		int minY = Math.min(this.p1.y, this.p2.y);
		int maxY = Math.max(this.p1.y, this.p2.y);
		
		//both points are left to the line
		if (p1.x < minX && p2.x < minX)
			return false;
		//both points are right to the line
		if (p1.x > maxX && p2.x > maxX)
			return false;
		//both points are above the line
		if (p1.y < minY && p2.y < minY)
			return false;
		//both points are below the line
		if (p1.y > maxY && p2.y > maxY)
			return false;
		
		//If reached here, the points are within the range of line from both of its sides
		return true;
	}
	
	// Checks whether the point x,y is on the line
	// The check is simple because it assumes only horizontal and vertical lines.
	public boolean isOnLine(int x, int y) {

		int minX = Math.min(this.p1.x, this.p2.x);
		int maxX = Math.max(this.p1.x, this.p2.x);
		int minY = Math.min(this.p1.y, this.p2.y);
		int maxY = Math.max(this.p1.y, this.p2.y);

		return (x >= minX && x <= maxX && y >= minY && y <= maxY);
	}
}
