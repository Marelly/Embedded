package main;

import pacman.Maze;
import java.awt.event.KeyEvent;

import game.KeyboardListener;
import main.MyContent;

public class MyKeyboardListener extends KeyboardListener{

	private MyContent myContent;
	
	public MyKeyboardListener() {
		super();
		myContent = (MyContent) this.content;
	}

	@Override
	public void directionalKeyPressed(Direction direction) {
		switch (direction) {
		  case RIGHT:
			  myContent.pacman().setDirectionPolicy(Maze.Direction.RIGHT);
			  break;
		  case LEFT:
			  myContent.pacman().setDirectionPolicy(Maze.Direction.LEFT);
			  break;
		  case UP:
			  myContent.pacman().setDirectionPolicy(Maze.Direction.UP);
			  break;
		  case DOWN:
			  myContent.pacman().setDirectionPolicy(Maze.Direction.DOWN);
			  break;
		}
	}
	
	@Override
	public void characterTyped(char c) {
		System.out.println("key character = '" + c + "'" + " pressed.");
	}
	
	@Override
	public void enterKeyPressed() {
		System.out.println("enter key pressed.");
	}
	
	@Override
	public void backSpaceKeyPressed() {
		System.out.println("backSpace key pressed.");
	}
	
	@Override
	public void spaceKeyPressed() {
		System.out.println("space key pressed.");
	}
	
	public void otherKeyPressed(KeyEvent e) {
		System.out.println("other key pressed. type:" + e);
	}
}
