import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Dictionary {
    private Set<String> dictionary;

    public Dictionary(String filePath) {
        dictionary = new HashSet<>();
        loadDictionary(filePath);
    }

    private void loadDictionary(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String word;
            while ((word = br.readLine()) != null) {
                dictionary.add(word.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isWord(String word) {
        return dictionary.contains(word.toLowerCase());
    }
}
