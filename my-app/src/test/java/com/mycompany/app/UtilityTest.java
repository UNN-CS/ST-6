package com.mycompany.app;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class UtilityTest {

    @Test
    public void testPrintBoard() {
        char[] board = {
            'X', 'O', 'X',
            ' ', 'O', ' ',
            'X', ' ', 'O'
        };
        Utility.print(board);
    }

    @Test
    public void testPrintEmptyBoard() {
        char[] board = new char[9];
        Utility.print(board);
    }

    @Test
    public void testPrintMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(3);
        moves.add(5);
        Utility.print(moves);
    }

    @Test
    public void testPrintEmptyMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        Utility.print(moves);
    }

    @Test
    public void testPrintIntArray() {
        int[] board = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.print(board);
    }

    @Test
    public void testPrintEmptyIntArray() {
        int[] board = new int[9];
        Utility.print(board);
    }
}