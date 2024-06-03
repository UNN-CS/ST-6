package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void testCheckStateXWin() {
        Game game = new Game();
        char[] boardXWin = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(boardXWin));
    }

    @Test
    void testCheckStateOWin() {
        Game game = new Game();
        char[] boardOWin = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(boardOWin));
    }

    @Test
    void testCheckStateDraw() {
        Game game = new Game();
        char[] boardDraw = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(State.DRAW, game.checkState(boardDraw));
    }

    @Test
    void testCheckStatePlaying() {
        Game game = new Game();
        char[] boardPlaying = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.PLAYING, game.checkState(boardPlaying));
    }

    @Test
    void testGenerateMovesPartiallyFilledBoard() {
        Game game = new Game();
        List<Integer> expectedMoves = Arrays.asList(3, 4, 5, 6, 7, 8);
        ArrayList<Integer> moves = new ArrayList<>();
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};

        game.generateMoves(board, moves);

        assertEquals(expectedMoves.size(), moves.size());
        assertTrue(moves.containsAll(expectedMoves));
    }

    @Test
    void testGenerateMovesEmptyBoard() {
        Game game = new Game();
        List<Integer> expectedMoves = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8);
        ArrayList<Integer> moves = new ArrayList<>();
        char[] board = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};

        game.generateMoves(board, moves);

        assertEquals(expectedMoves.size(), moves.size());
        assertTrue(moves.containsAll(expectedMoves));
    }

    @Test
    void testGenerateMovesFullBoard() {
        Game game = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        char[] board = {'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X'};

        game.generateMoves(board, moves);

        assertEquals(0, moves.size());
    }

    @Test
    void testEvaluatePositionXWin() {
        Game game = new Game();
        char[] boardXWin = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(boardXWin, game.player1));
    }

    @Test
    void testEvaluatePositionDraw() {
        Game game = new Game();
        char[] boardDraw = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(0, game.evaluatePosition(boardDraw, game.player1));
    }

    @Test
    void testMiniMax() {
        Game game = new Game();
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.player2.symbol = 'O';
        int move = game.MiniMax(board, game.player2);
        assertTrue(move >= 0 && move <= 8);
    }

    @Test
    void testMinMove() {
        Game game = new Game();
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.player2.symbol = 'O';
        int value = game.MinMove(board, game.player2);
        assertTrue(value >= -Game.INF && value <= Game.INF);
    }

    @Test
    void testMaxMove() {
        Game game = new Game();
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.player2.symbol = 'O';
        int value = game.MaxMove(board, game.player2);
        assertTrue(value >= -Game.INF && value <= Game.INF);
    }

    @Test
    void testGameInitialState() {
        Game game = new Game();
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertArrayEquals(new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, game.board);
    }

}