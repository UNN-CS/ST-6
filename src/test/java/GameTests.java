package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {

    private static Game game;

    @BeforeAll
    public static void setUp() {
        game = new Game();
    }

    @Test
    public void testDrawStateDetection() {
        game.board = new char[]{'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'};
        assertEquals(State.DRAW, game.checkState(game.board));
    }

    @Test
    public void testWinningCombinationDetectionForO() {
        game.board = new char[]{'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    public void testWinningDiagonalCombinationDetectionForX() {
        game.board = new char[]{' ', ' ', 'X', ' ', 'X', ' ', 'X', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    public void testWinningDiagonalCombinationDetectionForO() {
        game.board = new char[]{' ', ' ', 'O', ' ', 'O', ' ', 'O', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));
    }    

    @Test
    public void testWinningVerticalCombinationDetectionForX() {
        game.board = new char[]{'X', ' ', ' ', 'X', ' ', ' ', 'X', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    public void testWinningVerticalCombinationDetectionForO() {
        game.board = new char[]{'O', ' ', ' ', 'O', ' ', ' ', 'O', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    public void testStateWithTwoFilledRows() {
        game.board = new char[]{'X', 'X', 'X', 'O', 'O', 'O', ' ', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(game.board));
    }

   @Test
    public void testStateWithFilledBoardButNoWinningCombination() {
        game.board = new char[]{'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'};
        assertEquals(State.DRAW, game.checkState(game.board));
    } 

    @Test
    public void testStateWithOneFilledRow() {
        game.board = new char[]{'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    public void testStateWithOneFilledCell() {
        game.board = new char[]{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(State.PLAYING, game.checkState(game.board));
    }

}