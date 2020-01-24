package GUI.WordEvents;

import GUI.Panels.File.PracticePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static GUI.WordEvents.WordEvents.TEXT_STATUS.*;

/**
 * Manages live word events while practicing ideas.
 * Listens to and updates <code>JTextField</code>s
 * when user types or presses help buttons.
 */
public class WordEvents {

    protected static final Color COLOR_OK = new Color(0, 128, 0);
    protected static final Color COLOR_NOT_OK = Color.RED;

    protected enum TEXT_STATUS {
        NO_MATCH, PARTIAL_MATCH, FULL_MATCH
    }

    private ArrayList<JTextField> wordEventFields;
    private int fieldNumber;
    private String wordToShow;
    private PracticePanel practicePanel;
    protected boolean enabled;
    protected JTextField textField;
    protected ArrayList<String> spellings;
    private int hintSpellingId;

    /**
     * Sets and activates word events for specified field.
     *
     * @param practicePanel associated <code>PracticePanel</code>
     * @param eventFields   <code>JTextField</code>s with <code>WordEvents</code>
     * @param fieldIndex    index of the current <code>JTextField</code>
     * @param spellings     spellings of the word
     * @param fullWord      full word to show when event is disabled
     */
    public WordEvents(PracticePanel practicePanel, ArrayList<JTextField> eventFields,
                      int fieldIndex, ArrayList<String> spellings, String fullWord) {
        this.practicePanel = practicePanel;
        hintSpellingId = 0;
        extractFieldInfo(eventFields, fieldIndex);
        extractWordInfo(spellings, fullWord);
        addDocumentListener(this::colorTextOrChangeFocus);
        activate();
    }

    public WordEvents() {

    }

    private void extractFieldInfo(ArrayList<JTextField> allFields, int fieldNumber) {
        this.wordEventFields = allFields;
        this.fieldNumber = fieldNumber;
        this.textField = allFields.get(fieldNumber);
    }

    private void extractWordInfo(ArrayList<String> acceptedSpellings, String wordToShow) {
        this.spellings = acceptedSpellings;
        this.wordToShow = wordToShow;
    }

    private void addDocumentListener(Runnable runnable) {
        textField.getDocument().addDocumentListener(new WordEventsListener(runnable));
    }

    private void activate() {
        enabled = true;
        textField.setText("");
        textField.setEditable(true);
    }

    /**
     * Shows full word.
     */
    public void showWord() {
        if (enabled) {
            enabled = false;
            textField.setText(wordToShow);
            fullMatchAttained();
        }
    }

    /**
     * Shows hint (one letter).
     */
    public void showHint() {
        if (enabled) {
            String typedWord = textField.getText();
            textField.setText(addNextLetter(spellings, typedWord));
            SwingUtilities.invokeLater(textField::requestFocusInWindow);
        }
    }

    /**
     * Adds one letter to typed text, according to specific spelling.
     * If no letter typed, loops around different spellings.
     *
     * @param spellings spellings
     * @param typedWord typed word
     * @return spelling with one more letter
     */
    private String addNextLetter(ArrayList<String> spellings, String typedWord) {
        int i = 0;
        if (typedWord.length() == 0)  {
            if (hintSpellingId == spellings.size()) hintSpellingId = 0;
            return spellings.get(hintSpellingId++).substring(0, 1);
        }
        while (i < spellings.size()) {
            String currentSpelling = spellings.get(i);
            int j = 0;
            while (j < typedWord.length() &&
                    currentSpelling.charAt(j) == typedWord.charAt(j))
                j++;
            if (j > 0) return currentSpelling.substring(0, j + 1);
            i++;
        }
        return spellings.get(0).substring(0, 1);
    }

    /**
     * Makes necessary checks to see what color to give <code>textField</code>.
     * If full match, changes focus.
     */
    private void colorTextOrChangeFocus() {
        if (enabled) {
            switch (checkText()) {
                case NO_MATCH:
                    textField.setForeground(COLOR_NOT_OK);
                    break;
                case PARTIAL_MATCH:
                    textField.setForeground(COLOR_OK);
                    break;
                case FULL_MATCH:
                    fullMatchAttained();
                    break;
            }
        }
    }

    protected TEXT_STATUS checkText() {
        String typedWord = textField.getText();
        int i = 0;
        while (i < spellings.size()) {
            boolean firstLettersMatch = checkFirstLettersMatch
                    (spellings.get(i), typedWord);
            if (firstLettersMatch) {
                if (typedWord.length() == spellings.get(i).length())
                    return FULL_MATCH;
                else return PARTIAL_MATCH;
            }
            i++;
        }
        return NO_MATCH;
    }

    private boolean checkFirstLettersMatch(String word, String typedWord) {
        int i = 0;
        while (i < typedWord.length()) {
            if (word.charAt(i) == typedWord.charAt(i)) i++;
            else return false;
        }
        return true;
    }

    private void fullMatchAttained() {
        enabled = false;
        textField.setText(wordToShow);
        textField.setEnabled(false);
        textField.setDisabledTextColor(COLOR_OK);
        changeFocus();
    }

    /**
     * Changes focus to next <code>JTextField</code>
     * or next button if it's the last one.
     */
    private void changeFocus() {
        if (wordEventFields.size() > fieldNumber + 1) {
            wordEventFields.get(fieldNumber + 1).requestFocusInWindow();
        } else {
            SwingUtilities.invokeLater
                    (practicePanel.getNextButton()::requestFocusInWindow);
        }
    }

}
