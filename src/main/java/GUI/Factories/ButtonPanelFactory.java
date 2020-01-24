package GUI.Factories;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ButtonPanelFactory {

    public static JPanel createTwoButtonPanel(String leftText, ActionListener leftAction,
                                              String rightText, ActionListener rightAction) {
        JPanel twoButtonPanel = PanelFactory.createTwoColumnPanel();
        addOneButton(twoButtonPanel, leftText, leftAction);
        addOneButton(twoButtonPanel, rightText, rightAction);
        return twoButtonPanel;
    }

    public static JPanel createOneButtonPanel(String text, ActionListener action) {
        JPanel oneButtonPanel = PanelFactory.createOneColumnPanel();
        addOneButton(oneButtonPanel, text, action);
        return oneButtonPanel;
    }

    private static void addOneButton(JPanel panelToWhichAdd,
                                     String text, ActionListener action) {
        JButton button = ButtonFactory.createButton(text);
        panelToWhichAdd.add(button);
        button.addActionListener(action);
    }

}
