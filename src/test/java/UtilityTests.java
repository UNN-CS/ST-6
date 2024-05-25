import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class UtilityTests {

    @Test
    public void utilityPrintCharsTest() {
        char[] chars = {'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X'};
        Utility.print(chars);
    }

    @Test
    public void utilityPrintIntsTest() {
        int[] ints = {5, 4, 2, 7, 8, 9, 1, 3, 6};
        Utility.print(ints);
    }

    @Test
    public void utilityPrintArrayListTest() {
        ArrayList<Integer> moves = new ArrayList<>();

        moves.add(4);
        moves.add(7);
        moves.add(3);
        moves.add(8);
        moves.add(1);

        Utility.print(moves);
    }
}
