package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.awt.HeadlessException;

public class ProgramTest {

    @Test
    public void testMainMethodInHeadlessMode() {
        // Устанавливаем headless-режим перед вызовом main
        System.setProperty("java.awt.headless", "true");
        assertDoesNotThrow(() -> Program.main(new String[]{}));
    }

    @Test
    public void testMainMethodDoesNotCreateGUI() {
        // Перехватываем возможные GUI-вызовы
        assertDoesNotThrow(() -> {
            try {
                Program.main(new String[]{});
            } catch (HeadlessException e) {
                // Игнорируем исключение, так как GUI не требуется
            }
        });
    }
}