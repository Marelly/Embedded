package my_game;

import my_game.Maze.Direction;

public class Pacman {
	private BoardPoint location;
	/**
	 * DirectionPolicy is the direction in which the user wants to move.
	 * The user can click down-arrow even if there is a wall.
	 * In this case the pacman will move in the current direction but the policy will be
	 * rememberd, so that when there is an option to go down, the pacman will go down.
	 */
	private Direction directionPolicy = Direction.RIGHT;
	private Direction currentDirection = Direction.RIGHT;
	private Direction prevDirection = Direction.RIGHT;
	private Maze maze;
	
	public Pacman(Maze maze) {
		this.maze = maze;
		this.location = new BoardPoint(1,1);
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

	public Direction getDirectionPolicy() {
		return directionPolicy;
	}

	public void move() {
		
		prevDirection = currentDirection;
				
		// First try to move according to policy
		BoardPoint desired = new BoardPoint(location.x + directionPolicy.xVec(), location.y + directionPolicy.yVec());
		// if move is possible, i.e., maze does not block
		if (!maze.blocksMove(location, desired)) {
			currentDirection = directionPolicy;
			location.x = desired.x;
			location.y = desired.y;
			return;
		}
		// If reached here, desired policy is not applicable, move in opposite direction
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
			// recalculate next BoardPoint according to new direction
			next = new BoardPoint(location.x + currentDirection.xVec(), location.y + currentDirection.yVec());
		}
		// move to next BoardPoint
		location.x = next.x;
		location.y = next.y;
	}
	
	public boolean changedDirection() {
		return (currentDirection != prevDirection);
	}

}
