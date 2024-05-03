package algorithm;
import java.util.ArrayList;
import java.util.List;

import util.Dictionary;
import util.Node;

public abstract class WordLadderSolver {
    protected Dictionary dictionary;
    protected String startWord;
    protected String endWord;
    protected int visitedNodes;

    // Constructor untuk WordLadderSolver
    public WordLadderSolver(Dictionary dictionary, String startWord, String endWord) {
        this.dictionary = dictionary;
        this.startWord = startWord.toLowerCase();
        this.endWord = endWord.toLowerCase();
    }

    // Method untuk menghasilkan kata-kata yang berbeda satu karakter dari kata yang diberikan
    protected List<String> generateNeighbors(String word) {
        List<String> neighbors = new ArrayList<>();
        char[] wordArray = word.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            char originalChar = wordArray[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != originalChar) {
                    wordArray[i] = c;
                    String neighbor = new String(wordArray);
                    if (dictionary.isWord(neighbor)) {
                        neighbors.add(neighbor);
                    }
                }
            }
            wordArray[i] = originalChar;
        }
        return neighbors;
    }  

    // Method untuk mendapatkan jumlah node yang dikunjungi
    public int getVisitedNodes() {
        return visitedNodes;
    }

    // Method untuk merekonstruksi path dari node akhir ke node awal
    public List<String> reconstructPath(Node endNode) {
        List<String> path = new ArrayList<>();
        Node currentNode = endNode;
        while (currentNode != null) {
            path.add(0, currentNode.getWord().toUpperCase()); // Add the word to the beginning of the path
            currentNode = currentNode.getParent();
        }
        return path;
    }

    // Method untuk menghitung nilai g(n) dari node
    public int calculateG(Node node) {
        return node.countCost();
    }

    // Method untuk menghitung nilai h(n) dari node, menggunakan heuristik Hamming distance
    public int calculateH(String word) {
        int distance = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != endWord.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    // Abstract method algoritma penyelesaian
    public abstract List<String> solve();
}
