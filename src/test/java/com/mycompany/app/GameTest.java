package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class GameTest {
    @Test
    void constructorInitializesEmptyBoardAndPlayers() {
        Game game = new Game();

        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals(game.player1, game.cplayer);
        for (char cell : game.board) {
            assertEquals(' ', cell);
        }
    }

    @Test
    void checkStateDetectsXWinOWinDrawAndPlaying() {
        Game game = new Game();

        char[] xWinBoard = {
            'X', 'X', 'X',
            ' ', 'O', ' ',
            'O', ' ', ' '
        };
        char[] oWinBoard = {
            'X', 'X', ' ',
            'O', 'O', 'O',
            'X', ' ', ' '
        };
        char[] drawBoard = {
            'X', 'O', 'X',
            'X', 'O', 'O',
            'O', 'X', 'X'
        };
        char[] playingBoard = {
            'X', 'O', 'X',
            'O', ' ', 'O',
            ' ', 'X', 'X'
        };

        assertEquals(State.XWIN, game.checkState(xWinBoard));
        assertEquals(State.OWIN, game.checkState(oWinBoard));
        assertEquals(State.DRAW, game.checkState(drawBoard));
        assertEquals(State.PLAYING, game.checkState(playingBoard));
    }

    @Test
    void generateMovesCollectsOnlyFreeCells() {
        Game game = new Game();
        char[] board = {
            'X', ' ', 'O',
            ' ', 'X', ' ',
            'O', ' ', 'X'
        };
        ArrayList<Integer> moves = new ArrayList<>();

        game.generateMoves(board, moves);

        assertIterableEquals(List.of(1, 3, 5, 7), moves);
    }

    @Test
    void evaluatePositionReturnsExpectedScores() {
        Game game = new Game();
        Player xPlayer = game.player1;
        Player oPlayer = game.player2;

        char[] xWinBoard = {
            'X', 'X', 'X',
            ' ', 'O', ' ',
            'O', ' ', ' '
        };
        char[] drawBoard = {
            'X', 'O', 'X',
            'X', 'O', 'O',
            'O', 'X', 'X'
        };
        char[] ongoingBoard = {
            'X', 'O', 'X',
            'O', ' ', 'O',
            ' ', 'X', 'X'
        };

        assertEquals(Game.INF, game.evaluatePosition(xWinBoard, xPlayer));
        assertEquals(-Game.INF, game.evaluatePosition(xWinBoard, oPlayer));
        assertEquals(0, game.evaluatePosition(drawBoard, xPlayer));
        assertEquals(-1, game.evaluatePosition(ongoingBoard, xPlayer));
    }

    @Test
    void minimaxChoosesWinningMoveForPlayerX() {
        Game game = new Game();
        char[] board = {
            'X', 'X', ' ',
            'O', 'O', ' ',
            ' ', ' ', ' '
        };

        int bestMove = game.MiniMax(board, game.player1);

        assertEquals(3, bestMove);
    }

    @Test
    void minAndMaxMoveScoreTerminalPositions() {
        Game game = new Game();
        char[] board = {
            'X', 'X', 'X',
            ' ', 'O', ' ',
            'O', ' ', ' '
        };

        assertEquals(Game.INF, game.MaxMove(board, game.player1));
        assertEquals(Game.INF, game.MinMove(board, game.player1));
    }
}
