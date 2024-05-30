package org.example;

import org.junit.jupiter.api.Assertions;
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
    public void TestInitialValues() {
        Assertions.assertEquals(' ', cell.getMarker());
        Assertions.assertEquals(0, cell.getNum());
        Assertions.assertEquals(0, cell.getRow());
        Assertions.assertEquals(0, cell.getCol());
    }

    @Test
    public void TestGetCol() {
        assertEquals(0, cell.getCol());
    }

    @Test
    public void TestGetNum() {
        assertEquals(0, cell.getNum());
    }

    @Test
    public void TestSetMarker() {
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    public void TestGetRow() {
        assertEquals(0, cell.getRow());
    }
}
