package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.Game;
import shapes.Circle;
import shapes.Image;
import shapes.Line;
import shapes.Rectangle;
import shapes.Shape;
import shapes.Shape.STATUS;
import shapes.TextLabel;

/**
 * A 2D screen that displays graphical shapes and enables to set their location at runtime, causing an animation effect.
 * 
 * <h1>Properties:</h1>
 * <ul>
 * <li>int width - the screen width, in pixels<br>
 * <li>int height - the screen hight, in pixels<br>
 * <li>borderColor - the color of the screen border, from a list of given colors [red, black, yellow, gray, white]<br>
 * <li>borderWidth - the width of the screen border, in pixels (0 if no border)<br>
 * <li>int positionX, positionY - the screen position, in pixels<br>
 * </ul>
 * 
 * <h1>Methods:</h1>
 * <ul>
 * 	<li>void addShape(shape) - adds a graphical shape, identified by id, to the screen.<br>
 * 	<li>void moveShape(id, dx, dy) - moves a shape identified by id by dx and dy pixels respectively.
 * 		For animation effect, one can execute the moveShape(id, dx, dy) in a loop with a time-based wait condition between each moveObject call.
 * 	<br>
 * 	<li>void moveToLocation(id, cordX, cordY) - moves an shape identified by id to coordinates cordX and cordY respectively.<br>
 * 	<li>void deleteShape(id) - permanently removes a shape identified by id from the screen.<br>
 * 	<li>void flipStatus(id) - changes the status of a shape and shows or hide it accordingly.<br>
 *  <il>void show(id) - shows a shape identified by id.<br>
 *  <il>void hide(id) - hides a shape identified by id.<br>
 *  <il>void showAll() shows all shapes.<br>
 *  <il>void hideAll() - hides all shapes.<br>
 *  <il>void deleteAll() - deletes all shapes from the screen.<br>
 * </ul>
 * 
 */
public class GameCanvas extends JPanel  {
	
	private static final long serialVersionUID = 1L;
	
	private final Map<String, Shape> shapes;

	private Shape[] sortedShapes;
	
	private boolean resort = true;

	int borderWidth;
	
	int positionX;
	int positionY;
		
	public GameCanvas() {
		super();
		this.setBackground(Color.WHITE);
		this.shapes = new HashMap<>();
		// Not relevant -> will be assigned by the default values in the 
		this.setLayout(null);
		addListeners();
	}

	public void addShape(final Shape shape) {
		resort = true;
		shapes.put(shape.getId(), shape);
		this.repaint();
	}
	
	public Shape getShape(final String id) {
		return shapes.get(id);
	}

	public void changeImage(final String id, final String src, final int width, final int height)
	{
		final Shape shape = shapes.get(id);
		if(shape == null){
			return;
		}
		if (!(shape instanceof Image)) {
			return;
		}
		final Image image = (Image) shape;
		this.remove(image.getImg());
		image.setImage(src, width, height);
		this.add(image.getImg());
		this.repaint();
	} 
			
	public void moveShape(final String id, final int dx, final int dy) {
		final Shape shape = shapes.get(id);
		if (shape != null) {
			shape.move(dx, dy);
			this.repaint();
			if (shape.getshapeListener() != null) {
				shape.getshapeListener().shapeMoved(id, dx, dy);
			}
		}
	}

	public void moveToLocation(final String id, final int cordX, final int cordY) {
		final Shape shape = shapes.get(id);
		if (shape != null) {
			shape.moveToLocation(cordX, cordY);;
			this.repaint();
		}
	}
	
	public void deleteShape(final String id) {
		final Shape shape = shapes.get(id);
		if (shape != null) {
			hide(id);
			if (shape instanceof Image) {
				final Image image = (Image) shape;
				this.remove(image.getImg());
			}	
			shapes.remove(id);
		}
		resort = true;
		this.repaint();
	}

	public void hideAll() {
		for (final Shape shape : shapes.values()) {
			shape.setStatus(STATUS.HIDE);
		}
		resort = true;
		this.repaint();
	}

	public void showAll() {
		for (final Shape shape : shapes.values()) {
			shape.setStatus(STATUS.SHOW);
		}
		resort = true;
		this.repaint();
	}

	public void deleteAll() {
		Shape shape;
		for (final String id : shapes.keySet()) {
			shape = shapes.get(id);
			if (shape != null) {
				hide(id);
			}
			if (shape instanceof Image) {
				final Image image = (Image) shape;
				this.remove(image.getImg());
			}	
		}
		shapes.clear();
		resort = true;
		this.repaint();
	}

