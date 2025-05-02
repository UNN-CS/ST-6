package com.mycompany.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MinMax_test {
    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testMiniMaxWinInOneMove() {
        char[] board = {'X','X',' ','O','O',' ',' ',' ',' '};
        game.board = board;
        int bestMove = game.MiniMax(board, game.player1);
        assertEquals(3, bestMove); // X должен выиграть, поставив в позицию 3
    }

    @Test
    public void testMiniMaxBlockWin() {
        char[] board = {'O','O',' ','X',' ',' ',' ',' ',' '};
        game.board = board;
        int bestMove = game.MiniMax(board, game.player1);
        assertEquals(3, bestMove); // X должен блокировать победу O
    }

    @Test
    public void testMinMove() {
        char[] board = {'X',' ',' ',' ','O',' ',' ',' ',' '};
        assertEquals(-Game.INF, game.MinMove(board, game.player1));
    }

    @Test
    public void testMaxMove() {
        char[] board = {'X',' ',' ',' ','O',' ',' ',' ',' '};
        assertEquals(Game.INF, game.MaxMove(board, game.player2));
    }
}