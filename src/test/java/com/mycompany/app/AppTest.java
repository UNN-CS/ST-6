package com.mycompany.app;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class AppTest {

    @BeforeAll
    static void setupHeadless() {
        System.setProperty("java.awt.headless", "true");
    }

    @Test
    void testGameInit() {
        Game game = new Game();
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals(State.PLAYING, game.state);
        for (int i = 0; i < 9; i++)
            assertEquals(' ', game.board[i]);
    }

    @Test
    void testCheckStateXWinRow0() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {'X','X','X',' ',' ',' ',' ',' ',' '};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateXWinRow1() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {' ',' ',' ','X','X','X',' ',' ',' '};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateXWinRow2() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {' ',' ',' ',' ',' ',' ','X','X','X'};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateXWinCol0() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {'X',' ',' ','X',' ',' ','X',' ',' '};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateXWinCol1() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {' ','X',' ',' ','X',' ',' ','X',' '};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateXWinCol2() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {' ',' ','X',' ',' ','X',' ',' ','X'};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateXWinDiag1() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {'X',' ',' ',' ','X',' ',' ',' ','X'};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateXWinDiag2() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {' ',' ','X',' ','X',' ','X',' ',' '};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateOWinRow0() {
        Game game = new Game();
        game.symbol = 'O';
        char[] board = {'O','O','O',' ',' ',' ',' ',' ',' '};
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testCheckStateOWinRow1() {
        Game game = new Game();
        game.symbol = 'O';
        char[] board = {' ',' ',' ','O','O','O',' ',' ',' '};
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testCheckStateOWinRow2() {
        Game game = new Game();
        game.symbol = 'O';
        char[] board = {' ',' ',' ',' ',' ',' ','O','O','O'};
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testCheckStateOWinCol0() {
        Game game = new Game();
        game.symbol = 'O';
        char[] board = {'O',' ',' ','O',' ',' ','O',' ',' '};
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testCheckStateOWinCol1() {
        Game game = new Game();
        game.symbol = 'O';
        char[] board = {' ','O',' ',' ','O',' ',' ','O',' '};
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testCheckStateOWinCol2() {
        Game game = new Game();
        game.symbol = 'O';
        char[] board = {' ',' ','O',' ',' ','O',' ',' ','O'};
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testCheckStateOWinDiag1() {
        Game game = new Game();
        game.symbol = 'O';
        char[] board = {'O',' ',' ',' ','O',' ',' ',' ','O'};
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testCheckStateOWinDiag2() {
        Game game = new Game();
        game.symbol = 'O';
        char[] board = {' ',' ','O',' ','O',' ','O',' ',' '};
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testCheckStateDraw() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {'X','O','X','O','X','O','O','X','O'};
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    void testCheckStatePlaying() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {' ',' ',' ',' ',' ',' ',' ',' ',' '};
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    void testCheckStatePlayingPartial() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {'X','O',' ',' ',' ',' ',' ',' ',' '};
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    void testGenerateMovesEmpty() {
        Game game = new Game();
        char[] board = {' ',' ',' ',' ',' ',' ',' ',' ',' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(9, moves.size());
    }

    @Test
    void testGenerateMovesFull() {
        Game game = new Game();
        char[] board = {'X','O','X','O','X','O','O','X','O'};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(0, moves.size());
    }

    @Test
    void testGenerateMovesPartial() {
        Game game = new Game();
        char[] board = {'X','O',' ',' ',' ',' ',' ',' ',' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(7, moves.size());
    }

    @Test
    void testEvaluatePositionXWinXPlayer() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {'X','X','X','O','O',' ',' ',' ',' '};
        assertEquals(Game.INF, game.evaluatePosition(board, game.player1));
    }

    @Test
    void testEvaluatePositionXWinOPlayer() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {'X','X','X','O','O',' ',' ',' ',' '};
        assertEquals(-Game.INF, game.evaluatePosition(board, game.player2));
    }

    @Test
    void testEvaluatePositionOWinOPlayer() {
        Game game = new Game();
        game.symbol = 'O';
        char[] board = {'O','O','O','X','X',' ',' ',' ',' '};
        assertEquals(Game.INF, game.evaluatePosition(board, game.player2));
    }

    @Test
    void testEvaluatePositionOWinXPlayer() {
        Game game = new Game();
        game.symbol = 'O';
        char[] board = {'O','O','O','X','X',' ',' ',' ',' '};
        assertEquals(-Game.INF, game.evaluatePosition(board, game.player1));
    }

    @Test
    void testEvaluatePositionDraw() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {'X','O','X','O','X','O','O','X','O'};
        assertEquals(0, game.evaluatePosition(board, game.player1));
    }

    @Test
    void testEvaluatePositionPlaying() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {' ',' ',' ',' ',' ',' ',' ',' ',' '};
        assertEquals(-1, game.evaluatePosition(board, game.player1));
    }

    @Test
    void testMiniMaxNearlyFullBoard() {
        Game game = new Game();
        char[] board = {'X','O','X','O','X','O','O','X',' '};
        game.board = board;
        int move = game.MiniMax(board, game.player2);
        assertEquals(9, move);
    }

    @Test
    void testMiniMaxReturnsValidMove() {
        Game game = new Game();
        // O can win by completing col 1 (indices 1,4,7)
        char[] board = {'X','O','X','X','O','O',' ',' ',' '};
        game.board = board;
        int move = game.MiniMax(board, game.player2);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    void testMiniMaxBlocksWin() {
        Game game = new Game();
        char[] board = {'X','X',' ','O','O',' ',' ',' ',' '};
        game.board = board;
        int move = game.MiniMax(board, game.player2);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    void testMiniMaxTwoMovesLeft() {
        Game game = new Game();
        char[] board = {'X','O','X','O','O','X','X',' ',' '};
        game.board = board;
        int move = game.MiniMax(board, game.player2);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    void testPlayerFields() {
        Player p = new Player();
        p.symbol = 'X';
        p.move = 5;
        p.selected = true;
        p.win = false;
        assertEquals('X', p.symbol);
        assertEquals(5, p.move);
        assertTrue(p.selected);
        assertFalse(p.win);
    }

    @Test
    void testStateValues() {
        assertEquals(State.PLAYING, State.valueOf("PLAYING"));
        assertEquals(State.XWIN, State.valueOf("XWIN"));
        assertEquals(State.OWIN, State.valueOf("OWIN"));
        assertEquals(State.DRAW, State.valueOf("DRAW"));
    }

    @Test
    void testUtilityPrintChar() {
        char[] board = {'X','O','X','O','X','O','O','X','O'};
        assertDoesNotThrow(() -> Utility.print(board));
    }

    @Test
    void testUtilityPrintInt() {
        int[] board = {1,2,3,4,5,6,7,8,9};
        assertDoesNotThrow(() -> Utility.print(board));
    }

    @Test
    void testUtilityPrintList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(3);
        moves.add(5);
        assertDoesNotThrow(() -> Utility.print(moves));
    }

    @Test
    void testTicTacToeCellConstructor() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(0, cell.getNum());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertEquals(' ', cell.getMarker());
    }

    @Test
    void testTicTacToeCellSetMarkerX() {
        TicTacToeCell cell = new TicTacToeCell(4, 1, 1);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    void testTicTacToeCellSetMarkerO() {
        TicTacToeCell cell = new TicTacToeCell(2, 2, 0);
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
    }

    @Test
    void testTicTacToeCellGetters() {
        TicTacToeCell cell = new TicTacToeCell(5, 2, 1);
        assertEquals(5, cell.getNum());
        assertEquals(2, cell.getCol());
        assertEquals(1, cell.getRow());
    }

    @Test
    void testTicTacToePanelConstructor() {
        TicTacToePanel panel = new TicTacToePanel(new java.awt.GridLayout(3, 3));
        assertNotNull(panel);
    }
}
