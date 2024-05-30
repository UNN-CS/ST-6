package org.st6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProgramCellTest {
    private TicTacToeCell cell;

    @BeforeEach
    public void setUp() {
        cell = new TicTacToeCell(1, 0, 0);
    }

    @Test
    public void testCellInitialState() {
        assertEquals(' ', cell.getMarker());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertEquals(1, cell.getNum());
    }

    @Test
    public void testCellSetFont() {
        Font font = cell.getFont();
        assertEquals("Arial", font.getName());
        assertEquals(40, font.getSize());
    }
}
