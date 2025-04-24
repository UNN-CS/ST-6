package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TicTacToePanelTest {
    private TicTacToePanel panel;

    @BeforeEach
    public void setUp() {
        panel = new TicTacToePanel(new GridLayout(3, 3));
    }

    @Test
    public void testPanelInitialization() throws NoSuchFieldException, IllegalAccessException {
        Field cellsField = TicTacToePanel.class.getDeclaredField("cells");
        cellsField.setAccessible(true);
        TicTacToeCell[] cells = (TicTacToeCell[]) cellsField.get(panel);
        assertEquals(9, cells.length);
        assertEquals(0, cells[0].getNum());
        assertEquals(4, cells[4].getNum());
        assertEquals(8, cells[8].getNum());

        for (int i = 0; i < 9; i++) {
            assertEquals(' ', cells[i].getMarker());
        }

        Field gameField = TicTacToePanel.class.getDeclaredField("game");
        gameField.setAccessible(true);
        Game game = (Game) gameField.get(panel);
        
        assertNotNull(game);
        assertEquals(game.player1, game.cplayer);
        assertEquals('X', game.cplayer.symbol);
    }

    @Test
    public void testCreateCell() throws NoSuchFieldException, IllegalAccessException {
        Field cellsField = TicTacToePanel.class.getDeclaredField("cells");
        cellsField.setAccessible(true);
        TicTacToeCell[] cells = (TicTacToeCell[]) cellsField.get(panel);
        
        assertEquals(0, cells[0].getCol());
        assertEquals(0, cells[0].getRow());
        
        assertEquals(1, cells[1].getCol());
        assertEquals(0, cells[1].getRow());
        
        assertEquals(0, cells[3].getCol());
        assertEquals(1, cells[3].getRow());
        
        assertEquals(2, cells[8].getCol());
        assertEquals(2, cells[8].getRow());
    }
    
    @Test
    public void testActionPerformedFirstMove() throws NoSuchFieldException, IllegalAccessException {
        Field cellsField = TicTacToePanel.class.getDeclaredField("cells");
        cellsField.setAccessible(true);
        TicTacToeCell[] cells = (TicTacToeCell[]) cellsField.get(panel);
        
        ActionEvent event = new ActionEvent(cells[0], ActionEvent.ACTION_PERFORMED, null);
        panel.actionPerformed(event);
        assertEquals('X', cells[0].getMarker());
        
        boolean foundO = false;
        for (int i = 0; i < 9; i++) {
            if (cells[i].getMarker() == 'O') {
                foundO = true;
                break;
            }
        }
        assertTrue(foundO, "Компьютер должен сделать ответный ход");
        
        Field gameField = TicTacToePanel.class.getDeclaredField("game");
        gameField.setAccessible(true);
        Game game = (Game) gameField.get(panel);
        
        assertEquals(game.player1, game.cplayer);
    }
} 