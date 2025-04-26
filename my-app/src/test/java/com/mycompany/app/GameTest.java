package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {
    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testGameInitialization() {
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);

        for (int i = 0; i < 9; i++) {
            assertEquals(' ', game.board[i]);
        }
    }

    @Test
    public void testCheckStateEmptyBoard() {
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(game.board));
    }

    @Test
    public void testCheckStateXWin() {
        char[] board = {
            'X', 'X', 'X',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));

        board = new char[]{
            'X', ' ', ' ',
            'X', ' ', ' ',
            'X', ' ', ' '
        };
        assertEquals(State.XWIN, game.checkState(board));
        board = new char[]{
            'X', ' ', ' ',
            ' ', 'X', ' ',
            ' ', ' ', 'X'
        };
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateOWin() {
        char[] board = {
            'O', 'O', 'O',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
        board = new char[]{
            'O', ' ', ' ',
            'O', ' ', ' ',
            'O', ' ', ' '
        };
        assertEquals(State.OWIN, game.checkState(board));

        board = new char[]{
            'O', ' ', ' ',
            ' ', 'O', ' ',
            ' ', ' ', 'O'
        };
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateDraw() {
        char[] board = {
            'X', 'O', 'X',
            'X', 'O', 'O',
            'O', 'X', 'X'
        };
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(board));
        
        game.symbol = 'O';
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    public void testGenerateMoves() {
        ArrayList<Integer> moveList = new ArrayList<>();
        game.generateMoves(game.board, moveList);
        assertEquals(9, moveList.size());
        char[] board = {
            'X', ' ', 'O',
            ' ', 'X', ' ',
            'O', ' ', ' '
        };
        moveList.clear();
        game.generateMoves(board, moveList);
        assertEquals(5, moveList.size());
        assertTrue(moveList.contains(1));
        assertTrue(moveList.contains(3));
        assertTrue(moveList.contains(5));
        assertTrue(moveList.contains(7));
        assertTrue(moveList.contains(8));
    }

    @Test
    public void testEvaluatePosition() {
        char[] board = {
            'X', 'X', 'X',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(board, game.player1));
        assertEquals(-Game.INF, game.evaluatePosition(board, game.player2));

        board = new char[]{
            'O', 'O', 'O',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        game.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(board, game.player2));
        assertEquals(-Game.INF, game.evaluatePosition(board, game.player1));
        board = new char[]{
            'X', 'O', 'X',
            'X', 'O', 'O',
            'O', 'X', 'X'
        };
        assertEquals(0, game.evaluatePosition(board, game.player1));
        assertEquals(0, game.evaluatePosition(board, game.player2));
        
        board = new char[]{
            'X', ' ', ' ',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        assertEquals(-1, game.evaluatePosition(board, game.player1));
    }

    @Test
    public void testMiniMax() {
        char[] board = {
            'X', 'X', ' ',
            'O', 'O', ' ',
            ' ', ' ', ' '
        };
        game.board = board.clone();
        int bestMove = game.MiniMax(game.board, game.player1);
        assertEquals(3, bestMove);

        board = new char[]{
            'O', 'O', ' ',
            ' ', 'X', ' ',
            ' ', ' ', ' '
        };
        game.board = board.clone();
        bestMove = game.MiniMax(game.board, game.player1);
        assertEquals(3, bestMove);
    }

    @Test
    public void testMinMove() {
        char[] board = {
            'X', ' ', ' ',
            ' ', 'X', ' ',
            ' ', ' ', ' '
        };
        game.board = board.clone();
        int value = game.MinMove(board, game.player1);
        assertTrue(value >= -Game.INF && value <= Game.INF);
    }

    @Test
    public void testMaxMove() {
        char[] board = {
            'X', 'X', ' ',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        game.board = board.clone();
        int value = game.MaxMove(board, game.player1);
        assertTrue(value >= 0);
    }
} 