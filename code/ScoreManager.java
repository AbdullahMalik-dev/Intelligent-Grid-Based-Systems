import java.util.ArrayList;

public class ScoreManager {
    private ArrayList<Double> scores;

    public ScoreManager() {
        scores = new ArrayList<>();
        // Pre-load a few fake times so the leaderboard isn't empty on first run
        scores.add(15.5);
        scores.add(12.3);
        scores.add(20.1);
    }

    public void addScore(double time) {
        scores.add(time);
    }

    // MANDATORY REQUIREMENT: Sorting Algorithm (Bubble Sort)
    private void sortScores() {
        int n = scores.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (scores.get(j) > scores.get(j + 1)) {
                    // Swap the elements
                    double temp = scores.get(j);
                    scores.set(j, scores.get(j + 1));
                    scores.set(j + 1, temp);
                }
            }
        }
    }

    public String getLeaderboard() {
        sortScores(); // Sort before displaying
        
        StringBuilder sb = new StringBuilder("Fastest AI Completion Times:\n\n");
        // Display top 5 times
        for (int i = 0; i < Math.min(5, scores.size()); i++) {
            sb.append((i + 1)).append(". ").append(scores.get(i)).append(" seconds\n");
        }
        return sb.toString();
    }
}