import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTests {

    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player();
    }

    @Test
    public void testInitialValues() {
        assertFalse(player.selected);
        assertFalse(player.win);
        assertEquals(0, player.move);
    }

    @Test
    public void testSymbol() {
        player.symbol = 'X';
        assertEquals('X', player.symbol);
    }

    @Test
    public void testMove() {
        player.move = 1;
        assertEquals(1, player.move);
    }

    @Test
    public void testSelected() {
        player.selected = true;
        assertTrue(player.selected);
    }

    @Test
    public void testWin() {
        player.win = true;
        assertTrue(player.win);
    }
}