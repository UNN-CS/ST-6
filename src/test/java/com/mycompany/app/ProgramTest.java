package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ProgramTest {
    private String previousHeadless;

    @AfterEach
    void restoreHeadlessProperty() {
        if (previousHeadless == null) {
            System.clearProperty("java.awt.headless");
        } else {
            System.setProperty("java.awt.headless", previousHeadless);
        }
        previousHeadless = null;
    }

    @Test
    void checkStateDetectsAllBoardOutcomes() {
        Game game = new Game();

        assertEquals(State.PLAYING, game.checkState(game.board));

        char[] xWin = {'X', 'X', 'X', ' ', 'O', ' ', ' ', 'O', ' '};
        char[] oWin = {'X', 'X', ' ', 'O', 'O', 'O', 'X', ' ', 'X'};
        char[] draw = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};

        assertEquals(State.XWIN, game.checkState(xWin));
        assertEquals(State.OWIN, game.checkState(oWin));
        assertEquals(State.DRAW, game.checkState(draw));
    }

    @Test
    void generateMovesReturnsEmptySlotsInOrder() {
        Game game = new Game();
        char[] board = {'X', ' ', 'O', ' ', ' ', 'X', 'O', ' ', 'X'};
        List<Integer> moves = new ArrayList<>();

        game.generateMoves(board, moves);

        assertIterableEquals(Arrays.asList(1, 3, 4, 7), moves);
    }

    @Test
    void evaluatePositionScoresWinsLossesAndDraws() {
        Game game = new Game();
        Player x = game.player1;
        Player o = game.player2;

        char[] xWin = {'X', 'X', 'X', ' ', 'O', ' ', ' ', 'O', ' '};
        char[] oWin = {'X', 'X', ' ', 'O', 'O', 'O', 'X', ' ', 'X'};
        char[] draw = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};

        assertEquals(Game.INF, game.evaluatePosition(xWin, x));
        assertEquals(-Game.INF, game.evaluatePosition(xWin, o));
        assertEquals(Game.INF, game.evaluatePosition(oWin, o));
        assertEquals(-Game.INF, game.evaluatePosition(oWin, x));
        assertEquals(0, game.evaluatePosition(draw, x));
    }

    @Test
    void minimaxFindsImmediateWinningMove() {
        Game game = new Game();
        char[] board = {'X', 'X', ' ', 'O', 'O', 'X', 'X', 'O', 'O'};

        int move = game.MiniMax(board, game.player1);

        assertEquals(3, move);
    }

    @Test
    void minimaxBlocksForcedLoss() {
        Game game = new Game();
        char[] board = {'O', 'O', ' ', 'X', 'X', 'O', 'O', 'X', 'X'};

        int move = game.MiniMax(board, game.player1);

        assertEquals(3, move);
    }

    @Test
    void minAndMaxMovesReturnTerminalValues() {
        Game game = new Game();
        char[] xWin = {'X', 'X', 'X', ' ', 'O', ' ', ' ', 'O', ' '};
        char[] oWin = {'X', 'X', ' ', 'O', 'O', 'O', 'X', ' ', 'X'};

        assertEquals(Game.INF, game.MaxMove(xWin, game.player1));
        assertEquals(-Game.INF, game.MinMove(oWin, game.player1));
    }

    @Test
    void cellStoresMarkerAndCoordinates() {
        TicTacToeCell cell = new TicTacToeCell(4, 1, 2);

        assertEquals(' ', cell.getMarker());
        assertEquals(4, cell.getNum());
        assertEquals(2, cell.getRow());
        assertEquals(1, cell.getCol());

        cell.setMarker("X");

        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
        assertEquals("X", cell.getText());
    }

    @Test
    void panelProcessesHumanAndComputerMoves() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));

        panel.cells[0].doClick();

        long xCount = new String(panel.game.board).chars().filter(cell -> cell == 'X').count();
        long oCount = new String(panel.game.board).chars().filter(cell -> cell == 'O').count();

        assertEquals(1, xCount);
        assertEquals(1, oCount);
        assertEquals('X', panel.cells[0].getMarker());
        assertEquals(2, Arrays.stream(panel.cells).filter(cell -> !cell.isEnabled()).count());
        assertSame(panel.game.player1, panel.game.cplayer);
        assertEquals(State.PLAYING, panel.game.state);
    }

    @Test
    void mainReturnsInHeadlessMode() {
        previousHeadless = System.getProperty("java.awt.headless");
        System.setProperty("java.awt.headless", "true");

        assertDoesNotThrow(() -> Program.main(new String[0]));
    }
}
