package moe.lair.ttt;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class GameTest {
    private static char[] board(String cells) {
        return cells.replace('.', ' ').toCharArray();
    }

    @Test
    void constructorInitializesPlayersAndEmptyBoard() {
        Game game = new Game();

        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals(game.player1, game.cplayer);
        assertArrayEquals(board("........."), game.board);
    }

    @Test
    void checkStateDetectsXWinInTopRow() {
        Game game = new Game();
        game.symbol = 'X';

        assertEquals(State.XWIN, game.checkState(board("XXX.O.O..")));
    }

    @Test
    void checkStateDetectsOWinInFirstColumn() {
        Game game = new Game();
        game.symbol = 'O';

        assertEquals(State.OWIN, game.checkState(board("OX.XO.X.O")));
    }

    @Test
    void checkStateDetectsMainDiagonalWin() {
        Game game = new Game();
        game.symbol = 'X';

        assertEquals(State.XWIN, game.checkState(board("XO.OX...X")));
    }

    @Test
    void checkStateDetectsSecondaryDiagonalWin() {
        Game game = new Game();
        game.symbol = 'O';

        assertEquals(State.OWIN, game.checkState(board("XXO.O.OX.")));
    }

    @Test
    void checkStateReturnsDrawWhenBoardIsFullWithoutWinner() {
        Game game = new Game();
        game.symbol = 'X';

        assertEquals(State.DRAW, game.checkState(board("XOXXOOOXX")));
    }

    @Test
    void checkStateReturnsPlayingWhenEmptyCellsRemain() {
        Game game = new Game();
        game.symbol = 'X';

        assertEquals(State.PLAYING, game.checkState(board("XO..X.O..")));
    }

    @Test
    void generateMovesAddsIndexesOfEmptyCellsOnly() {
        Game game = new Game();
        ArrayList<Integer> moves = new ArrayList<Integer>();

        game.generateMoves(board("X.O..OXX."), moves);

        assertEquals(4, moves.size());
        assertEquals(Integer.valueOf(1), moves.get(0));
        assertEquals(Integer.valueOf(3), moves.get(1));
        assertEquals(Integer.valueOf(4), moves.get(2));
        assertEquals(Integer.valueOf(8), moves.get(3));
    }

    @Test
    void evaluatePositionScoresOwnWinAsPositiveInfinity() {
        Game game = new Game();
        game.symbol = 'X';

        assertEquals(Game.INF, game.evaluatePosition(board("XXX.O.O.."), game.player1));
    }

    @Test
    void evaluatePositionScoresOpponentWinAsNegativeInfinity() {
        Game game = new Game();
        game.symbol = 'O';

        assertEquals(-Game.INF, game.evaluatePosition(board("OOO.XX..."), game.player1));
    }

    @Test
    void evaluatePositionScoresDrawAsZero() {
        Game game = new Game();
        game.symbol = 'O';

        assertEquals(0, game.evaluatePosition(board("XOXXOOOXX"), game.player2));
    }

    @Test
    void evaluatePositionReturnsMinusOneForUnfinishedPosition() {
        Game game = new Game();
        game.symbol = 'X';

        assertEquals(-1, game.evaluatePosition(board("XOX.O...."), game.player1));
    }

    @Test
    void minimaxTakesImmediateWinningMove() {
        Game game = new Game();
        char[] board = board("OO.XX....");

        assertEquals(3, game.MiniMax(board, game.player2));
        assertArrayEquals(board("OO.XX...."), board, "MiniMax must restore the board after analysis");
        assertEquals(0, game.q);
    }

    @Test
    void minimaxBlocksImmediateOpponentWin() {
        Game game = new Game();
        char[] board = board("XX.O.....");

        assertEquals(3, game.MiniMax(board, game.player2));
        assertArrayEquals(board("XX.O....."), board);
    }

    @Test
    void minimaxReturnsZeroWhenNoMovesAreAvailable() {
        Game game = new Game();

        assertEquals(0, game.MiniMax(board("XOXXOOOXX"), game.player2));
    }

    @Test
    void minMoveReturnsTerminalValueWithoutRecursion() {
        Game game = new Game();
        game.symbol = 'O';

        assertEquals(Game.INF, game.MinMove(board("OOO.XX..."), game.player2));
        assertEquals(0, game.q);
    }

    @Test
    void maxMoveReturnsTerminalValueWithoutRecursion() {
        Game game = new Game();
        game.symbol = 'X';

        assertEquals(-Game.INF, game.MaxMove(board("XXX.OO..."), game.player2));
        assertEquals(0, game.q);
    }

    @Test
    void minMoveChoosesSmallestOutcomeForOpponent() {
        Game game = new Game();
        char[] board = board("OO.XX....");
        board[2] = game.player2.symbol;
        game.symbol = game.player2.symbol;

        assertEquals(Game.INF, game.MinMove(board, game.player2));
    }

    @Test
    void utilityPrintsCharBoard() {
        String output = captureOutput(new Runnable() {
            @Override
            public void run() {
                Utility.print(board("XO......."));
            }
        });

        assertTrue(output.contains("X-O- - - - - - - -"));
    }

    @Test
    void utilityPrintsIntBoard() {
        String output = captureOutput(new Runnable() {
            @Override
            public void run() {
                Utility.print(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
            }
        });

        assertTrue(output.contains("1-2-3-4-5-6-7-8-9-"));
    }

    @Test
    void utilityPrintsMoveList() {
        final ArrayList<Integer> moves = new ArrayList<Integer>();
        moves.add(2);
        moves.add(4);

        String output = captureOutput(new Runnable() {
            @Override
            public void run() {
                Utility.print(moves);
            }
        });

        assertTrue(output.contains("2-4-"));
    }

    @Test
    void playerFieldsCanStoreMoveSelectionAndWinFlags() {
        Player player = new Player();
        player.symbol = 'X';
        player.move = 5;
        player.selected = true;
        player.win = false;

        assertEquals('X', player.symbol);
        assertEquals(5, player.move);
        assertTrue(player.selected);
        assertFalse(player.win);
    }

    private static String captureOutput(Runnable runnable) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        try {
            runnable.run();
        } finally {
            System.setOut(originalOut);
        }
        return out.toString();
    }
}
