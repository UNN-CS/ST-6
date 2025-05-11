package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import java.awt.*;

class TicTacToePanelTest {
    private TicTacToePanel panel;

    @BeforeEach
    void init() {
        panel = new TicTacToePanel(new GridLayout(3, 3));
    }

    @Test
    void TicTacToePanel_Constructor() {
        for (Component comp : panel.getComponents()) {
            assertTrue(comp instanceof TicTacToeCell);
            TicTacToeCell cell = (TicTacToeCell) comp;
            assertEquals(' ', cell.getMarker());
            assertTrue(cell.isEnabled());
        }
    }

    @Test
    void doClick_SetsXAndDisablesIt() {
        TicTacToeCell cell = (TicTacToeCell) panel.getComponent(0);
        cell.doClick();
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }
}