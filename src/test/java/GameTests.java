import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class GameTests {

    @Test
    public void testCheckState() {
        Game game = new Game();
        char[] boardXWin = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        char[] boardOWin = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        char[] boardDraw = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        char[] boardPlaying = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};

        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(boardXWin));

        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(boardOWin));

        assertEquals(State.DRAW, game.checkState(boardDraw));
        assertEquals(State.PLAYING, game.checkState(boardPlaying));
    }

    @Test
    public void testGenerateMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        Game game = new Game();
        game.generateMoves(board, moves);
        assertEquals(6, moves.size());
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(4));
    }

    @Test
    public void testCheckStateXWin() {
        char[] field = {'X', 'X', 'X', 'O', 'O', ' ', ' ', ' '};
        Game game = new Game();
        game.symbol = 'X';
        State state = game.checkState(field);
        assertEquals(State.XWIN, state);
    }

    @Test
    public void testCheckStateOWin() {
        Game game = new Game();
        game.symbol = 'O';
        char[] field = {'O', 'O', 'O', 'X', 'X', ' ', 'X', 'X', ' '};
        State state = game.checkState(field);
        assertEquals(State.OWIN, state);
    }

   @Test
    public void testMaxMove() {
        Game game = new Game();
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.player2.symbol = 'O';
        int value = game.MaxMove(board, game.player2);
        assertTrue(value >= -Game.INF && value <= Game.INF);
    }

    @Test
    public void testGameInitialState() {
        Game game = new Game();
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals(' ', game.board[0]);
    }

    @Test
    public void testEvaluatePositionDraw() {
        Game game = new Game();
        game.symbol = 'X';
        game.board = new char[] {'X', 'X', 'O', 'O', 'O', 'X', 'O', 'X', 'O'};
        int result = game.evaluatePosition(game.board, game.player1);
        assertEquals(0, result);
    }

    @Test
    public void testMinimax() {
        Game game = new Game();
        game.board = new char[] {'X', ' ', 'O', ' ', ' ', 'X', ' ', 'X', 'O'};
        game.symbol = 'X';
        int move = game.MiniMax(game.board, game.player2);
        assertTrue(move >= 1 && move <= 9);
    }
}
