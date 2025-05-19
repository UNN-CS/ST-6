package com.mycompany.app;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class UtilityTest {
    
    @Test
    public void testPrintCharArray() {
        char[] board = {'X', 'O', ' ', 'X', ' ', 'O', ' ', ' ', 'X'};
        // Здесь мы просто проверяем, что метод не выбрасывает исключений
        Utility.print(board);
    }
    
    @Test
    public void testPrintIntArray() {
        int[] board = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        // Здесь мы просто проверяем, что метод не выбрасывает исключений
        Utility.print(board);
    }
    
    @Test
    public void testPrintArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(3);
        moves.add(5);
        // Здесь мы просто проверяем, что метод не выбрасывает исключений
        Utility.print(moves);
    }
}
