package org.st6;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProgramPlayerTest {
    private Player player;
    @BeforeEach
    public void setUp() {
        player = new Player();
    }

    @Test
    public void testPlayerSymbol() {
        player.symbol = 'O';
        assertEquals('O', player.symbol);
        player.symbol = 'X';
        assertEquals('X', player.symbol);
    }

    @Test
    public void testPlayerMove() {
        player.move = 1;
        assertEquals(1, player.move);
    }

}
