import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class TicTacToeCellTests {

    @Test
    void testTicTacToeCellInitialState() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(' ', cell.getMarker());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertEquals(0, cell.getNum());
        assertEquals(" ", cell.getText());
    }

    @Test
    void testTicTacToeCellSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertEquals("X", cell.getText());
        assertFalse(cell.isEnabled());
    }

    @Test
    void testTicTacToeCellSetMarkerO() {
        TicTacToeCell cell = new TicTacToeCell(1, 1, 1);
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
        assertEquals("O", cell.getText());
        assertFalse(cell.isEnabled());
    }

    @Test
    void testTicTacToeCellGetters() {
        TicTacToeCell cell = new TicTacToeCell(2, 2, 1);
        assertEquals(2, cell.getNum());
        assertEquals(1, cell.getRow());
        assertEquals(2, cell.getCol());
    }
}