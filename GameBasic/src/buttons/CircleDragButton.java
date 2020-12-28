package buttons;

import game.Game;
import shapes.Shape;

public class CircleDragButton extends GameButton{
	
	public CircleDragButton(String id, String name, int posX, int posY) {
		super(id, name, 180, 40, posX, posY);
		this.setText("Drag Circle");
	}

	@Override
	public void buttonAction() {
		// The basic buttonAction prints the id of the button to the console.
		// Keep the call to super to preserve this behavior or remove it if you don't want the printing.
		super.buttonAction();
		
		//TODO
		//Add your character to your game content

		Shape c = Game.UI().canvas().getShape("circle");
		c.setDraggable(!c.isDraggable());
		if (c.isDraggable()) {
			this.setText("Forbid Drag Circle");
		}
		else {
			this.setText("Drag Circle");
		}

	}

}
