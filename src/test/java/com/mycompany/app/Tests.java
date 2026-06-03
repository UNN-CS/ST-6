package com.mycompany.app;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {
    @Test
    public void testGameInitialization() {
        Game game = new Game();
        assertNotNull(game.player1);
        assertNotNull(game.player2);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals(State.PLAYING, game.state);
        assertEquals(9, game.board.length);
        for (char cell : game.board) {
            assertEquals(' ', cell);
        }
    }

    @Test
    public void testGenerateMovesOnEmptyBoard() {
        Game game = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size());
        for (int i = 0; i < 9; i++) {
            assertEquals(i, moves.get(i));
        }
    }

    @Test
    public void testGenerateMovesOnPartialBoard() {
        Game game = new Game();
        game.board[0] = 'X';
        game.board[4] = 'O';
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(7, moves.size());
        assertFalse(moves.contains(0));
        assertFalse(moves.contains(4));
    }

    @Test
    public void testCheckStateHorizontalWinX() {
        Game game = new Game();
        game.symbol = 'X';
        char[] mockBoard = {
                'X', 'X', 'X',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        State result = game.checkState(mockBoard);
        assertEquals(State.XWIN, result);
    }

    @Test
    public void testCheckStateVerticalWinO() {
        Game game = new Game();
        game.symbol = 'O';
        char[] mockBoard = {
                'O', ' ', ' ',
                'O', ' ', ' ',
                'O', ' ', ' '
        };
        State result = game.checkState(mockBoard);
        assertEquals(State.OWIN, result);
    }

    @Test
    public void testCheckStateDiagonalWin() {
        Game game = new Game();
        game.symbol = 'X';
        char[] mockBoard = {
                'X', ' ', ' ',
                ' ', 'X', ' ',
                ' ', ' ', 'X'
        };
        State result = game.checkState(mockBoard);
        assertEquals(State.XWIN, result);
    }

    @Test
    public void testCheckStateDraw() {
        Game game = new Game();
        game.symbol = 'O';
        char[] mockBoard = {
                'X', 'O', 'X',
                'X', 'O', 'O',
                'O', 'X', 'O'
        };
        State result = game.checkState(mockBoard);
        assertEquals(State.DRAW, result);
    }

    @Test
    public void testEvaluatePositionWin() {
        Game game = new Game();
        game.symbol = 'X';
        char[] mockBoard = {
                'X', 'X', 'X',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        int evaluation = game.evaluatePosition(mockBoard, game.player1);
        assertEquals(Game.INF, evaluation);
    }

    @Test
    public void testEvaluatePositionLoss() {
        Game game = new Game();
        game.symbol = 'X';
        char[] mockBoard = {
                'X', 'X', 'X',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        int evaluation = game.evaluatePosition(mockBoard, game.player2);
        assertEquals(-Game.INF, evaluation);
    }

    @Test
    public void testMiniMaxCalculatesMove() {
        Game game = new Game();
        char[] mockBoard = {
                'X', 'O', 'X',
                ' ', 'X', ' ',
                'O', ' ', ' '
        };
        int bestMove = game.MiniMax(mockBoard, game.player2);
        assertTrue(bestMove >= 1 && bestMove <= 9);
    }

    @Test
    public void testTicTacToeCellBehavior() {
        TicTacToeCell cell = new TicTacToeCell(5, 1, 2);
        assertEquals(5, cell.getNum());
        assertEquals(1, cell.getCol());
        assertEquals(2, cell.getRow());
        assertEquals(' ', cell.getMarker());
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    public void testUtilityPrintMethods() {
        char[] charBoard = new char[9];
        int[] intBoard = new int[9];
        ArrayList<Integer> moveList = new ArrayList<>();
        moveList.add(1);
        assertDoesNotThrow(() -> Utility.print(charBoard));
        assertDoesNotThrow(() -> Utility.print(intBoard));
        assertDoesNotThrow(() -> Utility.print(moveList));
    }

    @Test
    public void testEvaluatePositionDrawForCoverage() {
        Game game = new Game();
        char[] drawBoard = {
                'X', 'O', 'X',
                'X', 'O', 'O',
                'O', 'X', 'O'
        };
        int evaluation = game.evaluatePosition(drawBoard, game.player1);
        assertEquals(0, evaluation);
    }

    @Test
    public void testMiniMaxTerminalState() {
        Game game = new Game();
        char[] winBoard = {
                'X', 'X', 'X',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        int move = game.MiniMax(winBoard, game.player2);
        assertTrue(move >= -1);
    }
}