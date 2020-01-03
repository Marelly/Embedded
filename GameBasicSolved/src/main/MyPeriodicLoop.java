package main;


import java.awt.Color;

import game.Game;
import game.PeriodicLoop;
import gui.GameCanvas;
import my_game.MyCharacter;
import shapes.Circle;

public class MyPeriodicLoop extends PeriodicLoop {

	private MyContent content;
	private boolean flag = true;

	public void setContent(MyContent content) {
		this.content = content;
	}
	
	@Override
	public void execute() {
		// Let the super class do its work first
		super.execute();
		redrawPokimon();
		
		//TODO
		//Redraw your character periodically by calling the correct method
		redrawCharacter();
		
	}
	
	private void redrawPokimon() {
		if (flag) {
			Game.UI().canvas().changeImage("pokimon", "resources/Poki2.jpg", 260, 190);
		}
		else {
			Game.UI().canvas().changeImage("pokimon", "resources/Poki.jpg", 220, 200);
		}
		flag = !flag;
		content.pokimon().move();
		Game.UI().canvas().moveToLocation("pokimon", content.pokimon().getLocation().x, content.pokimon().getLocation().y);
	}

	private void redrawCharacter() {
		
		GameCanvas canvas = Game.UI().canvas();
		MyCharacter character = content.myCharacter();
		//Since this function is called every interval, it will also be called
		//before the character is created. Therefore, we check if the character 
		//exists and if not, we return without doing anything.
		if (character == null)
			return;
		
		//TODO
		//Call the canvas to change the shape properties according to
		//its current property values
		//You can get the shape using canvas.getShape(id) with the id of your character
		//Then you can cast it so you can refer to its specific properties.
		//For example, if your shape is a Circle you can use:
		//Circle circle = (Circle) canvas.getShape(id)
		//and then change the specific Circle properties.
		
		Circle circle = (Circle) canvas.getShape("Circle");
		circle.setRadius(character.getRadius());
		circle.setFillColor(character.getColor());
		circle.setIsFilled(character.getIsFilled());

	}


}
