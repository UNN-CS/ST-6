package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.util.ArrayList;

public class ProgramTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testInitialState() {
        assertEquals(State.PLAYING, game.state);
        assertArrayEquals(new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, game.board);
    }

    @Test
    public void testCheckState() {
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));

        board = new char[]{'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));

        board = new char[]{'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(State.DRAW, game.checkState(board));

        board = new char[]{'X', 'O', ' ', 'X', 'O', ' ', ' ', ' ', ' '};
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    public void testEvaluatePosition() {
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(board, game.player1));

        board = new char[]{'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(board, game.player2));

        board = new char[]{'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(0, game.evaluatePosition(board, game.player1));
    }

    @Test
    public void testMiniMax() {
        char[] board = {'X', 'O', 'X', 'X', 'O', ' ', 'O', 'X', ' '};
        int move = game.MiniMax(board, game.player2);
        assertTrue(move == 6 || move == 9);
    }

    @Test
    public void testGenerateMoves() {
        ArrayList<Integer> moveList = new ArrayList<>();
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', 'O', 'X', ' '};
        game.generateMoves(board, moveList);
        assertEquals(4, moveList.size());
        assertTrue(moveList.contains(3));
        assertTrue(moveList.contains(4));
        assertTrue(moveList.contains(5));
        assertTrue(moveList.contains(8));
    }

    @Test
    public void testPlayerProperties() {
        Player player = new Player();
        player.symbol = 'X';
        player.move = 1;
        player.selected = true;
        player.win = false;

        assertEquals('X', player.symbol);
        assertEquals(1, player.move);
        assertTrue(player.selected);
        assertFalse(player.win);
    }

    @Test
    public void testCellProperties() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);
        assertEquals(1, cell.getNum());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertEquals(' ', cell.getMarker());

        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertEquals("X", cell.getText());
        assertFalse(cell.isEnabled());
    }

    @Test
    public void testPrintBoard() {
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', 'O', 'X', ' '};
        Utility.print(board);
    }

    @Test
    public void testPrintIntBoard() {
        int[] board = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.print(board);
    }

    @Test
    public void testPrintMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(2);
        moves.add(3);
        Utility.print(moves);
    }
    @Test
    void testCellClick() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        TicTacToeCell cell = panel.cells[0];
        cell.doClick();
        assertEquals('X', cell.getMarker());
    }
}