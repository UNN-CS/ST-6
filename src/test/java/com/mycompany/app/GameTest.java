package com.mycompany.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testInitialGameSetup() {
        assertEquals('X', game.player1.symbol, "Игрок 1 должен играть крестиками");
        assertEquals('O', game.player2.symbol, "Игрок 2 должен играть ноликами");
        assertEquals(State.PLAYING, game.state, "Начальное состояние должно быть PLAYING");
        
        for (char c : game.board) {
            assertEquals(' ', c, "Доска должна быть пустой при инициализации");
        }
    }

    @Test
    public void testGenerateMoves() {
        // Заполним часть доски
        game.board = new char[]{'X', 'O', 'X', ' ', ' ', 'O', 'X', ' ', 'O'};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        
        assertEquals(3, moves.size(), "Должно быть 3 доступных хода");
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(4));
        assertTrue(moves.contains(7));
    }

    @Test
    public void testCheckStateWinCondition() {
        // Имитируем победу X (верхняя горизонталь)
        game.board = new char[]{'X', 'X', 'X', ' ', 'O', 'O', ' ', ' ', ' '};
        game.symbol = 'X'; // Важно! checkState опирается на поле класса symbol
        assertEquals(State.XWIN, game.checkState(game.board));

        // Имитируем победу O (диагональ)
        game.board = new char[]{'O', 'X', ' ', 'X', 'O', ' ', ' ', ' ', 'O'};
        game.symbol = 'O'; 
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    public void testCheckStateDrawCondition() {
        // Ничья: доска заполнена, победителя нет
        game.board = new char[]{'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'O'; 
        assertEquals(State.DRAW, game.checkState(game.board));
    }

    @Test
    public void testCheckStatePlayingCondition() {
        game.board = new char[]{'X', 'O', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X'; 
        assertEquals(State.PLAYING, game.checkState(game.board));
    }

    @Test
    public void testEvaluatePosition() {
        // Проверяем оценку позиции для игрока O при выигрыше O
        game.board = new char[]{'O', 'O', 'O', 'X', 'X', ' ', ' ', ' ', ' '};
        game.symbol = 'O'; 
        int score = game.evaluatePosition(game.board, game.player2); // player2 = 'O'
        assertEquals(Game.INF, score, "При победе игрока оценка должна быть максимальной");

        // Проверяем оценку позиции для игрока O при выигрыше X
        game.board = new char[]{'X', 'X', 'X', 'O', 'O', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        score = game.evaluatePosition(game.board, game.player2);
        assertEquals(-Game.INF, score, "При проигрыше оценка должна быть минимальной");

        // Ничья
        game.board = new char[]{'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        score = game.evaluatePosition(game.board, game.player1);
        assertEquals(0, score, "При ничьей оценка должна быть 0");
    }

    @Test
    public void testMiniMaxWins() {
        // Создаем ситуацию, где алгоритм 'O' (player2) может победить в один ход
        game.board = new char[]{
            'O', 'O', ' ',
            'X', 'X', ' ',
            ' ', ' ', ' '
        };
        // Ход O. Он должен выбрать ячейку 2 (по индексу массива это 2, но MiniMax возвращает index+1, то есть 3)
        int bestMove = game.MiniMax(game.board, game.player2);
        assertEquals(3, bestMove, "MiniMax должен найти победный ход (индекс+1)");
    }

    @Test
    public void testMiniMaxBlocks() {
        game.board = new char[]{
            'O', 'X', 'X',
            'X', ' ', ' ',
            'O', 'O', 'X'
        };

        int bestMove = game.MiniMax(game.board, game.player2);
        
        assertEquals(6, bestMove, "MiniMax должен заблокировать победу соперника");
    }
}
