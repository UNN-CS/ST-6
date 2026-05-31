package com.mycompany.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ProgramTest {

    @BeforeAll
    public static void setupHeadless() {
        System.setProperty("java.awt.headless", "true");
    }

    @Test
    public void testGameInit() {
        Program.Game g = new Program.Game();
        assertEquals(' ', g.board[0]);
        assertEquals(' ', g.board[4]);
        assertEquals(' ', g.board[8]);
    }

    @Test
    public void testIsFullEmpty() {
        Program.Game g = new Program.Game();
        assertFalse(g.isFull());
    }

    @Test
    public void testIsFullPartiallyFilled() {
        Program.Game g = new Program.Game();
        g.board[0] = 'X';
        g.board[1] = 'O';
        g.board[2] = 'X';
        assertFalse(g.isFull());
    }

    @Test
    public void testIsFullCompletelyFilled() {
        Program.Game g = new Program.Game();
        for(int i=0; i<9; i++) g.board[i] = 'X';
        assertTrue(g.isFull());
    }

    @Test
    public void testCheckWinnerEmpty() {
        Program.Game g = new Program.Game();
        assertEquals(' ', g.checkWinner());
    }

    @Test
    public void testCheckWinnerXFirstRow() {
        Program.Game g = new Program.Game();
        g.board[0] = 'X'; g.board[1] = 'X'; g.board[2] = 'X';
        assertEquals('X', g.checkWinner());
    }

    @Test
    public void testCheckWinnerXSecondRow() {
        Program.Game g = new Program.Game();
        g.board[3] = 'X'; g.board[4] = 'X'; g.board[5] = 'X';
        assertEquals('X', g.checkWinner());
    }

    @Test
    public void testCheckWinnerXThirdRow() {
        Program.Game g = new Program.Game();
        g.board[6] = 'X'; g.board[7] = 'X'; g.board[8] = 'X';
        assertEquals('X', g.checkWinner());
    }

    @Test
    public void testCheckWinnerXFirstColumn() {
        Program.Game g = new Program.Game();
        g.board[0] = 'X'; g.board[3] = 'X'; g.board[6] = 'X';
        assertEquals('X', g.checkWinner());
    }

    @Test
    public void testCheckWinnerXDiagonal() {
        Program.Game g = new Program.Game();
        g.board[0] = 'X'; g.board[4] = 'X'; g.board[8] = 'X';
        assertEquals('X', g.checkWinner());
    }

    @Test
    public void testCheckWinnerOFirstRow() {
        Program.Game g = new Program.Game();
        g.board[0] = 'O'; g.board[1] = 'O'; g.board[2] = 'O';
        assertEquals('O', g.checkWinner());
    }

    @Test
    public void testCheckWinnerODiagonal() {
        Program.Game g = new Program.Game();
        g.board[2] = 'O'; g.board[4] = 'O'; g.board[6] = 'O';
        assertEquals('O', g.checkWinner());
    }

    @Test
    public void testEvaluateDraw() {
        Program.Game g = new Program.Game();
        assertEquals(0, g.evaluate());
    }

    @Test
    public void testEvaluateXWins() {
        Program.Game g = new Program.Game();
        g.board[0] = 'X'; g.board[1] = 'X'; g.board[2] = 'X';
        assertEquals(10, g.evaluate());
    }

    @Test
    public void testEvaluateOWins() {
        Program.Game g = new Program.Game();
        g.board[0] = 'O'; g.board[1] = 'O'; g.board[2] = 'O';
        assertEquals(-10, g.evaluate());
    }

    @Test
    public void testMinimaxDepthZero() {
        Program.Game g = new Program.Game();
        g.board[0] = 'X'; g.board[1] = 'X';
        int score = g.minimax(0, true);
        assertTrue(score > 0);
    }

    @Test
    public void testMinimaxDepthOne() {
        Program.Game g = new Program.Game();
        g.board[0] = 'X';
        int score = g.minimax(1, false);
        assertTrue(score >= -10 && score <= 10);
    }

    @Test
    public void testFindBestMoveEmptyBoard() {
        Program.Game g = new Program.Game();
        int move = g.findBestMove();
        assertTrue(move >= 0 && move <= 8);
    }

    @Test
    public void testFindBestMoveWinningMove() {
        Program.Game g = new Program.Game();
        g.board[0] = 'X'; g.board[1] = 'X';
        int move = g.findBestMove();
        assertEquals(2, move);
    }

    @Test
    public void testFindBestMoveBlockingMove() {
        Program.Game g = new Program.Game();
        g.board[0] = 'O'; g.board[1] = 'O';
        int move = g.findBestMove();
        assertTrue(move >= 0 && move <= 8);
    }

    @Test
    public void testPrintBoardEmpty() {
        Program.Game g = new Program.Game();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        g.printBoard();
        String output = outContent.toString();
        assertNotNull(output);
        assertTrue(output.contains(" "));
        System.setOut(System.out);
    }

    @Test
    public void testPrintBoardWithMoves() {
        Program.Game g = new Program.Game();
        g.board[0] = 'X';
        g.board[4] = 'O';
        g.board[8] = 'X';
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        g.printBoard();
        String output = outContent.toString();
        assertTrue(output.contains("X"));
        assertTrue(output.contains("O"));
        System.setOut(System.out);
    }
    
    @Test
    public void testMain() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Program.main(new String[]{});
        String output = outContent.toString();
        assertTrue(output.contains("Best move"));
        System.setOut(System.out);
    }

    @Test
    public void testMainMultipleRuns() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Program.main(new String[]{});
        Program.main(new String[]{});
        String output = outContent.toString();
        assertTrue(output.split("Best move").length > 2);
        System.setOut(System.out);
    }

    @Test
    public void testMinimaxWithFullBoard() {
        Program.Game g = new Program.Game();
        for(int i=0; i<9; i++) g.board[i] = (i % 2 == 0) ? 'X' : 'O';
        int score = g.minimax(0, true);
        assertTrue(score >= -10 && score <= 10);
    }
}
