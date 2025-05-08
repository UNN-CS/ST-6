package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class ProgramTest {

    private Game game;
    private Player player1;
    private Player player2;
    private TicTacToeCell cell;
    private TicTacToePanel panel;

    @Test
    void testInitialGameState() {
        game = new Game();
        player1 = game.player1;
        player2 = game.player2;
        game.cplayer = game.player1;
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', player1.symbol);
        assertEquals('O', player2.symbol);
        assertEquals(player1, game.cplayer);

        for (int i = 0; i < 9; i++) {
            assertEquals(' ', game.board[i]);
        }
    }

    @Test
    void testCheckStateWinConditions() {
        game = new Game();
        player1 = game.player1;
        player2 = game.player2;
        game.cplayer = game.player1;
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));

        board = new char[]{'O', ' ', ' ', 'O', ' ', ' ', 'O', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testGenerateMoves() {
        game = new Game();
        player1 = game.player1;
        player2 = game.player2;
        game.cplayer = game.player1;
        char[] board = {'X', ' ', 'O', ' ', 'X', ' ', ' ', ' ', ' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);

        assertEquals(6, moves.size());
        assertTrue(moves.contains(1));
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(5));
        assertTrue(moves.contains(6));
        assertTrue(moves.contains(7));
        assertTrue(moves.contains(8));
    }

    @Test
    void testPlayerInitialization() {
        Game game = new Game();
        Player player = game.player1;

        assertEquals('X', player.symbol);
        assertFalse(player.selected);
        assertFalse(player.win);
        assertEquals(0, player.move);
    }

    @Test
    void testSetMarker() {
        cell = new TicTacToeCell(1, 0, 1);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertEquals("X", cell.getText());
        assertFalse(cell.isEnabled());
    }

    @Test
    void testActionPerformedPlayer2Move() {
        panel = new TicTacToePanel(new GridLayout(3, 3));
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
