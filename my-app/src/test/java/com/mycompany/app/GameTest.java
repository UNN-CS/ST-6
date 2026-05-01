package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class GameTest {
    @Test
    void constructorInitializesPlayersAndBoard() {
        Game game = new Game();

        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals(9, game.board.length);
        for (char cell : game.board) {
            assertEquals(' ', cell);
        }
    }

    @Test
    void checkStateDetectsXWin() {
        Game game = new Game();
        game.symbol = 'X';

        State state = game.checkState(new char[] {'X', 'X', 'X', ' ', 'O', ' ', 'O', ' ', ' '});

        assertEquals(State.XWIN, state);
    }

    @Test
    void checkStateDetectsOWin() {
        Game game = new Game();
        game.symbol = 'O';

        State state = game.checkState(new char[] {'O', 'X', 'X', ' ', 'O', ' ', 'X', ' ', 'O'});

        assertEquals(State.OWIN, state);
    }

    @Test
    void checkStateDetectsDraw() {
        Game game = new Game();
        game.symbol = 'X';

        State state = game.checkState(new char[] {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'});

        assertEquals(State.DRAW, state);
    }

    @Test
    void checkStateDetectsPlaying() {
        Game game = new Game();
        game.symbol = 'X';

        State state = game.checkState(new char[] {'X', 'O', 'X', ' ', 'O', ' ', 'O', 'X', ' '});

        assertEquals(State.PLAYING, state);
    }

    @Test
    void generateMovesCollectsEmptyCells() {
        Game game = new Game();
        ArrayList<Integer> moves = new ArrayList<>();

        game.generateMoves(new char[] {'X', ' ', 'O', ' ', ' ', 'X', 'O', ' ', 'X'}, moves);

        assertEquals(4, moves.size());
        assertEquals(1, moves.get(0));
        assertEquals(3, moves.get(1));
        assertEquals(4, moves.get(2));
        assertEquals(7, moves.get(3));
    }

    @Test
    void evaluatePositionReturnsPositiveForWinningPlayer() {
        Game game = new Game();
        game.symbol = 'X';

        int value = game.evaluatePosition(
                new char[] {'X', 'X', 'X', ' ', 'O', ' ', 'O', ' ', ' '},
                game.player1);

        assertEquals(Game.INF, value);
    }

    @Test
    void evaluatePositionReturnsNegativeForLosingPlayer() {
        Game game = new Game();
        game.symbol = 'X';

        int value = game.evaluatePosition(
                new char[] {'X', 'X', 'X', ' ', 'O', ' ', 'O', ' ', ' '},
                game.player2);

        assertEquals(-Game.INF, value);
    }

    @Test
    void evaluatePositionReturnsZeroForDraw() {
        Game game = new Game();
        game.symbol = 'X';

        int value = game.evaluatePosition(
                new char[] {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'},
                game.player1);

        assertEquals(0, value);
    }

    @Test
    void evaluatePositionReturnsMinusOneForNonTerminalPosition() {
        Game game = new Game();
        game.symbol = 'X';

        int value = game.evaluatePosition(
                new char[] {'X', 'O', 'X', ' ', 'O', ' ', 'O', 'X', ' '},
                game.player1);

        assertEquals(-1, value);
    }

    @Test
    void minimaxFindsImmediateWinningMove() {
        Game game = new Game();
        char[] board = {'O', 'O', ' ', 'X', 'X', ' ', ' ', ' ', ' '};

        int move = game.miniMax(board, game.player2);

        assertEquals(3, move);
    }

    @Test
    void minAndMaxReturnTerminalValues() {
        Game game = new Game();
        game.symbol = 'X';
        char[] winBoard = {'X', 'X', 'X', ' ', 'O', ' ', 'O', ' ', ' '};

        assertEquals(Game.INF, game.minMove(winBoard, game.player1));
        assertEquals(Game.INF, game.maxMove(winBoard, game.player1));
    }

    @Test
    void minimaxReturnsAvailableCellNumber() {
        Game game = new Game();
        char[] board = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', ' '};

        int move = game.miniMax(board, game.player2);

        assertTrue(move >= 1 && move <= 9);
        assertEquals(9, move);
    }
}
