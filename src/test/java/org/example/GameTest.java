package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class GameTest {

    @Test
    public void testCheckState() {
        Game game = new Game();
        char[] boardXWin = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        char[] boardOWin = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        char[] boardDraw = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        char[] boardPlaying = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};

        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(boardXWin));

        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(boardOWin));

        assertEquals(State.DRAW, game.checkState(boardDraw));
        assertEquals(State.PLAYING, game.checkState(boardPlaying));
    }

    @Test
    public void testGenerateMoves() {
        Game game = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.generateMoves(board, moves);
        assertEquals(6, moves.size());
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(4));
    }

    @Test
    public void testEvaluatePosition() {
        Game game = new Game();
        char[] boardXWin = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        char[] boardDraw = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};

        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(boardXWin, game.player1));
        assertEquals(0, game.evaluatePosition(boardDraw, game.player1));
    }

    @Test
    public void testMinimax() {
        Game game = new Game();
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.player2.symbol = 'O';
        int move = game.MiniMax(board, game.player2);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    public void testMinMove() {
        Game game = new Game();
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.player2.symbol = 'O';
        int value = game.MinMove(board, game.player2);
        assertTrue(value >= -Game.INF && value <= Game.INF);
    }

    @Test
    public void testMaxMove() {
        Game game = new Game();
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.player2.symbol = 'O';
        int value = game.MaxMove(board, game.player2);
        assertTrue(value >= -Game.INF && value <= Game.INF);
    }

    @Test
    public void testGameInitialState() {
        Game game = new Game();
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals(' ', game.board[0]);
    }
}
