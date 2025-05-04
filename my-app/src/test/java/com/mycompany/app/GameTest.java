package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class GameTest {

    @Test void testCheckStateXWins() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {
                'X', 'X', 'X',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test void testCheckStateOWins() {
        Game game = new Game();
        game.symbol = 'O';
        char[] board = {
                'O', 'O', 'O',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test void testCheckStateDraw() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {
                'X', 'O', 'X',
                'X', 'O', 'X',
                'O', 'X', 'O'
        };
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test void testCheckStatePlaying() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {
                'X', ' ', ' ',
                ' ', 'O', ' ',
                ' ', ' ', ' '
        };
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test void testGenerateMoves() {
        Game game = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        char[] board = {
                'X', ' ', 'O',
                ' ', 'X', ' ',
                ' ', ' ', 'O'
        };
        game.generateMoves(board, moves);
        assertEquals(5, moves.size());
    }

    @Test void testEvaluatePositionDraw() {
        Game game = new Game();
        Player player = game.player1;
        char[] board = {
                'X', 'O', 'X',
                'X', 'O', 'X',
                'O', 'X', 'O'
        };
        assertEquals(0, game.evaluatePosition(board, player));
    }

    @Test void testMiniMaxInstantWin() {
        Game game = new Game();
        Player player = game.player2;
        char[] board = {
                'O', 'O', ' ',
                'X', 'X', ' ',
                ' ', ' ', ' '
        };
        int move = game.MiniMax(board, player);
        assertEquals(3, move); // Ожидается выигрышный ход
    }

    @Test void testMinMoveTerminalState() {
        Game game = new Game();
        Player player = game.player1;
        char[] board = {
                'X', 'X', 'X',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        assertEquals(Game.INF, game.MinMove(board, player));
    }

    @Test void testMaxMoveTerminalState() {
        Game game = new Game();
        Player player = game.player1;
        char[] board = {
                'O', 'O', 'O',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        assertEquals(-Game.INF, game.MaxMove(board, player));
    }

    @Test
    void testCheckStateVerticalWin() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {
                'X', ' ', ' ',
                'X', ' ', ' ',
                'X', ' ', ' '
        };
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateDiagonalWin() {
        Game game = new Game();
        game.symbol = 'O';
        char[] board = {
                'O', ' ', ' ',
                ' ', 'O', ' ',
                ' ', ' ', 'O'
        };
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testEvaluatePositionPlaying() {
        Game game = new Game();
        Player player = game.player1;
        char[] board = {
                'X', ' ', ' ',
                ' ', 'O', ' ',
                ' ', ' ', ' '
        };
        assertEquals(-1, game.evaluatePosition(board, player));
    }

    @Test
    void testGenerateMovesFullBoard() {
        Game game = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        char[] board = {'X','O','X','O','X','O','X','O','X'};
        game.generateMoves(board, moves);
        assertTrue(moves.isEmpty());
    }

    @Test
    void testMaxMoveIntermediate() {
        Game game = new Game();
        Player player = game.player1;
        char[] board = {
                'X', ' ', ' ',
                ' ', 'O', ' ',
                ' ', ' ', ' '
        };
        assertTrue(game.MaxMove(board, player) < Game.INF);
    }

    @Test
    void testMinMoveIntermediate() {
        Game game = new Game();
        Player player = game.player2;
        char[] board = {
                'O', ' ', ' ',
                ' ', 'X', ' ',
                ' ', ' ', ' '
        };
        assertTrue(game.MinMove(board, player) > -Game.INF);
    }

    @Test
    void testGameInitialState() {
        Game game = new Game();
        assertEquals(State.PLAYING, game.state);
        assertEquals(9, game.board.length);
        assertEquals('X', game.player1.symbol);
    }

    @Test
    void testPlayerSwitch() {
        Game game = new Game();
        game.cplayer = game.player1;
        game.cplayer = game.player2;
        assertEquals(game.player2, game.cplayer);
    }
}
