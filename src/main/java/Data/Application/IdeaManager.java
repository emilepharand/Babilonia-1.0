package Data.Application;

import Data.Database;
import Data.Errors.NoSuchIdeaError;
import Data.Errors.NoSuchWordError;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class manages the data related to ideas and their words.
 * It ensures consistency in the data.
 */

public class IdeaManager {

    // Ideas and words
    private TreeMap<Integer, Idea> ideaTreeMap = new TreeMap<>();
    private TreeMap<Integer, Word> wordTreeMap = new TreeMap<>();

    // Practiceable ideas
    private ArrayList<Idea> practiceableIdeas = new ArrayList<>();
    private Iterator<Idea> ideaIterator;
    private boolean newPracticeListNeeded;

    // Context
    private int currentIdeaId;

    // Data objects
    private Database database;
    private DataManager dataManager;
    private LanguageManager languageManager;

    /**
     * <code>IdeaManager</code> constructor.
     * Called only once by <code>DataManager</code>.
     *
     * @param dataManager <code>DataManager</code> object
     */
    IdeaManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * Called only once by <code>DataManager</code>.
     */
    void init() {
        database = dataManager.getDatabase();
        languageManager = dataManager.getLanguageManager();
        database.loadIdeas(this);
        generatePracticeList();
        goToNextIdea();
    }

    /**
     * Generates list of practiceable ideas.
     */
    void generatePracticeList() {
        if (ideaTreeMap.size() > 0) {
            practiceableIdeas = ideaTreeMap.values().parallelStream()
                    .filter(this::isIdeaPracticeable)
                    .collect(Collectors.toCollection(ArrayList::new));
            if (practiceableIdeas.size() > 0) {
                Collections.shuffle(practiceableIdeas);
                ideaIterator = practiceableIdeas.iterator();
            }
        }
        newPracticeListNeeded = false;
    }

    /**
     * Checks if an idea is practiceable.
     * An idea is practiceable if it has at least one word in a practice language,
     * but not all of them.
     *
     * @param idea the idea
     * @return <code>true</code> if practiceable, <code>false</code> otherwise
     */
    private boolean isIdeaPracticeable(Idea idea) {
        int numberOfPracticeWords = (int) idea.getWords()
                .parallelStream()
                .map(Word::getLanguageId)
                .filter(languageId -> languageManager.isLanguagePractice(languageId))
                .count();
        return numberOfPracticeWords > 0 && numberOfPracticeWords < idea.getWords().size();
    }

    /**
     * Gets next idea in practice list.
     * If there are no practiceable ideas, current idea id is set to -1.
     */
    public void goToNextIdea() {
        if (newPracticeListNeeded) generatePracticeList();
        if (practiceableIdeas.size() > 0) {
            if (!ideaIterator.hasNext()) {
                Collections.shuffle(practiceableIdeas);
                ideaIterator = practiceableIdeas.iterator();
            }
            currentIdeaId = ideaIterator.next().getId();
        } else {
            currentIdeaId = -1;
        }
    }

    /**
     * Returns current idea id.
     *
     * @return current idea id (-1 if no practiceable ideas)
     */
    public int getCurrentIdeaId() {
        if (!ideaTreeMap.containsKey(currentIdeaId) && currentIdeaId != -1)
            throw new NoSuchIdeaError(dataManager);
        return currentIdeaId;
    }

    /**
     * Sets current idea id.
     * Used for editing an idea.
     *
     * @param id idea id
     */
    public void setCurrentIdeaId(int id) {
        if (!ideaTreeMap.containsKey(id))
            throw new NoSuchIdeaError(dataManager);
        this.currentIdeaId = id;
    }

    /**
     * Creates an empty idea in internal structure and returns its id.
     *
     * @return idea id
     */
    public int createIdea() {
        int id = getNewIdeaId();
        Idea idea = new Idea(id);
        ideaTreeMap.put(id, idea);
        return id;
    }

    /**
     * Returns next available idea id.
     *
     * @return next available idea id
     */
    private int getNewIdeaId() {
        if (ideaTreeMap.keySet().size() > 0)
            return Collections.max(ideaTreeMap.keySet()) + 1;
        else return 0;
    }

    /**
     * Returns next available word id.
     *
     * @return next available word id
     */
    private int getNewWordId() {
        if (wordTreeMap.keySet().size() > 0)
            return Collections.max(wordTreeMap.keySet()) + 1;
        else return 0;
    }

    /**
     * Adds a new word from GUI.
     * Word is added to internal structure and is also written to database.
     *
     * @param ideaId     idea id
     * @param languageId language id
     * @param wordString word
     */
    public void addWordFromGUI(int ideaId, int languageId, String wordString) {
        int wordId = getNewWordId();
        addWord(wordId, ideaId, languageId, wordString);
        database.insertWord(wordId, ideaId, languageId, wordString);
        newPracticeListNeeded = true;
    }

    /**
     * Adds a word to internal structure but not to database.
     *
     * @param ideaId     idea id
     * @param wordId     word id
     * @param languageId language id
     * @param wordString word
     */
    private void addWord(int wordId, int ideaId, int languageId, String wordString) {
        Idea idea = getOrCreateIdea(ideaId);
        if (!idea.containsLanguage(languageId))
            languageManager.incrementIdeaCount(languageId);
        Word word = new Word(wordId, wordString, languageId);
        wordTreeMap.put(wordId, word);
        idea.addWord(word);
    }

    /**
     * Creates an idea if it does not already exists.
     *
     * @param id idea id
     * @return the <code>Idea</code>
     */
    private Idea getOrCreateIdea(int id) {
        if (!ideaTreeMap.containsKey(id)) {
            Idea idea = new Idea(id);
            ideaTreeMap.put(id, idea);
            return idea;
        } else {
            return getIdeaById(id);
        }
    }

