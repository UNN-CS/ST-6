package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class GameUtilityTest {

    @Test
    public void testPrintCharArray() {
        char[] board = {'X', 'O', 'X', 'O', 'O', 'X', 'X', 'X', 'O'};

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Utility.print(board);

        String expectedOutput = System.lineSeparator() + "X-O-X-O-O-X-X-X-O-" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());

        System.setOut(System.out);
    }

    @Test
    public void testPrintIntArray() {
        int[] board = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Utility.print(board);

        String expectedOutput = System.lineSeparator() + "1-2-3-4-5-6-7-8-9-" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());

        System.setOut(System.out);
    }

    @Test
    public void testPrintArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(3);
        moves.add(5);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Utility.print(moves);

        String expectedOutput = System.lineSeparator() + "1-3-5-" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());

        System.setOut(System.out);
    }
}
