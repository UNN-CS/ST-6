package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    @Test
    public void testInitialState() {
        Game game = new Game();
        assertEquals(State.PLAYING, game.state);
        for (char cell : game.board) {
            assertEquals(' ', cell);
        }
    }

    @Test
    public void testPlayerMove() {
        Game game = new Game();
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = 'X';
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    public void testWinningCondition() {
        Game game = new Game();
        game.board[0] = 'O';
        game.board[1] = 'O';
        game.board[2] = 'O';
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    public void testDrawCondition() {
        Game game = new Game();
        game.board[0] = 'X';
        game.board[1] = 'O';
        game.board[2] = 'X';
        game.board[3] = 'X';
        game.board[4] = 'O';
        game.board[5] = 'X';
        game.board[6] = 'O';
        game.board[7] = 'X';
        game.board[8] = 'O';
        assertEquals(State.DRAW, game.checkState(game.board));
    }

    @Test
    public void testMiniMax() {
        Game game = new Game();
        game.player1.symbol = 'X';
        game.player2.symbol = 'O';
        game.board[0] = 'X';
        game.board[1] = 'O';
        game.board[2] = 'X';
        game.board[3] = ' ';
        game.board[4] = 'O';
        game.board[5] = ' ';
        game.board[6] = ' ';
        game.board[7] = ' ';
        game.board[8] = 'X';
        assertEquals(6, game.MiniMax(game.board, game.player2));
    }
}

