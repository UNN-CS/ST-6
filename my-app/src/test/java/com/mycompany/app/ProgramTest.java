package com.mycompany.app;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ProgramTest {
    @Test
    public void testInitialStateIsPlaying() {
        Game game = new Game();
        assertEquals(State.PLAYING, game.state);
        for (char c : game.board)
            assertEquals(' ', c);
    }

    @Test
    public void testGenerateMoves() {
        Game game = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size());

        game.board[0] = 'X';
        moves.clear();
        game.generateMoves(game.board, moves);
        assertEquals(8, moves.size());
        assertFalse(moves.contains(0));
    }

    @Test
    public void testCheckStateWinX() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {
                'X', 'X', 'X',
                ' ', 'O', ' ',
                ' ', 'O', ' '
        };
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateWinO() {
        Game game = new Game();
        game.symbol = 'O';
        char[] board = {
                'X', 'X', 'O',
                'X', 'O', ' ',
                'O', ' ', ' '
        };
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateDraw() {
        Game game = new Game();
        game.symbol = 'O';
        char[] board = {
                'X', 'O', 'X',
                'X', 'O', 'O',
                'O', 'X', 'X'
        };
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    public void testEvaluateWinningPosition() {
        Game game = new Game();
        game.symbol = 'X';
        Player p = new Player();
        p.symbol = 'X';
        char[] board = {
                'X', 'X', 'X',
                'O', 'O', ' ',
                ' ', ' ', ' '
        };
        assertEquals(Game.INF, game.evaluatePosition(board, p));
    }

    @Test
    public void testEvaluateLosingPosition() {
        Game game = new Game();
        game.symbol = 'O';
        Player p = new Player();
        p.symbol = 'X';
        p.symbol = 'X';
        char[] board = {
                'O', 'O', 'O',
                'X', 'X', ' ',
                ' ', ' ', ' '
        };
        assertEquals(-Game.INF, game.evaluatePosition(board, p));
    }

    @Test
    public void testEvaluateDrawPosition() {
        Game game = new Game();
        game.symbol = 'X';
        Player p = new Player();
        p.symbol = 'X';
        char[] board = {
                'X', 'O', 'X',
                'X', 'O', 'O',
                'O', 'X', 'X'
        };
        assertEquals(0, game.evaluatePosition(board, p));
    }
    @Test
    public void testInitialMarkerIsSpace() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(' ', cell.getMarker());
    }

    @Test
    public void testSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(1, 1, 1);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
    }

    @Test
    public void testGetRowColNum() {
        TicTacToeCell cell = new TicTacToeCell(5, 2, 1);
        assertEquals(2, cell.getCol());
        assertEquals(1, cell.getRow());

        assertEquals(5, cell.getNum());
    }

    @Test
    public void testMiniMaxFindsWinningMove() {
        Game game = new Game();
        Player ai = new Player();
        ai.symbol = 'O';
        game.symbol = 'O';
        char[] board = {
                'O', 'O', ' ',
                'X', 'X', ' ',
                ' ', ' ', ' '
        };
        int move = game.MiniMax(board, ai);
        assertEquals(3, move);
    }

    @Test
    public void testMinMoveBlocksOpponent() {
        Game game = new Game();
        Player ai = new Player();
        ai.symbol = 'O';
        game.symbol = 'O';
        char[] board = {
                'X', 'X', ' ',
                'O', ' ', ' ',
                ' ', ' ', ' '
        };
        int move = game.MinMove(board, ai);
        assertTrue(move < Game.INF);
    }

    @Test
    public void testGenerateMovesFullBoard() {
        Game game = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        for (int i = 0; i < 9; i++) game.board[i] = 'X';
        game.generateMoves(game.board, moves);
        assertTrue(moves.isEmpty());
    }

    @Test
    public void testUtilityPrints() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(2);
        Utility.print(moves);
        int[] arr = {1,2,3,4,5,6,7,8,9};
        Utility.print(arr);
        char[] chArr = {'X','O','X','O','X','O','X','O','X'};
        Utility.print(chArr);
    }

    @Test
    public void testTicTacToeCellClick() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertFalse(cell.isSelected());
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
    }

    @Test
    public void testCheckStateEmptyBoard() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {
                ' ', ' ', ' ',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        assertEquals(State.PLAYING, game.checkState(board));
    }
}
