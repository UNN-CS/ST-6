import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

/**
 * Модульные тесты основной логики игры "Крестики-нолики".
 * Упор сделан на покрытие классов Game и Player (минимаксный алгоритм),
 * а также вспомогательных классов.
 */
public class GameTest {

    private char[] board(String s) {
        char[] b = new char[9];
        for (int i = 0; i < 9; i++) {
            char c = s.charAt(i);
            b[i] = (c == '.') ? ' ' : c;
        }
        return b;
    }

    // ---------- Конструктор / начальное состояние ----------

    @Test
    public void testInitialState() {
        Game g = new Game();
        assertEquals(State.PLAYING, g.state);
        assertEquals('X', g.player1.symbol);
        assertEquals('O', g.player2.symbol);
        assertNotNull(g.board);
        assertEquals(9, g.board.length);
        for (char c : g.board) {
            assertEquals(' ', c);
        }
    }

    // ---------- checkState ----------

    @Test
    public void testCheckStateRowsXWin() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.XWIN, g.checkState(board("XXX......")));
        assertEquals(State.XWIN, g.checkState(board("...XXX...")));
        assertEquals(State.XWIN, g.checkState(board("......XXX")));
    }

    @Test
    public void testCheckStateColumnsXWin() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.XWIN, g.checkState(board("X..X..X..")));
        assertEquals(State.XWIN, g.checkState(board(".X..X..X.")));
        assertEquals(State.XWIN, g.checkState(board("..X..X..X")));
    }

    @Test
    public void testCheckStateDiagonalsXWin() {
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
        // полностью заполнено, выигрышной линии для X нет
        assertEquals(State.DRAW, g.checkState(board("XOXXOOOXX")));
    }

    @Test
    public void testCheckStatePlaying() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.PLAYING, g.checkState(board(".........")));
        assertEquals(State.PLAYING, g.checkState(board("XO.......")));
    }

    // ---------- generateMoves ----------

    @Test
    public void testGenerateMovesEmpty() {
        Game g = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        g.generateMoves(board("........."), moves);
        assertEquals(9, moves.size());
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
    public void testGenerateMovesFull() {
        Game g = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        g.generateMoves(board("XOXOXOXOX"), moves);
        assertEquals(0, moves.size());
    }

    // ---------- evaluatePosition ----------

    @Test
    public void testEvaluatePositionXWinsForX() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(Game.INF, g.evaluatePosition(board("XXX......"), g.player1));
    }

    @Test
    public void testEvaluatePositionXWinsAgainstO() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(-Game.INF, g.evaluatePosition(board("XXX......"), g.player2));
    }

    @Test
    public void testEvaluatePositionOWinsForO() {
        Game g = new Game();
        g.symbol = 'O';
        assertEquals(Game.INF, g.evaluatePosition(board("OOO......"), g.player2));
    }

    @Test
    public void testEvaluatePositionOWinsAgainstX() {
        Game g = new Game();
        g.symbol = 'O';
        assertEquals(-Game.INF, g.evaluatePosition(board("OOO......"), g.player1));
    }

    @Test
    public void testEvaluatePositionDraw() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(0, g.evaluatePosition(board("XOXXOOOXX"), g.player1));
    }

    @Test
    public void testEvaluatePositionOngoing() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(-1, g.evaluatePosition(board("XO......."), g.player1));
    }

    // ---------- MinMove / MaxMove на терминальных позициях ----------

    @Test
    public void testMinMoveTerminal() {
        Game g = new Game();
        g.symbol = 'X';
        // позиция уже выиграна -> возвращается оценка, рекурсии нет
        assertEquals(Game.INF, g.MinMove(board("XXX......"), g.player1));
    }

    @Test
    public void testMaxMoveTerminal() {
        Game g = new Game();
        g.symbol = 'O';
        assertEquals(-Game.INF, g.MaxMove(board("OOO......"), g.player1));
    }

    // ---------- MiniMax ----------

    @Test
    public void testMiniMaxPicksOnlyMove() {
        Game g = new Game();
        // одна свободная клетка (индекс 8) -> ход 9 (1-based)
        int move = g.MiniMax(board("XOXXOOOX."), g.player1);
        assertEquals(9, move);
    }

    @Test
    public void testMiniMaxReturnsValidMove() {
        Game g = new Game();
        // несколько свободных клеток -> ветвление мин/макс
        int move = g.MiniMax(board("XOXXO...."), g.player1);
        assertTrue("ход должен быть в диапазоне 1..9", move >= 1 && move <= 9);
    }

    @Test
    public void testMiniMaxTieBreak() {
        Game g = new Game();
        // пустая доска: много равноценных ходов -> срабатывает
        // случайный выбор среди лучших ходов
        int move = g.MiniMax(board("........."), g.player1);
        assertTrue("ход должен быть в диапазоне 1..9", move >= 1 && move <= 9);
    }

    @Test
    public void testMiniMaxTakesWinningMove() {
        Game g = new Game();
        // у X две в ряд (0,1), победный ход — клетка 3 (индекс 2)
        int move = g.MiniMax(board("XX.OO...."), g.player1);
        assertEquals(3, move);
    }

    // ---------- Player ----------

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

    // ---------- TicTacToeCell ----------

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

    // ---------- Utility ----------

    @Test
    public void testUtilityPrint() {
        Utility.print(board("XOX.O.X.."));
        Utility.print(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8});
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(2);
        Utility.print(moves);
        assertNotNull(new Utility());
        // методы только печатают; проверяем, что не бросают исключений
    }
}
