package main;



import game.Game;
import game.GameContent;
import gui.GameCanvas;
import my_game.Pokimon;
import my_game.MyCharacter;
import my_game.Point;

public class MyContent extends GameContent{
	private Pokimon pokimon;
	
	//TODO
	//Declare your own character
	

	@Override
	public void initContent() {
		pokimon = new Pokimon();
		pokimon.setLocation(new Point(100,100));		
	}	
	
	public Pokimon pokimon() {
		return pokimon;
	}
	
	public void addCharacter() {
		//TODO
		//Create an instance of your character and set its properties with
		//initial values
		//Make sure you set values to the location and imageID properties
		
		GameCanvas canvas = Game.UI().canvas();
		//TODO
		//Add your character shape or image to the canvas using its addShape method
		//Use the properties of your character for the parameters of the shape.

	}
	
	//TODO
	//create a method with the name myCharacter which returns
	//your character for others to use.

	
	//TODO
	//create a changeCharacter method and change inside all the properties you like.
}
