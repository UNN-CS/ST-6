package com.example;  //a
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProgramTest {

    private Game game = new Game();

    @Test
    public void testInitialState() {
        Game game1 = new Game();
        assertEquals(State.PLAYING, game1.state);
        for (char c : game1.board) {
            assertEquals(' ', c);
        }
    }

    @Test
    public void testSetProgram() {
        Program program = new Program();
        assertNotNull(program);
    }

    @Test
    public void testSetTic() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        assertNotNull(panel);
    }

    @Test
    public void testClickOnCell() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        JButton fakeButton = new JButton();
        ActionEvent fakeEvent = new ActionEvent(fakeButton, ActionEvent.ACTION_PERFORMED, "");

        assertDoesNotThrow(() -> panel.actionPerformed(fakeEvent));
    }

    @Test
    public void testSetTicCell() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertNotNull(cell);
    }

    @Test
    public void testConstructorAndAccessors() {
        int num = 0;
        int x = 1;
        int y = 2;

        TicTacToeCell cell = new TicTacToeCell(num, x, y);

        assertEquals(num, cell.getNum());
        assertEquals(x, cell.getCol());
        assertEquals(y, cell.getRow());
        assertEquals(' ', cell.getMarker());
    }

    @Test
    public void testPrintCharArray() {
        char[] board = {'X', 'O', ' ', 'X', ' ', 'O', 'O', ' ', 'X'};
        Utility.print(board);
    }

    @Test
    public void testPrintIntArray() {
        int[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.print(ints);
    }

    @Test
    public void testSetUtility() {
        Utility utility = new Utility();
        assertNotNull(utility);
    }

    @Test
    public void testPrintArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(3);
        moves.add(5);
        moves.add(7);
        Utility.print(moves);
    }

    @Test
    public void testGetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);

        cell.setMarker("X");
        assertEquals('X', cell.getMarker());

        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
    }

    @Test
    void testGetFont() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);

        Font expectedFont = new Font("Arial", Font.PLAIN, 40);
        assertEquals(expectedFont, cell.getFont());
    }

    @Test
    public void testGenerateMoves() {
        ArrayList<Integer> moveList = new ArrayList<>();
        char[] emptyBoard = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        game.board = emptyBoard;
        game.generateMoves(game.board, moveList);
        assertEquals(9, moveList.size());
    }

    @Test
    public void testCheckStatePlaying() {
        Game game1 = new Game();
        assertEquals(State.PLAYING, game1.checkState(game1.board));
    }

    @Test
    public void testCheckStateXWin() {
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = 'X';
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    public void testCheckStateOWin() {
        game.board[0] = 'O';
        game.board[1] = 'O';
        game.board[2] = 'O';
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    public void testCheckStateDraw() {
        char[] drawBoard = {'X', 'O', 'X',
                            'X', 'X', 'O',
                            'O', 'X', 'O'};
        game.board = drawBoard;
        assertEquals(State.DRAW, game.checkState(drawBoard));
    }

    @Test
    public void testEvaluatePosition() {
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = 'X';
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(game.board, game.player1));
    }

    @Test
    public void testMinMove() {
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = 'O';
        int result = game.MinMove(game.board, game.player2);
        assertTrue(result <= Game.INF && result >= -Game.INF);
    }

    @Test
    public void testMaxMove() {
        game.board[0] = 'X';
        game.board[1] = 'O';
        game.board[2] = 'X';
        int result = game.MaxMove(game.board, game.player1);
        assertTrue(result >= -Game.INF && result <= Game.INF);
    }

    @Test
    public void testMiniMax() {
        Game game1 = new Game();
        game1.board[0] = 'X';
        game1.board[1] = 'O';
        game1.board[2] = 'X';
        int result = game1.MiniMax(game1.board, game1.player1);
        assertTrue(result >= 1 && result <= 9);
    }

    @Test
    public void testMiniMaxWithNoMovesLeft() {
        char[] fullBoard = {'X', 'O', 'X',
                            'O', 'X', 'O',
                            'O', 'X', 'O'};
        int result = game.MiniMax(fullBoard, game.player1);
        assertEquals(0, result);
    }

    @Test
    public void testMiniMaxWithImmediateWin() {
        char[] immediateWinBoard = {'X', 'X', ' ', 'O', 'O', ' ', ' ', ' ', ' '};
        int result = game.MiniMax(immediateWinBoard, game.player1);
        assertEquals(3, result);
    }

    @Test
    public void testMiniMaxWithImmediateBlock() {
        char[] immediateBlockBoard = {'O', 'O', ' ', ' ', 'X', ' ', 'X', ' ', ' '};
        int result = game.MiniMax(immediateBlockBoard, game.player2);
        assertEquals(3, result);
    }

    @Test
    public void testMiniMaxWithDraw() {
        char[] drawBoard = {'X', 'O', 'X', 'X', 'X', 'O', 'O', 'X', 'O'};
        int result = game.MiniMax(drawBoard, game.player1);
        assertTrue(result >= 0 && result <= 9);
    }
}