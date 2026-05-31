package com.mycompany.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AppTest {

    @BeforeAll
    public static void setupHeadless() {
        // Включаем headless-режим, чтобы GUI-компоненты не требовали монитора
        System.setProperty("java.awt.headless", "true");
    }

    @Test
    public void testPlayer() {
        Player p = new Player();
        p.symbol = 'X';
        p.move = 1;
        p.selected = true;
        p.win = false;
        assertEquals('X', p.symbol);
    }

    @Test
    public void testGameConstructor() {
        Game game = new Game();
        assertNotNull(game.board);
        assertEquals(9, game.board.length);
        assertEquals(State.PLAYING, game.state);
    }

    @Test
    public void testCheckStateXWin() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateOWin() {
        Game game = new Game();
        game.symbol = 'O';
        char[] board = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateDraw() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    public void testCheckStatePlaying() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', ' '};
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    public void testGenerateMoves() {
        Game game = new Game();
        char[] board = {'X', ' ', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(6, moves.size());
        assertTrue(moves.contains(1));
    }

    @Test
    public void testEvaluatePosition() {
        Game game = new Game();
        Player p = new Player();
        p.symbol = 'X';
        
        game.symbol = 'X';
        char[] boardX = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(Game.INF, game.evaluatePosition(boardX, p));
        
        game.symbol = 'O';
        char[] boardO = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(-Game.INF, game.evaluatePosition(boardO, p));
    }

    @Test
    public void testMiniMax() {
        Game game = new Game();
        Player p = new Player();
        p.symbol = 'X';
        char[] board = {'X', 'O', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        int move = game.MiniMax(board, p);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    public void testUtilityPrintChar() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        char[] board = {'X', 'O', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        Utility.print(board);
        assertTrue(outContent.toString().contains("X"));
        System.setOut(System.out);
    }

    @Test
    public void testUtilityPrintInt() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        int[] board = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.print(board);
        assertTrue(outContent.toString().contains("1"));
        System.setOut(System.out);
    }

    @Test
    public void testUtilityPrintList() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1); moves.add(2);
        Utility.print(moves);
        assertTrue(outContent.toString().contains("1"));
        System.setOut(System.out);
    }

    @Test
    public void testTicTacToeCell() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);
        assertEquals(1, cell.getNum());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertEquals(' ', cell.getMarker());
        
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
    }

    @Test
    public void testTicTacToePanel() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3,3));
        assertNotNull(panel);
    }
}
