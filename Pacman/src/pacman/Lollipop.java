package pacman;

public class Lollipop {
	private Point location;
	private boolean visible = true;
	private String guid; // the id of the graphic element that represents this entity
	
	public Lollipop (int x, int y) {
		this.location = new Point(x,y);
		this.guid = "lp_"+ x + "_" + y;
	}
	
	public void hide() {
		visible = false;
	}
	
	public String getGuid() {
		return guid;
	}
	
	public Point getLocation() {
		return location;
	}
	
	public boolean isShown() {
		return visible;
	}

}
