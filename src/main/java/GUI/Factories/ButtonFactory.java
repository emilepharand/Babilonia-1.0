package GUI.Factories;

import javax.swing.*;

import java.awt.*;

import static GUI.GUIConstants.*;

public class ButtonFactory {

    public static JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setFont(DEFAULT_FONT);
        button.setForeground(DEFAULT_TEXT_COLOR);
        button.setBackground(Color.WHITE);
        return button;
    }

}
