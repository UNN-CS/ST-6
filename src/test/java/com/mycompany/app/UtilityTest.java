package com.mycompany.app;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UtilityTest {
    @Test
    public void testUtilityConstructorAndPrint() {
        Utility utility = new Utility();
        assertNotNull(utility);
        Utility.print(new char[9]);
        Utility.print(new int[9]);
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        Utility.print(moves);
    }
}
