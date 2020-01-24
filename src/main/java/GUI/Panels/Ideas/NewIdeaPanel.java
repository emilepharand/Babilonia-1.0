package GUI.Panels.Ideas;

import GUI.Factories.ButtonPanelFactory;

import GUI.Panels.AbstractIdeaModifyPanel;

import javax.swing.*;

import java.util.ArrayList;

import static GUI.GUIConstants.*;

public class NewIdeaPanel extends AbstractIdeaModifyPanel {

    private static final String TITLE = "New Idea";
    private static final String HEADER = "New Idea";
    private static final String MORE_BUTTON_TEXT = "More";
    private static final String SAVE_BUTTON_TEXT = "Save";

    // which languages were previously selected
    public static ArrayList<Integer> languageContext = new ArrayList<>();

    public void prepare() {
        title = TITLE;
        header = HEADER;
        minimumFieldSize = DEFAULT_NUMBER_OF_FIELDS;
        super.prepare();
        addEmptyFieldsToColumns(DEFAULT_NUMBER_OF_FIELDS);
    }

    @Override
    protected void addRows() {
        addRowsFromColumns();
        loadLanguageContext();
    }

    /**
     * Loads language context, i.e. which languages were selected
     * the last time a new idea was added.
     */
    private void loadLanguageContext() {
        for (int i = 0; i < languageContext.size() && i < leftColumn.size(); i++) {
            leftColumn.get(i).setSelectedIndex(languageContext.get(i));
        }
    }

    protected void addButtons() {
        JPanel buttonPanel = ButtonPanelFactory.createTwoButtonPanel(
                MORE_BUTTON_TEXT, actionEvent -> addMoreFields(),
                SAVE_BUTTON_TEXT, actionEvent -> defaultAction());
        addButtonPanel(buttonPanel);
    }

    @Override
    protected void defaultAction() {
        if (checkIdeaValidity()) {
            saveIdea();
            guiManager.showPanel(PANEL_TYPE.NEW_IDEA_PANEL);
        }
    }

    private void saveIdea() {
        saveLanguageContext();
        int ideaId = ideaManager.createIdea();
        createIdea(ideaId);
    }

    /**
     * Saves language context, i.e. which languages are currently selected.
     */
    private void saveLanguageContext() {
        languageContext = new ArrayList<>();
        leftColumn.forEach(stringJComboBox ->
                languageContext.add(stringJComboBox.getSelectedIndex()));
    }

}
