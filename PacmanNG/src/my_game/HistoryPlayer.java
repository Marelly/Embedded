package my_game;

import DB.ExcelTable;
import base.Game;
import my_base.MyContent;

public class HistoryPlayer {
    private MyContent content;
    private boolean isPlaying = true;
    private ExcelTable history;
    private String[][] historyArray;
    private int currentHistoryLine;

    public HistoryPlayer(MyContent content) {
        this.content = content;
        history = Game.excelDB().createTableFromExcel("pacmanHistory");
    }

    public void startPlaying() {
        isPlaying = true;
        // Sort history table by time stamp.
        history.sortByKey();
        // Get the table as a two dimensional array
        historyArray = history.getTableAsMatrix();
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
        String[] state = historyArray[currentHistoryLine];
        content.pacman().setLocation(new BoardPoint(Integer.valueOf(state[1]), Integer.valueOf(state[2])));
        content.ghosts().redGhost().setLocation(new BoardPoint(Integer.valueOf(state[3]), Integer.valueOf(state[4])));
        content.ghosts().blueGhost().setLocation(new BoardPoint(Integer.valueOf(state[3]), Integer.valueOf(state[4])));
        content.ghosts().orangeGhost().setLocation(new BoardPoint(Integer.valueOf(state[3]), Integer.valueOf(state[4])));
        content.ghosts().pinkGhost().setLocation(new BoardPoint(Integer.valueOf(state[3]), Integer.valueOf(state[4])));
        currentHistoryLine++;
    }
}
