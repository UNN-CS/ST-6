package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import javax.swing.JFrame;
import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    public void testStateEnum() {
        assertEquals(4, State.values().length);
        assertEquals(State.PLAYING, State.valueOf("PLAYING"));
        assertEquals(State.XWIN, State.valueOf("XWIN"));
        assertEquals(State.OWIN, State.valueOf("OWIN"));
        assertEquals(State.DRAW, State.valueOf("DRAW"));
    }
    
    @Test
    public void testGameFlow() {
        Game game = new Game();

        assertEquals(State.PLAYING, game.state);
 
        game.board[0] = 'X';
        game.board[4] = 'X';
        game.board[8] = 'X';
        
        game.symbol = 'X';
        game.state = game.checkState(game.board);
        assertEquals(State.XWIN, game.state);
    
        game = new Game();
        
        game.board[0] = 'O';
        game.board[3] = 'O';
        game.board[6] = 'O';
        game.symbol = 'O';
        game.state = game.checkState(game.board);
        assertEquals(State.OWIN, game.state);
        
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
        game.board[0] = 'X'; game.board[1] = 'X'; game.board[2] = ' ';
        game.board[3] = 'O'; game.board[4] = 'O'; game.board[5] = ' ';
        game.board[6] = ' '; game.board[7] = ' '; game.board[8] = ' ';
        
        int move = game.MiniMax(game.board, game.player1);
        assertEquals(3, move);

        game = new Game();
        game.board[0] = 'X'; game.board[1] = 'X'; game.board[2] = ' ';
        game.board[3] = ' '; game.board[4] = 'O'; game.board[5] = ' ';
        game.board[6] = ' '; game.board[7] = ' '; game.board[8] = ' ';
        
        move = game.MiniMax(game.board, game.player2);
        assertEquals(3, move);
    }
}
