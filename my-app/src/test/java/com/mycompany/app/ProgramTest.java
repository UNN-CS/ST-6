package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.GridLayout;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Arrays;

public class ProgramTest {
    private Game game;
    private Player player1;
    private Player player2;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        game = new Game();
        player1 = new Player();
        player1.symbol = 'X';
        player2 = new Player();
        player2.symbol = 'O';
    }

    @Test
    public void initializeGameTest() {
        assertNotNull(game);
        assertEquals(game.state, State.PLAYING);
        assertEquals(game.player1.symbol, 'X');
        assertEquals(game.player2.symbol, 'O');
        assertArrayEquals(game.board, new char[]{' ', ' ', ' ',
                                                ' ', ' ', ' ', 
                                                ' ', ' ', ' '});
    }

    @Test
    public void checkStatePLAYINGTest() {
        assertEquals(State.PLAYING, game.checkState(new char[]{' ', ' ', ' ',
                                                               ' ', ' ', ' ', 
                                                               ' ', ' ', ' '}));

        assertEquals(State.PLAYING, game.checkState(new char[]{'X', 'X', 'O',
                                                               'O', 'O', 'X', 
                                                               'x', 'O', ' '}));

        assertEquals(State.PLAYING, game.checkState(new char[]{' ', 'X', 'O',
                                                               'O', 'O', 'X', 
                                                               'X', 'O', 'X'}));

        assertEquals(State.PLAYING, game.checkState(new char[]{'O', 'X', 'X',
                                                               'X', 'O', 'O', 
                                                               ' ', 'O', 'X'}));

        assertEquals(State.PLAYING, game.checkState(new char[]{'O', 'X', ' ',
                                                               'X', 'O', 'O', 
                                                               'X', 'O', 'X'}));

        assertEquals(State.PLAYING, game.checkState(new char[]{'X', 'O', 'x',
                                                               ' ', 'X', 'O', 
                                                               'O', 'X', 'O'}));

        assertEquals(State.PLAYING, game.checkState(new char[]{'O', ' ', 'X',
                                                               'X', 'X', 'O', 
                                                               'O', 'O', 'X'}));

        assertEquals(State.PLAYING, game.checkState(new char[]{'O', 'X', 'O',
                                                               'O', 'X', ' ', 
                                                               'X', 'O', 'X'}));

        assertEquals(State.PLAYING, game.checkState(new char[]{'X', 'O', 'O',
                                                               'O', 'X', 'X', 
                                                               'X', ' ', 'O'}));

        assertEquals(State.PLAYING, game.checkState(new char[]{'O', 'O', 'X',
                                                               'X', ' ', 'O', 
                                                               'X', 'X', 'O'}));

    }

    @Test
    public void checkStateXWINTest() {
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(new char[]{'X', 'X', 'X',
                                                            ' ', ' ', ' ', 
                                                            ' ', ' ', ' '}));

        assertEquals(State.XWIN, game.checkState(new char[]{' ', ' ', ' ',
                                                            'X', 'X', 'X', 
                                                            ' ', ' ', ' '}));

        assertEquals(State.XWIN, game.checkState(new char[]{' ', ' ', ' ',
                                                            ' ', ' ', ' ', 
                                                            'X', 'X', 'X'}));

        assertEquals(State.XWIN, game.checkState(new char[]{'X', ' ', ' ',
                                                            'X', ' ', ' ', 
                                                            'X', ' ', ' '}));

        assertEquals(State.XWIN, game.checkState(new char[]{' ', 'X', ' ',
                                                            ' ', 'X', ' ', 
                                                            ' ', 'X', ' '}));

        assertEquals(State.XWIN, game.checkState(new char[]{' ', ' ', 'X',
                                                            ' ', ' ', 'X', 
                                                            ' ', ' ', 'X'}));

        assertEquals(State.XWIN, game.checkState(new char[]{' ', ' ', 'X',
                                                            ' ', 'X', ' ', 
                                                            'X', ' ', ' '}));

        assertEquals(State.XWIN, game.checkState(new char[]{'X', ' ', ' ',
                                                            ' ', 'X', ' ', 
                                                            ' ', ' ', 'X'}));
    }

    @Test
    public void checkStateOWINTest() {
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(new char[]{'O', 'O', 'O',
                                                            ' ', ' ', ' ', 
                                                            ' ', ' ', ' '}));

        assertEquals(State.OWIN, game.checkState(new char[]{' ', ' ', ' ',
                                                            'O', 'O', 'O', 
                                                            ' ', ' ', ' '}));

        assertEquals(State.OWIN, game.checkState(new char[]{' ', ' ', ' ',
                                                            ' ', ' ', ' ', 
                                                            'O', 'O', 'O'}));

        assertEquals(State.OWIN, game.checkState(new char[]{'O', ' ', ' ',
                                                            'O', ' ', ' ', 
                                                            'O', ' ', ' '}));

        assertEquals(State.OWIN, game.checkState(new char[]{' ', 'O', ' ',
                                                            ' ', 'O', ' ', 
                                                            ' ', 'O', ' '}));

        assertEquals(State.OWIN, game.checkState(new char[]{' ', ' ', 'O',
                                                            ' ', ' ', 'O', 
                                                            ' ', ' ', 'O'}));

        assertEquals(State.OWIN, game.checkState(new char[]{' ', ' ', 'O',
                                                            ' ', 'O', ' ', 
                                                            'O', ' ', ' '}));

        assertEquals(State.OWIN, game.checkState(new char[]{'O', ' ', ' ',
                                                            ' ', 'O', ' ', 
                                                            ' ', ' ', 'O'}));
    }

    @Test
    public void checkStateDRAWTest() {
        assertEquals(State.DRAW, game.checkState(new char[]{'X', 'X', 'O',
                                                            'O', 'O', 'X', 
                                                            'X', 'O', 'X'}));

        assertEquals(State.DRAW, game.checkState(new char[]{'X', 'X', 'O',
                                                            'O', 'X', 'X', 
                                                            'X', 'O', 'O'}));

        assertEquals(State.DRAW, game.checkState(new char[]{'O', 'X', 'O',
                                                            'X', 'O', 'X', 
                                                            'X', 'O', 'X'}));
    }

    @Test
    public void generateMovesTest() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(new char[]{' ', ' ', ' ',
                                      ' ', ' ', ' ',
                                      ' ', ' ', ' '}, moves);
        assertIterableEquals(moves, new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8)));

        moves.clear();
        game.generateMoves(new char[]{'X', ' ', ' ',
                                      ' ', ' ', ' ',
                                      ' ', ' ', ' '}, moves);
        assertIterableEquals(moves, new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)));

        moves.clear();
        game.generateMoves(new char[]{'X', ' ', ' ',
                                      ' ', 'O', ' ',
                                      ' ', ' ', ' '}, moves);
        assertIterableEquals(moves, new ArrayList<>(Arrays.asList(1, 2, 3, 5, 6, 7, 8)));
        
        moves.clear();
        game.generateMoves(new char[]{'X', ' ', 'X',
                                      ' ', 'O', ' ',
                                      ' ', ' ', ' '}, moves);
        assertIterableEquals(moves, new ArrayList<>(Arrays.asList(1, 3, 5, 6, 7, 8)));
        
        moves.clear();
        game.generateMoves(new char[]{'X', 'O', 'X',
                                      ' ', 'O', ' ',
                                      ' ', ' ', ' '}, moves);
        assertIterableEquals(moves, new ArrayList<>(Arrays.asList(3, 5, 6, 7, 8)));
        
        moves.clear();
        game.generateMoves(new char[]{'X', 'O', 'X',
                                      ' ', 'O', ' ',
                                      ' ', 'X', ' '}, moves);
        assertIterableEquals(moves, new ArrayList<>(Arrays.asList(3, 5, 6, 8)));
        
        moves.clear();
        game.generateMoves(new char[]{'X', 'O', 'X',
                                      ' ', 'O', 'O',
                                      ' ', 'X', ' '}, moves);
        assertIterableEquals(moves, new ArrayList<>(Arrays.asList(3, 6, 8)));
        
        moves.clear();
        game.generateMoves(new char[]{'X', 'O', 'X',
                                      'X', 'O', 'O',
                                      ' ', 'X', ' '}, moves);
        assertIterableEquals(moves, new ArrayList<>(Arrays.asList(6, 8)));
        
        moves.clear();
        game.generateMoves(new char[]{'X', 'O', 'X',
                                      'X', 'O', 'O',
                                      'O', 'X', ' '}, moves);
        assertIterableEquals(moves, new ArrayList<>(Arrays.asList(8)));
        
        moves.clear();
        game.generateMoves(new char[]{'X', 'O', 'X',
                                      'X', 'O', 'O',
                                      'O', 'X', 'X'}, moves);
        assertIterableEquals(moves, new ArrayList<>());
        
        moves.clear();
        game.generateMoves(new char[]{'X', ' ', 'O',
                                      'X', 'O', ' ',
                                      'X', 'O', 'X'}, moves);
        assertIterableEquals(moves, new ArrayList<>(Arrays.asList(1, 5)));
    }

    @Test
    public void evaluatePositionTest() {
        game.symbol = 'X';
        assertEquals(+Game.INF, game.evaluatePosition(new char[]{'X', 'X', 'X',
                                                                 ' ', ' ', ' ',
                                                                 ' ', ' ', ' '}, player1));

        game.symbol = 'O';
        assertEquals(+Game.INF, game.evaluatePosition(new char[]{'O', 'O', 'O',
                                                                 ' ', ' ', ' ',
                                                                 ' ', ' ', ' '}, player2));

        game.symbol = 'X';
        assertEquals(-Game.INF, game.evaluatePosition(new char[]{'X', 'X', 'X',
                                                                 ' ', ' ', ' ',
                                                                 ' ', ' ', ' '}, player2));

        game.symbol = 'O';
        assertEquals(-Game.INF, game.evaluatePosition(new char[]{'O', 'O', 'O',
                                                                 ' ', ' ', ' ',
                                                                 ' ', ' ', ' '}, player1));

        game.symbol = 'X';
        assertEquals(0, game.evaluatePosition(new char[]{'X', 'O', 'X',
                                                         'X', 'O', 'O',
                                                         'O', 'X', 'X'}, player1));

        game.symbol = 'X';
        assertEquals(-1, game.evaluatePosition(new char[]{'X', 'O', 'X',
                                                          ' ', 'O', ' ',
                                                          'O', ' ', 'X'}, player1));                        
    }

    @Test
    public void MaxMoveTest() {
        assertEquals(+Game.INF, game.MaxMove(new char[]{'X', ' ', 'O',
                                                        ' ', 'O', ' ',
                                                        ' ', ' ', 'X'}, player1));

        assertEquals(-Game.INF, game.MaxMove(new char[]{'X', ' ', 'O',
                                                        ' ', 'O', ' ',
                                                        'X', ' ', 'X'}, player2));

        assertEquals(0, game.MaxMove(new char[]{'X', ' ', ' ',
                                                ' ', 'O', ' ',
                                                ' ', ' ', 'X'}, player2));
    }

    @Test
    public void MinMoveTest() {
        assertEquals(-Game.INF, game.MinMove(new char[]{'X', ' ', 'O',
                                                        ' ', 'O', ' ',
                                                        ' ', ' ', 'X'}, player1));

        assertEquals(+Game.INF, game.MinMove(new char[]{'X', ' ', 'O',
                                                        ' ', 'O', ' ',
                                                        'X', ' ', 'X'}, player1));

        assertEquals(0, game.MinMove(new char[]{'X', ' ', ' ',
                                                ' ', 'O', ' ',
                                                ' ', ' ', 'X'}, player1));
    }

    @Test
    public void MiniMaxTest() {
        assertEquals(8, game.MiniMax(new char[]{'X', ' ', 'O',
                                                'O', 'O', ' ',
                                                'X', ' ', 'X'}, player1));

        assertEquals(4, game.MiniMax(new char[]{'X', ' ', 'O',
                                                ' ', 'O', ' ',
                                                'X', 'O', 'X'}, player1));

        assertEquals(2, game.MiniMax(new char[]{'X', ' ', 'X',
                                                ' ', 'O', ' ',
                                                ' ', ' ', ' '}, player2));

        assertEquals(7, game.MiniMax(new char[]{'X', ' ', ' ',
                                                'X', 'O', ' ',
                                                ' ', ' ', ' '}, player2));

        assertEquals(5, game.MiniMax(new char[]{'X', ' ', ' ',
                                                ' ', ' ', ' ',
                                                ' ', ' ', ' '}, player2));
    }

    @Test
    public void initializeAndSetMarkerInTicTacToeCellTest() {
        TicTacToeCell cell = new TicTacToeCell(3, 2, 0);
        assertNotNull(cell);
        assertEquals(3, cell.getNum());
        assertEquals(2, cell.getCol());
        assertEquals(0, cell.getRow());
        assertEquals(' ', cell.getMarker());
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        cell.setMarker("Hello");
        assertEquals('H', cell.getMarker());
    }

    @Test
    public void UtilityTest() {
        System.setOut(new PrintStream(outContent));

        Utility.print(new char[]{'X', 'O', 'X',
                                 'X', 'O', 'O',
                                 'O', 'X', 'X'});
        String expectedOutput = "X-O-X-X-O-O-O-X-X-";
        assertTrue(outContent.toString().contains(expectedOutput));

        Utility.print(new int[]{1, 2, 3,
                                4, 5, 6,
                                7, 8, 9});
        expectedOutput = "1-2-3-4-5-6-7-8-9-";
        assertTrue(outContent.toString().contains(expectedOutput));

        Utility.print(new ArrayList<>(Arrays.asList(3, 5, 6, 8)));
        expectedOutput = "3-5-6-8-";
        assertTrue(outContent.toString().contains(expectedOutput));

        System.setOut(originalOut);
    }

    @Test
    public void TicTacToePanelTest() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        assertNotNull(panel);
    }
}
