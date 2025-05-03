package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.awt.*;

class ProgramTest {

    @Test
    void testMainDoesNotThrow() {
        assertDoesNotThrow(() -> {
            if (!GraphicsEnvironment.isHeadless()) {
                Program.main(new String[0]);
            }
        });
    }

    @Test
    void testMultipleMainCalls() {
        assertDoesNotThrow(() -> {
            if (!GraphicsEnvironment.isHeadless()) {
                Program.main(new String[0]);
                Program.main(new String[] { "arg1", "arg2" });
            }
        });
    }
}
