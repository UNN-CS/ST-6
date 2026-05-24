package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicTacToeCellTest {

    private TicTacToeCell cell;

    @BeforeEach
    void setUp() {
        System.setProperty("java.awt.headless", "true");
        cell = new TicTacToeCell(4, 1, 1);
    }

    @Test
    void constructorSetsFields() {
        assertEquals(4, cell.getNum());
        assertEquals(1, cell.getRow());
        assertEquals(1, cell.getCol());
        assertEquals(' ', cell.getMarker());
        assertEquals(" ", cell.getText());
    }

    @Test
    void setMarkerUpdatesCell() {
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertEquals("X", cell.getText());
        assertFalse(cell.isEnabled());
    }

    @Test
    void setMarkerO() {
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
    }
}
