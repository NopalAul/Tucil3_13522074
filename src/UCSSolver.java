import util.*;
import java.util.*;

public class UCSSolver extends WordLadderSolver {
    private PriorityQueue<Node> frontier;

    public UCSSolver(Dictionary dictionary, String startWord, String endWord) {
        super(dictionary, startWord, endWord);
    }

    @Override
    public List<String> solve() {
        // Priority queue for storing nodes to be expanded, ordered by path cost
        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingInt(Node::getFn));
        // Map to keep track of the best known path cost for each node
        Map<String, Integer> pathCostMap = new HashMap<>();
        
        // Add the start node to the frontier
        Node startNode = new Node(startWord, null, 0);
        frontier.add(startNode);
        // Initialize the start node's path cost to 0
        pathCostMap.put(startWord, 0);

        while (!frontier.isEmpty()) {
            // Get the node with the lowest path cost
            Node currentNode = frontier.poll();
            String currentWord = currentNode.getWord();
            int currentCost = currentNode.countCost();

            // If the current node is the goal node, reconstruct and return the path
            if (currentWord.equals(endWord)) {
                return reconstructPath(currentNode);
            }

            // Generate neighbors of the current word
            List<String> neighbors = generateNeighbors(currentWord);
            for (String neighbor : neighbors) {
                int newCost = currentCost + 1;
                // If the neighbor is not in the path cost map or the new cost is better than the previous one
                if (!pathCostMap.containsKey(neighbor) || newCost < pathCostMap.get(neighbor)) {
                    Node neighborNode = new Node(neighbor, currentNode, newCost);
                    frontier.add(neighborNode);
                    pathCostMap.put(neighbor, newCost); // Update the path cost map
                }
            }
        }

        // If the frontier is empty and no solution is found, return an empty list
        return new ArrayList<>();
    }

    // Helper method to reconstruct the path from the end node to the start node
    private List<String> reconstructPath(Node endNode) {
        List<String> path = new ArrayList<>();
        Node currentNode = endNode;
        while (currentNode != null) {
            path.add(0, currentNode.getWord()); // Add the word to the beginning of the path
            currentNode = currentNode.getParent();
        }
        return path;
    }

    // printqueue method
    public void printPriorityQueue() {
        System.out.println("Priority Queue:");
        for (Node node : frontier) {
            System.out.println(node.getWord() + ", fn: " + node.getFn());
        }
    }
}
