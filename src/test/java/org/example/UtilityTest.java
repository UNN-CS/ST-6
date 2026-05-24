package org.example;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UtilityTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream out;

    @BeforeEach
    void captureOutput() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    void restoreOutput() {
        System.setOut(originalOut);
    }

    @Test
    void printCharBoard() {
        Utility.print(new char[]{'X', 'O', ' ', ' ', 'X', ' ', ' ', ' ', 'O'});
        assertTrue(out.toString().contains("X-"));
    }

    @Test
    void printIntBoard() {
        Utility.print(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        assertTrue(out.toString().contains("1-"));
    }

    @Test
    void printMoveList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(0);
        moves.add(4);
        moves.add(8);
        Utility.print(moves);
        assertTrue(out.toString().contains("0-"));
    }
}
