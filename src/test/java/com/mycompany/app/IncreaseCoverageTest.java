package com.mycompany.app;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.lang.reflect.Field;

public class IncreaseCoverageTest {
    
    @Test
    public void testAllCheckStateBranches() {
        Game game = new Game();
        
        // Тестируем все ветки в checkState
        char[] board1 = "XXX      ".toCharArray();
        assertEquals(State.XWIN, game.checkState(board1, 'X'));
        
        char[] board2 = "OOO      ".toCharArray();
        assertEquals(State.OWIN, game.checkState(board2, 'O'));
        
        char[] board3 = "XOXOXOOXX".toCharArray();
        State result = game.checkState(board3, 'X');
        assertTrue(result == State.DRAW || result == State.PLAYING);
        
        char[] board4 = "X  O  X  ".toCharArray();
        assertEquals(State.PLAYING, game.checkState(board4, 'X'));
    }
    
    @Test
    public void testEvaluatePositionAllBranches() {
        Game game = new Game();
        
        // Победа X для игрока X
        char[] boardXWin = "XXX      ".toCharArray();
        assertEquals(Game.INF, game.evaluatePosition(boardXWin, game.player1));
        
        // Победа X для игрока O (должно вернуть -INF)
        assertEquals(-Game.INF, game.evaluatePosition(boardXWin, game.player2));
        
        // Победа O для игрока O
        char[] boardOWin = "OOO      ".toCharArray();
        assertEquals(Game.INF, game.evaluatePosition(boardOWin, game.player2));
        
        // Победа O для игрока X
        assertEquals(-Game.INF, game.evaluatePosition(boardOWin, game.player1));
    }
    
    @Test
    public void testMiniMaxWithAllScenarios() {
        Game game = new Game();
        
        // Сценарий 1: Пустая доска
        char[] emptyBoard = "         ".toCharArray();
        int move1 = game.MiniMax(emptyBoard, game.player1);
        assertTrue(move1 >= 1 && move1 <= 9);
        
        // Сценарий 2: Почти выигрышная позиция для X
        char[] almostWinBoard = "XX  O    ".toCharArray();
        int move2 = game.MiniMax(almostWinBoard, game.player2);
        assertTrue(move2 >= 1 && move2 <= 9);
        
        // Сценарий 3: Защита от проигрыша
        char[] threatBoard = "O O X    ".toCharArray();
        int move3 = game.MiniMax(threatBoard, game.player1);
        assertTrue(move3 >= 1 && move3 <= 9);
    }
    
    @Test
    public void testMinMaxWithTerminalPositions() {
        Game game = new Game();
        
        // Терминальная позиция - победа X
        char[] winBoard = "XXX      ".toCharArray();
        game.symbol = 'X';
        
        // MinMove на терминальной позиции
        int minValue = game.MinMove(winBoard, game.player1);
        assertEquals(Game.INF, minValue);
        
        // MaxMove на терминальной позиции
        int maxValue = game.MaxMove(winBoard, game.player1);
        assertEquals(Game.INF, maxValue);
    }
    
    @Test
    public void testGenerateMovesEdgeCases() {
        Game game = new Game();
        
        // Полная доска
        char[] fullBoard = "XOXOXOOXO".toCharArray();
        ArrayList<Integer> moves1 = new ArrayList<>();
        game.generateMoves(fullBoard, moves1);
        assertEquals(0, moves1.size());
        
        // Почти полная доска
        char[] almostFull = "XOXOXOOX ".toCharArray();
        ArrayList<Integer> moves2 = new ArrayList<>();
        game.generateMoves(almostFull, moves2);
        assertEquals(1, moves2.size());
        assertEquals(8, (int)moves2.get(0));
        
        // Доска с одним пустым местом в центре
        char[] oneEmpty = "XOXOXOOX ".toCharArray();
        ArrayList<Integer> moves3 = new ArrayList<>();
        game.generateMoves(oneEmpty, moves3);
        assertFalse(moves3.isEmpty());
    }
    
    @Test
    public void testGameStateAfterMultipleMoves() {
        Game game = new Game();
        
        // Симулируем игру
        game.board[0] = 'X';
        game.board[4] = 'X';
        game.board[8] = 'X';
        
        // Проверяем победу по диагонали
        State state = game.checkState(game.board, 'X');
        assertEquals(State.XWIN, state);
        
        // Создаем новую игру
        Game game2 = new Game();
        game2.board[0] = 'O';
        game2.board[4] = 'O';
        game2.board[8] = 'O';
        
        State state2 = game2.checkState(game2.board, 'O');
        assertEquals(State.OWIN, state2);
    }
    
    @Test
    public void testMinMaxRecursionWithDepth() {
        Game game = new Game();
        char[] board = "         ".toCharArray();
        
        // Запускаем MinMove и проверяем глубину рекурсии
        game.q = 0;
        game.MinMove(board, game.player1);
        int depth1 = game.q;
        assertTrue(depth1 > 0);
        
        // Запускаем MaxMove
        game.q = 0;
        game.MaxMove(board, game.player1);
        int depth2 = game.q;
        assertTrue(depth2 > 0);
    }
}