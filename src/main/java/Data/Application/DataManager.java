package Data.Application;

import Data.Database;
import Data.SQL.SQLDatabase;

/**
 * Manages data objects needed by the application.
 */

public class DataManager {

    private Database database = new SQLDatabase();
    private LanguageManager languageManager = new LanguageManager(this);
    private IdeaManager ideaManager = new IdeaManager(this);
    private SettingsManager settingsManager = new SettingsManager(this);

    /**
     * Should be called only once by the <code>main</code> method.
     */
    public DataManager() {
    }

    /**
     * Should be called only once by the main function.
     */
    public void loadData() {
        database.openConnection();
        languageManager.init();
        ideaManager.init();
        settingsManager.init();
    }

    public IdeaManager getIdeaManager() {
        return ideaManager;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public Database getDatabase() {
        return database;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    /**
     * Removes all data from internal structures
     * as well as in the database.
     */
    public void resetEverything() {
        database.resetEverything();
        ideaManager.removeAll();
        languageManager.removeAll();
        settingsManager.removeAll();
    }

    /**
     * Normal shutdown of the application.
     */
    public void cleanShutDown() {
        database.closeConnection();
        System.exit(0);
    }

    /**
     * Called when a problem has occurred such that
     * it could leave the software in an unstable state.
     */
    public void fatalErrorShutDown() {
        database.closeConnection();
        System.exit(-1);
    }

}
