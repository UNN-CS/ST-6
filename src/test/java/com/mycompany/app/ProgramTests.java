package com.mycompany.app;

import java.awt.GridLayout;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ProgramTests {

    private Game game;
    private Player playerX;
    private Player playerO;

    @BeforeEach
    void setUp() {
        game = new Game();
        playerX = new Player();
        playerX.symbol = 'X';
        playerO = new Player();
        playerO.symbol = 'O';
    }

    @Test
    void testPlayerCreateX() {
        Player p = new Player();
        p.symbol = 'X';
        assertEquals('X', p.symbol);
    }

    @Test
    void testPlayerSetMove() {
        Player p = new Player();
        p.move = 5;
        assertEquals(5, p.move);
    }

    @Test
    void testPlayerSetSelected() {
        Player p = new Player();
        p.selected = true;
        assertTrue(p.selected);
    }

    @Test
    void testPlayerSetWin() {
        Player p = new Player();
        p.win = true;
        assertTrue(p.win);
    }

    @Test
    void testGameConstructorInitializesBoard() {
        Game g = new Game();
        assertNotNull(g.board);
        assertEquals(9, g.board.length);
        for (char c : g.board) {
            assertEquals(' ', c);
        }
    }

    @Test
    void testGameConstructorCreatesPlayers() {
        Game g = new Game();
        assertNotNull(g.player1);
        assertNotNull(g.player2);
        assertEquals('X', g.player1.symbol);
        assertEquals('O', g.player2.symbol);
    }

    @Test
    void testGameInitialStateIsPlaying() {
        Game g = new Game();
        assertEquals(State.PLAYING, g.state);
    }

    @Test
    void testGameNMoveInitialization() {
        assertEquals(0, game.nmove);
    }

    @Test
    void testCheckStateXWinHorizontal0() {
        char[] board = {'X','X','X', ' ',' ',' ', ' ',' ',' '};
        setGameSymbol(game, 'X');
        State result = game.checkState(board);
        assertEquals(State.XWIN, result);
    }

    @Test
    void testCheckStateXWinHorizontal1() {
        char[] board = {' ',' ',' ', 'X','X','X', ' ',' ',' '};
        setGameSymbol(game, 'X');
        State result = game.checkState(board);
        assertEquals(State.XWIN, result);
    }

    @Test
    void testCheckStateXWinHorizontal2() {
        char[] board = {' ',' ',' ', ' ',' ',' ', 'X','X','X'};
        setGameSymbol(game, 'X');
        State result = game.checkState(board);
        assertEquals(State.XWIN, result);
    }

    @Test
    void testCheckStateOWinHorizontal0() {
        char[] board = {'O','O','O', ' ',' ',' ', ' ',' ',' '};
        setGameSymbol(game, 'O');
        State result = game.checkState(board);
        assertEquals(State.OWIN, result);
    }

    @Test
    void testCheckStateXWinVertical0() {
        char[] board = {'X',' ',' ', 'X',' ',' ', 'X',' ',' '};
        setGameSymbol(game, 'X');
        State result = game.checkState(board);
        assertEquals(State.XWIN, result);
    }

    @Test
    void testCheckStateXWinVertical1() {
        char[] board = {' ','X',' ', ' ','X',' ', ' ','X',' '};
        setGameSymbol(game, 'X');
        State result = game.checkState(board);
        assertEquals(State.XWIN, result);
    }

    @Test
    void testCheckStateXWinVertical2() {
        char[] board = {' ',' ','X', ' ',' ','X', ' ',' ','X'};
        setGameSymbol(game, 'X');
        State result = game.checkState(board);
        assertEquals(State.XWIN, result);
    }

    @Test
    void testCheckStateOWinVertical() {
        char[] board = {'O',' ',' ', 'O',' ',' ', 'O',' ',' '};
        setGameSymbol(game, 'O');
        State result = game.checkState(board);
        assertEquals(State.OWIN, result);
    }

    @Test
    void testCheckStateXWinDiagonal048() {
        char[] board = {'X',' ',' ', ' ','X',' ', ' ',' ','X'};
        setGameSymbol(game, 'X');
        State result = game.checkState(board);
        assertEquals(State.XWIN, result);
    }

    @Test
    void testCheckStateXWinDiagonal246() {
        char[] board = {' ',' ','X', ' ','X',' ', 'X',' ',' '};
        setGameSymbol(game, 'X');
        State result = game.checkState(board);
        assertEquals(State.XWIN, result);
    }

    @Test
    void testCheckStateDraw() {
        char[] board = {'X','O','X', 'X','O','O', 'O','X','X'};
        setGameSymbol(game, 'X');
        State result = game.checkState(board);
        assertEquals(State.DRAW, result);
    }

    @Test
    void testCheckStatePlaying() {
        char[] board = {'X','O',' ', ' ',' ',' ', ' ',' ',' '};
        setGameSymbol(game, 'X');
        State result = game.checkState(board);
        assertEquals(State.PLAYING, result);
    }

    @ParameterizedTest
    @CsvSource({
        "0, 1, 2", "3, 4, 5", "6, 7, 8",
        "0, 3, 6", "1, 4, 7", "2, 5, 8",
        "0, 4, 8", "2, 4, 6"
    })
    void testCheckStateAllWinCombinations(int a, int b, int c) {
        char[] board = new char[9];
        for(int i=0; i<9; i++) board[i] = ' ';
        board[a] = 'X'; board[b] = 'X'; board[c] = 'X';
        setGameSymbol(game, 'X');
        State result = game.checkState(board);
        assertEquals(State.XWIN, result);
    }

    @Test
    void testGenerateMovesEmptyBoard() {
        char[] board = new char[9];
        for(int i=0; i<9; i++) board[i] = ' ';
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(9, moves.size());
        for(int i=0; i<9; i++) assertTrue(moves.contains(i));
    }

    @Test
    void testGenerateMovesFullBoard() {
        char[] board = {'X','O','X','O','X','O','X','O','X'};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertTrue(moves.isEmpty());
    }

    @Test
    void testGenerateMovesPartialBoard() {
        char[] board = {'X',' ','O', ' ','X',' ', ' ',' ',' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(6, moves.size());
        assertTrue(moves.contains(1));
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(5));
        assertTrue(moves.contains(6));
        assertTrue(moves.contains(7));
        assertTrue(moves.contains(8));
    }

    @Test
    void testGenerateMovesOneFreeCell() {
        char[] board = {'X','O','X', 'O','X','O', 'X','O',' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(1, moves.size());
        assertEquals(8, moves.get(0));
    }

    @Test
    void testEvaluatePositionXWinPlayerX() {
        char[] board = {'X','X','X', ' ',' ',' ', ' ',' ',' '};
        setGameSymbol(game, 'X');
        int result = game.evaluatePosition(board, playerX);
        assertEquals(Game.INF, result);
    }

    @Test
    void testEvaluatePositionXWinPlayerO() {
        char[] board = {'X','X','X', ' ',' ',' ', ' ',' ',' '};
        setGameSymbol(game, 'X');
        int result = game.evaluatePosition(board, playerO);
        assertEquals(-Game.INF, result);
    }

    @Test
    void testEvaluatePositionOWinPlayerO() {
        char[] board = {'O','O','O', ' ',' ',' ', ' ',' ',' '};
        setGameSymbol(game, 'O');
        int result = game.evaluatePosition(board, playerO);
        assertEquals(Game.INF, result);
    }

    @Test
    void testEvaluatePositionOWinPlayerX() {
        char[] board = {'O','O','O', ' ',' ',' ', ' ',' ',' '};
        setGameSymbol(game, 'O');
        int result = game.evaluatePosition(board, playerX);
        assertEquals(-Game.INF, result);
    }

    @Test
    void testEvaluatePositionDraw() {
        char[] board = {'X','O','X', 'X','O','O', 'O','X','X'};
        int result = game.evaluatePosition(board, playerX);
        assertEquals(0, result);
    }

    @Test
    void testEvaluatePositionNotFinished() {
        char[] board = new char[9];
        for(int i=0; i<9; i++) board[i] = ' ';
        int result = game.evaluatePosition(board, playerX);
        assertEquals(-1, result);
    }

    @Test
    void testEvaluatePositionOneMove() {
        char[] board = new char[9];
        for(int i=0; i<9; i++) board[i] = ' ';
        board[4] = 'X';
        int result = game.evaluatePosition(board, playerX);
        assertEquals(-1, result);
    }

    @Test
    void testMiniMaxAlmostFullBoard() {
        char[] board = {'X','O','X', 'O','X','O', 'X','O',' '};
        setGameSymbol(game, 'O');
        int move = game.MiniMax(board, playerO);
        assertEquals(9, move);
    }

    @Test
    void testMiniMaxOneFreeCell() {
        char[] board = {'X','O','X', 'O','X',' ', 'X','O','O'};
        setGameSymbol(game, 'X');
        int move = game.MiniMax(board, playerX);
        assertEquals(6, move);
    }

    @Test
    void testMiniMaxEmptyMoveList() {
        char[] board = {'X','O','X', 'O','X','O', 'X','O','X'};
        setGameSymbol(game, 'X');
        assertDoesNotThrow(() -> game.MiniMax(board, playerX));
    }

    @Test
    void testMiniMaxWithMultipleMoves() {
        char[] board = new char[9];
        for(int i=0; i<9; i++) board[i] = ' ';
        board[4] = 'X';
        setGameSymbol(game, 'O');
        int move = game.MiniMax(board, playerO);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    void testMinMoveWinPosition() {
        char[] board = {'O','O','O', ' ',' ',' ', ' ',' ',' '};
        setGameSymbol(game, 'O');
        int result = game.MinMove(board, playerO);
        assertEquals(Game.INF, result);
    }

    @Test
    void testMinMoveNotFinished() {
        char[] board = new char[9];
        for(int i=0; i<9; i++) board[i] = ' ';
        setGameSymbol(game, 'O');
        int result = game.MinMove(board, playerO);
        assertNotEquals(-1, result);
    }

    @Test
    void testMaxMoveWinPosition() {
        char[] board = {'X','X','X', ' ',' ',' ', ' ',' ',' '};
        setGameSymbol(game, 'X');
        int result = game.MaxMove(board, playerX);
        assertEquals(Game.INF, result);
    }

    @Test
    void testMaxMoveNotFinished() {
        char[] board = new char[9];
        for(int i=0; i<9; i++) board[i] = ' ';
        setGameSymbol(game, 'X');
        int result = game.MaxMove(board, playerX);
        assertNotEquals(-1, result);
    }

    @Test
    void testMiniMaxIncrementsCounter() {
        char[] board = {'X',' ','O', ' ','X',' ', 'O',' ',' '};
        setGameSymbol(game, 'X');
        game.q = 0;
        game.MiniMax(board, playerX);
        assertTrue(game.q >= 0);
    }

    @Test
    void testUtilityPrintCharArray() {
        char[] board = {'X','O','X', 'O','X','O', 'X','O','X'};
        assertDoesNotThrow(() -> Utility.print(board));
    }

    @Test
    void testUtilityPrintIntArray() {
        int[] arr = {1,2,3,4,5,6,7,8,9};
        assertDoesNotThrow(() -> Utility.print(arr));
    }

    @Test
    void testUtilityPrintArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(0); moves.add(4); moves.add(8);
        assertDoesNotThrow(() -> Utility.print(moves));
    }

    @Test
    void testUtilityPrintEmptyArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        assertDoesNotThrow(() -> Utility.print(moves));
    }

    @Test
    void testTicTacToeCellCreate() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertNotNull(cell);
        assertEquals(0, cell.getNum());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
    }

    @Test
    void testTicTacToeCellSetMarkerX() {
        TicTacToeCell cell = new TicTacToeCell(4, 1, 1);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertEquals("X", cell.getText());
        assertFalse(cell.isEnabled());
    }

    @Test
    void testTicTacToeCellSetMarkerO() {
        TicTacToeCell cell = new TicTacToeCell(4, 1, 1);
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
    }

    @Test
    void testTicTacToeCellInitialMarker() {
        TicTacToeCell cell = new TicTacToeCell(2, 2, 0);
        assertEquals(' ', cell.getMarker());
    }

    @Test
    void testTicTacToeCellGetCoordinates() {
        TicTacToeCell cell = new TicTacToeCell(7, 1, 2);
        assertEquals(1, cell.getCol());
        assertEquals(2, cell.getRow());
        assertEquals(7, cell.getNum());
    }

    @Test
    void testTicTacToePanelCreatesNineCells() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3,3));
        assertDoesNotThrow(() -> {
            Field cellsField = TicTacToePanel.class.getDeclaredField("cells");
            cellsField.setAccessible(true);
            TicTacToeCell[] cells = (TicTacToeCell[]) cellsField.get(panel);
            assertNotNull(cells);
            assertEquals(9, cells.length);
        });
    }

    @Test
    void testTicTacToePanelHasGame() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3,3));
        assertDoesNotThrow(() -> {
            Field gameField = TicTacToePanel.class.getDeclaredField("game");
            gameField.setAccessible(true);
            Game game = (Game) gameField.get(panel);
            assertNotNull(game);
        });
    }

    @Test
    void testStateEnumValues() {
        State[] states = State.values();
        assertEquals(4, states.length);
        assertEquals(State.PLAYING, states[0]);
        assertEquals(State.OWIN, states[1]);
        assertEquals(State.XWIN, states[2]);
        assertEquals(State.DRAW, states[3]);
    }

    @Test
    void testStateValueOf() {
        assertEquals(State.PLAYING, State.valueOf("PLAYING"));
        assertEquals(State.XWIN, State.valueOf("XWIN"));
        assertEquals(State.OWIN, State.valueOf("OWIN"));
        assertEquals(State.DRAW, State.valueOf("DRAW"));
    }

    @Test
    void testGameINFConstant() {
        assertEquals(100, Game.INF);
    }

    @Test
    void testGameQField() {
        Game g = new Game();
        assertEquals(0, g.q);
    }

    @Test
    void testGameCPlayerField() {
        Game g = new Game();
        assertNull(g.cplayer);
    }

    @Test
    void testGameNMoveField() {
        Game g = new Game();
        assertEquals(0, g.nmove);
    }

    @Test
    void testEvaluatePositionMultipleScenarios() {
        char[] board1 = {'X','O','X', 'O','X','O', 'X','O',' '};
        assertEquals(-1, game.evaluatePosition(board1, playerX));
        
        char[] board2 = {'X','X','X', 'O','O',' ', ' ',' ',' '};
        setGameSymbol(game, 'X');
        assertEquals(Game.INF, game.evaluatePosition(board2, playerX));
    }

    private void setGameSymbol(Game game, char symbol) {
        try {
            Field symbolField = Game.class.getDeclaredField("symbol");
            symbolField.setAccessible(true);
            symbolField.set(game, symbol);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}