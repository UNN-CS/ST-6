package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.io.*;
import javax.swing.*;
import java.awt.Component;
import java.lang.reflect.Field;

class AppTests {

    Game game;

    @BeforeEach
    void setup() {
        game = new Game();
    }

    @Nested
    class CheckStateTests {
        @Test
        void testXWinRow() {
            char[] board = {'X','X','X',' ',' ',' ',' ',' ',' '};
            game.symbol = 'X';
            assertEquals(State.XWIN, game.checkState(board));
        }

        @Test
        void testOWinColumn() {
            char[] board = {'O',' ',' ','O',' ',' ','O',' ',' '};
            game.symbol = 'O';
            assertEquals(State.OWIN, game.checkState(board));
        }

        @Test
        void testDraw() {
            char[] board = {'X','O','X','X','O','O','O','X','X'};
            game.symbol = 'X';
            assertEquals(State.DRAW, game.checkState(board));
        }

        @Test
        void testPlaying() {
            char[] board = {'X','O',' ',' ',' ',' ',' ',' ',' '};
            game.symbol = 'O';
            assertEquals(State.PLAYING, game.checkState(board));
        }

        @Test
        void testXWinDiagonal1() {
            char[] board = {'X',' ',' ',' ','X',' ',' ',' ','X'};
            game.symbol = 'X';
            assertEquals(State.XWIN, game.checkState(board));
        }

        @Test
        void testOWinDiagonal2() {
            char[] board = {' ',' ','O',' ','O',' ','O',' ',' '};
            game.symbol = 'O';
            assertEquals(State.OWIN, game.checkState(board));
        }

        @Test
        void testDrawFullBoard() {
            char[] board = {'X','O','X','O','X','O','O','X','O'};
            game.symbol = 'X';
            assertEquals(State.DRAW, game.checkState(board));
        }
    }

    @Nested
    class GenerateMovesTests {
        @Test
        void testGenerateMoves() {
            char[] board = {'X',' ','O',' ','X',' ','O',' ',' '};
            ArrayList<Integer> moves = new ArrayList<>();
            game.generateMoves(board, moves);
            assertTrue(moves.contains(1));
            assertTrue(moves.contains(3));
            assertEquals(5, moves.size());
        }

        @Test
        void testGenerateMovesEmptyBoard() {
            char[] board = {' ',' ',' ',' ',' ',' ',' ',' ',' '};
            ArrayList<Integer> moves = new ArrayList<>();
            game.generateMoves(board, moves);
            assertEquals(9, moves.size());
        }

        @Test
        void testGenerateMovesFullBoard() {
            char[] board = {'X','O','X','O','X','O','O','X','O'};
            ArrayList<Integer> moves = new ArrayList<>();
            game.generateMoves(board, moves);
            assertTrue(moves.isEmpty());
        }
    }

    @Nested
    class EvaluatePositionTests {

        @Test
        void testEvaluateDraw() {
            char[] board = {'X','O','X','X','O','O','O','X','X'};
            int val = game.evaluatePosition(board, game.player1);
            assertEquals(0, val);
        }

        @Test
        void testEvaluateInProgress() {
            char[] board = {'X','O',' ',' ',' ',' ',' ',' ',' '};
            int val = game.evaluatePosition(board, game.player1);
            assertEquals(-1, val);
        }
    }

    @Nested
    class MiniMaxTests {
        @Test
        void testMiniMaxImmediateWin() {
            char[] board = {'X','X',' ','O','O',' ',' ',' ',' '};
            game.player1.symbol = 'X';
            int move = game.MiniMax(board, game.player1);
            assertEquals(3, move);
        }

        @Test
        void testMiniMaxBlockOpponent() {
            char[] board = {'X','X',' ',' ','O',' ',' ',' ',' '};
            game.player2.symbol = 'O';
            int move = game.MiniMax(board, game.player2);
            assertEquals(3, move);
        }

        @Test
        void testMiniMaxPreventImmediateLoss() {
            char[] board = {'O','O',' ','X','X',' ',' ',' ',' '};
            game.player2.symbol = 'O';
            int move = game.MiniMax(board, game.player2);
            assertEquals(3, move);
        }
    }

    @Nested
    class UtilityPrintTests {
        private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        private final PrintStream originalOut = System.out;

        @BeforeEach
        void setUpStreams() {
            System.setOut(new PrintStream(outContent));
        }

        @AfterEach
        void restoreStreams() {
            System.setOut(originalOut);
        }

        @Test
        void testPrintList() {
            ArrayList<Integer> moves = new ArrayList<>();
            moves.add(5);
            moves.add(10);
            Utility.print(moves);
            assertTrue(outContent.toString().contains("5-10-"));
        }
    }

    @Nested
    class PanelAndProgramTests {
        @Test
        void testTicTacToePanelInit() throws Exception {
            TicTacToePanel panel = new TicTacToePanel(new java.awt.GridLayout(3,3));
            assertEquals(9, panel.getComponentCount());
            for (Component comp : panel.getComponents()) {
                assertTrue(comp instanceof TicTacToeCell);
                TicTacToeCell cell = (TicTacToeCell) comp;
                assertEquals(' ', cell.getMarker());
            }
            Field gameField = TicTacToePanel.class.getDeclaredField("game");
            gameField.setAccessible(true);
            Game internalGame = (Game) gameField.get(panel);
            assertEquals('X', internalGame.cplayer.symbol);
        }

        @Test
        void testProgramMainRuns() {
            assertDoesNotThrow(() -> Program.main(new String[0]));
        }
    }

    @Nested
    class TicTacToeCellTests {
        @Test
        void testCellInitialization() {
            TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
            assertEquals(' ', cell.getMarker());
            assertEquals(0, cell.getNum());
        }

        @Test
        void testCellMarkerUpdate() {
            TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
            cell.setMarker("X");
            assertEquals('X', cell.getMarker());
            assertFalse(cell.isEnabled());
        }
    }
}
