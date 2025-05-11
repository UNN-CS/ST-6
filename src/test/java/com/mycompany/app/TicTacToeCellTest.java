package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

class TicTacToeCellTest {
    private TicTacToeCell cell;

    @BeforeEach
    void init() {
        cell = new TicTacToeCell(5, 1, 2);
    }

    @Test
    void TicTacToeCell_Constructor() {
        assertEquals(' ', cell.getMarker());
        assertEquals(1, cell.getCol());
        assertEquals(2, cell.getRow());
        assertEquals(5, cell.getNum());
    }

    @Test
    void setMarker_DisablesButton() {
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }
}
