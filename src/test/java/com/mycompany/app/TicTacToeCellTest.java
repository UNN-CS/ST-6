package com.mycompany.app;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TicTacToeCellTest {
    private TicTacToeCell cell;
    
    @Before
    public void setUp() {
        cell = new TicTacToeCell(4, 1, 1);
    }
    
    @Test
    public void testCellInitialization() {
        assertEquals(' ', cell.getMarker());
        assertEquals(4, cell.getNum());
        assertEquals(1, cell.getRow());
        assertEquals(1, cell.getCol());
        assertTrue(cell.isEnabled());
    }
    
    @Test
    public void testSetMarker() {
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertEquals("X", cell.getText());
        assertFalse(cell.isEnabled());
    }
    
    @Test
    public void testSetMarkerO() {
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
        assertEquals("O", cell.getText());
        assertFalse(cell.isEnabled());
    }
    
    @Test
    public void testMultipleSetMarker() {
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
    }
}