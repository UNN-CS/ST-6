package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Player
 */
public class PlayerTest {

    @Test
    public void testPlayerAttributes() {
        Player player = new Player();
        
        // Проверка начальных значений
        assertEquals(0, player.move);
        assertFalse(player.selected);
        assertFalse(player.win);
        
        // Изменяем атрибуты и проверяем
        player.symbol = 'X';
        player.move = 5;
        player.selected = true;
        player.win = true;
        
        assertEquals('X', player.symbol);
        assertEquals(5, player.move);
        assertTrue(player.selected);
        assertTrue(player.win);
    }
} 