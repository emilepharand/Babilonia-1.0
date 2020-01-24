package GUI;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GUIConstants {

    public static final int WINDOW_WIDTH = 1000;
    public static final int ROW_HEIGHT = 50;
    public static final int DEFAULT_NUMBER_OF_FIELDS = 8;
    public static final int MINIMUM_NUMBER_OF_FIELDS = 8;
    public static final int DEFAULT_INCREMENT_FIELDS = 1;

    public static Font DEFAULT_FONT_BOLD;
    public static Font DEFAULT_FONT_REGULAR;

    static {
        try {
            DEFAULT_FONT_BOLD = Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/main/assets/fonts/OpenSans-Bold.ttf"));
            DEFAULT_FONT_REGULAR = Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/main/assets/fonts/OpenSans-Regular.ttf"));
        } catch (FontFormatException | IOException e) {
            DEFAULT_FONT_BOLD = new Font(Font.SANS_SERIF, Font.BOLD, 20);
            DEFAULT_FONT_REGULAR = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
        }
    }

    public static final Font DEFAULT_FONT = DEFAULT_FONT_BOLD.deriveFont(20f);
    public static final Font DEFAULT_FONT_UNICODE = new Font(Font.SANS_SERIF, Font.PLAIN, 22);
    public static final Font HEADER_FONT = DEFAULT_FONT.deriveFont(24f);
    public static final Font MENU_FONT = DEFAULT_FONT_REGULAR.deriveFont(16f);
    public static final Color DEFAULT_TEXT_COLOR = Color.BLACK;
    public static final Color HEADER_BACKGROUND_COLOR = new Color(40, 40, 40);
    public static final Color HEADER_TEXT_COLOR = Color.WHITE;
    public static final Color BACKGROUND_COLOR = new Color(40, 40, 40);
    public static final Color CONTENT_BACKGROUND_COLOR = new Color(200, 200, 200);

    public enum PANEL_TYPE {
        DASHBOARD_PANEL, LANGUAGE_ORDER_PANEL, SEARCH_IDEA_PANEL, PRACTICE_PANEL, NEW_IDEA_PANEL,
        EDIT_IDEA_PANEL, MODIFY_LANGUAGES_PANEL, ADD_LANGUAGES_PANEL, CHANGE_USERNAME_PANEL,
        SET_PRACTICE_LANGUAGES_PANEL, DELETE_LANGUAGES_PANEL, SEARCH_LANGUAGES_PANEL
    }

}
