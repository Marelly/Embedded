package main;


import game.Game;
import game.PeriodicLoop;

public class MyPeriodicLoop extends PeriodicLoop {

	private MyContent content;

	public void setContent(MyContent content) {
		this.content = content;
	}
	
	@Override
	public void execute() {
		// Let the super class do its work first
		super.execute();
		
		content.pokimon().move();
		Game.UI().canvas().moveToLocation("pokimon", content.pokimon().getLocation().x, content.pokimon().getLocation().y);
		
	}


}
