package org.example;

import org.junit.Assert;
import org.junit.Test;

public class TicTacToeCellTests {
    @Test
    public void setMarkerTest() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        Assert.assertEquals('X', cell.getMarker());
        Assert.assertFalse(cell.isEnabled());
    }

    @Test
    public void gettersTest() {
        TicTacToeCell cell = new TicTacToeCell(5, 2, 1);
        Assert.assertEquals(5, cell.getNum());
        Assert.assertEquals(1, cell.getRow());
        Assert.assertEquals(2, cell.getCol());
    }
}
