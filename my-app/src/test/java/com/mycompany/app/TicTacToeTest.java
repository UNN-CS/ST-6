package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.GridLayout;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

class TicTacToeTest {

    private Game game;

    @BeforeAll
    static void configureHeadlessMode() {
        System.setProperty("java.awt.headless", "true");
    }

    @BeforeEach
    void createNewGameInstance() {
        game = new Game();
    }

    @Test
    void newGameHasCorrectInitialState() {
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals(9, game.board.length);
        for (char cellValue : game.board)
            assertEquals(' ', cellValue);
    }

    @Test
    void playerObjectStoresAllFields() {
        Player testPlayer = new Player();
        testPlayer.symbol = 'Z';
        testPlayer.move = 7;
        testPlayer.selected = true;
        testPlayer.win = false;
        assertEquals('Z', testPlayer.symbol);
        assertEquals(7, testPlayer.move);
        assertTrue(testPlayer.selected);
        assertFalse(testPlayer.win);
    }

    @Test
    void xWinsOnTopRow() {
        game.symbol = 'X';
        game.board[0] = game.board[1] = game.board[2] = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void oWinsOnFirstColumn() {
        game.symbol = 'O';
        game.board[0] = game.board[3] = game.board[6] = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    void xWinsOnMainDiagonal() {
        game.symbol = 'X';
        game.board[0] = game.board[4] = game.board[8] = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void oWinsOnAntiDiagonal() {
        game.symbol = 'O';
        game.board[2] = game.board[4] = game.board[6] = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    void xWinsOnMiddleRow() {
        game.symbol = 'X';
        game.board[3] = game.board[4] = game.board[5] = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void oWinsOnBottomRow() {
        game.symbol = 'O';
        game.board[6] = game.board[7] = game.board[8] = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    void xWinsOnSecondColumn() {
        game.symbol = 'X';
        game.board[1] = game.board[4] = game.board[7] = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void oWinsOnThirdColumn() {
        game.symbol = 'O';
        game.board[2] = game.board[5] = game.board[8] = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    void fullBoardWithNoWinnerResultsInDraw() {
        game.symbol = 'X';
        char[] drawnBoard = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(State.DRAW, game.checkState(drawnBoard));
    }

    @Test
    void emptyBoardIsStillInPlayingState() {
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(game.board));
    }

    @Test
    void partiallyFilledBoardRemainsInPlayingState() {
        game.symbol = 'X';
        game.board[0] = 'X';
        game.board[4] = 'O';
        assertEquals(State.PLAYING, game.checkState(game.board));
    }

    @Test
    void allNineCellsAreAvailableOnEmptyBoard() {
        ArrayList<Integer> availableMovesList = new ArrayList<>();
        game.generateMoves(game.board, availableMovesList);
        assertEquals(9, availableMovesList.size());
    }

    @Test
    void occupiedCellsAreExcludedFromMoveList() {
        game.board[2] = 'X';
        game.board[5] = 'O';
        ArrayList<Integer> availableMovesList = new ArrayList<>();
        game.generateMoves(game.board, availableMovesList);
        assertEquals(7, availableMovesList.size());
        assertFalse(availableMovesList.contains(2));
        assertFalse(availableMovesList.contains(5));
    }

    @Test
    void noMovesAvailableOnCompletelyFilledBoard() {
        char[] completedBoard = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'};
        ArrayList<Integer> availableMovesList = new ArrayList<>();
        game.generateMoves(completedBoard, availableMovesList);
        assertTrue(availableMovesList.isEmpty());
    }

    @Test
    void onlyCornersRemainingInMoveList() {
        for (int boardIndex = 0; boardIndex < 9; boardIndex++) game.board[boardIndex] = 'X';
        game.board[0] = ' ';
        game.board[8] = ' ';
        ArrayList<Integer> availableMovesList = new ArrayList<>();
        game.generateMoves(game.board, availableMovesList);
        assertEquals(2, availableMovesList.size());
        assertTrue(availableMovesList.contains(0));
        assertTrue(availableMovesList.contains(8));
    }

    @Test
    void xWinReturnsPositiveScoreForPlayerX() {
        game.symbol = 'X';
        game.board[0] = game.board[1] = game.board[2] = 'X';
        assertEquals(Game.INF, game.evaluatePosition(game.board, game.player1));
    }

    @Test
    void xWinReturnsNegativeScoreForPlayerO() {
        game.symbol = 'X';
        game.board[0] = game.board[1] = game.board[2] = 'X';
        assertEquals(-Game.INF, game.evaluatePosition(game.board, game.player2));
    }

    @Test
    void oWinReturnsPositiveScoreForPlayerO() {
        game.symbol = 'O';
        game.board[0] = game.board[3] = game.board[6] = 'O';
        assertEquals(Game.INF, game.evaluatePosition(game.board, game.player2));
    }

    @Test
    void oWinReturnsNegativeScoreForPlayerX() {
        game.symbol = 'O';
        game.board[0] = game.board[3] = game.board[6] = 'O';
        assertEquals(-Game.INF, game.evaluatePosition(game.board, game.player1));
    }

    @Test
    void drawPositionReturnsZeroScore() {
        game.symbol = 'X';
        char[] drawnBoard = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(0, game.evaluatePosition(drawnBoard, game.player1));
    }

    @Test
    void ongoingGameReturnsMinusOneScore() {
        assertEquals(-1, game.evaluatePosition(game.board, game.player1));
    }

    private int runMiniMaxWithSuppressedOutput(Player activePlayer) {
        PrintStream originalOutputStream = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        try {
            return game.MiniMax(game.board, activePlayer);
        } finally {
            System.setOut(originalOutputStream);
        }
    }

    @Test
    void minimaxSelectsOnlyRemainingEmptyCell() {
        game.board[0] = 'X'; game.board[1] = 'O'; game.board[2] = 'X';
        game.board[3] = 'O'; game.board[4] = 'O'; game.board[5] = 'X';
        game.board[6] = 'X'; game.board[7] = 'X';
        int selectedMove = runMiniMaxWithSuppressedOutput(game.player1);
        assertEquals(9, selectedMove);
        assertEquals(' ', game.board[8]);
        assertEquals(0, game.q);
    }

    @Test
    void minimaxReturnsValidBoardPositionIndex() {
        game.board[0] = 'X';
        int selectedMove = runMiniMaxWithSuppressedOutput(game.player2);
        assertTrue(selectedMove >= 1 && selectedMove <= 9);
        assertEquals(0, game.q);
    }

    @Test
    void minimaxOnCompletedBoardReturnsZero() {
        game.board = new char[]{'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O'};
        int selectedMove = runMiniMaxWithSuppressedOutput(game.player1);
        assertEquals(0, selectedMove);
    }

    @Test
    void minMoveReturnsImmediatelyOnWonBoard() {
        game.symbol = 'X';
        game.board[0] = game.board[1] = game.board[2] = 'X';
        assertEquals(Game.INF, game.MinMove(game.board, game.player1));
    }

    @Test
    void maxMoveReturnsImmediatelyOnWonBoard() {
        game.symbol = 'O';
        game.board[0] = game.board[3] = game.board[6] = 'O';
        assertEquals(Game.INF, game.MaxMove(game.board, game.player2));
    }

    @Test
    void minMoveSearchesThroughOpenPositions() {
        game.board[0] = 'O'; game.board[4] = 'X'; game.board[8] = 'O';
        game.symbol = 'X';
        int computedValue = game.MinMove(game.board, game.player1);
        assertTrue(computedValue >= -Game.INF && computedValue <= Game.INF);
    }

    @Test
    void maxMoveSearchesThroughOpenPositions() {
        game.board[0] = 'X'; game.board[4] = 'O';
        game.symbol = 'O';
        int computedValue = game.MaxMove(game.board, game.player2);
        assertTrue(computedValue >= -Game.INF && computedValue <= Game.INF);
    }

    @Test
    void cellStoresCorrectPositionData() {
        TicTacToeCell testCell = new TicTacToeCell(4, 1, 1);
        assertEquals(4, testCell.getNum());
        assertEquals(1, testCell.getRow());
        assertEquals(1, testCell.getCol());
    }

    @Test
    void newCellStartsWithEmptyMarkerAndIsEnabled() {
        TicTacToeCell testCell = new TicTacToeCell(0, 0, 0);
        assertEquals(' ', testCell.getMarker());
        assertTrue(testCell.isEnabled());
    }

    @Test
    void settingMarkerOnCellDisablesIt() {
        TicTacToeCell testCell = new TicTacToeCell(0, 0, 0);
        testCell.setMarker("O");
        assertEquals('O', testCell.getMarker());
        assertFalse(testCell.isEnabled());
    }

    @Test
    void cellCorrectlyStoresXMarker() {
        TicTacToeCell testCell = new TicTacToeCell(3, 0, 1);
        testCell.setMarker("X");
        assertEquals('X', testCell.getMarker());
    }

    @Test
    void utilityPrintOutputsCharArrayWithDashes() {
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        PrintStream originalOutputStream = System.out;
        System.setOut(new PrintStream(outputBuffer));
        Utility.print(new char[]{'X', 'O', ' ', 'X', ' ', 'O', ' ', 'X', ' '});
        System.setOut(originalOutputStream);
        String capturedOutput = outputBuffer.toString();
        assertTrue(capturedOutput.contains("X-"));
        assertTrue(capturedOutput.contains("O-"));
    }

    @Test
    void utilityPrintOutputsIntArrayWithDashes() {
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        PrintStream originalOutputStream = System.out;
        System.setOut(new PrintStream(outputBuffer));
        Utility.print(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        System.setOut(originalOutputStream);
        assertTrue(outputBuffer.toString().contains("5-"));
    }

    @Test
    void utilityPrintOutputsArrayListWithDashes() {
        ArrayList<Integer> movesList = new ArrayList<>();
        for (int elementIndex = 0; elementIndex < 5; elementIndex++)
            movesList.add(elementIndex * 2);
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        PrintStream originalOutputStream = System.out;
        System.setOut(new PrintStream(outputBuffer));
        Utility.print(movesList);
        System.setOut(originalOutputStream);
        assertTrue(outputBuffer.toString().contains("8-"));
    }

    @Test
    void panelInitializesWithNineCells() {
        TicTacToePanel gamePanel = new TicTacToePanel(new GridLayout(3, 3));
        assertEquals(9, gamePanel.getComponentCount());
    }

    @Test
    void infConstantValueIsOneHundred() {
        assertEquals(100, Game.INF);
    }

    @Test
    void stateEnumContainsFourValues() {
        assertEquals(4, State.values().length);
    }
}
