package com.mycompany.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    public void testPlayerFields() {
        Player p = new Player();
        p.symbol = 'O';
        p.move = 1;
        p.selected = true;
        p.win = false;
        assertEquals('O', p.symbol);
        assertEquals(1, p.move);
        assertTrue(p.selected);
        assertFalse(p.win);
    }
}
