import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TicTacToeCellTest {

    @Test
    void testGetNum() {
        TicTacToeCell cell = new TicTacToeCell(5, 1, 2);
        assertEquals(5, cell.getNum());
    }

    @Test
    void testGetCol() {
        TicTacToeCell cell = new TicTacToeCell(5, 1, 2);
        assertEquals(1, cell.getCol());
    }

    @Test
    void testGetRow() {
        TicTacToeCell cell = new TicTacToeCell(5, 1, 2);
        assertEquals(2, cell.getRow());
    }

    @Test
    void testInitMarker() {
        TicTacToeCell cell = new TicTacToeCell(1, 1, 1);
        assertEquals(' ', cell.getMarker());
    }

    @Test
    void testMarkerX() {
        TicTacToeCell cell = new TicTacToeCell(2, 0, 1);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
    }

    @Test
    void testMarkerO() {
        TicTacToeCell cell = new TicTacToeCell(3, 1, 0);
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
    }

    @Test
    void testDisableCell() {
        TicTacToeCell cell = new TicTacToeCell(4, 2, 0);
        assertTrue(cell.isEnabled());
        cell.setMarker("X");
        assertFalse(cell.isEnabled());
    }

    @Test
    void testCornerCell() {
        TicTacToeCell cell = new TicTacToeCell(7, 1, 2);
        assertEquals(7, cell.getNum());
        assertEquals(1, cell.getCol());
        assertEquals(2, cell.getRow());
    }

    @Test
    void testBtnText() {
        TicTacToeCell cell = new TicTacToeCell(5, 0, 0);
        cell.setMarker("X");
        assertEquals("X", cell.getText());
    }

    @Test
    void testInitText() {
        TicTacToeCell cell = new TicTacToeCell(6, 1, 1);
        assertEquals(" ", cell.getText());
    }

    @Test
    void testTextX() {
        TicTacToeCell cell = new TicTacToeCell(7, 2, 2);
        cell.setMarker("X");
        assertEquals("X", cell.getText());
    }

    @Test
    void testTextO() {
        TicTacToeCell cell = new TicTacToeCell(8, 0, 2);
        cell.setMarker("O");
        assertEquals("O", cell.getText());
    }

    @Test
    void testCellFont() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals("Arial", cell.getFont().getName());
        assertEquals(40, cell.getFont().getSize());
    }

    @Test
    void testInitEnabled() {
        TicTacToeCell cell = new TicTacToeCell(2, 1, 0);
        assertTrue(cell.isEnabled());
    }

    @Test
    void testMarkerLength() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("OX");
        assertEquals('O', cell.getMarker());
        assertEquals("OX", cell.getText());
    }
}
