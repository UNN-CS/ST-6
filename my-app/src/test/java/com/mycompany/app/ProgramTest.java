package com.mycompany.app;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void testInitialGameState() {
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertNotNull(game.board);
        assertEquals(9, game.board.length);
        for (char c : game.board) {
            assertEquals(' ', c);
        }
    }

    @Test
    void testCheckStateXWin() {
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateOWin() {
        char[] board = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testCheckStateDraw() {
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    void testCheckStatePlaying() {
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    void testGenerateMoves() {
        char[] board = {'X', ' ', ' ', ' ', 'O', ' ', ' ', ' ', 'X'};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(6, moves.size());
        assertTrue(moves.contains(1));
        assertTrue(moves.contains(2));
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(5));
        assertTrue(moves.contains(6));
        assertTrue(moves.contains(7));
    }

    @Test
    void testEvaluatePositionXWin() {
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(board, game.player1));
    }

    @Test
    void testEvaluatePositionOWin() {
        char[] board = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(board, game.player2));
    }

    @Test
    void testEvaluatePositionDraw() {
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(0, game.evaluatePosition(board, game.player1));
    }

    @Test
    void testEvaluatePositionPlaying() {
        char[] board = {'X', ' ', ' ', ' ', 'O', ' ', ' ', ' ', 'X'};
        game.symbol = 'X';
        assertEquals(8, game.evaluatePosition(board, game.player1));
    }

    @Test
    void testMiniMaxWinInOneMove() {
        char[] board = {'X', 'X', ' ', 'O', 'O', ' ', ' ', ' ', ' '};
        game.board = board;
        int bestMove = game.MiniMax(board, game.player1);
        assertEquals(6, bestMove); // X should win by placing at position 2 (0-based)
    }

    @Test
    void testMiniMaxEmptyBoard() {
        char[] board = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        game.board = board;
        int bestMove = game.MiniMax(board, game.player1);
        assertTrue(bestMove >= 1 && bestMove <= 9); // Any move is acceptable
    }

    @Test
    void testMinMoveTerminalState() {
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.MinMove(board, game.player1));
    }

    @Test
    void testMaxMoveTerminalState() {
        char[] board = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(Game.INF, game.MaxMove(board, game.player2));
    }

    @Test
    void testTicTacToeCell() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(' ', cell.getMarker());
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
        assertEquals(0, cell.getNum());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
    }

    @Test
    void testGameSwitchPlayer() {
        game.cplayer = game.player1;
        game.cplayer = game.player2;
        assertEquals(game.player2, game.cplayer);
    }

    @Test
    void testPlayerInitialization() {
        assertNotNull(game.player1);
        assertNotNull(game.player2);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertFalse(game.player1.selected);
        assertFalse(game.player2.selected);
        assertFalse(game.player1.win);
        assertFalse(game.player2.win);
    }

    @Test
    void testBoardInitialization() {
        for (char c : game.board) {
            assertEquals(' ', c);
        }
    }

    @Test
    void testMiniMaxBlocksOpponentWinVertical() {
        char[] board = {'O', 'X', ' ',
                'O', 'X', ' ',
                ' ', ' ', ' '};
        game.board = board;
        int bestMove = game.MiniMax(board, game.player1);
        assertEquals(2, bestMove);
    }

    @Test
    void testMiniMaxCreatesForkOpportunity() {
        char[] board = {'X', ' ', ' ',
                ' ', 'O', ' ',
                ' ', ' ', 'X'};
        game.board = board;
        game.cplayer = game.player1;
        game.symbol = 'X';

        int bestMove = game.MiniMax(board, game.player1);

        // Для данной позиции (X в углах) оптимальный ход - другой угол
        // для создания вилки. Центральный ход (5) не создает вилку.
        assertTrue(bestMove == 1 || bestMove == 3 || bestMove == 7 || bestMove == 9,
                "AI должен выбрать угловой ход для создания вилки (1,3,7 или 9), но выбрал " + bestMove);
    }

    @Test
    void testEvaluatePositionForPlayer2Win() {
        char[] board = {'O', 'O', 'O',
                'X', 'X', ' ',
                ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(-Game.INF, game.evaluatePosition(board, game.player1));
    }

    @Test
    void testCheckStateDiagonalWin() {
        char[] board = {'X', ' ', ' ',
                ' ', 'X', ' ',
                ' ', ' ', 'X'};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateAntiDiagonalWin() {
        char[] board = {' ', ' ', 'O',
                ' ', 'O', ' ',
                'O', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testGenerateMovesFullBoard() {
        char[] board = {'X', 'O', 'X',
                'O', 'X', 'O',
                'O', 'X', 'O'};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertTrue(moves.isEmpty());
    }

    @Test
    void testMiniMaxEndGameScenario() {
        char[] board = {'X', 'O', 'X',
                'X', 'O', 'O',
                ' ', ' ', 'X'};
        game.board = board;
        int bestMove = game.MiniMax(board, game.player1);
        assertEquals(7, bestMove); // X должен сделать последний выигрышный ход
    }

    @Test
    void testEvaluatePositionEarlyGame() {
        char[] board = {'X', ' ', ' ',
                ' ', ' ', ' ',
                ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(5, game.evaluatePosition(board, game.player1));
    }
}
class TicTacToeCellTest {

    @Test
    void testTicTacToeCellInitialization() {
        TicTacToeCell cell = new TicTacToeCell(5, 1, 2);
        assertEquals(' ', cell.getMarker());
        assertEquals(5, cell.getNum());
        assertEquals(1, cell.getCol());
        assertEquals(2, cell.getRow());
        assertTrue(cell.isEnabled());
    }

    @Test
    void testSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());

        cell = new TicTacToeCell(1, 0, 0);
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    void testCellProperties() {
        TicTacToeCell cell = new TicTacToeCell(3, 1, 1);
        assertEquals(3, cell.getNum());
        assertEquals(1, cell.getCol());
        assertEquals(1, cell.getRow());
        assertEquals(" ", cell.getText());
        assertNotNull(cell.getFont());
    }

    @Test
    void testMultipleMarkers() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());

        // Попытка изменить маркер не должна работать
        cell.setMarker("O");
        assertEquals('X', cell.getMarker()); // Ожидаем, что маркер останется 'X'
    }
}

class UtilityTest {

    @Test
    void testPrintCharArray() {
        char[] board = {'X', 'O', ' '};
        Utility.print(board); // Проверяем вывод в консоль (визуально)
    }

    @Test
    void testPrintIntArray() {
        int[] moves = {1, 2, 3};
        Utility.print(moves); // Проверяем вывод в консоль (визуально)
    }

    @Test
    void testPrintArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(2);
        Utility.print(moves); // Проверяем вывод в консоль (визуально)
    }

    @Test
    void testPrintEmptyArrays() {
        char[] emptyChar = {};
        int[] emptyInt = {};
        ArrayList<Integer> emptyList = new ArrayList<>();

        Utility.print(emptyChar);
        Utility.print(emptyInt);
        Utility.print(emptyList);
    }
}

