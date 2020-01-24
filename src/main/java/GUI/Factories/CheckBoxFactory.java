package GUI.Factories;

import javax.swing.*;

import static GUI.GUIConstants.*;

public class CheckBoxFactory {

    public static JCheckBox createCheckBox(String text) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setFont(DEFAULT_FONT_UNICODE);
        checkBox.setForeground(DEFAULT_TEXT_COLOR);
        return checkBox;
    }

}
