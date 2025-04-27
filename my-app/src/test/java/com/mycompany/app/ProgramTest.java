package com.mycompany.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.io.PrintWriter;

public class ProgramTest {

    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true); // Простой тест для проверки базовой функциональности
    }

    @Test
    public void testProgramInitialization() {
        assertDoesNotThrow(() -> {
            Program.fileWriter = new FileWriter("test.txt");
            Program.printWriter = new PrintWriter(Program.fileWriter);
            Program.printWriter.println("Test");
            Program.printWriter.close();
        });
    }
}