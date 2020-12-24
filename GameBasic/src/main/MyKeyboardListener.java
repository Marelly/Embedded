package main;

import my_game.Pokimon;
import java.awt.event.KeyEvent;

import game.KeyboardListener;

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
			  myContent.pokimon().setDirectionPolicy(Pokimon.Direction.RIGHT);
			  break;
		  case LEFT:
			  myContent.pokimon().setDirectionPolicy(Pokimon.Direction.LEFT);
			  break;
		  case UP:
			  //myContent.pokimon().setDirectionPolicy(Pokimon.Direction.UP);
			  myContent.pokimon().setRotation(myContent.pokimon().getRotation() + 20);
			  break;
		  case DOWN:
			  //myContent.pokimon().setDirectionPolicy(Pokimon.Direction.DOWN);
			  myContent.pokimon().setRotation(myContent.pokimon().getRotation() - 20);
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
