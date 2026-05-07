package com.mycompany.app;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AllTests {
    private Game game;
    
    @Before
    public void setUp() {
        game = new Game();
    }
    
    @Test
    public void testGameInitialization() {
        assertNotNull(game);
        assertNotNull(game.board);
        assertEquals(9, game.board.length);
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
    }
    
    @Test
    public void testBoardInitiallyEmpty() {
        for (int i = 0; i < 9; i++) {
            assertEquals(' ', game.board[i]);
        }
    }
    
    @Test
    public void testCheckStateXWinHorizontal() {
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        State state = game.checkState(board);
        assertEquals(State.XWIN, state);
    }
    
    @Test
    public void testCheckStateXWinVertical() {
        char[] board = {'X', ' ', ' ', 'X', ' ', ' ', 'X', ' ', ' '};
        game.symbol = 'X';
        State state = game.checkState(board);
        assertEquals(State.XWIN, state);
    }
    
    @Test
    public void testCheckStateXWinDiagonal() {
        char[] board = {'X', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X'};
        game.symbol = 'X';
        State state = game.checkState(board);
        assertEquals(State.XWIN, state);
    }
    
    @Test
    public void testCheckStateXWinAntiDiagonal() {
        char[] board = {' ', ' ', 'X', ' ', 'X', ' ', 'X', ' ', ' '};
        game.symbol = 'X';
        State state = game.checkState(board);
        assertEquals(State.XWIN, state);
    }
    
    @Test
    public void testCheckStateOWinHorizontal() {
        char[] board = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        State state = game.checkState(board);
        assertEquals(State.OWIN, state);
    }
    
    @Test
    public void testCheckStateOWinVertical() {
        char[] board = {'O', ' ', ' ', 'O', ' ', ' ', 'O', ' ', ' '};
        game.symbol = 'O';
        State state = game.checkState(board);
        assertEquals(State.OWIN, state);
    }
    
    @Test
    public void testCheckStateOWinDiagonal() {
        char[] board = {'O', ' ', ' ', ' ', 'O', ' ', ' ', ' ', 'O'};
        game.symbol = 'O';
        State state = game.checkState(board);
        assertEquals(State.OWIN, state);
    }
    
    @Test
    public void testCheckStateDraw() {
        char[] board = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'};
        game.symbol = 'X';
        State state = game.checkState(board);
        assertEquals(State.DRAW, state);
    }
    
    @Test
    public void testCheckStatePlaying() {
        char[] board = {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        State state = game.checkState(board);
        assertEquals(State.PLAYING, state);
    }
    
    @Test
    public void testGenerateMovesEmptyBoard() {
        char[] board = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(9, moves.size());
    }
    
    @Test
    public void testGenerateMovesPartialBoard() {
        char[] board = {'X', ' ', 'O', ' ', 'X', ' ', 'O', ' ', ' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(5, moves.size());
    }
    
    @Test
    public void testEvaluatePositionWinForX() {
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        Player p = new Player();
        p.symbol = 'X';
        int value = game.evaluatePosition(board, p);
        assertEquals(Game.INF, value);
    }
    
    @Test
    public void testEvaluatePositionDraw() {
        char[] board = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'};
        game.symbol = 'X';
        Player p = new Player();
        p.symbol = 'X';
        int value = game.evaluatePosition(board, p);
        assertEquals(0, value);
    }
    
    @Test
    public void testMiniMaxFirstMove() {
        char[] board = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        Player player = new Player();
        player.symbol = 'X';
        int bestMove = game.MiniMax(board, player);
        assertTrue(bestMove >= 1 && bestMove <= 9);
    }
    
    @Test
    public void testMiniMaxWithOneMoveLeft() {
        char[] board = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', ' '};
        Player player = new Player();
        player.symbol = 'O';
        int bestMove = game.MiniMax(board, player);
        assertEquals(9, bestMove);
    }
    
    @Test
    public void testCellConstructor() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertNotNull(cell);
        assertEquals(0, cell.getNum());
        assertEquals(' ', cell.getMarker());
    }
    
    @Test
    public void testSetMarkerX() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }
    
    @Test
    public void testUtilityPrintCharArray() {
        char[] board = {'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X'};
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Utility.print(board);
        assertTrue(outContent.toString().contains("X-O-X-O-X-O-X-O-X"));
        System.setOut(System.out);
    }

    @Test
    public void testUtilityPrintIntArray() {
        int[] positions = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        Utility.print(positions);
        
        assertTrue(outContent.toString().contains("1-2-3-4-5-6-7-8-9"));
        System.setOut(System.out);
    }

    @Test
    public void testUtilityPrintArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            moves.add(i);
        }
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        Utility.print(moves);
        
        assertTrue(outContent.toString().contains("0-1-2-3-4-5-6-7-8"));
        System.setOut(System.out);
    }

    @Test
    public void testUtilityPrintEmptyArrayList() {
        ArrayList<Integer> emptyList = new ArrayList<>();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        Utility.print(emptyList);
        
        assertTrue(true);
        System.setOut(System.out);
    }

    @Test
    public void testCellGetRowAndCol() {
        TicTacToeCell cell = new TicTacToeCell(4, 1, 1);
        assertEquals(1, cell.getRow());
        assertEquals(1, cell.getCol());
    }

    @Test
    public void testCellAllPositions() {
        for (int num = 0; num < 9; num++) {
            int row = num / 3;
            int col = num % 3;
            TicTacToeCell cell = new TicTacToeCell(num, col, row);
            assertEquals(num, cell.getNum());
            assertEquals(row, cell.getRow());
            assertEquals(col, cell.getCol());
            assertEquals(' ', cell.getMarker());
        }
    }

    @Test
    public void testCellSetMarkerO() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
        assertEquals("O", cell.getText());
        assertFalse(cell.isEnabled());
    }

    @Test
    public void testUtilityPrintAllMethods() {
        int[] positions1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ByteArrayOutputStream out1 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out1));
        Utility.print(positions1);
        assertTrue(out1.toString().contains("1-2-3"));
        System.setOut(System.out);
        
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(0);
        moves.add(4);
        moves.add(8);
        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out2));
        Utility.print(moves);
        assertTrue(out2.toString().contains("0-4-8"));
        System.setOut(System.out);
    }
}