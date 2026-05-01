package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    @Test
    void testStateValues() {
        State[] states = State.values();
        assertEquals(4, states.length);
        assertTrue(containsState(states, State.PLAYING));
        assertTrue(containsState(states, State.OWIN));
        assertTrue(containsState(states, State.XWIN));
        assertTrue(containsState(states, State.DRAW));
    }

    private boolean containsState(State[] states, State target) {
        for (State s : states) {
            if (s == target) return true;
        }
        return false;
    }

    @Test
    void testStateValueOf() {
        assertEquals(State.PLAYING, State.valueOf("PLAYING"));
        assertEquals(State.OWIN, State.valueOf("OWIN"));
        assertEquals(State.XWIN, State.valueOf("XWIN"));
        assertEquals(State.DRAW, State.valueOf("DRAW"));
    }
}