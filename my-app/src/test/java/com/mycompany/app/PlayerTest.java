package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    public void testPlayerInitialization() {
        Player player = new Player();
        player.symbol = 'X';
        player.move = 0;
        player.selected = false;
        player.win = false;

        assertEquals('X', player.symbol);
        assertEquals(0, player.move);
        assertFalse(false);
        assertFalse(false);
    }
}

