package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.awt.GridLayout;

public class AppTest {
    @Test
    void testApp() {
        assertTrue(true);
    }

    @Test
    void testGameInitialization() {
        Game game = new Game();
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals('X', game.cplayer.symbol);
        for (int i = 0; i < 9; i++) {
            assertEquals(' ', game.board[i]);
        }
    }

    @Test
    void testCheckStateXWinRow() {
        Game game = new Game();
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = 'X';
        game.cplayer = game.player1;
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateOWinRow() {
        Game game = new Game();
        game.board[3] = 'O';
        game.board[4] = 'O';
        game.board[5] = 'O';
        game.cplayer = game.player2;
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateDraw() {
        Game game = new Game();
        game.board[0] = 'X';
        game.board[1] = 'O';
        game.board[2] = 'X';
        game.board[3] = 'O';
        game.board[4] = 'X';
        game.board[5] = 'O';
        game.board[6] = 'O';
        game.board[7] = 'X';
        game.board[8] = 'O';
        game.cplayer = game.player1;
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(game.board));
    }

    @Test
    void testCheckStatePlaying() {
        Game game = new Game();
        game.board[0] = 'X';
        game.cplayer = game.player1;
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(game.board));
    }

    @Test
    void testGenerateMoves() {
        Game game = new Game();
        ArrayList<Integer> moveList = new ArrayList<>();
        game.generateMoves(game.board, moveList);
        assertEquals(9, moveList.size());
        for (int i = 0; i < 9; i++) {
            assertEquals(i, moveList.get(i).intValue());
        }

        game.board[0] = 'X';
        moveList.clear();
        game.generateMoves(game.board, moveList);
        assertEquals(8, moveList.size());
    }

    @Test
    void testEvaluatePositionXWin() {
        Game game = new Game();
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = 'X';
        game.cplayer = game.player1;
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(game.board, game.player1));
        assertEquals(-Game.INF, game.evaluatePosition(game.board, game.player2));
    }

    @Test
    void testEvaluatePositionOWin() {
        Game game = new Game();
        game.board[0] = 'O';
        game.board[1] = 'O';
        game.board[2] = 'O';
        game.cplayer = game.player2;
        game.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(game.board, game.player2));
        assertEquals(-Game.INF, game.evaluatePosition(game.board, game.player1));
    }

    @Test
    void testEvaluatePositionDraw() {
        Game game = new Game();
        game.board[0] = 'X';
        game.board[1] = 'O';
        game.board[2] = 'X';
        game.board[3] = 'O';
        game.board[4] = 'X';
        game.board[5] = 'O';
        game.board[6] = 'O';
        game.board[7] = 'X';
        game.board[8] = 'O';
        game.cplayer = game.player1;
        game.symbol = 'X';
        assertEquals(0, game.evaluatePosition(game.board, game.player1));
    }

    @Test
    void testTicTacToeCell() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(' ', cell.getMarker());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertEquals(0, cell.getNum());

        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    void testUtilityPrintBoard() {
        char[] board = new char[9];
        for (int i = 0; i < 9; i++) {
            board[i] = (i % 2 == 0) ? 'X' : 'O';
        }
        Utility.print(board);
    }

    @Test
    void testUtilityPrintIntArray() {
        int[] board = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.print(board);
    }

    @Test
    void testUtilityPrintArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(2);
        moves.add(3);
        Utility.print(moves);
    }

    @Test
    void testMiniMax() {
        Game game = new Game();
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.cplayer = game.player1;
        int move = game.MiniMax(game.board, game.player1);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    void testMinMoveWithDraw() {
        Game game = new Game();
        game.board[0] = 'X';
        game.board[1] = 'O';
        game.board[2] = 'X';
        game.board[3] = 'O';
        game.board[4] = 'X';
        game.board[5] = 'O';
        game.board[6] = 'O';
        game.board[7] = 'X';
        game.board[8] = 'O';
        game.cplayer = game.player1;
        game.symbol = 'X';
        int val = game.MinMove(game.board, game.player1);
        assertTrue(val >= -Game.INF && val <= Game.INF);
    }

    @Test
    void testMaxMoveWithWin() {
        Game game = new Game();
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = ' ';
        game.cplayer = game.player1;
        game.symbol = 'X';
        int val = game.MaxMove(game.board, game.player1);
        assertEquals(Game.INF, val);
    }

    @Test
	void testProgramMain() {
		try {
			System.setProperty("java.awt.headless", "true");
			Program.main(new String[]{});
		} catch (java.io.IOException e) {
			fail("Unexpected IOException during Program.main execution: " + e.getMessage());
		}
	}

    @Test
    void testTicTacToePanelActionPerformed() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        Game game = panel.getGame();
        game.board[0] = ' ';
        TicTacToeCell cell = panel.getCells()[0];
        cell.doClick();
        assertEquals('X', game.board[0]);
        assertEquals(State.PLAYING, game.state);
    }
}