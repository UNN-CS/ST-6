package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicTacToeCellTests {
    TicTacToeCell cell;

    @BeforeEach
    void setUp() {
        cell = new TicTacToeCell(0, 0, 0);
    }

    @Test
    void testInitialCellState() {
        assertEquals(' ', cell.getMarker());
        assertTrue(cell.isEnabled());
    }

    @Test
    void testSetMarker() {
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    void testGetNum() {
        assertEquals(0, cell.getNum());
    }

    @Test
    void testGetRowAndCol() {
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
    }
}
