package Data.Application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Word {

    private int id;
    private int languageId;
    private String word;

    Word(int id, String word, int languageId) {
        this.id = id;
        this.word = word;
        this.languageId = languageId;
    }

    int getLanguageId() {
        return languageId;
    }

    int getId() {
        return id;
    }

    /**
     * Returns first word spelling, including parentheses.
     *
     * @return first word spelling, including parentheses
     */
    String getWordMainSpelling() {
        ArrayList<String> acceptedWords =
                new ArrayList<>(Arrays.asList(word.split("/", 0)));
        acceptedWords.replaceAll(String::trim);
        return acceptedWords.get(0);
    }

    /**
     * Returns full word, including slashes and parentheses.
     *
     * @return full word
     */
    String getFullWord() {
        return word;
    }

    /**
     * Parses word and returns word spellings.
     * Spellings are separated by slashes (/)
     * and text in parentheses can be ignored
     * or included.
     */
    ArrayList<String> getSpellings() {
        String wordWithoutParentheses = word
                .replaceAll("\\((.+?)\\)", "");
        ArrayList<String> spellings = new ArrayList<>(
                Arrays.asList(wordWithoutParentheses.split("/", 0)));
        spellings.addAll(Arrays.asList(word.replaceAll("[(|)]", "")
                .split("/", 0)));
        spellings.replaceAll(String::trim);
        spellings = spellings.stream().distinct()
                .collect(Collectors.toCollection(ArrayList::new));
        return spellings;
    }

}
