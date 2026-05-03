package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class GameTests {

    @Test
    void checkBase() {
        Game g = new Game();
        assertEquals('X', g.player1.symbol);
        assertEquals('O', g.player2.symbol);
        assertEquals(State.PLAYING, g.state);
        assertNotNull(g.board);
    }

    @Test
    void checkWinRow() {
        Game g = new Game();
        g.symbol = 'X';
        char[] b = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.XWIN, g.checkState(b));
    }

    @Test
    void checkWinCol() {
        Game g = new Game();
        g.symbol = 'O';
        char[] b = {'O', ' ', ' ', 'O', ' ', ' ', 'O', ' ', ' '};
        assertEquals(State.OWIN, g.checkState(b));
    }

    @Test
    void checkWinDiag() {
        Game g = new Game();
        g.symbol = 'X';
        char[] b = {'X', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X'};
        assertEquals(State.XWIN, g.checkState(b));
    }

    @Test
    void checkWinDiagReverse() {
        Game g = new Game();
        g.symbol = 'O';
        char[] b = {' ', ' ', 'O', ' ', 'O', ' ', 'O', ' ', ' '};
        assertEquals(State.OWIN, g.checkState(b));
    }

    @Test
    void checkFullBoard() {
        Game g = new Game();
        g.symbol = 'X';
        char[] b = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(State.DRAW, g.checkState(b));
    }

    @Test
    void checkPossibleMoves() {
        Game g = new Game();
        char[] b = {'X', 'O', ' ', ' ', 'X', ' ', ' ', ' ', ' '};
        ArrayList<Integer> list = new ArrayList<>();
        g.generateMoves(b, list);
        assertEquals(6, list.size());
    }

    @Test
    void checkPositionValue() {
        Game g = new Game();
        Player p = new Player(); p.symbol = 'X';
        char[] win = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        g.symbol = 'X';
        assertEquals(100, g.evaluatePosition(win, p));
        char[] draw = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(0, g.evaluatePosition(draw, p));
    }

    @Test
    void checkAiRun() {
        Game g = new Game();
        Player p2 = new Player(); p2.symbol = 'O';
        int m = g.MiniMax(g.board, p2);
        assertTrue(m >= 1 && m <= 9);
    }

    @Test
    void checkAiBlock() {
        Game g = new Game();
        Player ai = new Player(); ai.symbol = 'O';
        char[] b = {'X', 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        int m = g.MiniMax(b, ai);
        assertTrue(m > 0 && m <= 9);
    }

    @Test
    void checkAiWinMove() {
        Game g = new Game();
        Player ai = new Player(); ai.symbol = 'O';
        char[] b = {'O', 'O', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        int m = g.MiniMax(b, ai);
        assertTrue(m > 0);
    }

    @Test
    void checkBoardUpdate() {
        Game g = new Game();
        g.board[0] = 'X';
        assertEquals('X', g.board[0]);
        assertEquals(' ', g.board[1]);
    }
}