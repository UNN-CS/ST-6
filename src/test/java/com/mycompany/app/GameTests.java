package com.mycompany.app;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GameTests {

    private char[] boardFromString(String state) {
        char[] board = new char[9];
        for (int i = 0; i < 9; i++) {
            board[i] = state.charAt(i);
        }
        return board;
    }

    @Test
    public void initializationShouldSetCorrectState() {
        Game g = new Game();
        assertEquals(State.PLAYING, g.state);
        for (int i = 0; i < 9; i++) {
            assertEquals(' ', g.board[i]);
        }
        assertEquals('X', g.player1.symbol);
        assertEquals('O', g.player2.symbol);
    }

    @Test
    public void checkStateDetectsOWinRow() {
        Game g = new Game();
        g.board = boardFromString("      OOO");
        g.symbol = 'O';
        assertEquals(State.OWIN, g.checkState(g.board));
    }

    @Test
    public void checkStateDetectsXWinColumn() {
        Game g = new Game();
        g.board = boardFromString(" X  X  X ");
        g.symbol = 'X';
        assertEquals(State.XWIN, g.checkState(g.board));
    }

    @Test
    public void checkStateDrawWhenFull() {
        Game g = new Game();
        g.board = boardFromString("XOXOXOOXX");
        g.symbol = 'X';
        assertEquals(State.DRAW, g.checkState(g.board));
    }

    @Test
    public void checkStatePlayingWhenEmptyCellsExist() {
        Game g = new Game();
        g.board = boardFromString("XO  O    ");
        g.symbol = 'X';
        assertEquals(State.PLAYING, g.checkState(g.board));
    }

    @Test
    public void generateMovesOnPartialBoard() {
        Game g = new Game();
        g.board = boardFromString("X O X    ");
        ArrayList<Integer> moves = new ArrayList<>();
        g.generateMoves(g.board, moves);
        assertEquals(6, moves.size());
        assertFalse(moves.contains(0));
        assertFalse(moves.contains(2));
        assertFalse(moves.contains(4));
        assertTrue(moves.contains(1));
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(5));
        assertTrue(moves.contains(6));
        assertTrue(moves.contains(7));
        assertTrue(moves.contains(8));
    }

    @Test
    public void evaluatePositionOWinForOPlayer() {
        Game g = new Game();
        g.board = boardFromString("O O O    ");
        g.symbol = 'O';
        g.player2.symbol = 'O';
        assertEquals(+Game.INF, g.evaluatePosition(g.board, g.player2));
    }

    @Test
    public void evaluatePositionOWinForXPlayer() {
        Game g = new Game();
        g.board = boardFromString("OOO      ");
        g.symbol = 'O';
        g.player1.symbol = 'X';
        assertEquals(-Game.INF, g.evaluatePosition(g.board, g.player1));
    }

    @Test
    public void evaluatePositionDrawReturnsZero() {
        Game g = new Game();
        g.board = boardFromString("XOXOXOOXX");
        g.symbol = 'X';
        assertEquals(0, g.evaluatePosition(g.board, g.player1));
    }

    @Test
    public void evaluatePositionNonTerminalReturnsMinusOne() {
        Game g = new Game();
        g.board = boardFromString("XO       ");
        g.symbol = 'X';
        assertEquals(-1, g.evaluatePosition(g.board, g.player1));
    }

    @Test
    public void minimaxOnNonTerminalReturnsValidMove() {
        Game g = new Game();
        g.board = boardFromString("XOXOXO X ");
        g.player1.symbol = 'X';
        int move = g.MiniMax(g.board, g.player1);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    public void minimaxBlocksOWinThreat() {
        Game g = new Game();
        g.board = boardFromString(" O O     ");
        g.player1.symbol = 'X';
        int move = g.MiniMax(g.board, g.player1);
        assertEquals(6, move);
    }

    @Test
    public void minimaxPicksLastEmptyCell() {
        Game g = new Game();
        g.board = boardFromString("XOXOXOOX ");
        g.player1.symbol = 'X';
        int move = g.MiniMax(g.board, g.player1);
        assertEquals(9, move);
    }

    @Test
    public void minMoveOnTerminalPosition() {
        Game g = new Game();
        g.board = boardFromString("OOO XX   ");
        g.symbol = 'O';
        int val = g.MinMove(g.board, g.player1);
        assertEquals(-Game.INF, val);
    }

    @Test
    public void maxMoveOnTerminalPosition() {
        Game g = new Game();
        g.board = boardFromString("OOO XX   ");
        g.symbol = 'O';
        int val = g.MaxMove(g.board, g.player2);
        assertEquals(+Game.INF, val);
    }

    @Test
    public void utilityPrintCharArrayNoException() {
        char[] board = boardFromString("XO X O X ");
        Utility.print(board);
    }

    @Test
    public void utilityPrintIntArrayNoException() {
        int[] moves = {0,1,2,3,4,5,6,7,8};
        Utility.print(moves);
    }

    @Test
    public void generateMovesOnEmptyBoard() {
        Game g = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        g.generateMoves(g.board, moves);
        assertEquals(9, moves.size());
        for (int i = 0; i < 9; i++) {
            assertEquals(Integer.valueOf(i), moves.get(i));
        }
    }

    @Test
    public void minimaxAlwaysReturnsValidMove() {
        Game g = new Game();
        int move = g.MiniMax(g.board, g.player1);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test(timeout = 5000)
    public void minimaxOnEmptyCompletesQuickly() {
        Game g = new Game();
        g.MiniMax(g.board, g.player2);
    }

    @Test
    public void cellConstructorStoresValues() {
        TicTacToeCell cell = new TicTacToeCell(5, 2, 1);
        assertEquals(5, cell.getNum());
        assertEquals(2, cell.getCol());
        assertEquals(1, cell.getRow());
        assertEquals(' ', cell.getMarker());
    }

    @Test
    public void setMarkerDisablesButtonAndUpdatesText() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);
        assertTrue(cell.isEnabled());
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    public void utilityPrintArrayListNoException() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(0, 4, 8));
        Utility.print(list);
    }
}
