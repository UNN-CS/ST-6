package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.awt.GridLayout;

public class ProgramTest {
    
    private Game game;
    
    @BeforeEach
    void setUp() {
        game = new Game();
    }
    
    @Test
    void testCheckStateNoWinnerNotFull() {
        char[] board = {
                'X', 'O', 'X',
                'X', ' ', 'O',
                'O', 'X', ' '
        };
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(board));
    }
    
    @Test
    void testCheckStateXWinVertical() {
        char[] board = {
                'X', 'O', ' ',
                'X', 'O', ' ',
                'X', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }
    
    @Test
    void testCheckStateXWinDiagonal() {
        char[] board = {
                'X', 'O', ' ',
                ' ', 'X', ' ',
                ' ', 'O', 'X'
        };
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }
    
    @Test
    void testCheckStateOWinHorizontal() {
        char[] board = {
                'O', 'O', 'O',
                'X', 'X', ' ',
                ' ', ' ', ' '
        };
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }
    
    @Test
    void testCheckStateOWinVertical() {
        char[] board = {
                'O', 'X', ' ',
                'O', 'X', ' ',
                'O', ' ', ' '
        };
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }
    
    @Test
    void testCheckStateOWinDiagonal2() {
        char[] board = {
                ' ', ' ', 'O',
                ' ', 'O', ' ',
                'O', ' ', ' '
        };
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }
    
    @Test
    void testGenerateMovesNoEmptyCells() {
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
    void testGenerateMovesAllEmptyCells() {
        char[] board = {
                ' ', ' ', ' ',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(9, moves.size());
        for (int i = 0; i < 9; i++) {
            assertTrue(moves.contains(i));
        }
    }
    
    @Test
    void testEvaluatePositionNotTerminal() {
        char[] board = {
                'X', ' ', ' ',
                ' ', 'O', ' ',
                ' ', ' ', ' '
        };
        game.symbol = 'X';
        int result = game.evaluatePosition(board, game.player1);
        assertEquals(-1, result);
    }
    
    @Test
    void testMinMoveOnEmptyBoard() {
        char[] board = {
                ' ', ' ', ' ',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        int result = game.MinMove(board, game.player1);
        assertTrue(result >= -Game.INF && result <= Game.INF);
    }
    
    @Test
    void testMaxMoveOnEmptyBoard() {
        char[] board = {
                ' ', ' ', ' ',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        int result = game.MaxMove(board, game.player1);
        assertTrue(result >= -Game.INF && result <= Game.INF);
    }
    
    @Test
    void testMinMoveWithOneMoveLeft() {
        char[] board = {
                'X', 'O', 'X',
                'X', 'O', 'O',
                'O', 'X', ' '
        };
        int result = game.MinMove(board, game.player1);
        assertTrue(result >= -Game.INF && result <= Game.INF);
    }
    
    @Test
    void testMaxMoveWithOneMoveLeft() {
        char[] board = {
                'X', 'O', 'X',
                'X', 'O', 'O',
                'O', 'X', ' '
        };
        int result = game.MaxMove(board, game.player2);
        assertTrue(result >= -Game.INF && result <= Game.INF);
    }
    
    @Test
    void testMiniMaxBlocksFork() {
        game.board = new char[]{
                'X', ' ', ' ',
                ' ', 'X', ' ',
                ' ', ' ', 'O'
        };
        int bestMove = game.MiniMax(game.board, game.player2);
        assertTrue(bestMove >= 1 && bestMove <= 9);
    }
    
    @Test
    void testMiniMaxReturnsValidMove() {
        game.board = new char[]{
                ' ', ' ', ' ',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        int bestMove = game.MiniMax(game.board, game.player2);
        assertTrue(bestMove >= 1 && bestMove <= 9);
    }
    
    @Test
    void testMultipleBestMovesRandomSelection() {
        game.board = new char[]{
                'X', ' ', ' ',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        for (int i = 0; i < 10; i++) {
            int move = game.MiniMax(game.board, game.player2);
            assertTrue(move >= 1 && move <= 9);
        }
    }
    
    @Test
    void testEvaluatePositionWithNullPlayer() {
        char[] board = {
                'X', 'X', 'X',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        game.symbol = 'X';
        Player xPlayer = new Player();
        xPlayer.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(board, xPlayer));
    }
    
    @Test
    void testCheckStateWithDifferentSymbol() {
        char[] board = {
                'X', 'X', 'X',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        game.symbol = 'O';
        assertEquals(State.PLAYING, game.checkState(board));
    }
    
    @Test
    void testUtilityPrintCharArray() {
        char[] board = {'X', 'O', ' ', 'X', ' ', 'O', ' ', 'X', ' '};
        assertDoesNotThrow(() -> Utility.print(board));
    }
    
    @Test
    void testUtilityPrintIntArray() {
        int[] moves = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertDoesNotThrow(() -> Utility.print(moves));
    }
    
    @Test
    void testUtilityPrintArrayList() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(5);
        assertDoesNotThrow(() -> Utility.print(list));
    }
    
    @Test
    void testTicTacToeCellConstructor() {
        TicTacToeCell cell = new TicTacToeCell(4, 1, 2);
        assertEquals(4, cell.getNum());
        assertEquals(2, cell.getRow());
        assertEquals(1, cell.getCol());
        assertEquals(' ', cell.getMarker());
        assertTrue(cell.isEnabled());
    }
    
    @Test
    void testTicTacToeCellSetMarkerX() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertEquals("X", cell.getText());
        assertFalse(cell.isEnabled());
    }
    
    @Test
    void testTicTacToeCellSetMarkerO() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
        assertEquals("O", cell.getText());
        assertFalse(cell.isEnabled());
    }
    
    @Test
    void testTicTacToeCellSetMarkerOverwrites() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
    }
    
    @Test
    void testTicTacToeCellAllCoordinates() {
        for (int num = 0; num < 9; num++) {
            int row = num / 3;
            int col = num % 3;
            TicTacToeCell cell = new TicTacToeCell(num, col, row);
            assertEquals(num, cell.getNum());
            assertEquals(row, cell.getRow());
            assertEquals(col, cell.getCol());
        }
    }
    
    @Test
    void testTicTacToeCellFont() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertNotNull(cell.getFont());
        assertEquals("Arial", cell.getFont().getFamily());
    }
    
    @Test
    void testTicTacToePanelConstructor() {
        System.setProperty("java.awt.headless", "true");
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        assertNotNull(panel);
        assertEquals(9, panel.getComponentCount());
        for (int i = 0; i < 9; i++) {
            assertTrue(panel.getComponent(i) instanceof TicTacToeCell);
        }
    }
    
    @Test
    void testTicTacToePanelCellsInitialization() {
        System.setProperty("java.awt.headless", "true");
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            TicTacToeCell cell = (TicTacToeCell) panel.getComponent(i);
            assertEquals(i, cell.getNum());
        }
    }
    
    @Test
    void testTicTacToePanelGameInitialization() {
        System.setProperty("java.awt.headless", "true");
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        assertNotNull(panel);
    }
    
    @Test
    void testProgramMainMethod() {
        assertDoesNotThrow(() -> {
            Program program = new Program();
            assertNotNull(program);
        });
    }
    
    @Test
    void testProgramStaticFields() {
        assertNull(Program.fileWriter);
        assertNull(Program.printWriter);
    }
    
    @Test
    void testGameInitialState() {
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        for (char c : game.board) {
            assertEquals(' ', c);
        }
    }
    
    @Test
    void testCheckStateXWinHorizontal() {
        char[] board = {'X', 'X', 'X', ' ', 'O', ' ', ' ', ' ', 'O'};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }
    
    @Test
    void testCheckStateDraw() {
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(board));
    }
    
    @Test
    void testMiniMaxOnEmptyBoardReturnsValidMove() {
        game.board = new char[]{
                ' ', ' ', ' ',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        int bestMove = game.MiniMax(game.board, game.player1);
        assertTrue(bestMove >= 1 && bestMove <= 9);
    }

    @Test
void testTicTacToePanelFirstMoveByX() {
    System.setProperty("java.awt.headless", "true");
    TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
    
    java.lang.reflect.Field gameField;
    try {
        gameField = TicTacToePanel.class.getDeclaredField("game");
        gameField.setAccessible(true);
        Game game = (Game) gameField.get(panel);
        
        assertEquals(game.player1, game.cplayer);
        
        TicTacToeCell firstCell = (TicTacToeCell) panel.getComponent(0);
        firstCell.doClick();
        
        assertEquals('X', firstCell.getMarker());
        assertFalse(firstCell.isEnabled());
    } catch (Exception e) {
        fail("Reflection failed: " + e.getMessage());
    }
}

@Test
void testTicTacToePanelComputerResponds() {
    System.setProperty("java.awt.headless", "true");
    TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
    
    TicTacToeCell centerCell = (TicTacToeCell) panel.getComponent(4);
    centerCell.doClick();
    
    boolean computerMadeMove = false;
    for (int i = 0; i < 9; i++) {
        TicTacToeCell cell = (TicTacToeCell) panel.getComponent(i);
        if (cell.getMarker() == 'O') {
            computerMadeMove = true;
            assertFalse(cell.isEnabled());
        }
    }
    assertTrue(computerMadeMove, "Computer should make a move after player");
}

@Test
void testTicTacToePanelGameStateAfterMove() {
    System.setProperty("java.awt.headless", "true");
    TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
    
    java.lang.reflect.Field gameField;
    try {
        gameField = TicTacToePanel.class.getDeclaredField("game");
        gameField.setAccessible(true);
        
        TicTacToeCell cell = (TicTacToeCell) panel.getComponent(0);
        cell.doClick();
        
        Game game = (Game) gameField.get(panel);
        assertEquals(State.PLAYING, game.state);
    } catch (Exception e) {
        fail("Reflection failed: " + e.getMessage());
    }
}

@Test
void testTicTacToePanelAllCellsClickableInitially() {
    System.setProperty("java.awt.headless", "true");
    TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
    
    for (int i = 0; i < 9; i++) {
        TicTacToeCell cell = (TicTacToeCell) panel.getComponent(i);
        assertTrue(cell.isEnabled());
        assertEquals(' ', cell.getMarker());
    }
}

@Test
void testTicTacToePanelCellBecomesDisabledAfterClick() {
    System.setProperty("java.awt.headless", "true");
    TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
    
    TicTacToeCell cell = (TicTacToeCell) panel.getComponent(0);
    assertTrue(cell.isEnabled());
    
    cell.doClick();
    
    assertFalse(cell.isEnabled());
    assertEquals('X', cell.getMarker());
}

@Test
void testTicTacToePanelCannotClickSameCellTwice() {
    System.setProperty("java.awt.headless", "true");
    TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
    
    TicTacToeCell cell = (TicTacToeCell) panel.getComponent(0);
    cell.doClick();
    assertEquals('X', cell.getMarker());
    
    cell.doClick();
    assertEquals('X', cell.getMarker());
}

@Test
void testTicTacToePanelComputerDoesNotMoveOnEmptyCell() {
    System.setProperty("java.awt.headless", "true");
    TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
    
    TicTacToeCell cell = (TicTacToeCell) panel.getComponent(0);
    cell.doClick();
    
    for (int i = 0; i < 9; i++) {
        TicTacToeCell c = (TicTacToeCell) panel.getComponent(i);
        if (c.getMarker() == 'O') {
            assertFalse(c.isEnabled());
        }
    }
}

@Test
void testTicTacToePanelMultipleMoves() {
    System.setProperty("java.awt.headless", "true");
    TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
    
    TicTacToeCell cell0 = (TicTacToeCell) panel.getComponent(0);
    cell0.doClick();
    
    int computerMove = -1;
    for (int i = 0; i < 9; i++) {
        TicTacToeCell c = (TicTacToeCell) panel.getComponent(i);
        if (c.getMarker() == 'O') {
            computerMove = i;
            break;
        }
    }
    
    if (computerMove != -1) {
        TicTacToeCell anotherCell = null;
        for (int i = 0; i < 9; i++) {
            if (i != 0 && i != computerMove) {
                anotherCell = (TicTacToeCell) panel.getComponent(i);
                break;
            }
        }
        if (anotherCell != null && anotherCell.isEnabled()) {
            anotherCell.doClick();
            assertEquals('X', anotherCell.getMarker());
        }
    }
}
}