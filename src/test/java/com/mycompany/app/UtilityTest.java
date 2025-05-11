package com.mycompany.app;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class UtilityTest {
    @Test
    void printCharArray() {
        Utility.print(new char[] {'X', ' ', 'O'});
    }

    @Test
    void printIntArray() {
        Utility.print(new int[] {0, 1, 2});
    }

    @Test
    void printMoveList_NoException() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(2);
        Utility.print(moves);
    }
}
