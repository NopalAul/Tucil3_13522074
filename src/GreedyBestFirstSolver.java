import util.*;
import java.util.*;

public class GreedyBestFirstSolver extends WordLadderSolver {
    private PriorityQueue<Node> priorityQueue;

    public GreedyBestFirstSolver(Dictionary dictionary, String startWord, String endWord) {
        super(dictionary, startWord, endWord);
        priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::getFn));
    }

    @Override
    public List<String> solve() {
        Set<String> visited = new HashSet<>();
        List<String> path = new ArrayList<>();
        
        Node startNode = new Node(startWord, null, heuristic(startWord));
        priorityQueue.offer(startNode);

        while (!priorityQueue.isEmpty()) {
            Node current = priorityQueue.poll();
            visited.add(current.getWord());
            if (current.getWord().equals(endWord)) {
                // Reconstruct the path
                while (current != null) {
                    path.add(0, current.getWord());
                    current = current.getParent();
                }
                return path;
            }
            expandNode(current, visited);
        }
        return null; // No path found
    }

    private void expandNode(Node node, Set<String> visited) {
        List<String> neighbors = generateNeighbors(node.getWord());
        for (String neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                int fn = heuristic(neighbor);
                Node newNode = new Node(neighbor, node, fn);
                priorityQueue.offer(newNode);
            }
        }
    }

    private int heuristic(String word) {
        // Placeholder heuristic function, e.g., Hamming distance
        int distance = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != endWord.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }
}
