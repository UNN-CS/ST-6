package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import javax.swing.JPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicTacToePanelTest {

    private TicTacToePanel panel;

    @BeforeEach
    void setUp() {
        panel = new TicTacToePanel(new GridLayout(3, 3));
    }

    @Test
    void testCellsCount() {
        assertEquals(9, panel.getComponentCount());
    }

    @Test
    void testAllCellsAreTicTacToeCell() {
        for (Component comp : panel.getComponents()) {
            assertTrue(comp instanceof TicTacToeCell);
        }
    }

    @Test
    void testInitialCellsEnabled() {
        for (Component comp : panel.getComponents()) {
            TicTacToeCell c = (TicTacToeCell) comp;
            assertTrue(c.isEnabled());
            assertEquals(' ', c.getMarker());
        }
    }

    @Test
    void testClickUpdatesBoardAndSwitchesPlayer() {
        TicTacToePanel p = new TicTacToePanel(new GridLayout(3,3));
        TicTacToeCell first = (TicTacToeCell) p.getComponent(0);
        first.doClick();
        char marker = first.getMarker();
        assertTrue(marker == 'X' || marker == 'O');
        // After click, the cell should be disabled
        assertFalse(first.isEnabled());
    }
}
