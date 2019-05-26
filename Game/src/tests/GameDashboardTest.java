package tests;

import java.awt.Color;

import javax.swing.JFrame;

import org.junit.Test;

import gui.GameDashboard;
import gui.Sleeper;

public class GameDashboardTest {

	@Test
	public void test() {
		//Create a frame window and set its name, size and behavior when clicking the X
		JFrame frame = new JFrame("My Screen");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		
		//Create a dashboard
		GameDashboard dashboard = new GameDashboard();
		
		//Add a button to the dashboard
		dashboard.addButton("b1", "button1", 100, 60, 10, 10);
		
		//Set the dashboard background color to red
		dashboard.setBackground(Color.RED);
		
		//Add the dashboard to the frame window
		frame.getContentPane().add(dashboard);
		frame.setVisible(true);
		
		//Flip the visibility status of the button 10 times.
		for (int i = 0; i< 10; i++) {
			dashboard.flipStatus("b1");
			Sleeper.sleep(200);
		}

	}

}
