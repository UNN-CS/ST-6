package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class GameTest {
    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void testInitialGameState() {
        assertEquals(State.PLAYING, game.state);
        assertArrayEquals(new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, game.board);
    }

    @Test
    void testCheckState() {
        char[] boardXWin = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(boardXWin));

        char[] boardOWin = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(boardOWin));

        char[] boardDraw = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'};
        game.symbol = 'X'; // Symbol can be anything since it's a draw
        assertEquals(State.DRAW, game.checkState(boardDraw));
    }

    @Test
    void testGenerateMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size());
    }

    @Test
    void testEvaluatePosition() {
        char[] boardXWin = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(boardXWin, game.player1));
        assertEquals(-Game.INF, game.evaluatePosition(boardXWin, game.player2));

        char[] boardDraw = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'};
        assertEquals(0, game.evaluatePosition(boardDraw, game.player1));
    }

    @Test
    void testMinimax() {
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[8] = 'O';
        int move = game.MiniMax(game.board, game.player2);
        assertEquals(3, move);
    }
}