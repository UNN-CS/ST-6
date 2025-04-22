package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TicTacToeCellTest {
    
    @Test
    void testCellInitialization() {
        TicTacToeCell cell = new TicTacToeCell(5, 1, 2);
        
        // Проверяем инициализацию
        assertEquals(5, cell.getNum());
        assertEquals(1, cell.getCol());
        assertEquals(2, cell.getRow());
        assertEquals(' ', cell.getMarker());
        
        // Проверяем, что кнопка активна
        assertTrue(cell.isEnabled());
    }
    
    @Test
    void testSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        
        // Изначально пустая ячейка
        assertEquals(' ', cell.getMarker());
        
        // Устанавливаем X
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertEquals("X", cell.getText());
        
        // Кнопка должна быть отключена после установки маркера
        assertFalse(cell.isEnabled());
        
        // Устанавливаем O (хотя в реальной игре это не должно произойти)
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
        assertEquals("O", cell.getText());
    }
    
    @Test
    void testGetters() {
        TicTacToeCell cell = new TicTacToeCell(8, 2, 2);
        
        assertEquals(8, cell.getNum());
        assertEquals(2, cell.getCol());
        assertEquals(2, cell.getRow());
        
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
    }
} 