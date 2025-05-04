package com.mycompany.app;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;

public class ProgramTest {

    @Test
    public void testInitialGameState() {
        Game game = new Game();
        assertEquals(State.PLAYING, game.state);
        for (char cell : game.board) {
            assertEquals(' ', cell);
        }
    }

    @Test
    public void testCheckStateXWin() {
        Game game = new Game();
        char[] board = {
                'X', 'X', 'X',
                'O', 'O', ' ',
                ' ', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateOWin() {
        Game game = new Game();
        char[] board = {
                'X', 'X', ' ',
                'O', 'O', 'O',
                'X', ' ', ' '
        };
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateDraw() {
        Game game = new Game();
        char[] board = {
                'X', 'O', 'X',
                'X', 'O', 'O',
                'O', 'X', 'X'
        };
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    public void testCheckStateStillPlaying() {
        Game game = new Game();
        char[] board = {
                'X', 'O', 'X',
                'X', 'O', 'O',
                ' ', 'X', 'X'
        };
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    public void testGenerateMoves() {
        Game game = new Game();
        char[] board = {
                'X', 'O', ' ',
                ' ', 'X', ' ',
                'O', 'X', 'O'
        };
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(3, moves.size());
        assertTrue(moves.contains(2));
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(5));
    }

    @Test
    public void testEvaluateWinForPlayerX() {
        Game game = new Game();
        Player x = new Player();
        x.symbol = 'X';
        char[] board = {
                'X', 'X', 'X',
                'O', 'O', ' ',
                ' ', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(board, x));
    }

    @Test
    public void testEvaluateWinForOpponent() {
        Game game = new Game();
        Player x = new Player();
        x.symbol = 'X';
        char[] board = {
                'O', 'O', 'O',
                'X', 'X', ' ',
                ' ', ' ', ' '
        };
        game.symbol = 'O';
        assertEquals(-Game.INF, game.evaluatePosition(board, x));
    }

    @Test
    public void testEvaluateDraw() {
        Game game = new Game();
        Player x = new Player();
        x.symbol = 'X';
        char[] board = {
                'X', 'O', 'X',
                'X', 'O', 'O',
                'O', 'X', 'X'
        };
        game.symbol = 'X';
        assertEquals(0, game.evaluatePosition(board, x));
    }

    @Test
    public void testMinimaxReturnsValidMove() {
        Game game = new Game();
        game.player1.symbol = 'X';
        game.player2.symbol = 'O';
        Player ai = game.player2;
        char[] board = {
                'X', 'O', 'X',
                ' ', 'O', ' ',
                'X', ' ', ' '
        };
        game.symbol = ai.symbol;
        int move = game.MiniMax(board.clone(), ai);
        assertTrue(move >= 1 && move <= 9);
        assertEquals(' ', board[move - 1]);
    }

    @Test
    public void testMinMoveAndMaxMoveReturnReasonableValues() {
        Game game = new Game();
        Player x = new Player();
        x.symbol = 'X';
        char[] board = {
                'X', 'O', ' ',
                ' ', 'X', ' ',
                'O', ' ', ' '
        };
        game.symbol = x.symbol;
        int max = game.MaxMove(board.clone(), x);
        int min = game.MinMove(board.clone(), x);
        assertTrue(max <= Game.INF);
        assertTrue(min >= -Game.INF);
    }

    @Test
    public void testMiniMaxWithOnePossibleMove() {
        Game game = new Game();
        game.player1.symbol = 'X';
        game.player2.symbol = 'O';
        Player ai = game.player2;
        char[] board = {
                'X', 'O', 'X',
                'O', 'X', 'X',
                'X', ' ', 'O'
        };
        game.symbol = ai.symbol;
        int move = game.MiniMax(board.clone(), ai);
        assertEquals(8, move);
    }

    @Test
    public void testMinMaxWithEmptyBoard() {
        Game game = new Game();
        Player x = new Player();
        x.symbol = 'X';
        char[] board = {
                ' ', ' ', ' ',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        game.symbol = x.symbol;
        int move = game.MiniMax(board.clone(), x);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    public void testGenerateMovesNoMovesLeft() {
        Game game = new Game();
        char[] board = {
                'X', 'O', 'X',
                'X', 'O', 'O',
                'O', 'X', 'X'
        };
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(0, moves.size());
    }

    @Test
    public void testPanelInitialization() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        assertNotNull(panel);
        assertEquals(9, panel.getComponentCount());
    }

    @Test
    public void testCellsAreTicTacToeCells() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            assertTrue(panel.getComponent(i) instanceof TicTacToeCell);
        }
    }

    @Test
    public void testCellClickSetsMarker() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        TicTacToeCell cell = (TicTacToeCell) panel.getComponent(0);
        assertEquals(' ', cell.getMarker());

        cell.doClick();
        assertEquals('X', cell.getMarker());
    }

    @Test
    public void printCharArrayTest() {
        char[] cells = {'O', 'X', 'O', ' ', 'X', ' ', 'X', ' ', 'O'};
        Utility.print(cells);
    }

    @Test
    public void printIntegerArrayTest() {
        int[] data = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        Utility.print(data);
    }

    @Test
    public void printMoveListTest() {
        ArrayList<Integer> positions = new ArrayList<>();
        positions.add(4);
        positions.add(7);
        positions.add(9);
        Utility.print(positions);
    }
    @Test
    public void testMainExecutionNoCrash() {
        boolean noException = true;
        try {
            Program.main(new String[0]);
        } catch (Throwable t) {
            noException = false;
        }
        assertTrue(noException);
    }
}
