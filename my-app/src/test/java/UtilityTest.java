import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class UtilityTest {

    @Test
    void testPrintCharArray() {
        char[] board = {'X','O',' ','X','O',' ','X','O',' '};
        assertDoesNotThrow(() -> Utility.print(board));
    }

    @Test
    void testPrintCharArrayEmpty() {
        char[] board = {' ',' ',' ',' ',' ',' ',' ',' ',' '};
        assertDoesNotThrow(() -> Utility.print(board));
    }

    @Test
    void testPrintIntArray() {
        int[] board = {1,2,3,4,5,6,7,8,9};
        assertDoesNotThrow(() -> Utility.print(board));
    }

    @Test
    void testPrintIntArrayZeros() {
        int[] board = {0,0,0,0,0,0,0,0,0};
        assertDoesNotThrow(() -> Utility.print(board));
    }

    @Test
    void testPrintArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(3);
        moves.add(5);
        assertDoesNotThrow(() -> Utility.print(moves));
    }

    @Test
    void testPrintArrayListEmpty() {
        ArrayList<Integer> moves = new ArrayList<>();
        assertDoesNotThrow(() -> Utility.print(moves));
    }
}
