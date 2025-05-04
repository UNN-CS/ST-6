package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    // Тесты для checkWin()
    @Test
    void testCheckWinRows() {
        char[][] board = {
            {Program.HUMAN, Program.HUMAN, Program.HUMAN},
            {Program.EMPTY, Program.EMPTY, Program.EMPTY},
            {Program.EMPTY, Program.EMPTY, Program.EMPTY}
        };
        assertTrue(Program.checkWin(board, Program.HUMAN));
    }

    @Test
    void testCheckWinColumns() {
        char[][] board = {
            {Program.AI, Program.EMPTY, Program.EMPTY},
            {Program.AI, Program.EMPTY, Program.EMPTY},
            {Program.AI, Program.EMPTY, Program.EMPTY}
        };
        assertTrue(Program.checkWin(board, Program.AI));
    }

    @Test
    void testCheckWinMainDiagonal() {
        char[][] board = {
            {Program.HUMAN, Program.EMPTY, Program.EMPTY},
            {Program.EMPTY, Program.HUMAN, Program.EMPTY},
            {Program.EMPTY, Program.EMPTY, Program.HUMAN}
        };
        assertTrue(Program.checkWin(board, Program.HUMAN));
    }

    @Test
    void testCheckWinAntiDiagonal() {
        char[][] board = {
            {Program.EMPTY, Program.EMPTY, Program.AI},
            {Program.EMPTY, Program.AI, Program.EMPTY},
            {Program.AI, Program.EMPTY, Program.EMPTY}
        };
        assertTrue(Program.checkWin(board, Program.AI));
    }

    // Тесты для evaluateBoard()
    @Test
    void testEvaluateBoardWinForAI() {
        char[][] board = {
            {Program.AI, Program.AI, Program.AI},
            {Program.EMPTY, Program.EMPTY, Program.EMPTY},
            {Program.EMPTY, Program.EMPTY, Program.EMPTY}
        };
        assertEquals(10, Program.evaluateBoard(board));
    }

    @Test
    void testEvaluateBoardWinForHuman() {
        char[][] board = {
            {Program.HUMAN, Program.HUMAN, Program.HUMAN},
            {Program.EMPTY, Program.EMPTY, Program.EMPTY},
            {Program.EMPTY, Program.EMPTY, Program.EMPTY}
        };
        assertEquals(-10, Program.evaluateBoard(board));
    }

    // Тесты для isMovesLeft()
    @Test
    void testIsMovesLeftTrue() {
        char[][] board = Program.initializeBoard();
        assertTrue(Program.isMovesLeft(board));
    }

    @Test
    void testIsMovesLeftFalse() {
        char[][] board = {
            {Program.HUMAN, Program.AI, Program.HUMAN},
            {Program.AI, Program.HUMAN, Program.AI},
            {Program.HUMAN, Program.AI, Program.HUMAN}
        };
        assertFalse(Program.isMovesLeft(board));
    }

    // Тесты для gameOver()
    @Test
    void testGameOverWin() {
        char[][] board = {
            {Program.HUMAN, Program.HUMAN, Program.HUMAN},
            {Program.AI, Program.AI, Program.EMPTY},
            {Program.EMPTY, Program.EMPTY, Program.EMPTY}
        };
        assertTrue(Program.gameOver(board));
    }

    @Test
    void testGameOverDraw() {
        char[][] board = {
            {Program.HUMAN, Program.AI, Program.HUMAN},
            {Program.HUMAN, Program.AI, Program.AI},
            {Program.AI, Program.HUMAN, Program.HUMAN}
        };
        assertTrue(Program.gameOver(board));
    }

    // Тесты для minimax()
    @Test
    void testMinimaxTerminalState() {
        char[][] board = {
            {Program.HUMAN, Program.HUMAN, Program.HUMAN},
            {Program.EMPTY, Program.EMPTY, Program.EMPTY},
            {Program.EMPTY, Program.EMPTY, Program.EMPTY}
        };
        assertEquals(-10, Program.minimax(board, 0, false));
    }

    @Test
    void testMinimaxOptimalMove() {
        char[][] board = {
            {Program.AI, Program.HUMAN, Program.HUMAN},
            {Program.HUMAN, Program.AI, Program.EMPTY},
            {Program.EMPTY, Program.EMPTY, Program.EMPTY}
        };
        assertTrue(Program.minimax(board, 0, true) > 0);
    }

    // Тесты для findBestMove()
    @Test
    void testFindBestMoveEmptyBoard() {
        char[][] board = Program.initializeBoard();
        int[] move = Program.findBestMove(board);
        assertArrayEquals(new int[]{0, 0}, move);
    }

    // Тесты для aiMove()
    @Test
    void testAiMove() {
        char[][] board = Program.initializeBoard();
        Program.aiMove(board);
        int count = 0;
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == Program.AI) count++;
            }
        }
        assertEquals(1, count);
    }

    // Тест для humanMove()
    @Test
    void testHumanMove() {
        char[][] board = Program.initializeBoard();
        Program.humanMove(board);
        assertEquals(Program.HUMAN, board[0][0]);
    }

    // Тест для printBoard() (покрытие вызова)
    @Test
    void testPrintBoard() {
        char[][] board = Program.initializeBoard();
        assertDoesNotThrow(() -> Program.printBoard(board));
    }
}