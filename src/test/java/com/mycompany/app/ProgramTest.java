package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class ProgramTest {

    @Test
    void mainRunsDemoWithoutError() {
        Program.main(new String[0]);
        assertTrue(true);
    }

    @Test
    void initialBoardIsEmpty() {
        Game board = new Game();
        for (int row = 0; row < Game.SIZE; row++) {
            for (int col = 0; col < Game.SIZE; col++) {
                assertEquals(Game.EMPTY, board.getCell(row, col));
            }
        }
    }

    @Test
    void initialPlayerIsX() {
        assertEquals(Game.X, new Game().getCurrentPlayer());
    }

    @Test
    void gameNotOverAtStart() {
        assertFalse(new Game().isGameOver());
    }

    @Test
    void validMoveIsAccepted() {
        Game board = new Game();
        assertTrue(board.placeMove(0, 0));
        assertEquals(Game.X, board.getCell(0, 0));
    }

    @Test
    void invalidMoveOutsideBoardIsRejected() {
        Game board = new Game();
        assertFalse(board.placeMove(-1, 0));
        assertFalse(board.placeMove(0, 3));
    }

    @Test
    void invalidMoveOnOccupiedCellIsRejected() {
        Game board = new Game();
        board.placeMove(0, 0);
        assertFalse(board.placeMove(0, 0));
    }

    @Test
    void playerSwitchesAfterValidMove() {
        Game board = new Game();
        board.placeMove(0, 0);
        assertEquals(Game.O, board.getCurrentPlayer());
    }

    @Test
    void playerDoesNotSwitchAfterInvalidMove() {
        Game board = new Game();
        board.placeMove(-1, 0);
        assertEquals(Game.X, board.getCurrentPlayer());
    }

    @Test
    void resetBoardClearsMovesAndRestoresCurrentPlayer() {
        Game board = new Game();
        board.placeMove(0, 0);
        board.reset();
        assertEquals(Game.EMPTY, board.getCell(0, 0));
        assertEquals(Game.X, board.getCurrentPlayer());
    }

    @Test
    void xWinsByRow() {
        Game board = new Game();
        paintRow(board, 0, Game.X);
        assertEquals(GameState.X_WON, board.getState());
    }

    @Test
    void oWinsByRow() {
        Game board = new Game();
        paintRow(board, 2, Game.O);
        assertEquals(GameState.O_WON, board.getState());
    }

    @Test
    void xWinsByColumn() {
        Game board = new Game();
        paintColumn(board, 1, Game.X);
        assertEquals(GameState.X_WON, board.getState());
    }

    @Test
    void oWinsByColumn() {
        Game board = new Game();
        paintColumn(board, 2, Game.O);
        assertEquals(GameState.O_WON, board.getState());
    }

    @Test
    void xWinsByMainDiagonal() {
        Game board = new Game();
        board.setCellForTest(0, 0, Game.X);
        board.setCellForTest(1, 1, Game.X);
        board.setCellForTest(2, 2, Game.X);
        assertEquals(GameState.X_WON, board.getState());
    }

    @Test
    void oWinsByOtherDiagonal() {
        Game board = new Game();
        board.setCellForTest(0, 2, Game.O);
        board.setCellForTest(1, 1, Game.O);
        board.setCellForTest(2, 0, Game.O);
        assertEquals(GameState.O_WON, board.getState());
    }

    @Test
    void drawIsDetected() {
        assertEquals(GameState.DRAW, createDrawnBoard().getState());
    }

    @Test
    void gameOverAfterWin() {
        Game board = new Game();
        paintRow(board, 0, Game.X);
        assertTrue(board.isGameOver());
    }

    @Test
    void gameOverAfterDraw() {
        assertTrue(createDrawnBoard().isGameOver());
    }

    @Test
    void boardToStringReturnsExpectedContent() {
        Game board = new Game();
        board.placeMove(0, 0);
        assertEquals("X|-|-\n-|-|-\n-|-|-", board.boardToString());
    }

    @Test
    void minimaxScoresWinningPositionCorrectly() {
        Game board = new Game();
        paintRow(board, 0, Game.X);
        assertEquals(10, board.minimax(true, Game.X));
    }

    @Test
    void minimaxScoresLosingPositionCorrectly() {
        Game board = new Game();
        paintRow(board, 0, Game.X);
        assertEquals(-10, board.minimax(true, Game.O));
    }

    @Test
    void minimaxScoresDrawPositionCorrectly() {
        assertEquals(0, createDrawnBoard().minimax(true, Game.X));
    }

    @Test
    void bestMoveBlocksOpponentWin() {
        Game board = new Game();
        board.setCellForTest(0, 0, Game.X);
        board.setCellForTest(0, 1, Game.X);
        board.setCellForTest(1, 1, Game.O);
        assertEquals(new Move(0, 2), board.findBestMove(Game.O));
    }

    @Test
    void bestMoveWinsImmediatelyWhenPossible() {
        Game board = new Game();
        board.setCellForTest(0, 0, Game.O);
        board.setCellForTest(0, 1, Game.O);
        board.setCellForTest(1, 1, Game.X);
        assertEquals(new Move(0, 2), board.findBestMove(Game.O));
    }

    @Test
    void bestMoveChoosesCenterOnEmptyBoard() {
        assertEquals(new Move(1, 1), new Game().findBestMove(Game.O));
    }

    @Test
    void noBestMoveExistsWhenBoardIsFull() {
        assertNull(createDrawnBoard().findBestMove(Game.X));
    }

    @Test
    void bestMoveReturnsSomeMoveForNonTerminalBoard() {
        Game board = new Game();
        board.placeMove(0, 0);
        assertNotNull(board.findBestMove(Game.O));
    }

    @Test
    void availableMovesCountIsCorrect() {
        Game board = new Game();
        board.placeMove(0, 0);
        board.placeMove(1, 1);
        assertEquals(7, board.getAvailableMoves().size());
    }

    @Test
    void availableMovesContainExpectedMove() {
        List<Move> options = new Game().getAvailableMoves();
        assertTrue(options.contains(new Move(2, 2)));
    }

    @Test
    void currentPlayerGetterWorks() {
        Game board = new Game();
        board.switchPlayer();
        assertEquals(Game.O, board.getCurrentPlayer());
    }

    @Test
    void winnerGetterReturnsWinner() {
        Game board = new Game();
        paintColumn(board, 0, Game.O);
        assertEquals(Character.valueOf(Game.O), board.getWinner());
    }

    @Test
    void winnerGetterReturnsNullAtStart() {
        assertNull(new Game().getWinner());
    }

    @Test
    void emptyCellCheckWorks() {
        Game board = new Game();
        assertTrue(board.isEmptyCell(2, 2));
        board.placeMove(2, 2);
        assertFalse(board.isEmptyCell(2, 2));
    }

    @Test
    void fullBoardCheckWorks() {
        assertTrue(createDrawnBoard().isFullBoard());
    }

    @Test
    void invalidGetCellThrows() {
        Game board = new Game();
        assertThrows(IllegalArgumentException.class, () -> board.getCell(5, 0));
    }

    @Test
    void setCellRejectsUnknownPlayer() {
        Game board = new Game();
        assertThrows(IllegalArgumentException.class,
            () -> board.setCellForTest(0, 0, 'A'));
    }

    @Test
    void setCurrentPlayerRejectsUnknownPlayer() {
        Game board = new Game();
        assertThrows(IllegalArgumentException.class,
            () -> board.setCurrentPlayerForTest('B'));
    }

    @Test
    void setCurrentPlayerAcceptsO() {
        Game board = new Game();
        board.setCurrentPlayerForTest(Game.O);
        assertEquals(Game.O, board.getCurrentPlayer());
    }

    @Test
    void moveGettersReturnCoordinates() {
        Move position = new Move(1, 2);
        assertEquals(1, position.getRow());
        assertEquals(2, position.getCol());
    }

    @Test
    void moveToStringContainsCoordinates() {
        assertEquals("(1, 2)", new Move(1, 2).toString());
    }

    @Test
    void moveEqualsRejectsDifferentType() {
        assertFalse(new Move(1, 2).equals("not a move"));
    }

    @Test
    void equalMovesHaveSameHashCode() {
        Move first = new Move(2, 1);
        Move second = new Move(2, 1);
        assertEquals(first.hashCode(), second.hashCode());
    }

    private static void paintRow(Game board, int row, char mark) {
        for (int col = 0; col < Game.SIZE; col++) {
            board.setCellForTest(row, col, mark);
        }
    }

    private static void paintColumn(Game board, int col, char mark) {
        for (int row = 0; row < Game.SIZE; row++) {
            board.setCellForTest(row, col, mark);
        }
    }

    private static Game createDrawnBoard() {
        Game board = new Game();
        char[][] pattern = {
            {Game.X, Game.O, Game.X},
            {Game.X, Game.O, Game.O},
            {Game.O, Game.X, Game.X}
        };
        for (int row = 0; row < Game.SIZE; row++) {
            for (int col = 0; col < Game.SIZE; col++) {
                board.setCellForTest(row, col, pattern[row][col]);
            }
        }
        return board;
    }
}
