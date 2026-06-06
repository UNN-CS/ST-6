package com.mycompany.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import com.mycompany.app.Game;
import com.mycompany.app.State;
import com.mycompany.app.Player;
import com.mycompany.app.Utility;
import com.mycompany.app.TicTacToePanel;
import com.mycompany.app.TicTacToeCell;

public class AppTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testPlayer1Symbol() {
        assertEquals('X', game.player1.symbol);
    }

    @Test
    public void testPlayer2Symbol() {
        assertEquals('O', game.player2.symbol);
    }

    @Test
    public void testPlayerDefaultWin() {
        assertFalse(game.player1.win);
        assertFalse(game.player2.win);
    }

    @Test
    public void testInitialState() {
        assertEquals(State.PLAYING, game.state);
    }

    @Test
    public void testInitialBoardEmpty() {
        for (int i = 0; i < 9; i++)
            assertEquals(' ', game.board[i]);
    }

    @Test
    public void testBoardSize() {
        assertEquals(9, game.board.length);
    }

    @Test
    public void testCheckStateXWinRow1() {
        game.symbol = 'X';
        char[] board = {'X','X','X',' ',' ',' ',' ',' ',' '};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateXWinRow2() {
        game.symbol = 'X';
        char[] board = {' ',' ',' ','X','X','X',' ',' ',' '};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateXWinRow3() {
        game.symbol = 'X';
        char[] board = {' ',' ',' ',' ',' ',' ','X','X','X'};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateXWinCol1() {
        game.symbol = 'X';
        char[] board = {'X',' ',' ','X',' ',' ','X',' ',' '};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateXWinCol2() {
        game.symbol = 'X';
        char[] board = {' ','X',' ',' ','X',' ',' ','X',' '};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateXWinCol3() {
        game.symbol = 'X';
        char[] board = {' ',' ','X',' ',' ','X',' ',' ','X'};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateXWinDiag1() {
        game.symbol = 'X';
        char[] board = {'X',' ',' ',' ','X',' ',' ',' ','X'};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateXWinDiag2() {
        game.symbol = 'X';
        char[] board = {' ',' ','X',' ','X',' ','X',' ',' '};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateOWin() {
        game.symbol = 'O';
        char[] board = {'O','O','O',' ',' ',' ',' ',' ',' '};
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateDraw() {
        game.symbol = 'X';
        char[] board = {'X','O','X','X','O','O','O','X','X'};
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    public void testCheckStatePlaying() {
        game.symbol = 'X';
        char[] board = {'X','O','X',' ','O','O','O','X','X'};
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    public void testGenerateMovesEmptyBoard() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size());
    }

    @Test
    public void testGenerateMovesPartialBoard() {
        game.board[0] = 'X';
        game.board[4] = 'O';
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(7, moves.size());
        assertFalse(moves.contains(0));
        assertFalse(moves.contains(4));
    }

    @Test
    public void testGenerateMovesFullBoard() {
        char[] board = {'X','O','X','X','O','O','O','X','X'};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(0, moves.size());
    }

    @Test
    public void testEvaluatePositionXWinForX() {
        game.symbol = 'X';
        char[] board = {'X','X','X',' ',' ',' ',' ',' ',' '};
        int val = game.evaluatePosition(board, game.player1);
        assertEquals(Game.INF, val);
    }

    @Test
    public void testEvaluatePositionXWinForO() {
        game.symbol = 'X';
        char[] board = {'X','X','X',' ',' ',' ',' ',' ',' '};
        int val = game.evaluatePosition(board, game.player2);
        assertEquals(-Game.INF, val);
    }

    @Test
    public void testEvaluatePositionOWinForO() {
        game.symbol = 'O';
        char[] board = {'O','O','O',' ',' ',' ',' ',' ',' '};
        int val = game.evaluatePosition(board, game.player2);
        assertEquals(Game.INF, val);
    }

    @Test
    public void testEvaluatePositionOWinForX() {
        game.symbol = 'O';
        char[] board = {'O','O','O',' ',' ',' ',' ',' ',' '};
        int val = game.evaluatePosition(board, game.player1);
        assertEquals(-Game.INF, val);
    }

    @Test
    public void testEvaluatePositionDraw() {
        game.symbol = 'X';
        char[] board = {'X','O','X','X','O','O','O','X','X'};
        int val = game.evaluatePosition(board, game.player1);
        assertEquals(0, val);
    }

    @Test
    public void testEvaluatePositionPlaying() {
        game.symbol = 'X';
        char[] board = {'X',' ',' ',' ',' ',' ',' ',' ',' '};
        int val = game.evaluatePosition(board, game.player1);
        assertEquals(-1, val);
    }

    @Test
    public void testMiniMaxReturnsValidMove() {
        int move = game.MiniMax(game.board, game.player2);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    public void testMiniMaxOnNearlyFullBoard() {
        game.board = new char[]{'X','X',' ','O','O',' ','X',' ','O'};
        int move = game.MiniMax(game.board, game.player2);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    public void testMinMoveReturnsValue() {
        game.board[0] = 'X';
        int val = game.MinMove(game.board, game.player1);
        assertTrue(val >= -Game.INF && val <= Game.INF);
    }

    @Test
    public void testMaxMoveReturnsValue() {
        game.board[0] = 'O';
        int val = game.MaxMove(game.board, game.player2);
        assertTrue(val >= -Game.INF && val <= Game.INF);
    }

    @Test
    public void testMinMoveOnTerminalXWin() {
        game.symbol = 'X';
        game.board = new char[]{'X','X','X','O','O',' ',' ',' ',' '};
        int val = game.MinMove(game.board, game.player1);
        assertEquals(Game.INF, val);
    }

    @Test
    public void testMaxMoveOnTerminalOWin() {
        game.symbol = 'O';
        game.board = new char[]{'O','O','O','X','X',' ',' ',' ',' '};
        int val = game.MaxMove(game.board, game.player2);
        assertEquals(Game.INF, val);
    }

    @Test
    public void testCellInitialMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(' ', cell.getMarker());
    }

    @Test
    public void testCellSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
    }

    @Test
    public void testCellGetNum() {
        TicTacToeCell cell = new TicTacToeCell(5, 2, 1);
        assertEquals(5, cell.getNum());
    }

    @Test
    public void testCellGetRow() {
        TicTacToeCell cell = new TicTacToeCell(0, 1, 2);
        assertEquals(2, cell.getRow());
    }

    @Test
    public void testCellGetCol() {
        TicTacToeCell cell = new TicTacToeCell(0, 2, 1);
        assertEquals(2, cell.getCol());
    }

    @Test
    public void testCellDisabledAfterSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("O");
        assertFalse(cell.isEnabled());
    }

    @Test
    public void testUtilityPrintCharBoard() {
        char[] board = {'X','O','X','O','X','O','X','O','X'};
        assertDoesNotThrow(() -> Utility.print(board));
    }

    @Test
    public void testUtilityPrintIntBoard() {
        int[] board = {1,2,3,4,5,6,7,8,9};
        assertDoesNotThrow(() -> Utility.print(board));
    }

    @Test
    public void testUtilityPrintMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1); moves.add(3); moves.add(5);
        assertDoesNotThrow(() -> Utility.print(moves));
    }

    @Test
    public void testINFValue() {
        assertEquals(100, Game.INF);
    }
}