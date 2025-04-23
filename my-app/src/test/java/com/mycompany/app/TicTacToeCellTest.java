package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TicTacToeCellTest {

    @Test
    public void testCellInitialization() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);

        assertEquals(0, cell.getNum());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertEquals(' ', cell.getMarker());
    }

    @Test
    public void testSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);

        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled()); // Button should be disabled after marking

        // Test the text is set correctly
        assertEquals("X", cell.getText());
    }

    @Test
    public void testGetters() {
        TicTacToeCell cell = new TicTacToeCell(5, 1, 2);

        assertEquals(5, cell.getNum());
        assertEquals(2, cell.getRow());
        assertEquals(1, cell.getCol());
    }
}