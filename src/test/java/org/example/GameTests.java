package org.example;

import org.example.Game;
import org.example.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class GameTests {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testInitialState() {
        assertAll("Checking initial state",
                () -> assertEquals(org.example.State.PLAYING, game.state, "org.example.Game state should be PLAYING"),
                () -> assertEquals('X', game.player1.symbol, "org.example.Player 1 symbol should be 'X'"),
                () -> assertEquals('O', game.player2.symbol, "org.example.Player 2 symbol should be 'O'"),
                () -> assertEquals(' ', game.board[0], "Initial board position should be empty")
        );
    }

    @Test
    public void testCheckStatePlaying() {
        assertEquals(State.PLAYING, game.checkState(game.board), "org.example.Game state should be PLAYING when no one has won yet");
    }

    @Test
    public void testCheckStateXWin() {
        game.board[0] = game.board[1] = game.board[2] = 'X';
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board), "org.example.Game state should be XWIN when X has won");
    }

    @Test
    public void testCheckStateOWin() {
        game.board[0] = game.board[1] = game.board[2] = 'O';
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(game.board), "org.example.Game state should be OWIN when O has won");
    }

    @Test
    public void testCheckStateDraw() {
        game.board[0] = 'X';
        game.board[1] = 'O';
        game.board[2] = 'X';
        game.board[3] = 'X';
        game.board[4] = 'O';
        game.board[5] = 'O';
        game.board[6] = 'O';
        game.board[7] = 'X';
        game.board[8] = 'X';
        assertEquals(State.DRAW, game.checkState(game.board), "org.example.Game state should be DRAW when no moves left and no one has won");
    }

    @Test
    public void testGenerateMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size(), "Initially, there should be 9 possible moves");
    }

    @Test
    public void testEvaluatePositionXWin() {
        game.board[0] = game.board[1] = game.board[2] = 'X';
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(game.board, game.player1), "Evaluation should be INF when X has won");
    }

    @Test
    public void testEvaluatePositionOWin() {
        game.board[0] = game.board[1] = game.board[2] = 'O';
        game.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(game.board, game.player2), "Evaluation should be INF when O has won");
    }

    @Test
    public void testEvaluatePositionDraw() {
        game.board[0] = 'X';
        game.board[1] = 'O';
        game.board[2] = 'X';
        game.board[3] = 'X';
        game.board[4] = 'O';
        game.board[5] = 'O';
        game.board[6] = 'O';
        game.board[7] = 'X';
        game.board[8] = 'X';
        assertEquals(0, game.evaluatePosition(game.board, game.player1), "Evaluation should be 0 when the game is a draw");
    }

    @Test
    public void testMinimax() {
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = ' ';
        game.board[3] = 'O';
        game.board[4] = 'O';
        game.board[5] = ' ';
        game.board[6] = ' ';
        game.board[7] = ' ';
        game.board[8] = ' ';
        int move = game.MiniMax(game.board, game.player2);
        assertTrue(move >= 1 && move <= 9, "The move should be a valid board position (1-9)");
    }

    @Test
    public void testPlayerSymbols() {
        assertEquals('X', game.player1.symbol, "Символ игрока 1 должен быть 'X'");
        assertEquals('O', game.player2.symbol, "Символ игрока 2 должен быть 'O'");
    }

    @Test
    public void testBoardSize() {
        assertEquals(9, game.board.length, "Размер доски должен быть 9");
    }

    @Test
    public void testInitialBoardState() {
        for (int i = 0; i < game.board.length; i++) {
            assertEquals(' ', game.board[i], "Начальное состояние доски должно быть пустым");
        }
    }

    @Test
    public void testEvaluatePositionNotWin() {
        game.board[0] = 'X';
        game.board[1] = 'O';
        game.board[2] = 'X';
        game.board[3] = 'X';
        game.board[4] = 'O';
        game.board[5] = ' ';
        game.board[6] = ' ';
        game.board[7] = ' ';
        game.board[8] = ' ';
        assertNotEquals(Game.INF, game.evaluatePosition(game.board, game.player1), "Оценка не должна быть INF, когда игра еще не выиграна");
    }

    @Test
    public void testGenerateMovesNotEmptyBoard() {
        game.board[0] = 'X';
        game.board[1] = 'O';
        game.board[2] = 'X';
        game.board[3] = 'X';
        game.board[4] = 'O';
        game.board[5] = ' ';
        game.board[6] = ' ';
        game.board[7] = ' ';
        game.board[8] = ' ';
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(4, moves.size(), "Должно быть 4 возможных хода");
    }

}
