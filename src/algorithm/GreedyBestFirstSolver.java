package algorithm;
import java.util.*;

import util.Dictionary;
import util.Node;

public class GreedyBestFirstSolver extends WordLadderSolver {
    private PriorityQueue<Node> frontier;

    // Constructor untuk GreedyBestFirstSolver
    public GreedyBestFirstSolver(Dictionary dictionary, String startWord, String endWord) {
        super(dictionary, startWord, endWord);
    }

    @Override
    public List<String> solve() {
        // Jika kata bukan kata valid, kembalikan list berisi "Invalid"
        if (!dictionary.isWord(startWord) || !dictionary.isWord(endWord)) {
            return new ArrayList<>(Collections.singletonList("Invalid"));
        }

        // Priority queue untuk menyimpan node yang akan diekspan, diurutkan berdasarkan nilai fn
        frontier = new PriorityQueue<>(Comparator.comparingInt(Node::getFn));
        // Set untuk melacak node yang sudah dikunjungi
        Set<String> explored = new HashSet<>();

        Node startNode = new Node(startWord, null, 0);
        frontier.add(startNode);

        while (!frontier.isEmpty()) {
            Node currentNode = frontier.poll();
            String currentWord = currentNode.getWord();

            visitedNodes++;

            // Jika node saat ini adalah node tujuan, pencarian selesai
            if (currentWord.equals(endWord)) {
                return reconstructPath(currentNode);
            }

            explored.add(currentWord);

            // Generate tetangga dari kata saat ini
            List<String> neighbors = generateNeighbors(currentWord);
            for (String neighbor : neighbors) {
                if (!explored.contains(neighbor)) {
                    visitedNodes++;
                    int fn = calculateH(neighbor); // skor f(n) adalah h(n) pada Greedy Best First Search
                    Node neighborNode = new Node(neighbor, currentNode, fn);
                    frontier.add(neighborNode);
                }
            }
        }

        // Jika frontier kosong dan tidak ditemukan solusi, kembalikan list kosong
        return new ArrayList<>();
    }

    
}
