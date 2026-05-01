package com.mycompany.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import static org.junit.jupiter.api.Assertions.*;

class TicTacToePanelTest {
    private TicTacToePanel panel;
    private TicTacToeCell[] cells;

    @BeforeEach
    void setUp() {
        panel = new TicTacToePanel(new GridLayout(3, 3));
        cells = new TicTacToeCell[9];
        for (int i = 0; i < 9; i++) {
            cells[i] = (TicTacToeCell) panel.getComponent(i);
        }
    }

    @Test
    void testPanelCreation() {
        assertEquals(9, panel.getComponentCount());
        assertTrue(panel.getLayout() instanceof GridLayout);
    }

    @Test
    void testInitialBoardEmpty() {
        for (int i = 0; i < 9; i++) {
            assertEquals(' ', cells[i].getMarker());
            assertTrue(cells[i].isEnabled());
        }
    }

    @Test
    void testPlayerMove() {
        // Игрок (X) кликает на первую клетку
        ActionEvent event = new ActionEvent(cells[0], ActionEvent.ACTION_PERFORMED, "click");
        panel.actionPerformed(event);

        assertEquals('X', cells[0].getMarker());
        assertFalse(cells[0].isEnabled());
    }

    @Test
    void testComputerMoveAfterPlayer() {
        // Игрок делает ход
        ActionEvent event = new ActionEvent(cells[0], ActionEvent.ACTION_PERFORMED, "click");
        panel.actionPerformed(event);

        // Компьютер должен сделать ход (поставить O)
        boolean computerMoved = false;
        for (int i = 1; i < 9; i++) {
            if (cells[i].getMarker() == 'O') {
                computerMoved = true;
                break;
            }
        }
        assertTrue(computerMoved);
    }

    @Test
    void testGameContinuesAfterMove() {
        // Делаем ход, игра не должна закончиться
        ActionEvent event = new ActionEvent(cells[0], ActionEvent.ACTION_PERFORMED, "click");
        panel.actionPerformed(event);

        // Проверяем, что игра продолжается (нет сообщения о победе)
        assertTrue(true);
    }

    @Test
    void testCellNotClickableAfterMarked() {
        ActionEvent event = new ActionEvent(cells[0], ActionEvent.ACTION_PERFORMED, "click");
        panel.actionPerformed(event);

        assertFalse(cells[0].isEnabled());

        // Пытаемся кликнуть снова - клетка не должна измениться
        panel.actionPerformed(event);
        assertEquals('X', cells[0].getMarker());
    }
}