package org.st6;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ProgramUtilityTest {

    @Test
    public void testPrintIntArray() {
        int[] board = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.print(board);
    }

    @Test
    public void testPrintCharArray() {
        char[] board = {'O', 'O', 'O', 'O', 'X', 'X', 'X', 'O', 'X'};
        Utility.print(board);
    }

    @Test
    public void testPrintArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        Utility.print(moves);
    }
}
