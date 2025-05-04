package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

public class AppTest {

    // Тесты для Game
    @Test
    void testGameSetup() {
        Game game = new Game();
        assertNotNull(game.player1);
        assertNotNull(game.player2);
        assertEquals('O', game.player2.symbol);
        assertEquals('X', game.player1.symbol);
        assertEquals(9, game.board.length);
    }

    @Test
    void testCheckStateXWinVertical() {
        Game game = new Game();
        game.board[2] = 'X';
        game.board[5] = 'X';
        game.board[8] = 'X'; // Победа по третьему столбцу
        game.cplayer = game.player1;
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateOWinDiagonal() {
        Game game = new Game();
        game.board[2] = 'O';
        game.board[4] = 'O';
        game.board[6] = 'O'; // Победа по второстепенной диагонали
        game.cplayer = game.player2;
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateDrawAlternate() {
        Game game = new Game();
        game.board = new char[]{'O', 'X', 'O', 'X', 'O', 'X', 'X', 'O', 'X'};
        game.cplayer = game.player2;
        game.symbol = 'O';
        assertEquals(State.DRAW, game.checkState(game.board));
    }

    @Test
    void testCheckStateOngoing() {
        Game game = new Game();
        game.board[4] = 'O'; // Ход в центре
        game.cplayer = game.player2;
        game.symbol = 'O';
        assertEquals(State.PLAYING, game.checkState(game.board));
    }

    @Test
    void testGenerateMovesAllEmpty() {
        Game game = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size());
        assertTrue(moves.contains(4)); // Проверяем наличие центрального хода
    }

    @Test
    void testGenerateMovesSomeOccupied() {
        Game game = new Game();
        game.board[1] = 'O';
        game.board[3] = 'X';
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(7, moves.size());
        assertFalse(moves.contains(1));
        assertFalse(moves.contains(3));
    }

    @Test
    void testEvaluatePositionXWinDiagonal() {
        Game game = new Game();
        game.board[0] = 'X';
        game.board[4] = 'X';
        game.board[8] = 'X'; // Победа по главной диагонали
        game.cplayer = game.player1;
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(game.board, game.player1));
        assertEquals(-Game.INF, game.evaluatePosition(game.board, game.player2));
    }

    @Test
    void testEvaluatePositionOWinVertical() {
        Game game = new Game();
        game.board[1] = 'O';
        game.board[4] = 'O';
        game.board[7] = 'O'; // Победа по второму столбцу
        game.cplayer = game.player2;
        game.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(game.board, game.player2));
        assertEquals(-Game.INF, game.evaluatePosition(game.board, game.player1));
    }

    @Test
    void testEvaluatePositionTie() {
        Game game = new Game();
        game.board = new char[]{'O', 'X', 'O', 'X', 'O', 'X', 'X', 'O', 'X'};
        game.cplayer = game.player2;
        game.symbol = 'O';
        assertEquals(0, game.evaluatePosition(game.board, game.player2));
    }

    @Test
    void testEvaluatePositionInProgress() {
        Game game = new Game();
        game.board[8] = 'X'; // Ход в углу
        game.cplayer = game.player1;
        game.symbol = 'X';
        assertEquals(-1, game.evaluatePosition(game.board, game.player1));
    }

    @Test
    void testMiniMaxFirstMove() {
        Game game = new Game();
        game.cplayer = game.player1;
        int move = game.MiniMax(game.board, game.player1);
        assertTrue(move >= 1 && move <= 9);
        assertTrue(game.board[move - 1] == ' '); // Проверяем, что ход валидный
    }

    @Test
    void testMinMoveDuringGame() {
        Game game = new Game();
        game.board[4] = 'O'; // Ход в центре
        game.cplayer = game.player2;
        game.symbol = 'O';
        int val = game.MinMove(game.board, game.player2);
        assertTrue(val >= -Game.INF && val <= Game.INF);
    }

    @Test
    void testMaxMoveDuringGame() {
        Game game = new Game();
        game.board[4] = 'X'; // Ход в центре
        game.cplayer = game.player1;
        game.symbol = 'X';
        int val = game.MaxMove(game.board, game.player1);
        assertTrue(val >= -Game.INF && val <= Game.INF);
    }

    // Тесты для TicTacToeCell
    @Test
    void testTicTacToeCellMarkerChange() {
        TicTacToeCell cell = new TicTacToeCell(2, 1, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    // Тесты для Utility
    @Test
    void testUtilityPrintCharArrayMixed() {
        char[] board = new char[]{' ', 'X', ' ', 'O', ' ', 'X', ' ', 'O', ' '};
        Utility.print(board);
    }

    @Test
    void testUtilityPrintArrayListTwoElements() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(0);
        moves.add(5);
        Utility.print(moves);
    }

    // Тесты для TicTacToePanel
    @Test
    void testTicTacToePanelSetup() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        assertNotNull(panel.getGame());
        assertEquals(9, panel.getCells().length);
        assertEquals('X', panel.getGame().cplayer.symbol);
    }

    @Test
    void testTicTacToePanelMakeMove() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        Game game = panel.getGame();
        TicTacToeCell cell = panel.getCells()[3];
        ActionEvent event = new ActionEvent(cell, ActionEvent.ACTION_PERFORMED, "");
        panel.actionPerformed(event);
        assertEquals('X', game.board[3]);
    }
}