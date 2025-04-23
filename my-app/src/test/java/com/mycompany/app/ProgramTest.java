package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProgramTest {

    @Test
    public void testMainDoesNotThrow() {
        assertDoesNotThrow(() -> Program.main(new String[0]));
    }
}
