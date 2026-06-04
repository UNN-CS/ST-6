package com.mycompany.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProgramTest {
    @Test
    public void testProgramConstructor() {
        Program program = new Program();
        assertNotNull(program);
    }
}
