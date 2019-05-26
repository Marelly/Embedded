package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import shapes.Text;
import shapes.Shape.STATUS;

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

	public void addShape(Shape shape) {
		shapes.put(shape.getId(), shape);
		this.updateUI();
	}
	
	public Shape getShape(String id) {
		return shapes.get(id);
	}

	public void changeImage(String id, String src, int width, int height)
	{
		Shape shape = shapes.get(id);
		if(shape == null){
			return;
		}
		if (!(shape instanceof Image)) {
			return;
		}
		Image image = (Image) shape;
		this.remove(image.getImg());
		image.setImage(src, width, height);
		this.repaint();
	} 
			
	public void moveShape(String id, int dx, int dy) {
		Shape shape = shapes.get(id);
		if (shape != null) {
			shape.move(dx, dy);
			this.repaint();
		}
	}

	public void moveToLocation(String id, int cordX, int cordY) {
		Shape shape = shapes.get(id);
		if (shape != null) {
			shape.moveToLocation(cordX, cordY);;
			this.repaint();
		}
	}
	
	public void deleteShape(String id) {
		Shape shape = shapes.get(id);
		if (shape != null) {
			shapes.remove(id);
		}
		this.updateUI();
	}

	public void hideAll() {
		for (Shape shape : shapes.values()) {
			shape.setStatus(STATUS.HIDE);
//			this.remove(entity.getImg());
		}
		this.updateUI();
	}

	public void showAll() {
		for (Shape shape : shapes.values()) {
			shape.setStatus(STATUS.SHOW);
		}
		this.updateUI();
	}

	public void deleteAll() {
		for (String id : shapes.keySet()) {
			deleteShape(id);
		}
		this.updateUI();
	}

	public void flipStatus(String id) {
		Shape shape = shapes.get(id);
		if (shape != null) {
			if (shape.getStatus() == STATUS.HIDE) {
				shape.setStatus(STATUS.SHOW);
//				this.add(entity.getImg());
			} else if (shape.getStatus() == STATUS.SHOW) {
				shape.setStatus(STATUS.HIDE);
//				this.remove(entity.getImg());
			}
		}
		this.updateUI();
	}

	public void show(String id) {
		Shape shape = shapes.get(id);
		if (shape != null) {
			shape.setStatus(STATUS.SHOW);
//				this.add(entity.getImg());
		}
		this.updateUI();
	}
	
	public void hide(String id) {
		Shape shape = shapes.get(id);
		if (shape != null) {
			shape.setStatus(STATUS.HIDE);
//				this.remove(entity.getImg());
		}
		this.updateUI();
	}
	
	protected void addListeners() {
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent event) {
				Shape shape = ClickedShape(event.getX(), event.getY());
				if (shape != null) {
					if (event.getButton() == 1) {// click
						Game.MouseHandler().ShapeClicked(shape);
					}
					if (event.getButton() == 3) {// rightclick
						Game.MouseHandler().ShapeRightClicked(shape);
					}
					
				}
				else {
					if (event.getButton() == 1) {// click
						Game.MouseHandler().ScreenClicked(event.getX(), event.getY());
					}					
					if (event.getButton() == 3) {// rightclick
						Game.MouseHandler().ScreenRightClicked(event.getX(), event.getY());
					}
				}
			}
		});
	}

	private Shape ClickedShape(int x, int y) {
		for (Shape shape : shapes.values()) {
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
		 	 
		for (Shape shape : shapes.values()) {
			if (shape.getStatus() == STATUS.SHOW) {
				shape.draw((Graphics2D) g);
				if (shape instanceof Image) {
					Image image = (Image) shape;
					this.add(image.getImg());
				}
			}
			if (shape.getStatus() == STATUS.HIDE && shape instanceof Image) {
				Image image = (Image) shape;
				this.remove(image.getImg());
			}
		}

	  }


	public static void main(String[] args) {
		
		//Create a frame window and set its name, size and behavior when clicking the X
		JFrame frame = new JFrame("My Screen");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		
		//Create a canvas
		GameCanvas screen = new GameCanvas();
		
		//Add a pokemon to the canvas
		screen.addShape(new Image("e1", "resources/Poki.jpg", 220, 220, 10, 10));
		Circle c1 = new Circle("c1", 100,100,100);
		c1.setIsFilled(true);
		c1.setFillColor(Color.BLUE);
		screen.addShape(c1);
		screen.addShape(new Rectangle("r1", 600, 600, 200, 150));
		screen.addShape(new Line("l1", 20,20,120,120));
		Text t1 = new Text("t1", "Hello World", 600, 400);
		t1.setColor(Color.GREEN);
		t1.setFontName("Helvetica");
		t1.setFontSize(30);
		screen.addShape(t1);
		
		//Add the canvas to the frame ans show it
		frame.getContentPane().add(screen);
		frame.setVisible(true);
		
		//Move the pokemon 10 times
		for (int i = 0; i< 10; i++) {
			screen.moveShape("e1", 10, 5);
			screen.moveShape("t1", 20, 20);
			Sleeper.sleep(200);
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
			t1.setText("Hello: "+i);
			Sleeper.sleep(200);
		}
		
		screen.hide("c1");
		Sleeper.sleep(1000);
		screen.show("c1");
		Sleeper.sleep(1000);
		screen.hideAll();
		Sleeper.sleep(1000);
		screen.showAll();



	}

}
