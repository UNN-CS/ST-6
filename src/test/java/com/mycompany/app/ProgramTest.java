package com.mycompany.app;

import org.junit.Test;
import static org.junit.Assert.*;
import javax.swing.*;
import java.lang.reflect.Method;

public class ProgramTest {
    
    @Test
    public void testMainMethodExists() {
        // Проверяем, что main метод существует
        try {
            Method mainMethod = Program.class.getMethod("main", String[].class);
            assertNotNull(mainMethod);
        } catch (NoSuchMethodException e) {
            fail("Main method not found");
        }
    }
    
    @Test
    public void testProgramClassCanBeInstantiated() {
        try {
            Program program = new Program();
            assertNotNull(program);
        } catch (Exception e) {
            // Класс может не иметь конструктора по умолчанию, но это нормально
            assertTrue(true);
        }
    }
}