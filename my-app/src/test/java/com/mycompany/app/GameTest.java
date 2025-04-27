package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

public class GameTest {

    @Test
    void testInitialGameState() {
        Game game = new Game();
        assertEquals(State.PLAYING, game.state, "Initial state should be PLAYING");
        assertNotNull(game.player1, "Player 1 should be initialized");
        assertNotNull(game.player2, "Player 2 should be initialized");
        assertEquals('X', game.player1.symbol, "Player 1 symbol should be X");
        assertEquals('O', game.player2.symbol, "Player 2 symbol should be O");
        assertEquals(9, game.board.length, "Board size should be 9");
        for (char cell : game.board) {
            assertEquals(' ', cell, "Initial board cells should be empty");
        }
    }

    @Test
    void testCheckState_XWinsRow() {
        Game game = new Game();
        game.symbol = 'X'; // checkState uses this field

        char[] boardRow1Win = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(boardRow1Win), "Should detect X win in row 1");

        char[] boardRow2Win = {' ', ' ', ' ', 'X', 'X', 'X', ' ', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(boardRow2Win), "Should detect X win in row 2");

        char[] boardRow3Win = {' ', ' ', ' ', ' ', ' ', ' ', 'X', 'X', 'X'};
        assertEquals(State.XWIN, game.checkState(boardRow3Win), "Should detect X win in row 3");
    }

    @Test
    void testCheckState_OWinsRow() {
        Game game = new Game();
        game.symbol = 'O'; // checkState uses this field

        char[] boardRow1Win = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.OWIN, game.checkState(boardRow1Win), "Should detect O win in row 1");

        char[] boardRow2Win = {' ', ' ', ' ', 'O', 'O', 'O', ' ', ' ', ' '};
        assertEquals(State.OWIN, game.checkState(boardRow2Win), "Should detect O win in row 2");

        char[] boardRow3Win = {' ', ' ', ' ', ' ', ' ', ' ', 'O', 'O', 'O'};
        assertEquals(State.OWIN, game.checkState(boardRow3Win), "Should detect O win in row 3");
    }

    @Test
    void testCheckState_XWinsColumn() {
        Game game = new Game();
        game.symbol = 'X'; // checkState uses this field

        char[] boardCol1Win = {'X', ' ', ' ', 'X', ' ', ' ', 'X', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(boardCol1Win), "Should detect X win in col 1");

        char[] boardCol2Win = {' ', 'X', ' ', ' ', 'X', ' ', ' ', 'X', ' '};
        assertEquals(State.XWIN, game.checkState(boardCol2Win), "Should detect X win in col 2");

        char[] boardCol3Win = {' ', ' ', 'X', ' ', ' ', 'X', ' ', ' ', 'X'};
        assertEquals(State.XWIN, game.checkState(boardCol3Win), "Should detect X win in col 3");
    }

    @Test
    void testCheckState_OWinsColumn() {
        Game game = new Game();
        game.symbol = 'O'; // checkState uses this field

        char[] boardCol1Win = {'O', ' ', ' ', 'O', ' ', ' ', 'O', ' ', ' '};
        assertEquals(State.OWIN, game.checkState(boardCol1Win), "Should detect O win in col 1");

        char[] boardCol2Win = {' ', 'O', ' ', ' ', 'O', ' ', ' ', 'O', ' '};
        assertEquals(State.OWIN, game.checkState(boardCol2Win), "Should detect O win in col 2");

        char[] boardCol3Win = {' ', ' ', 'O', ' ', ' ', 'O', ' ', ' ', 'O'};
        assertEquals(State.OWIN, game.checkState(boardCol3Win), "Should detect O win in col 3");
    }


    @Test
    void testCheckState_XWinsDiagonal() {
        Game game = new Game();
        game.symbol = 'X'; // checkState uses this field

        char[] boardDiag1Win = {'X', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X'};
        assertEquals(State.XWIN, game.checkState(boardDiag1Win), "Should detect X win on main diagonal");

        char[] boardDiag2Win = {' ', ' ', 'X', ' ', 'X', ' ', 'X', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(boardDiag2Win), "Should detect X win on anti-diagonal");
    }

    @Test
    void testCheckState_OWinsDiagonal() {
        Game game = new Game();
        game.symbol = 'O'; // checkState uses this field

        char[] boardDiag1Win = {'O', ' ', ' ', ' ', 'O', ' ', ' ', ' ', 'O'};
        assertEquals(State.OWIN, game.checkState(boardDiag1Win), "Should detect O win on main diagonal");

        char[] boardDiag2Win = {' ', ' ', 'O', ' ', 'O', ' ', 'O', ' ', ' '};
        assertEquals(State.OWIN, game.checkState(boardDiag2Win), "Should detect O win on anti-diagonal");
    }


    @Test
    void testCheckState_Draw() {
        Game game = new Game();
        // The symbol passed to checkState doesn't matter for a draw state
        game.symbol = 'X';

        char[] boardDraw = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'};
        assertEquals(State.DRAW, game.checkState(boardDraw), "Should detect a draw when board is full and no winner");
    }

    @Test
    void testCheckState_Playing() {
        Game game = new Game();
        game.symbol = 'X'; // checkState uses this field

        char[] boardPlaying = {'X', 'O', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.PLAYING, game.checkState(boardPlaying), "Should detect game is still playing if there are empty cells");

        char[] boardPlayingNoWinner = {'X', 'O', 'X', 'O', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.PLAYING, game.checkState(boardPlayingNoWinner), "Should detect game is still playing if no winner yet and empty cells exist");
    }

    @Test
    void testGenerateMoves() {
        Game game = new Game();
        char[] boardFull = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'};
        ArrayList<Integer> movesFull = new ArrayList<>();
        game.generateMoves(boardFull, movesFull);
        assertTrue(movesFull.isEmpty(), "Should generate no moves on a full board");

        char[] boardEmpty = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        ArrayList<Integer> movesEmpty = new ArrayList<>();
        game.generateMoves(boardEmpty, movesEmpty);
        assertEquals(9, movesEmpty.size(), "Should generate 9 moves on an empty board");
        ArrayList<Integer> expectedEmptyMoves = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
        assertEquals(expectedEmptyMoves, movesEmpty, "Moves on empty board should be indices 0-8");


        char[] boardPartial = {'X', ' ', 'O', ' ', 'X', ' ', 'O', ' ', 'X'};
        ArrayList<Integer> movesPartial = new ArrayList<>();
        game.generateMoves(boardPartial, movesPartial);
        assertEquals(4, movesPartial.size(), "Should generate 4 moves on a partially filled board");
        ArrayList<Integer> expectedPartialMoves = new ArrayList<>(Arrays.asList(1, 3, 5, 7));
        assertEquals(expectedPartialMoves, movesPartial, "Moves on partially filled board should be correct indices");
    }

    @Test
    void testEvaluatePosition() {
        Game game = new Game();
        Player xPlayer = new Player();
        xPlayer.symbol = 'X';
        Player oPlayer = new Player();
        oPlayer.symbol = 'O';

        // X Win scenarios
        game.symbol = 'X'; // Set symbol as checkState uses it internally
        char[] boardXWin = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(Game.INF, game.evaluatePosition(boardXWin, xPlayer), "Evaluate X win for X");
        assertEquals(-Game.INF, game.evaluatePosition(boardXWin, oPlayer), "Evaluate X win for O");

        // O Win scenarios
        game.symbol = 'O'; // Set symbol as checkState uses it internally
        char[] boardOWin = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(Game.INF, game.evaluatePosition(boardOWin, oPlayer), "Evaluate O win for O");
        assertEquals(-Game.INF, game.evaluatePosition(boardOWin, xPlayer), "Evaluate O win for X");

        // Draw scenario
        game.symbol = 'X'; // Symbol doesn't affect draw check
        char[] boardDraw = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'};
        assertEquals(0, game.evaluatePosition(boardDraw, xPlayer), "Evaluate draw for X");
        assertEquals(0, game.evaluatePosition(boardDraw, oPlayer), "Evaluate draw for O");

        // Playing scenario
        char[] boardPlaying = {'X', 'O', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(-1, game.evaluatePosition(boardPlaying, xPlayer), "Evaluate playing state should return -1");
        assertEquals(-1, game.evaluatePosition(boardPlaying, oPlayer), "Evaluate playing state should return -1");
    }

    // Testing MiniMax, MinMove, MaxMove requires more care due to recursion and side effects (System.out, game.symbol, game.q)
    // We'll add basic tests to call these methods and check for expected behavior in simple cases.

    @Test
    void testMiniMax_ReturnsValidMoveEmptyBoard() {
        Game game = new Game();
        Player xPlayer = new Player();
        xPlayer.symbol = 'X';

        char[] emptyBoard = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        // MiniMax on an empty board should return a valid move (1-9)
        int move = game.MiniMax(emptyBoard, xPlayer);

        assertTrue(move >= 1 && move <= 9, "MiniMax should return a valid move index (1-9) on an empty board");
        // On an empty board with MiniMax, the center (5) or corners (1,3,7,9) are usually optimal,
        // but due to the random tie-breaker, any of the 9 moves is theoretically possible depending on the implementation details.
        // A simple validity check is sufficient here for basic coverage.
    }

    @Test
    void testUtilityPrintBoard_char() {
        char[] board = {'X', 'O', ' ', ' ', 'X', ' ', ' ', 'O', 'X'};
        // This test primarily ensures the method doesn't throw exceptions.
        // Actual output verification requires mocking System.out, which is more advanced.
        assertDoesNotThrow(() -> Utility.print(board), "Utility.print(char[]) should run without errors");
    }

    @Test
    void testUtilityPrintBoard_int() {
        int[] board = {1, 2, 0, 0, 1, 0, 0, 2, 1};
        assertDoesNotThrow(() -> Utility.print(board), "Utility.print(int[]) should run without errors");
    }

    @Test
    void testUtilityPrintMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(3);
        moves.add(5);
        assertDoesNotThrow(() -> Utility.print(moves), "Utility.print(ArrayList<Integer>) should run without errors");
    }
}

