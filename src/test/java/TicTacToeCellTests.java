import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicTacToeCellTests {

    @Test
    public void initTicTacToeCellTest() {
        TicTacToeCell tttCell = new TicTacToeCell(4, 2, 2);
        assertEquals(tttCell.getNum(), 4);
        assertEquals(tttCell.getCol(), 2);
        assertEquals(tttCell.getRow(), 2);
        assertEquals(tttCell.getMarker(), ' ');
    }

    @Test
    public void setTicTacToeCellMarkerTest() {
        TicTacToeCell tttCell = new TicTacToeCell(4, 2, 2);
        tttCell.setMarker("X");
        assertEquals(tttCell.getMarker(), 'X');
    }
}
