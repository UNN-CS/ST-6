package org.example;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    static Game g;

    @BeforeAll
    static void initializeGame() {
        g = new Game();
    }


    @org.junit.jupiter.api.Test
    void GameDefaultConstructor(){
        Assertions.assertDoesNotThrow(() -> {Game g1 = new Game();});
        g = new Game();
        Assertions.assertDoesNotThrow(() -> {Player p = g.player1; p = g.player2;});
        Assertions.assertEquals('X', g.player1.symbol);
        Assertions.assertEquals('O', g.player2.symbol);
        Assertions.assertEquals(State.PLAYING, g.state);
        Assertions.assertDoesNotThrow(() -> {char c = g.board[0]; c = g.board[8];});
        for(int i = 0; i < 9; i++) {
            Assertions.assertEquals(' ', g.board[i]);
        }
    }

    @org.junit.jupiter.api.Test
    void GameCheckStateWins() {
        g = new Game();
        g.symbol = 'X';
        char[] b = new char[]{'X','X','X',
                ' ',' ',' ',
                ' ',' ',' '};
        Assertions.assertEquals(State.XWIN,g.checkState(b));
        b = new char[]{' ',' ',' ',
                'X','X','X',
                ' ',' ',' '};
        Assertions.assertEquals(State.XWIN,g.checkState(b));
        b = new char[]{' ',' ',' ',
                ' ',' ',' ',
                'X','X','X'};
        Assertions.assertEquals(State.XWIN,g.checkState(b));
        b = new char[]{'X',' ',' ',
                'X',' ',' ',
                'X',' ',' '};
        Assertions.assertEquals(State.XWIN,g.checkState(b));
        b = new char[]{' ','X',' ',
                ' ','X',' ',
                ' ','X',' '};
        Assertions.assertEquals(State.XWIN,g.checkState(b));
        b = new char[]{' ',' ','X',
                ' ',' ','X',
                ' ',' ','X'};
        Assertions.assertEquals(State.XWIN,g.checkState(b));
        b = new char[]{'X',' ',' ',
                ' ','X',' ',
                ' ',' ','X'};
        Assertions.assertEquals(State.XWIN,g.checkState(b));
        b = new char[]{' ',' ','X',
                ' ','X',' ',
                'X',' ',' '};
        Assertions.assertEquals(State.XWIN,g.checkState(b));
        g.symbol = 'O';
        b = new char[]{'O','O','O',
                ' ',' ',' ',
                ' ',' ',' '};
        Assertions.assertEquals(State.OWIN,g.checkState(b));
        b = new char[]{' ',' ',' ',
                'O','O','O',
                ' ',' ',' '};
        Assertions.assertEquals(State.OWIN,g.checkState(b));
        b = new char[]{' ',' ',' ',
                ' ',' ',' ',
                'O','O','O'};
        Assertions.assertEquals(State.OWIN,g.checkState(b));
        b = new char[]{'O',' ',' ',
                'O',' ',' ',
                'O',' ',' '};
        Assertions.assertEquals(State.OWIN,g.checkState(b));
        b = new char[]{' ','O',' ',
                ' ','O',' ',
                ' ','O',' '};
        Assertions.assertEquals(State.OWIN,g.checkState(b));
        b = new char[]{' ',' ','O',
                ' ',' ','O',
                ' ',' ','O'};
        Assertions.assertEquals(State.OWIN,g.checkState(b));
        b = new char[]{'O',' ',' ',
                ' ','O',' ',
                ' ',' ','O'};
        Assertions.assertEquals(State.OWIN,g.checkState(b));
        b = new char[]{' ',' ','O',
                ' ','O',' ',
                'O',' ',' '};
        Assertions.assertEquals(State.OWIN,g.checkState(b));
    }

    @org.junit.jupiter.api.Test
    void GameCheckStateDrawn() {
        g = new Game();
        char[] b = new char[] {'X','O','X',
                'O','X','O',
                'O','X','O'};
        Assertions.assertEquals(State.DRAW,g.checkState(b));
    }

    @org.junit.jupiter.api.Test
    void GameCheckStatePlaying() {
        g = new Game();
        char[] b = new char[] {'X','O','X',
                'O','X','O',
                'O','X',' '};
        Assertions.assertEquals(State.PLAYING,g.checkState(b));
    }

    @org.junit.jupiter.api.Test
    void GameGenerateMoves() {
        g = new Game();
        char[] b = new char[] {'X','O','X',
                'O','X','O',
                'O','X','O'};
        ArrayList<Integer> res = new ArrayList<>();
        g.generateMoves(b, res);
        Assertions.assertEquals(0,res.size());
        b = new char[] {' ',' ',' ',
                ' ',' ',' ',
                ' ',' ',' '};
        res = new ArrayList<>();
        g.generateMoves(b, res);
        for(int i = 0; i < 9; i++) {
            Assertions.assertEquals(i, res.get(i));
        }
    }
    @org.junit.jupiter.api.Test
    void GameEvaluatePosition() {
        g = new Game();
        g.symbol = 'X';
        g.board = new char[]{' ',' ',' ',
                'X','X','X',
                ' ',' ',' '};
        Assertions.assertEquals(State.PLAYING, g.state);
        Assertions.assertEquals(100,g.evaluatePosition(g.board,g.player1));
        Assertions.assertEquals(-100,g.evaluatePosition(g.board,g.player2));

        g.symbol = 'O';
        g.board = new char[]{' ',' ',' ',
                'O','O','O',
                ' ',' ',' '};
        Assertions.assertEquals(100,g.evaluatePosition(g.board,g.player2));
        Assertions.assertEquals(-100,g.evaluatePosition(g.board,g.player1));

        g.board = new char[]{' ',' ',' ',
                'O','O',' ',
                ' ',' ',' '};
        Assertions.assertEquals(-1,g.evaluatePosition(g.board,g.player2));
        g.board = new char[]{'X','X','O',
                'O','O','X',
                'X','X','O'};
        Assertions.assertEquals(0,g.evaluatePosition(g.board,g.player2));
    }

    @org.junit.jupiter.api.Test
    void GameMinimaxEmptyBoard() {
        char[] board = new char[9];
        g = new Game();
        int expectedMove = 0;

        int actualMove = g.MiniMax(board, g.player1);

        Assertions.assertEquals(expectedMove, actualMove);
    }

    @org.junit.jupiter.api.Test
    void GameMinimaxBlockWin() {
        char[] board = {' ', ' ', 'O',
                ' ', 'X', 'O',
                ' ', ' ', ' '};
        g = new Game();
        int expectedMove = 9;

        int actualMove = g.MiniMax(board, g.player1);

        Assertions.assertEquals(expectedMove, actualMove);
    }

    @org.junit.jupiter.api.Test
    void CellConstructor() {
        Assertions.assertDoesNotThrow(() -> {TicTacToeCell c = new TicTacToeCell(1,0,2);});
        TicTacToeCell c = new TicTacToeCell(1,0,2);
        Assertions.assertEquals(1, c.getNum());
        Assertions.assertEquals(0, c.getCol());
        Assertions.assertEquals(2, c.getRow());
        Assertions.assertEquals(' ', c.getMarker());
    }

    @org.junit.jupiter.api.Test
    void CellSetMarker() {
        TicTacToeCell c = new TicTacToeCell(1,0,2);
        c.setMarker("Xxx");
        Assertions.assertEquals('X', c.getMarker());
        Assertions.assertEquals("Xxx", c.getText());
        Assertions.assertEquals(false, c.isEnabled());
    }

    @org.junit.jupiter.api.Test
    void PanelConstructor() {
        Assertions.assertDoesNotThrow(() -> {TicTacToePanel p = new TicTacToePanel(new GridLayout(3,3));});
    }

    @org.junit.jupiter.api.Test
    void UtilityMethods() {
        Assertions.assertDoesNotThrow(() -> {Utility.print(new char[] {' ','X','O',' ',' ',' ',' ',' ',' '});});
        Assertions.assertDoesNotThrow(() -> {Utility.print(new int[] {0,1,2,3,4,5,6,7,8});});
        Assertions.assertDoesNotThrow(() -> {ArrayList<Integer> ar = new ArrayList<Integer>();
            ar.add(1); ar.add(2); ar.add(0); ar.add(-1); ar.add(1); ar.add(1); ar.add(2); ar.add(0); ar.add(-1);
            Utility.print(ar);});
    }
}
