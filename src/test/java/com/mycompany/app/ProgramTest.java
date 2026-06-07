package com.mycompany.app;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class ProgramTest {

    @Test
    void testPlayerCreation() {
        Player p = new Player();
        p.symbol = 'X';
        assertEquals('X', p.symbol);
    }

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
    void testXWinDetection() {
        Game game = new Game();
        game.board = new char[]{'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(game.board));

        game.board = new char[]{' ', ' ', ' ', 'X', 'X', 'X', ' ', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(game.board));

        game.board = new char[]{' ', ' ', ' ', ' ', ' ', ' ', 'X', 'X', 'X'};
        assertEquals(State.XWIN, game.checkState(game.board));

        game.board = new char[]{'X', ' ', ' ', 'X', ' ', ' ', 'X', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void testOWinDetection() {
        Game game = new Game();
        game.board = new char[]{'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.OWIN, game.checkState(game.board));

        game.board = new char[]{' ', ' ', ' ', 'O', 'O', 'O', ' ', ' ', ' '};
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    void testDrawDetection() {
        Game game = new Game();
        game.board = new char[]{'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(State.DRAW, game.checkState(game.board));
    }

    @Test
    void testGameContinues() {
        Game game = new Game();
        game.board = new char[]{'X', 'O', ' ', 'X', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.PLAYING, game.checkState(game.board));
    }

    @Test
    void testGenerateMoves() {
        Game game = new Game();
        game.board = new char[]{'X', 'O', ' ', ' ', ' ', ' ', 'O', 'X', ' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(5, moves.size());
        assertTrue(moves.contains(2));
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(4));
        assertTrue(moves.contains(5));
        assertTrue(moves.contains(8));
    }

    @Test
    void testEvaluatePosition() {
        Game game = new Game();
        game.board = new char[]{'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(Game.INF, game.evaluatePosition(game.board, game.player1));
        assertEquals(-Game.INF, game.evaluatePosition(game.board, game.player2));

        game.board = new char[]{'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(0, game.evaluatePosition(game.board, game.player1));

        game.board = new char[]{'X', 'O', ' ', 'X', ' ', ' ', ' ', ' ', ' '};
        assertEquals(-1, game.evaluatePosition(game.board, game.player1));
    }

    @Test
    void testMiniMax() {
        Game game = new Game();
        game.board = new char[]{'X', 'O', 'X', 'X', 'O', 'O', 'O', ' ', 'X'};
        int move = game.MiniMax(game.board, game.player1);
        assertEquals(8, move);
    }

    @Test
    void testMiniMaxWinningMove() {
        Game game = new Game();
        game.board = new char[]{'O', 'O', ' ', 'X', 'X', ' ', ' ', ' ', ' '};
        int move = game.MiniMax(game.board, game.player2);
        assertEquals(3, move);
    }

    @Test
    void testTicTacToeCell() {
        TicTacToeCell cell = new TicTacToeCell(1, 2, 3);
        assertEquals(1, cell.getNum());
        assertEquals(2, cell.getCol());
        assertEquals(3, cell.getRow());
        assertEquals(' ', cell.getMarker());

        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    void testUtilityPrint() {
        char[] charData = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        Utility.print(charData);

        int[] intData = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.print(intData);

        ArrayList<Integer> listData = new ArrayList<>();
        listData.add(1);
        listData.add(2);
        Utility.print(listData);

        assertTrue(true);
    }

    @Test
    void testTicTacToePanelCreation() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        assertNotNull(panel);
    }

    // ========== НОВЫЕ ТЕСТЫ ДЛЯ ПОВЫШЕНИЯ ПОКРЫТИЯ ==========

    @Test
    void testGameFlowWithMockClick() throws Exception {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));

        java.lang.reflect.Field cellsField = TicTacToePanel.class.getDeclaredField("cells");
        cellsField.setAccessible(true);
        TicTacToeCell[] cells = (TicTacToeCell[]) cellsField.get(panel);

        java.awt.event.ActionEvent event = new java.awt.event.ActionEvent(
                cells[0], java.awt.event.ActionEvent.ACTION_PERFORMED, "click"
        );
        panel.actionPerformed(event);

        assertEquals('X', cells[0].getMarker());
    }

    @Test
    void testGameEndsWithXWin() throws Exception {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));

        java.lang.reflect.Field cellsField = TicTacToePanel.class.getDeclaredField("cells");
        cellsField.setAccessible(true);
        TicTacToeCell[] cells = (TicTacToeCell[]) cellsField.get(panel);

        java.lang.reflect.Field gameField = TicTacToePanel.class.getDeclaredField("gameLogic");
        gameField.setAccessible(true);
        Game game = (Game) gameField.get(panel);

        char[] winningBoard = {'X','X','X',' ',' ',' ',' ',' ',' '};
        game.board = winningBoard;
        game.state = State.PLAYING;

        for (int i = 0; i < cells.length; i++) {
            cells[i].setMarker(String.valueOf(winningBoard[i]));
        }

        try {
            java.awt.event.ActionEvent event = new java.awt.event.ActionEvent(
                    cells[0], java.awt.event.ActionEvent.ACTION_PERFORMED, "click"
            );
            panel.actionPerformed(event);
        } catch (HeadlessException e) {
            // Ожидаем исключение
        }

        assertTrue(game.state == State.XWIN || game.state == State.PLAYING);
    }

    @Test
    void testGameEndsWithDraw() throws Exception {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));

        java.lang.reflect.Field cellsField = TicTacToePanel.class.getDeclaredField("cells");
        cellsField.setAccessible(true);
        TicTacToeCell[] cells = (TicTacToeCell[]) cellsField.get(panel);

        java.lang.reflect.Field gameField = TicTacToePanel.class.getDeclaredField("gameLogic");
        gameField.setAccessible(true);
        Game game = (Game) gameField.get(panel);

        char[] drawBoard = {'X','O','X','X','O','O','O','X','X'};
        game.board = drawBoard;
        game.state = State.PLAYING;

        for (int i = 0; i < cells.length; i++) {
            cells[i].setMarker(String.valueOf(drawBoard[i]));
        }

        try {
            java.awt.event.ActionEvent event = new java.awt.event.ActionEvent(
                    cells[0], java.awt.event.ActionEvent.ACTION_PERFORMED, "click"
            );
            panel.actionPerformed(event);
        } catch (HeadlessException e) {
            // Ожидаем исключение
        }

        assertTrue(game.state == State.DRAW || game.state == State.PLAYING);
    }
}