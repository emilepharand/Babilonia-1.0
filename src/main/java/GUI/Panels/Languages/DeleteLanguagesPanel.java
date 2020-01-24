package GUI.Panels.Languages;

import GUI.Factories.ButtonPanelFactory;
import GUI.Factories.LabelFactory;
import GUI.GUIConstants;
import GUI.GUIManager;
import GUI.Panels.AbstractPanel;

import javax.swing.*;
import java.util.ArrayList;

import static GUI.GUIConstants.DEFAULT_FONT_UNICODE;

public class DeleteLanguagesPanel extends AbstractPanel {

    private static final String TITLE = "Delete Languages";
    private static final String HEADER = "Delete Languages";
    private static final String DELETE_BUTTON_TEXT = "Delete";

    private static final String WARNING_MSG_1 =
            "<html>Warning! You are about to delete language <i>";
    private static final String WARNING_MSG_2 =
            "</i><br>which will also delete all words in that language.<br>"
                    + "This action cannot be undone!<br>Are you sure?</html>";
    private static final String WARNING_MSG_TITLE = "Are you sure?";

    private ArrayList<String> languagesNames;
    private ArrayList<Integer> languageIds;

    @Override
    protected void prepare() {
        title = TITLE;
        header = HEADER;
        languageIds = languageManager.getLanguageIds();
        languagesNames = languageManager.getLanguageStrings();
        minimumFieldSize = languageManager.getLanguageCount();
    }

    @Override
    protected void addRows() {
        for (int i = 0; i < languagesNames.size(); i++) {
            addRow(i, languagesNames.get(i));
        }
    }

    private void addRow(int n, String languageName) {
        JLabel languageNameLabel = LabelFactory.createLabel(languageName);
        languageNameLabel.setFont(DEFAULT_FONT_UNICODE);
        JPanel buttonPanel = ButtonPanelFactory.createOneButtonPanel
                (DELETE_BUTTON_TEXT,
                        actionEvent -> deleteLanguage(languageName, languageIds.get(n)));
        makeAndAddPanelRow(languageNameLabel, buttonPanel);
    }

    protected void addButtons() {
        // no buttons for this panel
    }

    @Override
    protected void defaultAction() {
        // no default action for this panel
    }

    /**
     * Deletes a language and all associated words.
     *
     * @param name language name
     * @param id   language id
     */
    private void deleteLanguage(String name, int id) {
        if (guiManager.showConfirm(
                WARNING_MSG_1 + name + WARNING_MSG_2, WARNING_MSG_TITLE)) {
            languageManager.deleteLanguage(id);
            guiManager.showDialog("Language successfully deleted.", "");
            guiManager.showPanel(GUIConstants.PANEL_TYPE.DELETE_LANGUAGES_PANEL);
        }
    }

}
