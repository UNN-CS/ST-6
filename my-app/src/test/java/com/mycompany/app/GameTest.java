package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    public void testMinMove() {
        Game game = new Game();
        Player player = new Player();
        player.symbol = 'X';

        // Test with a simple board where O should block X
        char[] board = {'X', 'X', ' ', ' ', 'O', ' ', ' ', ' ', ' '};
        int value = game.MinMove(board, player);
        // MinMove should return a negative or 0 value here
        assertTrue(value <= 0);
    }

    @Test
    public void testCheckStateGameContinues() {
        Game game = new Game();
        game.symbol = 'X';

        // Test game continues - not full board and no wins
        char[] board = {'X', 'O', 'X', ' ', 'O', ' ', ' ', ' ', ' '};
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    public void testEvaluatePosition() {
        Game game = new Game();
        Player player = new Player();
        player.symbol = 'X';

        // Test X win
        char[] xWinBoard = {'X', 'X', 'X', 'O', 'O', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(xWinBoard, player));

        // Test O win
        player.symbol = 'O';
        char[] oWinBoard = {'O', 'O', 'O', 'X', 'X', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(oWinBoard, player));

        // Test draw
        player.symbol = 'X';
        char[] drawBoard = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(0, game.evaluatePosition(drawBoard, player));

        // Test game continues
        char[] continueBoard = {'X', 'O', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(-1, game.evaluatePosition(continueBoard, player));
    }

    @Test
    public void testCheckStateWinningConditionsForO() {
        Game game = new Game();
        game.symbol = 'O';

        // Test horizontal win
        char[] board1 = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.OWIN, game.checkState(board1));

        // Test vertical win
        char[] board2 = {'O', ' ', ' ', 'O', ' ', ' ', 'O', ' ', ' '};
        assertEquals(State.OWIN, game.checkState(board2));
    }

    @Test
    public void testMaxMove() {
        Game game = new Game();
        Player player = new Player();
        player.symbol = 'X';

        // Test with a simple board
        char[] board = {'X', ' ', ' ', ' ', 'O', ' ', ' ', ' ', ' '};
        int value = game.MaxMove(board, player);
        // MaxMove should return a positive or 0 value for a good position
        assertTrue(value >= 0);
    }

    @Test
    public void testGenerateMoves() {
        Game game = new Game();
        ArrayList<Integer> moveList = new ArrayList<>();

        // Test with empty board
        char[] emptyBoard = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        game.generateMoves(emptyBoard, moveList);
        assertEquals(9, moveList.size());

        // Clear and test with partially filled board
        moveList.clear();
        char[] partialBoard = {'X', 'O', ' ', 'X', ' ', 'O', ' ', ' ', 'X'};
        game.generateMoves(partialBoard, moveList);
        assertEquals(4, moveList.size());
        assertTrue(moveList.contains(2));
        assertTrue(moveList.contains(4));
        assertTrue(moveList.contains(6));
        assertTrue(moveList.contains(7));
    }

    @Test
    public void testMiniMax() {
        Game game = new Game();
        game.board = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        game.player2.symbol = 'O';

        // This tests that MiniMax returns a valid move (1-9)
        int move = game.MiniMax(game.board, game.player2);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    public void testGameInitialization() {
        Game game = new Game();

        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals(State.PLAYING, game.state);

        // Check that board is initialized with spaces
        for (int i = 0; i < 9; i++) {
            assertEquals(' ', game.board[i]);
        }
    }

    @Test
    public void testCheckStateDraw() {
        Game game = new Game();
        game.symbol = 'X'; // The last player to move

        // Test draw condition - full board with no wins
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    public void testCheckStateWinningConditionsForX() {
        Game game = new Game();
        game.symbol = 'X';

        // Test horizontal win
        char[] board1 = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(board1));

        // Test vertical win
        char[] board2 = {'X', ' ', ' ', 'X', ' ', ' ', 'X', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(board2));

        // Test diagonal win
        char[] board3 = {'X', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X'};
        assertEquals(State.XWIN, game.checkState(board3));

        // Test other diagonal win
        char[] board4 = {' ', ' ', 'X', ' ', 'X', ' ', 'X', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(board4));
    }
}