package org.example;

import org.example.Player;
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
        assertAll("Checking initial values",
                () -> assertFalse(player.selected, "org.example.Player should not be selected initially"),
                () -> assertFalse(player.win, "org.example.Player should not win initially"),
                () -> assertEquals(0, player.move, "Initial move should be 0")
        );
    }

    @Test
    public void testSymbol() {
        player.symbol = 'X';
        assertEquals('X', player.symbol, "org.example.Player symbol should be set to 'X'");
    }

    @Test
    public void testMove() {
        player.move = 1;
        assertEquals(1, player.move, "org.example.Player move should be set to 1");
    }

    @Test
    public void testSelected() {
        player.selected = true;
        assertTrue(player.selected, "org.example.Player should be selected");
    }

    @Test
    public void testWin() {
        player.win = true;
        assertTrue(player.win, "org.example.Player should win");
    }
}
