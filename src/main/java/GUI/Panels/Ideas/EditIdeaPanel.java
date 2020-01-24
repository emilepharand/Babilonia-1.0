package GUI.Panels.Ideas;

import GUI.Factories.*;
import GUI.GUIConstants;
import GUI.Panels.AbstractIdeaModifyPanel;

import javax.swing.*;
import java.util.ArrayList;

public class EditIdeaPanel extends AbstractIdeaModifyPanel {

    private static final String TITLE = "Edit Idea";
    private static final String HEADER = "Edit Idea";
    private static final String MORE_BUTTON_TEXT = "More";
    private static final String SAVE_BUTTON_TEXT = "Save";
    private static final String SAVE_CONFIRMATION = "Idea has been saved.";

    public void prepare() {
        title = TITLE;
        header = HEADER;
        int ideaId = ideaManager.getCurrentIdeaId();
        super.prepare();
        ArrayList<Integer> wordIds = ideaManager.getWordIdsForIdea(ideaId);
        for (int wordId : ideaManager.getWordIdsForIdea(ideaId))
            addToColumns(languages, ideaManager.getLanguageIdForWord(wordId),
                    ideaManager.getFullWord(wordId));
        minimumFieldSize = wordIds.size();
    }

    @Override
    protected void addRows() {
        addRowsFromColumns();
    }

    protected void addButtons() {
        JPanel buttonPanel = ButtonPanelFactory.createTwoButtonPanel(
                MORE_BUTTON_TEXT, actionEvent -> addMoreFields(),
                SAVE_BUTTON_TEXT, actionEvent -> defaultAction());
        addButtonPanel(buttonPanel);
    }

    protected void defaultAction() {
        if (checkIdeaValidity()) {
            saveIdea();
            guiManager.showDialog(SAVE_CONFIRMATION, "");
            guiManager.showPanel(GUIConstants.PANEL_TYPE.EDIT_IDEA_PANEL);
        }
    }

    /**
     * Saves idea by deleting it and recreating it with current info.
     */
    private void saveIdea() {
        int ideaId = ideaManager.getCurrentIdeaId();
        ideaManager.deleteIdea(ideaId);
        createIdea(ideaId);
    }

}
