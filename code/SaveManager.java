import java.io.*;
import java.util.Scanner;
import javax.swing.JPanel;

public class SaveManager {
    private MazeGrid mazeGrid;
    private final String FILE_NAME = "maze_save.txt";

    public SaveManager(MazeGrid mazeGrid) {
        this.mazeGrid = mazeGrid;
    }

    public void saveMaze() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            int[][] maze = mazeGrid.getMaze();
            for (int r = 0; r < mazeGrid.getRows(); r++) {
                for (int c = 0; c < mazeGrid.getCols(); c++) {
                    writer.print(maze[r][c] + " ");
                }
                writer.println(); // New line for each row
            }
        } catch (IOException e) {
            System.out.println("Error saving maze: " + e.getMessage());
        }
    }

    public void loadMaze(JPanel panel) {
        try (Scanner scanner = new Scanner(new File(FILE_NAME))) {
            int[][] maze = mazeGrid.getMaze();
            for (int r = 0; r < mazeGrid.getRows(); r++) {
                for (int c = 0; c < mazeGrid.getCols(); c++) {
                    if (scanner.hasNextInt()) {
                        maze[r][c] = scanner.nextInt();
                    }
                }
            }
            panel.repaint(); // Force the screen to draw the newly loaded grid
        } catch (FileNotFoundException e) {
            System.out.println("No save file found.");
        }
    }
}