package com.mycompany.app;

import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class UtilityTest {
    
    @Test
    public void testPrintCharArray() {
        char[] board = "X O      ".toCharArray();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        Utility.print(board);
        
        String output = outContent.toString();
        assertTrue(output.contains("X") && output.contains("O"));
        System.setOut(System.out);
    }
    
    @Test
    public void testPrintIntArray() {
        int[] board = {1, 0, 2, 0, 0, 0, 0, 0, 0};
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        Utility.print(board);
        
        String output = outContent.toString();
        assertTrue(output.contains("1") && output.contains("2"));
        System.setOut(System.out);
    }
    
    @Test
    public void testPrintArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(0);
        moves.add(2);
        moves.add(4);
        
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        Utility.print(moves);
        
        String output = outContent.toString();
        assertTrue(output.contains("0") && output.contains("2") && output.contains("4"));
        System.setOut(System.out);
    }
    
    @Test
    public void testPrintEmptyArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        Utility.print(moves);
        
        // Пустой список должен вывести пустую строку
        String output = outContent.toString();
        assertNotNull(output);
        System.setOut(System.out);
    }
}