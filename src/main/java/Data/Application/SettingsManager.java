package Data.Application;

import Data.Database;

import java.util.*;

public class SettingsManager {

    private HashMap<String, String> settings = new HashMap<>();
    private Database database;

    public SettingsManager(DataManager dataManager) {
        this.database = dataManager.getDatabase();
    }

    void init() {
        loadSettings();
    }

    private void loadSettings() {
        database.loadSettings(this);
    }

    public void setUsername(String username) {
        database.updateSetting("username", username.replaceAll("'", "''"));
        settings.put("username", username);
    }

    public void setSettings(String name, String value) {
        settings.put(name, value);
    }

    public String getUsername() {
        return settings.get("username");
    }

    void removeAll() {
        settings.clear();
    }

}
