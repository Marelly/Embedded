package my_game;

import game.Game;
import gui.GameButton;
import gui.GameCanvas;
import main.MyContent;
import my_game.Pokimon.Direction;
import shapes.Circle;

public class AddButton extends GameButton{
	
	public AddButton(String id, String name, int posX, int posY) {
		super(id, name, 100, 40, posX, posY);
	}

	@Override
	public void buttonAction() {
		// The basic buttonAction prints the id of the button to the console.
		// Keep the call to super to preserve this behavior or remove it if you don't want the printing.
		super.buttonAction();
		
		MyContent content = (MyContent) Game.Content();
		//TODO
		//Add your character to your game content
		content.addCharacter();
		
	}

}
