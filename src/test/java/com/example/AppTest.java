package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testInitialBoard() {
        char[] expectedBoard = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        assertArrayEquals(expectedBoard, game.board);
    }

    @Test
    public void testCheckStatePlaying() {
        assertEquals(State.PLAYING, game.checkState(game.board));
    }

    @Test
    public void testCheckStateXWin() {
        char[] xWinBoard = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(xWinBoard));
    }

    @Test
    public void testCheckStateOWin() {
        char[] oWinBoard = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(oWinBoard));
    }

    @Test
    public void testCheckStateDraw() {
        char[] drawBoard = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'};
        assertEquals(State.DRAW, game.checkState(drawBoard));
    }

    @Test
    public void testGenerateMoves() {
        ArrayList<Integer> expectedMoves = new ArrayList<>();
        expectedMoves.add(0);
        expectedMoves.add(1);
        expectedMoves.add(2);
        expectedMoves.add(3);
        expectedMoves.add(4);
        expectedMoves.add(5);
        expectedMoves.add(6);
        expectedMoves.add(7);
        expectedMoves.add(8);

        ArrayList<Integer> actualMoves = new ArrayList<>();
        game.generateMoves(game.board, actualMoves);
        assertEquals(expectedMoves, actualMoves);
    }

    @Test
    public void testEvaluatePositionDraw() {
        char[] drawBoard = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'};
        assertEquals(0, game.evaluatePosition(drawBoard, game.player1));
    }

    @Test
    public void testMiniMax() {
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        int move = game.MiniMax(board, game.player2);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    public void testMinMove() {
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        int minMoveValue = game.MinMove(board, game.player2);
        assertNotEquals(-1, minMoveValue);
    }

    @Test
    public void testMaxMove() {
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        int maxMoveValue = game.MaxMove(board, game.player2);
        assertNotEquals(-1, maxMoveValue);
    }
}
