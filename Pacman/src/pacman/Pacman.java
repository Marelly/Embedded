package pacman;

import pacman.Maze.Direction;

public class Pacman {
	private Point location;
	private Direction directionPolicy = Direction.RIGHT;
	private Direction direction = Direction.RIGHT;
	private Direction prevDirection = Direction.RIGHT;
	private Maze maze;
	
	public Pacman(Maze maze) {
		this.maze = maze;
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

	public void move() {
		
		prevDirection = direction;
				
		// First try to move according to policy
		Point desired = new Point(location.x + directionPolicy.xVec(), location.y + directionPolicy.yVec());
		// if move is possible, i.e., maze does not block
		if (!maze.blocksMove(location, desired)) {
			direction = directionPolicy;
			location.x = desired.x;
			location.y = desired.y;
			return;
		}
		// If reached here, desired policy is not applicable, move in current direction
		Point next = new Point(location.x + direction.xVec(), location.y + direction.yVec());
		if (maze.blocksMove(location, next)) {
			switch (direction) {
			case RIGHT:
				direction = Direction.LEFT;
				break;
			case LEFT:
				direction = Direction.RIGHT;
				break;
			case UP:
				direction = Direction.DOWN;
				break;
			case DOWN:
				direction = Direction.UP;
				break;
			}
			// recalculate next point according to new direction
			next = new Point(location.x + direction.xVec(), location.y + direction.yVec());
		}
		// move to next point
		location.x = next.x;
		location.y = next.y;
	}
	
	public boolean changedDirection() {
		return (direction != prevDirection);
	}

}
