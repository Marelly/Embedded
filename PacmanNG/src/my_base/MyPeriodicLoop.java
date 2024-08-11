package my_base;

import base.PeriodicLoop;

public class MyPeriodicLoop extends PeriodicLoop {

	private MyContent content;

	public void setContent(MyContent content) {
		this.content = content;
	}


	@Override
	public void execute() {
		// Let the super class do its work first
		super.execute();
		// Do someting only if game control was initialized.
		if (content.gameControl() != null) {
			content.gameControl().gameStep();
		}			
	}

}
