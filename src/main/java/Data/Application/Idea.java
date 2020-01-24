package Data.Application;

import java.util.ArrayList;

/**
 * Represents one idea.
 * Known only by IdeaManager.
 */

public class Idea {

    private int id;
    private ArrayList<Word> words;

    Idea(int id) {
        this.id = id;
        words = new ArrayList<>();
    }

    void addWord(Word word) {
        words.add(word);
    }

    ArrayList<Word> getWords() {
        return words;
    }

    boolean containsLanguage(int languageId) {
        return words.stream()
                .anyMatch(word -> word.getLanguageId() == languageId);
    }

    int getId() {
        return id;
    }

}
