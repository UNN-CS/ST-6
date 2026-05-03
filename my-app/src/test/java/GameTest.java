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

    // --- Constructor ---

    @Test
    void testGameInitialState() {
        assertEquals(State.PLAYING, game.state);
        assertNotNull(game.player1);
        assertNotNull(game.player2);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
    }

    @Test
    void testGameBoardInitiallyEmpty() {
        assertEquals(9, game.board.length);
        for (char c : game.board) {
            assertEquals(' ', c);
        }
    }

    @Test
    void testINFConstant() {
        assertEquals(100, Game.INF);
    }

    // --- checkState: XWIN ---

    @Test
    void testCheckStateXWinRow0() {
        char[] board = {'X','X','X',' ',' ',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateXWinRow1() {
        char[] board = {' ',' ',' ','X','X','X',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateXWinRow2() {
        char[] board = {' ',' ',' ',' ',' ',' ','X','X','X'};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateXWinCol0() {
        char[] board = {'X',' ',' ','X',' ',' ','X',' ',' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateXWinCol1() {
        char[] board = {' ','X',' ',' ','X',' ',' ','X',' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateXWinCol2() {
        char[] board = {' ',' ','X',' ',' ','X',' ',' ','X'};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateXWinDiag1() {
        char[] board = {'X',' ',' ',' ','X',' ',' ',' ','X'};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateXWinDiag2() {
        char[] board = {' ',' ','X',' ','X',' ','X',' ',' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    // --- checkState: OWIN ---

    @Test
    void testCheckStateOWinRow() {
        char[] board = {'O','O','O',' ',' ',' ',' ',' ',' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testCheckStateOWinCol() {
        char[] board = {'O',' ',' ','O',' ',' ','O',' ',' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testCheckStateOWinDiag() {
        char[] board = {'O',' ',' ',' ','O',' ',' ',' ','O'};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    // --- checkState: DRAW ---

    @Test
    void testCheckStateDraw() {
        // X O X / X X O / O X O — no winner, board full
        char[] board = {'X','O','X','X','X','O','O','X','O'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(board));
    }

    // --- checkState: PLAYING ---

    @Test
    void testCheckStatePlaying() {
        char[] board = {' ',' ',' ',' ',' ',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    void testCheckStatePlayingPartial() {
        char[] board = {'X','O',' ',' ',' ',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(board));
    }

    // --- generateMoves ---

    @Test
    void testGenerateMovesEmptyBoard() {
        char[] board = {' ',' ',' ',' ',' ',' ',' ',' ',' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(9, moves.size());
    }

    @Test
    void testGenerateMovesPartialBoard() {
        char[] board = {'X',' ',' ',' ','O',' ',' ',' ',' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(7, moves.size());
        assertTrue(moves.contains(1));
        assertTrue(moves.contains(2));
    }

    @Test
    void testGenerateMovesFullBoard() {
        char[] board = {'X','O','X','O','X','O','X','O','X'};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(0, moves.size());
    }

    // --- evaluatePosition ---

    @Test
    void testEvaluatePositionWin() {
        char[] board = {'X','X','X','O','O',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(board, game.player1));
    }

    @Test
    void testEvaluatePositionLoss() {
        char[] board = {'X','X','X','O','O',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(-Game.INF, game.evaluatePosition(board, game.player2));
    }

    @Test
    void testEvaluatePositionDraw() {
        char[] board = {'X','O','X','X','X','O','O','X','O'};
        game.symbol = 'X';
        assertEquals(0, game.evaluatePosition(board, game.player1));
    }

    @Test
    void testEvaluatePositionStillPlaying() {
        char[] board = {' ',' ',' ',' ',' ',' ',' ',' ',' '};
        game.symbol = 'X';
        assertEquals(-1, game.evaluatePosition(board, game.player1));
    }

    @Test
    void testEvaluatePositionOWinForO() {
        char[] board = {'O','O','O','X','X',' ',' ',' ',' '};
        game.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(board, game.player2));
    }

    @Test
    void testEvaluatePositionOWinForX() {
        char[] board = {'O','O','O','X','X',' ',' ',' ',' '};
        game.symbol = 'O';
        assertEquals(-Game.INF, game.evaluatePosition(board, game.player1));
    }

    // --- MiniMax ---

    @Test
    void testMiniMaxOnlyOneMove() {
        // Only position 8 (index) is empty — MiniMax must return 9
        char[] board = {'X','O','X','X','X','O','O','X',' '};
        game.board = board.clone();
        int move = game.MiniMax(board, game.player2);
        assertEquals(9, move);
    }

    @Test
    void testMiniMaxWinsImmediately() {
        // X O X / O O _ / X X _  — O wins by playing at index 5 (position 6), completing row 1
        char[] board = {'X','O','X','O','O',' ','X','X',' '};
        game.board = board.clone();
        int move = game.MiniMax(board, game.player2);
        assertEquals(6, move);
    }

    // --- MinMove ---

    @Test
    void testMinMoveTerminal() {
        // Already won by X — terminal state, returns immediately
        char[] board = {'X','X','X','O','O',' ',' ',' ',' '};
        game.symbol = 'X';
        int val = game.MinMove(board, game.player1);
        assertEquals(Game.INF, val);
    }

    @Test
    void testMinMoveTerminalLoss() {
        char[] board = {'X','X','X','O','O',' ',' ',' ',' '};
        game.symbol = 'X';
        int val = game.MinMove(board, game.player2);
        assertEquals(-Game.INF, val);
    }

    // --- MaxMove ---

    @Test
    void testMaxMoveTerminal() {
        char[] board = {'X','X','X','O','O',' ',' ',' ',' '};
        game.symbol = 'X';
        int val = game.MaxMove(board, game.player1);
        assertEquals(Game.INF, val);
    }

    @Test
    void testMaxMoveOneEmpty() {
        // One cell left — MaxMove recurses once then hits terminal
        char[] board = {'X','O','X','X','X','O','O','X',' '};
        game.symbol = 'O';
        int val = game.MaxMove(board, game.player2);
        // O plays at index 8, board becomes full with no winner → DRAW → 0
        assertEquals(0, val);
    }

    // --- Player fields ---

    @Test
    void testPlayerSymbols() {
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
    }

    @Test
    void testPlayerMoveDefault() {
        assertEquals(0, game.player1.move);
        assertEquals(0, game.player2.move);
    }

        @Test
    void testPlayerDefaultBooleanFields() {
        Player player = new Player();

        assertFalse(player.selected);
        assertFalse(player.win);
        assertEquals(0, player.move);
        assertEquals('\u0000', player.symbol);
    }

    @Test
    void testGenerateMovesKeepsExistingMoves() {
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
    void testMiniMaxFullBoardReturnsZero() {
        char[] board = {'X','O','X','X','O','O','O','X','X'};

        int move = game.MiniMax(board, game.player2);

        assertEquals(0, move);
    }

    @Test
    void testMinMoveIncrementsQOnNonTerminalPosition() {
        char[] board = {'X','O','X',' ','O',' ',' ',' ','X'};
        game.symbol = 'X';
        game.q = 0;

        game.MinMove(board, game.player1);

        assertTrue(game.q > 0);
    }

    @Test
    void testMaxMoveIncrementsQOnNonTerminalPosition() {
        char[] board = {'X','O','X',' ','O',' ',' ',' ','X'};
        game.symbol = 'X';
        game.q = 0;

        game.MaxMove(board, game.player1);

        assertTrue(game.q > 0);
    }

    @Test
    void testMiniMaxResetsQAfterExecution() {
        char[] board = {'X','O','X','O','O',' ','X','X',' '};
        game.q = 10;

        game.MiniMax(board, game.player2);

        assertEquals(0, game.q);
    }
    @Test
    void testMiniMaxMultipleBestMoves() {
    char[] board = {'X','O','X',
                    'O','X',' ',
                    ' ',' ','O'};

    int move = game.MiniMax(board, game.player1);

    assertTrue(move >= 1 && move <= 9);
    }   
}

