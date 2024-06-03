package com.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class AppTest {

    private static Game game;


    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testInitialGameState() {
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals(' ', game.board[0]);
    }

    @Test
    public void testCheckStatePlaying() {
        assertEquals(State.PLAYING, game.checkState(game.board));
    }

    @Test
    public void testCheckStateForXWin() {
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = 'X';
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    public void testCheckStateForOWin() {
        game.board[0] = 'O';
        game.board[1] = 'O';
        game.board[2] = 'O';
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    public void testCheckStateForDraw() {
        game.board[0] = 'X';
        game.board[1] = 'O';
        game.board[2] = 'X';
        game.board[3] = 'X';
        game.board[4] = 'O';
        game.board[5] = 'O';
        game.board[6] = 'O';
        game.board[7] = 'X';
        game.board[8] = 'X';
        assertEquals(State.DRAW, game.checkState(game.board));
    }

    @Test
    public void testGenerateMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size());
    }

    @Test
    public void testEvaluatePositionForXWin() {
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = 'X';
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(game.board, game.player1));
    }

    @Test
    public void testEvaluatePositionForOWin() {
        game.board[0] = 'O';
        game.board[1] = 'O';
        game.board[2] = 'O';
        game.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(game.board, game.player2));
    }

    @Test
    public void testEvaluatePositionForDraw() {
        game.board[0] = 'X';
        game.board[1] = 'O';
        game.board[2] = 'X';
        game.board[3] = 'X';
        game.board[4] = 'O';
        game.board[5] = 'O';
        game.board[6] = 'O';
        game.board[7] = 'X';
        game.board[8] = 'X';
        assertEquals(0, game.evaluatePosition(game.board, game.player1));
    }

    @Test
    public void testMinimax() {
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = ' ';
        game.board[3] = 'O';
        game.board[4] = 'O';
        game.board[5] = ' ';
        game.board[6] = ' ';
        game.board[7] = ' ';
        game.board[8] = ' ';
        int move = game.MiniMax(game.board, game.player2);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    public void testMinMove() {
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = ' ';
        game.board[3] = 'O';
        game.board[4] = 'O';
        game.board[5] = ' ';
        game.board[6] = ' ';
        game.board[7] = ' ';
        game.board[8] = ' ';
        int minMoveValue = game.MinMove(game.board, game.player1);
        assertTrue(minMoveValue <= 0);
    }

    @Test
    public void testMaxMove() {
        game.board[0] = 'X';
        game.board[1] = ' ';
        game.board[2] = 'O';
        game.board[3] = 'O';
        game.board[4] = 'X';
        game.board[5] = ' ';
        game.board[6] = ' ';
        game.board[7] = ' ';
        game.board[8] = ' ';
        int maxMoveValue = game.MaxMove(game.board, game.player2);
        assertTrue(maxMoveValue >= 0);
    }

    @Test
    public void testPlayerSymbol() {
        game.player1.symbol = 'X';
        assertEquals('X', game.player1.symbol);
    }

    @Test
    public void testPlayerMove() {
        game.player1.move = 1;
        assertEquals(1, game.player1.move);
    }

    @Test
    public void testPlayerSelected() {
        game.player1.selected = true;
        assertTrue(game.player1.selected);
    }

    @Test
    public void testPlayerWin() {
        game.player1.win = true;
        assertTrue(game.player1.win);
    }

    @Test
    public void testCellMarker() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 2);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    public void testCellRow() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 2);
        assertEquals(2, cell.getRow());
    }

    @Test
    public void testCellCol() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 2);
        assertEquals(0, cell.getCol());
    }

    @Test
    public void testCellNum() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 2);
        assertEquals(1, cell.getNum());
    }
}

