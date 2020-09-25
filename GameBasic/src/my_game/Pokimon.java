package my_game;

import DB.ExcelDB;
import DB.ExcelTable;
import game.Game;
import game.PeriodicLoop;

public class Pokimon {
	
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
	
	private final String imageName = "resources/Poki.jpg";
	private final String imageID = "pokimon";
	
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
		return this.imageName;
	}
	
	public String getImageID() {
		return this.imageID;
	}
	
	

	public void move() {
		
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
