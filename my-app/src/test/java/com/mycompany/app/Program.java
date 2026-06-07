package com.mycompany.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.awt.GridLayout;

class ProgramTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void gameInitialization() {
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals(9, game.board.length);
    }

    @Test
    void xWinsInAnyCombination() {
        char[][] wins = {
            {'X','X','X',' ',' ',' ',' ',' ',' '},
            {'X',' ',' ','X',' ',' ','X',' ',' '},
            {'X',' ',' ',' ','X',' ',' ',' ','X'}
        };

        game.symbol = 'X';

        for (char[] board : wins) {
            assertEquals(State.XWIN, game.checkState(board));
        }
    }

    @Test
    void oWinsInAnyCombination() {
        char[][] wins = {
            {'O','O','O',' ',' ',' ',' ',' ',' '},
            {'O',' ',' ','O',' ',' ','O',' ',' '},
            {' ',' ','O',' ','O',' ','O',' ',' '}
        };

        game.symbol = 'O';

        for (char[] board : wins) {
            assertEquals(State.OWIN, game.checkState(board));
        }
    }

    @Test
    void drawPosition() {
        char[] board = {
            'X','O','X',
            'O','X','O',
            'O','X','O'
        };

        game.symbol = 'X';

        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    void gameStillPlaying() {
        char[] board = {
            'X',' ',' ',
            ' ',' ',' ',
            ' ',' ',' '
        };

        game.symbol = 'X';

        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    void generateMovesReturnsFreeCells() {
        char[] board = {
            'X',' ','O',
            ' ','X',' ',
            'O',' ',' '
        };

        ArrayList<Integer> moves = new ArrayList<>();

        game.generateMoves(board, moves);

        assertEquals(5, moves.size());
        assertTrue(moves.contains(1));
        assertTrue(moves.contains(8));
    }

    @Test
    void evaluateWinningPosition() {
        char[] board = {
            'X','X','X',
            ' ',' ',' ',
            ' ',' ',' '
        };

        game.symbol = 'X';

        assertEquals(Game.INF,
                game.evaluatePosition(board, game.player1));
    }

    @Test
    void evaluateDrawPosition() {
        char[] board = {
            'X','O','X',
            'O','X','O',
            'O','X','O'
        };

        assertEquals(0,
                game.evaluatePosition(board, game.player1));
    }

    @Test
    void minimaxChoosesValidMove() {
        char[] board = {
            ' ',' ',' ',
            ' ',' ',' ',
            ' ',' ',' '
        };

        int move = game.MiniMax(board, game.player2);

        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    void minimaxBlocksOpponentWin() {
        char[] board = {
            'X','X',' ',
            ' ','O',' ',
            ' ',' ',' '
        };

        int move = game.MiniMax(board, game.player2);

        assertEquals(3, move);
    }

    @Test
    void cellStoresMarker() {
        TicTacToeCell cell =
                new TicTacToeCell(4,1,1);

        cell.setMarker("X");

        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    void cellCoordinatesAreCorrect() {
        TicTacToeCell cell =
                new TicTacToeCell(8,2,2);

        assertEquals(8, cell.getNum());
        assertEquals(2, cell.getRow());
        assertEquals(2, cell.getCol());
    }

    @Test
    void panelContainsNineCells() {
        TicTacToePanel panel =
                new TicTacToePanel(
                        new GridLayout(3,3));

        assertEquals(9,
                panel.getComponentCount());
    }

    @Test
    void completeGameScenario() {
        Game testGame = new Game();

        char[] board = {
            'X','X','X',
            'O','O',' ',
            ' ',' ',' '
        };

        testGame.symbol = 'X';

        assertEquals(State.XWIN,
                testGame.checkState(board));
    }
}
