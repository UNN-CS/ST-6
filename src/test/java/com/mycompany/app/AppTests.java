package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.awt.GridLayout;

public class AppTests {

    @Test
    void checkPlayerData() {
        Player p = new Player();
        p.symbol = 'O';
        p.move = 3;
        p.selected = false;
        p.win = true;
        assertEquals('O', p.symbol);
        assertEquals(3, p.move);
        assertTrue(p.win);
    }

    @Test
    void checkPrintOut() {
        char[] b = new char[9];
        int[] ib = new int[9];
        ArrayList<Integer> m = new ArrayList<>();
        m.add(1);
        assertDoesNotThrow(() -> {
            Utility.print(b);
            Utility.print(ib);
            Utility.print(m);
        });
    }

    @Test
    void checkCellLogic() {
        TicTacToeCell c = new TicTacToeCell(2, 1, 1);
        c.setMarker("O");
        assertEquals('O', c.getMarker());
        assertEquals(2, c.getNum());
        assertEquals(1, c.getCol());
        assertFalse(c.isEnabled());
    }

    @Test
    void checkCellCoords() {
        TicTacToeCell c = new TicTacToeCell(9, 2, 2);
        assertEquals(2, c.getRow());
        assertEquals(2, c.getCol());
    }

    @Test
    void checkEmptyListPrint() {
        ArrayList<Integer> empty = new ArrayList<>();
        assertDoesNotThrow(() -> Utility.print(empty));
    }

    @Test
    void checkPanel() {
        assertDoesNotThrow(() -> {
            GridLayout layout = new GridLayout(3, 3);
            TicTacToePanel panel = new TicTacToePanel(layout);
            assertNotNull(panel);
        });
    }

    @Test
    void checkProgram() {
        assertDoesNotThrow(() -> {
            Program p = new Program();
            assertNotNull(p);
        });
    }
}