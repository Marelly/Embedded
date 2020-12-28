package buttons;

import game.Game;

public class EndButton extends GameButton {
	
	public EndButton(String id, String name, int width, int height, int posX, int posY) {
		super(id, name, width, height, posX, posY);
	}

	@Override
	public void buttonAction() {
		// The basic buttonAction prints the id of the button to the console.
		// Keep the call to super to preserve this behavior or remove it if you don't want the printing.
		super.buttonAction();
		Game.excelDB().getTable("pokimonMoves").sortByKey();
		Game.excelDB().commit();
		Game.endGame();

	}

}
