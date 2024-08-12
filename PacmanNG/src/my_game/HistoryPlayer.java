package my_game;

import my_base.MyContent;
import my_game.HistoryIndication.HistoryState;

public class HistoryPlayer {
    private MyContent content;
    private boolean isPlaying = false;
    private String[][] historyArray;
    private int currentHistoryLine;

    public HistoryPlayer(MyContent content) {
        this.content = content;
    }

    public void startPlaying() {
        content.maze().initLollipops();
        content.getBoard().initBoard();
        content.score().reset();
        isPlaying = true;
        // Sort history table by time stamp.
        content.historyRecorder().getHistoryTable().sortByKey();
        // Get the history table as a two dimensional array
        historyArray = content.historyRecorder().getHistoryTable().getTableAsMatrix();
        // set index to first line
        currentHistoryLine = 0;
    }

    public void stopPlaying() {
        isPlaying = false;
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public void playState() {
        //If reached the end of the history, stop playing
        if (currentHistoryLine == historyArray.length) {
            content.historyIndication().setHistoryState(HistoryState.Play);
            return;
        }
        String[] state = historyArray[currentHistoryLine];
        content.pacman().setLocation(new BoardPoint(Integer.valueOf(state[1]), Integer.valueOf(state[2])));
        content.ghosts().redGhost().setLocation(new BoardPoint(Integer.valueOf(state[3]), Integer.valueOf(state[4])));
        content.ghosts().blueGhost().setLocation(new BoardPoint(Integer.valueOf(state[5]), Integer.valueOf(state[6])));
        content.ghosts().orangeGhost().setLocation(new BoardPoint(Integer.valueOf(state[7]), Integer.valueOf(state[8])));
        content.ghosts().pinkGhost().setLocation(new BoardPoint(Integer.valueOf(state[9]), Integer.valueOf(state[10])));
        currentHistoryLine++;
    }
}
