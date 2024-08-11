package my_game;

/**
 * This is a simple class that manages the score.
 * It is responsible for resetting and increasing the score, as well managing its text.
 */
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
