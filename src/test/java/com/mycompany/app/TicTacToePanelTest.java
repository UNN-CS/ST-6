package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import org.junit.jupiter.api.Test;

class TicTacToePanelTest {
    private static class TestPanel extends TicTacToePanel {
        private State finishedState;

        TestPanel() {
            super(new GridLayout(3, 3));
        }

        @Override
        protected void finishGame(State result) {
            finishedState = result;
        }
    }

    @Test
    void clickingAsHumanTriggersComputerReply() {
        TestPanel panel = new TestPanel();
        TicTacToeCell clickedCell = panelCell(panel, 0);

        panel.actionPerformed(new ActionEvent(clickedCell, ActionEvent.ACTION_PERFORMED, "click"));

        assertEquals('X', panelCell(panel, 0).getMarker());
        assertEquals(2, countMarkedCells(panel));
        assertEquals(State.PLAYING, panel.gameState());
    }

    @Test
    void clickingAsComputerAdvancesTurnWithoutRecursion() {
        TestPanel panel = new TestPanel();
        panel.game().cplayer = panel.game().player2;
        TicTacToeCell clickedCell = panelCell(panel, 4);

        panel.actionPerformed(new ActionEvent(clickedCell, ActionEvent.ACTION_PERFORMED, "click"));

        assertEquals('O', panelCell(panel, 4).getMarker());
        assertEquals(panel.game().player1, panel.game().cplayer);
        assertEquals(State.PLAYING, panel.gameState());
    }

    private TicTacToeCell panelCell(TicTacToePanel panel, int index) {
        return (TicTacToeCell) panel.getComponent(index);
    }

    private int countMarkedCells(TicTacToePanel panel) {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            if (panelCell(panel, i).getMarker() != ' ') {
                count++;
            }
        }
        return count;
    }
}
