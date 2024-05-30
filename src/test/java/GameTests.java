import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {
    @Test
    public void TicTacToeGameGenerateMovesEmptyBoardTest() {
        Game game = new Game();
        char[] moves = {
                ' ', ' ', ' ',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        ArrayList<Integer> list = new ArrayList<>();

        game.generateMoves(moves, list);

        assertEquals(list.size(), 9);
        for (int i = 0; i < 9; i++) {
            assertTrue(list.contains(i));
        }
    }

    @Test
    public void TicTacToeGameGenerateMovesFullBoardTest() {
        Game game = new Game();
        char[] moves = {
                'X', 'O', 'X',
                'O', 'X', 'O',
                'X', 'O', 'X'
        };
        ArrayList<Integer> list = new ArrayList<>();

        game.generateMoves(moves, list);

        assertEquals(list.size(), 0);
    }

    @Test
    public void TicTacToeGameCheckStateVerticalWinTest() {
        Game game = new Game();
        char[] moves = {
                'X', 'O', ' ',
                'X', 'O', ' ',
                'X', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(game.checkState(moves), State.XWIN);
    }

    @Test
    public void TicTacToeGameCheckStateHorizontalWinTest() {
        Game game = new Game();
        char[] moves = {
                'O', 'O', 'O',
                'X', 'X', ' ',
                ' ', ' ', 'X'
        };
        game.symbol = 'O';
        assertEquals(game.checkState(moves), State.OWIN);
    }

    @Test
    public void TicTacToeGameEvaluatePositionDrawTest() {
        Game game = new Game();
        char[] moves = {
                'X', 'O', 'X',
                'O', 'X', 'O',
                'O', 'X', 'O'
        };
        int value = game.evaluatePosition(moves, game.player1);

        assertEquals(value, 0);
    }

    @Test
    public void TicTacToeGameEvaluatePositionPlayingTest() {
        Game game = new Game();
        char[] moves = {
                'X', 'O', 'X',
                'O', ' ', 'X',
                ' ', 'X', 'O'
        };
        int value = game.evaluatePosition(moves, game.player1);

        assertEquals(value, -1);
    }

    @Test
    public void TicTacToeGameMinMoveTest() {
        Game game = new Game();
        char[] moves = {
                'X', ' ', ' ',
                ' ', 'O', ' ',
                ' ', ' ', ' '
        };

        int value = game.MinMove(moves, game.player2);

        assertTrue(value >= -Game.INF && value <= Game.INF);
    }

    @Test
    public void TicTacToeGameMaxMoveTest() {
        Game game = new Game();
        char[] moves = {
                'O', ' ', ' ',
                ' ', 'X', ' ',
                ' ', ' ', ' '
        };

        int value = game.MaxMove(moves, game.player1);

        assertTrue(value >= -Game.INF && value <= Game.INF);
    }

    @Test
    public void TicTacToePlayerInitializationTest() {
        Player player = new Player();
        player.symbol = 'X';
        player.move = 0;
        player.selected = false;
        player.win = false;

        assertEquals(player.symbol, 'X');
        assertEquals(player.move, 0);
        assertFalse(player.selected);
        assertFalse(player.win);
    }

    @Test
    public void TicTacToeGamePlayerSwitchTest() {
        Game game = new Game();
        game.cplayer = game.player1;

        assertEquals(game.cplayer, game.player1);

        game.cplayer = game.player2;

        assertEquals(game.cplayer, game.player2);
    }
}