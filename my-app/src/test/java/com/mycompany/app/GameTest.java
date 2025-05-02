package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void testInitialBoardIsEmpty() {
        for (int i = 0; i < 9; i++) {
            assertEquals(' ', game.board[i]);
        }
    }

    @Test
    void testCheckStateXWins() {
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateOWins() {
        char[] board = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testCheckStateDraw() {
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    void testGenerateMoves() {
        char[] board = {'X', ' ', 'O', ' ', 'X', ' ', ' ', ' ', ' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertArrayEquals(new Integer[]{1, 3, 5, 6, 7, 8}, moves.toArray());
    }

    @Test
    void testEvaluatePositionXWins() {
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(board, game.player1));
    }

    @Test
    void testMinMax() {
        char[] board = {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        int bestMove = game.MiniMax(board, game.player1);
        assertTrue(bestMove >= 1 && bestMove <= 9);
    }
}