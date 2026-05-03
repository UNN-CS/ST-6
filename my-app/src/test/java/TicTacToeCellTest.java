import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TicTacToeCellTest {

    @Test
    void testConstructorGetNum() {
        TicTacToeCell cell = new TicTacToeCell(4, 2, 1);
        assertEquals(4, cell.getNum());
    }

    @Test
    void testConstructorGetCol() {
        TicTacToeCell cell = new TicTacToeCell(4, 2, 1);
        assertEquals(2, cell.getCol());
    }

    @Test
    void testConstructorGetRow() {
        TicTacToeCell cell = new TicTacToeCell(4, 2, 1);
        assertEquals(1, cell.getRow());
    }

    @Test
    void testInitialMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(' ', cell.getMarker());
    }

    @Test
    void testSetMarkerX() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
    }

    @Test
    void testSetMarkerO() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
    }

    @Test
    void testSetMarkerDisablesCell() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertTrue(cell.isEnabled());
        cell.setMarker("X");
        assertFalse(cell.isEnabled());
    }

    @Test
    void testCellAtCorner() {
        TicTacToeCell cell = new TicTacToeCell(8, 2, 2);
        assertEquals(8, cell.getNum());
        assertEquals(2, cell.getCol());
        assertEquals(2, cell.getRow());
    }

    @Test
    void testSetMarkerChangesButtonText() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);

        cell.setMarker("X");

        assertEquals("X", cell.getText());
    }
    @Test
void testInitialTextIsBlank() {
    TicTacToeCell cell = new TicTacToeCell(0, 0, 0);

    assertEquals(" ", cell.getText());
}

@Test
void testSetMarkerChangesButtonTextToX() {
    TicTacToeCell cell = new TicTacToeCell(0, 0, 0);

    cell.setMarker("X");

    assertEquals("X", cell.getText());
}

@Test
void testSetMarkerChangesButtonTextToO() {
    TicTacToeCell cell = new TicTacToeCell(0, 0, 0);

    cell.setMarker("O");

    assertEquals("O", cell.getText());
}

@Test
void testFontIsConfiguredInConstructor() {
    TicTacToeCell cell = new TicTacToeCell(0, 0, 0);

    assertEquals("Arial", cell.getFont().getName());
    assertEquals(40, cell.getFont().getSize());
}

@Test
void testCellIsEnabledInitially() {
    TicTacToeCell cell = new TicTacToeCell(0, 0, 0);

    assertTrue(cell.isEnabled());
}

@Test
void testSetMarkerUsesFirstCharacterOnly() {
    TicTacToeCell cell = new TicTacToeCell(0, 0, 0);

    cell.setMarker("XO");

    assertEquals('X', cell.getMarker());
    assertEquals("XO", cell.getText());
}
}
