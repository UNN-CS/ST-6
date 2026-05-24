package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

class TicTacToePanelTest {

    @Test
    void panelCreatesCellsAndGame() throws Exception {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        Field cellsField = TicTacToePanel.class.getDeclaredField("cells");
        cellsField.setAccessible(true);
        TicTacToeCell[] cells = (TicTacToeCell[]) cellsField.get(panel);
        assertEquals(9, cells.length);
        assertEquals(' ', cells[0].getMarker());
    }

    @Test
    void actionPerformedHumanMoveAndAiResponse() throws Exception {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        Field cellsField = TicTacToePanel.class.getDeclaredField("cells");
        cellsField.setAccessible(true);
        TicTacToeCell[] cells = (TicTacToeCell[]) cellsField.get(panel);

        panel.actionPerformed(new ActionEvent(cells[0], 0, "click"));
        long marked = 0;
        for (TicTacToeCell cell : cells) {
            if (cell.getMarker() != ' ') {
                marked++;
            }
        }
        assertEquals(2, marked);
    }

    @Test
    void actionPerformedSecondPlayerBranch() throws Exception {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        Field gameField = TicTacToePanel.class.getDeclaredField("game");
        gameField.setAccessible(true);
        Game game = (Game) gameField.get(panel);
        game.cplayer = game.player2;
        game.player1.move = 5;

        Field cellsField = TicTacToePanel.class.getDeclaredField("cells");
        cellsField.setAccessible(true);
        TicTacToeCell[] cells = (TicTacToeCell[]) cellsField.get(panel);

        panel.actionPerformed(new ActionEvent(cells[4], 0, "click"));
        assertEquals(game.player1, game.cplayer);
    }

    @Test
    void actionPerformedDrawShowsDialogInHeadlessMode() throws Exception {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        Field gameField = TicTacToePanel.class.getDeclaredField("game");
        gameField.setAccessible(true);
        Game game = (Game) gameField.get(panel);

        Field cellsField = TicTacToePanel.class.getDeclaredField("cells");
        cellsField.setAccessible(true);
        TicTacToeCell[] cells = (TicTacToeCell[]) cellsField.get(panel);

        char[] markers = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', ' '};
        for (int i = 0; i < 9; i++) {
            if (i < 8) {
                cells[i].setMarker(String.valueOf(markers[i]));
            }
            game.board[i] = i < 8 ? markers[i] : ' ';
        }
        game.cplayer = game.player1;
        game.symbol = 'X';

        assertThrows(HeadlessException.class, () ->
                panel.actionPerformed(new ActionEvent(cells[8], 0, "click")));
    }
}
