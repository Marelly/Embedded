package main;

import game.GameContent;
import my_game.Pokimon;
import my_game.Point;

public class MyContent extends GameContent{
	private Pokimon pokimon;

	@Override
	public void initContent() {
		pokimon = new Pokimon();
		pokimon.setLocation(new Point(100,100));
	}	
	
	public Pokimon pokimon() {
		return pokimon;
	}
	
}
