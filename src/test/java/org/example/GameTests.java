package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class GameTests {
    Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testState() {
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals(' ', game.board[0]);
    }

    @Test
    public void TestCheckState() {
        State state = game.checkState(game.board);
        assertEquals(State.PLAYING, state);
    }

    @Test
    public void TestCheckStateXWin() {
        game.symbol = 'X';
        char[] field = {'X', 'X', 'X', 'O', 'O', ' ', ' ', ' '};
        State state = game.checkState(field);
        assertEquals(State.XWIN, state);
    }

    @Test
    public void TestCheckStateOWin() {
        game.symbol = 'O';
        char[] field = {'O', 'O', 'O', 'X', 'X', ' ', 'X', 'X', ' '};
        State state = game.checkState(field);
        assertEquals(State.OWIN, state);
    }

    @Test
    public void TestEvaluatePositionDraw() {
        game.symbol = 'X';
        game.board = new char[] {'X', 'X', 'O', 'O', 'O', 'X', 'O', 'X', 'O'};
        int result = game.evaluatePosition(game.board, game.player1);
        assertEquals(0, result);
    }

    @Test
    public void TestEvaluateFirstPlayerWin() {
        game.symbol = 'X';
        for (int i = 0; i < 3; i++) {
            game.board[i] = game.symbol;
        }
        int result = game.evaluatePosition(game.board, game.player1);
        assertEquals(Game.INF, result);
        result = game.evaluatePosition(game.board, game.player2);
        assertEquals(-Game.INF, result);
    }

    @Test
    public void TestCheckStateDraw() {
        game.symbol = 'X';
        char[] field = {'X', 'X', 'O', 'O', 'O', 'X', 'O', 'X', 'O'};
        State state = game.checkState(field);
        assertEquals(State.DRAW, state);
    }

    @Test
    public void TestGenerateMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size());
    }

    @Test
    public void TestEvaluateSecondPlayerWin() {
        game.symbol = 'O';
        for (int i = 0; i < 3; i++) {
            game.board[i] = game.symbol;
        }

        int result = game.evaluatePosition(game.board, game.player1);
        assertEquals(-Game.INF, result);
        result = game.evaluatePosition(game.board, game.player2);
        assertEquals(Game.INF, result);
    }

    @Test
    public void TestMinimax() {
        game.symbol = 'X';
        game.board = new char[] {'X', ' ', 'O', ' ', ' ', 'X', ' ', 'X', 'O'};
        int move = game.MiniMax(game.board, game.player2);
        assertTrue(move >= 1 && move <= 9);
    }
}
