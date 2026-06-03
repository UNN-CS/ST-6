import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

public class GameTest {

    private char[] board(String s) {
        char[] b = new char[9];
        for (int i = 0; i < 9; i++) {
            char c = s.charAt(i);
            b[i] = (c == '.') ? ' ' : c;
        }
        return b;
    }

    @Test
    public void testInitialState() {
        Game g = new Game();
        assertEquals(State.PLAYING, g.state);
        assertEquals('X', g.player1.symbol);
        assertEquals('O', g.player2.symbol);
        assertEquals(9, g.board.length);
        for (char c : g.board)
            assertEquals(' ', c);
    }

    @Test
    public void testCheckStateXWin() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.XWIN, g.checkState(board("XXX......")));
        assertEquals(State.XWIN, g.checkState(board("...XXX...")));
        assertEquals(State.XWIN, g.checkState(board("X...X...X")));
        assertEquals(State.XWIN, g.checkState(board("X..X..X..")));
    }

    @Test
    public void testCheckStateOWin() {
        Game g = new Game();
        g.symbol = 'O';
        assertEquals(State.OWIN, g.checkState(board("OOO......")));
        assertEquals(State.OWIN, g.checkState(board("O...O...O")));
    }

    @Test
    public void testCheckStateDraw() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.DRAW, g.checkState(board("XOXXOOOXX")));
    }

    @Test
    public void testCheckStatePlaying() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.PLAYING, g.checkState(board(".........")));
        assertEquals(State.PLAYING, g.checkState(board("XO.......")));
    }

    @Test
    public void testGenerateMoves() {
        Game g = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        g.generateMoves(board("........."), moves);
        assertEquals(9, moves.size());

        moves.clear();
        g.generateMoves(board("XOXOXOXOX"), moves);
        assertEquals(0, moves.size());

        moves.clear();
        g.generateMoves(board("XO.X.O..."), moves);
        assertEquals(5, moves.size());
    }

    @Test
    public void testEvaluatePosition() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(Game.INF, g.evaluatePosition(board("XXX......"), g.player1));
        assertEquals(-Game.INF, g.evaluatePosition(board("XXX......"), g.player2));
        assertEquals(-1, g.evaluatePosition(board("XO......."), g.player1));
    }

    @Test
    public void testEvaluateDraw() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(0, g.evaluatePosition(board("XOXXOOOXX"), g.player1));
    }

    @Test
    public void testMiniMaxOnlyMove() {
        Game g = new Game();
        int move = g.MiniMax(board("XOXXOOOX."), g.player1);
        assertEquals(9, move);
    }

    @Test
    public void testMiniMaxWinningMove() {
        Game g = new Game();
        int move = g.MiniMax(board("XX.OO...."), g.player1);
        assertEquals(3, move);
    }

    @Test
    public void testMiniMaxValidRange() {
        Game g = new Game();
        int move = g.MiniMax(board("........."), g.player1);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    public void testCellSetMarker() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(' ', cell.getMarker());
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    public void testUtility() {
        Utility.print(board("XOX......"));
        Utility.print(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1); moves.add(5);
        Utility.print(moves);
    }
}