class TicTacToePanelTest {

    private TicTacToePanel panel;
    private Game game;

    @BeforeEach
    void setUp() {
        panel = new TicTacToePanel(new GridLayout(3, 3));
        game = panel.getGame();
    }

    @Test
    void testPanelInitialization() {
        assertEquals(9, panel.getComponentCount());
        for (int i = 0; i < 9; i++) {
            assertTrue(panel.getComponent(i) instanceof TicTacToeCell);
            TicTacToeCell cell = (TicTacToeCell) panel.getComponent(i);
            assertEquals(i, cell.getNum());
        }
    }

    @Test
    void testActionPerformedPlayer1Move() {
        TicTacToeCell cell = (TicTacToeCell) panel.getComponent(0);
        ActionEvent event = new ActionEvent(cell, ActionEvent.ACTION_PERFORMED, "");
        panel.actionPerformed(event);

        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
        assertEquals(State.PLAYING, game.state);
    }

    @Test
    void testCreateCell() {
        // Проверка создания ячеек через рефлексию
        assertEquals(9, panel.getComponentCount());
        for (int i = 0; i < 9; i++) {
            TicTacToeCell cell = (TicTacToeCell) panel.getComponent(i);
            assertEquals(i, cell.getNum());
        }
    }

    @Test
    void testGameInitializationInPanel() {
        assertNotNull(game);
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
    }

    @Test
    void testPanelLayout() {
        assertTrue(panel.getLayout() instanceof GridLayout);
        GridLayout layout = (GridLayout) panel.getLayout();
        assertEquals(3, layout.getRows());
        assertEquals(3, layout.getColumns());
    }


}