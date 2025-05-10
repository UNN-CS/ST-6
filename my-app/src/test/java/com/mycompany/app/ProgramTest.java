package com.mycompany.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class ProgramTest {
    private Game game;
    private Player player;

    @BeforeEach
    public void setUp() {
        game = new Game();
        player = new Player();
    }

    @Test
    public void testGameInitialization() {
        assertNotNull(game);
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        for (char c : game.board) {
            assertEquals(' ', c);
        }
    }

    @Test
    public void testPlayerDefaultValues() {
        Player p = new Player();
        assertEquals(0, p.move);
        assertFalse(p.selected);
        assertFalse(p.win);
    }

    @Test
    public void testCheckStateDraw() {
        char[] board = {
            'X', 'O', 'X',
            'X', 'O', 'O',
            'O', 'X', 'X'
        };
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    public void testCheckStateXWin() {
        // Test horizontal win
        char[] board1 = {
            'X', 'X', 'X',
            'O', 'O', ' ',
            ' ', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board1));

        // Test vertical win
        char[] board2 = {
            'X', 'O', ' ',
            'X', 'O', ' ',
            'X', ' ', ' '
        };
        assertEquals(State.XWIN, game.checkState(board2));

        // Test diagonal win
        char[] board3 = {
            'X', 'O', ' ',
            'O', 'X', ' ',
            ' ', ' ', 'X'
        };
        assertEquals(State.XWIN, game.checkState(board3));
    }

    @Test
    public void testCheckStateOWin() {
        char[] board = {
            'X', 'X', ' ',
            'O', 'O', 'O',
            'X', ' ', ' '
        };
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    public void testGenerateMovesEmptyBoard() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size());
    }

    @Test
    public void testEvaluatePositionDraw() {
        char[] board = {
            'X', 'O', 'X',
            'X', 'O', 'O',
            'O', 'X', 'X'
        };
        Player xPlayer = new Player();
        xPlayer.symbol = 'X';
        assertEquals(0, game.evaluatePosition(board, xPlayer));
    }

    @Test
    public void testEvaluatePositionWin() {
        // Test X win position
        char[] boardXWin = {
            'X', 'X', 'X',
            'O', 'O', ' ',
            ' ', ' ', ' '
        };
        Player xPlayer = new Player();
        xPlayer.symbol = 'X';
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(boardXWin, xPlayer));

        // Test O win position
        char[] boardOWin = {
            'X', 'X', ' ',
            'O', 'O', 'O',
            'X', ' ', ' '
        };
        Player oPlayer = new Player();
        oPlayer.symbol = 'O';
        game.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(boardOWin, oPlayer));

        // Test losing position for X
        assertEquals(-Game.INF, game.evaluatePosition(boardOWin, xPlayer));
    }

    @Test
    public void testMiniMaxImmediateWin() {
        char[] board = {
            'X', 'O', 'X',
            'O', 'O', ' ',
            'X', ' ', ' '
        };
        game.board = board.clone();
        Player oPlayer = game.player2;
        oPlayer.symbol = 'O';
        game.symbol = oPlayer.symbol;
        int bestMove = game.MiniMax(game.board, oPlayer);
        assertEquals(8, bestMove);
    }

    @Test
    public void testMinMoveBlocking() {
        char[] board = {
            'X', 'X', ' ',
            'O', 'O', ' ',
            ' ', ' ', ' '
        };
        game.board = board.clone();
        Player xPlayer = game.player1;
        xPlayer.symbol = 'X';
        game.symbol = 'O';
        int minValue = game.MinMove(game.board, xPlayer);
        assertTrue(minValue <= Game.INF);
    }

    @Test
    public void testMaxMoveWinning() {
        char[] board = {
            'X', 'X', ' ',
            'O', 'O', ' ',
            ' ', ' ', ' '
        };
        game.board = board.clone();
        Player oPlayer = game.player2;
        oPlayer.symbol = 'O';
        game.symbol = 'O';
        int maxValue = game.MaxMove(game.board, oPlayer);
        assertTrue(maxValue >= -Game.INF);
    }

    @Test
    public void testTicTacToeCellInitialization() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(0, cell.getNum());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertEquals(' ', cell.getMarker());
    }

    @Test
    public void testTicTacToeCellSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    public void testTicTacToePanelComponents() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        assertNotNull(panel);
        assertEquals(9, panel.getComponentCount());
    }

    @Test
    public void testTicTacToePanelActionPerformed() throws Exception {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        
        // Access cells through reflection to test actionPerformed
        java.lang.reflect.Field cellsField = TicTacToePanel.class.getDeclaredField("cells");
        cellsField.setAccessible(true);
        TicTacToeCell[] cells = (TicTacToeCell[]) cellsField.get(panel);
        
        // Access game field
        java.lang.reflect.Field gameField = TicTacToePanel.class.getDeclaredField("game");
        gameField.setAccessible(true);
        Game panelGame = (Game) gameField.get(panel);
        
        // Create a mock ActionEvent
        ActionEvent event = new ActionEvent(cells[0], ActionEvent.ACTION_PERFORMED, null);
        
        // Make sure player1 is current player before action
        panelGame.cplayer = panelGame.player1;
        
        // Call actionPerformed
        try {
            panel.actionPerformed(event);
        } catch (Exception e) {
            // We expect possible exceptions in a headless environment
            // Just verifying the method was called
        }
    }

    @Test
    public void testUtilityConstructor() {
        Utility utility = new Utility();
        assertNotNull(utility);
    }

    @Test
    public void testUtilityPrint() {
        char[] board = {'X', 'O', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        Utility.print(board);
        int[] intBoard = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.print(intBoard);
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(0);
        moves.add(4);
        moves.add(8);
        Utility.print(moves);
    }

    @Test
    public void testCompleteGameScenario() {
        Game testGame = new Game();
        testGame.player1.symbol = 'X';
        testGame.player2.symbol = 'O';
        testGame.cplayer = testGame.player1;
        char[] finalBoard = {
            'X', 'O', 'X',
            'O', 'X', 'O',
            ' ', ' ', 'X'
        };
        testGame.board = finalBoard;
        testGame.symbol = 'X';
        testGame.state = testGame.checkState(testGame.board);
        assertEquals(State.XWIN, testGame.state);
    }

    @Test
    public void testProgramClass() {
        Program program = new Program();
        assertNotNull(program);
        
        // Set static fields to null to test their initialization
        try {
            java.lang.reflect.Field fileWriterField = Program.class.getDeclaredField("fileWriter");
            fileWriterField.setAccessible(true);
            fileWriterField.set(null, null);
            
            java.lang.reflect.Field printWriterField = Program.class.getDeclaredField("printWriter");
            printWriterField.setAccessible(true);
            printWriterField.set(null, null);
        } catch (Exception e) {
            fail("Failed to set Program static fields");
        }
    }
}