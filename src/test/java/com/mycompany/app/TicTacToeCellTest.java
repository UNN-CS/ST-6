package com.mycompany.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TicTacToeCellTest {
    @Test
    public void testConstructor() {
        TicTacToeCell c = new TicTacToeCell(3, 2, 1);
        assertEquals(3, c.getNum());
        assertEquals(2, c.getCol());
        assertEquals(1, c.getRow());
        assertEquals(' ', c.getMarker());
    }
    @Test
    public void testSetMarker() {
        TicTacToeCell c = new TicTacToeCell(3, 2, 1);
        c.setMarker("X");
        assertEquals('X', c.getMarker());
        assertFalse(c.isEnabled());
    }
}
