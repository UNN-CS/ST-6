package com.mycompany.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void testInitialBoardEmpty() {
        for (char c : game.board) {
            assertEquals(' ', c);
        }
        assertEquals(State.PLAYING, game.state);
    }

    @Test
    void testCheckStateXWin() {
        game.symbol = 'X';
        game.board = new char[]{'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateOWin() {
        game.symbol = 'O';
        game.board = new char[]{'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateXWinVertical() {
        game.symbol = 'X';
        game.board = new char[]{'X', ' ', ' ', 'X', ' ', ' ', 'X', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateXWinDiagonal() {
        game.symbol = 'X';
        game.board = new char[]{'X', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X'};
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateXWinAntiDiagonal() {
        game.symbol = 'X';
        game.board = new char[]{' ', ' ', 'X', ' ', 'X', ' ', 'X', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateDraw() {
        game.symbol = 'X';
        game.board = new char[]{'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'};
        assertEquals(State.DRAW, game.checkState(game.board));
    }

    @Test
    void testCheckStatePlaying() {
        game.symbol = 'X';
        game.board = new char[]{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.PLAYING, game.checkState(game.board));
    }

    @Test
    void testGenerateMoves() {
        game.board = new char[]{'X', ' ', 'O', ' ', 'X', ' ', ' ', ' ', ' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertTrue(moves.contains(1));
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(5));
        assertTrue(moves.contains(6));
        assertTrue(moves.contains(7));
        assertTrue(moves.contains(8));
        assertEquals(6, moves.size());
    }

    @Test
    void testGenerateMovesFullBoard() {
        game.board = new char[]{'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(0, moves.size());
    }

    @Test
    void testEvaluatePositionWinForPlayer() {
        Player player = new Player();
        player.symbol = 'X';
        game.board = new char[]{'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(game.board, player));
    }

    @Test
    void testEvaluatePositionLoseForPlayer() {
        Player player = new Player();
        player.symbol = 'O';
        game.board = new char[]{'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(-Game.INF, game.evaluatePosition(game.board, player));
    }

    @Test
    void testEvaluatePositionDraw() {
        Player player = new Player();
        player.symbol = 'X';
        game.board = new char[]{'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'};
        game.symbol = 'X';
        assertEquals(0, game.evaluatePosition(game.board, player));
    }

    @Test
    void testEvaluatePositionPlaying() {
        Player player = new Player();
        player.symbol = 'X';
        game.board = new char[]{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(-1, game.evaluatePosition(game.board, player));
    }

    @Test
    void testMiniMaxEmptyBoard() {
        Player player = new Player();
        player.symbol = 'X';
        game.board = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        int move = game.MiniMax(game.board, player);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    void testMiniMaxBlocksWin() {
        Player player = new Player();
        player.symbol = 'O';
        game.board = new char[]{'X', 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        int move = game.MiniMax(game.board, player);
        assertTrue(move >= 1 && move <= 9);
        game.board[move - 1] = player.symbol;
        game.symbol = 'X';
        State state = game.checkState(game.board);
        assertNotEquals(State.XWIN, state);
    }

    @Test
    void testMinMoveTerminal() {
        Player player = new Player();
        player.symbol = 'X';
        game.board = new char[]{'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        int val = game.MinMove(game.board, player);
        assertEquals(Game.INF, val);
    }

    @Test
    void testMaxMoveTerminal() {
        Player player = new Player();
        player.symbol = 'X';
        game.board = new char[]{'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        int val = game.MaxMove(game.board, player);
        assertEquals(Game.INF, val);
    }

    @Test
    void testMiniMaxWithMultipleBestMoves() {
        Player player = new Player();
        player.symbol = 'X';
        game.board = new char[]{' ', ' ', ' ', ' ', 'X', ' ', ' ', ' ', ' '};
        int move = game.MiniMax(game.board, player);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    void testMinMoveNonTerminal() {
        Player player = new Player();
        player.symbol = 'X';
        game.board = new char[]{'X', ' ', ' ', ' ', 'O', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        int val = game.MinMove(game.board, player);
        assertTrue(val >= -Game.INF && val <= Game.INF);
    }

    @Test
    void testMaxMoveNonTerminal() {
        Player player = new Player();
        player.symbol = 'X';
        game.board = new char[]{'X', ' ', ' ', ' ', 'O', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        int val = game.MaxMove(game.board, player);
        assertTrue(val >= -Game.INF && val <= Game.INF);
    }

    @Test
    void testCheckStateOWinVertical() {
        game.symbol = 'O';
        game.board = new char[]{'O', ' ', ' ', 'O', ' ', ' ', 'O', ' ', ' '};
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateOWinDiagonal() {
        game.symbol = 'O';
        game.board = new char[]{'O', ' ', ' ', ' ', 'O', ' ', ' ', ' ', 'O'};
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateMultipleWinningConditions() {
        game.symbol = 'X';

        // Горизонталь
        game.board = new char[]{'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(game.board));

        // Вертикаль
        game.board = new char[]{'X', ' ', ' ', 'X', ' ', ' ', 'X', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(game.board));

        // Диагональ
        game.board = new char[]{'X', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X'};
        assertEquals(State.XWIN, game.checkState(game.board));

        // Антидиагональ
        game.board = new char[]{' ', ' ', 'X', ' ', 'X', ' ', 'X', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateForOWin() {
        game.symbol = 'O';
        game.board = new char[]{'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    void testEvaluatePositionWithPlayingState() {
        Player player = new Player();
        player.symbol = 'X';
        game.board = new char[]{'X', 'O', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(-1, game.evaluatePosition(game.board, player));
    }
}