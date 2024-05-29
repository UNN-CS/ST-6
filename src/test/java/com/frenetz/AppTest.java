package com.frenetz;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TicTacToe tests")
public class AppTest {

    private static Game game;

    @BeforeAll
    private static void initializeGame() {
        game = new Game();
    }

    @Test
    @DisplayName("Init test")
    public void checkInitStates() {
        assertEquals(game.state, State.PLAYING);
        assertEquals(game.player1.symbol, 'X');
        assertEquals(game.player2.symbol, 'O');
        assertEquals(game.board.length, 9);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/game_data.csv")
    @DisplayName("Check game states test")
    public void checkGameStateTest(
            String c1,
            String c2,
            String c3,
            String c4,
            String c5,
            String c6,
            String c7,
            String c8,
            String c9,
            State expectedState) {
        game.symbol = 'X';
        char[] board = new char[]{
                c1.charAt(0), c2.charAt(0), c3.charAt(0),
                c4.charAt(0), c5.charAt(0), c6.charAt(0),
                c7.charAt(0), c8.charAt(0), c9.charAt(0)
        };
        assertEquals(expectedState, game.checkState(board));
    }

    @Test
    @DisplayName("Full moves test")
    public void checkGenerateFullMoves() {
        char[] board = {
                ' ', ' ', ' ',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        ArrayList<Integer> result = new ArrayList<>();

        game.generateMoves(board, result);

        for (int i = 0; i < 9; i++) {
            assertEquals(i, result.get(i));
        }
    }

    @Test
    @DisplayName("Zero moves test")
    public void checkGenerateZeroMoves() {
        char[] board = {
                'X', 'O', 'X',
                'O', 'X', 'O',
                'O', 'X', 'O'
        };
        ArrayList<Integer> result = new ArrayList<>();

        game.generateMoves(board, result);

        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Check evaluate position to inf")
    public void checkEvaluatePositionInf() {
        char[] board = {
                'X', 'O', 'O',
                ' ', 'X', 'O',
                ' ', ' ', 'X'
        };

        game.symbol = 'X';

        assertEquals(game.evaluatePosition(board, game.player1), Game.INF);
    }

    @Test
    @DisplayName("Minimax empty board")
    public void checkMinimaxEmptyBoard() {
        char[] board = new char[9];
        int expectedMove = 0;

        int actualMove = game.MiniMax(board, game.player1);

        assertEquals(expectedMove, actualMove);
    }

    @Test
    @DisplayName("Cell constructor")
    public void checkCellInit() {
        assertAll("Cell Constructor",
                () -> assertDoesNotThrow(() -> new TicTacToeCell(1, 0, 2)),
                () -> {
                    TicTacToeCell cell = new TicTacToeCell(1, 0, 2);
                    assertEquals(1, cell.getNum(), "Cell number should be 1");
                    assertEquals(0, cell.getCol(), "Column index should be 0");
                    assertEquals(2, cell.getRow(), "Row index should be 2");
                    assertEquals(' ', cell.getMarker(), "Marker should be space character");
                }
        );
    }

    @Test
    @DisplayName("Minimax win")
    public void checkMinimaxWin() {
        char[] board = {
                ' ', ' ', 'X',
                ' ', 'O', 'X',
                ' ', ' ', ' '
        };
        int expectedMove = 9;

        int actualMove = game.MiniMax(board, game.player2);

        assertEquals(expectedMove, actualMove);
    }

    @Test
    @DisplayName("Setting marker")
    public void checkSettingMarker() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 2);
        cell.setMarker("TEST");

        assertAll(
                () -> assertEquals('T', cell.getMarker(), "Marker should be set to 'T'"),
                () -> assertEquals("TEST", cell.getText(), "Text should be set to 'TEST'"),
                () -> assertFalse(cell.isEnabled(), "Cell should be disabled after setting marker")
        );
    }

    @Test
    @DisplayName("Check evaluate position to zero")
    public void checkEvaluatePositionZero() {
        char[] board = {
                'X', 'X', 'O',
                'O', 'O', 'X',
                'X', 'O', 'X'
        };

        assertEquals(game.evaluatePosition(board, game.player1), 0);
    }

    @Test
    @DisplayName("Display method")
    public void checkDisplayMethod() {
        assertAll(
                "Utility print",
                () -> assertDoesNotThrow(() -> Utility.print(new char[]{'X', 'X', 'O', 'O', ' ', 'X', ' ', ' ', 'O'})),
                () -> assertDoesNotThrow(() -> Utility.print(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8})),
                () -> {
                    ArrayList<Integer> test = new ArrayList<>();
                    test.add(0);
                    test.add(0);
                    test.add(0);
                    test.add(0);
                    test.add(0);
                    test.add(0);
                    test.add(0);
                    test.add(0);
                    test.add(0);
                    assertDoesNotThrow(() -> Utility.print(test));
                });
    }

    @Test
    @DisplayName("Panel init")
    public void checkPanelInit() {
        assertDoesNotThrow(() -> {
            new TicTacToePanel(new GridLayout(3, 3));
        });
    }

    @Test
    @DisplayName("Check game state with different markers")
    public void checkGameStateWithDifferentMarkers() {
        game.symbol = 'O';
        char[] board = {
                'O', 'O', 'O',
                'X', ' ', 'X',
                ' ', ' ', 'X'
        };
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    @DisplayName("Check game state with no winner")
    public void checkGameStateWithNoWinner() {
        game.symbol = 'X';
        char[] board = {
                'O', 'X', 'X',
                'X', 'O', 'O',
                'O', 'O', 'X'
        };
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    @DisplayName("Check game state with full board")
    public void checkGameStateWithFullBoard() {
        game.symbol = 'O';
        char[] board = {
                'X', 'O', 'X',
                'X', 'O', 'O',
                'O', 'X', 'X'
        };
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    @DisplayName("Check generating moves on partially filled board")
    public void checkGenerateMovesOnPartiallyFilledBoard() {
        char[] board = {
                'X', 'O', 'X',
                'X', ' ', ' ',
                'O', ' ', ' '
        };
        ArrayList<Integer> result = new ArrayList<>();

        game.generateMoves(board, result);

        assertEquals(4, result.size());
        assertTrue(result.contains(4));
        assertTrue(result.contains(5));
        assertTrue(result.contains(7));
        assertTrue(result.contains(8));
    }

    @Test
    @DisplayName("Check evaluate position when game is in progress")
    public void checkEvaluatePositionInProgress() {
        char[] board = {
                'O', 'X', 'O',
                'X', ' ', 'X',
                ' ', ' ', ' '
        };

        game.symbol = 'O';

        assertEquals(-1, game.evaluatePosition(board, game.player1));
    }

    @Test
    @DisplayName("Minimax optimal move when opponent can win")
    public void checkMinimaxOptimalMoveWhenOpponentCanWin() {
        char[] board = {
                'O', 'X', 'O',
                ' ', 'X', ' ',
                ' ', ' ', ' '
        };
        int expectedMove = 6;

        int actualMove = game.MiniMax(board, game.player1);

        assertEquals(expectedMove, actualMove);
    }

    @Test
    @DisplayName("Setting marker with custom symbol")
    public void checkSettingMarkerWithCustomSymbol() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 2);
        cell.setMarker("NEW");

        assertAll(
                () -> assertEquals('N', cell.getMarker(), "Marker should be set to 'N'"),
                () -> assertEquals("NEW", cell.getText(), "Text should be set to 'NEW'"),
                () -> assertFalse(cell.isEnabled(), "Cell should be disabled after setting marker")
        );
    }

}