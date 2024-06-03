package org.example;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class TicTacToePanelTest {
    @Test
    public void testTicTacToePanel() {
        int side = 3;
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(side, side));
        assertNotNull(panel);
        assertEquals(side * side, panel.getComponentCount());
    }
}
