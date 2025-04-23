package com.mycompany.app;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class UtilityTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testPrintCharArray() {
        char[] board = {'X', 'O', 'X', ' ', 'O', ' ', ' ', ' ', ' '};
        Utility.print(board);

        // Check that something was printed
        assertTrue(outContent.toString().length() > 0);

        // Check that it contains the characters from the board
        String output = outContent.toString();
        assertTrue(output.contains("X"));
        assertTrue(output.contains("O"));
        assertTrue(output.contains(" "));
    }

    @Test
    public void testPrintIntArray() {
        int[] board = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.print(board);

        // Check that something was printed
        assertTrue(outContent.toString().length() > 0);

        // Check that it contains the numbers from the array
        String output = outContent.toString();
        for (int i = 1; i <= 9; i++) {
            assertTrue(output.contains(Integer.toString(i)));
        }
    }

    @Test
    public void testPrintArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(3);
        moves.add(5);
        Utility.print(moves);

        // Check that something was printed
        assertTrue(outContent.toString().length() > 0);

        // Check that it contains the numbers from the array list
        String output = outContent.toString();
        assertTrue(output.contains("1"));
        assertTrue(output.contains("3"));
        assertTrue(output.contains("5"));
    }
}