package pacman;

import pacman.Maze.Direction;

public class Ghost {
	private Point location;
	private String name;
	private Direction directionPolicy = Direction.DOWN;
	private Direction direction = Direction.RIGHT;
	private Maze maze;
	
	public Ghost(String name, Maze maze) {
		this.name = name;
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
	
	String name() {
		return name;
	}

	public void move() {
						
		// First try to move according to policy
		Point desired = new Point(location.x + directionPolicy.xVec(), location.y + directionPolicy.yVec());
		// if move is possible, i.e., maze does not block
		if (!maze.blocksMove(location, desired)) {
			direction = directionPolicy;
			location.x = desired.x;
			location.y = desired.y;
			updateDirectionPolicy();
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
			updateDirectionPolicy();
			// recalculate next point according to new direction
			next = new Point(location.x + direction.xVec(), location.y + direction.yVec());
		}
		// move to next point
		location.x = next.x;
		location.y = next.y;
	}
	
	private void updateDirectionPolicy() {
		switch (direction) {
		case RIGHT:
			directionPolicy = (Math.random() < 0.5 ? Direction.DOWN : Direction.UP);
			break;
		case LEFT:
			directionPolicy = (Math.random() < 0.5 ? Direction.DOWN : Direction.UP);
			break;
		case UP:
			directionPolicy = (Math.random() < 0.5 ? Direction.RIGHT : Direction.LEFT);
			break;
		case DOWN:
			directionPolicy = (Math.random() < 0.5 ? Direction.RIGHT : Direction.LEFT);
			break;
		}

	}


}
