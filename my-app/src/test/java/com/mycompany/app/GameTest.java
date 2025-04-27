package com.mycompany.app;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void testGameInitialization() {
        Game game = new Game();
        assertNotNull(game.player1);
        assertNotNull(game.player2);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals(State.PLAYING, game.state);
        assertNotNull(game.board);
        assertEquals(9, game.board.length);
        for (char cell : game.board) {
            assertEquals(' ', cell);
        }
    }

    @Test
    public void testCheckState() {
        Game game = new Game();
        char[] board = {
            'X', 'X', 'X',
            ' ', 'O', 'O',
            ' ', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));

        game.symbol = 'O';
        assertEquals(State.PLAYING, game.checkState(board));

        board = new char[]{
            'X', 'O', 'X',
            'O', 'X', 'O',
            'O', 'X', 'O'
        };
        assertEquals(State.DRAW, game.checkState(board));

        board = new char[]{
            'O', 'O', 'O',
            'X', 'X', ' ',
            ' ', ' ', ' '
        };
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    public void testGenerateMoves() {
        Game game = new Game();
        char[] board = {
            'X', 'O', 'X',
            ' ', 'O', ' ',
            'X', ' ', 'O'
        };
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(3, moves.size());
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(5));
        assertTrue(moves.contains(7));
    }

    @Test
    public void testEvaluatePosition() {
        Game game = new Game();
        char[] board = {
            'X', 'X', 'X',
            ' ', 'O', 'O',
            ' ', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(board, game.player1));

        board = new char[]{
            'O', 'O', 'O',
            'X', 'X', ' ',
            ' ', ' ', ' '
        };
        game.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(board, game.player2));

        board = new char[]{
            'X', 'O', 'X',
            'O', 'X', 'O',
            'O', 'X', 'O'
        };
        assertEquals(0, game.evaluatePosition(board, game.player1));
    }

    @Test
    public void testMiniMax() {
        Game game = new Game();
        char[] board = {
            'X', 'O', 'X',
            ' ', 'O', ' ',
            'X', ' ', 'O'
        };
        game.player2.symbol = 'O';
        int bestMove = game.MiniMax(board, game.player2);
        assertTrue(bestMove == 4 || bestMove == 6 || bestMove == 8);
    }

    @Test
    public void testMinMove() {
        Game game = new Game();
        char[] board = {
            'X', 'O', 'X',
            ' ', 'O', ' ',
            'X', ' ', 'O'
        };
        game.player2.symbol = 'O';
        int minMoveValue = game.MinMove(board, game.player2);
        assertTrue(minMoveValue <= Game.INF && minMoveValue >= -Game.INF);
    }

    @Test
    public void testMaxMove() {
        Game game = new Game();
        char[] board = {
            'X', 'O', 'X',
            ' ', 'O', ' ',
            'X', ' ', 'O'
        };
        game.player1.symbol = 'X';
        int maxMoveValue = game.MaxMove(board, game.player1);
        assertTrue(maxMoveValue <= Game.INF && maxMoveValue >= -Game.INF);
    }
}