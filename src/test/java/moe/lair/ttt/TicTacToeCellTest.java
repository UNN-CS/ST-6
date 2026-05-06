package moe.lair.ttt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.Font;
import org.junit.jupiter.api.Test;

class TicTacToeCellTest {
    @Test
    void constructorStoresCoordinatesNumberAndBlankMarker() {
        TicTacToeCell cell = new TicTacToeCell(5, 2, 1);

        assertEquals(5, cell.getNum());
        assertEquals(2, cell.getCol());
        assertEquals(1, cell.getRow());
        assertEquals(' ', cell.getMarker());
        assertEquals(" ", cell.getText());
        assertNotNull(cell.getFont());
        assertEquals(Font.PLAIN, cell.getFont().getStyle());
    }

    @Test
    void setMarkerChangesTextMarkerAndDisablesCell() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);

        cell.setMarker("X");

        assertEquals('X', cell.getMarker());
        assertEquals("X", cell.getText());
        assertFalse(cell.isEnabled());
    }
}
