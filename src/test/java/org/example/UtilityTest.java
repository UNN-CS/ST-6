package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UtilityTest {

    @Test
    public void testPrintCharArray() {
        Utility.print(new char[]{'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X'});
    }

    @Test
    public void testPrintIntArray() {
        Utility.print(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
    }

    @Test
    public void testPrintArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        Utility.print(moves);
    }
}
