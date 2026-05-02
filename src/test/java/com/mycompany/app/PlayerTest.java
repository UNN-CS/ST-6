package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void testPlayerFields() {
        Player player = new Player();
        player.symbol = 'X';
        player.move = 5;
        player.selected = true;
        player.win = false;

        assertEquals('X', player.symbol, "Символ игрока должен быть 'X'");
        assertEquals(5, player.move, "Ход игрока должен быть равен 5");
        assertTrue(player.selected, "Поле selected должно быть true");
        assertFalse(player.win, "Поле win должно быть false");
    }
}
