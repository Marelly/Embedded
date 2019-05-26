package my_game;

import gui.GameButton;
import main.MyContent;
import my_game.Pokimon.Direction;

public class DirectionButton extends GameButton{
	
	private Direction direction;
	private MyContent myContent;
	
	public DirectionButton(String id, String name, int posX, int posY, Direction direction) {
		super(id, name, 100, 40, posX, posY);
		this.direction = direction;
		myContent = (MyContent) this.content;
	}

	@Override
	public void buttonAction() {
		// The basic buttonAction prints the id of the button to the console.
		// Keep the call to super to preserve this behavior or remove it if you don't want the printing.
		super.buttonAction();
		myContent.pokimon().setDirectionPolicy(direction);
	}

}
