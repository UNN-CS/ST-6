package com.mycompany.app;

import org.junit.jupiter.api.*;
import java.lang.reflect.Field;
import java.awt.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private TicTacToePanel panel;

    @BeforeEach
    void setUp() {
        panel = new TicTacToePanel(new GridLayout(3,3));
    }

    @Test
    void testConstructorCreatesCells() throws Exception {
        Field cellsField = TicTacToePanel.class.getDeclaredField("cells");
        cellsField.setAccessible(true);
        TicTacToeCell[] cells = (TicTacToeCell[]) cellsField.get(panel);
        assertNotNull(cells);
        assertEquals(9, cells.length);
        for (int i = 0; i < 9; i++) {
            assertNotNull(cells[i]);
            assertEquals(' ', cells[i].getMarker());
        }
    }

    @Test
    void testGameInitialized() throws Exception {
        Field gameField = TicTacToePanel.class.getDeclaredField("game");
        gameField.setAccessible(true);
        Game game = (Game) gameField.get(panel);
        assertNotNull(game);
        assertEquals(game.player1, game.cplayer);
    }

    @Test
    void testCheckState_X_Wins_Row() {
        Game game = new Game();
        char[] board = {'X', 'X', 'X', ' ', 'O', 'O', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckState_O_Wins_Row() {
        Game game = new Game();
        char[] board = {'X', 'X', ' ', 'O', 'O', 'O', 'X', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testCheckState_X_Wins_Column() {
        Game game = new Game();
        char[] board = {'X', 'O', 'O', 'X', ' ', ' ', 'X', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckState_X_Wins_Diagonal() {
        Game game = new Game();
        char[] board = {'X', 'O', 'O', ' ', 'X', ' ', ' ', ' ', 'X'};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckState_Draw() {
        Game game = new Game();
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    void testCheckState_Playing() {
        Game game = new Game();
        char[] board = {'X', ' ', ' ', ' ', 'O', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    void testGenerateMoves() {
        Game game = new Game();
        char[] board = {'X', ' ', 'O', ' ', 'X', ' ', 'O', ' ', ' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        int[] expected = {1, 3, 5, 7, 8};
        assertArrayEquals(expected, moves.stream().mapToInt(Integer::intValue).toArray());
    }

    @Test
    void testEvaluatePosition_X_Win() {
        Game game = new Game();
        Player playerX = new Player();
        playerX.symbol = 'X';
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(board, playerX));
    }

    @Test
    void testEvaluatePosition_O_Win() {
        Game game = new Game();
        Player playerX = new Player();
        playerX.symbol = 'X';
        char[] board = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(-Game.INF, game.evaluatePosition(board, playerX));
    }

    @Test
    void testEvaluatePosition_Draw() {
        Game game = new Game();
        Player playerX = new Player();
        playerX.symbol = 'X';
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(0, game.evaluatePosition(board, playerX));
    }

    @Test
    void testEvaluatePosition_Incomplete() {
        Game game = new Game();
        Player playerX = new Player();
        playerX.symbol = 'X';
        char[] board = {'X', ' ', ' ', ' ', 'O', ' ', ' ', ' ', ' '};
        assertEquals(-1, game.evaluatePosition(board, playerX));
    }

    @Test
    void testMiniMax_Simple_Win_For_X() {
        Game game = new Game();
        Player player = new Player();
        player.symbol = 'X';
        char[] board = {'X', ' ', ' ', 'X', ' ', ' ', ' ', ' ', ' '};
        int move = game.MiniMax(board, player);
        assertTrue(move >= 1 && move <= 9, "MiniMax должен вернуть номер хода от 1 до 9");
        char[] tempBoard = board.clone();
        tempBoard[move - 1] = 'X';
        game.symbol = 'X';
        assertTrue(game.checkState(tempBoard) != State.OWIN, "Ход " + move + " ведёт к проигрышу X");
    }


    @Test
    void testMiniMax_Simple_Win_For_O() {
        Game game = new Game();
        Player player = new Player();
        player.symbol = 'O';
        char[] board = {'X', ' ', ' ', 'X', ' ', ' ', ' ', ' ', ' '};
        int move = game.MiniMax(board, player);
        assertTrue(move >= 1 && move <= 9, "MiniMax должен вернуть номер хода от 1 до 9");
        char[] tempBoard = board.clone();
        tempBoard[move - 1] = 'O';
        game.symbol = 'O';
        assertTrue(game.checkState(tempBoard) != State.XWIN, "Ход " + move + " ведёт к проигрышу O");
    }

    @Test
    void testMiniMax_Preference_For_Win_Over_Draw() {
        Game game = new Game();
        Player player = new Player();
        player.symbol = 'O';
        char[] board = {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        int move = game.MiniMax(board, player);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    void testMiniMax_No_Moves() {
        Game game = new Game();
        Player player = new Player();
        player.symbol = 'X';
        char[] board = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'}; 
        game.symbol = 'X';
        int move = game.MiniMax(board, player);
        assertEquals(0, move);
    }

    @Test
    void testGameInitialization() {
        Game game = new Game();
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        char[] expectedBoard = new char[9];
        for (int i = 0; i < 9; i++) {
            expectedBoard[i] = ' ';
        }
        assertArrayEquals(expectedBoard, game.board);
    }

    @Test
    void testPlayerInitialization() {
        Player player1 = new Player();
        assertFalse(player1.selected);
        assertFalse(player1.win);
        assertEquals(0, player1.move);
    }

    @Test
    void testPrintCharArray() {
        char[] board = {'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X'};
        Utility.print(board);
    }

    @Test
    void testPrintIntArray() {
        int[] board = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.print(board);
    }

    @Test
    void testPrintArrayList() {
        java.util.ArrayList<Integer> moves = new java.util.ArrayList<>();
        moves.add(1);
        moves.add(5);
        moves.add(9);
        Utility.print(moves);
    }

     @Test
    void testTicTacToeCellInitialization() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(0, cell.getNum());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertEquals(' ', cell.getMarker());
    }

    @Test
    void testSetMarkerAndGetMarker() {
        TicTacToeCell cell = new TicTacToeCell(1, 1, 1);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
    }

    @Test
    void testGetRowColNum() {
        TicTacToeCell cell = new TicTacToeCell(4, 1, 1);
        assertEquals(1, cell.getRow());
        assertEquals(1, cell.getCol());
        assertEquals(4, cell.getNum());
    }

    @Test
    void testPlayerFields() {
        Player player = new Player();
        assertEquals('\0', player.symbol);
        assertEquals(0, player.move);
        assertEquals(false, player.selected);
        assertEquals(false, player.win);
    }

    @Test
    void testPlayerSetFields() {
        Player player = new Player();
        player.symbol = 'O';
        player.move = 5;
        player.selected = true;
        player.win = true;
        assertEquals('O', player.symbol);
        assertEquals(5, player.move);
        assertEquals(true, player.selected);
        assertEquals(true, player.win);
    }

}