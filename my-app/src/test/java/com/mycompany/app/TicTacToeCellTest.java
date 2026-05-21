package com.mycompany.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeCellTest {

    @Test
    void testCellInitialization() {
        TicTacToeCell cell = new TicTacToeCell(1, 2, 0);

        assertEquals(1, cell.getNum());
        assertEquals(2, cell.getCol());
        assertEquals(0, cell.getRow());
        assertEquals(' ', cell.getMarker());
    }

    @Test
    void testSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0,0,0);

        cell.setMarker("X");

        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }
}