package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StateTest {
    
    @Test
    void testStateValues() {
        // Проверяем наличие всех возможных состояний
        assertEquals(4, State.values().length);
        
        // Проверяем значения перечисления
        assertEquals(State.PLAYING, State.valueOf("PLAYING"));
        assertEquals(State.OWIN, State.valueOf("OWIN"));
        assertEquals(State.XWIN, State.valueOf("XWIN"));
        assertEquals(State.DRAW, State.valueOf("DRAW"));
    }
    
    @Test
    void testOrdinalValues() {
        // Проверяем порядковые значения
        assertEquals(0, State.PLAYING.ordinal());
        assertEquals(1, State.OWIN.ordinal());
        assertEquals(2, State.XWIN.ordinal());
        assertEquals(3, State.DRAW.ordinal());
    }
} 