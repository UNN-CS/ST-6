import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class UtilityTest {

    @Test
    void testPrintChar() {
        char[] board = {'O','X','O',' ','X',' ',' ','O','X'};
        assertDoesNotThrow(() -> Utility.print(board));
    }

    @Test
    void testPrintCharEmpty() {
        char[] board = {' ',' ',' ',' ',' ',' ',' ',' ',' '};
        assertDoesNotThrow(() -> Utility.print(board));
    }

    @Test
    void testPrintInt() {
        int[] board = {9,8,7,6,5,4,3,2,1};
        assertDoesNotThrow(() -> Utility.print(board));
    }

    @Test
    void testPrintIntZeros() {
        int[] board = {0,0,0,0,0,0,0,0,0};
        assertDoesNotThrow(() -> Utility.print(board));
    }

    @Test
    void testPrintList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(2);
        moves.add(4);
        moves.add(6);
        assertDoesNotThrow(() -> Utility.print(moves));
    }

    @Test
    void testPrintListEmpty() {
        ArrayList<Integer> moves = new ArrayList<>();
        assertDoesNotThrow(() -> Utility.print(moves));
    }
}
