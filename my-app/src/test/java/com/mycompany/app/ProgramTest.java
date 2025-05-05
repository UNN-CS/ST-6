package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.GridLayout;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgramTest {
    private Game ticTacToeGame;
    private Player p1;
    private Player p2;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream standardOutputStream = System.out;

    @BeforeEach
    public void setUp() {
        ticTacToeGame = new Game();
        p1 = new Player();
        p1.symbol = 'X';
        p2 = new Player();
        p2.symbol = 'O';
    }

    @Test
    public void testGameInitialization() {
        assertNotNull(ticTacToeGame);
        assertEquals(ticTacToeGame.state, State.PLAYING);
        assertEquals(ticTacToeGame.player1.symbol, 'X');
        assertEquals(ticTacToeGame.player2.symbol, 'O');
        assertArrayEquals(ticTacToeGame.board, new char[]{' ', ' ', ' ',
                                                  ' ', ' ', ' ',
                                                  ' ', ' ', ' '});
    }

    @Test
    public void testCheckStatePlaying() {
        assertEquals(State.PLAYING, ticTacToeGame.checkState(new char[]{' ', ' ', ' ',
                                                                 ' ', ' ', ' ',
                                                                 ' ', ' ', ' '}));

        assertEquals(State.PLAYING, ticTacToeGame.checkState(new char[]{'X', 'O', 'X',
                                                                 'O', 'O', 'X',
                                                                 'X', 'O', ' '}));

        assertEquals(State.PLAYING, ticTacToeGame.checkState(new char[]{' ', 'X', 'O',
                                                                 'O', 'O', 'X',
                                                                 'X', 'O', 'X'}));

        assertEquals(State.PLAYING, ticTacToeGame.checkState(new char[]{'O', 'X', 'X',
                                                                 'X', 'O', 'O',
                                                                 ' ', 'O', 'X'}));

        assertEquals(State.PLAYING, ticTacToeGame.checkState(new char[]{'O', 'X', ' ',
                                                                 'X', 'O', 'O',
                                                                 'X', 'O', 'X'}));

        assertEquals(State.PLAYING, ticTacToeGame.checkState(new char[]{'X', 'O', 'X',
                                                                 ' ', 'X', 'O',
                                                                 'O', 'X', 'O'}));

        assertEquals(State.PLAYING, ticTacToeGame.checkState(new char[]{'O', ' ', 'X',
                                                                 'X', 'X', 'O',
                                                                 'O', 'O', 'X'}));

        assertEquals(State.PLAYING, ticTacToeGame.checkState(new char[]{'O', 'X', 'O',
                                                                 'O', 'X', ' ',
                                                                 'X', 'O', 'X'}));

        assertEquals(State.PLAYING, ticTacToeGame.checkState(new char[]{'X', 'O', 'O',
                                                                 'O', 'X', 'X',
                                                                 'X', ' ', 'O'}));

        assertEquals(State.PLAYING, ticTacToeGame.checkState(new char[]{'O', 'O', 'X',
                                                                 'X', ' ', 'O',
                                                                 'X', 'X', 'O'}));

    }

    @Test
    public void testCheckStateXWin() {
        ticTacToeGame.symbol = 'X';
        assertEquals(State.XWIN, ticTacToeGame.checkState(new char[]{'X', 'X', 'X',
                                                              ' ', ' ', ' ',
                                                              ' ', ' ', ' '}));

        assertEquals(State.XWIN, ticTacToeGame.checkState(new char[]{'O', 'O', ' ',
                                                              'X', 'X', 'X',
                                                              ' ', ' ', ' '}));

        assertEquals(State.XWIN, ticTacToeGame.checkState(new char[]{' ', ' ', ' ',
                                                              'O', ' ', 'O',
                                                              'X', 'X', 'X'}));

        assertEquals(State.XWIN, ticTacToeGame.checkState(new char[]{'X', ' ', 'O',
                                                              'X', 'O', ' ',
                                                              'X', ' ', ' '}));

        assertEquals(State.XWIN, ticTacToeGame.checkState(new char[]{'O', 'X', ' ',
                                                              ' ', 'X', ' ',
                                                              'O', 'X', ' '}));

        assertEquals(State.XWIN, ticTacToeGame.checkState(new char[]{' ', 'O', 'X',
                                                              ' ', ' ', 'X',
                                                              'O', ' ', 'X'}));

        assertEquals(State.XWIN, ticTacToeGame.checkState(new char[]{'O', ' ', 'X',
                                                              ' ', 'X', ' ',
                                                              'X', 'O', ' '}));

        assertEquals(State.XWIN, ticTacToeGame.checkState(new char[]{'X', ' ', ' ',
                                                              'O', 'X', ' ',
                                                              ' ', 'O', 'X'}));
    }

    @Test
    public void testCheckStateOWin() {
        ticTacToeGame.symbol = 'O';
        assertEquals(State.OWIN, ticTacToeGame.checkState(new char[]{'O', 'O', 'O',
                                                              'X', 'X', ' ',
                                                              ' ', ' ', 'X'}));

        assertEquals(State.OWIN, ticTacToeGame.checkState(new char[]{'X', ' ', 'X',
                                                              'O', 'O', 'O',
                                                              ' ', 'X', ' '}));

        assertEquals(State.OWIN, ticTacToeGame.checkState(new char[]{'X', ' ', ' ',
                                                              ' ', 'X', 'X',
                                                              'O', 'O', 'O'}));

        assertEquals(State.OWIN, ticTacToeGame.checkState(new char[]{'O', 'X', ' ',
                                                              'O', 'X', ' ',
                                                              'O', ' ', 'X'}));

        assertEquals(State.OWIN, ticTacToeGame.checkState(new char[]{'X', 'O', ' ',
                                                              ' ', 'O', 'X',
                                                              'X', 'O', ' '}));

        assertEquals(State.OWIN, ticTacToeGame.checkState(new char[]{' ', 'X', 'O',
                                                              'X', ' ', 'O',
                                                              ' ', ' ', 'O'}));

        assertEquals(State.OWIN, ticTacToeGame.checkState(new char[]{'X', ' ', 'O',
                                                              ' ', 'O', ' ',
                                                              'O', 'X', ' '}));

        assertEquals(State.OWIN, ticTacToeGame.checkState(new char[]{'O', 'X', 'X',
                                                              ' ', 'O', ' ',
                                                              'X', ' ', 'O'}));
    }

    @Test
    public void testCheckStateDraw() {
        assertEquals(State.DRAW, ticTacToeGame.checkState(new char[]{'X', 'X', 'O',
                                                              'O', 'O', 'X',
                                                              'X', 'O', 'X'}));

        assertEquals(State.DRAW, ticTacToeGame.checkState(new char[]{'X', 'X', 'O',
                                                              'O', 'X', 'X',
                                                              'X', 'O', 'O'}));

        assertEquals(State.DRAW, ticTacToeGame.checkState(new char[]{'O', 'X', 'O',
                                                              'X', 'O', 'X',
                                                              'X', 'O', 'X'}));
        assertEquals(State.DRAW, ticTacToeGame.checkState(new char[]{'O', 'X', 'X',
                                                              'X', 'O', 'O',
                                                              'X', 'O', 'X'}));
    }

    @Test
    public void testGenerateAvailableMoves() {
        ArrayList<Integer> availableMoves = new ArrayList<>();
        ticTacToeGame.generateMoves(new char[]{' ', ' ', ' ',
                                        ' ', ' ', ' ',
                                        ' ', ' ', ' '}, availableMoves);
        assertEquals(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8)), availableMoves);

        availableMoves.clear();
        ticTacToeGame.generateMoves(new char[]{'X', ' ', ' ',
                                        ' ', ' ', ' ',
                                        ' ', ' ', ' '}, availableMoves);
        assertEquals(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)), availableMoves);

        availableMoves.clear();
        ticTacToeGame.generateMoves(new char[]{'X', ' ', ' ',
                                        ' ', 'O', ' ',
                                        ' ', ' ', ' '}, availableMoves);
        assertEquals(new ArrayList<>(Arrays.asList(1, 2, 3, 5, 6, 7, 8)), availableMoves);

        availableMoves.clear();
        ticTacToeGame.generateMoves(new char[]{'X', ' ', 'X',
                                        ' ', 'O', ' ',
                                        ' ', ' ', ' '}, availableMoves);
        assertEquals(new ArrayList<>(Arrays.asList(1, 3, 5, 6, 7, 8)), availableMoves);

        availableMoves.clear();
        ticTacToeGame.generateMoves(new char[]{'X', 'O', 'X',
                                        ' ', 'O', ' ',
                                        ' ', ' ', ' '}, availableMoves);
        assertEquals(new ArrayList<>(Arrays.asList(3, 5, 6, 7, 8)), availableMoves);

        availableMoves.clear();
        ticTacToeGame.generateMoves(new char[]{'X', 'O', 'X',
                                        ' ', 'O', ' ',
                                        ' ', 'X', ' '}, availableMoves);
        assertEquals(new ArrayList<>(Arrays.asList(3, 5, 6, 8)), availableMoves);

        availableMoves.clear();
        ticTacToeGame.generateMoves(new char[]{'X', 'O', 'X',
                                        ' ', 'O', 'O',
                                        ' ', 'X', ' '}, availableMoves);
        assertEquals(new ArrayList<>(Arrays.asList(3, 6, 8)), availableMoves);

        availableMoves.clear();
        ticTacToeGame.generateMoves(new char[]{'X', 'O', 'X',
                                        'X', 'O', 'O',
                                        ' ', 'X', ' '}, availableMoves);
        assertEquals(new ArrayList<>(Arrays.asList(6, 8)), availableMoves);

        availableMoves.clear();
        ticTacToeGame.generateMoves(new char[]{'X', 'O', 'X',
                                        'X', 'O', 'O',
                                        'O', 'X', ' '}, availableMoves);
        assertEquals(new ArrayList<>(Arrays.asList(8)), availableMoves);

        availableMoves.clear();
        ticTacToeGame.generateMoves(new char[]{'X', 'O', 'X',
                                        'X', 'O', 'O',
                                        'O', 'X', 'X'}, availableMoves);
        assertEquals(new ArrayList<>(), availableMoves);

        availableMoves.clear();
        ticTacToeGame.generateMoves(new char[]{'X', ' ', 'O',
                                        'X', 'O', ' ',
                                        'X', 'O', 'X'}, availableMoves);
        assertEquals(new ArrayList<>(Arrays.asList(1, 5)), availableMoves);
    }

    @Test
    public void testEvaluatePosition() {
        ticTacToeGame.symbol = 'X';
        assertEquals(+Game.INF, ticTacToeGame.evaluatePosition(new char[]{'X', 'X', 'X',
                                                                   ' ', ' ', ' ',
                                                                   ' ', ' ', ' '}, p1));

        ticTacToeGame.symbol = 'O';
        assertEquals(+Game.INF, ticTacToeGame.evaluatePosition(new char[]{'O', 'O', 'O',
                                                                   ' ', ' ', ' ',
                                                                   ' ', ' ', ' '}, p2));

        ticTacToeGame.symbol = 'X';
        assertEquals(-Game.INF, ticTacToeGame.evaluatePosition(new char[]{'X', 'X', 'X',
                                                                   ' ', ' ', ' ',
                                                                   ' ', ' ', ' '}, p2));

        ticTacToeGame.symbol = 'O';
        assertEquals(-Game.INF, ticTacToeGame.evaluatePosition(new char[]{'O', 'O', 'O',
                                                                   ' ', ' ', ' ',
                                                                   ' ', ' ', ' '}, p1));

        ticTacToeGame.symbol = 'X';
        assertEquals(0, ticTacToeGame.evaluatePosition(new char[]{'X', 'O', 'X',
                                                           'X', 'O', 'O',
                                                           'O', 'X', 'X'}, p1));

        ticTacToeGame.symbol = 'X';
        assertEquals(-1, ticTacToeGame.evaluatePosition(new char[]{'X', 'O', 'X',
                                                            ' ', 'O', ' ',
                                                            'O', ' ', 'X'}, p1));
    }

    @Test
    public void testMinMove() {
        assertEquals(-Game.INF, ticTacToeGame.MinMove(new char[]{'X', ' ', 'O',
                                                          ' ', 'O', ' ',
                                                          ' ', ' ', 'X'}, p1));

        assertEquals(+Game.INF, ticTacToeGame.MinMove(new char[]{'X', ' ', 'O',
                                                          ' ', 'O', ' ',
                                                          'X', ' ', 'X'}, p1));

        assertEquals(0, ticTacToeGame.MinMove(new char[]{'X', ' ', ' ',
                                                  ' ', 'O', ' ',
                                                  ' ', ' ', 'X'}, p1));
    }

    @Test
    public void testMaxMove() {
        assertEquals(+Game.INF, ticTacToeGame.MaxMove(new char[]{'X', ' ', 'O',
                                                          ' ', 'O', ' ',
                                                          ' ', ' ', 'X'}, p1));

        assertEquals(-Game.INF, ticTacToeGame.MaxMove(new char[]{'X', ' ', 'O',
                                                          ' ', 'O', ' ',
                                                          'X', ' ', 'X'}, p2));

        assertEquals(0, ticTacToeGame.MaxMove(new char[]{'X', ' ', ' ',
                                                  ' ', 'O', ' ',
                                                  ' ', ' ', 'X'}, p2));
    }

    @Test
    public void testMiniMaxAlgorithm() {
        assertEquals(8, ticTacToeGame.MiniMax(new char[]{'X', ' ', 'O',
                                                  'O', 'O', ' ',
                                                  'X', ' ', 'X'}, p1));

        assertEquals(4, ticTacToeGame.MiniMax(new char[]{'X', ' ', 'O',
                                                  ' ', 'O', ' ',
                                                  'X', 'O', 'X'}, p1));

        assertEquals(2, ticTacToeGame.MiniMax(new char[]{'X', ' ', 'X',
                                                  ' ', 'O', ' ',
                                                  ' ', ' ', ' '}, p2));

        assertEquals(7, ticTacToeGame.MiniMax(new char[]{'X', ' ', ' ',
                                                  'X', 'O', ' ',
                                                  ' ', ' ', ' '}, p2));

        assertEquals(4, ticTacToeGame.MiniMax(new char[]{'X', ' ', ' ',
                                                  ' ', ' ', ' ',
                                                  ' ', ' ', ' '}, p2));
    }

    @Test
    public void testCellInitializationAndMarkerSetting() {
        TicTacToeCell boardCell = new TicTacToeCell(3, 2, 0);
        assertNotNull(boardCell);
        assertEquals(3, boardCell.getNum());
        assertEquals(2, boardCell.getCol());
        assertEquals(0, boardCell.getRow());
        assertEquals(' ', boardCell.getMarker());
        boardCell.setMarker("X");
        assertEquals('X', boardCell.getMarker());
        boardCell.setMarker("Check");
        assertEquals('C', boardCell.getMarker());
    }

    @Test
    public void testUtilityPrinting() {
        System.setOut(new PrintStream(outputStream));

        Utility.print(new char[]{'X', 'O', 'X',
                                 'X', 'O', 'O',
                                 'O', 'X', 'X'});
        String expectedBoardOutput = "X-O-X-X-O-O-O-X-X-";
        assertTrue(outputStream.toString().contains(expectedBoardOutput));
        outputStream.reset();

        Utility.print(new int[]{1, 2, 3,
                                4, 5, 6,
                                7, 8, 9});
        String expectedIntArrayOutput = "1-2-3-4-5-6-7-8-9-";
        assertTrue(outputStream.toString().contains(expectedIntArrayOutput));
        outputStream.reset();

        Utility.print(new ArrayList<>(Arrays.asList(3, 5, 6, 8)));
        String expectedListOutput = "3-5-6-8-";
        assertTrue(outputStream.toString().contains(expectedListOutput));

        System.setOut(standardOutputStream);
    }

    @Test
    public void testTicTacToePanelCreation() {
        TicTacToePanel gamePanel = new TicTacToePanel(new GridLayout(3, 3));
        assertNotNull(gamePanel);
    }
}
