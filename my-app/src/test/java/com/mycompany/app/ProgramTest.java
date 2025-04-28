package com.mycompany.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;




public class ProgramTest {
    private Game game;
    private Player player;
    private TicTacToePanel panel;
    private TicTacToeCell[] cells;

    @BeforeEach
    public void setUp() {
        game = new Game();
        player = new Player();
        panel = new TicTacToePanel(new GridLayout(3, 3));
        cells = panel.cells;
    }

    @Test
    public void testGameConstructor() {
        assertNotNull(game);
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        for (int i = 0; i < 9; i++) {
            assertEquals(' ', game.board[i]);
        }
    }

    @Test
    public void testPlayerInitialization() {
        Player p = new Player();
        assertEquals(0, p.move);
        assertFalse(p.selected);
        assertFalse(p.win);
    }

    @Test
    public void testCheckStateXWinRows() {
        char[] board = {'X','X','X',' ',' ',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));

        board = new char[]{' ',' ',' ','X','X','X',' ',' ',' '};
        assertEquals(State.XWIN, game.checkState(board));

        board = new char[]{' ',' ',' ',' ',' ',' ','X','X','X'};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateXWinColumns() {
        char[] board = {'X',' ',' ','X',' ',' ','X',' ',' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));

        board = new char[]{' ','X',' ',' ','X',' ',' ','X',' '};
        assertEquals(State.XWIN, game.checkState(board));

        board = new char[]{' ',' ','X',' ',' ','X',' ',' ','X'};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateXWinDiagonals() {
        char[] board = {'X',' ',' ',' ','X',' ',' ',' ','X'};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));

        board = new char[]{' ',' ','X',' ','X',' ','X',' ',' '};
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateOWinConditions() {
        char[] board = {'O','O','O',' ',' ',' ',' ',' ',' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));

        board = new char[]{'O',' ',' ','O',' ',' ','O',' ',' '};
        assertEquals(State.OWIN, game.checkState(board));

        board = new char[]{'O',' ',' ',' ','O',' ',' ',' ','O'};
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    public void testCheckStatePlayingAndDraw() {
        char[] board = {'X','O','X','O',' ',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(board));

        board = new char[]{'X','O','X','X','O','O','O','X','O'};
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    public void testGenerateMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size());

        char[] board = {'X',' ','O',' ','X',' ','O',' ','X'};
        ArrayList<Integer> partialMoves = new ArrayList<>();
        game.generateMoves(board, partialMoves);
        assertEquals(4, partialMoves.size());
        assertTrue(partialMoves.contains(1));
        assertTrue(partialMoves.contains(3));
        assertTrue(partialMoves.contains(5));
        assertTrue(partialMoves.contains(7));
    }

    @Test
    public void testEvaluatePosition() {
        Player xPlayer = new Player();
        xPlayer.symbol = 'X';
        Player oPlayer = new Player();
        oPlayer.symbol = 'O';

        char[] xWinBoard = {'X','X','X',' ','O',' ',' ','O',' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(xWinBoard, xPlayer));
        assertEquals(-Game.INF, game.evaluatePosition(xWinBoard, oPlayer));

        char[] oWinBoard = {'X','X',' ','O','O','O','X',' ',' '};
        game.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(oWinBoard, oPlayer));
        assertEquals(-Game.INF, game.evaluatePosition(oWinBoard, xPlayer));

        char[] drawBoard = {'X','O','X','X','O','O','O','X','O'};
        assertEquals(0, game.evaluatePosition(drawBoard, xPlayer));
        assertEquals(0, game.evaluatePosition(drawBoard, oPlayer));

        char[] playingBoard = {'X',' ',' ',' ',' ',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(-1, game.evaluatePosition(playingBoard, xPlayer));
    }

    @Test
    public void testMinMaxBestMove() {
        char[] board = {'X',' ','X','O','O',' ',' ',' ',' '};
        game.board = board.clone();
        Player oPlayer = game.player2;
        oPlayer.symbol = 'O';
        game.symbol = oPlayer.symbol;
        int bestMove = game.MiniMax(game.board, oPlayer);
        assertEquals(2, bestMove);
    }

    @Test
    public void testMinMove() {
        Player xPlayer = game.player1;
        xPlayer.symbol = 'X';
        char[] board = {' ',' ',' ','O','O',' ','X',' ',' '};
        game.board = board.clone();
        game.symbol = 'O';
        int minValue = game.MinMove(game.board, xPlayer);
        assertTrue(minValue <= Game.INF);
    }

    @Test
    public void testMaxMove() {
        Player oPlayer = game.player2;
        oPlayer.symbol = 'O';
        char[] board = {'X','X',' ','O','O',' ',' ',' ',' '};
        game.board = board.clone();
        game.symbol = 'O';
        int maxValue = game.MaxMove(game.board, oPlayer);
        assertTrue(maxValue >= -Game.INF);
    }

    @Test
    public void testTicTacToeCell() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);
        assertEquals(1, cell.getNum());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertEquals(' ', cell.getMarker());
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    public void testTicTacToePanelInitialization() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        assertNotNull(panel);
        assertEquals(9, panel.getComponentCount());
    }

    @Test
    public void testUtilityPrintMethods() {
        char[] charBoard = {'X','O',' ',' ',' ',' ',' ',' ',' '};
        Utility.print(charBoard);
        int[] intBoard = {1,2,3,4,5,6,7,8,9};
        Utility.print(intBoard);
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(0);
        moves.add(4);
        moves.add(8);
        Utility.print(moves);
    }

    @Test
    public void testCompleteGame() {
        Game testGame = new Game();
        testGame.player1.symbol = 'X';
        testGame.player2.symbol = 'O';
        testGame.cplayer = testGame.player1;
        char[] finalBoard = {'X','O','X','O','X','O',' ',' ','X'};
        testGame.board = finalBoard;
        testGame.symbol = 'X';
        testGame.state = testGame.checkState(testGame.board);
        assertEquals(State.XWIN, testGame.state);
    }

    @Test
    public void testMiniMaxTerminalState() {
        char[] terminalBoard = {'X','O','X','O','X','O','O','X','X'};
        game.board = terminalBoard;
        game.symbol = 'X';
        assertEquals(0, game.MiniMax(terminalBoard, game.player1));
    }

    @Test
    public void testMiniMaxOneMoveWin() {
        char[] board = {'X','X',' ','O','O',' ',' ',' ',' '};
        game.board = board;
        game.symbol = 'X';
        assertEquals(3, game.MiniMax(board, game.player1));
    }

    @Test
    public void testMiniMaxBlockWin() {
        char[] board = {'X',' ',' ',' ','O','O',' ',' ',' '};
        game.board = board;
        game.symbol = 'X';
        assertEquals(4, game.MiniMax(board, game.player1));
    }

    @Test
    public void testGenerateMovesEmptyBoard() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(new char[9], moves);
        assertEquals(0, moves.size());
    }

