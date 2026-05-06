package moe.lair.ttt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import org.junit.jupiter.api.Test;

class TicTacToePanelTest {
    @Test
    void createPanelBuildsNineCellsAndInitialGame() {
        TicTacToePanel panel = Program.createPanel();

        assertEquals(9, panel.getComponentCount());
        assertEquals(9, panel.getCells().length);
        assertNotNull(panel.getGame());
        assertSame(panel.getGame().player1, panel.getGame().cplayer);
    }

    @Test
    void constructorCreatesCellsWithExpectedCoordinates() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));

        assertEquals(0, panel.getCell(0).getRow());
        assertEquals(0, panel.getCell(0).getCol());
        assertEquals(2, panel.getCell(8).getRow());
        assertEquals(2, panel.getCell(8).getCol());
    }

    @Test
    void actionPerformedMarksHumanMoveAndComputerResponse() {
        TestPanel panel = new TestPanel();

        panel.actionPerformed(eventFor(panel, 0));

        assertEquals('X', panel.getCell(0).getMarker());
        assertTrue(countFilledCells(panel) >= 2);
        assertSame(panel.getGame().player1, panel.getGame().cplayer);
    }

    @Test
    void actionPerformedFinishesOnXWin() {
        TestPanel panel = new TestPanel();
        panel.getGame().cplayer = panel.getGame().player2;
        panel.getGame().player2.symbol = 'X';
        panel.getCell(0).setMarker("X");
        panel.getCell(1).setMarker("X");

        panel.actionPerformed(eventFor(panel, 2));

        assertEquals(State.XWIN, panel.finishedState);
        assertEquals(1, panel.finishCount);
    }

    @Test
    void actionPerformedFinishesOnOWin() {
        TestPanel panel = new TestPanel();
        panel.getGame().cplayer = panel.getGame().player2;
        panel.getGame().player1.symbol = 'O';
        panel.getGame().player2.symbol = 'O';
        panel.getCell(0).setMarker("O");
        panel.getCell(1).setMarker("O");

        panel.actionPerformed(eventFor(panel, 2));

        assertEquals(State.OWIN, panel.finishedState);
        assertEquals(1, panel.finishCount);
    }

    @Test
    void actionPerformedFinishesOnDraw() {
        TestPanel panel = new TestPanel();
        panel.getGame().cplayer = panel.getGame().player2;
        panel.getCell(0).setMarker("X");
        panel.getCell(1).setMarker("O");
        panel.getCell(2).setMarker("X");
        panel.getCell(3).setMarker("X");
        panel.getCell(4).setMarker("O");
        panel.getCell(5).setMarker("O");
        panel.getCell(6).setMarker("O");
        panel.getCell(7).setMarker("X");

        panel.actionPerformed(eventFor(panel, 8));

        assertEquals(State.DRAW, panel.finishedState);
        assertEquals(1, panel.finishCount);
    }

    private static ActionEvent eventFor(TicTacToePanel panel, int cellIndex) {
        return new ActionEvent(panel.getCell(cellIndex), ActionEvent.ACTION_PERFORMED, "click");
    }

    private static int countFilledCells(TicTacToePanel panel) {
        int count = 0;
        for (TicTacToeCell cell : panel.getCells()) {
            if (cell.getMarker() != ' ') {
                count++;
            }
        }
        return count;
    }

    private static class TestPanel extends TicTacToePanel {
        private State finishedState;
        private int finishCount;

        TestPanel() {
            super(new GridLayout(3, 3));
        }

        @Override
        protected void finishGame(State finalState) {
            this.finishedState = finalState;
            this.finishCount++;
        }
    }
}
