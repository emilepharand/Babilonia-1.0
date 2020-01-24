package GUI.Panels.File;

import GUI.Factories.*;
import GUI.GUIConstants;
import GUI.Panels.AbstractPanel;
import GUI.WordEvents.WordEvents;

import javax.swing.*;
import java.util.ArrayList;

public class PracticePanel extends AbstractPanel {

    private static final String TITLE = "Practice";
    private static final String HEADER = "Practice";
    private static final String HINT_BUTTON_TEXT_1 = "Hint";
    private static final String FULL_BUTTON_TEXT = "Full";
    private static final String NEXT_BUTTON_TEXT = "Next";

    private ArrayList<JLabel> leftColumn = new ArrayList<>();
    private ArrayList<JTextField> rightColumn = new ArrayList<>();
    private ArrayList<JButton> hintButtons = new ArrayList<>();
    private ArrayList<JButton> fullButtons = new ArrayList<>();

    private JButton nextButton;
    private ArrayList<Integer> wordIds;

    public void prepare() {
        title = TITLE;
        header = HEADER;
        wordIds = ideaManager.getWordIdsForIdea(ideaManager.getCurrentIdeaId());
        minimumFieldSize = wordIds.size();
    }

    @Override
    protected void addRows() {
        for (Integer wordId : wordIds) addRow(wordId);
        addWordEvents();
        findFocusTextField();
    }

    private void addRow(int wordId) {
        String language = ideaManager.getLanguageStringForWord(wordId);
        String word = ideaManager.getFullWord(wordId);
        JLabel label = LabelFactory.createLabel(language);
        label.setFont(GUIConstants.DEFAULT_FONT_UNICODE);
        JTextField textField = TextFieldFactory.createTextField(word);
        JPanel helpPanel = createHelpPanel();
        makeAndAddPanelRow(label, textField, helpPanel);
        addToColumns(label, textField);
    }

    private JPanel createHelpPanel() {
        JPanel helpPanel = PanelFactory.createTwoColumnPanel();
        JButton hintButton = ButtonFactory.createButton(HINT_BUTTON_TEXT_1);
        JButton fullButton = ButtonFactory.createButton(FULL_BUTTON_TEXT);
        helpPanel.add(hintButton);
        helpPanel.add(fullButton);
        hintButtons.add(hintButton);
        fullButtons.add(fullButton);
        return helpPanel;
    }

    private void addToColumns(JLabel label, JTextField textField) {
        leftColumn.add(label);
        rightColumn.add(textField);
    }

    private void addWordEvents() {
        rightColumn.forEach(textField -> textField.setEditable(false));
        ArrayList<JTextField> textFields = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < leftColumn.size(); i++) {
            int languageId = ideaManager.getLanguageIdForWord(wordIds.get(i));
            if (languageManager.isLanguagePractice(languageId))
                addWordEvent(i, textFields, count++);
        }
    }

    private void addWordEvent(int fieldIndex, ArrayList<JTextField> eventFields,
                              int eventFieldIndex) {
        JTextField textField = rightColumn.get(fieldIndex);
        eventFields.add(textField);
        ArrayList<String> spellings = ideaManager.getSpellings(wordIds.get(fieldIndex));
        String word = ideaManager.getFullWord(wordIds.get(fieldIndex));
        WordEvents wordEvents = new WordEvents(this, eventFields,
                eventFieldIndex, spellings, word);
        fullButtons.get(fieldIndex)
                .addActionListener(actionEvent -> wordEvents.showWord());
        hintButtons.get(fieldIndex)
                .addActionListener(actionEvent -> wordEvents.showHint());
    }

    /**
     * Looks for <code>JTextField</code> that must be focused.
     * That is the first one that is empty.
     */
    private void findFocusTextField() {
        int i = 0;
        while (i < rightColumn.size() && !rightColumn.get(i).getText().isEmpty())
            i++;
        if (i < rightColumn.size())
            SwingUtilities.invokeLater(rightColumn.get(i)::requestFocusInWindow);
    }

    protected void addButtons() {
        JPanel twoButtonPanel = PanelFactory.createOneColumnPanel();
        JButton nextButton = ButtonFactory.createButton(NEXT_BUTTON_TEXT);
        nextButton.addActionListener(actionEvent ->
                guiManager.showPanel(GUIConstants.PANEL_TYPE.PRACTICE_PANEL));
        twoButtonPanel.add(nextButton);
        this.nextButton = nextButton;
        addButtonPanel(twoButtonPanel);
    }

    @Override
    protected void defaultAction() {
        // no default action for this panel
    }

    public JButton getNextButton() {
        return nextButton;
    }

}
