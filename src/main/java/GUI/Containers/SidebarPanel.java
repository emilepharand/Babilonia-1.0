package GUI.Containers;

import javax.swing.*;
import java.awt.*;

public class SidebarPanel {

    protected JPanel panel;

    public SidebarPanel() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout(0,0));
        panel.setPreferredSize(new Dimension(TopContainer.sideBarWidth, TopContainer.screenHeight));
        panel.setBackground(Color.BLUE);
        panel.setVisible(true);
    }

    public JPanel getJPanel() {
        return panel;
    }

}
