package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest 
{
    @Test
    public void testCheckState() {
        Game game1 = new Game();
        char[] board1 = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.XWIN, game1.checkState(board1));

        board1 = new char[]{'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.OWIN, game1.checkState(board1));

        board1 = new char[]{'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X'};
        assertEquals(State.DRAW, game1.checkState(board1));

        board1 = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.PLAYING, game1.checkState(board1));
    }

    @Test
    public void testGenerateMoves() {
        Game game2 = new Game();
        ArrayList<Integer> moveList = new ArrayList<>();
        game2.generateMoves(game2.board, moveList);
        assertEquals(9, moveList.size());

        game2.board[0] = 'X';
        moveList.clear();
        game2.generateMoves(game2.board, moveList);
        assertEquals(8, moveList.size());
    }

    @Test
    public void testEvaluatePosition() {
        Game game3 = new Game();
        game3.board[0] = 'X';
        game3.board[1] = 'X';
        game3.board[2] = 'X';
        assertEquals(Game.INF, game3.evaluatePosition(game3.board, game3.player1));

        game3.board[0] = 'O';
        game3.board[1] = 'O';
        game3.board[2] = 'O';
        assertEquals(-Game.INF, game3.evaluatePosition(game3.board, game3.player2));

        game3.board[0] = 'X';
        game3.board[1] = 'O';
        game3.board[2] = 'X';
        assertEquals(0, game3.evaluatePosition(game3.board, game3.player1));
    }

    @Test
    public void testMiniMax() {
        Game game4 = new Game();
        game4.board[0] = 'X';
        game4.board[1] = 'X';
        assertEquals(2, game4.MiniMax(game4.board, game4.player2));
    }

    @Test
    public void testGetRow() {
        TicTacToeCell cell1 = new TicTacToeCell(0, 1, 2);
        assertEquals(2, cell1.getRow());
    }

    @Test
    public void testGetCol() {
        TicTacToeCell cell2 = new TicTacToeCell(0, 1, 2);
        assertEquals(1, cell2.getCol());
    }

    @Test
    public void testGetNum() {
        TicTacToeCell cell3 = new TicTacToeCell(5, 1, 2);
        assertEquals(5, cell3.getNum());
    }

    @Test
    public void testInitialValues() {
        TicTacToeCell cell4 = new TicTacToeCell(0, 0, 0);
        assertEquals(' ', cell4.getMarker());
        assertTrue(cell4.isEnabled());
    }

    @Test
    public void testSetMarker() {
        TicTacToeCell cell5 = new TicTacToeCell(0, 0, 0);
        cell5.setMarker("O");
        assertEquals('O', cell5.getMarker());
        assertFalse(cell5.isEnabled());
    }

    @Test
    public void testSetMarkerTwice() {
        TicTacToeCell cell6 = new TicTacToeCell(0, 0, 0);
        cell6.setMarker("X");
        cell6.setMarker("O");
        assertEquals('X', cell6.getMarker());
        assertFalse(cell6.isEnabled());
    }

    @Test
    public void testSetMarkerAfterReset() {
        TicTacToeCell cell7 = new TicTacToeCell(0, 0, 0);
        cell7.setMarker("X");
        cell7.setText(" ");
        cell7.setEnabled(true);
        cell7.setMarker("O");
        assertEquals('O', cell7.getMarker());
        assertFalse(cell7.isEnabled());
    }

    @Test
    public void testSymbol() {
        Player player1 = new Player();
        player1.symbol = 'X';
        assertEquals('X', player1.symbol);

        player1.symbol = 'O';
        assertEquals('O', player1.symbol);
    }

    @Test
    public void testValues() {
        Player player2 = new Player();
        assertFalse(player2.selected);
        assertFalse(player2.win);
        assertEquals(0, player2.move);

        player2.selected = true;
        player2.win = true;
        player2.move = 5;
        assertTrue(player2.selected);
        assertTrue(player2.win);
        assertEquals(5, player2.move);
    }

    @Test
    public void testDefaultConstructor() {
        Player player3 = new Player();
        assertEquals('\u0000', player3.symbol);
        assertFalse(player3.selected);
        assertFalse(player3.win);
        assertEquals(0, player3.move);
    }

    @Test
    public void testSettersAndGetters() {
        Player player4 = new Player();
        player4.symbol = 'O';
        player4.move = 3;
        player4.selected = true;
        player4.win = true;

        assertEquals('O', player4.symbol);
        assertEquals(3, player4.move);
        assertTrue(player4.selected);
        assertTrue(player4.win);
    }

    @Test
    public void testEvaluateSecondPlayerWin() {
        Game game1 = new Game();
        game1.symbol = 'O';
        for (int i = 0; i < 3; i++) {
            game1.board[i] = game1.symbol;
        }

        int result = game1.evaluatePosition(game1.board, game1.player2);
        assertEquals(Game.INF, result);
    }

    @Test
    public void testState() {
        Game game2 = new Game();
        assertEquals(State.PLAYING, game2.state);
        assertEquals('X', game2.player1.symbol);
        assertEquals('O', game2.player2.symbol);
        assertEquals(' ', game2.board[0]);
    }

    @Test
    public void testEvaluateFirstPlayerWin() {
        Game game3 = new Game();
        game3.symbol = 'X';
        for (int i = 0; i < 3; i++) {
            game3.board[i] = game3.symbol;
        }

        int result = game3.evaluatePosition(game3.board, game3.player1);
        assertEquals(Game.INF, result);
    }

    @Test
    public void testEvaluateDraw() {
        Game game4 = new Game();
        game4.board[0] = 'X';
        game4.board[1] = 'O';
        game4.board[2] = 'X';
        game4.board[3] = 'O';
        game4.board[4] = 'X';
        game4.board[5] = 'O';
        game4.board[6] = 'O';
        game4.board[7] = 'X';
        game4.board[8] = 'O';

        int result = game4.evaluatePosition(game4.board, game4.player1);
        assertEquals(0, result);
    }
}
