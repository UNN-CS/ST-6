package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeCellTests {
    private TicTacToeCell cell;

    @BeforeEach
    public void setUp() {
        cell = new TicTacToeCell(0, 0, 0);
    }

    @Test
    public void initialCellState_Test() {
        assertEquals(' ', cell.getMarker());
        assertEquals(0, cell.getNum());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
    }

    @Test
    public void getNum_Test() {
        assertEquals(0, cell.getNum());
    }

    @Test
    public void getRow_Test() {
        assertEquals(0, cell.getRow());
    }

    @Test
    public void getCol_Test() {
        assertEquals(0, cell.getCol());
    }

    @Test
    public void setMarker_Test() {
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }
} 
