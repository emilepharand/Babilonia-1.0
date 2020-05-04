package Data.SQL;

import Data.Database;
import Data.Application.SettingsManager;
import Data.Application.LanguageManager;
import Data.Application.IdeaManager;

/**
 * SQL implementation of the Database interface.
 */

public class SQLDatabase implements Database {

    private static SQLConnection sqlConnection = new SQLConnection();

    public SQLDatabase() {
    }

    @Override
    public void openConnection() {
        sqlConnection = new SQLConnection();
        sqlConnection.connect();
    }

    @Override
    public void closeConnection() {
        sqlConnection.disconnect();
    }

    /**
     * Helper function for SQL queries.
     *
     * @param query    SQL query
     * @param errorMsg error message to be shown
     */
    private static void executeUpdate(String query, String errorMsg) {
        SQLTask sqlTask = new SQLTask(sqlConnection);
        sqlTask.setQuery(query);
        sqlTask.setErrorMsg(errorMsg);
        sqlTask.setProcedure(() -> {
        });
        sqlTask.executeUpdate();
    }

    /**
     * Fetches ideas from database and fills <code>IdeaManager</code>
     *
     * @param ideaManager <code>IdeaManager</code> object
     */
    @Override
    public void loadIdeas(IdeaManager ideaManager) {
        SQLTask sqlTask = new SQLTask(sqlConnection);
        sqlTask.setQuery("SELECT * FROM ideas");
        sqlTask.setErrorMsg("Fatal database error while loading ideas.");
        sqlTask.setProcedure(() -> {
            while (sqlTask.nextRow()) {
                int wordId = sqlTask.getNextInt("word_id");
                int ideaId = sqlTask.getNextInt("idea_id");
                int languageId = sqlTask.getNextInt("language_id");
                String wordString = sqlTask.getNextString("word");
                ideaManager.loadWord(this, wordId, ideaId,
                        languageId, wordString);
            }
        });
        sqlTask.executeQuery();
    }

    /**
     * Fetches languages from database and fills <code>LanguageManager</code>
     *
     * @param languageManager <code>LanguageManager</code> object
     */
    @Override
    public void loadLanguages(LanguageManager languageManager) {
        SQLTask sqlTask = new SQLTask(sqlConnection);
        sqlTask.setQuery("SELECT id, name, \"order\", practice FROM languages");
        sqlTask.setErrorMsg("Fatal database error while getting languages.");
        sqlTask.setProcedure(() -> {
            while (sqlTask.nextRow()) {
                int id = sqlTask.getNextInt("id");
                String name = sqlTask.getNextString("name");
                int order = sqlTask.getNextInt("order");
                boolean practice = sqlTask.getNextBoolean("practice");
                languageManager.createLanguage(this, id, name,
                        order, practice);
            }
        });
        sqlTask.executeQuery();
    }

    /**
     * Fetches settings from database and fills <code>SettingsManager</code>
     *
     * @param settingsManager <code>SettingsManager</code> object
     */
    @Override
    public void loadSettings(SettingsManager settingsManager) {
        SQLTask sqlTask = new SQLTask(sqlConnection);
        sqlTask.setQuery("SELECT name, value FROM settings");
        sqlTask.setErrorMsg("Fatal database error while loading settings");
        sqlTask.setProcedure(() -> {
            while (sqlTask.nextRow()) {
                String name = sqlTask.getNextString("name");
                String value = sqlTask.getNextString("value");
                settingsManager.setSettings(name, value);
            }
        });
        sqlTask.executeQuery();
    }

    /**
     * Writes a word to database.
     *
     * @param wordId     word id
     * @param ideaId     idea id
     * @param languageId language id
     * @param word       word
     */
    @Override
    public void insertWord(int wordId, int ideaId, int languageId, String word) {
        SQLTask sqlTask = new SQLTask(sqlConnection);
        sqlTask.setQuery("INSERT INTO ideas(word_id, idea_id, language_id, word)" +
                " VALUES(" + wordId + "," + ideaId + "," + languageId + ",'" + word + "')");
        sqlTask.setErrorMsg("Fatal database error while inserting an idea.");
        sqlTask.setProcedure(() -> {
        });
        sqlTask.executeUpdate();
    }

    /**
     * Deletes an idea from databse.
     *
     * @param id idea id
     */
    @Override
    public void deleteIdea(int id) {
        String query = "DELETE FROM ideas WHERE idea_id = " + id;
        String errorMsg = "Fatal database error while deleting an idea.";
        executeUpdate(query, errorMsg);
    }

    /**
     * Writes a new language to database.
     *
     * @param id       language id
     * @param name     language name
     * @param order    language order
     * @param practice is practice or not
     */
    @Override
    public void insertLanguage(int id, String name, int order, boolean practice) {
        String query = "INSERT INTO languages(id, name, \"order\", practice) " +
                "VALUES(" + id + ", '" + name + "'," + order + ", \"" + practice + "\")";
        String errorMsg = ("Fatal database error while adding new language.");
        executeUpdate(query, errorMsg);
    }

    /**
     * Updates language name in database.
     *
     * @param id      language id
     * @param newName new language name
     */
    @Override
    public void updateLanguageName(int id, String newName) {
        String query = "UPDATE languages SET \"name\" = '" + newName
                + "' WHERE \"id\" = \"" + id + "\"";
        String errorMsg = ("Fatal database error while updating language name.");
        executeUpdate(query, errorMsg);
    }

    /**
     * Updates language order in database.
     *
     * @param id    idea id
     * @param order idea order
     */
    @Override
    public void updateLanguageOrder(int id, int order) {
        String query = "UPDATE languages SET \"order\" = \"" + order
                + "\" WHERE \"id\" = \"" + id + "\"";
        String errorMsg = "Fatal database error while saving language order.";
        executeUpdate(query, errorMsg);
    }

    /**
     * Deletes a language and associated ideas from database.
     *
     * @param id language id
     */
    @Override
    public void deleteLanguage(int id) {
        String query1 = "DELETE FROM languages WHERE \"id\" = \"" + id + "\"";
        String query2 = "DELETE FROM ideas WHERE \"language_id\" = \"" + id + "\"";
        String errorMsg = "Fatal database error while deleting language.";
        executeUpdate(query1, errorMsg);
        executeUpdate(query2, errorMsg);
    }

    /**
     * Updates practice setting for language in database.
     *
     * @param id       language id
     * @param practice is practice or not
     */
    @Override
    public void updatePracticeLanguage(int id, boolean practice) {
        String query = "UPDATE languages SET practice = \"" + practice
                + "\" WHERE id = \"" + id + "\"";
        String errorMsg = ("Fatal database error while updating practice languages.");
        executeUpdate(query, errorMsg);
    }

    /**
     * Updates a setting in database.
     *
     * @param name  settings name
     * @param value settings value
     */
    @Override
    public void updateSetting(String name, String value) {
        String query = "UPDATE settings SET \"value\" = '" + value
                + "' WHERE name = \"" + name + "\"";
        String errorMsg = ("Fatal database error while updating username.");
        executeUpdate(query, errorMsg);
    }

    /**
     * Wipes all data from database, leaving only empty tables.
     */
    @Override
    public void resetEverything() {
        String query1 = "DELETE FROM languages";
        String query2 = "DELETE FROM ideas";
        String query3 = "UPDATE settings SET 'value'=''";
        String errorMsg = "Fatal database error while resetting.";
        executeUpdate(query1, errorMsg);
        executeUpdate(query2, errorMsg);
        executeUpdate(query3, errorMsg);
    }

}
