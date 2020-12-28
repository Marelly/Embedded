package my_game;

import DB.ExcelTable;
import game.ShapeListener;
import game.Game;
import game.PeriodicLoop;
import shapes.Image;

public class Pokimon implements ShapeListener {
	
	public enum Direction{
		RIGHT (10,0),
		LEFT(-10,0),
		UP (0,-10),
		DOWN(0,10);
		
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

	private ExcelTable pokimonTable; 
	
	private Point location;
	private Direction directionPolicy = Direction.RIGHT;
	private Direction direction = Direction.RIGHT;
	
	private final String[] images = {"resources/Poki.jpg", "resources/Poki2.jpg"};
	private int imageIndex = 0;
	private final String imageID = "pokimon";
	private boolean isMoving = true;
	private int rotation = 0;	// In degrees
	
	public Pokimon() {
		
		pokimonTable = Game.excelDB().createTableFromExcel("pokimonMoves");
		pokimonTable.deleteAllRows();
	}	
	
	public Point getLocation() {
		return this.location;
	}
	
	public void setLocation(Point location) {
		this.location = location;
	}

	public void moveLocation(int dx, int dy) {
		this.location.x += dx;
		this.location.y += dy;
	}

	
	public void setDirectionPolicy(Direction direction) {
		directionPolicy = direction;
	}
	
	public Direction getDirection() {
		return direction;
	}

	public Direction getPolicy() {
		return directionPolicy;
	}
	
	public String getImageName() {
		return images[imageIndex];
	}
	
	public String getImageID() {
		return this.imageID;
	}
	
	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
		Image i = (Image) (Game.UI().canvas().getShape(imageID));
		i.setRotation(rotation);
	}

	public void switchImage() {
		setImage(1 - imageIndex);
	}

	public void setImage(int index) {
		this.imageIndex = index;
		if (imageIndex == 0) {
			Game.UI().canvas().changeImage(imageID, getImageName(), 220, 200);
		}
		else {
			Game.UI().canvas().changeImage(imageID, getImageName(), 260, 195);
		}
	}

	public void stopMoving() {
		isMoving = false;
	}

	public void resumeMoving() {
		isMoving = true;
	}
	
	public void move() {
		
		if (isMoving) {
			// Move according to policy
			Point desired = new Point(location.x + directionPolicy.xVec(), location.y + directionPolicy.yVec());
			// if move is possible, i.e., maze does not block
			direction = directionPolicy;
			location.x = desired.x;
			location.y = desired.y;
			try {
				pokimonTable.insertRow(new String[] {PeriodicLoop.elapsedTime() + "", location.x + "", location.y +"", direction.toString()});
				//Game.excelDB().commit();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error inserting new line to pokimon table");			
			}
		}
	}

	@Override
	public void shapeMoved(String shapeID, int dx, int dy) {
		moveLocation(dx, dy);
	}

	@Override
	public void shapeStartDrag(String shapeID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void shapeEndDrag(String shapeID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void shapeClicked(String shapeID, int x, int y) {
		stopMoving();

	}

	@Override
	public void shapeRightClicked(String shapeID, int x, int y) {
		resumeMoving();
	}

	@Override
	public void mouseEnterShape(String shapeID, int x, int y) {
		switchImage();

	}

	@Override
	public void mouseExitShape(String shapeID, int x, int y) {
		switchImage();

	}
	
}
