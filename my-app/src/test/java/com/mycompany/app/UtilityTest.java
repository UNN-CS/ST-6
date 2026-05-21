package com.mycompany.app;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UtilityTest {

    @Test
    void testPrintCharArray() {
        char[] board = {
                'X','O','X',
                ' ',' ',' ',
                ' ',' ',' '
        };

        Utility.print(board);

        assertTrue(true);
    }

    @Test
    void testPrintIntArray() {
        int[] arr = {1,2,3,4,5,6,7,8,9};

        Utility.print(arr);

        assertTrue(true);
    }

    @Test
    void testPrintMoves() {
        ArrayList<Integer> moves = new ArrayList<>();

        moves.add(1);
        moves.add(5);

        Utility.print(moves);

        assertTrue(true);
    }
}
