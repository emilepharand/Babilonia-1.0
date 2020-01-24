package GUI.Panels.Languages;

import GUI.Factories.ButtonPanelFactory;
import GUI.Factories.LabelFactory;
import GUI.Factories.TextFieldFactory;
import GUI.GUIConstants;

import GUI.Panels.AbstractPanel;

import javax.swing.*;
import java.util.ArrayList;

import static GUI.GUIConstants.DEFAULT_FONT_UNICODE;

public class ModifyLanguagesPanel extends AbstractPanel {

    private static final String TITLE = "Modify Languages";
    private static final String HEADER = "Modify Languages";
    private static final String CURRENT_NAME = "Current Name";
    private static final String NEW_NAME = "New Name";
    private static final String SAVE_BUTTON_TEXT = "Save";
    private static final String CONFIRMATION_MSG = "Changes saved.";

    private ArrayList<Integer> languageIds;
    private ArrayList<JTextField> textFields;

    @Override
    protected void prepare() {
        title = TITLE;
        header = HEADER;
        languageIds = languageManager.getLanguageIds();
        textFields = new ArrayList<>();
        minimumFieldSize = languageManager.getLanguageCount() + 2;
    }

    @Override
    protected void addRows() {
        JLabel labelLeft = LabelFactory.createLabel(CURRENT_NAME);
        JLabel labelRight = LabelFactory.createLabel(NEW_NAME);
        makeAndAddPanelRow(labelLeft, labelRight);
        languageManager.getLanguageIds().forEach(this::addRow);
    }

    private void addRow(int languageId) {
        String language = languageManager.getLanguageName(languageId);
        JTextField newName = TextFieldFactory.createTextField(language);
        JLabel label = LabelFactory.createLabel(language);
        label.setFont(DEFAULT_FONT_UNICODE);
        newName.addActionListener(actionEvent -> defaultAction());
        makeAndAddPanelRow(label, newName);
        textFields.add(newName);
    }

    @Override
    protected void addButtons() {
        JPanel buttonPanel = ButtonPanelFactory.createOneButtonPanel
                (SAVE_BUTTON_TEXT, actionEvent -> defaultAction());
        addButtonPanel(buttonPanel);
    }

    @Override
    protected void defaultAction() {
        for (int i = 0; i < textFields.size(); i++) {
            String oldName = languageManager.getLanguageName(languageIds.get(i));
            if (!textFields.get(i).getText().equals(oldName)
                    && !textFields.get(i).getText().isEmpty()) {
                languageManager.changeLanguageName(languageIds.get(i),
                        textFields.get(i).getText());
            }
        }
        guiManager.showDialog(CONFIRMATION_MSG, "");
        guiManager.showPanel(GUIConstants.PANEL_TYPE.MODIFY_LANGUAGES_PANEL);
    }

}
