package GUI.Factories;

import javax.swing.*;

import static GUI.GUIConstants.*;

public class ButtonFactory {

    public static JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setFont(DEFAULT_FONT);
        button.setForeground(DEFAULT_TEXT_COLOR);
        return button;
    }

}
