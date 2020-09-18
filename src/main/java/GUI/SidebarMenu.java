package GUI;

import GUI.Containers.SidebarPanel;
import GUI.Containers.TopContainer;
import GUI.Factories.ButtonFactory;

import javax.swing.*;
import java.awt.*;

public class SidebarMenu {

    JPanel panel = new JPanel();

    public void init(SidebarPanel sidebar) {
        for (String item : MenuElements.SIDEBAR_MENU_ITEMS_IDEAS) {
            JButton button = ButtonFactory.createButton(item);
            panel.add(button);
        }
        for (String item : MenuElements.SIDEBAR_MENU_ITEMS_LANGUAGES) {
            JButton button = ButtonFactory.createButton(item);
            panel.add(button);
        }
        sidebar.getJPanel().add(panel);
        panel.setVisible(true);
    }

    public JPanel getJPanel() {
        return panel;
    }

}
