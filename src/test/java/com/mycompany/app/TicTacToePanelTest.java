package com.mycompany.app;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TicTacToePanelTest {
    @Test
    public void testPanelCreation() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3,3));
        assertNotNull(panel);
    }
}
