package com.mycompany.app;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class GameIntegrationTest {
    private Game game;
    
    @Before
    public void setUp() {
        game = new Game();
    }
    
    @Test
    public void testFullGameScenarioXWin() {
        char[] board = "XX       ".toCharArray();
        game.board = board;
        int move = game.MiniMax(game.board, game.player2);
        assertTrue(move >= 1 && move <= 9);
    }
    
    @Test
    public void testGameStateTransitions() {
        char[] board = "X        ".toCharArray();
        State state = game.checkState(board, 'X');
        assertEquals(State.PLAYING, state);
        
        board = "XXX      ".toCharArray();
        state = game.checkState(board, 'X');
        assertEquals(State.XWIN, state);
    }
    
    @Test
    public void testMinMaxWithDifferentBoards() {
        // Тест для доски с одним ходом
        char[] board1 = "X        ".toCharArray();
        int move1 = game.MiniMax(board1, game.player2);
        assertTrue(move1 >= 1 && move1 <= 9);
        
        // Тест для доски с двумя ходами
        char[] board2 = "X O      ".toCharArray();
        int move2 = game.MiniMax(board2, game.player1);
        assertTrue(move2 >= 1 && move2 <= 9);
    }
    
    @Test
    public void testCheckStateAllWinConditions() {
        // Горизонталь верхняя
        assertEquals(State.XWIN, game.checkState("XXX      ".toCharArray(), 'X'));
        // Горизонталь средняя
        assertEquals(State.XWIN, game.checkState("   XXX   ".toCharArray(), 'X'));
        // Горизонталь нижняя
        assertEquals(State.XWIN, game.checkState("      XXX".toCharArray(), 'X'));
        // Вертикаль левая
        assertEquals(State.XWIN, game.checkState("X  X  X  ".toCharArray(), 'X'));
        // Вертикаль центральная
        assertEquals(State.XWIN, game.checkState(" X  X  X ".toCharArray(), 'X'));
        // Вертикаль правая
        assertEquals(State.XWIN, game.checkState("  X  X  X".toCharArray(), 'X'));
        // Диагональ главная
        assertEquals(State.XWIN, game.checkState("X   X   X".toCharArray(), 'X'));
        // Диагональ побочная
        assertEquals(State.XWIN, game.checkState("  X X X  ".toCharArray(), 'X'));
    }
}