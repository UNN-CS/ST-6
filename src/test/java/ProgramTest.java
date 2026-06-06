import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class ProgramTest {

    @Test
    void gameConstructorTest() {
        Game game = new Game();

        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);

        for (int i = 0; i < 9; i++) {
            assertEquals(' ', game.board[i]);
        }
    }

    @Test
    void checkStateXWinRow() {
        Game game = new Game();

        char[] board = {
                'X','X','X',
                ' ',' ',' ',
                ' ',' ',' '
        };

        game.symbol = 'X';

        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void checkStateOWinColumn() {
        Game game = new Game();

        char[] board = {
                'O',' ',' ',
                'O',' ',' ',
                'O',' ',' '
        };

        game.symbol = 'O';

        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void checkStateDiagonalWin() {
        Game game = new Game();

        char[] board = {
                'X',' ',' ',
                ' ','X',' ',
                ' ',' ','X'
        };

        game.symbol = 'X';

        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void checkStateDraw() {
        Game game = new Game();

        char[] board = {
                'X','O','X',
                'X','O','O',
                'O','X','X'
        };

        game.symbol = 'X';

        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    void checkStatePlaying() {
        Game game = new Game();

        char[] board = {
                'X','O','X',
                ' ','O','O',
                'O','X','X'
        };

        game.symbol = 'X';

        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    void generateMovesTest() {
        Game game = new Game();

        char[] board = {
                'X','O','X',
                ' ',' ','O',
                'O','X',' '
        };

        ArrayList<Integer> moves = new ArrayList<>();

        game.generateMoves(board, moves);

        assertEquals(3, moves.size());
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(4));
        assertTrue(moves.contains(8));
    }

    @Test
    void evaluatePositionXWin() {
        Game game = new Game();

        char[] board = {
                'X','X','X',
                ' ',' ',' ',
                ' ',' ',' '
        };

        game.symbol = 'X';

        int score = game.evaluatePosition(board, game.player1);

        assertEquals(Game.INF, score);
    }

    @Test
    void evaluatePositionDraw() {
        Game game = new Game();

        char[] board = {
                'X','O','X',
                'X','O','O',
                'O','X','X'
        };

        game.symbol = 'X';

        int score = game.evaluatePosition(board, game.player1);

        assertEquals(0, score);
    }

    @Test
    void minimaxReturnsValidMove() {
        Game game = new Game();

        char[] board = {
                'X','O','X',
                ' ','O',' ',
                ' ',' ','X'
        };

        int move = game.MiniMax(board, game.player2);

        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    void ticTacToeCellTest() {
        TicTacToeCell cell = new TicTacToeCell(5,1,2);

        cell.setMarker("X");

        assertEquals('X', cell.getMarker());
        assertEquals(5, cell.getNum());
        assertEquals(1, cell.getCol());
        assertEquals(2, cell.getRow());
    }

    @Test
    void maxMoveTest() {
        Game game = new Game();

        char[] board = {
                'X','O','X',
                ' ','O',' ',
                ' ',' ','X'
        };

        int value = game.MaxMove(board, game.player2);

        assertTrue(value >= -Game.INF && value <= Game.INF);
    }

    @Test
    void minMoveTest() {
        Game game = new Game();

        char[] board = {
                'X','O','X',
                ' ','O',' ',
                ' ',' ','X'
        };

        int value = game.MinMove(board, game.player2);

        assertTrue(value >= -Game.INF && value <= Game.INF);
    }

    @Test
    void utilityPrintMethodsTest() {
        char[] board1 = {'X','O','X','O','X','O','X','O','X'};
        Utility.print(board1);

        int[] board2 = {1,2,3,4,5,6,7,8,9};
        Utility.print(board2);

        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(2);
        Utility.print(moves);

        assertTrue(true);
    }

    @Test
    void evaluatePositionLoseTest() {
        Game game = new Game();

        char[] board = {
                'O','O','O',
                ' ',' ',' ',
                ' ',' ',' '
        };

        game.symbol = 'O';

        int score = game.evaluatePosition(board, game.player1);

        assertEquals(-Game.INF, score);
    }

    @Test
    void minimaxSingleMoveTest() {
        Game game = new Game();

        char[] board = {
                'X','O','X',
                'O','X','O',
                'O','X',' '
        };

        int move = game.MiniMax(board, game.player2);

        assertEquals(9, move);
    }

    @Test
    void panelCreationTest() {
        TicTacToePanel panel =
                new TicTacToePanel(new java.awt.GridLayout(3,3));

        assertNotNull(panel);
    }

    @Test
    void cellDefaultValuesTest() {
        TicTacToeCell cell = new TicTacToeCell(0,0,0);

        assertEquals(' ', cell.getMarker());
        assertEquals(0, cell.getNum());
    }

    @Test
    void testCellCoordinates() {
        TicTacToeCell cell = new TicTacToeCell(5,2,1);

        assertEquals(5, cell.getNum());
        assertEquals(1, cell.getRow());
        assertEquals(2, cell.getCol());
    }

    @Test
    void testPlayerFields() {
        Player p = new Player();

        p.move = 3;
        p.selected = true;
        p.win = false;

        assertEquals(3, p.move);
        assertTrue(p.selected);
        assertFalse(p.win);
    }

    @Test
    void testUtilityPrints() {
        Utility.print(new char[]{
            'X','O','X',
            'O','X','O',
            'X','O','X'
        });

        Utility.print(new int[]{
            1,2,3,4,5,6,7,8,9
        });

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);

        Utility.print(list);
    }
    
}
