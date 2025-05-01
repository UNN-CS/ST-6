package com.mycompany.app;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class UtilityTest {
    @Test
    public void testPrintCharArray() {
        Utility.print(new char[]{'X', 'O', ' '}); // Проверка вывода без ошибок
    }

    @Test
    public void testPrintIntArray() {
        Utility.print(new int[]{1, 2, 3}); // Проверка вывода
    }

    @Test
    public void testPrintArrayList() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(5);
        Utility.print(list); // Проверка вывода
    }
     @Test
    public void testPrintEmptyBoard() {
        Utility.print(new char[9]); // Проверка пустой доски
    }

    @Test
    public void testPrintFullBoard() {
        char[] fullBoard = {'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X'};
        Utility.print(fullBoard);
    }
}