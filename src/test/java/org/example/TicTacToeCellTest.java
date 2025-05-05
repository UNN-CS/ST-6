package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TicTacToeCellTest {
    private TicTacToeCell cell;

    @BeforeEach
    void setUp() {
        cell = new TicTacToeCell(1, 0, 1);
    }

    @Test
    void testInitialState() {
        assertEquals(1, cell.getNum());
        assertEquals(0, cell.getCol());
        assertEquals(1, cell.getRow());
        assertEquals(' ', cell.getMarker());
        assertTrue(cell.isEnabled());
    }

    @Test
    void testSetMarker() {
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertEquals("X", cell.getText());
        assertFalse(cell.isEnabled());
    }

    @Test
    void testGetters() {
        assertEquals(1, cell.getNum());
        assertEquals(0, cell.getCol());
        assertEquals(1, cell.getRow());
    }
}