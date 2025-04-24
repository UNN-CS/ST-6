package com.mycompany.app;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
 import java.util.Arrays; 

public class ProgramTest {
    private TicTacToe game;

    @Before
    public void setUp() {
        game = new TicTacToe();
    }

    @Test
    public void testHasWonColumns() {
        // Test column 0
        game.getBoard()[0][0] = 'X';
        game.getBoard()[1][0] = 'X';
        game.getBoard()[2][0] = 'X';
        assertTrue(game.hasWon('X'));
    }

    @Test
    public void testHasWonDiagonals() {
        // Test main diagonal
        game.getBoard()[0][0] = 'O';
        game.getBoard()[1][1] = 'O';
        game.getBoard()[2][2] = 'O';
        assertTrue(game.hasWon('O'));

        // Test anti-diagonal
        game = new TicTacToe();
        game.getBoard()[0][2] = 'X';
        game.getBoard()[1][1] = 'X';
        game.getBoard()[2][0] = 'X';
        assertTrue(game.hasWon('X'));
    }

    @Test
    public void testIsBoardFull() {
        assertFalse(game.isBoardFull());
        
        // Fill board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                game.getBoard()[i][j] = 'X';
            }
        }
        assertTrue(game.isBoardFull());
    }

    @Test
    public void testValidMoves() {
        assertTrue(game.isValidMove(0, 0));
        game.getBoard()[1][1] = 'O';
        assertFalse(game.isValidMove(1, 1));
        assertFalse(game.isValidMove(-1, 2));
        assertFalse(game.isValidMove(3, 1));
    }

    @Test
    public void testFindBestMoveWinScenario() {
        // Setup near win for O
        game.getBoard()[0][0] = 'O';
        game.getBoard()[0][1] = 'O';
        int[] bestMove = game.findBestMove();
        assertArrayEquals(new int[]{0, 2}, bestMove);
    }

    

    @Test
    public void testMinimaxScores() {
        // Test win for O
        game.getBoard()[0][0] = 'O';
        game.getBoard()[0][1] = 'O';
        game.getBoard()[0][2] = 'O';
        assertEquals(1, game.minimax(game.getBoard(), 0, true));

        // Test loss prevention
        game = new TicTacToe();
        game.getBoard()[1][0] = 'X';
        game.getBoard()[1][1] = 'X';
        assertEquals(-1, game.minimax(game.getBoard(), 0, false));
    }

    @Test
    public void testGameResult() {
        // Test draw
        char[][] fullBoard = {
            {'X','O','X'},
            {'X','O','O'},
            {'O','X','X'}
        };
        game = new TicTacToeWithBoard(fullBoard);
        assertTrue(game.isGameOver());
        assertFalse(game.hasWon('X'));
        assertFalse(game.hasWon('O'));
    }

    // Helper class for testing board states
    private static class TicTacToeWithBoard extends TicTacToe {
		TicTacToeWithBoard(char[][] board) {
			super();
			for (int i = 0; i < 3; i++) {
				this.getBoard()[i] = Arrays.copyOf(board[i], 3);
			}
		}
	}
}