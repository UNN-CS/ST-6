package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

public class GameTest {
    private Game game = new Game();
    private Player player1 = game.player1;
    private Player player2 = game.player2;

    @Test
    public void testEvaluatePositionWin() {
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(board, player1));
    }

    @Test
    public void testEvaluatePositionDraw() {
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(0, game.evaluatePosition(board, player1));
    }

    @Test
    public void testMiniMaxBlockWin() {
        // Ситуация: игрок O может выиграть следующим ходом, X должен заблокировать
        char[] board = {'O', 'O', ' ', 'X', 'X', ' ', ' ', ' ', ' '};
        game.board = board;
        int bestMove = game.MiniMax(board, player2);
        assertEquals(3, bestMove); // Ожидается ход в позицию 3 (индекс 2)
    }

    @Test
    public void testCheckStateDiagonalWin() {
        char[] board = {'X', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X'};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }
}