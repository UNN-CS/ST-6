package com.andreychh;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;
    private Player playerX;
    private Player playerO;

    @BeforeEach
    void setUp() {
        game = new Game();
        playerX = game.player1;
        playerO = game.player2;
    }

    @Test
    void testCheckState_WhenBoardNotFinished_ReturnsPlaying() {
        char[] board = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(Game.State.PLAYING, game.checkState(board));
        board[0] = 'X';
        board[1] = 'O';
        assertEquals(Game.State.PLAYING, game.checkState(board));
    }

    @Test
    void testCheckState_WhenXHasWinningLine_ReturnsXWin() {
        char[] board = {'X', 'X', 'X', 'O', 'O', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(Game.State.XWIN, game.checkState(board));

        board = new char[]{'O', 'O', ' ', 'X', 'X', 'X', ' ', ' ', ' '};
        assertEquals(Game.State.XWIN, game.checkState(board));

        board = new char[]{'O', 'O', ' ', ' ', ' ', ' ', 'X', 'X', 'X'};
        assertEquals(Game.State.XWIN, game.checkState(board));

        board = new char[]{'X', 'O', 'O', 'X', ' ', ' ', 'X', ' ', ' '};
        assertEquals(Game.State.XWIN, game.checkState(board));

        board = new char[]{'O', 'X', 'O', ' ', 'X', ' ', ' ', 'X', ' '};
        assertEquals(Game.State.XWIN, game.checkState(board));

        board = new char[]{'O', 'O', 'X', ' ', ' ', 'X', ' ', ' ', 'X'};
        assertEquals(Game.State.XWIN, game.checkState(board));

        board = new char[]{'X', 'O', 'O', ' ', 'X', ' ', ' ', ' ', 'X'};
        assertEquals(Game.State.XWIN, game.checkState(board));

        board = new char[]{'O', 'O', 'X', ' ', 'X', ' ', 'X', ' ', ' '};
        assertEquals(Game.State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckState_WhenOHasWinningLine_ReturnsOWin() {
        char[] board = {'O', 'O', 'O', 'X', 'X', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(Game.State.OWIN, game.checkState(board));

        board = new char[]{'X', 'X', ' ', 'O', 'O', 'O', ' ', ' ', ' '};
        assertEquals(Game.State.OWIN, game.checkState(board));

        board = new char[]{'X', 'X', ' ', ' ', ' ', ' ', 'O', 'O', 'O'};
        assertEquals(Game.State.OWIN, game.checkState(board));

        board = new char[]{'O', 'X', 'X', 'O', ' ', ' ', 'O', ' ', ' '};
        assertEquals(Game.State.OWIN, game.checkState(board));

        board = new char[]{'X', 'O', 'X', ' ', 'O', ' ', ' ', 'O', ' '};
        assertEquals(Game.State.OWIN, game.checkState(board));

        board = new char[]{'X', 'X', 'O', ' ', ' ', 'O', ' ', ' ', 'O'};
        assertEquals(Game.State.OWIN, game.checkState(board));

        board = new char[]{'O', 'X', 'X', ' ', 'O', ' ', ' ', ' ', 'O'};
        assertEquals(Game.State.OWIN, game.checkState(board));

        board = new char[]{'X', 'X', 'O', ' ', 'O', ' ', 'O', ' ', ' '};
        assertEquals(Game.State.OWIN, game.checkState(board));
    }

    @Test
    void testCheckState_WhenBoardFullAndNoWinner_ReturnsDraw() {
        char[] board = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'};
        game.symbol = 'X';
        assertEquals(Game.State.DRAW, game.checkState(board));
        game.symbol = 'O';
        assertEquals(Game.State.DRAW, game.checkState(board));
    }

    @Test
    void testGenerateMoves_WhenBoardIsEmpty_ReturnsAllNineIndices() {
        char[] board = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(9, moves.size());
        List<Integer> expectedMoves = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8);
        assertTrue(moves.containsAll(expectedMoves) && expectedMoves.containsAll(moves));
    }

    @Test
    void testGenerateMoves_WhenBoardIsPartiallyFilled_ReturnsEmptyCellIndices() {
        char[] board = {'X', 'O', ' ', 'X', ' ', ' ', 'O', ' ', ' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(5, moves.size());
        List<Integer> expectedMoves = Arrays.asList(2, 4, 5, 7, 8);
        assertTrue(moves.containsAll(expectedMoves) && expectedMoves.containsAll(moves));
    }

    @Test
    void testGenerateMoves_WhenBoardIsFull_ReturnsEmptyList() {
        char[] board = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(0, moves.size());
    }

    @Test
    void testEvaluatePosition_VariousScenarios_ReturnsCorrectScore() {
        char[] boardXWin = {'X', 'X', 'X', 'O', 'O', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(boardXWin, playerX));

        game.symbol = 'X';
        assertEquals(-Game.INF, game.evaluatePosition(boardXWin, playerO));

        char[] boardOWin = {'O', 'O', 'O', 'X', 'X', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(boardOWin, playerO));

        game.symbol = 'O';
        assertEquals(-Game.INF, game.evaluatePosition(boardOWin, playerX));

        char[] boardDraw = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'};
        game.symbol = 'X';
        assertEquals(0, game.evaluatePosition(boardDraw, playerX));
        game.symbol = 'O';
        assertEquals(0, game.evaluatePosition(boardDraw, playerO));

        char[] boardPlaying = {'X', 'O', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(-1, game.evaluatePosition(boardPlaying, playerX));
    }

    @Test
    void testMiniMax_WhenWinningMoveAvailable_ReturnsWinningMoveIndex() {
        char[] board = {'X', 'X', ' ', 'O', 'O', ' ', ' ', ' ', ' '};
        assertEquals(3, game.MiniMax(board, playerX));
    }

    @Test
    void testMiniMax_WhenOpponentCanWin_ReturnsBlockingMoveIndex() {
        char[] board = {'O', 'O', ' ', 'X', 'X', ' ', ' ', ' ', ' '};
        assertEquals(3, game.MiniMax(board, playerX));
    }
}
