package my_ui_elements;

import base.Game;
import my_base.MyContent;
import ui_elements.GameButton;

public class PlayButton extends GameButton {
    private MyContent content;
	
	public PlayButton(String id, String name, int width, int height, int posX, int posY) {
		super(id, name, width, height, posX, posY);
        this.content = (MyContent) Game.Content();
	}

	@Override
	public void action() {
		// The basic buttonAction prints the id of the button to the console.
		// Keep the call to super to preserve this behavior or remove it if you don't want the printing.
		super.action();
		content.historyPlayer().startPlaying();
	}

}
