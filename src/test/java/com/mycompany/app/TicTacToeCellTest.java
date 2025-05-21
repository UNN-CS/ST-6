package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса TicTacToeCell
 */
public class TicTacToeCellTest {

    @Test
    public void testCellInitialization() {
        TicTacToeCell cell = new TicTacToeCell(5, 1, 2);
        
        assertEquals(5, cell.getNum());
        assertEquals(1, cell.getCol());
        assertEquals(2, cell.getRow());
        assertEquals(' ', cell.getMarker());
    }

    @Test
    public void testSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        
        // Проверка начального маркера
        assertEquals(' ', cell.getMarker());
        
        // Установка X
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled()); // Кнопка должна быть отключена после установки маркера
        
        // Установка O
        TicTacToeCell cell2 = new TicTacToeCell(1, 0, 1);
        cell2.setMarker("O");
        assertEquals('O', cell2.getMarker());
        assertFalse(cell2.isEnabled());
    }
} 