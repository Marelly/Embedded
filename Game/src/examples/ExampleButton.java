package examples;

import java.sql.ResultSet;
import java.sql.SQLException;

import game.Game;
import gui.GameButton;

/*
 * This class extends GameButton and overrides its buttonAction behavior.
 * This way, you can create your own buttons with commands that do what you want.
 */
public class ExampleButton extends GameButton{

	public ExampleButton(String id, String name, int width, int height, int posX, int posY) {
		super(id, name, width, height, posX, posY);
	}
	
@Override
public void buttonAction() {
	// The basic buttonAction prints the id of the button to the console.
	// Keep the call to super to preserve this behavior or remove it if you don't want the printing.
	super.buttonAction();
	
	Game.UI().canvas().moveShape("e2", 10, 10);
	//Execute an SQL query and get the results into a result set
	ResultSet rs = Game.DB().executeQuery("select * from users");  
	try {
		//Iterate over the rows and print each column according to its type (int, string, etc.)
		while(rs.next())  
			System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getInt(3));
	} catch (SQLException e) {
		e.printStackTrace();
	}

}


}
