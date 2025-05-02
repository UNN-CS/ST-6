package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    public void testPlayerInitialization() {
        Player player = new Player();
        assertEquals(0, player.move);
        assertFalse(player.selected);
        assertFalse(player.win);
    }
}