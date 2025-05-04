package com.example.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class ProgramTest {
    private Game game;
    private TicTacToeCell cell;
    private TicTacToePanel panel;

    @BeforeEach
    public void setUp() {
        game = new Game();
        cell = new TicTacToeCell(0, 0, 0);
        panel = new TicTacToePanel(new java.awt.GridLayout(3, 3));
    }

    @Test
    public void testInitialGameState() {
        assertEquals(State.PLAYING, game.state, "Game should start in PLAYING state");
        assertEquals('X', game.player1.symbol, "Player 1 should be X");
        assertEquals('O', game.player2.symbol, "Player 2 should be O");
    }

    @Test
    public void testBoardInitialization() {
        for (char c : game.board) {
            assertEquals(' ', c, "Board should be initialized with empty spaces");
        }
    }

    @Test
    public void testXWinHorizontal() {
        game.board[0] = game.board[1] = game.board[2] = 'X';
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board), "X should win with horizontal line");
    }

    @Test
    public void testOWinVertical() {
        game.board[0] = game.board[3] = game.board[6] = 'O';
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(game.board), "O should win with vertical line");
    }

    @Test
    public void testDraw() {
        game.board[0] = 'X'; game.board[1] = 'X'; game.board[2] = 'O';
        game.board[3] = 'O'; game.board[4] = 'X'; game.board[5] = 'X';
        game.board[6] = 'X'; game.board[7] = 'O'; game.board[8] = 'O';
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(game.board), "Game should be a draw");
    }

    @Test
    public void testPlayingState() {
        game.board[0] = 'X';
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(game.board), "Game should still be in PLAYING state");
    }

    @Test
    public void testGenerateMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.board[0] = 'X';
        game.board[1] = 'O';
        game.generateMoves(game.board, moves);
        assertEquals(7, moves.size(), "Should generate 7 possible moves");
        assertFalse(moves.contains(0), "Move list should not include occupied position 0");
        assertFalse(moves.contains(1), "Move list should not include occupied position 1");
    }

    @Test
    public void testEvaluatePositionXWin() {
        game.board[0] = game.board[1] = game.board[2] = 'X';
        game.symbol = 'X';
        int score = game.evaluatePosition(game.board, game.player1);
        assertEquals(Game.INF, score, "X should get +INF for winning position");
    }

    @Test
    public void testEvaluatePositionOLoss() {
        game.board[0] = game.board[1] = game.board[2] = 'X';
        game.symbol = 'X';
        int score = game.evaluatePosition(game.board, game.player2);
        assertEquals(-Game.INF, score, "O should get -INF for losing position");
    }

    @Test
    public void testEvaluatePositionDraw() {
        game.board[0] = 'X'; game.board[1] = 'X'; game.board[2] = 'O';
        game.board[3] = 'O'; game.board[4] = 'X'; game.board[5] = 'X';
        game.board[6] = 'X'; game.board[7] = 'O'; game.board[8] = 'O';
        game.symbol = 'X';
        int score = game.evaluatePosition(game.board, game.player1);
        assertEquals(0, score, " RAW should return 0");
    }

    @Test
    public void testMiniMaxMove() {
        game.board[0] = 'X';
        game.board[1] = 'O';
        int move = game.MiniMax(game.board, game.player2);
        assertTrue(move >= 1 && move <= 9, "Minimax should return a valid move");
    }

    @Test
    public void testCellSetMarker() {
        cell.setMarker("X");
        assertEquals('X', cell.getMarker(), "Cell marker should be set to X");
        assertFalse(cell.isEnabled(), "Cell should be disabled after setting marker");
    }

    @Test
    public void testCellProperties() {
        assertEquals(0, cell.getNum(), "Cell number should be 0");
        assertEquals(0, cell.getRow(), "Cell row should be 0");
        assertEquals(0, cell.getCol(), "Cell column should be 0");
    }

    @Test
    public void testPanelCellCreation() {
        assertEquals(9, panel.getComponentCount(), "Panel should have 9 cells");
        for (int i = 0; i < 9; i++) {
            assertTrue(panel.getComponent(i) instanceof TicTacToeCell, "Component should be TicTacToeCell");
        }
    }

    @Test
    public void testActionPerformed() {
        TicTacToeCell testCell = (TicTacToeCell) panel.getComponent(0);
        testCell.doClick();
        assertEquals('X', testCell.getMarker(), "Cell should be marked with X after click");
    }
}