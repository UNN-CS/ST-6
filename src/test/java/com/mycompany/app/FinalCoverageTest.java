package com.mycompany.app;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class FinalCoverageTest {
    
    @Test
    public void testCoverAllGameMethods() {
        Game game = new Game();
        
        // Тест 1: Пустая доска
        char[] emptyBoard = "         ".toCharArray();
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(emptyBoard, moves);
        assertEquals(9, moves.size());
        
        // Тест 2: Доска с одним X
        char[] oneXBoard = "X        ".toCharArray();
        game.checkState(oneXBoard, 'X');
        game.evaluatePosition(oneXBoard, game.player1);
        
        // Тест 3: Запуск минимакса
        game.MiniMax(emptyBoard, game.player1);
        
        // Тест 4: MinMove и MaxMove
        game.MinMove(emptyBoard, game.player1);
        game.MaxMove(emptyBoard, game.player1);
        
        // Тест 5: Проверка на ничью
        char[] drawBoard = {
            'X', 'O', 'X',
            'X', 'O', 'O',
            'O', 'X', 'X'
        };
        State state = game.checkState(drawBoard, 'X');
        assertEquals(State.DRAW, state);
    }
    
    @Test
    public void testUtilityMethods() {
        // Покрываем методы Utility
        char[] charBoard = "XOXOXOXOX".toCharArray();
        Utility.print(charBoard);
        
        int[] intBoard = {1,2,3,4,5,6,7,8,9};
        Utility.print(intBoard);
        
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Utility.print(list);
    }
    
    @Test
    public void testTicTacToeCell() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(' ', cell.getMarker());
        assertEquals(0, cell.getNum());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        
        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
    }
}