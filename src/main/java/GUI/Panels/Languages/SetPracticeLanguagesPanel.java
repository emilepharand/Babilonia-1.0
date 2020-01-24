package GUI.Panels.Languages;

import GUI.Factories.ButtonPanelFactory;
import GUI.Factories.CheckBoxFactory;
import GUI.GUIConstants;
import GUI.GUIManager;
import GUI.Panels.AbstractPanel;

import javax.swing.*;
import java.util.ArrayList;

public class SetPracticeLanguagesPanel extends AbstractPanel {

    private static final String TITLE = "Set Practice Languages";
    private static final String HEADER = "Set Practice Languages";
    private static final String SAVE_BUTTON_TEXT = "Save";
    private static final String ONE_LANGUAGE_NOT_PRACTICE_MSG =
            "<html>You need to choose at least one language that is not practice.<br><br>" +
            "Otherwise all words will be hidden!</html>";

    private ArrayList<JCheckBox> checkBoxes;

    @Override
    public void prepare() {
        title = TITLE;
        header = HEADER;
        minimumFieldSize = languageManager.getLanguageCount();
    }

    @Override
    protected void addRows() {
        checkBoxes = new ArrayList<>();
        languageManager.getLanguageIds().forEach(this::addRow);
    }

    private void addRow(int languageId) {
        String language = languageManager.getLanguageName(languageId);
        JCheckBox checkBox = CheckBoxFactory.createCheckBox(language);
        if (languageManager.isLanguagePractice(languageId)) checkBox.setSelected(true);
        makeAndAddPanelRow(checkBox);
        checkBoxes.add(checkBox);
    }

    protected void addButtons() {
        JPanel buttonPanel = ButtonPanelFactory.createOneButtonPanel
                (SAVE_BUTTON_TEXT, actionEvent -> defaultAction());
        addButtonPanel(buttonPanel);
    }

    @Override
    protected void defaultAction() {
        if (!checkAllSelected()) {
            checkBoxes.forEach(checkBox -> languageManager
                    .setPractice(languageManager.getLanguageId
                            (checkBox.getText()), checkBox.isSelected()));
            guiManager.showPanel(GUIConstants.PANEL_TYPE.SET_PRACTICE_LANGUAGES_PANEL);
        } else {
            guiManager.showDialog(ONE_LANGUAGE_NOT_PRACTICE_MSG, "");
        }
    }

    private boolean checkAllSelected() {
        return checkBoxes.stream().allMatch(JCheckBox::isSelected);
    }

}
