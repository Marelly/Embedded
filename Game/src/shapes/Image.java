package shapes;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Image extends Shape {
	
	private JLabel img;	
	private int width;
	private int height;
	private int posX;
	private int posY;
	public Image(String id, String src, int width, int height, int posX, int posY) {
		super(id);
		setImage(src, width, height);
		this.posX = posX;
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
	public JLabel getImg() {
		return img;
	}
	
	public void setImage(String src, int width, int height)
	{
		JLabel label;
		try {
			BufferedImage myPicture = ImageIO.read(new File(src));
			label = new JLabel(new ImageIcon(myPicture));
		} catch (IOException e) {
			// The image cannot be loaded
			label = new JLabel();
		}
		this.img = label;
		this.width = width;
		this.height = height;		
	} 


	@Override
	public void draw(Graphics2D g) {
		getImg().setBounds(getPosX(), getPosY(), getWidth(), getHeight());
	}
	
	@Override
	public boolean isInArea(int x, int y) {
		return (x >= posX && x<= posX + width && y >= posY && y<= posY + height );
	}
	
	@Override
	public void move(int dx, int dy) {
		this.posX += dx;
		this.posY += dy;
		draw(null);
	}
	
	@Override
	public void moveToLocation(int x, int y) {
		this.posX = x;
		this.posY = y;
		draw(null);
	}	
	
}
