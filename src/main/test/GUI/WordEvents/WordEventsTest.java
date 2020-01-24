package GUI.WordEvents;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class WordEventsTest {

    WordEvents wordEvents;

    @Before
    public void setUp() {
        wordEvents = new WordEvents();
        wordEvents.enabled = true;
    }

    @Test
    public void colorTextOrChangeFocusTestNoMatch() {
        wordEvents.textField = new JTextField("2");
        wordEvents.spellings = new ArrayList<>(Arrays.asList("123", "321"));
        assert (wordEvents.checkText() == WordEvents.TEXT_STATUS.NO_MATCH);
    }

    @Test
    public void colorTextOrChangeFocusTestPartialMatch() {
        wordEvents.textField = new JTextField("3");
        wordEvents.spellings = new ArrayList<>(Arrays.asList("321", "123"));
        assert (wordEvents.checkText() == WordEvents.TEXT_STATUS.PARTIAL_MATCH);
    }

    @Test
    public void colorTextOrChangeFocusTestPartialMatch2() {
        wordEvents.textField = new JTextField("");
        wordEvents.spellings = new ArrayList<>(Arrays.asList("123", "321"));
        assert (wordEvents.checkText() == WordEvents.TEXT_STATUS.PARTIAL_MATCH);
    }

    @Test
    public void colorTextOrChangeFocusTestFullMatch() {
        wordEvents.textField = new JTextField("123");
        wordEvents.spellings = new ArrayList<>(Arrays.asList("321", "123"));
        assert (wordEvents.checkText() == WordEvents.TEXT_STATUS.FULL_MATCH);
    }

}
