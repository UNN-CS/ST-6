package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class AppTest {
    private Game game;
    private Player playerX;
    private Player playerO;

    @BeforeEach
    public void setUp() {
        game = new Game();
        playerX = game.player1;
        playerO = game.player2;
    }

    // Дополнительные тесты для checkState()
    @Test
    public void testCheckStateXWinsSecondColumn() {
        char[] board = {
                ' ', 'X', ' ',
                ' ', 'X', ' ',
                ' ', 'X', ' '
        };
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateOWinsAntiDiagonal() {
        char[] board = {
                ' ', ' ', 'O',
                ' ', 'O', ' ',
                'O', ' ', ' '
        };
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateAlmostDraw() {
        char[] board = {
                'X', 'O', 'X',
                'X', 'O', 'O',
                'O', 'X', ' '
        };
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(board));
    }

    // Углубленные тесты для MiniMax алгоритма
    @Test
    public void testMiniMaxForkSituation() {
        char[] board = {
                'X', ' ', ' ',
                ' ', 'O', ' ',
                ' ', ' ', 'X'
        };
        game.board = board;
        int bestMove = game.MiniMax(board, playerO);
        // Проверяем, что алгоритм выбирает ход, создающий вилку
        assertTrue(bestMove == 2 || bestMove == 4 || bestMove == 6 || bestMove == 8);
    }

    @Test
    public void testMiniMaxBlocksFork() {
        char[] board = {
                'X', ' ', ' ',
                ' ', ' ', ' ',
                ' ', ' ', 'O'
        };
        game.board = board;
        int bestMove = game.MiniMax(board, playerX);
        // Лучший ход - центр, чтобы блокировать вилку
        assertEquals(3, bestMove);
    }

    @Test
    public void testMiniMaxEndGameScenario() {
        char[] board = {
                'X', 'O', 'X',
                'X', 'O', 'O',
                ' ', ' ', ' '
        };
        game.board = board;
        int bestMove = game.MiniMax(board, playerX);
        assertEquals(7, bestMove); // Должен завершить игру
    }

    // Подробные тесты для MinMove/MaxMove
    @Test
    public void testMinMoveWithDepth() {
        char[] board = {
                'X', ' ', ' ',
                ' ', 'O', ' ',
                ' ', ' ', ' '
        };
        int result = game.MinMove(board, playerX);
        assertTrue(result < Game.INF && result > -Game.INF);
    }

    @Test
    public void testMaxMoveWithDepth() {
        char[] board = {
                'X', ' ', ' ',
                ' ', 'O', ' ',
                ' ', ' ', ' '
        };
        int result = game.MaxMove(board, playerO);
        assertTrue(result < Game.INF && result > -Game.INF);
    }

    // Тесты для TicTacToeCell
    @Test
    public void testTicTacToeCellMethods() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(' ', cell.getMarker());
        assertEquals(0, cell.getNum());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());

        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }


    @Test
    public void testPlayerSwitch() {
        game.cplayer = playerX;
        game.nmove = 1;
        game.symbol = 'X';
        game.state = game.checkState(game.board);
        assertEquals(State.PLAYING, game.state);
    }

    // Тесты для крайних случаев
    @Test
    public void testMiniMaxWithOneEmptyCell() {
        char[] board = {
                'X', 'O', 'X',
                'X', 'O', 'O',
                'O', 'X', ' '
        };
        game.board = board;
        int bestMove = game.MiniMax(board, playerX);
        assertEquals(9, bestMove);
    }

    @Test
    public void testMiniMaxWithTwoEmptyCells() {
        char[] board = {
                'X', 'O', 'X',
                'X', ' ', 'O',
                'O', ' ', 'X'
        };
        game.board = board;
        int bestMove = game.MiniMax(board, playerO);
        assertTrue(bestMove == 5 || bestMove == 8);
    }

    // Тест для проверки счетчика ходов q
    @Test
    public void testMoveCounterInMinMax() {
        char[] board = {
                'X', ' ', ' ',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        game.MiniMax(board, playerO);
        assertEquals(0, game.q); // Должен обнуляться после MiniMax
    }

    // Тест для проверки вывода в консоль
    @Test
    public void testMiniMaxConsoleOutput() {
        char[] board = {
                'X', ' ', ' ',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        game.MiniMax(board, playerO);
        // Не можем проверить вывод, но можем проверить что выполняется без ошибок
        assertTrue(true);
    }
}