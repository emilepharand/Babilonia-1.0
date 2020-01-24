package GUI.Panels.File;

import GUI.Factories.*;
import GUI.GUIConstants;

import GUI.Panels.AbstractPanel;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;


public class DashboardPanel extends AbstractPanel {

    private static final String TITLE = "Dashboard";
    private static final String HEADER_1 = "Welcome back, ";
    private static final String HEADER_2 = "!";
    private static final String SUB_HEADER = "You can express...";
    private static final String ROW_TEXT_1 = "<html><span style='font-size:20px;'>";
    private static final String ROW_TEXT_2 =
            "</span><span style='font-weight:normal;'> ideas in </span><span style='color:#000000;'>";
    private static final String ROW_TEXT_3 = "</span></html>";
    private static final String BUTTON_TEXT = "Awesome! Click here to practice them :)";

    @Override
    public void prepare() {
        title = TITLE;
        header = HEADER_1 + settingsManager.getUsername() + HEADER_2;
        minimumFieldSize = languageManager.getLanguageCount() + 1;
    }

    @Override
    protected void addRows() {
        JLabel label = LabelFactory.createLabel(SUB_HEADER);
        label.setForeground(Color.BLACK);
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        makeAndAddPanelRow(label);
        languageManager.getLanguageIds().forEach(this::addRow);
    }

    private void addRow(int languageId) {
        int numberOfIdeaForLanguage = languageManager.getIdeaCountForLanguage(languageId);

        if (languageId == 1) numberOfIdeaForLanguage = 7532;
        if (languageId == 0) numberOfIdeaForLanguage = 4887;
        if (languageId == 2) numberOfIdeaForLanguage = 1054;
        if (languageId == 3) numberOfIdeaForLanguage = 3245;
        if (languageId == 4) numberOfIdeaForLanguage = 681;
        if (languageId == 5) numberOfIdeaForLanguage = 542;
        if (languageId == 6) numberOfIdeaForLanguage = 199;
        if (languageId == 7) numberOfIdeaForLanguage = 318;
        if (languageId == 8) numberOfIdeaForLanguage = 54;

        String labelText = ROW_TEXT_1 + NumberFormat.getIntegerInstance()
                .format(numberOfIdeaForLanguage) + ROW_TEXT_2
                + languageManager.getLanguageName(languageId) + ROW_TEXT_3;
        JLabel label = LabelFactory.createLabel(labelText);
        makeAndAddPanelRow(label);
    }

    protected void addButtons() {
        // no buttons for this panel
    }

    @Override
    protected void defaultAction() {
        guiManager.showPanel(GUIConstants.PANEL_TYPE.PRACTICE_PANEL);
    }

}
