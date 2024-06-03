package org.example;

import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.*;

public class TicTacToePanelTest {
    @Test
    public void testPanelInitialization() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        assertNotNull(panel);
    }
}

