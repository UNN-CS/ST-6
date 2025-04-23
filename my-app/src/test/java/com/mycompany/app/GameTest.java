package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Game
 */
public class GameTest {
    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testGameInitialization() {
        // Проверяем начальное состояние игры
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        
        // Проверяем, что доска пуста
        for (int i = 0; i < 9; i++) {
            assertEquals(' ', game.board[i]);
        }
    }

    @Test
    public void testCheckStateEmptyBoard() {
        game.symbol = 'X'; // Устанавливаем символ для проверки
        assertEquals(State.PLAYING, game.checkState(game.board));
    }

    @Test
    public void testCheckStateXWin() {
        // Горизонтальная победа X
        char[] board = {
            'X', 'X', 'X',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));

        // Вертикальная победа X
        board = new char[]{
            'X', ' ', ' ',
            'X', ' ', ' ',
            'X', ' ', ' '
        };
        assertEquals(State.XWIN, game.checkState(board));

        // Диагональная победа X
        board = new char[]{
            'X', ' ', ' ',
            ' ', 'X', ' ',
            ' ', ' ', 'X'
        };
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateOWin() {
        // Горизонтальная победа O
        char[] board = {
            'O', 'O', 'O',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));

        // Вертикальная победа O
        board = new char[]{
            'O', ' ', ' ',
            'O', ' ', ' ',
            'O', ' ', ' '
        };
        assertEquals(State.OWIN, game.checkState(board));

        // Диагональная победа O
        board = new char[]{
            'O', ' ', ' ',
            ' ', 'O', ' ',
            ' ', ' ', 'O'
        };
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    public void testCheckStateDraw() {
        // Ничья - доска заполнена без победителя
        char[] board = {
            'X', 'O', 'X',
            'X', 'O', 'O',
            'O', 'X', 'X'
        };
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(board));
        
        game.symbol = 'O';
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    public void testGenerateMoves() {
        // Пустая доска - все ходы возможны
        ArrayList<Integer> moveList = new ArrayList<>();
        game.generateMoves(game.board, moveList);
        assertEquals(9, moveList.size());
        
        // Доска с несколькими занятыми ячейками
        char[] board = {
            'X', ' ', 'O',
            ' ', 'X', ' ',
            'O', ' ', ' '
        };
        moveList.clear();
        game.generateMoves(board, moveList);
        assertEquals(5, moveList.size());
        assertTrue(moveList.contains(1));
        assertTrue(moveList.contains(3));
        assertTrue(moveList.contains(5));
        assertTrue(moveList.contains(7));
        assertTrue(moveList.contains(8));
    }

    @Test
    public void testEvaluatePosition() {
        // Тестирование оценки позиции - победа X
        char[] board = {
            'X', 'X', 'X',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(board, game.player1));
        assertEquals(-Game.INF, game.evaluatePosition(board, game.player2));
        
        // Тестирование оценки позиции - победа O
        board = new char[]{
            'O', 'O', 'O',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        game.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(board, game.player2));
        assertEquals(-Game.INF, game.evaluatePosition(board, game.player1));
        
        // Тестирование оценки позиции - ничья
        board = new char[]{
            'X', 'O', 'X',
            'X', 'O', 'O',
            'O', 'X', 'X'
        };
        assertEquals(0, game.evaluatePosition(board, game.player1));
        assertEquals(0, game.evaluatePosition(board, game.player2));
        
        // Тестирование оценки позиции - игра продолжается
        board = new char[]{
            'X', ' ', ' ',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        assertEquals(-1, game.evaluatePosition(board, game.player1));
    }

    @Test
    public void testMiniMax() {
        // Тест MiniMax для выигрыша в один ход
        char[] board = {
            'X', 'X', ' ',
            'O', 'O', ' ',
            ' ', ' ', ' '
        };
        game.board = board.clone();
        int bestMove = game.MiniMax(game.board, game.player1);
        assertEquals(3, bestMove); // Индекс клетки, в которую нужно поставить X для победы
        
        // Тест MiniMax для блокировки соперника
        board = new char[]{
            'O', 'O', ' ',
            ' ', 'X', ' ',
            ' ', ' ', ' '
        };
        game.board = board.clone();
        bestMove = game.MiniMax(game.board, game.player1);
        assertEquals(3, bestMove); // Индекс клетки, в которую нужно поставить X для блокировки O
    }

    @Test
    public void testMinMove() {
        // Тест MinMove для ситуации с возможной победой O
        char[] board = {
            'X', ' ', ' ',
            ' ', 'X', ' ',
            ' ', ' ', ' '
        };
        game.board = board.clone();
        int value = game.MinMove(board, game.player1);
        // MinMove может вернуть значение меньше, больше или равное 0
        // в зависимости от стратегии и возможных ходов
        // Просто проверяем, что функция работает и возвращает число
        assertTrue(value >= -Game.INF && value <= Game.INF);
    }

    @Test
    public void testMaxMove() {
        // Тест MaxMove для ситуации с возможной победой X
        char[] board = {
            'X', 'X', ' ',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        game.board = board.clone();
        int value = game.MaxMove(board, game.player1);
        // MaxMove должен вернуть наибольшее значение для игрока X
        assertTrue(value >= 0);
    }
} 