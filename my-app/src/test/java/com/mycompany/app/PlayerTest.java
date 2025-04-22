package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    
    @Test
    void testPlayerInitialization() {
        Player player = new Player();
        
        // По умолчанию поля должны иметь следующие значения
        assertEquals(0, player.move);
        assertFalse(player.selected);
        assertFalse(player.win);
        
        // Символ по умолчанию не инициализирован
        assertEquals('\0', player.symbol);
    }
    
    @Test
    void testPlayerSymbolAssignment() {
        Player player = new Player();
        
        // Присваиваем символ X
        player.symbol = 'X';
        assertEquals('X', player.symbol);
        
        // Присваиваем символ O
        player.symbol = 'O';
        assertEquals('O', player.symbol);
    }
    
    @Test
    void testPlayerMoveAssignment() {
        Player player = new Player();
        
        // Присваиваем ход
        player.move = 5;
        assertEquals(5, player.move);
    }
    
    @Test
    void testPlayerSelectedAssignment() {
        Player player = new Player();
        
        // Изначально не выбран
        assertFalse(player.selected);
        
        // Отмечаем как выбранный
        player.selected = true;
        assertTrue(player.selected);
    }
    
    @Test
    void testPlayerWinAssignment() {
        Player player = new Player();
        
        // Изначально не победитель
        assertFalse(player.win);
        
        // Отмечаем как победителя
        player.win = true;
        assertTrue(player.win);
    }
} 