package GUI.Panels;

import Data.Application.DataManager;
import Data.Application.IdeaManager;
import Data.Application.LanguageManager;
import Data.Application.SettingsManager;
import GUI.Factories.LabelFactory;
import GUI.Factories.PanelFactory;
import GUI.GUIManager;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static GUI.GUIConstants.*;

/**
 * All panels must extend this class.
 */
public abstract class AbstractPanel {

    protected String title;
    protected String header;

    protected GUIManager guiManager;
    protected DataManager dataManager;
    protected IdeaManager ideaManager;
    protected LanguageManager languageManager;
    protected SettingsManager settingsManager;

    protected JPanel jPanel;
    protected JPanel jPanelMain;
    protected JPanel jPanelHeader;
    protected JPanel jPanelButtons;

    protected int minimumFieldSize;

    /**
     * Shows panel.
     * Should only be called by <code>GUIManager</code> or by own instance.
     * Otherwise, <code>showPanel</code> method of GUIManager should be used.
     */
    public void show() {
        jPanel = new JPanel(new BorderLayout(0, 0));
        clear();
        prepare();
        fillPanel();
        guiManager.setTitle(title);
        finish();
    }

    public void setManagers(DataManager dataManager, GUIManager guiManager) {
        this.dataManager = dataManager;
        this.ideaManager = dataManager.getIdeaManager();
        this.languageManager = dataManager.getLanguageManager();
        this.settingsManager = dataManager.getSettingsManager();
        this.guiManager = guiManager;
    }

    /**
     * Removes everything from panel.
     */
    protected void clear() {
        jPanel.setVisible(false);
        guiManager.getMainFrame().getContentPane().removeAll();
        jPanel.removeAll();
        jPanel.revalidate();
        jPanel.repaint();
    }

    /**
     * Method used by subclasses to do panel-specific
     * things ahead of being shown.
     */
    protected abstract void prepare();

    protected void fillPanel() {
        createAndPlacePanels();
        addHeader();
        addRows();
        addButtons();
        setGUILook();
    }

    private void createAndPlacePanels() {
        jPanelHeader = new JPanel(new BorderLayout(0, 0));
        jPanelMain = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jPanelButtons = new JPanel(new BorderLayout(0, 0));
        jPanel.add(jPanelHeader, BorderLayout.NORTH);
        jPanel.add(jPanelMain, BorderLayout.CENTER);
        jPanel.add(jPanelButtons, BorderLayout.SOUTH);
    }

    private void addHeader() {
        JPanel panel = PanelFactory.createOneColumnPanel();
        JLabel label = LabelFactory.createLabel(header);
        label.setFont(HEADER_FONT);
        label.setForeground(HEADER_TEXT_COLOR);
        panel.add(label);
        jPanelHeader.add(panel);
        jPanelHeader.setPreferredSize(new Dimension(WINDOW_WIDTH, ROW_HEIGHT));
    }

    abstract protected void addRows();

    abstract protected void addButtons();

    /**
     * Arranges some GUI details.
     */
    private void setGUILook() {
        jPanel.setBackground(BACKGROUND_COLOR);
        jPanelHeader.setBackground(HEADER_BACKGROUND_COLOR);
        jPanelHeader.setOpaque(true);
        jPanelMain.setOpaque(false);
        jPanelHeader.setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));
        jPanelMain.setBorder(new MatteBorder(0, 2, 2, 2, Color.WHITE));
    }

    /**
     * Makes a row, adds components to it and adds row panel
     * to <code>jPanelMain</code>.
     *
     * @param components components to add
     */
    protected void makeAndAddPanelRow(JComponent... components) {
        JPanel panelRow;
        if (components.length == 2) panelRow = PanelFactory.createTwoColumnPanel();
        else if (components.length == 3) panelRow = PanelFactory.createThreeColumnPanel();
        else panelRow = PanelFactory.createOneColumnPanel();
        panelRow.setOpaque(true);
        panelRow.setBackground(CONTENT_BACKGROUND_COLOR);
        for (JComponent component : components) {
            panelRow.add(component);
        }
        jPanelMain.add(panelRow);
    }

    /**
     * Finishes panel and shows it.
     */
    protected void finish() {
        sizePanel(Math.max(minimumFieldSize, MINIMUM_NUMBER_OF_FIELDS));
        jPanel.setVisible(true);
        JFrame mainFrame = guiManager.getMainFrame();
        mainFrame.add(jPanel);
        mainFrame.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(dim.width / 2 - mainFrame.getSize().width / 2,
                dim.height / 2 - mainFrame.getSize().height / 2);
        mainFrame.setVisible(true);
    }

    /**
     * Sizes panels according to number of fields.
     *
     * @param numberOfFields number of fields
     */
    protected void sizePanel(int numberOfFields) {
        jPanelMain.setPreferredSize(new Dimension(WINDOW_WIDTH, numberOfFields * ROW_HEIGHT));
    }

    protected void addButtonPanel(JPanel buttonPanel) {
        jPanelButtons.add(buttonPanel);
    }

    public JPanel getJPanel() {
        return jPanel;
    }

    public JPanel getButtonPanel() {
        return jPanelButtons;
    }

    /**
     * Default panel action.
     * Called when user hits enter on an element.
     * If that behavior is not wanted, leave empty.
     */
    protected abstract void defaultAction();

}
