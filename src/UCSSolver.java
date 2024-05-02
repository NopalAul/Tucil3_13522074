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
        frontier = new PriorityQueue<>(Comparator.comparingInt(Node::getFn));
        // Set to keep track of visited nodes
        Set<String> explored = new HashSet<>();
        
        // Add the start node to the frontier
        Node startNode = new Node(startWord, null, 0);
        frontier.add(startNode);

        printPriorityQueue();

        while (!frontier.isEmpty()) {
            // printPriorityQueue();

            // Get the node with the lowest path cost
            Node currentNode = frontier.poll();
            String currentWord = currentNode.getWord();

            visitedNodes++;
            
            // If the current node is the goal node, reconstruct and return the path
            if (currentWord.equals(endWord)) {
                return reconstructPath(currentNode);
            }
            
            // Add current node to explored set
            explored.add(currentWord);

            // Generate neighbors of the current word
            List<String> neighbors = generateNeighbors(currentWord);
            for (String neighbor : neighbors) {
                if (!explored.contains(neighbor)) {
                    visitedNodes++;
                    int newFn = currentNode.countCost() + 1; // Increment the path cost
                    Node neighborNode = new Node(neighbor, currentNode, newFn);
                    frontier.add(neighborNode);
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

    // printPrioqueue method
    public void printPriorityQueue() {
        System.out.println("Priority Queue:");
        for (Node node : frontier) {
            System.out.println(node.getWord() + ", fn: " + node.getFn());
        }
    }
}
