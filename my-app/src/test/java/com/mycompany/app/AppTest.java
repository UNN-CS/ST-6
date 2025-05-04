package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class AppTest {

    @Test
    void testGameInitialization() {
        Game game = new Game();
        assertEquals(State.PLAYING, game.state);
        for (char c : game.board) {
            assertEquals(' ', c);
        }
    }

    @Test
    void testPlayerSymbols() {
        Game game = new Game();
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
    }

    @Test
    void testCheckStateXWinsRow() {
        Game game = new Game();
        game.board = new char[]{'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateOWinsColumn() {
        Game game = new Game();
        game.board = new char[]{'O', ' ', ' ', 'O', ' ', ' ', 'O', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateXWinsDiagonal() {
        Game game = new Game();
        game.board = new char[]{'X', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X'};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateDraw() {
        Game game = new Game();
        game.board = new char[]{'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(game.board));
    }

    @Test
    void testCheckStatePlaying() {
        Game game = new Game();
        game.board = new char[]{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(game.board));
    }

    @Test
    void testGenerateMovesEmptyBoard() {
        Game game = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size());
    }

    @Test
    void testGenerateMovesPartiallyFilled() {
        Game game = new Game();
        game.board = new char[]{'X', ' ', 'O', ' ', 'X', ' ', ' ', ' ', ' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(6, moves.size());
        assertTrue(moves.contains(1));
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(5));
    }

    @Test
    void testEvaluatePositionXWins() {
        Game game = new Game();
        game.board = new char[]{'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        int score = game.evaluatePosition(game.board, game.player1);
        assertEquals(Game.INF, score);
    }

    @Test
    void testEvaluatePositionOWins() {
        Game game = new Game();
        game.board = new char[]{'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        int score = game.evaluatePosition(game.board, game.player2);
        assertEquals(Game.INF, score);
    }

    @Test
    void testEvaluatePositionDraw() {
        Game game = new Game();
        game.board = new char[]{'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        int score = game.evaluatePosition(game.board, game.player1);
        assertEquals(0, score);
    }

    @Test
    void testEvaluatePositionPlaying() {
        Game game = new Game();
        game.board = new char[]{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        int score = game.evaluatePosition(game.board, game.player1);
        assertEquals(-1, score);
    }

    @Test
    void testMiniMaxBlocksWin() {
        Game game = new Game();
        game.board = new char[]{'X', 'X', ' ', 'O', 'O', ' ', ' ', ' ', ' '};
        int bestMove = game.MiniMax(game.board, game.player2);
        assertEquals(3, bestMove); // Блокирует победу X
    }

    @Test
    void testMinMoveChoosesLowestScore() {
        Game game = new Game();
        game.board = new char[]{'X', 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        int score = game.MinMove(game.board, game.player2);
        assertEquals(-Game.INF, score);
    }

    @Test
    void testMaxMoveChoosesHighestScore() {
        Game game = new Game();
        game.board = new char[]{'X', 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        int score = game.MaxMove(game.board, game.player1);
        assertEquals(Game.INF, score);
    }

    @Test
    void testTicTacToeCell() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(' ', cell.getMarker());
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    void testUtilityPrint() {
        Utility.print(new char[9]);
        Utility.print(new int[9]);
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        Utility.print(list);
        assertTrue(true);
    }
}