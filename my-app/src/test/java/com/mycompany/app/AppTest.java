package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import javax.swing.JFrame;
import org.junit.jupiter.api.Test;

/**
 * Интеграционные тесты для приложения
 */
public class AppTest {

    @Test
    public void testStateEnum() {
        // Проверка корректности enum State
        assertEquals(4, State.values().length);
        assertEquals(State.PLAYING, State.valueOf("PLAYING"));
        assertEquals(State.XWIN, State.valueOf("XWIN"));
        assertEquals(State.OWIN, State.valueOf("OWIN"));
        assertEquals(State.DRAW, State.valueOf("DRAW"));
    }
    
    @Test
    public void testGameFlow() {
        // Тест имитирующий игровой процесс
        Game game = new Game();
        
        // Начальное состояние
        assertEquals(State.PLAYING, game.state);
        
        // Несколько ходов X
        game.board[0] = 'X';
        game.board[4] = 'X';
        game.board[8] = 'X';
        
        // Проверка выигрыша X
        game.symbol = 'X';
        game.state = game.checkState(game.board);
        assertEquals(State.XWIN, game.state);
        
        // Новая игра
        game = new Game();
        
        // Несколько ходов O
        game.board[0] = 'O';
        game.board[3] = 'O';
        game.board[6] = 'O';
        
        // Проверка выигрыша O
        game.symbol = 'O';
        game.state = game.checkState(game.board);
        assertEquals(State.OWIN, game.state);
        
        // Тест на ничью
        game = new Game();
        game.board[0] = 'X'; game.board[1] = 'O'; game.board[2] = 'X';
        game.board[3] = 'X'; game.board[4] = 'O'; game.board[5] = 'O';
        game.board[6] = 'O'; game.board[7] = 'X'; game.board[8] = 'X';
        
        game.symbol = 'X';
        game.state = game.checkState(game.board);
        assertEquals(State.DRAW, game.state);
    }
    
    @Test
    public void testMiniMaxAlgorithm() {
        Game game = new Game();
        
        // Тест на выигрышный ход для X
        game.board[0] = 'X'; game.board[1] = 'X'; game.board[2] = ' ';
        game.board[3] = 'O'; game.board[4] = 'O'; game.board[5] = ' ';
        game.board[6] = ' '; game.board[7] = ' '; game.board[8] = ' ';
        
        int move = game.MiniMax(game.board, game.player1);
        assertEquals(3, move); // Ход, ведущий к победе X
        
        // Тест на блокирующий ход для O
        game = new Game();
        game.board[0] = 'X'; game.board[1] = 'X'; game.board[2] = ' ';
        game.board[3] = ' '; game.board[4] = 'O'; game.board[5] = ' ';
        game.board[6] = ' '; game.board[7] = ' '; game.board[8] = ' ';
        
        move = game.MiniMax(game.board, game.player2);
        assertEquals(3, move); // Ход, блокирующий победу X
    }
}
