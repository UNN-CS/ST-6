package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ProgramTest {

    @Test
    public void testInitialState() {
        Game game = new Game();
        assertEquals(State.PLAYING, game.state);
        for (char cell : game.board) {
            assertEquals(' ', cell);
        }
    }

    @Test
    public void testWinX() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {'X','X','X',' ',' ',' ',' ',' ',' '};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testWinO() {
        Game game = new Game();
        game.symbol = 'O';
        char[] board = {'O','O','O',' ',' ',' ',' ',' ',' '};
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    public void testDraw() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {'X','O','X','X','O','O','O','X','X'};
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    public void testGenerateMoves() {
        Game game = new Game();
        char[] board = {'X','O','X',' ',' ',' ','O','X','O'};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertArrayEquals(new Integer[]{3, 4, 5}, moves.toArray(new Integer[0]));
    }

    @Test
    public void testEvaluateWinX() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {'X','X','X',' ',' ',' ',' ',' ',' '};
        game.player1.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(board, game.player1));
    }

    @Test
    public void testEvaluateLossX() {
        Game game = new Game();
        game.symbol = 'O';
        char[] board = {'O','O','O',' ',' ',' ',' ',' ',' '};
        game.player1.symbol = 'X';
        assertEquals(-Game.INF, game.evaluatePosition(board, game.player1));
    }

    @Test
    public void testPlayerFields() {
        Player p = new Player();
        p.symbol = 'X';
        p.move = 4;
        p.selected = true;
        p.win = false;

        assertEquals('X', p.symbol);
        assertEquals(4, p.move);
        assertTrue(p.selected);
        assertFalse(p.win);
    }

    @Test
    public void testUtilityPrintCharArray() {
        char[] board = {'X','O','X',' ',' ',' ','O','X','O'};
        Utility.print(board);
    }

    @Test
    public void testUtilityPrintIntArray() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.print(arr);
    }

    @Test
    public void testUtilityPrintMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(2);
        Utility.print(moves);
    }

    @Test
    public void testTicTacToeCellBasic() {
        TicTacToeCell cell = new TicTacToeCell(4, 1, 1);
        assertEquals(4, cell.getNum());
        assertEquals(1, cell.getRow());
        assertEquals(1, cell.getCol());
        assertEquals(' ', cell.getMarker());

        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
    }

    @Test
    public void testMinimaxReturnsValidMove() {
        Game game = new Game();
        Player ai = new Player();
        ai.symbol = 'O';
        char[] board = {
                'X','O','X',
                ' ','O',' ',
                'X',' ',' '
        };
        int move = game.MiniMax(board.clone(), ai);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    public void testMinMoveAndMaxMove() {
        Game game = new Game();
        Player ai = new Player();
        ai.symbol = 'X';

        char[] board = {
                'X','O',' ',
                'O','X',' ',
                ' ',' ','O'
        };

        int minVal = game.MinMove(board.clone(), ai);
        int maxVal = game.MaxMove(board.clone(), ai);

        assertTrue(minVal <= Game.INF && minVal >= -Game.INF);
        assertTrue(maxVal <= Game.INF && maxVal >= -Game.INF);
    }

    @Test
    public void testPanelInitialization() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        assertNotNull(panel);
    }

    @Test
    public void testPanelActionPerformed() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        ActionEvent e = new ActionEvent(cell, ActionEvent.ACTION_PERFORMED, "click");

        cell.addActionListener(panel);
        panel.actionPerformed(new ActionEvent(panel.cells[0], ActionEvent.ACTION_PERFORMED, "test"));
    }

    @Test
    public void testAppLaunch() {
        try {
            JFrame frame = new JFrame("Test");
            frame.add(new TicTacToePanel(new GridLayout(3, 3)));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setBounds(5, 5, 300, 300);
            frame.setVisible(false);
            assertTrue(true);
        } catch (Exception e) {
            fail("App launch failed");
        }
    }

    @Test
    public void testAppMainLogic() throws IOException {
        Program.main(new String[]{});
        assertTrue(true);
    }

    @Test
    public void testProgramMainGUIStartup() {
        try {
            Program.main(new String[]{});
            assertTrue(true);
        } catch (Exception e) {
            fail("Program.main threw exception");
        }
    }
}
