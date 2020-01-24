package GUI.Panels.Languages;

import GUI.Factories.ButtonFactory;
import GUI.Factories.ComboBoxFactory;
import GUI.Factories.PanelFactory;
import GUI.GUIManager;
import GUI.Panels.AbstractSearchPanel;

import javax.swing.*;
import java.util.ArrayList;

import static GUI.GUIConstants.*;

public class SearchLanguagesPanel extends AbstractSearchPanel {

    private static final String TITLE = "Search Languages";
    private static final String HEADER = "Search Languages";
    private static final String SEARCH_BUTTON_TEXT = "Search";
    private static final String EDIT_BUTTON_TEXT = "Edit";

    private ArrayList<Integer> ideasFound;
    private String[] languages;
    private JComboBox<String> languageDropdown;
    private int selectedIndex = -1;
    private ArrayList<Integer> languageIds;

    @Override
    public void prepare() {
        title = TITLE;
        header = HEADER;
        languages = languageManager.getLanguageStrings().toArray(String[]::new);
        languageIds = languageManager.getLanguageIds();
        minimumFieldSize = MINIMUM_NUMBER_OF_FIELDS;
    }

    @Override
    protected void addRows() {
        JButton searchButton = ButtonFactory.createButton(SEARCH_BUTTON_TEXT);
        searchButton.addActionListener(actionEvent -> defaultAction());
        languageDropdown = ComboBoxFactory.createStringComboBox(languages);
        makeAndAddPanelRow(languageDropdown, searchButton);
        if (selectedIndex != -1) {
            languageDropdown.setSelectedIndex(selectedIndex);
            showSearchResults(languageIds.get(selectedIndex));
        }
    }

    @Override
    protected void addButtons() {
        JPanel buttonPanel = PanelFactory.createOneColumnPanel();
        JButton editButton = ButtonFactory.createButton(EDIT_BUTTON_TEXT);
        buttonPanel.add(editButton);
        addButtonPanel(buttonPanel);
        if (jList.getSelectedIndex() == -1) {
            editButton.setEnabled(false);
        } else {
            SwingUtilities.invokeLater(editButton::requestFocusInWindow);
        }
        editButton.addActionListener(actionEvent -> editButtonPressed());
    }

    private void showSearchResults(int languageId) {
        JPanel resultPanel = PanelFactory.createOneColumnPanel(DEFAULT_NUMBER_OF_FIELDS - 2);
        ideasFound = ideaManager.searchByLanguageId(languageId);
        resultPanel.add(makeScrollPane(ideasFound));
        jPanelMain.add(resultPanel);
    }

    @Override
    protected void defaultAction() {
        selectedIndex = languageDropdown.getSelectedIndex();
        show();
    }

    private void editButtonPressed() {
        ideaManager.setCurrentIdeaId(ideasFound.get(jList.getSelectedIndex()));
        guiManager.showPanel(PANEL_TYPE.EDIT_IDEA_PANEL);
    }

}
