package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testPlayerDefaultValues() {
        Player player = new Player();
        assertEquals('\0', player.symbol);
        assertEquals(0, player.move);
        assertFalse(player.selected);
        assertFalse(player.win);
    }

    @Test
    void testPlayerSetValues() {
        Player player = new Player();
        player.symbol = 'X';
        player.move = 5;
        player.selected = true;
        player.win = true;

        assertEquals('X', player.symbol);
        assertEquals(5, player.move);
        assertTrue(player.selected);
        assertTrue(player.win);
    }

    @Test
    void testPlayerWithOSymbol() {
        Player player = new Player();
        player.symbol = 'O';
        player.move = 3;
        assertEquals('O', player.symbol);
        assertEquals(3, player.move);
    }
}