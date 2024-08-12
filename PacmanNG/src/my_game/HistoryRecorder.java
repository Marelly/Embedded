package my_game;

import DB.ExcelTable;
import base.Game;
import base.PeriodicLoop;
import my_base.MyContent;

public class HistoryRecorder {
    private MyContent content;
    private boolean isRecording = true;
    private ExcelTable history;; 

    public HistoryRecorder(MyContent content) {
        this.content = content;
        history = Game.excelDB().createTableFromExcel("PacmanHistory");
		history.deleteAllRows();
    }

    public void startRecording() {
        isRecording = true;
    }

    public void stopRecording() {
        isRecording = false;
        Game.excelDB().getTable("PacmanHistory").sortByKey();
		Game.excelDB().getTable("PacmanHistory").logTable();;
		Game.excelDB().commit();
    }

    public boolean isRecording() {
        return this.isRecording;
    }

    public void recordState() {
        // Record only if required.
        if (!isRecording) {
            return;
        }
        String[] state = new String[] {
            String.valueOf(PeriodicLoop.elapsedTime()),
            String.valueOf(content.pacman().getLocation().x),
            String.valueOf(content.pacman().getLocation().y),
            String.valueOf(content.ghosts().redGhost().getLocation().x),
            String.valueOf(content.ghosts().redGhost().getLocation().y),
            String.valueOf(content.ghosts().blueGhost().getLocation().x),
            String.valueOf(content.ghosts().blueGhost().getLocation().y),
            String.valueOf(content.ghosts().orangeGhost().getLocation().x),
            String.valueOf(content.ghosts().orangeGhost().getLocation().y),
            String.valueOf(content.ghosts().pinkGhost().getLocation().x),
            String.valueOf(content.ghosts().pinkGhost().getLocation().y)
        };
        try {
            history.insertRow(state);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error inserting new line to pacman history table");			
        }

    }

    public ExcelTable getHistoryTable() {
        return this.history;
    }

}
