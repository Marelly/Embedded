package my_game;

/**
 * BoardPoint is used for point in Board coordinates, which is the maze grid.
 * Transformation to pixels (ScreenPoints) will be done by the board class.
 */
public class BoardPoint {
  
        // Although not recommended to use public variables, allows easy access through p.x
        public int x, y;
    
        public BoardPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
