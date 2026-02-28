package team.model;

public class Score {
    private int value;

    public int getValue() { return value; }

    public void reset() { value = 0; }

    public void onTargetReached(int howMany) {
        value += 100 * howMany;
    }

    public void onCrash() {
        value -= 50; // optional
    }
}