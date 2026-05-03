package com.mycompany.app;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.lang.reflect.Field;

public class GameAdditionalTest {
    private Game game;
    private Player playerX;
    private Player playerO;
    
    @Before
    public void setUp() {
        game = new Game();
        playerX = game.player1;
        playerO = game.player2;
    }
    
    @Test
    public void testCheckStateWithAllWinConditions() {
        // Тестируем все возможные выигрышные комбинации
        String[] winCombinations = {
            "XXX      ", // верхняя горизонталь
            "   XXX   ", // средняя горизонталь
            "      XXX", // нижняя горизонталь
            "X  X  X  ", // левая вертикаль
            " X  X  X ", // центральная вертикаль
            "  X  X  X", // правая вертикаль
            "X   X   X", // главная диагональ
            "  X X X  "  // побочная диагональ
        };
        
        for (String boardStr : winCombinations) {
            char[] board = boardStr.toCharArray();
            State state = game.checkState(board, 'X');
            assertEquals(State.XWIN, state);
        }
    }
    
    @Test
    public void testCheckStateWithNoWinner() {
        char[] board = "XO  OX  X".toCharArray();
        State state = game.checkState(board, 'X');
        assertNotEquals(State.XWIN, state);
        assertNotEquals(State.OWIN, state);
    }
    
    @Test
    public void testEvaluatePositionWithDifferentScenarios() {
        // Сценарий, где X выигрывает
        char[] boardXWin = "XXX      ".toCharArray();
        assertEquals(Game.INF, game.evaluatePosition(boardXWin, playerX));
        
        // Сценарий, где O выигрывает
        char[] boardOWin = "OOO      ".toCharArray();
        assertEquals(Game.INF, game.evaluatePosition(boardOWin, playerO));
        
        // Сценарий ничьи (создаем реальную ничью)
        char[] boardDraw = {
            'X', 'O', 'X',
            'X', 'O', 'O',
            'O', 'X', 'X'
        };
        assertEquals(0, game.evaluatePosition(boardDraw, playerX));
    }
    
    @Test
    public void testMinMoveWithMultipleOptions() {
        char[] board = "X  O  X  ".toCharArray();
        game.symbol = 'O';
        int value = game.MinMove(board, playerO);
        assertTrue(value >= -Game.INF && value <= Game.INF);
    }
    
    @Test
    public void testMaxMoveWithMultipleOptions() {
        char[] board = "X  O  X  ".toCharArray();
        game.symbol = 'X';
        int value = game.MaxMove(board, playerX);
        assertTrue(value >= -Game.INF && value <= Game.INF);
    }
    
    @Test
    public void testMiniMaxWithMultipleBestMoves() {
        char[] board = "         ".toCharArray();
        // Запускаем несколько раз, чтобы проверить случайный выбор при равных значениях
        for (int i = 0; i < 5; i++) {
            int move = game.MiniMax(board, playerX);
            assertTrue(move >= 1 && move <= 9);
        }
    }
    
    @Test
    public void testGenerateMovesWithSpecificBoard() {
        char[] board = "X O X O X O".toCharArray();
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        // Проверяем, что сгенерированы правильные ходы
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                assertTrue(moves.contains(i));
            }
        }
    }
    
    @Test
    public void testGameStateAfterMoves() {
        // Тестируем последовательность ходов
        game.board = "X        ".toCharArray();
        game.state = game.checkState(game.board, 'X');
        assertEquals(State.PLAYING, game.state);
        
        game.board = "XX       ".toCharArray();
        game.state = game.checkState(game.board, 'X');
        assertEquals(State.PLAYING, game.state);
        
        game.board = "XXX      ".toCharArray();
        game.state = game.checkState(game.board, 'X');
        assertEquals(State.XWIN, game.state);
    }
    
    @Test
    public void testPlayerMoveAssignment() {
        playerX.move = 5;
        assertEquals(5, playerX.move);
        
        playerO.move = 3;
        assertEquals(3, playerO.move);
        
        playerX.selected = true;
        assertTrue(playerX.selected);
        
        playerO.win = true;
        assertTrue(playerO.win);
    }
    
    @Test
    public void testGameConstructor() {
        Game newGame = new Game();
        assertNotNull(newGame);
        assertNotNull(newGame.player1);
        assertNotNull(newGame.player2);
        assertEquals('X', newGame.player1.symbol);
        assertEquals('O', newGame.player2.symbol);
        assertEquals(State.PLAYING, newGame.state);
        assertEquals(9, newGame.board.length);
    }
    
    @Test
    public void testCheckStateWithSymbolParam() {
        char[] board = "XO       ".toCharArray();
        
        // Проверяем с символом X
        State stateX = game.checkState(board, 'X');
        assertEquals(State.PLAYING, stateX);
        
        // Проверяем с символом O
        State stateO = game.checkState(board, 'O');
        assertEquals(State.PLAYING, stateO);
        
        // Проверяем выигрыш X
        char[] boardXWin = "XXX      ".toCharArray();
        assertEquals(State.XWIN, game.checkState(boardXWin, 'X'));
        assertEquals(State.PLAYING, game.checkState(boardXWin, 'O'));
    }
    
    @Test
    public void testMiniMaxWithDifferentPlayers() {
        char[] board = "         ".toCharArray();
        
        // Ход за X
        int moveX = game.MiniMax(board, playerX);
        assertTrue(moveX >= 1 && moveX <= 9);
        
        // Сбрасываем доску
        for (int i = 0; i < 9; i++) {
            game.board[i] = ' ';
        }
        
        // Ход за O
        int moveO = game.MiniMax(board, playerO);
        assertTrue(moveO >= 1 && moveO <= 9);
    }
    
    @Test
    public void testEvaluatePositionBoundaryCases() {
        // Пустая доска
        char[] emptyBoard = "         ".toCharArray();
        assertEquals(-1, game.evaluatePosition(emptyBoard, playerX));
        
        // Почти полная доска без победителя
        char[] almostFullBoard = "XOXOXOOX ".toCharArray();
        assertEquals(-1, game.evaluatePosition(almostFullBoard, playerX));
        
        // Полная доска с победой X
        char[] fullBoardXWin = "XXXOOOXXX".toCharArray();
        assertEquals(Game.INF, game.evaluatePosition(fullBoardXWin, playerX));
    }
    
    @Test
    public void testMinMaxRecursionDepth() {
        char[] board = "         ".toCharArray();
        game.q = 0;
        game.MiniMax(board, playerX);
        // Проверяем, что рекурсия работала (q увеличилось)
        assertTrue(game.q > 0);
        
        int qValue = game.q;
        game.MiniMax(board, playerO);
        // При втором запуске q должно снова увеличиться
        assertTrue(game.q > qValue);
    }
}