package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ProgramTest {

    @Test
    void testMainDoesNotThrow() {
        assertDoesNotThrow(() -> Program.main(new String[0]));
    }
    @Test
    void testStaticWritersInitiallyNull() {
        assertNull(Program.fileWriter);
        assertNull(Program.printWriter);
    }
    @Test
    void testMultipleMainCalls() {
        assertDoesNotThrow(() -> {
            Program.main(new String[0]);
            Program.main(new String[] { "arg1", "arg2" });
        });
    }
}
