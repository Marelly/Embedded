package my_game;

public class Lollipop {
	private BoardPoint location;
	private boolean visible = true;
	private String guid; // the id of the graphic element that represents this entity
	
	public Lollipop (int x, int y) {
		this.location = new BoardPoint(x,y);
		this.guid = "lp_"+ x + "_" + y;
	}
	
	public void hide() {
		visible = false;
	}
	
	public String getGuid() {
		return guid;
	}
	
	public BoardPoint getLocation() {
		return location;
	}
	
	public boolean isShown() {
		return visible;
	}

}
