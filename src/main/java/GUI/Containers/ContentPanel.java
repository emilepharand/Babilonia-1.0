package GUI.Containers;

import javax.swing.*;
import java.awt.*;

public class ContentPanel {

    protected JPanel panel;

    public ContentPanel() {
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setPreferredSize(new Dimension(TopContainer.contentWidth, TopContainer.screenHeight));
        panel.setBackground(Color.RED);
        panel.setVisible(true);
    }

}
