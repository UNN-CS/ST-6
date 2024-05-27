package org.example;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class GameTest {
    @Test
    public void checkStatePlaying() {
        Game game = new Game();
        game.symbol = 'X';
        State state = game.checkState(game.board);
        Assert.assertEquals(state, State.PLAYING);
    }

    @Test
    public void checkStateXWin() {
        Game game = new Game();
        game.symbol = 'X';
        char[] field = {'X', 'O', 'O', ' ', 'X', 'O', ' ', 'O', 'X'};
        State state = game.checkState(field);
        Assert.assertEquals(state, State.XWIN);
    }

    @Test
    public void checkStateOWin() {
        Game game = new Game();
        game.symbol = 'O';
        char[] field = {'O', 'X', 'X', ' ', 'O', 'X', ' ', 'X', 'O'};
        State state = game.checkState(field);
        Assert.assertEquals(state, State.OWIN);
    }

    @Test
    public void checkStateDraw() {
        Game game = new Game();
        game.symbol = 'X';
        game.board = new char[]
                {'X', 'X', 'O', 'O', 'O', 'X', 'X', 'X', 'O'};
        State state = game.checkState(game.board);
        Assert.assertEquals(state, State.DRAW);
    }

    @Test
    public void generateMovesEmptyArray() {
        Game game = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        Assert.assertEquals(9, moves.size());
    }

    @Test
    public void generateMovesOneRow() {
        Game game = new Game();
        for (int i = 0; i < 3; i++) {
            game.board[i] = 'X';
        }
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        Assert.assertEquals(6, moves.size());
    }

    @Test
    public void evaluatePositionPlayerWin() {
        Game game = new Game();
        game.symbol = 'X';
        for (int i = 0; i < 3; i++) {
            game.board[i] = game.player1.symbol;
        }
        int result = game.evaluatePosition(game.board, game.player1);
        Assert.assertEquals(+Game.INF, result);
    }

    @Test
    public void evaluatePositionPlayerLose() {
        Game game = new Game();
        game.symbol = 'O';
        for (int i = 0; i < 3; i++) {
            game.board[i] = game.player2.symbol;
        }
        int result = game.evaluatePosition(game.board, game.player1);
        Assert.assertEquals(-Game.INF, result);
    }

    @Test
    public void evaluatePositionDraw() {
        Game game = new Game();
        game.symbol = 'X';
        game.board = new char[]
                {'X', 'X', 'O', 'O', 'O', 'X', 'X', 'X', 'O'};
        int result = game.evaluatePosition(game.board, game.player1);
        Assert.assertEquals(0, result);
    }

    @Test
    public void evaluatePositionPlaying() {
        Game game = new Game();
        int result = game.evaluatePosition(game.board, game.player1);
        Assert.assertEquals(-1, result);
    }

    @Test
    public void minimaxTest() {
        Game game = new Game();
        game.board = new char[] {'X', 'X', ' ', 'O', 'O', 'X', 'O', ' ', 'X'};
        int move = game.MiniMax(game.board, game.player2);
        Assert.assertTrue(move >= 1 && move <= 9);
    }
}
