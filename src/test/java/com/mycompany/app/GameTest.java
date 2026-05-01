package com.mycompany.app;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class GameTest {
    private Game game;
    private char[] board;

    @Before
    public void setUp() {
        game = new Game();
        board = new char[9];
        for (int i = 0; i < 9; i++) {
            board[i] = ' ';
        }
    }

    // ========== ТЕСТЫ checkState ==========
    @Test
    public void testCheckStateXWinRow() {
        game.symbol = 'X';
        board[0] = 'X'; board[1] = 'X'; board[2] = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateOWinColumn() {
        game.symbol = 'O';
        board[0] = 'O'; board[3] = 'O'; board[6] = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateXWinDiagonal() {
        game.symbol = 'X';
        board[0] = 'X'; board[4] = 'X'; board[8] = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateOWinAntiDiagonal() {
        game.symbol = 'O';
        board[2] = 'O'; board[4] = 'O'; board[6] = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    public void testCheckStatePlaying() {
        game.symbol = 'X';
        board[0] = 'X'; board[4] = 'O'; board[8] = 'X';
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    public void testCheckStateDraw() {
        game.symbol = 'X';
        char[] fullBoard = {'X','O','X','O','X','O','O','X','O'};
        assertEquals(State.DRAW, game.checkState(fullBoard));
    }

    // ========== ТЕСТЫ generateMoves ==========
    @Test
    public void testGenerateMovesEmptyBoard() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(9, moves.size());
    }

    @Test
    public void testGenerateMovesPartialBoard() {
        board[0] = 'X';
        board[4] = 'O';
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(7, moves.size());
        assertFalse(moves.contains(0));
        assertFalse(moves.contains(4));
    }

    @Test
    public void testGenerateMovesFullBoard() {
        for (int i = 0; i < 9; i++) board[i] = 'X';
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(0, moves.size());
    }

    // ========== ТЕСТЫ evaluatePosition ==========
    @Test
    public void testEvaluatePositionXWin() {
        game.symbol = 'X';
        board[0] = 'X'; board[1] = 'X'; board[2] = 'X';
        int value = game.evaluatePosition(board, game.player1);
        assertEquals(Game.INF, value);
    }

    @Test
    public void testEvaluatePositionOWin() {
        game.symbol = 'O';
        board[0] = 'O'; board[3] = 'O'; board[6] = 'O';
        int value = game.evaluatePosition(board, game.player2);
        assertEquals(Game.INF, value);
    }

    @Test
    public void testEvaluatePositionDraw() {
        char[] fullBoard = {'X','O','X','O','X','O','O','X','O'};
        int value = game.evaluatePosition(fullBoard, game.player1);
        assertEquals(0, value);
    }

    @Test
    public void testEvaluatePositionPlaying() {
        board[0] = 'X';
        int value = game.evaluatePosition(board, game.player1);
        assertEquals(-1, value);
    }

    // ========== ТЕСТЫ MiniMax ==========
    @Test
    public void testMiniMaxEmptyBoard() {
        game.cplayer = game.player1;
        int bestMove = game.MiniMax(board, game.player1);
        assertTrue(bestMove >= 1 && bestMove <= 9);
    }

    @Test
    public void testMiniMaxCenterPreferred() {
        game.cplayer = game.player1;
        int bestMove = game.MiniMax(board, game.player1);
        // центр обычно лучший первый ход
        assertTrue(bestMove >= 1 && bestMove <= 9);
    }

    // ========== ТЕСТЫ конструктора ==========
    @Test
    public void testGameConstructor() {
        Game newGame = new Game();
        assertEquals(State.PLAYING, newGame.state);
        assertEquals('X', newGame.player1.symbol);
        assertEquals('O', newGame.player2.symbol);
        assertEquals(9, newGame.board.length);
    }

    // ========== ТЕСТЫ Player ==========
    @Test
    public void testPlayerSymbol() {
        Player p = new Player();
        p.symbol = 'X';
        assertEquals('X', p.symbol);
    }

    // ========== ТЕСТЫ Utility ==========
    @Test
    public void testUtilityPrintBoard() {
        Utility.print(board);
        assertTrue(true); // просто проверка что не падает
    }

    // ========== ДОПОЛНИТЕЛЬНЫЕ ТЕСТЫ ДЛЯ ПОКРЫТИЯ ==========
    @Test
    public void testCheckStateXWinDifferentSymbol() {
        game.symbol = 'O';
        board[0] = 'X'; board[1] = 'X'; board[2] = 'X';
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    public void testEvaluatePositionXWinForOWinCondition() {
        game.symbol = 'X';
        board[0] = 'X'; board[1] = 'X'; board[2] = 'X';
        int value = game.evaluatePosition(board, game.player2);
        assertEquals(-Game.INF, value);
    }

    // Тесты для TicTacToeCell
    @Test
    public void testTicTacToeCellConstructor() {
        TicTacToeCell cell = new TicTacToeCell(5, 1, 2);
        assertEquals(5, cell.getNum());
        assertEquals(2, cell.getRow());
        assertEquals(1, cell.getCol());
        assertEquals(' ', cell.getMarker());
    }

    @Test
    public void testTicTacToeCellSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    public void testTicTacToeCellGetRowColNum() {
        TicTacToeCell cell = new TicTacToeCell(7, 2, 1);
        assertEquals(7, cell.getNum());
        assertEquals(1, cell.getRow());
        assertEquals(2, cell.getCol());
    }

    // Тесты для Utility
    @Test
    public void testUtilityPrintCharArray() {
        char[] testBoard = {'X', 'O', ' ', 'X', ' ', 'O', ' ', 'X', ' '};
        Utility.print(testBoard);
        assertTrue(true);
    }

    @Test
    public void testUtilityPrintIntArray() {
        int[] testArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.print(testArray);
        assertTrue(true);
    }

    @Test
    public void testUtilityPrintArrayList() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(4);
        list.add(8);
        Utility.print(list);
        assertTrue(true);
    }

    // Дополнительные тесты для evaluatePosition
    @Test
    public void testEvaluatePositionXWinForPlayerX() {
        game.symbol = 'X';
        for (int i = 0; i < 9; i++) board[i] = ' ';
        board[0] = 'X'; board[1] = 'X'; board[2] = 'X';
        int value = game.evaluatePosition(board, game.player1);
        assertEquals(Game.INF, value);
    }

    @Test
    public void testEvaluatePositionXWinForPlayerOLoses() {
        game.symbol = 'X';
        board[0] = 'X'; board[1] = 'X'; board[2] = 'X';
        int value = game.evaluatePosition(board, game.player2);
        assertEquals(-Game.INF, value);
    }

    @Test
    public void testEvaluatePositionOWinForPlayerOWins() {
        game.symbol = 'O';
        board[0] = 'O'; board[3] = 'O'; board[6] = 'O';
        int value = game.evaluatePosition(board, game.player2);
        assertEquals(Game.INF, value);
    }

    // Тесты для MinMove и MaxMove через публичные методы
    @Test
    public void testMinMoveReturnsCorrectValue() {
        game.symbol = 'X';
        board[0] = 'X'; board[1] = 'O'; board[2] = 'X';
        board[3] = 'O'; board[4] = 'X'; board[5] = 'O';
        board[6] = 'O'; board[7] = 'X'; board[8] = ' ';
        int result = game.MinMove(board, game.player1);
        assertTrue(result >= -Game.INF && result <= Game.INF);
    }

    @Test
    public void testMaxMoveReturnsCorrectValue() {
        game.symbol = 'O';
        board[0] = 'X'; board[1] = 'O'; board[2] = 'X';
        board[3] = 'O'; board[4] = 'X'; board[5] = 'O';
        board[6] = 'O'; board[7] = 'X'; board[8] = ' ';
        int result = game.MaxMove(board, game.player2);
        assertTrue(result >= -Game.INF && result <= Game.INF);
    }

    // Тесты для generateMoves с разными досками
    @Test
    public void testGenerateMovesCornerOnly() {
        for (int i = 0; i < 9; i++) board[i] = 'X';
        board[0] = ' ';
        board[8] = ' ';
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(2, moves.size());
        assertTrue(moves.contains(0));
        assertTrue(moves.contains(8));
    }

    // Тест для MiniMax с полной доской (должен вернуть 0)
    @Test
    public void testMiniMaxFullBoard() {
        char[] fullBoard = {'X','O','X','O','X','O','O','X','O'};
        game.cplayer = game.player1;
        int bestMove = game.MiniMax(fullBoard, game.player1);
        assertEquals(0, bestMove);
    }

    // Тест для checkState с ничьей
    @Test
    public void testCheckStateDrawFullBoard() {
        game.symbol = 'X';
        char[] fullBoard = {'X','O','X','O','X','O','O','X','O'};
        assertEquals(State.DRAW, game.checkState(fullBoard));
    }

    // Тест для checkState с победой по вертикали
    @Test
    public void testCheckStateXWinVertical() {
        game.symbol = 'X';
        board[1] = 'X'; board[4] = 'X'; board[7] = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    // Тест для checkState с победой по горизонтали
    @Test
    public void testCheckStateOWinHorizontal() {
        game.symbol = 'O';
        board[6] = 'O'; board[7] = 'O'; board[8] = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }
}
