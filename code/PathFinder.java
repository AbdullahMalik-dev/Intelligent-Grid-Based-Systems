import java.util.*;

public class PathFinder {
    private MazeGrid mazeGrid;

    public PathFinder(MazeGrid mazeGrid) {
        this.mazeGrid = mazeGrid;
    }

    public List<int[]> findPath() {
        int[][] maze = mazeGrid.getMaze();
        int startRow = -1, startCol = -1;

        //  Locate the starting position (Green cell)
        for (int r = 0; r < mazeGrid.getRows(); r++) {
            for (int c = 0; c < mazeGrid.getCols(); c++) {
                if (maze[r][c] == 2) {
                    startRow = r; 
                    startCol = c;
                }
            }
        }

        //  Set up BFS Queue and Visited array
        Queue<Node> queue = new LinkedList<>();
        boolean[][] visited = new boolean[mazeGrid.getRows()][mazeGrid.getCols()];

        queue.add(new Node(startRow, startCol, null));
        visited[startRow][startCol] = true;

        // Up, Down, Right, Left
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //  Execute BFS
        while (!queue.isEmpty()) {
            Node current = queue.poll();

            // If we reached the exit (3), trace back the path
            if (maze[current.row][current.col] == 3) {
                return buildPath(current);
            }

            // Check all 4 adjacent cells
            for (int[] dir : directions) {
                int newRow = current.row + dir[0];
                int newCol = current.col + dir[1];

                // If valid, unvisited, and NOT a wall (0)
                if (mazeGrid.isValid(newRow, newCol) && !visited[newRow][newCol] && maze[newRow][newCol] != 0) {
                    visited[newRow][newCol] = true;
                    queue.add(new Node(newRow, newCol, current));
                }
            }
        }
        return new ArrayList<>(); // Return empty if no path exists
    }

    // Helper method to walk backwards from the exit to the start
    private List<int[]> buildPath(Node endNode) {
        List<int[]> path = new ArrayList<>();
        Node current = endNode;
        while (current != null) {
            path.add(new int[]{current.row, current.col});
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }
}

// Inner class to track parent nodes for path reconstruction
class Node {
    int row, col;
    Node parent;

    public Node(int row, int col, Node parent) {
        this.row = row;
        this.col = col;
        this.parent = parent;
    }
}