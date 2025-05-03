package com.andreychh;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeCellTest {
    private TicTacToeCell cell;
    private final int testNum = 5;
    private final int testRow = 1;
    private final int testCol = 2;

    @BeforeEach
    void setUp() {
        cell = new TicTacToeCell(testNum, testCol, testRow);
    }

    @Test
    void testConstructor_WhenCalled_SetsNumberRowAndColumn() {
        assertNotNull(cell);
        assertEquals(testNum, cell.getNum());
        assertEquals(testRow, cell.getRow());
        assertEquals(testCol, cell.getCol());
    }

    @Test
    void testConstructor_WhenCalled_SetsDefaultMarkerTextEnabledAndFont() {
        assertEquals(' ', cell.getMarker());
        assertEquals(" ", cell.getText());
        assertTrue(cell.isEnabled());
        assertNotNull(cell.getFont());
    }

    @Test
    void testSetMarker_WhenCalledWithX_SetsXMarkerTextAndDisables() {
        String newMarker = "X";
        cell.setMarker(newMarker);

        assertEquals('X', cell.getMarker());
        assertEquals("X", cell.getText());
        assertFalse(cell.isEnabled());
    }

    @Test
    void testSetMarker_WhenCalledWithO_SetsOMarkerTextAndDisables() {
        String newMarker = "O";
        cell.setMarker(newMarker);

        assertEquals('O', cell.getMarker());
        assertEquals("O", cell.getText());
        assertFalse(cell.isEnabled());
    }
}
