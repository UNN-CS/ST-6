package org.example;

import org.junit.Assert;
import org.junit.Test;

public class GameTest {
    @Test
    public void checkStateInitially() {
        Game game = new Game();
        State state = game.checkState(game.board);
        Assert.assertEquals(state, State.PLAYING);
    }

    @Test
    public void checkStateOneXWin() {
        Game game = new Game();
        game.symbol = 'X';
        char[] field = {'X', 'O', 'O', ' ', 'X', 'O', ' ', 'O', 'X'};
        State state = game.checkState(field);
        Assert.assertEquals(state, State.XWIN);
    }
    @Test
    public void checkStateOneOWin() {
        Game game = new Game();
        game.symbol = 'O';
        char[] field = {'O', 'X', 'X', ' ', 'O', 'X', ' ', 'X', 'O'};
        State state = game.checkState(field);
        Assert.assertEquals(state, State.OWIN);
    }
}
