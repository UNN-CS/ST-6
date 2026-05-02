package com.mycompany.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class UtilityTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        // Перехватываем вывод в консоль перед каждым тестом
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        // Возвращаем стандартный вывод после тестов
        System.setOut(originalOut);
    }

    @Test
    public void testPrintCharArray() {
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        Utility.print(board);
        assertTrue(outContent.toString().contains("X-O-X- - - - - - -"), "Метод должен корректно печатать char массив");
    }

    @Test
    public void testPrintIntArray() {
        int[] moves = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.print(moves);
        assertTrue(outContent.toString().contains("1-2-3-4-5-6-7-8-9-"), "Метод должен корректно печатать int массив");
    }

    @Test
    public void testPrintArrayList() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0); list.add(4); list.add(8);
        Utility.print(list);
        assertTrue(outContent.toString().contains("0-4-8-"), "Метод должен корректно печатать ArrayList");
    }
}
