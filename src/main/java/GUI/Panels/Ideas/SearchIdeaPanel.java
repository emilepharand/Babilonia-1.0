package GUI.Panels.Ideas;

import GUI.Factories.ButtonFactory;
import GUI.Factories.PanelFactory;
import GUI.Factories.TextFieldFactory;
import GUI.GUIManager;
import GUI.Panels.AbstractSearchPanel;

import javax.swing.*;
import java.util.ArrayList;

import static GUI.GUIConstants.*;

public class SearchIdeaPanel extends AbstractSearchPanel {

    private static final String TITLE = "Search Idea";
    private static final String HEADER = "Search Idea";
    private static final String SEARCH_BUTTON_TEXT = "Search";
    private static final String EDIT_BUTTON_TEXT = "Edit";

    private JTextField searchTextField;
    private String searchText = "";
    private ArrayList<Integer> ideasFound;

    @Override
    public void prepare() {
        title = TITLE;
        header = HEADER;
        minimumFieldSize = MINIMUM_NUMBER_OF_FIELDS;
    }

    @Override
    protected void addRows() {
        addSearchRow();
        if (!searchText.isBlank()) showSearchResults(searchText);
        else SwingUtilities.invokeLater(searchTextField::requestFocusInWindow);
    }

    /**
     * Adds top search row, i.e. the search button and text field.
     */
    private void addSearchRow() {
        JButton searchButton = ButtonFactory.createButton(SEARCH_BUTTON_TEXT);
        searchButton.addActionListener(actionEvent -> defaultAction());
        searchTextField = TextFieldFactory.createTextField(searchText);
        searchTextField.addActionListener(actionEvent -> defaultAction());
        makeAndAddPanelRow(searchTextField, searchButton);
    }

    /**
     * Shows search results in a <code>JScrollPane</code>.
     *
     * @param searchText searched text
     */
    private void showSearchResults(String searchText) {
        JPanel resultPanel = PanelFactory.createOneColumnPanel(DEFAULT_NUMBER_OF_FIELDS - 2);
        ideasFound = ideaManager.search(searchText);
        resultPanel.add(makeScrollPane(ideasFound));
        jPanelMain.add(resultPanel);
    }

    @Override
    protected void addButtons() {
        JPanel buttonPanel = PanelFactory.createOneColumnPanel();
        JButton editButton = ButtonFactory.createButton(EDIT_BUTTON_TEXT);
        buttonPanel.add(editButton);
        addButtonPanel(buttonPanel);
        if (jList.getSelectedIndex() == -1) {
            editButton.setEnabled(false);
            SwingUtilities.invokeLater(searchTextField::requestFocusInWindow);
        } else {
            SwingUtilities.invokeLater(editButton::requestFocusInWindow);
        }
        editButton.addActionListener(actionEvent -> editButtonPressed());
    }

    private void editButtonPressed() {
        ideaManager.setCurrentIdeaId(ideasFound.get(jList.getSelectedIndex()));
        guiManager.showPanel(PANEL_TYPE.EDIT_IDEA_PANEL);
    }

    @Override
    protected void defaultAction() {
        searchText = searchTextField.getText();
        show();
    }

}
