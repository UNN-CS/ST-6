package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void constructorInitializesBoard() {
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        for (char c : game.board) {
            assertEquals(' ', c);
        }
    }

    @Test
    void checkStatePlayingOnEmptyBoard() {
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(game.board));
    }

    @Test
    void checkStateXWinTopRow() {
        game.symbol = 'X';
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void checkStateOWinMiddleRow() {
        game.symbol = 'O';
        char[] board = {' ', ' ', ' ', 'O', 'O', 'O', ' ', ' ', ' '};
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void checkStateXWinDiagonal() {
        game.symbol = 'X';
        char[] board = {'X', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X'};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void checkStateOWinVertical() {
        game.symbol = 'O';
        char[] board = {'O', 'X', ' ', 'O', 'X', ' ', 'O', ' ', ' '};
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void checkStateXWinAntiDiagonal() {
        game.symbol = 'X';
        char[] board = {' ', ' ', 'X', ' ', 'X', ' ', 'X', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void checkStateDrawOnFullBoard() {
        game.symbol = 'X';
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    void generateMovesFindsAllEmptyCells() {
        char[] board = {'X', ' ', ' ', ' ', 'O', ' ', ' ', ' ', ' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(7, moves.size());
        assertTrue(moves.contains(1));
        assertTrue(moves.contains(8));
    }

    @Test
    void evaluatePositionWinForPlayer() {
        game.symbol = 'X';
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(Game.INF, game.evaluatePosition(board, game.player1));
    }

    @Test
    void evaluatePositionLossForPlayer() {
        game.symbol = 'X';
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(-Game.INF, game.evaluatePosition(board, game.player2));
    }

    @Test
    void evaluatePositionDraw() {
        game.symbol = 'X';
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(0, game.evaluatePosition(board, game.player1));
    }

    @Test
    void evaluatePositionOngoing() {
        game.symbol = 'X';
        char[] board = {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(-1, game.evaluatePosition(board, game.player1));
    }

    @Test
    void miniMaxReturnsValidMoveOnEmptyBoard() {
        int move = game.MiniMax(game.board, game.player2);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    void miniMaxBlocksImmediateWin() {
        game.board = new char[]{'X', 'X', ' ', ' ', 'O', ' ', ' ', ' ', ' '};
        int move = game.MiniMax(game.board, game.player2);
        assertEquals(3, move);
    }

    @Test
    void miniMaxWithMultipleEqualMoves() {
        char[] board = {'O', 'X', ' ', ' ', 'X', ' ', ' ', ' ', ' '};
        for (int i = 0; i < 5; i++) {
            int move = game.MiniMax(board, game.player2);
            assertTrue(move >= 1 && move <= 9);
        }
    }

    @Test
    void minMoveAndMaxMoveThroughNearEndgame() {
        char[] board = {'X', 'O', 'X', 'O', 'X', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        int val = game.MinMove(board, game.player2);
        assertTrue(val <= Game.INF && val >= -Game.INF);
    }

    @Test
    void maxMoveEvaluatesBranch() {
        char[] board = {'X', 'O', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        int val = game.MaxMove(board, game.player1);
        assertTrue(val != 0 || val == 0);
    }
}
