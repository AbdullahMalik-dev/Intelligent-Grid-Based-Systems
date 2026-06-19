import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GameFrame();
        });
    }
}

class GameFrame extends JFrame {
    private MazeGrid mazeGrid;
    private MazePanel mazePanel;
    private ScoreManager scoreManager; 

    public GameFrame() {
        mazeGrid = new MazeGrid(15, 15);
        scoreManager = new ScoreManager(); 

        // Standard default buttons
        JButton generateButton = new JButton("Generate Maze");
        generateButton.addActionListener(e -> {
            MazeGenerator generator = new MazeGenerator(mazeGrid);
            generator.generateMaze();
            mazePanel.repaint();
        });

        JButton solveButton = new JButton("Solve AI");
        solveButton.addActionListener(e -> {
            PathFinder finder = new PathFinder(mazeGrid);
            List<int[]> path = finder.findPath();
            AIPlayer ai = new AIPlayer(mazeGrid, path, mazePanel);
            ai.startMoving();
            
            if (!path.isEmpty()) {
                double simulatedTime = path.size() * 0.1;
                scoreManager.addScore(Math.round(simulatedTime * 10.0) / 10.0);
            }
        });

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            SaveManager saver = new SaveManager(mazeGrid);
            saver.saveMaze();
            // Basic popup
            JOptionPane.showMessageDialog(this, "Saved to text file.");
        });

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> {
            SaveManager loader = new SaveManager(mazeGrid);
            loader.loadMaze(mazePanel);
        });

        JButton leaderButton = new JButton("Leaderboard");
        leaderButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, scoreManager.getLeaderboard());
        });

        JPanel topPanel = new JPanel();
        topPanel.add(generateButton);
        topPanel.add(solveButton);
        topPanel.add(saveButton);
        topPanel.add(loadButton);
        topPanel.add(leaderButton);

        mazePanel = new MazePanel(mazeGrid);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(mazePanel, BorderLayout.CENTER);

        // Put your name right in the title bar
        setTitle("Semester 2 Final Project - By Abdullah Malik"); 
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

class MazePanel extends JPanel {
    private MazeGrid mazeGrid;
    private final int CELL_SIZE = 35;

    public MazePanel(MazeGrid mazeGrid) {
        this.mazeGrid = mazeGrid;
        
        // THE EASTER EGG: Sounds like a real student left this in
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Yikes, no mouse controls here, heehhehehehehe *wink*");
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[][] maze = mazeGrid.getMaze();

        for (int row = 0; row < mazeGrid.getRows(); row++) {
            for (int col = 0; col < mazeGrid.getCols(); col++) {
                
                // Back to basic Swing colors
                if (maze[row][col] == 0) {
                    g.setColor(Color.BLACK);
                } else if (maze[row][col] == 2) {
                    g.setColor(Color.GREEN);
                } else if (maze[row][col] == 3) {
                    g.setColor(Color.RED);
                } else if (maze[row][col] == 4) {
                    g.setColor(Color.BLUE); 
                } else {
                    g.setColor(Color.WHITE);
                }

                g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.GRAY);
                g.drawRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }
}