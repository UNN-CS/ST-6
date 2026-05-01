package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class UtilityTest {
    @Test
    void printHandlesCharBoards() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream previous = System.out;
        System.setOut(new PrintStream(output));
        try {
            Utility.print(new char[] {'X', 'O', 'X', ' ', ' ', ' ', 'O', 'X', 'O'});
        } finally {
            System.setOut(previous);
        }

        assertTrue(output.toString().contains("X-O-X"));
    }

    @Test
    void printHandlesIntBoards() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream previous = System.out;
        System.setOut(new PrintStream(output));
        try {
            Utility.print(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
        } finally {
            System.setOut(previous);
        }

        assertTrue(output.toString().contains("1-2-3"));
    }

    @Test
    void printHandlesMoveLists() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream previous = System.out;
        System.setOut(new PrintStream(output));
        try {
            ArrayList<Integer> moves = new ArrayList<>();
            moves.add(0);
            moves.add(4);
            moves.add(8);
            Utility.print(moves);
        } finally {
            System.setOut(previous);
        }

        assertTrue(output.toString().contains("0-4-8"));
    }
}
