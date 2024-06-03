//
// Кашин Степан 2024
//
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
public class TicTacToeCellTests {
    @Test
    public void testGetRow() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(0, cell.getRow());
    }

    @Test
    public void testGetCol() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(0, cell.getCol());
    }

    @Test
    public void testGetNum() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(0, cell.getNum());
    }

    @Test
    public void testInitialValues() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        Assertions.assertEquals(' ', cell.getMarker());
        Assertions.assertEquals(0, cell.getNum());
        Assertions.assertEquals(0, cell.getRow());
        Assertions.assertEquals(0, cell.getCol());
    }

    @Test
    public void testSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

}