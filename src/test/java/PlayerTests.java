//
// Кашин Степан 2024
//
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
public class PlayerTests {
    @Test
    public void testSymbol() {
        Player player = new Player();
        player.symbol = 'X';
        assertEquals('X', player.symbol);

        player.symbol = 'O';
        assertEquals('O', player.symbol);
    }

    @Test
    public void testValues() {
        Player player = new Player();
        assertFalse(player.selected);
        assertFalse(player.win);
        assertEquals(0, player.move);

        player.selected = true;
        player.win = true;
        player.move = 5;
        assertTrue(player.selected);
        assertTrue(player.win);
        assertEquals(5, player.move);
    }
}
