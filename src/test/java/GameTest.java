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
        assertNotNull(g.board);
        assertEquals(9, g.board.length);
        for (char c : g.board)
            assertEquals(' ', c);
    }

    @Test
    public void testInitialMoves() {
        Game g = new Game();
        assertEquals(0, g.player1.move);
        assertEquals(0, g.player2.move);
    }

    @Test
    public void testCheckStateXWinRows() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.XWIN, g.checkState(board("XXX......")));
        assertEquals(State.XWIN, g.checkState(board("...XXX...")));
        assertEquals(State.XWIN, g.checkState(board("......XXX")));
    }

    @Test
    public void testCheckStateXWinCols() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.XWIN, g.checkState(board("X..X..X..")));
        assertEquals(State.XWIN, g.checkState(board(".X..X..X.")));
        assertEquals(State.XWIN, g.checkState(board("..X..X..X")));
    }

    @Test
    public void testCheckStateXWinDiags() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.XWIN, g.checkState(board("X...X...X")));
        assertEquals(State.XWIN, g.checkState(board("..X.X.X..")));
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
    public void testGenerateMovesEmpty() {
        Game g = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        g.generateMoves(board("........."), moves);
        assertEquals(9, moves.size());
    }

    @Test
    public void testGenerateMovesFull() {
        Game g = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        g.generateMoves(board("XOXOXOXOX"), moves);
        assertEquals(0, moves.size());
    }

    @Test
    public void testGenerateMovesPartial() {
        Game g = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        g.generateMoves(board("XOX.O.X.."), moves);
        assertEquals(4, moves.size());
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(5));
        assertTrue(moves.contains(7));
        assertTrue(moves.contains(8));
    }

    @Test
    public void testEvaluateXWinsForX() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(Game.INF, g.evaluatePosition(board("XXX......"), g.player1));
    }

    @Test
    public void testEvaluateXWinsAgainstO() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(-Game.INF, g.evaluatePosition(board("XXX......"), g.player2));
    }

    @Test
    public void testEvaluateOWinsForO() {
        Game g = new Game();
        g.symbol = 'O';
        assertEquals(Game.INF, g.evaluatePosition(board("OOO......"), g.player2));
    }

    @Test
    public void testEvaluateDraw() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(0, g.evaluatePosition(board("XOXXOOOXX"), g.player1));
    }

    @Test
    public void testEvaluateOngoing() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(-1, g.evaluatePosition(board("XO......."), g.player1));
    }

    @Test
    public void testMinMoveTerminal() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(Game.INF, g.MinMove(board("XXX......"), g.player1));
    }

    @Test
    public void testMaxMoveTerminal() {
        Game g = new Game();
        g.symbol = 'O';
        assertEquals(-Game.INF, g.MaxMove(board("OOO......"), g.player1));
    }

    @Test
    public void testMiniMaxOnlyMove() {
        Game g = new Game();
        assertEquals(9, g.MiniMax(board("XOXXOOOX."), g.player1));
    }

    @Test
    public void testMiniMaxWinningMove() {
        Game g = new Game();
        assertEquals(3, g.MiniMax(board("XX.OO...."), g.player1));
    }

    @Test
    public void testMiniMaxValidRange() {
        Game g = new Game();
        int move = g.MiniMax(board("........."), g.player1);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    public void testPlayerFields() {
        Player p = new Player();
        p.symbol = 'X';
        p.move = 4;
        p.selected = true;
        p.win = true;
        assertEquals('X', p.symbol);
        assertEquals(4, p.move);
        assertTrue(p.selected);
        assertTrue(p.win);
    }

    @Test
    public void testTicTacToeCell() {
        TicTacToeCell cell = new TicTacToeCell(5, 2, 1);
        assertEquals(5, cell.getNum());
        assertEquals(2, cell.getCol());
        assertEquals(1, cell.getRow());
        assertEquals(' ', cell.getMarker());
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    public void testUtilityPrint() {
        Utility.print(board("XOX.O.X.."));
        Utility.print(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8});
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(2);
        Utility.print(moves);
        assertNotNull(new Utility());
    }
}
