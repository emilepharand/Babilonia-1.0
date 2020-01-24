package Data.Application;

/**
 * Represents one language.
 * Known only by LanguageManager.
 */

public class Language {

    private String name;
    private int id;
    private int numberOfAssociatedIdeas;
    private boolean practice;
    private int order;

    Language(int id, String name, int order, boolean practice) {
        this.name = name;
        this.order = order;
        this.practice = practice;
        this.id = id;
    }

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    boolean isPractice() {
        return practice;
    }

    void setPractice(boolean practice) {
        this.practice = practice;
    }

    int getOrder() {
        return order;
    }

    void setOrder(int order) {
        this.order = order;
    }

    void incrementNumberOfIdeas() {
        numberOfAssociatedIdeas++;
    }

    void decrementNumberOfIdeas() {
        numberOfAssociatedIdeas--;
    }

    int getIdeaCount() {
        return numberOfAssociatedIdeas;
    }

}
