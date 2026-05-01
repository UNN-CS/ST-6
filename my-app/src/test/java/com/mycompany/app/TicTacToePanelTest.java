package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import org.junit.jupiter.api.Test;

class TicTacToePanelTest {
    private static class TestPanel extends TicTacToePanel {
        private String resultMessage;
        private boolean exitCalled;

        TestPanel() {
            super(new GridLayout(3, 3));
        }

        @Override
        protected void showResult(String message) {
            resultMessage = message;
        }

        @Override
        protected void exitGame() {
            exitCalled = true;
        }
    }

    @Test
    void constructorCreatesBoardAndStartsWithX() {
        TestPanel panel = new TestPanel();

        assertNotNull(panel.getGame());
        assertEquals(panel.getGame().player1, panel.getGame().cplayer);
        assertEquals(9, panel.getCells().length);
        for (TicTacToeCell cell : panel.getCells()) {
            assertNotNull(cell);
        }
    }

    @Test
    void actionPerformedPlacesHumanAndAiMoves() {
        TestPanel panel = new TestPanel();
        TicTacToeCell firstCell = panel.getCells()[0];

        panel.actionPerformed(new ActionEvent(firstCell, ActionEvent.ACTION_PERFORMED, "click"));

        assertEquals('X', panel.getGame().board[0]);
        long occupiedCount = 0;
        for (char cell : panel.getGame().board) {
            if (cell != ' ') {
                occupiedCount++;
            }
        }
        assertEquals(2, occupiedCount);
        assertEquals(panel.getGame().player1, panel.getGame().cplayer);
        assertFalse(firstCell.isEnabled());
        assertEquals(State.PLAYING, panel.getGame().state);
    }

    @Test
    void resolveGameStateShowsDrawMessageAndRequestsExit() {
        TestPanel panel = new TestPanel();
        char[] drawBoard = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        System.arraycopy(drawBoard, 0, panel.getGame().board, 0, drawBoard.length);
        panel.getGame().symbol = 'X';

        panel.resolveGameState();

        assertEquals(State.DRAW, panel.getGame().state);
        assertEquals("Ничья", panel.resultMessage);
        assertTrue(panel.exitCalled);
    }

    @Test
    void resolveGameStateShowsWinMessageAndRequestsExit() {
        TestPanel panel = new TestPanel();
        char[] winBoard = {'X', 'X', 'X', ' ', 'O', ' ', 'O', ' ', ' '};
        System.arraycopy(winBoard, 0, panel.getGame().board, 0, winBoard.length);
        panel.getGame().symbol = 'X';

        panel.resolveGameState();

        assertEquals(State.XWIN, panel.getGame().state);
        assertEquals("Выиграли крестики", panel.resultMessage);
        assertTrue(panel.exitCalled);
    }
}
