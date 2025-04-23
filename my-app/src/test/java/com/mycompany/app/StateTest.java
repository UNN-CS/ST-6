package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class StateTest {

    @Test
    public void testStateEnumValues() {
        // Check that the State enum has all expected values
        assertEquals(4, State.values().length);
        assertEquals(State.PLAYING, State.valueOf("PLAYING"));
        assertEquals(State.XWIN, State.valueOf("XWIN"));
        assertEquals(State.OWIN, State.valueOf("OWIN"));
        assertEquals(State.DRAW, State.valueOf("DRAW"));
    }
}