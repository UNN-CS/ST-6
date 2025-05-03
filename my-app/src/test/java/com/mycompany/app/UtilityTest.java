package com.mycompany.app;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class UtilityTest {

    @Test
    void testPrintCharArray() {
        Utility.print(new char[]{'X','O',' ','X','O',' ',' ',' ',' '});
    }

    @Test
    void testPrintIntArray() {
        Utility.print(new int[]{1,2,3,4,5,6,7,8,9});
    }

    @Test
    void testPrintArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(2);
        moves.add(3);
        Utility.print(moves);
    }
}
