package com.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.util.ArrayList;

public class GameTest {

    @Test
    public void testTicTacToeGrid() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        assertNotNull(panel);
        assertEquals(9, panel.getComponentCount());
    }

    @Test
    public void testPrintArrayBoard() {
        int[] board = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.print(board);
    }

    @Test
    public void testMiniMaxForWinningMove() {
        Game game = new Game();
        char[] board = {'X', 'X', ' ', 'O', 'O', ' ', ' ', ' ', ' '};
        game.player1.symbol = 'X';
        int move = game.MiniMax(board, game.player1);
        assertEquals(3, move, "MiniMax should select the winning move");
    }

    @Test
    public void testMiniMaxForBlockingMove() {
        Game game = new Game();
        char[] board = {'X', 'X', ' ', 'O', 'O', ' ', ' ', ' ', ' '};
        game.player2.symbol = 'O';
        int move = game.MiniMax(board, game.player2);
        assertEquals(3, move, "MiniMax should select the blocking move");
    }


    @Test
    public void testPlayerSymbolAssignment() {
        Game game = new Game();
        assertEquals('X', game.player1.symbol, "Player 1 should have the 'X' symbol");
        assertEquals('O', game.player2.symbol, "Player 2 should have the 'O' symbol");
    }

    @Test
    public void testRandomMoveSelection() {
        Game game = new Game();
        char[] boardRandomMove = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(boardRandomMove, moves);
        int move = game.MiniMax(boardRandomMove, game.player1);
        assertTrue(moves.contains(move), "Random move should be one of the available moves");
    }

    @Test
    public void testPrintArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(2);
        moves.add(3);
        Utility.print(moves);
    }

    @Test
    public void testStateCheck() {
        Game game = new Game();
        char[] boardXWin = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        char[] boardOWin = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        char[] boardDraw = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        char[] boardPlaying = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};

        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(boardXWin));

        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(boardOWin));

        assertEquals(State.DRAW, game.checkState(boardDraw));
        assertEquals(State.PLAYING, game.checkState(boardPlaying));
    }

    @Test
    public void testMoveGeneration() {
        Game game = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.generateMoves(board, moves);
        assertEquals(6, moves.size());
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(4));
    }

    @Test
    public void testPositionEvaluation() {
        Game game = new Game();
        char[] boardXWin = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        char[] boardDraw = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};

        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(boardXWin, game.player1));
        assertEquals(0, game.evaluatePosition(boardDraw, game.player1));
    }

    @Test
    public void testMiniMax() {
        Game game = new Game();
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.player2.symbol = 'O';
        int move = game.MiniMax(board, game.player2);
        assertTrue(move >= 1 && move <= 9);
    }


    @Test
    public void testStateGameInit() {
        Game game = new Game();
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals(' ', game.board[0]);
    }

    @Test
    public void testSymbolsPlayerInit() {
        Game game = new Game();
        assertEquals('X', game.player1.symbol, "Player 1 should be 'X'");
        assertEquals('O', game.player2.symbol, "Player 2 should be 'O'");
    }

    @Test
    public void testEmptyBoardInit() {
        Game game = new Game();
        for (char cell : game.board) {
            assertEquals(' ', cell, "All cells should be initially empty");
        }
    }

    @Test
    public void testStateOngoingCheck() {
        Game game = new Game();
        char[] boardOngoing = {'X', 'O', 'X', 'O', 'X', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(boardOngoing));
    }

    @Test
    public void testOngoingPosEvaluation() {
        Game game = new Game();
        char[] boardOngoing = {'X', 'O', 'X', 'O', 'X', ' ', ' ', ' ', ' '};
        assertEquals(-1, game.evaluatePosition(boardOngoing, game.player1));
    }

    @Test
    public void testEndgameMaxMove() {
        Game game = new Game();
        char[] boardXWin = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        int maxMoveValue = game.MaxMove(boardXWin, game.player1);
        assertEquals(Game.INF, maxMoveValue);
    }

    @Test
    public void testEndgameMinMove() {
        Game game = new Game();
        char[] boardOWin = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        int minMoveValue = game.MinMove(boardOWin, game.player2);
        assertEquals(Game.INF, minMoveValue);
    }

    @Test
    public void testBoardInitialization() {
        Game game = new Game();
        for (char cell : game.board) {
            assertEquals(' ', cell, "All cells should be initially empty");
        }
    }

    @Test
    public void testStateCheckDiagonalWin() {
        Game game = new Game();
        char[] boardDiagonalWin = {'X', ' ', 'O', ' ', 'X', ' ', 'O', ' ', 'X'};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(boardDiagonalWin), "Board should be recognized as a diagonal win for player X");
    }

    @Test
    public void testStateCheckColumnWin() {
        Game game = new Game();
        char[] boardColumnWin = {'X', ' ', ' ', 'X', ' ', ' ', 'X', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(boardColumnWin), "Board should be recognized as a column win for player X");
    }

    @Test
    public void testMiniMaxForBlockingFork() {
        Game game = new Game();
        char[] board = {'X', 'X', ' ', ' ', 'O', ' ', ' ', ' ', ' '};
        game.player2.symbol = 'O';
        int move = game.MiniMax(board, game.player2);
        assertEquals(3, move, "MiniMax should select a move that blocks a fork");
    }

}
