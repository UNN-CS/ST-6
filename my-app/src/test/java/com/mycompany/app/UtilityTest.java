package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Utility
 */
public class UtilityTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        // Перенаправляем стандартный вывод для тестирования печати
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        // Восстанавливаем стандартный вывод
        System.setOut(originalOut);
    }

    @Test
    public void testPrintCharArray() {
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        Utility.print(board);
        
        String output = outContent.toString().trim();
        assertTrue(output.contains("X-O-X- - - - - - -"));
    }

    @Test
    public void testPrintIntArray() {
        int[] board = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.print(board);
        
        String output = outContent.toString().trim();
        assertTrue(output.contains("1-2-3-4-5-6-7-8-9"));
    }

    @Test
    public void testPrintArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(3);
        moves.add(5);
        Utility.print(moves);
        
        String output = outContent.toString().trim();
        assertTrue(output.contains("1-3-5"));
    }
} 