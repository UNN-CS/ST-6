import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class ProgramTest {

    @Test
    void newGameStartsWithEmptyBoard() {
        Game game = new Game();

        assertArrayEquals(new char[] {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, game.getBoardCopy());
    }

    @Test
    void checkStateDetectsXWin() {
        Game game = new Game();
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};

        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void checkStateDetectsOWin() {
        Game game = new Game();
        char[] board = {'O', ' ', ' ', 'O', ' ', ' ', 'O', ' ', ' '};

        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void checkStateDetectsDraw() {
        Game game = new Game();
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};

        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    void generateMovesReturnsAllEmptyCells() {
        Game game = new Game();
        char[] board = {'X', ' ', 'O', ' ', 'X', ' ', 'O', ' ', ' '};

        assertEquals(List.of(1, 3, 5, 7, 8), game.generateMoves(board));
    }

    @Test
    void evaluatePositionReturnsPositiveForMatchingWinner() {
        Game game = new Game();
        Player player = new Player('X');
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};

        assertEquals(Game.INF, game.evaluatePosition(board, player));
    }

    @Test
    void evaluatePositionReturnsNegativeForLosingPosition() {
        Game game = new Game();
        Player player = new Player('X');
        char[] board = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};

        assertEquals(-Game.INF, game.evaluatePosition(board, player));
    }

    @Test
    void evaluatePositionReturnsZeroForDraw() {
        Game game = new Game();
        Player player = new Player('O');
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};

        assertEquals(0, game.evaluatePosition(board, player));
    }

    @Test
    void bestMoveFindsImmediateWinningCellForX() {
        Game game = new Game();
        game.setCell(0, 'X');
        game.setCell(1, 'X');
        game.setCell(3, 'O');
        game.setCell(4, 'O');

        assertEquals(3, game.findBestMove('X'));
    }

    @Test
    void bestMoveFindsImmediateWinningCellForO() {
        Game game = new Game();
        game.setCell(0, 'O');
        game.setCell(1, 'O');
        game.setCell(3, 'O');
        game.setCell(4, 'O');
        game.setCell(6, 'X');
        game.setCell(7, 'X');

        assertEquals(3, game.findBestMove('O'));
    }

    @Test
    void miniMaxScorePrefersWinningOutcome() {
        Game game = new Game();
        char[] board = {'X', 'X', ' ', 'O', 'O', ' ', ' ', ' ', ' '};

        assertTrue(game.miniMaxScore(board, new Player('X')) >= 0);
    }
}