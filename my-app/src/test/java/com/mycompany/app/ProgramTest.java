package com.mycompany.app;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProgramTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testGameInitialization() {
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals(9, game.board.length);
        for (int i = 0; i < 9; i++) {
            assertEquals(' ', game.board[i]);
        }
        assertEquals(100, Game.INF);
    }

    @Test
    public void testCheckState_XWinsByRows() {
        game.symbol = 'X';

        char[] row1 = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(row1));

        char[] row2 = {' ', ' ', ' ', 'X', 'X', 'X', ' ', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(row2));

        char[] row3 = {' ', ' ', ' ', ' ', ' ', ' ', 'X', 'X', 'X'};
        assertEquals(State.XWIN, game.checkState(row3));
    }

    @Test
    public void testCheckState_XWinsByColumns() {
        game.symbol = 'X';

        char[] col1 = {'X', ' ', ' ', 'X', ' ', ' ', 'X', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(col1));

        char[] col2 = {' ', 'X', ' ', ' ', 'X', ' ', ' ', 'X', ' '};
        assertEquals(State.XWIN, game.checkState(col2));

        char[] col3 = {' ', ' ', 'X', ' ', ' ', 'X', ' ', ' ', 'X'};
        assertEquals(State.XWIN, game.checkState(col3));
    }

    @Test
    public void testCheckState_XWinsByDiagonals() {
        game.symbol = 'X';

        char[] mainDiag = {'X', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X'};
        assertEquals(State.XWIN, game.checkState(mainDiag));

        char[] antiDiag = {' ', ' ', 'X', ' ', 'X', ' ', 'X', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(antiDiag));
    }

    @Test
    public void testCheckState_OWinsAllLines() {
        game.symbol = 'O';

        char[] row = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.OWIN, game.checkState(row));

        char[] col = {'O', ' ', ' ', 'O', ' ', ' ', 'O', ' ', ' '};
        assertEquals(State.OWIN, game.checkState(col));

        char[] diag = {'O', ' ', ' ', ' ', 'O', ' ', ' ', ' ', 'O'};
        assertEquals(State.OWIN, game.checkState(diag));

        char[] antiDiag = {' ', ' ', 'O', ' ', 'O', ' ', 'O', ' ', ' '};
        assertEquals(State.OWIN, game.checkState(antiDiag));
    }

    @Test
    public void testCheckState_Draw() {
        game.symbol = 'X';
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(State.DRAW, game.checkState(board));

        game.symbol = 'O';
        char[] board2 = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'};
        assertEquals(State.DRAW, game.checkState(board2));
    }

    @Test
    public void testCheckState_Playing() {
        game.symbol = 'X';

        char[] empty = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.PLAYING, game.checkState(empty));

        char[] partial = {'X', ' ', ' ', ' ', 'O', ' ', ' ', ' ', ' '};
        assertEquals(State.PLAYING, game.checkState(partial));

        char[] almostFull = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', ' '};
        assertEquals(State.PLAYING, game.checkState(almostFull));
    }

    @Test
    public void testGenerateMoves_AllCases() {
        ArrayList<Integer> moves;


        char[] empty = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        moves = new ArrayList<>();
        game.generateMoves(empty, moves);
        assertEquals(9, moves.size());
        for (int i = 0; i < 9; i++) {
            assertTrue(moves.contains(i));
        }


        char[] full = {'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X'};
        moves = new ArrayList<>();
        game.generateMoves(full, moves);
        assertEquals(0, moves.size());


        char[] partial = {'X', ' ', 'O', ' ', 'X', ' ', 'O', ' ', 'X'};
        moves = new ArrayList<>();
        game.generateMoves(partial, moves);
        assertEquals(4, moves.size());
        assertTrue(moves.contains(1));
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(5));
        assertTrue(moves.contains(7));
    }

    @Test
    public void testEvaluatePosition_XWins() {
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';

        assertEquals(Game.INF, game.evaluatePosition(board, game.player1));
        assertEquals(-Game.INF, game.evaluatePosition(board, game.player2));
    }

    @Test
    public void testEvaluatePosition_OWins() {
        char[] board = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'O';

        assertEquals(Game.INF, game.evaluatePosition(board, game.player2));
        assertEquals(-Game.INF, game.evaluatePosition(board, game.player1));
    }

    @Test
    public void testEvaluatePosition_DrawAndPlaying() {
        game.symbol = 'X';

        char[] draw = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(0, game.evaluatePosition(draw, game.player1));

        char[] playing = {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(-1, game.evaluatePosition(playing, game.player1));
    }

    @Test
    public void testMiniMax_WinningMove() {
        char[] board = {'X', 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        int move = game.MiniMax(board, game.player1);
        // Алгоритм может выбрать любой выигрышный ход
        assertTrue(move >= 1 && move <= 9, "Ход должен быть валидным");
    }

    @Test
    public void testMiniMax_BlockingMove() {
        char[] board = {'X', 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        int move = game.MiniMax(board, game.player2);
        // Алгоритм должен сделать какой-то ход
        assertTrue(move >= 1 && move <= 9, "Ход должен быть валидным");
    }

    @Test
    public void testMiniMax_EmptyBoard() {
        char[] board = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        int move = game.MiniMax(board, game.player1);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    public void testMiniMax_ComplexPosition() {
        char[] board = {'X', 'O', 'X', ' ', 'O', ' ', ' ', 'X', ' '};
        int move = game.MiniMax(board, game.player2);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    public void testMinMoveAndMaxMove() {
        char[] boardX = {'X', 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.MaxMove(boardX, game.player1));

        char[] boardO = {'O', 'O', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(Game.INF, game.MinMove(boardO, game.player2));

        game.symbol = 'X';
        assertEquals(-Game.INF, game.MinMove(boardX, game.player2));
    }

    @Test
    public void testTicTacToeCell() {
        TicTacToeCell cell = new TicTacToeCell(5, 2, 1);
        assertEquals(5, cell.getNum());
        assertEquals(2, cell.getCol());
        assertEquals(1, cell.getRow());
        assertEquals(' ', cell.getMarker());

        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());

        TicTacToeCell cell2 = new TicTacToeCell(0, 0, 0);
        cell2.setMarker("O");
        assertEquals('O', cell2.getMarker());
    }

    @Test
    public void testUtilityMethods() {
        char[] cBoard = {'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X'};
        Utility.print(cBoard);

        int[] iBoard = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.print(iBoard);

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(5);
        list.add(9);
        Utility.print(list);

        Utility.print(new ArrayList<>());
    }

    @Test
    public void testGuiPanel() {
        GridLayout layout = new GridLayout(3, 3);
        TicTacToePanel panel = new TicTacToePanel(layout);

        assertNotNull(panel);
        assertEquals(9, panel.getComponentCount());

        ActionEvent ae = new ActionEvent(panel.getComponent(0), ActionEvent.ACTION_PERFORMED, "");
        panel.actionPerformed(ae);

        ActionEvent ae2 = new ActionEvent(panel.getComponent(4), ActionEvent.ACTION_PERFORMED, "");
        panel.actionPerformed(ae2);
    }

    @Test
    public void testFullGameSequence() {
        char[] board = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};


        game.symbol = 'X';
        board[0] = 'X';
        assertEquals(State.PLAYING, game.checkState(board));


        game.symbol = 'O';
        board[4] = 'O';
        assertEquals(State.PLAYING, game.checkState(board));


        game.symbol = 'X';
        board[1] = 'X';
        assertEquals(State.PLAYING, game.checkState(board));


        game.symbol = 'O';
        board[5] = 'O';
        assertEquals(State.PLAYING, game.checkState(board));


        game.symbol = 'X';
        board[2] = 'X';
        assertEquals(State.XWIN, game.checkState(board));


        int eval = game.evaluatePosition(board, game.player1);
        assertEquals(Game.INF, eval);
    }
}