    /**
     * Loads a word from database.
     * Word is added to internal structure but it is not written to database.
     * <code>Database</code> object is required to ensure only a
     * <code>Database</code> object can call it.
     *
     * @param database   <code>Database</code> object
     * @param wordId     word id
     * @param ideaId     idea id
     * @param languageId language id
     * @param wordString word
     */
    public void loadWord(Database database, int wordId, int ideaId,
                         int languageId, String wordString) {
        addWord(wordId, ideaId, languageId, wordString);
    }

    /**
     * Deletes an idea from internal structure and from database too.
     *
     * @param ideaId idea id
     */
    public void deleteIdea(int ideaId) {
        if (!ideaTreeMap.containsKey(ideaId))
            throw new NoSuchIdeaError(dataManager);
        decrementIdeaCountForLanguages(ideaId);
        ideaTreeMap.remove(ideaId);
        database.deleteIdea(ideaId);
        newPracticeListNeeded = true;
    }

    /**
     * Decrements idea count for unique languages associated with idea.
     *
     * @param ideaId idea id
     */
    private void decrementIdeaCountForLanguages(int ideaId) {
        getIdeaById(ideaId).getWords().stream()
                .map(Word::getLanguageId)
                .distinct()
                .forEach(languageId -> languageManager.decrementIdeaCount(languageId));
    }

    /**
     * Returns an idea's list of words, which is sorted by language order.
     *
     * @param ideaId idea id
     * @return list of words
     */
    public ArrayList<Integer> getWordIdsForIdea(int ideaId) {
        return getIdeaById(ideaId)
                .getWords()
                .stream()
                .sorted(Comparator.comparingInt
                        (word -> languageManager.getLanguageOrder(word.getLanguageId())))
                .map(Word::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Returns word's <code>String</code> representation for word id.
     *
     * @param wordId word id
     * @return word's <code>String</code> representation
     */
    public String getWordMainSpelling(int wordId) {
        return getWordById(wordId).getWordMainSpelling();
    }

    /**
     * Returns full word for word id.
     *
     * @param wordId word id
     * @return full word
     */
    public String getFullWord(int wordId){
        return getWordById(wordId).getFullWord();
    }

    /**
     * Returns spellings for word id.
     *
     * @param wordId word id
     * @return spellings
     */
    public ArrayList<String> getSpellings(int wordId) {
        return getWordById(wordId).getSpellings();
    }

    /**
     * Returns word's language <code>String</code> representation for word id.
     *
     * @param wordId word id
     * @return word's language <code>String</code> representation
     */
    public String getLanguageStringForWord(int wordId) {
        return languageManager.getLanguageName(getLanguageIdForWord(wordId));
    }

    /**
     * Returns language id of a word.
     *
     * @param wordId word id
     * @return language id
     */
    public int getLanguageIdForWord(int wordId) {
        return getWordById(wordId).getLanguageId();
    }

    /**
     * Returns the <code>String</code> representation of an idea.
     *
     * @param id idea id
     * @return <code>String</code> representation of idea
     */
    public String getIdeaString(int id) {
        StringBuilder str = new StringBuilder();
        for (Word word : getIdeaById(id).getWords()) {
            str
                    .append(languageManager.getLanguageName(word.getLanguageId()))
                    .append(" : ")
                    .append(word.getWordMainSpelling())
                    .append(", ");
        }
        str = new StringBuilder(str.substring(0, str.length() - 2));
        return str.toString();
    }

    /**
     * Searches for an idea and returns the ids of the ones matching.
     * Full match only.
     *
     * @param searchText search text
     * @return matching idea ids
     */
    public ArrayList<Integer> search(String searchText) {
        return ideaTreeMap.entrySet()
                .parallelStream()
                .filter(entry -> entry.getValue()
                        .getWords()
                        .stream().anyMatch(x -> x.getWordMainSpelling().equals(searchText)))
                .map(Map.Entry::getValue)
                .map(Idea::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Searches for ideas that have at least one word in specified language id.
     *
     * @param id language id
     * @return matching ideas
     */
    public ArrayList<Integer> searchByLanguageId(int id) {
        return ideaTreeMap.entrySet()
                .parallelStream()
                .filter(entry -> entry.getValue()
                        .getWords()
                        .stream().anyMatch(x -> x.getLanguageId() == id))
                .map(Map.Entry::getValue)
                .map(Idea::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Deletes all words that are associated with a language id.
     *
     * @param languageId language id
     */
    public void deleteWordsWithLanguageId(int languageId) {
        ideaTreeMap
                .values()
                .parallelStream()
                .map(Idea::getWords)
                .forEach(x -> x.removeIf(word -> word.getLanguageId() == languageId));
        newPracticeListNeeded = true;
    }

    /**
     * Returns total number of ideas.
     *
     * @return idea count
     */
    public int getIdeaCount() {
        return ideaTreeMap.entrySet().size();
    }

    /**
     * Used to flag that a new practice list must be generated.
     */
    void setNewPracticeListNeededTrue() {
        newPracticeListNeeded = true;
    }

    private Idea getIdeaById(int id) {
        if (!ideaTreeMap.containsKey(id))
            throw new NoSuchIdeaError(dataManager);
        return ideaTreeMap.get(id);
    }

    private Word getWordById(int id) {
        if (!wordTreeMap.containsKey(id))
            throw new NoSuchWordError(dataManager);
        return wordTreeMap.get(id);
    }

    /**
     * Removes all words and all ideas from internal structure.
     */
    void removeAll() {
        ideaTreeMap.clear();
        wordTreeMap.clear();
    }

}
