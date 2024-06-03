package org.example;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeCellTest {
    @Test
    void testInitialState() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);

        assertNotNull(cell, "Cell object should not be null");

        assertEquals(' ', cell.getMarker());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertEquals(1, cell.getNum());
    }

    @Test
    void testSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);

        assertNotNull(cell, "Cell object should not be null");

        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    void testSetFont() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);

        assertNotNull(cell, "Cell object should not be null");

        Font font = cell.getFont();
        assertNotNull(font);

        assertEquals("Arial", font.getName());
        assertEquals(40, font.getSize());
    }
}