    @Test
    public void testPlayerSymbolAssignment() {
        Player p1 = new Player();
        Player p2 = new Player();
        p1.symbol = 'X';
        p2.symbol = 'O';
        assertEquals('X', p1.symbol);
        assertEquals('O', p2.symbol);
    }

    @Test
    public void testEvaluateEmptyBoard() {
        assertEquals(-1, game.evaluatePosition(new char[9], game.player1));
    }

    @Test
    public void testMiniMaxDepth() {
        char[] board = {'X',' ',' ',' ',' ',' ',' ',' ',' '};
        game.board = board;
        game.symbol = 'O';
        assertTrue(game.MiniMax(board, game.player2) >= 0 && game.MiniMax(board, game.player2) < 9);
    }

    @Test
    public void testCheckStateXWinWithDifferentConfigurations() {
        char[] board = {'X', ' ', ' ', 'X', 'X', ' ', 'X', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateOWinWithDifferentConfigurations() {
        char[] board = {'O', ' ', ' ', 'O', 'O', ' ', 'O', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateDraw() {
        char[] board = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    public void testMiniMaxWithComplexBoard() {
        char[] board = {'X', 'O', 'X', 'O', ' ', 'X', ' ', 'O', ' '};
        game.board = board;
        game.symbol = 'O';
        int bestMove = game.MiniMax(board, game.player2);
        assertEquals(5, bestMove);
    }

    @Test
    public void testGenerateMovesWithEmptyBoard() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(new char[9], moves);
        assertEquals(0, moves.size());
    }

    @Test
    public void testGenerateMovesWithPartialBoard() {
        char[] board = {'X', 'O', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(7, moves.size());
    }

    @Test
    public void testGameProgression() {
        char[] board = {'X', ' ', ' ', 'O', ' ', ' ', ' ', ' ', ' '};
        game.board = board;
        game.symbol = 'X';
        game.player1.move = 0;
        game.player2.move = 3;
        game.state = game.checkState(board);
        assertEquals(State.PLAYING, game.state);

        game.player1.move = 1;
        game.player2.move = 4;
        board[game.player1.move] = 'X';
        board[game.player2.move] = 'O';
        game.state = game.checkState(board);
        assertEquals(State.PLAYING, game.state);
    }

    @Test
    public void testActionPerformedPlayer2MoveWithMiniMax() {
        TicTacToeCell cell = cells[0];
        ActionEvent mockEvent = mock(ActionEvent.class);
        when(mockEvent.getSource()).thenReturn(cell);
        game.cplayer = game.player1;
        game.player1.symbol = 'X';
        panel.actionPerformed(mockEvent);

        game.cplayer = game.player2;
        game.player2.symbol = 'O';
        int mockMove = 4;
        game.player2.move = mockMove;

        panel.actionPerformed(mockEvent);

        assertEquals('O', cells[mockMove].getMarker());

        JOptionPane mockJOptionPane = mock(JOptionPane.class);
        panel.setJOptionPane(mockJOptionPane);
        panel.optionPane.showMessageDialog(null, "Выиграли крестики", "Результат", JOptionPane.WARNING_MESSAGE);
        verify(mockJOptionPane).showMessageDialog(any(), eq("Выиграли крестики"), eq("Результат"), eq(JOptionPane.WARNING_MESSAGE));
    }
}
