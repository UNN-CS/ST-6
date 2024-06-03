package org.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class TicTacToeCellTest {
    @Test
    public void testCellInitialization() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(0, cell.getNum());
        assertEquals(' ', cell.getMarker());
    }

    @Test
    public void testSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
    }
}

