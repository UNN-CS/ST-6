package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class PlayerTest {

    @Test
    public void testPlayerDefaultValues() {
        Player player = new Player();

        // Check default values
        assertEquals(0, player.move);
        assertFalse(player.selected);
        assertFalse(player.win);
        assertEquals('\0', player.symbol); // Default char value
    }

    @Test
    public void testPlayerSetValues() {
        Player player = new Player();

        // Set values
        player.symbol = 'X';
        player.move = 5;
        player.selected = true;
        player.win = true;

        // Verify values
        assertEquals('X', player.symbol);
        assertEquals(5, player.move);
        assertTrue(player.selected);
        assertTrue(player.win);
    }
}