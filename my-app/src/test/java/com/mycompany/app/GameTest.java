package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void testGameInitialization() {
        assertEquals(State.PLAYING, game.state);
        assertEquals(9, game.board.length);
        for (char c : game.board) {
            assertEquals(' ', c);
        }
    }

    @Test
    void testCheckWinXHorizontal() {
        char[] b = {'X','X','X',' ',' ',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(b));
    }

    @Test
    void testCheckWinOVertical() {
        char[] b = {'O',' ',' ','O',' ',' ','O',' ',' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(b));
    }

    @Test
    void testCheckWinXMainDiagonal() {
        char[] b = {'X',' ',' ',' ','X',' ',' ',' ','X'};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(b));
    }

    @Test
    void testCheckWinOAntiDiagonal() {
        char[] b = {' ',' ','O',' ','O',' ','O',' ',' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(b));
    }

    @Test
    void testCheckStillPlaying() {
        char[] b = {'X','O','X',' ',' ',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(b));
    }

    @Test
    void testCheckDraw() {
        char[] b = {'X','O','X','X','O','X','O','X','O'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(b));
    }

    @Test
    void testGenerateMovesPartial() {
        char[] b = {'X','O',' ',' ','X',' ','O',' ',' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(b, moves);
        assertEquals(5, moves.size());
        assertFalse(moves.contains(0));
        assertFalse(moves.contains(1));
        assertFalse(moves.contains(4));
    }

    @Test
    void testEvaluatePositionWinForPlayer() {
        Player p = new Player();
        p.symbol = 'X';
        char[] b = {'X','X','X',' ',' ',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(b, p));
    }

    @Test
    void testEvaluatePositionLoseForPlayer() {
        Player p = new Player();
        p.symbol = 'X';
        char[] b = {'O','O','O',' ',' ',' ',' ',' ',' '};
        game.symbol = 'O';
        assertEquals(-Game.INF, game.evaluatePosition(b, p));
    }

    @Test
    void testEvaluatePositionDraw() {
        Player p = new Player();
        p.symbol = 'O';
        char[] b = {'X','O','X','X','O','X','O','X','O'};
        game.symbol = 'X';
        assertEquals(0, game.evaluatePosition(b, p));
    }

    @Test
    void testEvaluatePositionNonTerminal() {
        Player p = new Player();
        p.symbol = 'X';
        char[] b = {'X',' ',' ',' ',' ',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(-1, game.evaluatePosition(b, p));
    }

    @Test
    void testMinMoveOnTerminalBoard() {
        Player p = new Player();
        p.symbol = 'O';
        char[] b = {'O','O','O',' ',' ',' ',' ',' ',' '};
        game.symbol = 'O';
        assertEquals(Game.INF, game.MinMove(b, p));
    }

    @Test
    void testMaxMoveOnTerminalBoard() {
        Player p = new Player();
        p.symbol = 'X';
        char[] b = {'X','X','X',' ',' ',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.MaxMove(b, p));
    }

    @Test
    void testMiniMaxFirstMoveValid() {
        Player ai = new Player();
        ai.symbol = 'O';
        int move = game.MiniMax(game.board, ai);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    void testMiniMaxChoosesWinningMove() {
        game.board = new char[]{
                'O','O',' ',
                'X','X',' ',
                ' ',' ',' '
        };
        Player ai = new Player();
        ai.symbol = 'O';
        int best = game.MiniMax(game.board, ai);
        assertEquals(3, best);
    }
}
