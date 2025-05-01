package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    @Test
    public void testPlayerSymbol() {
        Player player = new Player();
        player.symbol = 'X';
        assertEquals('X', player.symbol);
    }
}