package GUI.Factories;

import javax.swing.*;

import static GUI.GUIConstants.*;

public class ComboBoxFactory {

    public static JComboBox<String> createStringComboBox(String[] array) {
        JComboBox<String> comboBox = new JComboBox<>(array);
        comboBox.setFont(DEFAULT_FONT_UNICODE);
        comboBox.setForeground(DEFAULT_TEXT_COLOR);
        return comboBox;
    }

    public static JComboBox<Integer> createIntegerComboBox(Integer[] array) {
        JComboBox<Integer> comboBox = new JComboBox<>(array);
        comboBox.setFont(DEFAULT_FONT_UNICODE);
        comboBox.setForeground(DEFAULT_TEXT_COLOR);
        return comboBox;
    }

}
