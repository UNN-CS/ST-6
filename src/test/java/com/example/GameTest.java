import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class GameTest {

    @Test
    public void testCheckState() {
        Game game = new Game();
        char[] boardXWin = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        char[] boardOWin = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        char[] boardDraw = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        char[] boardPlaying = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};

        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(boardXWin));

        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(boardOWin));

        assertEquals(State.DRAW, game.checkState(boardDraw));
        assertEquals(State.PLAYING, game.checkState(boardPlaying));
    }

    @Test
    public void testGenerateMoves() {
        Game game = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.generateMoves(board, moves);
        assertEquals(6, moves.size());
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(4));
    }

    @Test
    public void testEvaluatePosition() {
        Game game = new Game();
        char[] boardXWin = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        char[] boardDraw = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};

        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(boardXWin, game.player1));
        assertEquals(0, game.evaluatePosition(boardDraw, game.player1));
    }

    @Test
    public void testMinimax() {
        Game game = new Game();
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.player2.symbol = 'O';
        int move = game.MiniMax(board, game.player2);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    public void testMinMove() {
        Game game = new Game();
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.player2.symbol = 'O';
        int value = game.MinMove(board, game.player2);
        assertTrue(value >= -Game.INF && value <= Game.INF);
    }

    @Test
    public void testMaxMove() {
        Game game = new Game();
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.player2.symbol = 'O';
        int value = game.MaxMove(board, game.player2);
        assertTrue(value >= -Game.INF && value <= Game.INF);
    }

    @Test
    public void testGameInitialState() {
        Game game = new Game();
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals(' ', game.board[0]);
    }

    @Test
    public void testPlayerInitialSymbols() {
        Game game = new Game();
        assertEquals('X', game.player1.symbol, "Player 1 should be 'X'");
        assertEquals('O', game.player2.symbol, "Player 2 should be 'O'");
    }

    @Test
    public void testEmptyBoardInitial() {
        Game game = new Game();
        for (char cell : game.board) {
            assertEquals(' ', cell, "All cells should be initially empty");
        }
    }

    @Test
    public void testCheckStateOngoing() {
        Game game = new Game();
        char[] boardOngoing = {'X', 'O', 'X', 'O', 'X', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(boardOngoing));
    }

    @Test
    public void testEvaluateOngoingPosition() {
        Game game = new Game();
        char[] boardOngoing = {'X', 'O', 'X', 'O', 'X', ' ', ' ', ' ', ' '};
        assertEquals(-1, game.evaluatePosition(boardOngoing, game.player1));
    }

    @Test
    public void testMaxMoveEndgame() {
        Game game = new Game();
        char[] boardXWin = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        int maxMoveValue = game.MaxMove(boardXWin, game.player1);
        assertEquals(Game.INF, maxMoveValue);
    }

    @Test
    public void testMinMoveEndgame() {
        Game game = new Game();
        char[] boardOWin = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        int minMoveValue = game.MinMove(boardOWin, game.player2);
        assertEquals(Game.INF, minMoveValue);
    }

    @Test
    public void testGenerateMovesFullBoard() {
        Game game = new Game();
        char[] fullBoard = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(fullBoard, moves);
        assertTrue(moves.isEmpty(), "No moves should be generated for a full board");
    }

    @Test
    public void testGenerateMovesSingleEmpty() {
        Game game = new Game();
        char[] almostFullBoard = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', ' '};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(almostFullBoard, moves);
        assertEquals(1, moves.size(), "Only one move should be generated for a nearly full board");
        assertEquals(8, (int)moves.get(0));
    }

    @Test
    public void testEvaluatePositionOngoing() {
        Game game = new Game();
        char[] board = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(-1, game.evaluatePosition(board, game.player1));
        assertEquals(-1, game.evaluatePosition(board, game.player2));
    }

    @Test
    public void testMinimaxEdgeCase() {
        Game game = new Game();
        char[] board = {'O', 'X', 'O', 'X', 'X', 'O', 'O', ' ', 'X'};
        game.player2.symbol = 'O';
        int move = game.MiniMax(board, game.player2);
        assertEquals(8, move, "The best move should be the last remaining cell");
    }

    @Test
    public void testMinimaxInitialMove() {
        Game game = new Game();
        char[] board = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        game.player2.symbol = 'O';
        int move = game.MiniMax(board, game.player2);
        assertTrue(move >= 1 && move <= 9, "The initial move should be within the board range");
    }

    @Test
    public void testInitialState() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);
        assertEquals(' ', cell.getMarker());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertEquals(1, cell.getNum());
    }
}