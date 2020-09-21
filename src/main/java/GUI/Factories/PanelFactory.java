package GUI.Factories;

import javax.swing.*;
import java.awt.*;

import static GUI.GUIConstants.*;

public class PanelFactory {

    public static JPanel createOneColumnPanel(Dimension dim) {
        JPanel oneColumnPanel = new JPanel();
        oneColumnPanel.setPreferredSize(dim);
        oneColumnPanel.setLayout(new BorderLayout(0, 0));
        oneColumnPanel.setOpaque(false);
        return oneColumnPanel;
    }

    public static JPanel createOneColumnPanel() {
        JPanel oneColumnPanel = new JPanel();
        oneColumnPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, ROW_HEIGHT));
        oneColumnPanel.setLayout(new BorderLayout(0, 0));
        oneColumnPanel.setOpaque(false);
        return oneColumnPanel;
    }

    public static JPanel createOneColumnPanel(int numberOfRows) {
        JPanel oneColumnPanel = new JPanel();
        oneColumnPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, ROW_HEIGHT * numberOfRows));
        oneColumnPanel.setLayout(new BorderLayout(0, 0));
        return oneColumnPanel;
    }

    public static JPanel createTwoColumnPanel() {
        JPanel twoColumnPanel = new JPanel();
        twoColumnPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, ROW_HEIGHT));
        twoColumnPanel.setLayout(new GridLayout(1, 2, 0, 0));
        return twoColumnPanel;
    }

    public static JPanel createThreeColumnPanel() {
        JPanel threeColumnPanel = new JPanel();
        threeColumnPanel.setLayout(new GridLayout(1, 3, 0, 0));
        threeColumnPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, ROW_HEIGHT));
        return threeColumnPanel;
    }

}
