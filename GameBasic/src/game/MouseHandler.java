package game;

import shapes.Shape;

public class MouseHandler {
	public void ScreenClicked(int x, int y) {
		System.out.println("Mouse clicked: " + x + ", "+ y);
	}

	public void ScreenRightClicked(int x, int y) {
		System.out.println("Mouse right-clicked: " + x + ", "+ y);
	}

	public void mouseMovedOverScreen(int x, int y) {
		System.out.println("Mouse moved: " + x + ", "+ y);
	}

	public void mouseDraggedOverScreen(int x, int y) {
		System.out.println("Mouse dragged: " + x + ", "+ y);
	}

	public void ShapeClicked(Shape shape) {
		System.out.println("Shape clicked: " + shape.getId());
	}
	
	public void ShapeRightClicked(Shape shape) {
		System.out.println("Shape right-clicked: " + shape.getId());
	}
	
    public void mouseMovedOverShape(Shape shape) {
		System.out.println("Mouse moved over Shape: " + shape.getId());
    }
    	
    public void mouseDraggedOverShape(Shape shape) {
		System.out.println("Mouse Dragged over Shape: " + shape.getId());
    }    
	
}
