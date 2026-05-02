package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

public class UITest {

    @Test
    public void testTicTacToeCell() {
        TicTacToeCell cell = new TicTacToeCell(5, 1, 2);
        assertEquals(5, cell.getNum(), "Номер ячейки должен быть 5");
        assertEquals(1, cell.getCol(), "Колонка должна быть 1");
        assertEquals(2, cell.getRow(), "Строка должна быть 2");
        assertEquals(' ', cell.getMarker(), "Изначально ячейка должна быть пустой");

        cell.setMarker("X");
        assertEquals('X', cell.getMarker(), "Маркер должен стать 'X'");
        assertFalse(cell.isEnabled(), "После установки маркера ячейка должна блокироваться (isEnabled = false)");
        assertEquals("X", cell.getText(), "Текст на кнопке должен измениться");
    }

    @Test
    public void testTicTacToePanelInitialState() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3,3));
        assertEquals(9, panel.getComponentCount(), "На панели должно быть ровно 9 ячеек (кнопок)");
    }

    @Test
    public void testTicTacToePanelSafeMove() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3,3));
        
        TicTacToeCell firstCell = (TicTacToeCell) panel.getComponent(0);
        
        ActionEvent event = new ActionEvent(firstCell, ActionEvent.ACTION_PERFORMED, "");
        panel.actionPerformed(event);

        assertEquals('X', firstCell.getMarker(), "После клика должен установиться крестик");

        boolean hasO = false;
        for (int i = 0; i < 9; i++) {
            TicTacToeCell cell = (TicTacToeCell) panel.getComponent(i);
            if (cell.getMarker() == 'O') {
                hasO = true;
                break;
            }
        }
        assertTrue(hasO, "Алгоритм MiniMax должен был сделать ответный ход ноликом");
    }
}
