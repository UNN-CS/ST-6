package com.mycompany.app;

import org.junit.jupiter.api.Test;
import java.awt.GridLayout;

import static org.junit.jupiter.api.Assertions.*;

public class TicTacToePanelTest {

    @Test
    void testPanelCreation() {
        TicTacToePanel panel =
                new TicTacToePanel(new GridLayout(3,3));

        assertNotNull(panel);
    }
}
