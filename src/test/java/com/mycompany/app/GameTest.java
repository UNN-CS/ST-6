package com.mycompany.app;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @Test
    public void testOWin() {
        Game game = new Game();
        char[] board = {' ', ' ', ' ', 'O', 'O', 'O', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }
    @Test
    public void testXWin() {
        Game game = new Game();
        char[] board = {' ', ' ', ' ', 'X', 'X', 'X', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }
    @Test
    public void testDiagonalWin() {
        Game game = new Game();
        char[] board1 = {'X', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X'};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board1));

        char[] board2 = {' ', ' ', 'O', ' ', 'O', ' ', 'O', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board2));
    }
    @Test
    public void testDraw() {
        Game game = new Game();
        char[] board = {'O', 'X', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(State.DRAW, game.checkState(board));
    }
    @Test
    public void testPlaying() {
        Game game = new Game();
        char[] board = {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(board));
    }
    @Test
    public void testGenerateMoves() {
        Game game = new Game();
        char[] board = {'X', 'X', 'O', 'O', 'X', 'O', 'O', ' ', ' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(2, moves.size());
        assertTrue(moves.contains(7));
        assertTrue(moves.contains(8));
    }
    @Test
    public void testEvaluatePositionWin() {
        Game game = new Game();
        char[] board = {'O', 'X', ' ', 'O', 'X', ' ', 'O', ' ', ' '};
        game.symbol = 'O';
        Player player = new Player();
        player.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(board, player));
    }
    @Test
    public void testEvaluatePositionLose() {
        Game game = new Game();
        char[] board = {'X','X','X', ' ',' ',' ', ' ',' ',' '};
        game.symbol = 'X';
        Player player = new Player();
        player.symbol = 'O';
        assertEquals(-Game.INF, game.evaluatePosition(board, player));
    }
    @Test
    public void testEvaluatePositionDraw() {
        Game game = new Game();
        char[] board = {'O', 'X', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        Player player = new Player();
        assertEquals(0, game.evaluatePosition(board, player));
    }
    @Test
    public void testMiniMaxPlayer2() {
        Game game = new Game();
        char[] board = {'O', 'X', 'X', 'X', 'O', 'O', 'O', ' ', ' '};
        int move = game.MiniMax(board, game.player2);
        assertEquals(9, move);
    }
    @Test
    public void testMiniMaxPlayer1() {
        Game game = new Game();
        char[] board = {'X', 'X', ' ', 'O', 'O', 'X', 'X', 'O', 'O'};
        int move = game.MiniMax(board, game.player1);
        assertEquals(3, move);
    }
}