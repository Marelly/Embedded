package main;

import game.Game;
import game.MouseHandler;
import my_game.Point;
import shapes.Shape;

public class MyMouseHandler extends MouseHandler {

	// set content to point to myContent so we can get to all characters in the game
	MyContent content = (MyContent) Game.Content();
	boolean onShape = false;

	@Override
	public void screenClicked(int x, int y) {
		super.screenClicked(x, y);
		//TODO: Enter your specific code here
	}

	@Override
	public void screenRightClicked(int x, int y) {
		super.screenRightClicked(x, y);
		//TODO: Enter your specific code here
	}
	
	@Override
	public void shapeClicked(Shape shape, int x, int y) {
		super.shapeClicked(shape, x, y);
		//TODO: Enter your specific code here
		if (shape.getId() == content.pokimon().getImageID()) {
			content.pokimon().stopMoving();;
		}		
	}
	
	@Override
	public void shapeRightClicked(Shape shape, int x, int y) {
		super.shapeRightClicked(shape, x, y);
		//TODO: Enter your specific code here
		if (shape.getId() == content.pokimon().getImageID()) {
			content.pokimon().resumeMoving();;
		}		
	}

	@Override
    public void mouseMovedOverShape(Shape shape, int x, int y) {
		super.mouseMovedOverShape(shape, x, y);
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
    public void mouseDraggedOverShape(Shape shape, int x, int y) {
		super.mouseDraggedOverShape(shape, x, y);
		//TODO: Enter your specific code here

		//If we were already on shape do nothing
		if (!onShape) {
			if (shape.getId() == content.pokimon().getImageID()) {
				content.pokimon().switchImage();;
			}
			// Mark that we are already on shape
			onShape = true;
			if (draggedShape.getId() == "pokimon") {
				content.pokimon().setLocation(new Point(x,y));
			}
	
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
		super.mouseDraggedOverScreen(x, y);

		// Act only if we just left the shape
		if (onShape) {
			content.pokimon().switchImage();;
			// Mark that we left the shape
			onShape = false;
		}
		if (draggedShape.getId() == "pokimon") {
			content.pokimon().setLocation(new Point(x,y));
		}
		
	}

	@Override 
	public void shapePressed(Shape shape, int x, int y) {
		super.shapePressed(shape, x, y);
	}    
	
	@Override 
	public void shapeReleased(Shape shape, int x, int y) {
		super.shapeReleased(shape, x, y);
	}

	@Override 
	public void screenPressed(int x, int y) {
		super.screenPressed(x,y);
	}

	@Override 
	public void screenReleased(int x, int y) {
		super.screenReleased(x,y);
	}


}
