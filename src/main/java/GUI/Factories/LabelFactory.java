package GUI.Factories;

import javax.swing.*;

import static GUI.GUIConstants.DEFAULT_FONT;
import static GUI.GUIConstants.DEFAULT_TEXT_COLOR;

public class LabelFactory {

    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(DEFAULT_FONT);
        label.setForeground(DEFAULT_TEXT_COLOR);
        return label;
    }

}
