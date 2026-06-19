import java.util.*;

public class MazeGenerator {
    private MazeGrid mazeGrid;
    private int[][] maze;

    public MazeGenerator(MazeGrid mazeGrid) {
        this.mazeGrid = mazeGrid;
        this.maze = mazeGrid.getMaze();
    }

    public void generateMaze() {
        // Step 1: Initialize everything as a wall (0)
        for (int row = 0; row < mazeGrid.getRows(); row++) {
            for (int col = 0; col < mazeGrid.getCols(); col++) {
                maze[row][col] = 0;
            }
        }
        // Step 2: Carve paths recursively starting at cell (1,1)
        generate(1, 1);

        // Step 3: Set constant entry and escape points
        maze[1][1] = 2; 
        maze[mazeGrid.getRows() - 2][mazeGrid.getCols() - 2] = 3; 
    }

    private void generate(int row, int col) {
        maze[row][col] = 1; // Mark current cell as a path

        // Look two steps out in all 4 cardinal directions
        List<int[]> directions = new ArrayList<>();
        directions.add(new int[]{0, 2});
        directions.add(new int[]{0, -2});
        directions.add(new int[]{2, 0});
        directions.add(new int[]{-2, 0});

        Collections.shuffle(directions); // Ensures unique layout variations

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (mazeGrid.isValid(newRow, newCol) && maze[newRow][newCol] == 0) {
                // Break down the wall barrier cell sitting between our positions
                maze[row + direction[0] / 2][col + direction[1] / 2] = 1;
                
                // Recursively jump to the new spot
                generate(newRow, newCol);
            }
        }
    }
}