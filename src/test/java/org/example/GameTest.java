package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;


public class GameTest {

    private Game game;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        game = new Game();
        player1 = game.player1;
        player2 = game.player2;
        game.cplayer = game.player1;
    }

    @Test
    void testInitialGameState() {
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', player1.symbol);
        assertEquals('O', player2.symbol);
        assertEquals(player1, game.cplayer);

        for (int i = 0; i < 9; i++) {
            assertEquals(' ', game.board[i]);
        }
    }

    @Test
    void testCheckStateWinConditions() {
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));

        board = new char[]{'O', ' ', ' ', 'O', ' ', ' ', 'O', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));

        board = new char[]{' ', ' ', 'X', ' ', 'X', ' ', 'X', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateDraw() {
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(board));

        board = new char[]{'X', 'O', 'X', 'X', ' ', 'O', 'O', 'X', 'X'};
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    void testGenerateMoves() {
        char[] board = {'X', ' ', 'O', ' ', 'X', ' ', ' ', ' ', ' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);

        assertEquals(6, moves.size());
        assertTrue(moves.contains(1));
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(5));
        assertTrue(moves.contains(6));
        assertTrue(moves.contains(7));
        assertTrue(moves.contains(8));
    }

    @Test
    void testEvaluatePosition() {
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(board, player1));
        assertEquals(-Game.INF, game.evaluatePosition(board, player2));

        board = new char[]{'O', ' ', ' ', 'O', ' ', ' ', 'O', ' ', ' '};
        game.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(board, player2));
        assertEquals(-Game.INF, game.evaluatePosition(board, player1));

        board = new char[]{'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(0, game.evaluatePosition(board, player1));
        assertEquals(0, game.evaluatePosition(board, player2));

        board = new char[]{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(-1, game.evaluatePosition(board, player1));
    }

    @Test
    void testMiniMaxForWin() {
        char[] board = {'X', 'X', ' ', 'O', 'O', ' ', ' ', ' ', ' '};
        System.arraycopy(board, 0, game.board, 0, 9);

        assertEquals(3, game.MiniMax(board, player1));
    }
}
