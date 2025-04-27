package com.mycompany.app;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class TicTacToePanelTest {

    @Test
    public void testPanelInitialization() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        assertNotNull(panel);
    }

    @Test
    public void testCreateCell() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        Component[] components = panel.getComponents();
        assertEquals(9, components.length);
        for (Component component : components) {
            assertTrue(component instanceof TicTacToeCell);
        }
    }
}