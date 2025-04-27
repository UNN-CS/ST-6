package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javax.swing.JButton;

public class TicTacToeCellTest {
    
    @Test
    void testConstructor() {
        TicTacToeCell cell = new TicTacToeCell(5, 1, 2);
        
        assertEquals(5, cell.getNum());
        assertEquals(2, cell.getRow());
        assertEquals(1, cell.getCol());
        assertEquals(' ', cell.getMarker());
        assertTrue(cell.isEnabled());
    }
    
    @Test
    void testSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
        
        cell = new TicTacToeCell(1, 1, 0);
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
        assertFalse(cell.isEnabled());
    }
    
    @Test
    void testGetMethods() {
        TicTacToeCell cell = new TicTacToeCell(3, 0, 1);
        
        assertEquals(3, cell.getNum());
        assertEquals(1, cell.getRow());
        assertEquals(0, cell.getCol());
        assertEquals(' ', cell.getMarker());
    }
} 