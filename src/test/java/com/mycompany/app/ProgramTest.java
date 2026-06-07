package com.mycompany.app;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProgramTest {

    @BeforeAll
    static void enableHeadless() {
        System.setProperty("java.awt.headless", "true");
    }

    // -------------------- Game tests --------------------
    @Test
    void gameConstructorInitializesCorrectly() {
        Game g = new Game();
        assertEquals('X', g.player1.symbol);
        assertEquals('O', g.player2.symbol);
        assertEquals(State.PLAYING, g.state);
        for (char c : g.board) assertEquals(' ', c);
    }

    @ParameterizedTest
    @MethodSource("winningPatterns")
    void checkStateDetectsWins(String pattern, char symbolForState, State expected) {
        Game g = new Game();
        g.symbol = symbolForState;
        char[] board = boardFromString(pattern);
        assertEquals(expected, g.checkState(board));
    }

    static Stream<Arguments> winningPatterns() {
        String[] xWins = {
                "XXX......", "...XXX...", "......XXX",
                "X..X..X..", ".X..X..X.", "..X..X..X",
                "X...X...X", "..X.X.X.."
        };
        Stream<Arguments> xArgs = Arrays.stream(xWins)
                .map(p -> Arguments.of(p, 'X', State.XWIN));
        Stream<Arguments> oArgs = Arrays.stream(xWins)
                .map(p -> p.replace('X', 'O'))
                .map(p -> Arguments.of(p, 'O', State.OWIN));
        return Stream.concat(xArgs, oArgs);
    }

    @Test
    void checkStateReturnsPlayingWhenNotFinished() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.PLAYING, g.checkState(boardFromString("X.O......")));
    }

    @Test
    void checkStateReturnsDrawWhenFull() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.DRAW, g.checkState(boardFromString("XOXXOOOXX")));
    }

    @Test
    void generateMovesListsEmptyCells() {
        Game g = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        g.generateMoves(boardFromString("X.O..OXX."), moves);
        assertEquals(Arrays.asList(1, 3, 4, 8), moves);
    }

    @Test
    void evaluatePositionReturnsWinForPlayer() {
        Game g = new Game();
        char[] board = boardFromString("XXX......");
        assertEquals(Game.INF, g.evaluatePosition(board, g.player1));
        assertEquals(-Game.INF, g.evaluatePosition(board, g.player2));
    }

    @Test
    void evaluatePositionReturnsDraw() {
        Game g = new Game();
        assertEquals(0, g.evaluatePosition(boardFromString("XOXXOOOXX"), g.player1));
    }

    @Test
    void evaluatePositionReturnsMinusOneForUnfinished() {
        Game g = new Game();
        assertEquals(-1, g.evaluatePosition(boardFromString("X.O......"), g.player1));
    }

    @Test
    void miniMaxReturnsWinningMoveForO() {
        Game g = new Game();
        char[] board = boardFromString("OO.XX.X..");
        int move = g.MiniMax(board, g.player2);
        assertEquals(3, move);
        assertTrue(g.q > 0);
    }

    @Test
    void miniMaxWhenOnlyOneMove() {
        Game g = new Game();
        char[] board = boardFromString("XOXOOXXO.");
        int move = g.MiniMax(board, g.player1);
        assertEquals(9, move);
    }

    @Test
    void miniMaxNoMovesReturnsZero() {
        Game g = new Game();
        assertEquals(0, g.MiniMax(boardFromString("XOXXOOOXX"), g.player1));
    }

    // -------------------- Utility tests --------------------
    @Test
    void utilityPrintsCharBoard() {
        String out = captureOutput(() -> Utility.print(boardFromString("XO.......")));
        assertTrue(out.contains("X-O- - - - - - - -"));
    }

    @Test
    void utilityPrintsIntBoard() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        String out = captureOutput(() -> Utility.print(arr));
        assertTrue(out.contains("1-2-3-4-5-6-7-8-9-"));
    }

    @Test
    void utilityPrintsMoveList() {
        ArrayList<Integer> moves = new ArrayList<>(Arrays.asList(2, 4, 8));
        String out = captureOutput(() -> Utility.print(moves));
        assertTrue(out.contains("2-4-8-"));
    }

    // -------------------- TicTacToeCell tests --------------------
    @Test
    void cellStoresCorrectData() {
        TicTacToeCell cell = new TicTacToeCell(5, 2, 1);
        assertEquals(5, cell.getNum());
        assertEquals(1, cell.getRow());
        assertEquals(2, cell.getCol());
        assertEquals(' ', cell.getMarker());
        assertTrue(cell.isEnabled());
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertEquals("X", cell.getText());
        assertFalse(cell.isEnabled());
    }

    // -------------------- TicTacToePanel tests --------------------
    @Test
    void panelCreatesNineCells() throws Exception {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        assertEquals(9, panel.getComponentCount());
        Field cellsField = TicTacToePanel.class.getDeclaredField("cells");
        cellsField.setAccessible(true);
        TicTacToeCell[] cells = (TicTacToeCell[]) cellsField.get(panel);
        for (int i = 0; i < cells.length; i++) {
            assertSame(cells[i], panel.getComponent(i));
        }
    }

    @Test
    void actionPerformedPlacesHumanAndComputerMoves() throws Exception {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        Field cellsField = TicTacToePanel.class.getDeclaredField("cells");
        cellsField.setAccessible(true);
        TicTacToeCell[] cells = (TicTacToeCell[]) cellsField.get(panel);

        panel.actionPerformed(new ActionEvent(cells[0], ActionEvent.ACTION_PERFORMED, "click"));

        assertEquals('X', cells[0].getMarker());
        long xCount = Arrays.stream(cells).filter(c -> c.getMarker() == 'X').count();
        long oCount = Arrays.stream(cells).filter(c -> c.getMarker() == 'O').count();
        assertEquals(1, xCount);
        assertEquals(1, oCount);

        Field gameField = TicTacToePanel.class.getDeclaredField("game");
        gameField.setAccessible(true);
        Game g = (Game) gameField.get(panel);
        assertEquals(State.PLAYING, g.state);
    }

    @Test
    void programMainThrowsHeadless() {
        assertThrows(HeadlessException.class, () -> Program.main(new String[0]));
    }

    // -------------------- Helpers --------------------
    private static char[] boardFromString(String s) {
        assertEquals(9, s.length());
        char[] b = new char[9];
        for (int i = 0; i < 9; i++) {
            b[i] = s.charAt(i) == '.' ? ' ' : s.charAt(i);
        }
        return b;
    }

    private static String captureOutput(Runnable r) {
        PrintStream orig = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        try {
            r.run();
        } finally {
            System.setOut(orig);
        }
        return baos.toString();
    }
}
