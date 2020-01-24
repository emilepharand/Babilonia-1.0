package Data;

import Data.Application.IdeaManager;
import Data.Application.LanguageManager;
import Data.Application.SettingsManager;


/**
 * This interface separates the database implementation
 * from the actual data used in the application.
 */

public interface Database {

    void openConnection();
    void closeConnection();
    void loadIdeas(IdeaManager ideaManager);
    void loadLanguages(LanguageManager languageManager);
    void loadSettings(SettingsManager settingsManager);
    void insertWord(int wordId, int ideaId, int languageId, String word);
    void deleteIdea(int id);
    void updateLanguageOrder(int id, int order);
    void updateLanguageName(int id, String newName);
    void deleteLanguage(int id);
    void insertLanguage(int id, String name, int order, boolean practice);
    void updateSetting(String name, String value);
    void updatePracticeLanguage(int id, boolean practice);
    void resetEverything();

}
