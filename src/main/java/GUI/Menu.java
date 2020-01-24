package GUI;

import Data.Application.DataManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static GUI.GUIConstants.MENU_FONT;
import static GUI.GUIConstants.PANEL_TYPE.*;

public class Menu {

    private JMenuBar menuBar;
    private JMenuItem menuEdit;
    private JMenuItem menuDelete;

    private static final String FILE = "File";
    private static final String DASHBOARD = "Dashboard";
    private static final String PRACTICE_IDEAS = "Practice Ideas";

    private static final String IDEAS = "Ideas";
    private static final String NEW_IDEA = "New Idea";
    private static final String EDIT_IDEA = "Edit";
    private static final String DELETE_IDEA = "Delete";
    private static final String DELETE_IDEA_CONFIRM_MSG = "Are you sure you want to delete this idea?";
    private static final String DELETE_IDEA_CONFIRM_TITLE = "Are you sure?";
    private static final String SEARCH_IDEA = "Search";

    private static final String LANGUAGES = "Languages";
    private static final String ADD_LANGUAGES = "Add Languages";
    private static final String MODIFY_LANGUAGES = "Modify";
    private static final String DELETE_LANGUAGES = "Delete";
    private static final String SEARCH_LANGUAGES = "Search";
    private static final String SET_ORDER = "Set Order";
    private static final String SET_PRACTICE = "Set Practice";

    private static final String SETTINGS = "Settings";
    private static final String CHANGE_USERNAME = "Change Username";
    private static final String RESET_EVERYTHING = "Reset Everything";
    private static final String RESET_CONFIRM_MSG = "<html>Are you sure you want to reset settings?<br>" +
            "This action cannot be undone!</html>";
    private static final String RESET_CONFIRM_TITLE = "Are you sure?";
    private static final String RESET_CONFIRMATION = "Settings have been reset.";

    private static final String HELP = "Help";
    private static final String GITHUB_PAGE = "GitHub Page";
    private static final String GITHUB_PAGE_URL = "https://github.com/emilepharand/Multi-Vocabulary";
    private static final String GITHUB_PAGE_ERROR = "Visit https://github.com/emilepharand/Multi-Vocabulary";
    private static final String ABOUT = "About";
    private static final String ABOUT_TEXT = "<html><center>Copyright © 2020 Émile Pharand" +
            "<br>All rights reserved.<br><br>" +
            "This work is licensed under the terms of the MIT license.<br>" +
            "For a copy, see https://opensource.org/licenses/MIT</center></html>";
    
    private GUIManager guiManager;
    private DataManager dataManager;
    
    public Menu(GUIManager guiManager, DataManager dataManager) {
        this.guiManager = guiManager;
        this.dataManager = dataManager;
        menuBar = new JMenuBar();
        UIManager.put("Menu.font", MENU_FONT);
        UIManager.put("MenuItem.font", MENU_FONT);
        addFileMenu(menuBar);
        addIdeasMenu(menuBar);
        addLanguageMenu(menuBar);
        addSettingsMenu(menuBar);
        addHelpMenu(menuBar);

    }

    private void addFileMenu(JMenuBar menuBar) {
        JMenu menuFile = new JMenu(FILE);
        menuBar.add(menuFile);
        menuFile.add(createMenuItem(DASHBOARD, actionEvent -> guiManager.showPanel(GUIConstants.PANEL_TYPE.DASHBOARD_PANEL)));
        menuFile.addSeparator();
        menuFile.add(createMenuItem(PRACTICE_IDEAS, actionEvent -> guiManager.showPanel(PRACTICE_PANEL)));
    }

