package GUI.Panels.Settings;

import GUI.Factories.ButtonPanelFactory;
import GUI.Factories.LabelFactory;
import GUI.Factories.TextFieldFactory;
import GUI.GUIConstants;
import GUI.Panels.AbstractPanel;

import javax.swing.*;

public class ChangeUsernamePanel extends AbstractPanel {

    private static final String TITLE = "Change Username";
    private static final String HEADER = "Change Username";
    private static final String ENTER_USERNAME = "Enter your username :";
    private static final String SAVE_BUTTON_TEXT = "Save";
    private static final String CONFIRMATION_MSG = "Username successfully saved.";
    private static final String USERNAME_EMPTY_MSG = "Username cannot be empty.";

    private JTextField textField;

    @Override
    protected void prepare() {
        title = TITLE;
        header = HEADER;
        minimumFieldSize = 1;
    }

    @Override
    protected void addRows() {
        addRow();
    }

    private void addRow() {
        JLabel label = LabelFactory.createLabel(ENTER_USERNAME);
        makeAndAddPanelRow(label);
        textField = TextFieldFactory.createTextField(settingsManager.getUsername());
        textField.addActionListener(actionEvent -> defaultAction());
        SwingUtilities.invokeLater(textField::requestFocusInWindow);
        makeAndAddPanelRow(textField);
    }

    @Override
    protected void addButtons() {
        JPanel buttonPanel = ButtonPanelFactory.createOneButtonPanel
                (SAVE_BUTTON_TEXT, actionEvent -> defaultAction());
        addButtonPanel(buttonPanel);
    }

    @Override
    protected void defaultAction() {
        if (textField.getText().isEmpty())
            guiManager.showDialog(USERNAME_EMPTY_MSG, "");
        else {
            settingsManager.setUsername(textField.getText());
            guiManager.showDialog(CONFIRMATION_MSG, "");
            guiManager.showPanel(GUIConstants.PANEL_TYPE.DASHBOARD_PANEL);
        }
    }

}
