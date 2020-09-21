package GUI.Containers;

import GUI.Factories.ButtonFactory;
import GUI.Factories.LabelFactory;
import GUI.Factories.PanelFactory;
import GUI.MenuElements;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SidebarPanel {

    protected JPanel panel;

    public SidebarPanel() {
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.setPreferredSize(new Dimension(TopContainer.sideBarWidth, TopContainer.screenHeight));
        panel.setBackground(Color.BLUE);
        panel.setVisible(true);
    }

    public void init() {
        addSidebarGroup("Ideas", MenuElements.SIDEBAR_MENU_ITEMS_IDEAS);
        addSidebarGroup("Languages", MenuElements.SIDEBAR_MENU_ITEMS_LANGUAGES);
        panel.setVisible(true);
    }

    public void addSidebarGroup(String name, ArrayList<String> items) {
        JPanel row = PanelFactory.createOneColumnPanel(new Dimension(TopContainer.sideBarWidth, MenuElements.SIDEBAR_MENU_ROW_HEIGHT));
        JLabel label = LabelFactory.createLabel(name);
        row.add(label);
        panel.add(row);
        for (String item : MenuElements.SIDEBAR_MENU_ITEMS_IDEAS) {
            row = PanelFactory.createOneColumnPanel(new Dimension(TopContainer.sideBarWidth, MenuElements.SIDEBAR_MENU_ROW_HEIGHT));
            JButton button = ButtonFactory.createButton(item);
            row.add(button);
            panel.add(row);
        }
    }

    public JPanel getJPanel() {
        return panel;
    }

}
