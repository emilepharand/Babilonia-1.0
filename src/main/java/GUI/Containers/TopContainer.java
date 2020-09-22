package GUI.Containers;

import Data.Application.DataManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TopContainer {

    private JFrame mainFrame;
    private JPanel mainPanel;
    protected static int sideBarWidth;
    protected static int contentWidth;
    protected static int screenHeight;

    private DataManager dataManager;

    public TopContainer(DataManager dataManager) {
        this.dataManager = dataManager;
        createMainWindow();
        createMainPanel();
        calculateSize();
        mainFrame.add(mainPanel);
    }

    private void calculateSize() {
        Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        sideBarWidth = screen.width / 5;
        contentWidth = screen.width - sideBarWidth;
        screenHeight = screen.height;
    }

    private void createMainWindow() {
        mainFrame = new JFrame();
        mainFrame.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        mainFrame.setSize(new Dimension(screen.width, screen.height));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dataManager.cleanShutDown();
            }
        });
    }

    private void createMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        mainPanel.setPreferredSize(new Dimension(screen.width, screen.height));
        mainPanel.setVisible(true);
    }

    public void show(SidebarPanel sidebar, ContentPanel content) {
        mainPanel.add(sidebar.panel);
        mainPanel.add(content.panel);
        mainFrame.setVisible(true);
    }

    public void pack() {
        mainFrame.pack();
    }

    public void setMenuBar(JMenuBar menu) {
        mainFrame.setJMenuBar(menu);
    }

}
