import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {

    @Test
    public void TicTacToeGameInitTest() {
        Game game = new Game();

        assertEquals(game.state, State.PLAYING);
        assertEquals(game.player1.symbol, 'X');
        assertEquals(game.player2.symbol, 'O');
        assertEquals(game.board.length, 9);
    }

    @Test
    public void TicTacToeGameCheckStateTest(){
        Game game = new Game();
        char[] X = {
                'X', 'O', 'X',
                'O', 'X', 'O',
                'X', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(game.checkState(X), State.XWIN);

        char[] O = {
                'X', 'O', 'X',
                ' ', 'O', 'X',
                ' ', 'O', ' '
        };
        game.symbol = 'O';
        assertEquals(game.checkState(O), State.OWIN);

        char[] Draw = {
                'X', 'O', 'X',
                'X', 'O', 'O',
                'O', 'X', 'X'
        };
        assertEquals(game.checkState(Draw), State.DRAW);

        char[] Playing = {
                ' ', ' ', ' ',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        assertEquals(game.checkState(Playing), State.PLAYING);
    }

    @Test
    public void TicTacToeGameGenerateMovesTest() {
        Game game = new Game();
        char[] moves = {
                'X', 'O', 'X',
                'O', ' ', 'X',
                'O', ' ', 'X'
        };
        ArrayList<Integer> list = new ArrayList<>();

        game.generateMoves(moves, list);

        assertTrue(list.contains(4));
        assertTrue(list.contains(7));
        assertFalse(list.contains(1));
        assertFalse(list.contains(8));

        assertEquals(list.size(), 2);
    }

    @Test
    public void TicTacToeGameEvaluatePosTest() {
        Game game = new Game();
        char[] X = {
                'X', 'O', 'O',
                ' ', 'X', 'O',
                ' ', ' ', 'X'
        };
        char[] Draw = {
                'X', 'X', 'O',
                'O', 'O', 'X',
                'X', 'O', 'X'
        };

        game.symbol = 'X';

        assertEquals(game.evaluatePosition(X, game.player1), Game.INF);
        assertEquals(game.evaluatePosition(Draw, game.player1), 0);
    }

    @Test
    public void TicTacToeGameMiniMaxTest() {
        Game game = new Game();
        char[] moves = {
                'X', ' ', ' ',
                ' ', 'O', ' ',
                ' ', ' ', ' '
        };

        game.player1.symbol = 'X';
        int value = game.MiniMax(moves, game.player1);

        assertTrue(value >= -Game.INF && value <= Game.INF);
    }

    @Test
    public void TicTacToeGameMinMoveTest() {
        Game game = new Game();
        char[] moves = {
                ' ', ' ', 'O',
                ' ', 'X', ' ',
                ' ', ' ', ' '
        };

        game.player1.symbol = 'X';
        int value = game.MinMove(moves, game.player1);

        assertTrue(value >= -Game.INF && value <= Game.INF);
    }

    @Test
    public void TicTacToeGameMaxMoveTest() {
        Game game = new Game();
        char[] moves = {
                ' ', ' ', ' ',
                ' ', ' ', ' ',
                'X', ' ', 'O'
        };

        game.player1.symbol = 'X';
        int value = game.MaxMove(moves, game.player1);

        assertTrue(value >= -Game.INF && value <= Game.INF);
    }
}
