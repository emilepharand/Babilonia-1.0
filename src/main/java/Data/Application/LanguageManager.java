package Data.Application;

import Data.Database;
import Data.Errors.NoSuchLanguageError;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class manages the data related to languages.
 * It ensures consistency in the data.
 */

public class LanguageManager {

    private TreeMap<Integer, Language> languageTreeMap = new TreeMap<>();
    private Database database;
    private DataManager dataManager;
    private IdeaManager ideaManager;

    /**
     * <code>LanguageManager</code> constructor.
     * Should be called only once by <code>DataManager</code>.
     *
     * @param dataManager <code>DataManager</code> object
     */
    LanguageManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * Called only once by <code>DataManager</code>.
     */
    void init() {
        database = dataManager.getDatabase();
        ideaManager = dataManager.getIdeaManager();
        database.loadLanguages(this);
    }

    /**
     * Creates a language by specifying only its name.
     * Language is added to internal structure and is also written to database.
     * If language name already exists, no action is performed.
     *
     * @param name language name
     */
    public void createLanguageFromGUI(String name) {
        if (!nameAlreadyExists(name)) {
            int id = getNextAvailableLanguageId();
            int order = getNextAvailableOrder();
            createLanguageWithDetails(id, name, order, false);
            database.insertLanguage(id, name, order, false);
        }
    }

    /**
     * Checks if a language name already exists.
     *
     * @param name language name
     * @return <code>true</code> if already exists, <code>false</code> otherwise
     */
    private boolean nameAlreadyExists(String name) {
        return languageTreeMap.values().parallelStream()
                .anyMatch(language -> language.getName().equals(name));
    }

    /**
     * Returns next available order (at the end of the list).
     *
     * @return next available order
     */
    private int getNextAvailableOrder() {
        if (languageTreeMap.keySet().size() == 0) return 0;
        else return languageTreeMap.values().stream()
                .mapToInt(Language::getOrder).max().orElse(0) + 1;
    }

    /**
     * Creates a language with specified details.
     * <code>Database</code> object is required to ensure only a
     * <code>Database</code> object can call it.
     *
     * @param database <code>Database</code> object
     * @param id       language id
     * @param name     language name
     * @param order    language order
     * @param practice practice or not
     */
    public void createLanguage(Database database, int id, String name,
                               int order, boolean practice) {
        createLanguageWithDetails(id, name, order, practice);
    }

    /**
     * Creates a language with specified details.
     *
     * @param id       language id
     * @param name     language name
     * @param order    language order
     * @param practice practice or not
     */
    private void createLanguageWithDetails(int id, String name,
                                           int order, boolean practice) {
        Language language = new Language(id, name, order, practice);
        languageTreeMap.put(id, language);
    }

    /**
     * Finds next available language id.
     *
     * @return next available id
     */
    private int getNextAvailableLanguageId() {
        if (languageTreeMap.keySet().size() > 0)
            return Collections.max(languageTreeMap.keySet()) + 1;
        else return 0;
    }

    /**
     * Returns the id number of a language by its name.
     *
     * @param name language name
     * @return language id
     */
    public int getLanguageId(String name) {
        return languageTreeMap
                .values()
                .parallelStream()
                .filter(language -> language.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchLanguageError(dataManager))
                .getId();
    }

    /**
     * Returns the list of language ids, sorted by language order.
     *
     * @return list of language ids, sorted by language order
     */
    public ArrayList<Integer> getLanguageIds() {
        return languageTreeMap
                .values()
                .stream()
                .sorted(Comparator.comparingInt(Language::getOrder))
                .map(Language::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Returns the list of language names, sorted by language order.
     *
     * @return list of language names, sorted by language order
     */
    public ArrayList<String> getLanguageStrings() {
        return languageTreeMap
                .values()
                .stream()
                .sorted(Comparator.comparingInt(Language::getOrder))
                .map(Language::getName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Returns the name of a language by its id.
     *
     * @param id language id
     * @return language name
     */
    public String getLanguageName(int id) {
        return getLanguageById(id).getName();
    }

    /**
     * +1 to the number of ideas associated to a language.
     *
     * @param id language id
     */
    void incrementIdeaCount(int id) {
        getLanguageById(id).incrementNumberOfIdeas();
    }

    /**
     * -1 to the number of ideas associated to a language.
     *
     * @param id language id
     */
    void decrementIdeaCount(int id) {
        getLanguageById(id).decrementNumberOfIdeas();
    }

    /**
     * Returns the number of ideas associated with a language.
     *
     * @param id language id
     * @return number of ideas
     */
    public int getIdeaCountForLanguage(int id) {
        return getLanguageById(id).getIdeaCount();
    }

    /**
     * Returns the current order of a language.
     *
     * @param id language id
     * @return language order
     */
    public int getLanguageOrder(int id) {
        return getLanguageById(id).getOrder();
    }

    /**
     * Sets the order for a language.
     *
     * @param id    language id
     * @param order new language order
     */
    public void setLanguageOrder(int id, int order) {
        getLanguageById(id).setOrder(order);
        database.updateLanguageOrder(id, order);
    }

    /**
     * Changes the name of a language.
     *
     * @param id   language id
     * @param name new name
     */
    public void changeLanguageName(int id, String name) {
        getLanguageById(id).setName(name);
        database.updateLanguageName(id, name);
    }

    /**
     * Removes a language from internal structure and from database.
     *
     * @param id language id
     */
    public void deleteLanguage(int id) {
        languageTreeMap.remove(id);
        ideaManager.deleteWordsWithLanguageId(id);
        database.deleteLanguage(id);
        removeOrderGaps();
    }

    /**
     * Removes language order gaps.
     * For example:
     * Before: [0, 1, 3]
     * After: [0, 1, 2]
     */
    private void removeOrderGaps() {
        ArrayList<Language> languages = languageTreeMap.values().stream()
                .sorted(Comparator.comparingInt(Language::getOrder))
                .collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0; i < languages.size(); i++)
            setLanguageOrder(languages.get(i).getId(), i);
    }

    /**
     * Sets a language's practice language parameter.
     *
     * @param id       language id
     * @param practice practice or not
     */
    public void setPractice(int id, boolean practice) {
        getLanguageById(id).setPractice(practice);
        database.updatePracticeLanguage(id, practice);
        ideaManager.setNewPracticeListNeededTrue();
    }

    /**
     * Checks if a language is defined as practice language.
     *
     * @param id language id
     * @return <code>true</code> if practice, <code>false</code> otherwise
     */
    public boolean isLanguagePractice(int id) {
        return getLanguageById(id).isPractice();
    }

    /**
     * Returns the number of languages.
     *
     * @return count
     */
    public int getLanguageCount() {
        return languageTreeMap.keySet().size();
    }

    /**
     * Returns the <code>Language</code> instance associated with an id.
     *
     * @param id language id
     * @return <code>Language</code> instance
     */
    private Language getLanguageById(int id) {
        if (!languageTreeMap.containsKey(id))
            throw new NoSuchLanguageError(dataManager);
        return languageTreeMap.get(id);
    }

    /**
     * Removes all languages from internal structure.
     */
    void removeAll() {
        languageTreeMap.clear();
    }

}
