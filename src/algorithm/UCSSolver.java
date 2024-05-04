package algorithm;
import java.util.*;

import util.Dictionary;
import util.Node;

public class UCSSolver extends WordLadderSolver {
    private PriorityQueue<Node> simpulHidup;

    // Constructor untuk UCSSolver
    public UCSSolver(Dictionary dictionary, String startWord, String endWord) {
        super(dictionary, startWord, endWord);
    }

    @Override
    public List<String> solve() {
        // Priority queue untuk menyimpan node yang akan diekspan, diurutkan berdasarkan nilai fn
        simpulHidup = new PriorityQueue<>(Comparator.comparingInt(Node::getFn));
        // Set untuk melacak node yang sudah dikunjungi
        Set<String> explored = new HashSet<>();
        
        Node startNode = new Node(startWord, null, 0);
        simpulHidup.add(startNode);

        while (!simpulHidup.isEmpty()) {
            Node currentNode = simpulHidup.poll();
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
                    int fn = calculateG(currentNode); // skor f(n) adalah g(n) pada UCS
                    Node neighborNode = new Node(neighbor, currentNode, fn);
                    simpulHidup.add(neighborNode);
                }
            }
        }

        // Jika simpulHidup kosong dan tidak ditemukan solusi, kembalikan list kosong
        return new ArrayList<>();
    }
}
