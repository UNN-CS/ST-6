package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class AppTest {
    @Test
    void testMain() {
        assertDoesNotThrow(() -> Program.main(new String[]{}));
    }

    @Test
    void testGameInitialization() {
        Game game = new Game();
        assertNotNull(game.player1);
        assertNotNull(game.player2);
        assertEquals(State.PLAYING, game.state);
    }
}