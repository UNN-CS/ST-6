package org.st6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProgramTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }


    @Test
    public void testGameEvaluatePosition() {
        char[] boardXWin = {'X', ' ', ' ', 'X', ' ', ' ', 'X', ' ', ' '};
        char[] boardDraw = {'O', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};

        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(boardXWin, game.player1));
        assertEquals(0, game.evaluatePosition(boardDraw, game.player1));
    }

    @Test
    public void testGameCheckState() {
        char[] boardXWin = {'X', ' ', ' ', 'X', ' ', ' ', 'X', ' ', ' '};
        char[] boardOWin = {'O', ' ', ' ', ' ', 'O', ' ', ' ', ' ', 'O'};
        char[] boardDraw = {'O', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        char[] boardPlaying = {'O', 'X', 'O', ' ', ' ', ' ', ' ', ' ', ' '};

        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(boardXWin));
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(boardOWin));
        assertEquals(State.DRAW, game.checkState(boardDraw));
        assertEquals(State.PLAYING, game.checkState(boardPlaying));
    }

    @Test
    public void testGameGenerateMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size());
    }

    @Test
    public void testGameCheckStatePlaying() {
        assertEquals(State.PLAYING, game.checkState(game.board));
    }

    @Test
    public void testGameInitialState() {
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals(' ', game.board[0]);
    }

    @Test
    public void testGameMinimax() {
        Game game = new Game();
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.player2.symbol = 'O';
        int move = game.MiniMax(board, game.player2);
        assertEquals(true, move >= 1);
        assertEquals(true, move <= 9);
    }

    @Test
    public void testGameMinMove() {
        Game game = new Game();
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.player2.symbol = 'O';
        int value = game.MinMove(board, game.player2);
        assertEquals(true, value >= -Game.INF);
        assertEquals(true, value <= Game.INF);
    }

    @Test
    public void testGameMaxMove() {
        Game game = new Game();
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.player2.symbol = 'O';
        int value = game.MaxMove(board, game.player2);
        assertEquals(true, value >= -Game.INF);
        assertEquals(true, value <= Game.INF);
    }

}
