package GUI.WordEvents;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class WordEventsListener implements DocumentListener {

    Runnable runnable;

    public WordEventsListener(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void insertUpdate(DocumentEvent documentEvent) {
        SwingUtilities.invokeLater(runnable);
    }

    @Override
    public void removeUpdate(DocumentEvent documentEvent) {
        SwingUtilities.invokeLater(runnable);
    }

    @Override
    public void changedUpdate(DocumentEvent documentEvent) {
        SwingUtilities.invokeLater(runnable);
    }

}
