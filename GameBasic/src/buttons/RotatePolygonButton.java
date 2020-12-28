package buttons;

import game.Game;
import main.MyContent;

public class RotatePolygonButton extends GameButton{
	
	public RotatePolygonButton(String id, String name, int posX, int posY) {
		super(id, name, 160, 40, posX, posY);
		this.setText("Rotate Polygon");
	}

	@Override
	public void buttonAction() {
		// The basic buttonAction prints the id of the button to the console.
		// Keep the call to super to preserve this behavior or remove it if you don't want the printing.
		super.buttonAction();
		
		MyContent content = (MyContent) Game.Content();
		content.polygon().exitEditMode();
		content.polygon().getVisualPolygon().rotate(45);
		//TODO
		//Change your character properties by calling the appropriate method of
		//MyContent
		
	}

}
