package game;

import shapes.Shape;

public class MouseHandler {

	protected Shape draggedShape;

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
		if (draggedShape != null) {
			draggedShape.dragMove(x, y);
		}
	}

	public void shapeClicked(Shape shape, int x, int y) {
		System.out.println("Shape clicked: " + shape.getId());
	}
	
	public void shapeRightClicked(Shape shape, int x, int y) {
		System.out.println("Shape right-clicked: " + shape.getId());
	}
	
    public void mouseMovedOverShape(Shape shape, int x, int y) {
		System.out.println("Mouse moved over Shape: " + shape.getId());
    }
    	
    public void mouseDraggedOverShape(Shape shape, int x, int y) {
		System.out.println("Mouse Dragged over Shape: " + shape.getId());
		if (draggedShape != null) {
			draggedShape.dragMove(x, y);
		}
    }    

	public void shapePressed(Shape shape, int x, int y) {
		System.out.println("Shape pressed: " + shape.getId());
		draggedShape = shape;
		draggedShape.startDrag(x, y);
    }    

	public void shapeReleased(Shape shape, int x, int y) {
		System.out.println("Shape Released: " + shape.getId());
		if (draggedShape != null) {
			draggedShape.endDrag(x, y);
			draggedShape = null;
		}
	} 
	
	public void screenPressed(int x, int y) {
		System.out.println("Mouse pressed: " + x + ", "+ y);
	}

	public void screenReleased(int x, int y) {
		System.out.println("Mouse released: " + x + ", "+ y);
		if (draggedShape != null) {
			draggedShape.endDrag(x, y);
			draggedShape = null;
		}
	}

}
