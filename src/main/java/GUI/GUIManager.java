package GUI;

import Data.Application.DataManager;
import Data.Application.IdeaManager;
import Data.Application.LanguageManager;
import Data.Application.SettingsManager;
import GUI.Panels.AbstractPanel;
import GUI.Panels.Languages.*;
import GUI.Panels.Languages.SetPracticeLanguagesPanel;
import GUI.Panels.Settings.*;
import GUI.Panels.File.*;
import GUI.Panels.Ideas.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static GUI.GUIConstants.*;
import static GUI.GUIConstants.PANEL_TYPE.*;

/**
 * Manages everything related to GUI.
 */
public class GUIManager {

    private Menu menu;
    private JFrame mainFrame;
    private DataManager dataManager;
    private IdeaManager ideaManager;
    private LanguageManager languageManager;
    private SettingsManager settingsManager;

    private final static String WELCOME_MESSAGE =
            "<html><p style='text-align:center'>" +
            "<p style='margin-bottom:5;'><b>Welcome to Babilonia!</b></p>" +
            "<br>" +
            "<p style='margin-bottom:5;'>We hope you will enjoy using this software :)</p>" +
            "<br>" +
            "<p>Let's start by choosing a username.</p>" +
            "</p></html>";
    private final static String WELCOME_TITLE = "Welcome";
    private final static String NO_IDEA_MESSAGE = "Hey there! Please add at least one idea to start.";
    private final static String NO_LANGUAGE_MESSAGE = "Hey there! Please add at least one language to start.";
    private final static String NO_PRACTICE_IDEA_MESSAGE = "<html>No idea to practice has been found." +
            "<br>Please add ideas or let one language not be practice.</html>";
    private final static String NO_USERNAME_MESSAGE = "Please choose a username.";

