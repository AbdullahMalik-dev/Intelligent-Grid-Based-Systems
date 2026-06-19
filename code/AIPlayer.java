import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class AIPlayer {
    private MazeGrid mazeGrid;
    private List<int[]> path;
    private int currentStep = 0;
    private Timer timer;

    public AIPlayer(MazeGrid mazeGrid, List<int[]> path, JPanel panel) {
        this.mazeGrid = mazeGrid;
        this.path = path;

        // Timer fires every 100 milliseconds to create animation
        timer = new Timer(100, e -> {
            if (currentStep < path.size()) {
                int[] pos = path.get(currentStep);
                int[][] maze = mazeGrid.getMaze();
                
                // Mark current position as AI (Value 4), unless it's the Start or Exit
                if (maze[pos[0]][pos[1]] != 2 && maze[pos[0]][pos[1]] != 3) {
                    maze[pos[0]][pos[1]] = 4; 
                }

                // Clear the previous cell's color so it doesn't leave a trail
                if (currentStep > 0) {
                    int[] prev = path.get(currentStep - 1);
                    if (maze[prev[0]][prev[1]] == 4) {
                        maze[prev[0]][prev[1]] = 1; // Revert to path (Value 1)
                    }
                }
                
                panel.repaint();
                currentStep++;
            } else {
                timer.stop(); // Stop when exit is reached
            }
        });
    }

    public void startMoving() {
        if (path != null && !path.isEmpty()) {
            timer.start();
        }
    }
}