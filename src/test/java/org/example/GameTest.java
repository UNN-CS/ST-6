package org.example;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @Test
    public void testCheckStateOWIN() {
        Game game = new Game();
        char[] board = { 'X', 'O', 'X', ' ', 'O', 'X', ' ', 'O', ' ' };
        game.symbol = 'O';
        assertEquals(game.checkState(board), State.OWIN);
    }
    @Test
    public void testCheckStateXWIN() {
        Game game = new Game();
        char[] board = { 'X', 'O', 'X', 'O', 'X', 'O', 'X', ' ', ' ' };
        game.symbol = 'X';
        assertEquals(game.checkState(board), State.XWIN);
    }
    @Test
    public void testCheckStatePLAYING() {
        Game game = new Game();
        char[] board = { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' };
        assertEquals(game.checkState(board), State.PLAYING);
    }
    @Test
    public void testCheckStateDRAW() {
        Game game = new Game();
        char[] board = { 'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X' };
        assertEquals(game.checkState(board), State.DRAW);
    }
    @Test
    public void testInitialGameState() {
        Game game = new Game();
        assertEquals(game.state, State.PLAYING);
        assertEquals(game.player1.symbol, 'X');
        assertEquals(game.player2.symbol, 'O');
        assertEquals(game.board.length, 9);
	for (int i = 0; i < game.board.length; i++) {
            assertEquals(game.board[i], ' ');
	}
    }
    @Test
    public void testGenerateMovesEmptyArray() {
        Game game = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size());
    }

    @Test
    public void testGenerateMovesOneCol() {
        Game game = new Game();
        game.board[0] = 'X';
        game.board[4] = 'X';
        game.board[7] = 'X';
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(6, moves.size());
    }

    @Test
    public void testEvaluatePositionPlaying() {
        Game game = new Game();
        int result = game.evaluatePosition(game.board, game.player1);
        assertEquals(-1, result);
    }

    @Test
    public void testEvaluatePosition() {
        Game game = new Game();
        game.symbol = 'X';
        game.board = new char[] {'X', 'X', 'O', 'O', 'O', 'X', 'X', 'X', 'O'};
        int result = game.evaluatePosition(game.board, game.player1);
        assertEquals(0, result);
    }

    @Test
    public void testEvaluatePositionPlayer1Lose() {
        Game game = new Game();
        game.symbol = 'O';
        game.board[0] = game.player2.symbol;
        game.board[1] = game.player2.symbol;
        game.board[2] = game.player2.symbol;
        int result = game.evaluatePosition(game.board, game.player1);
        assertEquals(-Game.INF, result);
    }

    @Test
    public void testMiniMax() {
        Game game = new Game();
        game.board = new char[] {'X', 'X', ' ', 'O', 'O', ' ', ' ', ' ', ' '};
        int move = game.MiniMax(game.board, game.player2);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    public void testEvaluatePositionPlayer1Win() {
        Game game = new Game();
        game.symbol = 'X';
        game.board[0] = game.player1.symbol;
        game.board[1] = game.player1.symbol;
        game.board[2] = game.player1.symbol;
        int result = game.evaluatePosition(game.board, game.player1);
        assertEquals(+Game.INF, result);
    }
}
