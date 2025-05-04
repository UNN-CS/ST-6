package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.awt.GridLayout;

public class ProgramTest {
    @Test
    public void testTicTacToePanel() {
        System.setProperty("java.awt.headless", "true");

        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        TicTacToeCell cell = (TicTacToeCell) panel.getComponent(0);
        cell.doClick();

        assertEquals(cell.getMarker(), 'X');
    }
}
