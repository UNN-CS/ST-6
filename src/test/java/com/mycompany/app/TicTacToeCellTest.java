package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TicTacToeCellTest {

    @Test
    void testCellCreation() {
        TicTacToeCell cell = new TicTacToeCell(4, 1, 2);
        assertEquals(4, cell.getNum());
        assertEquals(1, cell.getCol());
        assertEquals(2, cell.getRow());
        assertEquals(' ', cell.getMarker());
    }

    @Test
    void testSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    void testSetMarkerO() {
        TicTacToeCell cell = new TicTacToeCell(5, 2, 1);
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    void testMarkerInitiallyEmpty() {
        TicTacToeCell cell = new TicTacToeCell(1, 1, 1);
        assertEquals(' ', cell.getMarker());
    }
}