package org.example;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ProgramTest {
    private Game game = new Game();

    @Test
    public void testInitState() {
        assertEquals(game.state, State.PLAYING);
        assertEquals(game.player1.symbol, 'X');
        assertEquals(game.player2.symbol, 'O');
        assertEquals( game.board[0], ' ');
        assertEquals(game.board.length, 9);
    }

    @Test
    public void testCheckStatePlaying() {
        assertEquals(game.checkState(game.board), State.PLAYING);
    }

    @Test
    public void testCheckStateXWIN() {
        char[] board = {
                'X', 'X', 'X',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(game.checkState(board), State.XWIN);
    }

    @Test
    public void testCheckStateOWIN() {
        char[] board = {
                'O', ' ', ' ',
                ' ', 'O', ' ',
                ' ', ' ', 'O'
        };
        game.symbol = 'O';
        assertEquals(game.checkState(board), State.OWIN);
    }

    @Test
    public void testGenerateMoves() {
        char[] board = {
                'O', 'X', ' ',
                ' ', 'X', 'X',
                'X', 'O', 'O'
        };
        ArrayList<Integer> move_list = new ArrayList<>();
        game.generateMoves(board, move_list);
        assertEquals(move_list.size(), 2);
    }

    @Test
    public void testEvaluatePosition() {
        char[] boardDraw = {
                'X', 'O', 'X',
                'O', 'O', 'X',
                'X', 'X', 'O'
        };
        char[] board = {
                'X', 'X', 'X',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        char[] boardNeg = {
                'X', 'O', 'X',
                'O', 'O', 'X',
                'X', 'O', 'O'
        };
        game.symbol = 'X';
        assertEquals(game.evaluatePosition(boardDraw, game.player1), 0);
        assertEquals(game.evaluatePosition(board, game.player1), Game.INF);
        game.symbol = 'O';
        assertEquals(game.evaluatePosition(boardNeg, game.player1), -Game.INF);
    }

    @Test
    public void testMiniMax() {
        char[] board = {
                'O', ' ', ' ',
                'X', 'X', ' ',
                ' ', ' ', 'O'
        };
        int move = game.MiniMax(board, game.player1);
        assertTrue(move <= 9 && move >=1);
    }

    @Test
    public void testTicTacToeCell() {
        TicTacToeCell cell = new TicTacToeCell(6, 3, 3);
        cell.setMarker("X");
        assertEquals(cell.getNum(), 6);
        assertEquals(cell.getCol(), 3);
        assertEquals(cell.getRow(), 3);
        assertEquals(cell.getMarker(), 'X');
    }

    @Test
    public void testPrintChar() {
        Utility utility = new Utility();
        char[] chars = { 'O', ' ', ' ', 'X', 'X', ' ', ' ', ' ', 'O' };
        Utility.print(chars);
    }

    @Test
    public void testPrintInt() {
        int[] ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        Utility.print(ints);
    }

    @Test
    public void testPrintArray() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(2);
        moves.add(3);
        Utility.print(moves);
    }

    @Test
    public void testCreateCell() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        assertNotNull(panel);
    }

    @Test
    public void testMain() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        assertNotNull(panel);
    }
}
