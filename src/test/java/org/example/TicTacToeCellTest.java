package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeCellTest {
    private TicTacToeCell cell;

    @Test
    public void testInitialCellState() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(' ', cell.getMarker());
        assertEquals(0, cell.getNum());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
    }
    @Test
    public void testGetRow() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(0, cell.getRow());
    }
    @Test
    public void testGetCol() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(0, cell.getCol());
    }
    @Test
    public void testGetNum() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(0, cell.getNum());
    }
    @Test
    public void testSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }
} 
