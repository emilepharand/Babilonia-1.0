package GUI.Panels.Languages;

import GUI.Factories.ButtonPanelFactory;
import GUI.Factories.TextFieldFactory;
import GUI.GUIManager;
import GUI.Panels.AbstractPanel;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.util.ArrayList;

import static GUI.GUIConstants.*;

public class AddLanguagesPanel extends AbstractPanel {

    private static final String TITLE = "Add Languages";
    private static final String HEADER = "Add Languages";
    private static final String SAVE_BUTTON_TEXT = "Save";
    private static final String CONFIRMATION_MSG = "New languages have been added.";
    private static final String NO_IDEA_MSG =
            "Don't forget to add some ideas so you can start practicing! :)";

    private ArrayList<JTextField> textFields = new ArrayList<>();

    @Override
    protected void prepare() {
        title = TITLE;
        header = HEADER;
        textFields = new ArrayList<>();
        minimumFieldSize = DEFAULT_NUMBER_OF_FIELDS;
    }

    @Override
    protected void addRows() {
        for (int i = 0; i < DEFAULT_NUMBER_OF_FIELDS; i++) {
            JTextField textField = TextFieldFactory.createTextField("");
            makeAndAddPanelRow(textField);
            textFields.add(textField);
            textField.addActionListener(actionEvent -> defaultAction());
        }
    }

    @Override
    protected void addButtons() {
        JPanel buttonPanel = ButtonPanelFactory.createOneButtonPanel
                (SAVE_BUTTON_TEXT, actionEvent -> defaultAction());
        addButtonPanel(buttonPanel);
    }

    @Override
    protected void defaultAction() {
        save();
    }

    private void save() {
        textFields.stream().map(JTextComponent::getText)
                .filter(text -> !text.isEmpty()).
                forEach(language -> languageManager.createLanguageFromGUI(language));
        guiManager.showDialog(CONFIRMATION_MSG, "");
        if (ideaManager.getIdeaCount() == 0) {
            guiManager.showDialog(NO_IDEA_MSG, "");
            guiManager.showPanel(PANEL_TYPE.NEW_IDEA_PANEL);
        } else {
            guiManager.showPanel(PANEL_TYPE.ADD_LANGUAGES_PANEL);
        }
    }

}
