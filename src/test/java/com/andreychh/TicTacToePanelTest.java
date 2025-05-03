package com.andreychh;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToePanelTest {
    private TicTacToePanel ticTacToePanel;

    @BeforeEach
    void setUp() {
        ticTacToePanel = new TicTacToePanel(new GridLayout(3, 3));
    }

    @Test
    void testConstructor_WhenCalled_PanelIsNotNull() {
        assertNotNull(ticTacToePanel);
    }

    @Test
    void testConstructor_WhenCalled_HasNineCells() {
        assertEquals(9, ticTacToePanel.getComponentCount());
    }

    @Test
    void testConstructor_WhenCalled_ComponentsAreTicTacToeCells() {
        for (Component comp : ticTacToePanel.getComponents()) {
            assertInstanceOf(TicTacToeCell.class, comp);
        }
    }

    @Test
    void testConstructor_WhenCalled_CellsHaveDefaultState() {
        for (Component comp : ticTacToePanel.getComponents()) {
            TicTacToeCell cell = (TicTacToeCell) comp;
            assertTrue(cell.isEnabled());
            assertEquals(' ', cell.getMarker());
            assertEquals(" ", cell.getText());
        }
    }
}