    private void addIdeasMenu(JMenuBar menuBar) {
        JMenu menuIdeas = new JMenu(IDEAS);
        menuBar.add(menuIdeas);
        menuIdeas.add(createMenuItem(NEW_IDEA, actionEvent -> guiManager.showPanel(GUIConstants.PANEL_TYPE.NEW_IDEA_PANEL)));
        menuIdeas.addSeparator();
        menuEdit = createMenuItem(EDIT_IDEA, actionEvent -> guiManager.showPanel(GUIConstants.PANEL_TYPE.EDIT_IDEA_PANEL));
        menuDelete = createMenuItem(DELETE_IDEA, actionEvent -> {
            if (guiManager.showConfirm(DELETE_IDEA_CONFIRM_MSG, DELETE_IDEA_CONFIRM_TITLE)) {
                dataManager.getIdeaManager().deleteIdea(dataManager.getIdeaManager().getCurrentIdeaId());
                guiManager.showPanel(PRACTICE_PANEL);
            }
        });
        menuIdeas.addSeparator();
        menuIdeas.add(createMenuItem(SEARCH_IDEA, actionEvent -> guiManager.showPanel(GUIConstants.PANEL_TYPE.SEARCH_IDEA_PANEL)));
        menuIdeas.add(menuEdit);
        menuIdeas.add(menuDelete);
    }

    private void addLanguageMenu(JMenuBar menuBar) {
        JMenu menuLanguage = new JMenu(LANGUAGES);
        menuBar.add(menuLanguage);
        menuLanguage.add(createMenuItem(ADD_LANGUAGES, actionEvent -> guiManager.showPanel(GUIConstants.PANEL_TYPE.ADD_LANGUAGES_PANEL)));
        menuLanguage.addSeparator();
        menuLanguage.add(createMenuItem(MODIFY_LANGUAGES, actionEvent -> guiManager.showPanel(MODIFY_LANGUAGES_PANEL)));
        menuLanguage.add(createMenuItem(DELETE_LANGUAGES, actionEvent -> guiManager.showPanel(DELETE_LANGUAGES_PANEL)));
        menuLanguage.addSeparator();
        menuLanguage.add(createMenuItem(SEARCH_LANGUAGES, actionEvent -> guiManager.showPanel(SEARCH_LANGUAGES_PANEL)));
        menuLanguage.addSeparator();
        menuLanguage.add(createMenuItem(SET_ORDER, actionEvent -> guiManager.showPanel(LANGUAGE_ORDER_PANEL)));
        menuLanguage.add(createMenuItem(SET_PRACTICE, actionEvent -> guiManager.showPanel(SET_PRACTICE_LANGUAGES_PANEL)));
    }

    private void addSettingsMenu(JMenuBar menuBar) {
        JMenu menuSettings = new JMenu(SETTINGS);
        menuBar.add(menuSettings);
        menuSettings.add(createMenuItem(CHANGE_USERNAME, actionEvent -> guiManager.showPanel(CHANGE_USERNAME_PANEL)));
        menuSettings.add(createMenuItem(RESET_EVERYTHING, actionEvent -> {
            if (guiManager.showConfirm(RESET_CONFIRM_MSG, RESET_CONFIRM_TITLE)) {
                dataManager.resetEverything();
                guiManager.showDialog(RESET_CONFIRMATION, "");
                guiManager.showDefault();
            }
        }));
    }

    private void addHelpMenu(JMenuBar menuBar) {
        JMenu menuHelp = new JMenu(HELP);
        menuBar.add(menuHelp);
        menuHelp.add(createMenuItem(GITHUB_PAGE, actionEvent -> {
            try {
                Desktop.getDesktop().browse(new URI(GITHUB_PAGE_URL));
            } catch (IOException | URISyntaxException e) {
                guiManager.showDialog(GITHUB_PAGE_ERROR, "");
            }
        }));
        menuHelp.addSeparator();
        menuHelp.add(createMenuItem(ABOUT, actionEvent -> guiManager.showDialog(ABOUT_TEXT, "")));
    }

    private JMenuItem createMenuItem(String text, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(actionListener);
        return menuItem;
    }

    /**
     * Disables idea modifying (edit and delete)
     * when it doesn't make sense to do so.
     */
    void disableIdeaModifying() {
        menuEdit.setEnabled(false);
        menuDelete.setEnabled(false);
    }

    /**
     * Enables idea modifying (edit and delete)
     * when it makes sense to do so.
     */
    void enableIdeaModifying() {
        menuEdit.setEnabled(true);
        menuDelete.setEnabled(true);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

}
