package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TicTacToePanelTest {
    private TicTacToePanel panel;

    @BeforeEach
    void setUp() {
        panel = new TicTacToePanel(new GridLayout(3, 3));
    }

    @Test
    void testInitialState() {
        for (TicTacToeCell cell : panel.cells) {
            assertNotNull(cell);
            assertEquals(' ', cell.getMarker());
        }
    }

    @Test
    void testActionPerformedPlayerMove() {
        ActionEvent event = new ActionEvent(panel.cells[0], ActionEvent.ACTION_PERFORMED, null);
        panel.actionPerformed(event);

        assertEquals('X', panel.cells[0].getMarker());

        assertEquals(panel.game.player1, panel.game.cplayer);
    }

    @Test
    void testActionPerformedPlayer2Move() {
        ActionEvent event = new ActionEvent(panel.cells[0], ActionEvent.ACTION_PERFORMED, null);
        panel.actionPerformed(event);

        assertEquals('X', panel.cells[0].getMarker(), "First cell should have player's marker 'X'");

        int player2Move = panel.game.player2.move;
        if (player2Move > 0) {
            panel.cells[player2Move - 1].doClick();
            assertEquals('O', panel.cells[player2Move - 1].getMarker(), "Player 2 should mark their move in the corresponding cell");
        }
    }

}
