package main;

import game.Game;
import game.MouseHandler;
import shapes.Shape;

public class MyMouseHandler extends MouseHandler {
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
		Game.UI().canvas().changeImage("pokimon", "resources/Poki2.jpg", 260, 195);
	}
	
	@Override
	public void ShapeRightClicked(Shape shape) {
		super.ShapeRightClicked(shape);
		//TODO: Enter your specific code here
		Game.UI().canvas().changeImage("pokimon", "resources/Poki.jpg", 220, 200);
	}


}
