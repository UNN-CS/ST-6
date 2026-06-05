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
    void initialBoardIsEmpty() {
        Game session = new Game();
        for (int r = 0; r < Game.SIZE; r++) {
            for (int c = 0; c < Game.SIZE; c++) {
                assertEquals(Game.EMPTY, session.getCell(r, c));
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
        Game session = new Game();
        assertTrue(session.placeMove(0, 0));
        assertEquals(Game.X, session.getCell(0, 0));
    }

    @Test
    void invalidMoveOutsideBoardIsRejected() {
        Game session = new Game();
        assertFalse(session.placeMove(-1, 0));
        assertFalse(session.placeMove(0, 3));
    }

    @Test
    void invalidMoveOnOccupiedCellIsRejected() {
        Game session = new Game();
        session.placeMove(0, 0);
        assertFalse(session.placeMove(0, 0));
    }

    @Test
    void playerSwitchesAfterValidMove() {
        Game session = new Game();
        session.placeMove(0, 0);
        assertEquals(Game.O, session.getCurrentPlayer());
    }

    @Test
    void playerDoesNotSwitchAfterInvalidMove() {
        Game session = new Game();
        session.placeMove(-1, 0);
        assertEquals(Game.X, session.getCurrentPlayer());
    }

    @Test
    void resetBoardClearsMovesAndRestoresCurrentPlayer() {
        Game session = new Game();
        session.placeMove(0, 0);
        session.reset();
        assertEquals(Game.EMPTY, session.getCell(0, 0));
        assertEquals(Game.X, session.getCurrentPlayer());
    }

    @Test
    void xWinsByRow() {
        Game session = new Game();
        fillRow(session, 0, Game.X);
        assertEquals(GameState.X_WON, session.getState());
    }

    @Test
    void oWinsByRow() {
        Game session = new Game();
        fillRow(session, 2, Game.O);
        assertEquals(GameState.O_WON, session.getState());
    }

    @Test
    void xWinsByColumn() {
        Game session = new Game();
        fillColumn(session, 1, Game.X);
        assertEquals(GameState.X_WON, session.getState());
    }

    @Test
    void oWinsByColumn() {
        Game session = new Game();
        fillColumn(session, 2, Game.O);
        assertEquals(GameState.O_WON, session.getState());
    }

    @Test
    void xWinsByMainDiagonal() {
        Game session = new Game();
        session.setCellForTest(0, 0, Game.X);
        session.setCellForTest(1, 1, Game.X);
        session.setCellForTest(2, 2, Game.X);
        assertEquals(GameState.X_WON, session.getState());
    }

    @Test
    void oWinsByOtherDiagonal() {
        Game session = new Game();
        session.setCellForTest(0, 2, Game.O);
        session.setCellForTest(1, 1, Game.O);
        session.setCellForTest(2, 0, Game.O);
        assertEquals(GameState.O_WON, session.getState());
    }

    @Test
    void drawIsDetected() {
        assertEquals(GameState.DRAW, buildDrawBoard().getState());
    }

    @Test
    void gameOverAfterWin() {
        Game session = new Game();
        fillRow(session, 0, Game.X);
        assertTrue(session.isGameOver());
    }

    @Test
    void gameOverAfterDraw() {
        assertTrue(buildDrawBoard().isGameOver());
    }

    @Test
    void boardToStringReturnsExpectedContent() {
        Game session = new Game();
        session.placeMove(0, 0);
        assertEquals("X|-|-\n-|-|-\n-|-|-", session.boardToString());
    }

    @Test
    void minimaxScoresWinningPositionCorrectly() {
        Game session = new Game();
        fillRow(session, 0, Game.X);
        assertEquals(10, session.minimax(true, Game.X));
    }

    @Test
    void minimaxScoresLosingPositionCorrectly() {
        Game session = new Game();
        fillRow(session, 0, Game.X);
        assertEquals(-10, session.minimax(true, Game.O));
    }

    @Test
    void minimaxScoresDrawPositionCorrectly() {
        assertEquals(0, buildDrawBoard().minimax(true, Game.X));
    }

    @Test
    void bestMoveBlocksOpponentWin() {
        Game session = new Game();
        session.setCellForTest(0, 0, Game.X);
        session.setCellForTest(0, 1, Game.X);
        session.setCellForTest(1, 1, Game.O);
        assertEquals(new Move(0, 2), session.findBestMove(Game.O));
    }

    @Test
    void bestMoveWinsImmediatelyWhenPossible() {
        Game session = new Game();
        session.setCellForTest(0, 0, Game.O);
        session.setCellForTest(0, 1, Game.O);
        session.setCellForTest(1, 1, Game.X);
        assertEquals(new Move(0, 2), session.findBestMove(Game.O));
    }

    @Test
    void bestMoveChoosesCenterOnEmptyBoard() {
        assertEquals(new Move(1, 1), new Game().findBestMove(Game.O));
    }

    @Test
    void noBestMoveExistsWhenBoardIsFull() {
        assertNull(buildDrawBoard().findBestMove(Game.X));
    }

    @Test
    void bestMoveReturnsSomeMoveForNonTerminalBoard() {
        Game session = new Game();
        session.placeMove(0, 0);
        assertNotNull(session.findBestMove(Game.O));
    }

    @Test
    void availableMovesCountIsCorrect() {
        Game session = new Game();
        session.placeMove(0, 0);
        session.placeMove(1, 1);
        assertEquals(7, session.getAvailableMoves().size());
    }

    @Test
    void availableMovesContainExpectedMove() {
        List<Move> moves = new Game().getAvailableMoves();
        assertTrue(moves.contains(new Move(2, 2)));
    }

    @Test
    void currentPlayerGetterWorks() {
        Game session = new Game();
        session.switchPlayer();
        assertEquals(Game.O, session.getCurrentPlayer());
    }

    @Test
    void winnerGetterReturnsWinner() {
        Game session = new Game();
        fillColumn(session, 0, Game.O);
        assertEquals(Character.valueOf(Game.O), session.getWinner());
    }

    @Test
    void winnerGetterReturnsNullAtStart() {
        assertNull(new Game().getWinner());
    }

    @Test
    void emptyCellCheckWorks() {
        Game session = new Game();
        assertTrue(session.isEmptyCell(2, 2));
        session.placeMove(2, 2);
        assertFalse(session.isEmptyCell(2, 2));
    }

    @Test
    void fullBoardCheckWorks() {
        assertTrue(buildDrawBoard().isFullBoard());
    }

    @Test
    void invalidGetCellThrows() {
        Game session = new Game();
        assertThrows(IllegalArgumentException.class, () -> session.getCell(5, 0));
    }

    @Test
    void setCellRejectsUnknownPlayer() {
        Game session = new Game();
        assertThrows(IllegalArgumentException.class,
            () -> session.setCellForTest(0, 0, 'A'));
    }

    @Test
    void setCurrentPlayerRejectsUnknownPlayer() {
        Game session = new Game();
        assertThrows(IllegalArgumentException.class,
            () -> session.setCurrentPlayerForTest('B'));
    }

    @Test
    void setCurrentPlayerAcceptsO() {
        Game session = new Game();
        session.setCurrentPlayerForTest(Game.O);
        assertEquals(Game.O, session.getCurrentPlayer());
    }

    @Test
    void moveGettersReturnCoordinates() {
        Move step = new Move(1, 2);
        assertEquals(1, step.getRow());
        assertEquals(2, step.getCol());
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
        Move a = new Move(2, 1);
        Move b = new Move(2, 1);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void mainRunsDemoWithoutError() {
        Program.main(new String[0]);
        assertTrue(true);
    }

    private static void fillRow(Game session, int row, char mark) {
        for (int col = 0; col < Game.SIZE; col++) {
            session.setCellForTest(row, col, mark);
        }
    }

    private static void fillColumn(Game session, int col, char mark) {
        for (int row = 0; row < Game.SIZE; row++) {
            session.setCellForTest(row, col, mark);
        }
    }

    private static Game buildDrawBoard() {
        Game session = new Game();
        char[][] layout = {
            {Game.X, Game.O, Game.X},
            {Game.X, Game.O, Game.O},
            {Game.O, Game.X, Game.X}
        };
        for (int r = 0; r < Game.SIZE; r++) {
            for (int c = 0; c < Game.SIZE; c++) {
                session.setCellForTest(r, c, layout[r][c]);
            }
        }
        return session;
    }
}
