package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Font;

public class TicTacToeCellTest {

    @Test
    public void testInitialState() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);
        assertEquals(' ', cell.getMarker());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertEquals(1, cell.getNum());
    }

    @Test
    public void testSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    public void testSetFont() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);
        Font font = cell.getFont();
        assertEquals("Arial", font.getName());
        assertEquals(40, font.getSize());
    }
}
