package game;

import shapes.Shape;

public class MouseHandler {
	public void screenClicked(int x, int y) {
		System.out.println("Mouse clicked: " + x + ", "+ y);
	}

	public void screenRightClicked(int x, int y) {
		System.out.println("Mouse right-clicked: " + x + ", "+ y);
	}

	public void mouseMovedOverScreen(int x, int y) {
		System.out.println("Mouse moved: " + x + ", "+ y);
	}

	public void mouseDraggedOverScreen(int x, int y) {
		System.out.println("Mouse dragged: " + x + ", "+ y);
	}

	public void shapeClicked(Shape shape) {
		System.out.println("Shape clicked: " + shape.getId());
	}
	
	public void shapeRightClicked(Shape shape) {
		System.out.println("Shape right-clicked: " + shape.getId());
	}
	
    public void mouseMovedOverShape(Shape shape) {
		System.out.println("Mouse moved over Shape: " + shape.getId());
    }
    	
    public void mouseDraggedOverShape(Shape shape) {
		System.out.println("Mouse Dragged over Shape: " + shape.getId());
    }    

	public void shapePressed(Shape shape) {
		System.out.println("Shape pressed: " + shape.getId());
    }    

	public void shapeReleased(Shape shape) {
		System.out.println("Shape Released: " + shape.getId());
	} 
	
	public void screenPressed(int x, int y) {
		System.out.println("Mouse pressed: " + x + ", "+ y);
	}

	public void screenReleased(int x, int y) {
		System.out.println("Mouse released: " + x + ", "+ y);
	}

}
