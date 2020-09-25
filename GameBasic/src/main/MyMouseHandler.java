package main;

import game.Game;
import game.MouseHandler;
import shapes.Shape;

public class MyMouseHandler extends MouseHandler {

	// set content to point to myContent so we can get to all characters in the game
	MyContent content = (MyContent) Game.Content();
	boolean onShape = false;

	@Override
	public void ScreenClicked(int x, int y) {
		super.ScreenClicked(x, y);
		//TODO: Enter your specific code here
	}

	@Override
	public void ScreenRightClicked(int x, int y) {
		super.ScreenRightClicked(x, y);
		//TODO: Enter your specific code here
	}
	
	@Override
	public void ShapeClicked(Shape shape) {
		super.ShapeClicked(shape);
		//TODO: Enter your specific code here
		if (shape.getId() == content.pokimon().getImageID()) {
			content.pokimon().stopMoving();;
		}		
	}
	
	@Override
	public void ShapeRightClicked(Shape shape) {
		super.ShapeRightClicked(shape);
		//TODO: Enter your specific code here
		if (shape.getId() == content.pokimon().getImageID()) {
			content.pokimon().resumeMoving();;
		}		
	}

	@Override
    public void mouseMovedOverShape(Shape shape) {
		super.mouseMovedOverShape(shape);
		//TODO: Enter your specific code here

		//If we were already on shape do nothing
		if (!onShape) {
			if (shape.getId() == content.pokimon().getImageID()) {
				content.pokimon().switchImage();;
			}
			// Mark that we are already on shape
			onShape = true;
		}
    }
	
	@Override
    public void mouseDraggedOverShape(Shape shape) {
		super.mouseDraggedOverShape(shape);
		//TODO: Enter your specific code here

		//If we were already on shape do nothing
		if (!onShape) {
			if (shape.getId() == content.pokimon().getImageID()) {
				content.pokimon().switchImage();;
			}
			// Mark that we are already on shape
			onShape = true;
		}
	}
	
	@Override 
	public void mouseMovedOverScreen(int x, int y) {
		//super.mouseMovedOverScreen(x, y);

		// Act only if we just left the shape
		if (onShape) {
			content.pokimon().switchImage();;
			// Mark that we left the shape
			onShape = false;
		}
	}
	
	@Override 
	public void mouseDraggedOverScreen(int x, int y) {
		//super.mouseDraggedOverScreen(x, y);

		// Act only if we just left the shape
		if (onShape) {
			content.pokimon().switchImage();;
			// Mark that we left the shape
			onShape = false;
		}
		
	}

}
