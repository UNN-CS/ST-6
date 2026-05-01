package com.mycompany.app;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class UtilityTest {

    @Test
    void testPrintCharArray() {
        char[] board = {'X', 'O', ' ', ' ', 'X', ' ', ' ', ' ', ' '};
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Utility.print(board);
        assertTrue(out.toString().contains("X-O-"));
    }

    @Test
    void testPrintIntArray() {
        int[] board = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Utility.print(board);
        assertTrue(out.toString().contains("1-2-3-4-5-6-7-8-9-"));
    }

    @Test
    void testPrintArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(2);
        moves.add(5);
        moves.add(7);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Utility.print(moves);
        assertTrue(out.toString().contains("2-5-7-"));
    }

    @Test
    void testPrintEmptyArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Utility.print(moves);
    }
}