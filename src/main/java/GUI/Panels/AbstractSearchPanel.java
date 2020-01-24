package GUI.Panels;

import javax.swing.*;
import java.util.ArrayList;

import static GUI.GUIConstants.DEFAULT_NUMBER_OF_FIELDS;
import static GUI.GUIConstants.ROW_HEIGHT;

/**
 * Groups attributes and behavior common to
 * <code>SearchIdeasPanel</code> and <code>SearchLanguagesPanel</code>.
 */
public abstract class AbstractSearchPanel extends AbstractPanel {

    protected JList<String> jList = new JList<>();

    protected JScrollPane makeScrollPane(ArrayList<Integer> ideasFound) {
        DefaultListModel<String> listModel = makeListModel(ideasFound);
        jList = makeJList(listModel);
        return new JScrollPane(jList);
    }

    protected DefaultListModel<String> makeListModel(ArrayList<Integer> ideasFound) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        ideasFound.forEach(ideaId -> listModel.addElement
                (ideaManager.getIdeaString(ideaId)));
        return listModel;
    }

    protected JList<String> makeJList(DefaultListModel<String> listModel) {
        jList = new JList<>(listModel);
        jList.setFixedCellHeight(ROW_HEIGHT);
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList.setSelectedIndex(0);
        jList.setVisibleRowCount(DEFAULT_NUMBER_OF_FIELDS - 2);
        return jList;
    }

}
