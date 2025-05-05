package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testPlayerInitialization() {
        Game game = new Game();
        Player player = game.player1;

        assertEquals('X', player.symbol);
        assertFalse(player.selected);
        assertFalse(player.win);
        assertEquals(0, player.move);
    }
}