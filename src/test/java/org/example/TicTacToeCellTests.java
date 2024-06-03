package org.example;

import org.example.TicTacToeCell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeCellTests {

    private TicTacToeCell cell;

    @BeforeEach
    public void initialize() {
        cell = new TicTacToeCell(0, 0, 0);
    }

    @Test
    public void checkInitialValues() {
        assertEquals(' ', cell.getMarker());
        assertEquals(0, cell.getNum());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
    }

    @Test
    public void checkSetMarker() {
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    public void checkGetRow() {
        assertEquals(0, cell.getRow());
    }

    @Test
    public void checkGetCol() {
        assertEquals(0, cell.getCol());
    }

    @Test
    public void checkGetNum() {
        assertEquals(0, cell.getNum());
    }
}
