package com.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    private static Game game;

    @BeforeAll
    public static void setUp() {
        game = new Game();
    }

    @Test
    public void testPlayerInitialization() {
        Player player = new Player();
        assertNotNull(player);
        assertFalse(player.selected);
        assertFalse(player.win);
        assertEquals(0, player.move);
    }

    @Test
    public void testGameInitialization() {
        Game game = new Game();
        assertNotNull(game);
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        for (char cell : game.board) {
            assertEquals(' ', cell);
        }
    }

    @Test
    public void testCheckStateWhenPlaying() {
        Game game = new Game();
        assertEquals(State.PLAYING, game.checkState(game.board));
    }

    @Test
    public void testCheckStateForXWinTopRow() {
        Game game = new Game();
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = 'X';
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    public void testCheckStateForOWinDiagonal() {
        Game game = new Game();
        game.board[0] = 'O';
        game.board[4] = 'O';
        game.board[8] = 'O';
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    public void testCheckStateForDraw() {
        Game game = new Game();
        game.board = new char[] {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(State.DRAW, game.checkState(game.board));
    }

    @Test
    public void testGenerateMoves() {
        Game game = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size());

        game.board[0] = 'X';
        game.board[4] = 'O';
        moves.clear();
        game.generateMoves(game.board, moves);
        assertEquals(7, moves.size());
        assertFalse(moves.contains(0));
        assertFalse(moves.contains(4));
    }

    @Test
    public void testEvaluatePositionXWin() {
        Game game = new Game();
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = 'X';
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(game.board, game.player1));
    }

    @Test
    public void testEvaluatePositionOWin() {
        Game game = new Game();
        game.board[0] = 'O';
        game.board[1] = 'O';
        game.board[2] = 'O';
        game.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(game.board, game.player2));
    }

    @Test
    public void testEvaluatePositionDraw() {
        Game game = new Game();
        game.board = new char[] {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(0, game.evaluatePosition(game.board, game.player1));
    }

    @Test
    public void testMiniMaxInitialMove() {
        Game game = new Game();
        int move = game.MiniMax(game.board, game.player1);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    public void testMiniMaxBlockOpponentWin() {
        Game game = new Game();
        game.board[0] = 'O';
        game.board[1] = 'O';
        game.board[3] = 'X';
        game.board[4] = 'X';
        game.board[8] = 'X';
        int move = game.MiniMax(game.board, game.player2);
        assertEquals(3, move);
    }

    @Test
    public void testMiniMaxChooseWinningMove() {
        Game game = new Game();
        game.board[0] = 'O';
        game.board[1] = 'O';
        game.board[3] = 'X';
        game.board[4] = 'X';
        game.board[8] = 'X';
        int move = game.MiniMax(game.board, game.player2);
        assertEquals(3, move);
    }
}
