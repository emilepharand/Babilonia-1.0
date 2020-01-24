package GUI.Panels.Languages;

import GUI.Factories.ButtonPanelFactory;
import GUI.Factories.ComboBoxFactory;
import GUI.Factories.LabelFactory;
import GUI.GUIConstants;
import GUI.GUIManager;
import GUI.Panels.AbstractPanel;
import GUI.Panels.Ideas.NewIdeaPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import static GUI.GUIConstants.DEFAULT_FONT_UNICODE;

public class LanguageOrderPanel extends AbstractPanel {

    private static final String TITLE = "Set Language Order";
    private static final String HEADER = "Set Language Order";
    private static final String SAVE_BUTTON_TEXT = "Save";
    private static final String ERROR_DUPLICATE_ORDER =
            "<html>Duplicate orders found."
            + "<br>Please check and try again.</html>";

    private ArrayList<JLabel> leftColumn = new ArrayList<>();
    private ArrayList<JComboBox<Integer>> rightColumn = new ArrayList<>();

    private ArrayList<Integer> languageIds;

    @Override
    public void prepare() {
        title = TITLE;
        header = HEADER;
        languageIds = languageManager.getLanguageIds();
        minimumFieldSize = languageManager.getLanguageCount();
    }

    @Override
    protected void addRows() {
        Integer[] orderArray = makeOrderArray();
        ArrayList<Integer> languageIds = languageManager.getLanguageIds();
        for (int i = 0; i < languageIds.size(); i++)
            addRow(orderArray, i, languageIds);
    }

    /**
     * Creates an array of same length as number of languages
     * in which array[i] == i+1.
     *
     * @return the array
     */
    private Integer[] makeOrderArray() {
        Integer[] orderArray = new Integer[languageManager.getLanguageCount()];
        for (int i = 0; i < orderArray.length; i++) orderArray[i] = i + 1;
        return orderArray;
    }

    private void addRow(Integer[] orderArray, int i, ArrayList<Integer> languageIds) {
        JLabel label = LabelFactory.createLabel
                (languageManager.getLanguageName(languageIds.get(i)));
        JComboBox<Integer> comboBox = ComboBoxFactory.createIntegerComboBox(orderArray);
        comboBox.setSelectedIndex(i);
        label.setFont(DEFAULT_FONT_UNICODE);
        makeAndAddPanelRow(label, comboBox);
        addToColumns(label, comboBox);
    }

    private void addToColumns(JLabel label, JComboBox<Integer> comboBox) {
        leftColumn.add(label);
        rightColumn.add(comboBox);
    }

    @Override
    protected void addButtons() {
        JPanel buttonPanel = ButtonPanelFactory.createOneButtonPanel
                (SAVE_BUTTON_TEXT, actionEvent -> defaultAction());
        addButtonPanel(buttonPanel);
    }

    @Override
    protected void defaultAction() {
        if (!checkLanguageOrder()) {
            guiManager.showDialog(
                    ERROR_DUPLICATE_ORDER, "");
        } else {
            for (int i = 0; i < leftColumn.size(); i++) {
                languageManager.setLanguageOrder(languageIds.get(i),
                        rightColumn.get(i).getSelectedIndex());
            }
            NewIdeaPanel.languageContext.clear();
            guiManager.showPanel(GUIConstants.PANEL_TYPE.LANGUAGE_ORDER_PANEL);
        }
    }

    /**
     * Checks that language order is consistent.
     * That means every language has a different order.
     *
     * @return <code>true</code> if consistent, <code>false</code> otherwise
     */
    private boolean checkLanguageOrder() {
        ArrayList<Integer> array = rightColumn.stream().map(JComboBox::getSelectedIndex)
                .collect(Collectors.toCollection(ArrayList::new));
        Set<Integer> set = rightColumn.stream().map(JComboBox::getSelectedIndex)
                .collect(Collectors.toSet());
        return array.size() == set.size();
    }

}
