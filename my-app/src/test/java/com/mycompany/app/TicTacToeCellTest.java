package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicTacToeCellTest {

    private TicTacToeCell cell;

    @BeforeEach
    void setUp() {
        cell = new TicTacToeCell(4, 1, 2);
    }

    @Test
    void testInitialState() {
        assertEquals(4, cell.getNum());
        assertEquals(1, cell.getCol());
        assertEquals(2, cell.getRow());
        assertEquals(' ', cell.getMarker());
        assertTrue(cell.isEnabled());
    }

    @Test
    void testSetMarkerDisablesCell() {
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    void testMultipleMarkersIgnoreAfterFirst() {
        cell.setMarker("X");
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
    }

    @Test
    void testTextMatchesMarker() {
        cell.setMarker("O");
        assertEquals("O", cell.getText());
    }
}
