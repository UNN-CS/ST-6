package hugelka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProgramTest {

    private static Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    void testInitialGameState() {
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        for (char c : game.board) {
            assertEquals(' ', c);
        }
    }

    @Test
    void testCheckState() {
        char[] board = {
                'X', 'X', 'X',
                'O', 'O', ' ',
                ' ', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
        game.symbol = 'O';
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    void testMinimax() {
        Player player = new Player();
        player.symbol = 'O';
        char[] board = {
                'X', 'X', ' ',
                'O', 'O', ' ',
                ' ', ' ', ' '
        };
        int move = game.MiniMax(board, player);
        assertTrue(move == 3 || move == 6);
    }

    // Tests for TicTacToeCell class
    @Test
    void testInitialMarker() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);
        assertEquals(' ', cell.getMarker());
    }

    @Test
    void testSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
    }

    @Test
    void testGetRowAndCol() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
    }

    @Test
    void testGetNum() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);
        assertEquals(1, cell.getNum());
    }

    // Tests for Utility class
    @Test
    void testPrintCharArray() {
        char[] board = {'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X'};
        Utility.print(board);
    }

    @Test
    void testPrintIntArray() {
        int[] board = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Utility.print(board);
    }

    @Test
    void testPrintArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(2);
        moves.add(3);
        Utility.print(moves);
    }
}
