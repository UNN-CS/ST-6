package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class ProgramTest {

    @Test void testPlayerFields() {
        Player p = new Player();
        p.symbol = 'X'; p.move = 5; p.selected = true; p.win = true;
        assertEquals('X', p.symbol);
        assertEquals(5, p.move);
        assertTrue(p.selected);
        assertTrue(p.win);
    }

    @Test void testStateEnum() {
        assertEquals(4, State.values().length);
        assertEquals(State.PLAYING, State.PLAYING);
        assertEquals(State.XWIN, State.XWIN);
        assertEquals(State.OWIN, State.OWIN);
        assertEquals(State.DRAW, State.DRAW);
    }

    @Test void testGameConstructor() {
        Game g = new Game();
        assertNotNull(g.board);
        assertEquals(9, g.board.length);
        assertEquals(State.PLAYING, g.state);
        assertEquals('X', g.player1.symbol);
        assertEquals('O', g.player2.symbol);
        assertEquals(100, Game.INF);
        for (char c : g.board) assertEquals(' ', c);
    }

    @Test void testXWinsAllPatterns() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.XWIN, g.checkState(new char[]{'X','X','X',' ',' ',' ',' ',' ',' '})); // top
        assertEquals(State.XWIN, g.checkState(new char[]{' ',' ',' ','X','X','X',' ',' ',' '})); // mid
        assertEquals(State.XWIN, g.checkState(new char[]{' ',' ',' ',' ',' ',' ','X','X','X'})); // bot
        assertEquals(State.XWIN, g.checkState(new char[]{'X',' ',' ','X',' ',' ','X',' ',' '})); // left
        assertEquals(State.XWIN, g.checkState(new char[]{' ','X',' ',' ','X',' ',' ','X',' '})); // mid
        assertEquals(State.XWIN, g.checkState(new char[]{' ',' ','X',' ',' ','X',' ',' ','X'})); // right
        assertEquals(State.XWIN, g.checkState(new char[]{'X',' ',' ',' ','X',' ',' ',' ','X'})); // diag1
        assertEquals(State.XWIN, g.checkState(new char[]{' ',' ','X',' ','X',' ','X',' ',' '})); // diag2
    }

    @Test void testOWins() {
        Game g = new Game();
        g.symbol = 'O';
        assertEquals(State.OWIN, g.checkState(new char[]{'O','O','O',' ',' ',' ',' ',' ',' '}));
        assertEquals(State.OWIN, g.checkState(new char[]{'O',' ',' ','O',' ',' ','O',' ',' '}));
        assertEquals(State.OWIN, g.checkState(new char[]{'O',' ',' ',' ','O',' ',' ',' ','O'}));
    }

    @Test void testDrawAndPlaying() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.DRAW, g.checkState(new char[]{'X','O','X','X','O','O','O','X','X'}));
        assertEquals(State.PLAYING, g.checkState(new char[]{'X','O',' ',' ',' ',' ',' ',' ',' '}));
        assertEquals(State.PLAYING, g.checkState(new char[]{' ',' ',' ',' ',' ',' ',' ',' ',' '}));
    }

    @Test void testGenerateMoves() {
        Game g = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        
        g.generateMoves(new char[]{'X',' ','O',' ',' ',' ',' ',' ',' '}, moves);
        assertEquals(7, moves.size());
        
        moves.clear();
        g.generateMoves(new char[]{'X','O','X','O','X','O','X','O','X'}, moves);
        assertEquals(0, moves.size());
        
        moves.clear();
        g.generateMoves(new char[]{' ',' ',' ',' ',' ',' ',' ',' ',' '}, moves);
        assertEquals(9, moves.size());
    }

    @Test void testEvaluatePosition() {
        Game g = new Game();
        Player px = new Player(); px.symbol = 'X';
        Player po = new Player(); po.symbol = 'O';
        
        g.symbol = 'X';
        assertEquals(Game.INF, g.evaluatePosition(new char[]{'X','X','X',' ',' ',' ',' ',' ',' '}, px));
        assertEquals(-Game.INF, g.evaluatePosition(new char[]{'X','X','X',' ',' ',' ',' ',' ',' '}, po));
        
        g.symbol = 'O';
        assertEquals(Game.INF, g.evaluatePosition(new char[]{'O','O','O',' ',' ',' ',' ',' ',' '}, po));
        assertEquals(-Game.INF, g.evaluatePosition(new char[]{'O','O','O',' ',' ',' ',' ',' ',' '}, px));
        
        assertEquals(0, g.evaluatePosition(new char[]{'X','O','X','X','O','O','O','X','X'}, px));
        assertEquals(-1, g.evaluatePosition(new char[]{'X','O',' ',' ',' ',' ',' ',' ',' '}, px));
    }

    @Test void testMinimaxReturnsValidMove() {
        Game g = new Game();
        Player p = new Player(); p.symbol = 'X';
        char[] board = {' ',' ',' ',' ',' ',' ',' ',' ',' '};
        g.symbol = 'X';
        int move = g.MiniMax(board, p);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test void testMinimaxOnPartiallyFilledBoard() {
        Game g = new Game();
        Player p = new Player(); p.symbol = 'O';
        char[] board = {'X',' ',' ',' ','O',' ',' ',' ','X'};
        g.symbol = 'X';
        int move = g.MiniMax(board, p);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test void testBoardDirectModification() {
        Game g = new Game();
        g.board[4] = 'X';
        g.board[0] = 'O';
        assertEquals('X', g.board[4]);
        assertEquals('O', g.board[0]);
    }

    @Test void testSymbolAndStateFields() {
        Game g = new Game();
        g.symbol = 'O';
        g.state = State.XWIN;
        assertEquals('O', g.symbol);
        assertEquals(State.XWIN, g.state);
    }

    @Test void testPlayerReferencesInGame() {
        Game g = new Game();
        assertEquals('X', g.player1.symbol);
        assertEquals('O', g.player2.symbol);
        g.player1.symbol = 'O';
        assertEquals('O', g.player1.symbol);
    }

    @Test void testCellConstructorAndGetters() {
        TicTacToeCell cell = new TicTacToeCell(4, 1, 1);
        assertEquals(4, cell.getNum());
        assertEquals(1, cell.getRow());
        assertEquals(1, cell.getCol());
        assertEquals(' ', cell.getMarker());
    }

    @Test void testCellSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertEquals("X", cell.getText());
        assertFalse(cell.isEnabled());
    }

    @Test void testCellSetMarkerO() {
        TicTacToeCell cell = new TicTacToeCell(2, 2, 0);
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test void testCellInitiallyEnabled() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertTrue(cell.isEnabled());
    }

    @Test void testUtilityPrintDoesNotThrow() {
        char[] charBoard = {'X', 'O', ' ', ' ', 'X', ' ', ' ', ' ', 'O'};
        int[] intBoard = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(0); moves.add(4); moves.add(8);
        
        assertDoesNotThrow(() -> Utility.print(charBoard));
        assertDoesNotThrow(() -> Utility.print(intBoard));
        assertDoesNotThrow(() -> Utility.print(moves));
    }

    @Test void testIntegrationBoardModification() {
        Game g = new Game();
        g.board[0] = 'X'; g.board[1] = 'X'; g.board[2] = 'X';
        g.symbol = 'X';
        assertEquals(State.XWIN, g.checkState(g.board));
    }

    @Test void testIntegrationGenerateAndEvaluate() {
        Game g = new Game();
        g.board[0] = 'X'; g.board[4] = 'O';
        ArrayList<Integer> moves = new ArrayList<>();
        g.generateMoves(g.board, moves);
        assertEquals(7, moves.size());
        
        Player p = new Player(); p.symbol = 'X';
        g.symbol = 'X';
        assertEquals(-1, g.evaluatePosition(g.board, p));
    }

    @Test void testEmptyBoardIsPlaying() {
        Game g = new Game();
        char[] empty = {' ',' ',' ',' ',' ',' ',' ',' ',' '};
        g.symbol = 'X';
        assertEquals(State.PLAYING, g.checkState(empty));
    }

    @Test void testTwoInRowNotWin() {
        Game g = new Game();
        char[] two = {'X','X',' ',' ',' ',' ',' ',' ',' '};
        g.symbol = 'X';
        assertEquals(State.PLAYING, g.checkState(two));
    }

    @Test void testINFConstantValue() {
        assertEquals(100, Game.INF);
    }

    @Test void testBoardLengthConstant() {
        Game g = new Game();
        assertEquals(9, g.board.length);
    }

    @Test void testPlayerDefaultValues() {
        Player p = new Player();
        p.symbol = 'X';
        assertEquals('X', p.symbol);
    }

    @Test void testGameCPlayerField() {
    Game g = new Game();
    g.cplayer = g.player1;
    assertNotNull(g.cplayer, "cplayer должно быть доступно для установки");
    assertEquals('X', g.cplayer.symbol);
    
    g.cplayer = g.player2;
    assertEquals('O', g.cplayer.symbol);
}

    @Test void testMinimaxCounterQ() {
        Game g = new Game();
        Player p = new Player(); p.symbol = 'X';
        char[] board = {'X', 'O', ' ', ' ', 'X', ' ', ' ', ' ', 'O'};
        g.symbol = 'X';
        g.q = 0;
        int move = g.MiniMax(board.clone(), p);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test void testMinimaxMultipleCalls() {
        Game g = new Game();
        Player p = new Player(); p.symbol = 'X';
        g.symbol = 'X';
        char[] board = {' ',' ',' ',' ',' ',' ',' ',' ',' '};
        
        for (int i = 0; i < 3; i++) {
            int move = g.MiniMax(board.clone(), p);
            assertTrue(move >= 1 && move <= 9);
        }
    }

    @Test void testEvaluatePositionNonTerminal() {
        Game g = new Game();
        Player p = new Player(); p.symbol = 'X';
        char[] board = {'X','O','X','O',' ',' ',' ',' ',' '};
        g.symbol = 'X';
        assertEquals(-1, g.evaluatePosition(board, p));
    }

    @Test void testGenerateMovesWithSymbols() {
        Game g = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        char[] board = {'X','O','X','O','X','O',' ',' ',' '};
        g.generateMoves(board, moves);
        assertEquals(3, moves.size());
        assertTrue(moves.contains(6));
        assertTrue(moves.contains(7));
        assertTrue(moves.contains(8));
    }

    @Test void testCheckStateAfterBoardChange() {
        Game g = new Game();
        g.board[0] = 'O'; g.board[1] = 'O'; g.board[2] = 'O';
        g.symbol = 'O';
        assertEquals(State.OWIN, g.checkState(g.board));
    }

    @Test void testPlayerSymbolChange() {
        Player p = new Player();
        p.symbol = 'X';
        assertEquals('X', p.symbol);
        p.symbol = 'O';
        assertEquals('O', p.symbol);
    }

    @Test void testGamePlayer1AndPlayer2Different() {
        Game g = new Game();
        assertNotEquals(g.player1, g.player2);
        assertEquals('X', g.player1.symbol);
        assertEquals('O', g.player2.symbol);
    }

    @Test void testCellDifferentPositions() {
        TicTacToeCell c1 = new TicTacToeCell(0, 0, 0);
        TicTacToeCell c2 = new TicTacToeCell(8, 2, 2);
        assertNotEquals(c1.getNum(), c2.getNum());
        assertNotEquals(c1.getRow(), c2.getRow());
        assertNotEquals(c1.getCol(), c2.getCol());
    }

    @Test void testCellMarkerRoundtrip() {
        TicTacToeCell cell = new TicTacToeCell(5, 2, 1);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
    }
}
