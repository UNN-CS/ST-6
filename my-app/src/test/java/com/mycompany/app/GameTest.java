package com.mycompany.app;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    void testGameInitialization() {
        Game game = new Game();

        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);

        for (char c : game.board) {
            assertEquals(' ', c);
        }
    }

    @Test
    void testCheckStateXWin() {
        Game game = new Game();

        char[] board = {
                'X', 'X', 'X',
                ' ', 'O', ' ',
                'O', ' ', ' '
        };

        game.symbol = 'X';

        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateOWin() {
        Game game = new Game();

        char[] board = {
                'O', 'X', 'X',
                'O', 'X', ' ',
                'O', ' ', ' '
        };

        game.symbol = 'O';

        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testCheckStateDraw() {
        Game game = new Game();

        char[] board = {
                'X', 'O', 'X',
                'X', 'O', 'O',
                'O', 'X', 'X'
        };

        game.symbol = 'X';

        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    void testCheckStatePlaying() {
        Game game = new Game();

        char[] board = {
                'X', 'O', 'X',
                ' ', 'O', ' ',
                'O', 'X', ' '
        };

        game.symbol = 'X';

        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    void testGenerateMoves() {
        Game game = new Game();

        char[] board = {
                'X', 'O', 'X',
                ' ', 'O', ' ',
                'O', 'X', ' '
        };

        ArrayList<Integer> moves = new ArrayList<>();

        game.generateMoves(board, moves);

        assertEquals(3, moves.size());
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(5));
        assertTrue(moves.contains(8));
    }

    @Test
    void testEvaluatePositionWin() {
        Game game = new Game();

        Player player = new Player();
        player.symbol = 'X';

        char[] board = {
                'X', 'X', 'X',
                'O', 'O', ' ',
                ' ', ' ', ' '
        };

        game.symbol = 'X';

        assertEquals(Game.INF, game.evaluatePosition(board, player));
    }

    @Test
    void testEvaluatePositionLose() {
        Game game = new Game();

        Player player = new Player();
        player.symbol = 'X';

        char[] board = {
                'O', 'O', 'O',
                'X', 'X', ' ',
                ' ', ' ', ' '
        };

        game.symbol = 'O';

        assertEquals(-Game.INF, game.evaluatePosition(board, player));
    }

    @Test
    void testEvaluatePositionDraw() {
        Game game = new Game();

        Player player = new Player();
        player.symbol = 'X';

        char[] board = {
                'X', 'O', 'X',
                'X', 'O', 'O',
                'O', 'X', 'X'
        };

        game.symbol = 'X';

        assertEquals(0, game.evaluatePosition(board, player));
    }

    @Test
    void testMiniMaxReturnsMove() {
        Game game = new Game();

        Player player = new Player();
        player.symbol = 'O';

        char[] board = {
            'X', 'X', ' ',
            'O', ' ', ' ',
            ' ', ' ', ' '
        };

        int move = game.MiniMax(board, player);

        assertNotNull(move);
    }

    @Test
    void testMinMove() {
        Game game = new Game();

        Player player = new Player();
        player.symbol = 'X';

        char[] board = {
                'X', 'X', 'X',
                'O', 'O', ' ',
                ' ', ' ', ' '
        };

        game.symbol = 'X';

        int result = game.MinMove(board, player);

        assertEquals(Game.INF, result);
    }

    @Test
    void testMaxMove() {
        Game game = new Game();

        Player player = new Player();
        player.symbol = 'O';

        char[] board = {
                'O', 'O', 'O',
                'X', 'X', ' ',
                ' ', ' ', ' '
        };

        game.symbol = 'O';

        int result = game.MaxMove(board, player);

        assertEquals(Game.INF, result);
    }
}