    public void createAndShowGUI(DataManager dataManager) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // do nothing
        }
        this.dataManager = dataManager;
        ideaManager = dataManager.getIdeaManager();
        languageManager = dataManager.getLanguageManager();
        settingsManager = dataManager.getSettingsManager();
        mainFrame = new JFrame();
        menu = new Menu(this, dataManager);
        mainFrame.setJMenuBar(menu.getMenuBar());
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dataManager.cleanShutDown();
            }
        });
        showDefault();
    }

    public void showDefault() {
        if (ideaManager.getIdeaCount() == 0 && languageManager.getLanguageCount() == 0) {
            showDialog(WELCOME_MESSAGE, WELCOME_TITLE, new Dimension(500, 200));
            showPanel(CHANGE_USERNAME_PANEL);
        } else {
            showPanel(DASHBOARD_PANEL);
        }
    }

    /**
     * Shows a new panel to the user.
     * <p>
     * This is the method that should generally be called to show a panel.
     * Main window will be cleared and a new panel will be created.
     * It first makes sure the user has enough data to use panel.
     *
     * @param panelType the type of panel
     */
    public void showPanel(PANEL_TYPE panelType) {
        AbstractPanel panel = null;
        menu.disableIdeaModifying();
        if (panelType == PRACTICE_PANEL || panelType == EDIT_IDEA_PANEL)
            menu.enableIdeaModifying();
        if (settingsManager.getUsername().isEmpty() && panelType != CHANGE_USERNAME_PANEL) {
            showDialog(NO_USERNAME_MESSAGE, "");
            panelType = CHANGE_USERNAME_PANEL;
        }
        switch (panelType) {
            case PRACTICE_PANEL:
                ideaManager.goToNextIdea();
                if (!showAddIdeasIfNeeded() && !showSetPracticeLanguagesIfNeeded())
                    panel = new PracticePanel();
                break;
            case NEW_IDEA_PANEL:
                if (!showAddLanguagesIfNeeded()) panel = new NewIdeaPanel();
                break;
            case EDIT_IDEA_PANEL:
                panel = new EditIdeaPanel();
                break;
            case LANGUAGE_ORDER_PANEL:
                if (!showAddLanguagesIfNeeded())
                    panel = new LanguageOrderPanel();
                break;
            case SEARCH_IDEA_PANEL:
                if (!showAddIdeasIfNeeded()) panel = new SearchIdeaPanel();
                break;
            case ADD_LANGUAGES_PANEL:
                panel = new AddLanguagesPanel();
                break;
            case MODIFY_LANGUAGES_PANEL:
                if (!showAddLanguagesIfNeeded())
                    panel = new ModifyLanguagesPanel();
                break;
            case DELETE_LANGUAGES_PANEL:
                if (!showAddLanguagesIfNeeded())
                    panel = new DeleteLanguagesPanel();
                break;
            case SEARCH_LANGUAGES_PANEL:
                panel = new SearchLanguagesPanel();
                break;
            case SET_PRACTICE_LANGUAGES_PANEL:
                if (!showAddLanguagesIfNeeded())
                    panel = new SetPracticeLanguagesPanel();
                break;
            case CHANGE_USERNAME_PANEL:
                panel = new ChangeUsernamePanel();
                break;
            default:
                if (!showAddIdeasIfNeeded()) panel = new DashboardPanel();
        }
        if (panel != null) {
            panel.setManagers(dataManager, this);
            panel.show();
        }
    }

    /**
     * Prompts user to add ideas if there are none.
     *
     * @return <code>true</code> if panel was shown <code>false</code> otherwise
     */
    public boolean showAddIdeasIfNeeded() {
        boolean panelShown = false;
        if (showAddLanguagesIfNeeded()) {
            panelShown = true;
        } else if (ideaManager.getIdeaCount() == 0) {
            panelShown = true;
            showDialog(NO_IDEA_MESSAGE, "");
            showPanel(NEW_IDEA_PANEL);
        }
        return panelShown;
    }

    /**
     * Prompts user to add languages if there are none.
     *
     * @return <code>true</code> if panel was shown <code>false</code> otherwise
     */
    public boolean showAddLanguagesIfNeeded() {
        boolean panelShown = false;
        if (languageManager.getLanguageCount() < 1) {
            panelShown = true;
            showDialog(NO_LANGUAGE_MESSAGE, "");
            showPanel(ADD_LANGUAGES_PANEL);
        }
        return panelShown;
    }

    /**
     * Prompts user to check practice languages if no practiceable ideas are found.
     *
     * @return <code>true</code> if panel was shown <code>false</code> otherwise
     */
    public boolean showSetPracticeLanguagesIfNeeded() {
        boolean panelShown = false;
        if (ideaManager.getCurrentIdeaId() == -1) {
            panelShown = true;
            showDialog(NO_PRACTICE_IDEA_MESSAGE, "");
            showPanel(GUIConstants.PANEL_TYPE.SET_PRACTICE_LANGUAGES_PANEL);
        }
        return panelShown;
    }

    /**
     * Sets window title.
     *
     * @param title window title
     */
    public void setTitle(String title) {
        mainFrame.setTitle(title);
    }

    /**
     * Shows a dialog box to the user.
     *
     * @param msg   message
     * @param title dialog title
     */
    public void showDialog(String msg, String title) {
        JLabel label = new JLabel(msg);
        label.setFont(DEFAULT_FONT);
        JOptionPane.showMessageDialog(mainFrame.getContentPane(), label,
                title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows a dialog box to the user with specified dimension.
     *
     * @param msg       message
     * @param title     dialog title
     * @param dimension desired dimension
     */
    public void showDialog(String msg, String title, Dimension dimension) {
        JLabel label = new JLabel(msg);
        label.setPreferredSize(dimension);
        label.setFont(DEFAULT_FONT);
        JOptionPane.showMessageDialog(mainFrame.getContentPane(), label,
                title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows a yes/no confirm box to the user.
     *
     * @param msg message
     * @return <code>true</code> if user confirms, <code>false</code> otherwise
     */
    public boolean showConfirm(String msg, String title) {
        JLabel label = new JLabel(msg);
        label.setFont(DEFAULT_FONT);
        int confirm = JOptionPane.showConfirmDialog(mainFrame, label,
                title, JOptionPane.YES_NO_OPTION);
        return confirm == 0;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

}
