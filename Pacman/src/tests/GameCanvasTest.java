package tests;

import javax.swing.JFrame;

import org.junit.Test;

import gui.GameCanvas;
import gui.Sleeper;
import shapes.Image;

public class GameCanvasTest {

	@Test
	public void test() {
		//Create a frame window and set its name, size and behavior when clicking the X
		JFrame frame = new JFrame("My Screen");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		
		//Create a canvas
		GameCanvas screen = new GameCanvas();
		
		//Add a pokimon to the canvas
		screen.addShape(new Image("e1", "resources/Poki.jpg", 220, 220, 10, 10));
		
		//Add the canvas to the frame ans show it
		frame.getContentPane().add(screen);
		frame.setVisible(true);
		
		//Move the pokimon 10 times
		for (int i = 0; i< 10; i++) {
			screen.moveShape("e1", 10, 5);
//			screen.flipStatus("e1");
			Sleeper.sleep(200);
		}
	}

}
