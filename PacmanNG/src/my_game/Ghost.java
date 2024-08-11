package my_game;

import my_game.Maze.Direction;

public class Ghost {
	private BoardPoint location;
	private String name;
	private Direction directionPolicy = Direction.DOWN;
	private Direction currentDirection = Direction.RIGHT;
	private Maze maze;
	
	public Ghost(String name, Maze maze) {
		this.name = name;
		this.maze = maze;
	}
	public BoardPoint getLocation() {
		return this.location;
	}
	
	public void setLocation(BoardPoint location) {
		this.location = location;
	}
	
	
	public void setDirectionPolicy(Direction direction) {
		directionPolicy = direction;
	}
	
	public Direction getCurrentDirection() {
		return currentDirection;
	}

	public Direction getPolicy() {
		return directionPolicy;
	}
	
	String name() {
		return name;
	}

	public void move() {
						
		// First try to move according to policy
		BoardPoint desired = new BoardPoint(location.x + directionPolicy.xVec(), location.y + directionPolicy.yVec());
		// if move is possible, i.e., maze does not block
		if (!maze.blocksMove(location, desired)) {
			currentDirection = directionPolicy;
			location.x = desired.x;
			location.y = desired.y;
			// After moving to next location, update movement direction randomly for next movement
			updateDirectionPolicy();
			return;
		}
		// If reached here, desired policy is not applicable, move in current direction
		BoardPoint next = new BoardPoint(location.x + currentDirection.xVec(), location.y + currentDirection.yVec());
		if (maze.blocksMove(location, next)) {
			switch (currentDirection) {
				case RIGHT:
					currentDirection = Direction.LEFT;
					break;
				case LEFT:
					currentDirection = Direction.RIGHT;
					break;
				case UP:
					currentDirection = Direction.DOWN;
					break;
				case DOWN:
					currentDirection = Direction.UP;
					break;
			}
			updateDirectionPolicy();
			// recalculate next point according to new direction
			next = new BoardPoint(location.x + currentDirection.xVec(), location.y + currentDirection.yVec());
		}
		// move to next point
		location.x = next.x;
		location.y = next.y;
	}
	
	private void updateDirectionPolicy() {
		switch (currentDirection) {
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
