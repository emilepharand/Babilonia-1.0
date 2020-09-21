package GUI;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuElements {

    public static final int SIDEBAR_MENU_ROW_HEIGHT = 50;

    public static final String FILE = "File";
    public static final String DASHBOARD = "Dashboard";
    public static final String PRACTICE_IDEAS = "Practice Ideas";

    public static final String IDEAS = "Ideas";
    public static final String NEW_IDEA = "New Idea";
    public static final String EDIT_IDEA = "Edit";
    public static final String DELETE_IDEA = "Delete";
    public static final String DELETE_IDEA_CONFIRM_MSG = "Are you sure you want to delete this idea?";
    public static final String DELETE_IDEA_CONFIRM_TITLE = "Are you sure?";
    public static final String SEARCH_IDEA = "Search";

    public static final String LANGUAGES = "Languages";
    public static final String ADD_LANGUAGES = "Add Languages";
    public static final String MODIFY_LANGUAGES = "Modify";
    public static final String DELETE_LANGUAGES = "Delete";
    public static final String SEARCH_LANGUAGES = "Search";
    public static final String SET_ORDER = "Set Order";
    public static final String SET_PRACTICE = "Set Practice";

    public static final String SETTINGS = "Settings";
    public static final String CHANGE_USERNAME = "Change Username";
    public static final String RESET_EVERYTHING = "Reset Everything";
    public static final String RESET_CONFIRM_MSG = "<html>Are you sure you want to reset settings?<br>" +
            "This action cannot be undone!</html>";
    public static final String RESET_CONFIRM_TITLE = "Are you sure?";
    public static final String RESET_CONFIRMATION = "Settings have been reset.";

    public static final String HELP = "Help";
    public static final String GITHUB_PAGE = "GitHub Page";
    public static final String GITHUB_PAGE_URL = "https://github.com/emilepharand/Babilonia";
    public static final String GITHUB_PAGE_ERROR = "Visit https://github.com/emilepharand/Babilonia";
    public static final String ABOUT = "About";
    public static final String ABOUT_TEXT = "<html><center>Copyright © 2020 Émile Pharand" +
            "<br>All rights reserved.<br><br>" +
            "This work is licensed under the terms of the MIT license.<br>" +
            "For a copy, see https://opensource.org/licenses/MIT</center></html>";

    public static final ArrayList<String> SIDEBAR_MENU_ITEMS_IDEAS
            = new ArrayList<>(Arrays.asList(NEW_IDEA,DELETE_IDEA,SEARCH_IDEA));
    public static final ArrayList<String> SIDEBAR_MENU_ITEMS_LANGUAGES
            = new ArrayList<>(Arrays.asList(ADD_LANGUAGES,MODIFY_LANGUAGES,DELETE_LANGUAGES,SEARCH_LANGUAGES));

}
