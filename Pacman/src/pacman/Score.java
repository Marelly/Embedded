package pacman;

public class Score {
	private int score = 0;
	
	public void reset() {
		score = 0;
	}
	
	public void add(int points) {
		score += points;
	}
	
	public void subtract(int points ) {
		score -= points;
	}
	
	public int points() {
		return score;
	}
	
	public String guid() {
		return "score";
	}
	
	public String getText() {
		return "SCORE: " + score;
	}

}
