package shapes;

import java.awt.Graphics2D;

public class Rectangle extends FilledShape{
	private int posX, posY, width, height;
	
	public Rectangle(String id, int x, int y, int width, int height) {
		super(id);
		this.posX = x;
		this.posY = y;
		this.width = width;
		this.height = height;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		g.drawRect(posX, posY, width, height);
		if (isFilled()) {
			g.setColor(getFillColor());
			g.fillRect(posX, posY, width, height);
		}
	}
	
	@Override
	public boolean isInArea(int x, int y) {
		return (x >= posX && x <= posX + width && y > posY && y < posY + height );
	}
	
	@Override
	public void move(int dx, int dy) {
		this.posX += dx;
		this.posY += dy;
	}
	
	@Override
	public void moveToLocation(int x, int y) {
		this.posX = x;
		this.posY = y;
	}	
	

}
