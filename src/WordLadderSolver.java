import java.util.ArrayList;
import java.util.List;

public abstract class WordLadderSolver {
    protected Dictionary dictionary;
    protected String startWord;
    protected String endWord;
    protected int visitedNodes;

    public WordLadderSolver(Dictionary dictionary, String startWord, String endWord) {
        this.dictionary = dictionary;
        this.startWord = startWord.toLowerCase();
        this.endWord = endWord.toLowerCase();
    }

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

    public int getVisitedNodes() {
        return visitedNodes;
    }

    public abstract List<String> solve();
}
