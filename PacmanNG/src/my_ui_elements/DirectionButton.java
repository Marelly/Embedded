package my_ui_elements;

import ui_elements.GameButton;
import my_base.MyContent;
import my_game.Maze.Direction;

public class DirectionButton extends GameButton{
	
	private Direction direction;
	private MyContent myContent;
	
	public DirectionButton(String id, String name, int posX, int posY, Direction direction) {
		super(id, name, 100, 40, posX, posY);
		this.direction = direction;
		myContent = (MyContent) this.content;
	}

	@Override
	public void action() {
		// The basic buttonAction prints the id of the button to the console.
		// Keep the call to super to preserve this behavior or remove it if you don't want the printing.
		super.action();
		myContent.pacman().setDirectionPolicy(direction);
	}

}
