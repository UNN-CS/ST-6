package com.mycompany.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void testGameInitialization() {
        assertNotNull(game.player1);
        assertNotNull(game.player2);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals(State.PLAYING, game.state);
        assertEquals(9, game.board.length);
        for (char c : game.board) {
            assertEquals(' ', c);
        }
    }

    @Test
    void testPlayerFields() {
        Player p = new Player();
        p.symbol = 'X';
        p.move = 3;
        p.selected = true;
        p.win = true;
        assertEquals('X', p.symbol);
        assertEquals(3, p.move);
        assertTrue(p.selected);
        assertTrue(p.win);
    }

    @Test
    void testStateValues() {
        assertNotNull(State.PLAYING);
        assertNotNull(State.XWIN);
        assertNotNull(State.OWIN);
        assertNotNull(State.DRAW);
        assertEquals(4, State.values().length);
    }

    @Test
    void testGameINFConstant() {
        assertEquals(100, Game.INF);
    }

    @Test
    void testCheckStateXWinsRow0() {
        game.symbol = 'X';
        game.board[0] = game.board[1] = game.board[2] = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateXWinsRow1() {
        game.symbol = 'X';
        game.board[3] = game.board[4] = game.board[5] = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateXWinsRow2() {
        game.symbol = 'X';
        game.board[6] = game.board[7] = game.board[8] = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateXWinsCol0() {
        game.symbol = 'X';
        game.board[0] = game.board[3] = game.board[6] = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateXWinsCol1() {
        game.symbol = 'X';
        game.board[1] = game.board[4] = game.board[7] = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateXWinsCol2() {
        game.symbol = 'X';
        game.board[2] = game.board[5] = game.board[8] = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateXWinsDiagonal() {
        game.symbol = 'X';
        game.board[0] = game.board[4] = game.board[8] = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateXWinsAntiDiagonal() {
        game.symbol = 'X';
        game.board[2] = game.board[4] = game.board[6] = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateOWins() {
        game.symbol = 'O';
        game.board[0] = game.board[1] = game.board[2] = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateDraw() {
        game.symbol = 'X';
        game.board[0] = 'X'; game.board[1] = 'O'; game.board[2] = 'X';
        game.board[3] = 'O'; game.board[4] = 'O'; game.board[5] = 'X';
        game.board[6] = 'X'; game.board[7] = 'X'; game.board[8] = 'O';
        assertEquals(State.DRAW, game.checkState(game.board));
    }

    @Test
    void testCheckStatePlaying() {
        game.symbol = 'X';
        game.board[0] = 'X';
        game.board[4] = 'O';
        assertEquals(State.PLAYING, game.checkState(game.board));
    }

    @Test
    void testGenerateMovesEmptyBoard() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size());
    }

    @Test
    void testGenerateMovesFullBoard() {
        for (int i = 0; i < 9; i++) game.board[i] = (i % 2 == 0) ? 'X' : 'O';
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(0, moves.size());
    }

    @Test
    void testGenerateMovesPartialBoard() {
        game.board[0] = 'X'; game.board[4] = 'O'; game.board[8] = 'X';
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(6, moves.size());
        assertFalse(moves.contains(0));
        assertFalse(moves.contains(4));
        assertFalse(moves.contains(8));
    }

    @Test
    void testEvaluatePositionXWinForX() {
        game.symbol = 'X';
        game.board[0] = game.board[1] = game.board[2] = 'X';
        assertEquals(Game.INF, game.evaluatePosition(game.board, game.player1));
    }

    @Test
    void testEvaluatePositionXWinForO() {
        game.symbol = 'X';
        game.board[0] = game.board[1] = game.board[2] = 'X';
        assertEquals(-Game.INF, game.evaluatePosition(game.board, game.player2));
    }

    @Test
    void testEvaluatePositionOWinForO() {
        game.symbol = 'O';
        game.board[0] = game.board[1] = game.board[2] = 'O';
        assertEquals(Game.INF, game.evaluatePosition(game.board, game.player2));
    }

    @Test
    void testEvaluatePositionOWinForX() {
        game.symbol = 'O';
        game.board[0] = game.board[1] = game.board[2] = 'O';
        assertEquals(-Game.INF, game.evaluatePosition(game.board, game.player1));
    }

    @Test
    void testEvaluatePositionDraw() {
        game.symbol = 'X';
        game.board[0] = 'X'; game.board[1] = 'O'; game.board[2] = 'X';
        game.board[3] = 'O'; game.board[4] = 'O'; game.board[5] = 'X';
        game.board[6] = 'X'; game.board[7] = 'X'; game.board[8] = 'O';
        assertEquals(0, game.evaluatePosition(game.board, game.player1));
    }

    @Test
    void testEvaluatePositionPlaying() {
        assertEquals(-1, game.evaluatePosition(game.board, game.player1));
    }

    @Test
    void testMiniMaxOneMoveLeft() {
        game.board[0] = 'X'; game.board[1] = 'O'; game.board[2] = 'X';
        game.board[3] = 'O'; game.board[4] = 'O'; game.board[5] = 'X';
        game.board[6] = 'X'; game.board[7] = 'X';
        int move = game.MiniMax(game.board, game.player1);
        assertEquals(9, move);
    }

    @Test
    void testMiniMaxChoosesValidMove() {
        game.board[0] = 'X'; game.board[2] = 'O'; game.board[4] = 'X'; game.board[6] = 'O';
        int move = game.MiniMax(game.board, game.player2);
        assertTrue(move >= 1 && move <= 9);
        assertTrue(game.board[move - 1] == ' ');
    }

    @Test
    void testMiniMaxEqualValues() {
        game.board[4] = 'X';
        int move = game.MiniMax(game.board, game.player2);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    void testMinMoveTerminalXWin() {
        game.symbol = 'X';
        game.board[0] = game.board[1] = game.board[2] = 'X';
        int val = game.MinMove(game.board, game.player1);
        assertEquals(Game.INF, val);
    }

    @Test
    void testMinMoveNonTerminal() {
        game.board[0] = 'O'; game.board[1] = 'X'; game.board[2] = 'O';
        game.board[3] = 'X'; game.board[4] = 'O';
        game.board[6] = 'X';
        game.symbol = 'X';
        int val = game.MinMove(game.board, game.player1);
        assertTrue(val >= -Game.INF && val <= Game.INF);
    }

    @Test
    void testMaxMoveTerminalOWin() {
        game.symbol = 'O';
        game.board[0] = game.board[1] = game.board[2] = 'O';
        int val = game.MaxMove(game.board, game.player2);
        assertEquals(Game.INF, val);
    }

    @Test
    void testMaxMoveNonTerminal() {
        game.board[0] = 'O'; game.board[1] = 'X'; game.board[2] = 'O';
        game.board[3] = 'X'; game.board[4] = 'O';
        game.board[6] = 'X';
        game.symbol = 'X';
        int val = game.MaxMove(game.board, game.player1);
        assertTrue(val >= -Game.INF && val <= Game.INF);
    }

    @Test
    void testUtilityPrintCharArray() {
        char[] board = {'X', 'O', ' ', 'X', 'O', ' ', 'X', 'O', ' '};
        Utility.print(board);
    }

    @Test
    void testUtilityPrintIntArray() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.print(arr);
    }

    @Test
    void testUtilityPrintArrayList() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1); list.add(2); list.add(3);
        Utility.print(list);
    }

    @Test
    void testTicTacToeCellGetters() {
        TicTacToeCell cell = new TicTacToeCell(3, 1, 2);
        assertEquals(3, cell.getNum());
        assertEquals(2, cell.getRow());
        assertEquals(1, cell.getCol());
        assertEquals(' ', cell.getMarker());
    }

    @Test
    void testTicTacToeCellSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
    }

    @Test
    void testTicTacToeCellSetMarkerO() {
        TicTacToeCell cell = new TicTacToeCell(5, 2, 1);
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
    }

    @Test
    void testTicTacToePanel() {
        TicTacToePanel panel = new TicTacToePanel(new java.awt.GridLayout(3, 3));
        assertNotNull(panel);
    }
}
