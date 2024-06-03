package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

public class GameTest {

    @Test
    public void testCheckState() {
        Game game = new Game();
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));

        board = new char[]{'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));

        board = new char[]{'X', 'O', 'X', 'O', 'O', 'X', 'X', 'X', 'O'};
        assertEquals(State.DRAW, game.checkState(board));

        board = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    public void testGenerateMoves() {
        Game game = new Game();
        char[] board = {' ', 'X', ' ', 'O', ' ', 'X', ' ', 'O', ' '};
        ArrayList<Integer> moveList = new ArrayList<>();
        game.generateMoves(board, moveList);
        assertEquals(5, moveList.size());
        assertTrue(moveList.contains(0));
        assertTrue(moveList.contains(2));
        assertTrue(moveList.contains(4));
        assertTrue(moveList.contains(6));
        assertTrue(moveList.contains(8));
    }

    @Test
    public void testEvaluatePosition() {
        Game game = new Game();
        Player player = new Player();
        player.symbol = 'X';

        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(-1, game.evaluatePosition(board, player));

        player.symbol = 'O';
        assertEquals(-1, game.evaluatePosition(board, player));

        board = new char[]{'X', 'O', 'X', 'O', 'O', 'X', 'X', 'X', 'O'};
        assertEquals(0, game.evaluatePosition(board, player));

        board = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(-1, game.evaluatePosition(board, player));
    }

    @Test
    public void testMiniMax() {
        Game game = new Game();
        Player player1 = new Player();
        player1.symbol = 'X';
        Player player2 = new Player();
        player2.symbol = 'O';

        char[] board = new char[9];
        Arrays.fill(board, ' ');
        int move = game.MiniMax(board, player1);
        assertTrue(move >= 1 && move <= 9);

        board = new char[]{'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', ' '};
        move = game.MiniMax(board, player1);
        assertEquals(9, move);

        board = new char[]{'O', 'X', 'O', 'X', 'O', 'X', 'X', 'O', ' '};
        move = game.MiniMax(board, player2);
        assertEquals(9, move);
    }

    @Test
    public void testMinMove() {
        Game game = new Game();
        Player player1 = new Player();
        player1.symbol = 'X';
        Player player2 = new Player();
        player2.symbol = 'O';

        char[] board = new char[9];
        Arrays.fill(board, ' ');
        int value = game.MinMove(board, player1);
        assertEquals(0, value);

        board = new char[]{'X', 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        value = game.MinMove(board, player1);
        assertEquals(100, value);
    }

    @Test
    public void testMaxMove() {
        Game game = new Game();
        Player player1 = new Player();
        player1.symbol = 'X';
        Player player2 = new Player();
        player2.symbol = 'O';

        char[] board = new char[9];
        Arrays.fill(board, ' ');
        int value = game.MaxMove(board, player1);
        assertEquals(0, value);

        board = new char[]{' ', ' ', ' ', ' ', 'O', ' ', ' ', ' ', 'O'};
        value = game.MaxMove(board, player2);
        assertEquals(100, value);
    }
}

