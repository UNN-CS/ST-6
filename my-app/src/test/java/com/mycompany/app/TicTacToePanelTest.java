package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class TicTacToePanelTest {
    
    private TicTacToePanel panel;
    
    @BeforeEach
    void setUp() {
        panel = new TicTacToePanel(new GridLayout(3, 3));
    }
    
    @Test
    void testInitialization() {
        assertEquals(9, panel.getComponentCount());
    }
    
    @Test
    void testCreateCell() {
        TicTacToeCell cell = (TicTacToeCell) panel.getComponent(0);
        assertEquals(0, cell.getNum());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertTrue(cell.isEnabled());
    }
    
    @Test
    void testActionPerformed() {
        TicTacToeCell cell = (TicTacToeCell) panel.getComponent(0);
        ActionEvent event = new ActionEvent(cell, ActionEvent.ACTION_PERFORMED, "click");
        
        panel.actionPerformed(event);
        
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }
    
    @Test
    void testGameStateTransitions() {
        TicTacToeCell cell1 = (TicTacToeCell) panel.getComponent(0);
        TicTacToeCell cell2 = (TicTacToeCell) panel.getComponent(1);
        TicTacToeCell cell3 = (TicTacToeCell) panel.getComponent(2);
        
        cell1.setMarker("X");
        cell2.setMarker("X");
        cell3.setMarker("X");
        
        assertEquals('X', cell1.getMarker());
        assertEquals('X', cell2.getMarker());
        assertEquals('X', cell3.getMarker());
        
        assertFalse(cell1.isEnabled());
        assertFalse(cell2.isEnabled());
        assertFalse(cell3.isEnabled());
    }
    
    @Test
    void testCellPositions() {
        for (int i = 0; i < 9; i++) {
            TicTacToeCell cell = (TicTacToeCell) panel.getComponent(i);
            assertEquals(i, cell.getNum());
        }
    }
} 