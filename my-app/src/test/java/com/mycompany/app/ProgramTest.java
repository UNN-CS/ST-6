package com.mycompany.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class ProgramTest {
    private Program game;

    @BeforeEach
    void setUp() {
        game = new Program();
    }

    @Test
    void testInitialBoardState() {
        char[][] board = game.getBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(' ', board[i][j]);
            }
        }
    }

    @Test
    void testMakeMove() {
        game.makeMove(0, 0);
        assertEquals('X', game.getBoard()[0][0]);

        game.makeMove(1, 1);
        assertEquals('O', game.getBoard()[1][1]);
    }

    @Test
    void testInvalidMove() {
        game.makeMove(0, 0);
        assertFalse(game.isValidMove(0, 0));
        assertFalse(game.isValidMove(-1, 0));
        assertFalse(game.isValidMove(0, 3));
    }

    @ParameterizedTest
    @CsvSource({
        "0,0, 0,1, 0,2",
        "0,0, 1,0, 2,0",
        "0,0, 1,1, 2,2",
        "0,2, 1,1, 2,0"
    })
    void testWinConditions(int r1, int c1, int r2, int c2, int r3, int c3) {
        game.makeMove(r1, c1);
        game.makeMove(1, 2);
        game.makeMove(r2, c2);
        game.makeMove(2, 1);
        game.makeMove(r3, c3);
        assertTrue(game.isWin('X'));
    }

    @Test
    void testDraw() {
        game.makeMove(0, 0);
        game.makeMove(1, 1);
        game.makeMove(0, 1);
        game.makeMove(0, 2);
        game.makeMove(1, 2);
        game.makeMove(1, 0);
        game.makeMove(2, 0);
        game.makeMove(2, 1);
        game.makeMove(2, 2);

        assertTrue(game.isDraw());
    }

    @Test
    void testComputerMove() {
        game.makeMove(0, 0);
        int[] computerMove = game.findBestMove();
        assertNotNull(computerMove);
        assertEquals(2, computerMove.length);
        assertTrue(computerMove[0] >= 0 && computerMove[0] < 3);
        assertTrue(computerMove[1] >= 0 && computerMove[1] < 3);
    }

    @Test
    void testMinimaxAlgorithm() {
        game.makeMove(0, 0);
        game.makeMove(1, 1);
        game.makeMove(0, 1);
        game.makeMove(1, 0);

        int[] bestMove = game.findBestMove();
        game.makeMove(bestMove[0], bestMove[1]);

        assertTrue(game.isWin('O') || !game.isWin('X'));
    }

    @Test
    void testGameNotOver() {
        game.makeMove(0, 0);
        assertFalse(game.isWin('X'));
        assertFalse(game.isDraw());
    }

    @Test
    void testIsValidMoveEdgeCases() {
        assertFalse(game.isValidMove(-1, 0));
        assertFalse(game.isValidMove(0, -1));
        assertFalse(game.isValidMove(3, 0));
        assertFalse(game.isValidMove(0, 3));
        assertTrue(game.isValidMove(2, 2));
        game.makeMove(2, 2);
        assertFalse(game.isValidMove(2, 2));
    }

    @Test
    void testPlayerSwitching() {
        assertEquals('X', game.getCurrentPlayer());
        game.makeMove(0, 0);
        assertEquals('O', game.getCurrentPlayer());
        game.makeMove(1, 1);
        assertEquals('X', game.getCurrentPlayer());
    }

    @Test
    void testNoMoveAfterWin() {
        game.makeMove(0, 0);
        game.makeMove(1, 0);
        game.makeMove(0, 1);
        game.makeMove(1, 1);
        game.makeMove(0, 2);
        assertTrue(game.isWin('X'));
        char[][] before = copyBoard(game.getBoard());
        game.makeMove(2, 2);
        assertArrayEquals(before, game.getBoard());
    }

    @Test
    void testNoMoveAfterDraw() {
        game.makeMove(0, 0);
        game.makeMove(1, 1);
        game.makeMove(0, 1);
        game.makeMove(0, 2);
        game.makeMove(1, 2);
        game.makeMove(1, 0);
        game.makeMove(2, 0);
        game.makeMove(2, 1);
        game.makeMove(2, 2);
        assertTrue(game.isDraw());
        char[][] before = copyBoard(game.getBoard());
        game.makeMove(1, 1);
        assertArrayEquals(before, game.getBoard());
    }

    @Test
    void testPrintBoardCoverage() {
        game.printBoard();
        game.makeMove(0, 0);
        game.printBoard();
    }

    private char[][] copyBoard(char[][] board) {
        char[][] copy = new char[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, 3);
        }
        return copy;
    }
} 