	public void flipStatus(final String id) {
		final Shape shape = shapes.get(id);
		if (shape != null) {
			if (shape.getStatus() == STATUS.HIDE) {
				shape.setStatus(STATUS.SHOW);
			} else if (shape.getStatus() == STATUS.SHOW) {
				shape.setStatus(STATUS.HIDE);
			}
		}
		resort = true;
		this.repaint();
	}

	public void show(final String id) {
		final Shape shape = shapes.get(id);
		if (shape != null) {
			shape.setStatus(STATUS.SHOW);
		}
		resort = true;
		this.repaint();
	}
	
	public void hide(final String id) {
		final Shape shape = shapes.get(id);
		if (shape != null) {
			shape.setStatus(STATUS.HIDE);
		}
		resort = true;
		this.repaint();
	}
	
	protected void addListeners() {
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(final MouseEvent event) {
				final Shape shape = getShapeByXY(event.getX(), event.getY());
				if (shape != null) {
					Game.MouseHandler().shapeReleased(shape, event.getX(), event.getY());
				}
				else {
					Game.MouseHandler().screenReleased(event.getX(), event.getY());
				}
			}

			@Override
			public void mousePressed(final MouseEvent event) {
				final Shape shape = getShapeByXY(event.getX(), event.getY());
				if (shape != null) {
					Game.MouseHandler().shapePressed(shape, event.getX(), event.getY());
				}
				else {
					Game.MouseHandler().screenPressed(event.getX(), event.getY());
				}
			}

			@Override
			public void mouseExited(final MouseEvent event) {
			}

			@Override
			public void mouseEntered(final MouseEvent event) {
			}

			@Override
			public void mouseClicked(final MouseEvent event) {
				final Shape shape = getShapeByXY(event.getX(), event.getY());
				if (shape != null) {
					if (event.getButton() == 1) {// click
						Game.MouseHandler().shapeClicked(shape, event.getX(), event.getY());
					}
					if (event.getButton() == 3) {// rightclick
						Game.MouseHandler().shapeRightClicked(shape, event.getX(), event.getY());
					}
					
				}
				else {
					if (event.getButton() == 1) {// click
						Game.MouseHandler().screenClicked(event.getX(), event.getY());
					}					
					if (event.getButton() == 3) {// rightclick
						Game.MouseHandler().screenRightClicked(event.getX(), event.getY());
					}
				}
			}
		});
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(final MouseEvent event) {
				final Shape shape = getShapeByXY(event.getX(), event.getY());
				if (shape != null) {
					Game.MouseHandler().mouseMovedOverShape(shape, event.getX(), event.getY());
				}
				else {
					Game.MouseHandler().mouseMovedOverScreen(event.getX(), event.getY());
				}
			}

			@Override
			public void mouseDragged(final MouseEvent event) {
				final Shape shape = getShapeByXY(event.getX(), event.getY());
				if (shape != null) {
					Game.MouseHandler().mouseDraggedOverShape(shape, event.getX(), event.getY());
				}
				else {
					Game.MouseHandler().mouseDraggedOverScreen(event.getX(), event.getY());
				}

			}
		});
		
		
	}

	private Shape[] getImagesSortedByZOrder() {
		if (resort) {
			sortedShapes = shapes.values().toArray(new Shape[0]);
			Arrays.sort(sortedShapes, (s1,s2) -> {return (s1.getzOrder() - s2.getzOrder());});
			resort = false;
		}
		return sortedShapes;
	}

	private Shape getShapeByXY(final int x, final int y) {
		Shape[] tempShapes = getImagesSortedByZOrder();
		Shape shape;

		// Run over the shapes in reverse order so that top shape is selected 
		// before a bottom shape.
		for (int i = tempShapes.length-1; i >=0 ; i--) {
		   shape = tempShapes[i];
		   if (shape.getStatus() == STATUS.SHOW) {
			   if (shape.isInArea(x, y)) {
					return shape;
				}
			}
		}
		return null;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		 super.paintComponent(g);

		 // Draw the shapes according to their ZOrder.
		 // To have a geometric shape in front of another it should be drawn later.
		 // To have an image in front of another it should be drawn earlier.
		 // Thus, we traverse the array twice, in order for the shapes and then in reverse
		 // order for the images.
		 Shape[] tempShapes = getImagesSortedByZOrder();
		 Shape shape;
			  
		 // Draw geometric shapes and texts in order
		 for (int i = 0; i < tempShapes.length; i++) {
			shape = tempShapes[i];
			if (shape instanceof Image) {
				continue; // Do not handle Images in this pass
			}
			if (shape.getStatus() == STATUS.SHOW) {
				shape.draw((Graphics2D) g);
				if (shape instanceof TextLabel) {
					TextLabel lbl = (TextLabel) shape;
					this.add(lbl.getLabel());
				}

			}
			if (shape.getStatus() == STATUS.HIDE) {
				if (shape instanceof TextLabel) {
					TextLabel lbl = (TextLabel) shape;
					this.remove(lbl.getLabel());
				}

			}
		}

		// Draw images in reverse order
		for (int i = tempShapes.length-1; i >=  0; i--) {
			shape = tempShapes[i];
			if (!(shape instanceof Image)) {
				continue; // Do not handle Non-Image shapes in this pass
			}
			if (shape.getStatus() == STATUS.SHOW) {
				shape.draw((Graphics2D) g);
				Image image = (Image) shape;
				this.add(image.getImg());

			}
			if (shape.getStatus() == STATUS.HIDE) {
				Image image = (Image) shape;
				this.remove(image.getImg());
			}
		}

	}


	public static void main(final String[] args) {
		
		//Create a frame window and set its name, size and behavior when clicking the X
		final JFrame frame = new JFrame("My Screen");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		
		//Create a canvas
		final GameCanvas screen = new GameCanvas();
		
		//Add a pokemon to the canvas
		final Image p1 = new Image("e1", "resources/Poki.jpg", 220, 220, 10, 10);
		screen.addShape(p1);
		final Circle c1 = new Circle("c1", 100,100,100);
		c1.setIsFilled(true);
		c1.setFillColor(Color.BLUE);
		screen.addShape(c1);
		screen.addShape(new Rectangle("r1", 600, 600, 200, 150));
		screen.addShape(new Line("l1", 20,20,120,120));
		final TextLabel t1 = new TextLabel("t1", "Hello World", 600, 400);
		t1.setColor(Color.GREEN);
		t1.setFontName("Helvetica");
		t1.setFontSize(30);
		screen.addShape(t1);

//		Text t1 = new Text("t1", "Hello World", 600, 400);
//		t1.setColor(Color.GREEN);
//		t1.setFontName("Helvetica");
//		t1.setFontSize(30);
//		screen.addShape(t1);

		
		//Add the canvas to the frame and show it
		frame.getContentPane().add(screen);
		frame.setVisible(true);

		Sleeper.sleep(200);
		
		final TextLabel tp = new TextLabel("tp", "Pokimon", 50, 50);
		tp.setColor(Color.RED);
		tp.setFontName("Helvetica");
		tp.setFontSize(30);
		p1.addTextLabel(tp);
		
		//Move the pokemon 10 times
		for (int i = 0; i< 10; i++) {
			screen.moveShape("e1", 10, 5);
			screen.moveShape("t1", 20, 20);
			Sleeper.sleep(400);
		}
		//Flicker pokemon
		for (int i = 0; i< 4; i++) {
			screen.flipStatus("e1");
			Sleeper.sleep(200);
		}
		
		screen.changeImage("e1", "resources/Poki2.jpg", 259, 194);
		
		//Move the pokemon 10 times
		for (int i = 0; i< 10; i++) {
			screen.moveShape("e1", 10, 5);
			Sleeper.sleep(200);
		}
		
		//Move the circle 5 times
		for (int i = 0; i< 5; i++) {
			screen.moveShape("c1", 50, 50);
			screen.moveShape("r1", 50, 50);
			Sleeper.sleep(200);
		}
		c1.setFillColor(Color.RED);
		
		for (int i = 0; i< 5; i++) {
			screen.moveShape("c1", 50, 50);
			screen.moveShape("l1", 50, 50);
			//t1.setText("Hello: "+i);
			Sleeper.sleep(200);
		}
		
		((Image) screen.getShape("e1")).removeTextLabel();
		Sleeper.sleep(1000);
		
		screen.hide("c1");
		Sleeper.sleep(1000);
		screen.show("c1");
		Sleeper.sleep(1000);
		screen.hideAll();
		Sleeper.sleep(1000);
		screen.showAll();
	}

}
