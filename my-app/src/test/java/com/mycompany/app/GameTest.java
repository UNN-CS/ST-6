package com.mycompany.app;

import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {
    @Test
    public void testInitialBoardIsEmpty() {
        Program game = new Program();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(Program.EMPTY, game.getCell(i, j));
            }
        }
    }
    
    @Test
    public void testMakeValidMove() {
        Program game = new Program();
        assertTrue(game.makeMove(Program.X, 0, 0));
        assertEquals(Program.X, game.getCell(0, 0));
    }
    
    @Test
    public void testMakeInvalidMove() {
        Program game = new Program();
        game.makeMove(Program.X, 0, 0);
        assertFalse(game.makeMove(Program.O, 0, 0));
    }
    
    @Test
    public void testCheckWinnerRows() {
        Program game = new Program();
        game.makeMove(Program.X, 0, 0);
        game.makeMove(Program.X, 0, 1);
        game.makeMove(Program.X, 0, 2);
        assertEquals(Program.X, game.checkWinner());
    }
    
    @Test
    public void testCheckWinnerColumns() {
        Program game = new Program();
        game.makeMove(Program.O, 0, 1);
        game.makeMove(Program.O, 1, 1);
        game.makeMove(Program.O, 2, 1);
        assertEquals(Program.O, game.checkWinner());
    }
    
    @Test
    public void testCheckWinnerDiagonal1() {
        Program game = new Program();
        game.makeMove(Program.X, 0, 0);
        game.makeMove(Program.X, 1, 1);
        game.makeMove(Program.X, 2, 2);
        assertEquals(Program.X, game.checkWinner());
    }
    
    @Test
    public void testCheckWinnerDiagonal2() {
        Program game = new Program();
        game.makeMove(Program.O, 0, 2);
        game.makeMove(Program.O, 1, 1);
        game.makeMove(Program.O, 2, 0);
        assertEquals(Program.O, game.checkWinner());
    }
    
    @Test
    public void testCheckDraw() {
        Program game = new Program();
        game.makeMove(Program.X, 0, 0);
        game.makeMove(Program.O, 0, 1);
        game.makeMove(Program.X, 0, 2);
        game.makeMove(Program.X, 1, 0);
        game.makeMove(Program.O, 1, 1);
        game.makeMove(Program.O, 1, 2);
        game.makeMove(Program.O, 2, 0);
        game.makeMove(Program.X, 2, 1);
        game.makeMove(Program.X, 2, 2);
        assertEquals(-1, game.checkWinner());
    }
    
    @Test
    public void testFindBestMove() {
        Program game = new Program();
        game.makeMove(Program.X, 0, 0);
        game.makeMove(Program.O, 1, 1);
        game.makeMove(Program.X, 2, 2);
        
        int[] bestMove = game.findBestMove();
        assertNotNull(bestMove);
        assertEquals(2, bestMove.length);
    }
    
    @Test
    public void testEvaluateWin() {
        Program game = new Program();
        game.makeMove(Program.O, 0, 0);
        game.makeMove(Program.O, 0, 1);
        game.makeMove(Program.O, 0, 2);
        assertEquals(10, game.evaluate());
    }
    @Test
    public void testEvaluateLoss() {
        Program game = new Program();
        game.makeMove(Program.X, 0, 0);
        game.makeMove(Program.X, 0, 1);
        game.makeMove(Program.X, 0, 2);
        assertEquals(-10, game.evaluate());
    }
    
    @Test
    public void testIsBoardFull() {
        Program game = new Program();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                game.makeMove((i + j) % 2 == 0 ? Program.X : Program.O, i, j);
            }
        }
        assertTrue(game.isBoardFull());
    }
}