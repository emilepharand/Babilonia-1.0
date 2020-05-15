package GUI.Containers;

import javax.swing.*;
import java.awt.*;

public class SidebarPanel {

    protected JPanel panel;

    public SidebarPanel() {
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setPreferredSize(new Dimension(TopContainer.sideBarWidth, TopContainer.screenHeight));
        panel.setBackground(Color.BLUE);
        panel.setVisible(true);
    }

}
