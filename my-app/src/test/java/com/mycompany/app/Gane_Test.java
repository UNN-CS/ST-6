package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Gane_Test {
    @Test
    public void testMain() {
        assertDoesNotThrow(() -> Program.main(new String[]{}));
    }
}