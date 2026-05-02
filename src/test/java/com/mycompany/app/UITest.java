package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

public class UITest {

    @Test
    public void testTicTacToeCell() {
        // Проверяем конструктор и геттеры ячейки
        TicTacToeCell cell = new TicTacToeCell(5, 1, 2);
        assertEquals(5, cell.getNum(), "Номер ячейки должен быть 5");
        assertEquals(1, cell.getCol(), "Колонка должна быть 1");
        assertEquals(2, cell.getRow(), "Строка должна быть 2");
        assertEquals(' ', cell.getMarker(), "Изначально ячейка должна быть пустой");

        // Проверяем логику установки маркера
        cell.setMarker("X");
        assertEquals('X', cell.getMarker(), "Маркер должен стать 'X'");
        assertFalse(cell.isEnabled(), "После установки маркера ячейка должна блокироваться (isEnabled = false)");
        assertEquals("X", cell.getText(), "Текст на кнопке должен измениться");
    }

    @Test
    public void testTicTacToePanelInitialState() {
        // Проверяем, что панель корректно инициализирует все 9 кнопок
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3,3));
        assertEquals(9, panel.getComponentCount(), "На панели должно быть ровно 9 ячеек (кнопок)");
    }

    @Test
    public void testTicTacToePanelSafeMove() {
        // Тестируем один безопасный ход, чтобы покрыть метод actionPerformed, но не вызвать System.exit(0)
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3,3));
        
        // Берем первую ячейку с доски
        TicTacToeCell firstCell = (TicTacToeCell) panel.getComponent(0);
        
        // Искусственно вызываем событие клика по этой ячейке
        ActionEvent event = new ActionEvent(firstCell, ActionEvent.ACTION_PERFORMED, "");
        panel.actionPerformed(event);

        // 1. Проверяем, что крестик поставился
        assertEquals('X', firstCell.getMarker(), "После клика должен установиться крестик");

        // 2. Так как панель автоматически заставляет бота (нолик) сделать ответный ход, 
        // проверим, что на доске действительно появился 'O'.
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
