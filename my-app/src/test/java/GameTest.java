import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void testInitState() {
        assertEquals(State.PLAYING, game.state);
        assertNotNull(game.player1);
        assertNotNull(game.player2);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
    }

    @Test
    void testInitBoard() {
        assertEquals(9, game.board.length);
        for (char c : game.board) {
            assertEquals(' ', c);
        }
    }

    @Test
    void testInf() {
        assertEquals(100, Game.INF);
    }

    @Test
    void testRow0() {
        char[] board = {'X','X','X','O',' ',' ',' ','O',' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testRow1() {
        char[] board = {'O',' ',' ','X','X','X',' ',' ','O'};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testRow2() {
        char[] board = {' ','O',' ',' ','O',' ','X','X','X'};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCol0() {
        char[] board = {'X','O',' ','X',' ','O','X',' ',' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCol1() {
        char[] board = {'O','X',' ',' ','X',' ','O','X',' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCol2() {
        char[] board = {' ','O','X',' ','O','X',' ',' ','X'};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testDiag1() {
        char[] board = {'X',' ','O',' ','X',' ','O',' ','X'};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testDiag2() {
        char[] board = {'O',' ','X',' ','X',' ','X','O',' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testORow() {
        char[] board = {'O','O','O','X','X',' ',' ',' ',' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testOCol() {
        char[] board = {'O','X',' ','O','X',' ','O',' ',' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testODiag() {
        char[] board = {'O',' ','X',' ','O','X',' ',' ','O'};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testDraw() {
        char[] board = {'X','O','X','X','X','O','O','X','O'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    void testPlaying() {
        char[] board = {' ',' ',' ',' ',' ',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    void testPlayingPart() {
        char[] board = {'X','O',' ',' ',' ',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    void testMovesEmpty() {
        char[] board = {' ',' ',' ',' ',' ',' ',' ',' ',' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(9, moves.size());
    }

    @Test
    void testMovesPart() {
        char[] board = {' ','O',' ',' ','X',' ',' ','O',' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(6, moves.size());
        assertTrue(moves.contains(0));
        assertTrue(moves.contains(2));
    }

    @Test
    void testMovesFull() {
        char[] board = {'X','O','X','O','X','O','X','O','X'};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(0, moves.size());
    }

    @Test
    void testEvalWin() {
        char[] board = {'X','X','X','O','O',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(board, game.player1));
    }

    @Test
    void testEvalLoss() {
        char[] board = {'X','X','X','O','O',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(-Game.INF, game.evaluatePosition(board, game.player2));
    }

    @Test
    void testEvalDraw() {
        char[] board = {'X','O','X','X','X','O','O','X','O'};
        game.symbol = 'X';
        assertEquals(0, game.evaluatePosition(board, game.player1));
    }

    @Test
    void testEvalPlaying() {
        char[] board = {' ',' ',' ',' ',' ',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(-1, game.evaluatePosition(board, game.player1));
    }

    @Test
    void testEvalOWin() {
        char[] board = {'O','O','O','X','X',' ',' ',' ',' '};
        game.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(board, game.player2));
    }

    @Test
    void testEvalOLoss() {
        char[] board = {'O','O','O','X','X',' ',' ',' ',' '};
        game.symbol = 'O';
        assertEquals(-Game.INF, game.evaluatePosition(board, game.player1));
    }

    @Test
    void testMinimaxOneMove() {
        char[] board = {'X','O','X','X','X','O','O','X',' '};
        game.board = board.clone();
        int move = game.MiniMax(board, game.player2);
        assertEquals(9, move);
    }

    @Test
    void testMinimaxWin() {
        char[] board = {'X','O','X','O','O',' ','X','X',' '};
        game.board = board.clone();
        int move = game.MiniMax(board, game.player2);
        assertEquals(6, move);
    }

    @Test
    void testMinTerminal() {
        char[] board = {'X','X','X','O','O',' ',' ',' ',' '};
        game.symbol = 'X';
        int val = game.MinMove(board, game.player1);
        assertEquals(Game.INF, val);
    }

    @Test
    void testMinTerminalLoss() {
        char[] board = {'X','X','X','O','O',' ',' ',' ',' '};
        game.symbol = 'X';
        int val = game.MinMove(board, game.player2);
        assertEquals(-Game.INF, val);
    }

    @Test
    void testMaxTerminal() {
        char[] board = {'X','X','X','O','O',' ',' ',' ',' '};
        game.symbol = 'X';
        int val = game.MaxMove(board, game.player1);
        assertEquals(Game.INF, val);
    }

    @Test
    void testMaxOneEmpty() {
        char[] board = {'X','O','X','X','X','O','O','X',' '};
        game.symbol = 'O';
        int val = game.MaxMove(board, game.player2);
        assertEquals(0, val);
    }

    @Test
    void testSymbols() {
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
    }

    @Test
    void testMoveDefault() {
        assertEquals(0, game.player1.move);
        assertEquals(0, game.player2.move);
    }

    @Test
    void testPlayerDefaults() {
        Player player = new Player();
        assertFalse(player.selected);
        assertFalse(player.win);
        assertEquals(0, player.move);
        assertEquals('\u0000', player.symbol);
    }

    @Test
    void testMovesExisting() {
        char[] board = {'X',' ','O',' ',' ','X','O',' ',' '};
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(99);
        game.generateMoves(board, moves);
        assertTrue(moves.contains(99));
        assertTrue(moves.contains(1));
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(4));
        assertTrue(moves.contains(7));
        assertTrue(moves.contains(8));
        assertEquals(6, moves.size());
    }

    @Test
    void testMinimaxFull() {
        char[] board = {'X','O','X','X','O','O','O','X','X'};
        int move = game.MiniMax(board, game.player2);
        assertEquals(0, move);
    }

    @Test
    void testMinQ() {
        char[] board = {'X','O','X',' ','O',' ',' ',' ','X'};
        game.symbol = 'X';
        game.q = 0;
        game.MinMove(board, game.player1);
        assertTrue(game.q > 0);
    }

    @Test
    void testMaxQ() {
        char[] board = {'X','O','X',' ','O',' ',' ',' ','X'};
        game.symbol = 'X';
        game.q = 0;
        game.MaxMove(board, game.player1);
        assertTrue(game.q > 0);
    }

    @Test
    void testResetQ() {
        char[] board = {'X','O','X','O','O',' ','X','X',' '};
        game.q = 10;
        game.MiniMax(board, game.player2);
        assertEquals(0, game.q);
    }

    @Test
    void testMinimaxBest() {
        char[] board = {'X','O','X',
                        'O','X',' ',
                        ' ',' ','O'};
        int move = game.MiniMax(board, game.player1);
        assertTrue(move >= 1 && move <= 9);
    }   
}
