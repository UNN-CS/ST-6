package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class TicTacToeCellTest {

    @Test
    public void testTicTacToeCell() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(0, cell.getNum());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertEquals(' ', cell.getMarker());
        assertTrue(cell.isEnabled());

        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }
}
