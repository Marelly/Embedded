package my_base;

import base.PeriodicLoop;
import my_game.GameControl;

public class MyPeriodicLoop extends PeriodicLoop {

	private MyContent content;
	private GameControl control;

	public void setContent(MyContent content) {
		this.content = content;
	}

	public void setGameControl(GameControl control) {
		this.control = control;
	}
	
	@Override
	public void execute() {
		// Let the super class do its work first
		super.execute();
		// Do someting only if game control was initialized.
		if (control != null) {
			control.gameStep();
		}			
	}

}
