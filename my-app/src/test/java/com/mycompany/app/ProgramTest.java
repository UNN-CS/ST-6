package com.mycompany.app;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.GridLayout;

class ProgramTest  {

    private Game game;
    private Utility utility;
    private TicTacToeCell cell;
    private TicTacToePanel panel;

    @BeforeAll
    static void setupBeforeAll() {
    }

    @AfterAll
    static void tearDownAfterAll() {
    }

    @BeforeEach
    void setUp() {
        game = new Game();
        utility = new Utility();
        cell = new TicTacToeCell(0, 0, 0);
        panel = new TicTacToePanel(new GridLayout(3, 3));
    }

    @AfterEach
    void tearDown() {
        game = null;
        utility = null;
        cell = null;
        panel = null;
    }

    @Test
    void testCellInitialState() {
        assertEquals(' ', cell.getMarker());
    }

    @Test
    void testPrintIntArray() {
        int[] board = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.print(board);
    }

    @Test
    void testXWinState() {
        game.board = new char[]{'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void testEvaluatePosition_Draw() {
        game.board = new char[]{'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X'};
        int result = game.evaluatePosition(game.board, game.player1);
        assertEquals(0, result);
    }

    @Test
    void testGenerateMoves() {
        game.board = new char[]{'X', ' ', ' ', 'O', 'X', ' ', ' ', 'O', ' '};
        ArrayList<Integer> moveList = new ArrayList<>();
        game.generateMoves(game.board, moveList);
        assertTrue(moveList.contains(1));
        assertTrue(moveList.contains(2));
    }

    @Test
    void testMaxMoveWithNoValidMoves() {
        game.board = new char[]{'X', 'O', 'X', 'X', 'O', 'O', 'X', 'O', 'X'};
        int bestMove = game.MaxMove(game.board, game.player1);
        assertEquals(0, bestMove);
    }

    @Test
    void testCellCoordinates() {
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertEquals(0, cell.getNum());
    }

    @Test
    void testMiniMaxMoveSequence() {
        game.board = new char[]{'X', 'O', ' ', 'O', 'X', ' ', ' ', ' ', ' '};
        int move = game.MiniMax(game.board, game.player2);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    void testPlayerMove() {
        game.board = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        game.player1.move = 1;
        game.board[game.player1.move - 1] = game.player1.symbol;
        assertEquals('X', game.board[0]);
    }

    @Test
    void testPrintMoveList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(2);
        moves.add(5);
        Utility.print(moves);
    }

    @Test
    void testFullGame() {
        game.board = new char[]{'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X'};
        game.state = game.checkState(game.board);
        assertEquals(State.DRAW, game.state);
    }

    @Test
    void testMiniMaxBestMove() {
        game.board = new char[]{'X', 'O', ' ', 'O', 'X', ' ', ' ', ' ', ' '};
        Player player = game.player1;
        int bestMove = game.MiniMax(game.board, player);
        List<Integer> expectedMoves = List.of(3, 7, 9);
        assertTrue(expectedMoves.contains(bestMove),
                "Минимакс вернул неожиданный ход: " + bestMove);
    }

    @Test
    void testEvaluatePosition_XWin() {
        game.board = new char[]{'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        int result = game.evaluatePosition(game.board, game.player1);
        assertEquals(Game.INF, result);
    }

    @Test
    void testSetAndGetMarker() {
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
    }

    @Test
    void testOWinState() {
        game.board = new char[]{'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    void testMiniMaxMaxValue() {
        game.board = new char[]{'X', 'O', ' ', 'O', 'X', ' ', ' ', ' ', ' '};
        Player player = game.player2;
        int bestMove = game.MiniMax(game.board, player);
        assertEquals(9, bestMove);
    }

    @Test
    void testMaxMove() {
        game.board = new char[]{'X', 'O', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        int bestMove = game.MaxMove(game.board, game.player1);
        assertTrue(bestMove > -100);
    }

    @Test
    void testCellSetMarkerDisable() {
        cell.setMarker("X");
        assertFalse(cell.isEnabled());
    }

    @Test
    void testInitialGameState() {
        assertEquals(game.state, State.PLAYING);
        assertNotNull(game.player1);
        assertNotNull(game.player2);
        assertEquals(game.board.length, 9);
        for (char c : game.board) {
            assertEquals(c, ' ');
        }
    }

    @Test
    void testPrintBoard() {
        char[] board = new char[]{'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X'};
        Utility.print(board);
    }

    @Test
    void testDrawState() {
        game.board = new char[]{'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X'};
        assertEquals(State.DRAW, game.checkState(game.board));
    }

    // TicTacToePanel tests

    @Test
    void testPanelContainsCorrectNumberOfCells() {
        assertEquals(9, panel.getComponentCount(), "На панели должно быть 9 ячеек");
    }

    @Test
    void testAllComponentsAreTicTacToeCellInstances() {
        for (var component : panel.getComponents()) {
            assertTrue(component instanceof TicTacToeCell, "Компонент не является экземпляром TicTacToeCell");
        }
    }

    @Test
    void testCellsInitialState() {
        for (var component : panel.getComponents()) {
            TicTacToeCell cell = (TicTacToeCell) component;
            assertTrue(cell.isEnabled(), "Ячейка должна быть активной по умолчанию");
            assertEquals(' ', cell.getMarker(), "Ячейка должна быть пустой при инициализации");
        }
    }

    @Test
    void testCellClickDisablesItAndMarksIt() {
        TicTacToeCell firstCell = (TicTacToeCell) panel.getComponent(0);
        firstCell.doClick();

        char marker = firstCell.getMarker();
        assertTrue(marker == 'X' || marker == 'O', "Ячейка должна быть помечена 'X' или 'O'");

        assertFalse(firstCell.isEnabled(), "Ячейка должна стать неактивной после клика");
    }

    @Test
    void testAllCellsAreDisabledAfterGameEnds() {
        for (int i = 0; i < 9; i++) {
            TicTacToeCell cell = (TicTacToeCell) panel.getComponent(i);
            cell.setMarker(i % 2 == 0 ? "X" : "O");
            cell.setEnabled(false);
        }

        for (var component : panel.getComponents()) {
            TicTacToeCell cell = (TicTacToeCell) component;
            assertFalse(cell.isEnabled(), "Все ячейки должны быть неактивны после окончания игры");
        }
    }

    @Test
    void testClickOnDisabledCellDoesNothing() {
        TicTacToeCell firstCell = (TicTacToeCell) panel.getComponent(0);
        firstCell.setMarker("X");
        firstCell.setEnabled(false);

        boolean initialState = firstCell.isEnabled();
        firstCell.doClick();

        assertEquals(initialState, firstCell.isEnabled(), "Невозможно кликнуть по неактивной ячейке");
        assertEquals('X', firstCell.getMarker(), "Содержимое ячейки не должно измениться");
    }

    @Test
    void testCellMarkerIsCorrectAfterClick() {
        TicTacToeCell firstCell = (TicTacToeCell) panel.getComponent(0);
        firstCell.doClick();
        char marker = firstCell.getMarker();

        assertTrue(marker == 'X' || marker == 'O', "Ячейка должна быть помечена 'X' или 'O'");
    }

    @Test
    void testPanelIsEmptyAtTheStart() {
        for (var component : panel.getComponents()) {
            TicTacToeCell cell = (TicTacToeCell) component;
            assertEquals(' ', cell.getMarker(), "Ячейка должна быть пустой в начале игры");
        }
    }
}
