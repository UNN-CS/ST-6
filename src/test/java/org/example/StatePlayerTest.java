package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class StatePlayerTest {

    @Test
    void stateValues() {
        assertEquals(State.PLAYING, State.valueOf("PLAYING"));
        assertEquals(4, State.values().length);
    }

    @Test
    void playerFields() {
        Player p = new Player();
        p.symbol = 'X';
        p.move = 3;
        p.selected = true;
        p.win = false;
        assertEquals('X', p.symbol);
        assertEquals(3, p.move);
        assertNotNull(p);
    }
}
