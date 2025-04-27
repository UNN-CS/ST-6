package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.awt.event.ActionEvent;

public class GUITest {

    @Test
    void testTicTacToeCellConstructorAndGetters() {
        TicTacToeCell cell = new TicTacToeCell(5, 1, 2);

        assertEquals(5, cell.getNum(), "getNum should return the correct cell number");
        assertEquals(1, cell.getCol(), "getCol should return the correct column");
        assertEquals(2, cell.getRow(), "getRow should return the correct row");
        assertEquals(' ', cell.getMarker(), "Initial marker should be space");
        assertEquals(" ", cell.getText(), "Initial text should be space");
        assertTrue(cell.isEnabled(), "Cell should be enabled initially");
    }

    @Test
    void testTicTacToeCellSetMarkerX() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);
        cell.setMarker("X");

        assertEquals('X', cell.getMarker(), "setMarker should set the marker");
        assertEquals("X", cell.getText(), "setMarker should set the button text");
        assertFalse(cell.isEnabled(), "setMarker should disable the cell");
    }

    @Test
    void testTicTacToeCellSetMarkerO() {
        TicTacToeCell cell = new TicTacToeCell(7, 2, 2);
        cell.setMarker("O");

        assertEquals('O', cell.getMarker(), "setMarker should set the marker");
        assertEquals("O", cell.getText(), "setMarker should set the button text");
        assertFalse(cell.isEnabled(), "setMarker should disable the cell");
    }

    // Test for TicTacToePanel constructor
    @Test
    void testTicTacToePanelConstructor() {
        GridLayout layout = new GridLayout(3, 3);
        TicTacToePanel panel = new TicTacToePanel(layout);

        assertNotNull(panel, "TicTacToePanel should be created");
        assertEquals(layout, panel.getLayout(), "TicTacToePanel should have the correct layout");
        assertEquals(9, panel.getComponentCount(), "TicTacToePanel should contain 9 components (cells)");
    }

    @Test
    void testTicTacToePanelActionPerformed_EntryPoint() {
        GridLayout layout = new GridLayout(3, 3);
        TicTacToePanel panel = new TicTacToePanel(layout);

        Component firstComponent = panel.getComponent(0);
        assertTrue(firstComponent instanceof TicTacToeCell, "First component should be a TicTacToeCell");
        TicTacToeCell firstCell = (TicTacToeCell) firstComponent;

        ActionEvent dummyEvent = new ActionEvent(firstCell, ActionEvent.ACTION_PERFORMED, "test_click");

        assertDoesNotThrow(() -> panel.actionPerformed(dummyEvent), "actionPerformed should be callable with a dummy event");
    }
}