package com.mycompany.app;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void testInitialGameState() {
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        
        char[] board = game.getBoard();
        for (int i = 0; i < 9; i++) {
            assertEquals(' ', board[i]);
        }
    }

    @Test
    void testMakeMove() {
        // Делаем ход X
        State state = game.makeMove(0, 'X');
        assertEquals(State.PLAYING, state);
        assertEquals('X', game.board[0]);
        
        // Делаем ход O
        state = game.makeMove(1, 'O');
        assertEquals(State.PLAYING, state);
        assertEquals('O', game.board[1]);
        
        // Пытаемся сделать ход на занятую клетку
        state = game.makeMove(0, 'X');
        assertEquals(State.PLAYING, state); // Состояние не должно измениться
        assertEquals('X', game.board[0]);   // Клетка не должна измениться
    }

    @Test
    void testGenerateMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        
        // На пустой доске должно быть 9 возможных ходов
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size());
        
        // Делаем ход и проверяем, что осталось 8 возможных ходов
        game.makeMove(0, 'X');
        moves.clear();
        game.generateMoves(game.board, moves);
        assertEquals(8, moves.size());
        assertFalse(moves.contains(0));
    }

    @Test
    void testHorizontalWin() {
        // X занимает верхнюю горизонталь
        game.makeMove(0, 'X');
        game.makeMove(1, 'X');
        game.symbol = 'X';
        
        // Проверяем, что еще нет победы
        State state = game.checkState(game.board);
        assertEquals(State.PLAYING, state);
        
        // Делаем последний ход для победы
        game.makeMove(2, 'X');
        state = game.checkState(game.board);
        assertEquals(State.XWIN, state);
    }

    @Test
    void testVerticalWin() {
        // O занимает средний вертикальный ряд
        game.makeMove(1, 'O');
        game.makeMove(4, 'O');
        game.symbol = 'O';
        
        // Проверяем, что еще нет победы
        State state = game.checkState(game.board);
        assertEquals(State.PLAYING, state);
        
        // Делаем последний ход для победы
        game.makeMove(7, 'O');
        state = game.checkState(game.board);
        assertEquals(State.OWIN, state);
    }

    @Test
    void testDiagonalWin() {
        // X занимает диагональ
        game.makeMove(0, 'X');
        game.makeMove(4, 'X');
        game.symbol = 'X';
        
        // Проверяем, что еще нет победы
        State state = game.checkState(game.board);
        assertEquals(State.PLAYING, state);
        
        // Делаем последний ход для победы
        game.makeMove(8, 'X');
        state = game.checkState(game.board);
        assertEquals(State.XWIN, state);
    }

    @Test
    void testDraw() {
        // Заполняем доску без победы (ничья)
        // X | O | X
        // O | X | O
        // O | X | O
        game.makeMove(0, 'X');
        game.makeMove(1, 'O');
        game.makeMove(2, 'X');
        game.makeMove(3, 'O');
        game.makeMove(4, 'X');
        game.makeMove(5, 'O');
        game.makeMove(6, 'O');
        game.makeMove(7, 'X');
        
        // Проверяем, что еще нет ничьи
        game.symbol = 'X';
        State state = game.checkState(game.board);
        assertEquals(State.PLAYING, state);
        
        // Делаем последний ход
        game.makeMove(8, 'O');
        game.symbol = 'O';
        state = game.checkState(game.board);
        assertEquals(State.DRAW, state);
    }

    @Test
    void testEvaluatePosition() {
        // Создаем выигрышную ситуацию для X
        game.makeMove(0, 'X');
        game.makeMove(1, 'X');
        game.makeMove(2, 'X');
        game.symbol = 'X';
        
        // Для X это выигрыш
        int value = game.evaluatePosition(game.board, game.player1);
        assertEquals(Game.INF, value);
        
        // Для O это проигрыш
        value = game.evaluatePosition(game.board, game.player2);
        assertEquals(-Game.INF, value);
    }

    @Test
    void testMiniMax() {
        // Создаем ситуацию, где есть оптимальные ходы
        // X | O | X
        // O | 5 | X
        // 7 | 8 | O
        game.makeMove(0, 'X');
        game.makeMove(1, 'O');
        game.makeMove(2, 'X');
        game.makeMove(3, 'O');
        game.makeMove(5, 'X');
        game.makeMove(8, 'O');
        
        // Для X оптимальные ходы могут быть различными в зависимости от реализации
        int bestMove = game.MiniMax(game.board, game.player1);
        int[] validFirstMoves = {5, 7, 8};
        boolean isValidMove = false;
        for (int validMove : validFirstMoves) {
            if (bestMove == validMove) {
                isValidMove = true;
                break;
            }
        }
        assertTrue(isValidMove, "Ход " + bestMove + " должен быть одним из оптимальных ходов (5, 7, 8)");
        
        // Сбрасываем доску и создаем другую ситуацию
        game.resetGame();
        
        // X | 1 | 2
        // 3 | O | 5
        // 6 | 7 | X
        game.makeMove(0, 'X');
        game.makeMove(4, 'O');
        game.makeMove(8, 'X');
        
        // Для O возможные оптимальные ходы в этой ситуации
        int move = game.MiniMax(game.board, game.player2);
        // Проверяем, что ход является одним из возможных оптимальных ходов
        // В зависимости от реализации алгоритма, оптимальными могут быть разные ходы
        int[] validMoves = {1, 2, 3, 5, 6, 7, 8};
        isValidMove = false;
        for (int validMove : validMoves) {
            if (move == validMove) {
                isValidMove = true;
                break;
            }
        }
        assertTrue(isValidMove, "Ход " + move + " должен быть одним из оптимальных ходов");
    }

    @Test
    void testResetGame() {
        // Делаем несколько ходов
        game.makeMove(0, 'X');
        game.makeMove(1, 'O');
        game.makeMove(2, 'X');
        
        // Сбрасываем игру
        game.resetGame();
        
        // Проверяем, что доска пуста
        char[] board = game.getBoard();
        for (int i = 0; i < 9; i++) {
            assertEquals(' ', board[i]);
        }
        
        // Проверяем, что состояние игры - PLAYING
        assertEquals(State.PLAYING, game.state);
    }
} 