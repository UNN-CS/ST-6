package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PlayerTest {
    @Test
    void testPlayerInitialization() {
        Game game = new Game();
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertFalse(game.player1.selected);
        assertFalse(game.player1.win);
    }
}