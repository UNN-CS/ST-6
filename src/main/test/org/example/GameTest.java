package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void testCheckStatePlaying() {
        char[] board = {'X', 'O', 'X',
                        'O', 'X', ' ',
                        ' ', ' ', 'O'};
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    void testCheckStateXWin() {
        char[] board = {'X', 'X', 'X',
                        'O', 'O', ' ',
                        ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void testCheckStateOWin() {
        char[] board = {'O', 'O', 'O',
                        'X', 'X', ' ',
                        ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void testCheckStateDraw() {
        char[] board = {'X', 'O', 'X',
                        'O', 'X', 'X',
                        'O', 'X', 'O'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    void testGenerateMoves() {
        char[] board = {'X', 'O', 'X',
                        'O', 'X', ' ',
                        ' ', ' ', 'O'};
        ArrayList<Integer> moveList = new ArrayList<>();
        game.generateMoves(board, moveList);
        assertEquals(3, moveList.size());
        assertTrue(moveList.contains(5));
        assertTrue(moveList.contains(6));
        assertTrue(moveList.contains(7));
    }

    @Test
    void testEvaluatePositionXWin() {
        char[] board = {'X', 'X', 'X',
                        'O', 'O', ' ',
                        ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(board, game.player1));
    }

    @Test
    void testEvaluatePositionOWin() {
        char[] board = {'O', 'O', 'O',
                        'X', 'X', ' ',
                        ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(board, game.player2));
    }

    @Test
    void testEvaluatePositionDraw() {
        char[] board = {'X', 'O', 'X',
                        'O', 'X', 'X',
                        'O', 'X', 'O'};
        game.symbol = 'X';
        assertEquals(0, game.evaluatePosition(board, game.player1));
    }

    @Test
    void testMiniMax() {
        game.player1.symbol = 'X';
        game.player2.symbol = 'O';
        char[] board = {'X', 'O', 'X',
                        'O', 'X', ' ',
                        ' ', ' ', 'O'};
        int bestMove = game.MiniMax(board, game.player2);
        // Best move for 'O' is at index 7 (1-based index)
        assertEquals(7, bestMove);
    }

    @Test
    void testFirstMove() {
        game.player1.symbol = 'X';
        game.player2.symbol = 'O';
        char[] board = {' ', ' ', ' ',
                        ' ', 'X', ' ',
                        ' ', ' ', ' '};
        int[] bestMoves = {1, 3, 7, 9};
        int bestMove = game.MiniMax(board, game.player2);
        assertTrue(Arrays.stream(bestMoves).anyMatch(move -> move == bestMove));
    }

    @Test
    void testMinMove() {
        game.player1.symbol = 'X';
        game.player2.symbol = 'O';
        char[] board = {'X', 'O', 'X',
                        'O', 'X', ' ',
                        ' ', ' ', 'O'};
        int minMoveValue = game.MinMove(board, game.player2);
        assertEquals(-Game.INF, minMoveValue); // Assuming X has already won
    }

    @Test
    void testPanelCreation() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));

        // Проверяем, что панель создана
        assertNotNull(panel);

        // Проверяем, что в панели содержится 9 ячеек
        Component[] components = panel.getComponents();
        assertEquals(9, components.length);

        // Проверяем, что каждый компонент в панели - это объект класса TicTacToeCell
        for (Component component : components) {
            assertTrue(component instanceof TicTacToeCell);
        }
    }
}
