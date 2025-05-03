package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {

    private Player p1;
    private Player p2;

    @BeforeEach
    void setUp() {
        p1 = new Player();
        p2 = new Player();
    }

    @Test
    void testDefaultValues() {
        assertEquals('\0', p1.symbol);
        assertEquals(0, p1.move);
        assertFalse(p1.selected);
        assertFalse(p1.win);
    }

    @Test
    void testSetSymbolAndMove() {
        p1.symbol = 'X';
        p1.move = 5;
        assertEquals('X', p1.symbol);
        assertEquals(5, p1.move);
    }

    @Test
    void testToggleSelected() {
        p1.selected = true;
        assertTrue(p1.selected);
        p1.selected = false;
        assertFalse(p1.selected);
    }

    @Test
    void testWinFlag() {
        p1.win = true;
        assertTrue(p1.win);
    }

    @Test
    void testIndependenceBetweenInstances() {
        p1.symbol = 'X';
        p1.move = 3;
        p1.selected = true;
        p1.win = true;

        assertNotEquals(p1.symbol, p2.symbol);
        assertNotEquals(p1.move, p2.move);
        assertNotEquals(p1.selected, p2.selected);
        assertNotEquals(p1.win, p2.win);
    }
}
