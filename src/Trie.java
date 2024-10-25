import java.util.HashSet;

public class Trie {

    HashSet<String> set;

    public Trie() {
        set = new HashSet<>();
    }

    public void insert(String word) {
        set.add(word);
    }

    public boolean search(String word) {
        return set.contains(word);
    }

    public boolean startsWith(String prefix) {
        for (String word : set) {
            if (word.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }
}