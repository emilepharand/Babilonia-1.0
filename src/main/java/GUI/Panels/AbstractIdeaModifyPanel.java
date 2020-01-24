package GUI.Panels;

import GUI.Factories.ComboBoxFactory;
import GUI.Factories.TextFieldFactory;
import GUI.GUIManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import static GUI.GUIConstants.DEFAULT_INCREMENT_FIELDS;

/**
 * Groups attributes and behavior common to
 * <code>NewIdeaPanel</code> and <code>EditIdeaPanel</code>.
 */
public abstract class AbstractIdeaModifyPanel extends AbstractPanel {

    protected static final String IDEA_MINIMUM_TWO_WORDS =
            "An idea must have at least two words.";
    protected static final String IDEA_MINIMUM_TWO_LANGUAGES =
            "An idea must have at least two different languages.";

    protected ArrayList<JComboBox<String>> leftColumn = new ArrayList<>();
    protected ArrayList<JTextField> rightColumn = new ArrayList<>();

    protected String[] languages;

    @Override
    public void prepare() {
        languages = languageManager.getLanguageStrings().toArray(String[]::new);
    }

    /**
     * Adds empty fields to panel.
     */
    protected void addMoreFields() {
        clear();
        minimumFieldSize += DEFAULT_INCREMENT_FIELDS;
        addEmptyFieldsToColumns(DEFAULT_INCREMENT_FIELDS);
        fillPanel();
        finish();
    }

    /**
     * Adds empty fields to left and right columns.
     *
     * @param n number of fields to add
     */
    protected void addEmptyFieldsToColumns(int n) {
        int firstLanguageId = languageManager.getLanguageId(languages[0]);
        for (int i = 0; i < n; i++) {
            addToColumns(languages, firstLanguageId, "");
        }
    }

    /**
     * Adds a word to columns.
     *
     * @param languages  <code>Array</code> of language names
     * @param languageId language id
     * @param word       word
     */
    protected void addToColumns(String[] languages, int languageId, String word) {
        JComboBox<String> comboBox = ComboBoxFactory.createStringComboBox(languages);
        comboBox.setSelectedIndex(languageManager.getLanguageOrder(languageId));
        JTextField textField = TextFieldFactory.createTextField(word);
        textField.addActionListener(actionEvent -> defaultAction());
        leftColumn.add(comboBox);
        rightColumn.add(textField);
    }

    /**
     * Takes right and left columns and adds their content to panel.
     */
    protected void addRowsFromColumns() {
        for (int i = 0; i < leftColumn.size(); i++)
            makeAndAddPanelRow(leftColumn.get(i), rightColumn.get(i));
    }

    /**
     * Fetches the information in panel and creates an idea from it.
     *
     * @param ideaId idea id
     */
    protected void createIdea(int ideaId) {
        for (int i = 0; i < leftColumn.size(); i++) {
            if (!rightColumn.get(i).getText().isEmpty()) {
                String languageName = (String) leftColumn.get(i).getSelectedItem();
                int languageId = languageManager.getLanguageId(languageName);
                String word = rightColumn.get(i).getText();
                ideaManager.addWordFromGUI(ideaId, languageId, word);
            }
        }
    }

    /**
     * Checks if an idea is valid, i.e. whether it has at least two words and
     * whether it has at least two different languages.
     *
     * @return <code>true</code> if valid, <code>false</code> otherwise
     */
    protected boolean checkIdeaValidity() {
        boolean isValid = true;
        Set<String> distinctLanguages = new TreeSet<>();
        for (int i = 0; i < leftColumn.size(); i++) {
            if (!rightColumn.get(i).getText().isEmpty())
                distinctLanguages.add((String) leftColumn.get(i).getSelectedItem());
        }
        if (rightColumn.stream().filter(textField ->
                !textField.getText().isEmpty()).count() < 2) {
            guiManager.showDialog(IDEA_MINIMUM_TWO_WORDS, "");
            isValid = false;
        } else if (distinctLanguages.size() < 2) {
            guiManager.showDialog(IDEA_MINIMUM_TWO_LANGUAGES, "");
            isValid = false;
        }
        return isValid;
    }

}
