package com.mycompany.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeCellTest {

    @Test
    public void testCellInitialization() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);
        assertEquals(1, cell.getNum());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertEquals(' ', cell.getMarker());
    }

    @Test
    public void testSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }
}