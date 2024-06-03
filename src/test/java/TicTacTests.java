import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class TicTacTests {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void testTicTacInitialState() {
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals(9, game.board.length);
        for (char c : game.board) {
            assertEquals(' ', c);
        }
    }

    @Test
    void testTicTacCheckState() {
        char[] board = {
                'X', 'X', 'X',
                ' ', 'O', 'O',
                ' ', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));

        board[0] = 'O';
        board[1] = 'O';
        board[2] = 'O';
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));

        board[0] = 'X';
        board[1] = 'O';
        board[2] = 'X';
        board[3] = 'X';
        board[4] = 'O';
        board[5] = 'X';
        board[6] = 'O';
        board[7] = 'X';
        board[8] = 'O';
        assertEquals(State.DRAW, game.checkState(board));

        board[0] = 'X';
        board[1] = 'X';
        board[2] = ' ';
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    void testTicTacGenerateMoves() {
        char[] board = {
                'X', 'O', 'X',
                ' ', 'O', 'O',
                ' ', 'X', ' '
        };
        ArrayList<Integer> moveList = new ArrayList<>();
        game.generateMoves(board, moveList);
        assertEquals(3, moveList.size());
        assertTrue(moveList.contains(3));
        assertTrue(moveList.contains(6));
        assertTrue(moveList.contains(8));
    }

    @Test
    void testTicTacMaxMove() {
        char[] board = {
                'X', 'O', 'X',
                ' ', 'O', ' ',
                ' ', 'X', ' '
        };
        int val = game.MaxMove(board, game.player2);
        assertTrue(val > -Game.INF);
    }

    @Test
    void testTicTacPlayerSwitch() {
        game.cplayer = game.player1;
        assertEquals('X', game.cplayer.symbol);
        game.cplayer = game.player2;
        assertEquals('O', game.cplayer.symbol);
    }

    @Test
    void testTicTacWinningMove() {
        char[] board = {
                'X', 'X', ' ',
                'O', 'O', ' ',
                ' ', ' ', ' '
        };
        game.board = board;
        game.player1.move = 2;
        game.board[2] = game.player1.symbol;
        assertNotEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void testTicTacDrawingMove() {
        char[] board = {
                'X', 'O', 'X',
                'X', 'O', 'O',
                'O', 'X', 'X'
        };
        game.board = board;
        assertEquals(State.DRAW, game.checkState(game.board));
    }
}
