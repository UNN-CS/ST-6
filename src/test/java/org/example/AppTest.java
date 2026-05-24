package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class AppTest {

    private final PrintStream originalOut = System.out;

    @AfterEach
    void restore() {
        System.setOut(originalOut);
    }

    @Test
    void getGreeting() {
        assertEquals("Hello World!", new App().getGreeting());
    }

    @Test
    void mainPrintsGreeting() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        App.main(new String[]{});
        assertEquals("Hello World!" + System.lineSeparator(), out.toString());
    }
}
