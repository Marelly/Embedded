package buttons;

import game.Game;
import main.MyContent;

public class EditPolygonButton extends GameButton{
	
	public EditPolygonButton(String id, String name, int posX, int posY) {
		super(id, name, 160, 40, posX, posY);
		this.setText("Edit Polygon");
	}

	@Override
	public void buttonAction() {
		// The basic buttonAction prints the id of the button to the console.
		// Keep the call to super to preserve this behavior or remove it if you don't want the printing.
		super.buttonAction();
		
		MyContent content = (MyContent) Game.Content();
		if (content.polygon().isEditMode()) {
			content.polygon().exitEditMode();
		} else {
			content.polygon().enterEditMode();
		}
	}

}
