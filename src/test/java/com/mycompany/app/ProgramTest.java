package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.awt.GridLayout;

public class ProgramTest {

    @BeforeAll
    public static void setUp() {
        System.setProperty("java.awt.headless", "true");
    }

    @Test
    public void testTicTacToePanel() {
        final var panel = new TicTacToePanel(new GridLayout(3, 3));
        final var cell = (TicTacToeCell) panel.getComponent(0);

        assertEquals(' ', cell.getMarker());

        cell.doClick();
        assertEquals('X', cell.getMarker());
    }
}
