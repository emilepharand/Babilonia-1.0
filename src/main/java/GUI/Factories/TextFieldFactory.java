package GUI.Factories;

import javax.swing.*;

import static GUI.GUIConstants.*;

public class TextFieldFactory {

    public static JTextField createTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setFont(DEFAULT_FONT_UNICODE);
        textField.setForeground(DEFAULT_TEXT_COLOR);
        return textField;
    }

}
