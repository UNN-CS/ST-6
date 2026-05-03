package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.awt.GridLayout;

public class AppTest {

    @Test
    public void testGameInitialization() {
        Game game = new Game();
        assertNotNull(game.board);
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
    }

    @Test
    public void testCheckStateAllConditions() {
        Game game = new Game();
        char[] xWin = {'X', 'X', 'X', 'O', 'O', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(xWin));

        char[] oWin = {'O', 'X', ' ', 'O', 'X', ' ', 'O', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(oWin));

        char[] draw = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(draw));
    }

    @Test
    public void testMiniMaxDecision() {
        Game game = new Game();
        char[] board = {' ', 'O', 'O', 'X', 'X', ' ', ' ', ' ', ' '};
        int move = game.MiniMax(board, game.player2);
        assertEquals(1, move);
    }


    @Test
    public void testCellProperties() {
        TicTacToeCell cell = new TicTacToeCell(5, 1, 2);
        assertEquals(5, cell.getNum());
        assertEquals(1, cell.getCol());
        assertEquals(2, cell.getRow());

        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    public void testPanelStructure() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3,3));
        assertEquals(9, panel.getComponentCount());
    }


    @Test
    public void testUtilityFull() {
        char[] boardChar = new char[9];
        int[] boardInt = new int[9];
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);

        assertDoesNotThrow(() -> {
            Utility.print(boardChar);
            Utility.print(boardInt);
            Utility.print(moves);
        });
    }


    @Test
    public void testProgramClass() {
        Program p = new Program();
        assertNotNull(p);
    }
}