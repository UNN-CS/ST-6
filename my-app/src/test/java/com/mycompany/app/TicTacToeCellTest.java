package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class TicTacToeCellTest {
    @Test
    public void testSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    public void testGetters() {
        TicTacToeCell cell = new TicTacToeCell(5, 2, 1);
        assertEquals(5, cell.getNum());
        assertEquals(1, cell.getRow());
        assertEquals(2, cell.getCol());
    }
}