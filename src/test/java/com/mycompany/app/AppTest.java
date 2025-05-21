package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class AppTest {
    private Game game;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        game = new Game();
        player1 = game.player1;
        player2 = game.player2;
    }

    @Test
    void testInitialGameState() {
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', player1.symbol);
        assertEquals('O', player2.symbol);
        for (int i = 0; i < 9; i++) {
            assertEquals(' ', game.board[i]);
        }
    }

    @Test
    void testCheckStateWinX() {
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = 'X';
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));

        
        game = new Game();
        game.board[0] = 'X';
        game.board[3] = 'X';
        game.board[6] = 'X';
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
 
        game = new Game();
        game.board[0] = 'X';
        game.board[4] = 'X';
        game.board[8] = 'X';
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateWinO() {
        
        game.board[0] = 'O';
        game.board[1] = 'O';
        game.board[2] = 'O';
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));

        
        game = new Game();
        game.board[0] = 'O';
        game.board[3] = 'O';
        game.board[6] = 'O';
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));

        
        game = new Game();
        game.board[0] = 'O';
        game.board[4] = 'O';
        game.board[8] = 'O';
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    void testCheckStateDraw() {
        
        game.board[0] = 'X'; game.board[1] = 'O'; game.board[2] = 'X';
        game.board[3] = 'O'; game.board[4] = 'X'; game.board[5] = 'O';
        game.board[6] = 'O'; game.board[7] = 'X'; game.board[8] = 'O';
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(game.board));
    }

    @Test
    void testGenerateMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size());
        
        
        game.board[0] = 'X';
        game.board[4] = 'O';
        moves.clear();
        game.generateMoves(game.board, moves);
        assertEquals(7, moves.size());
    }

    @Test
    void testEvaluatePosition() {
        
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = 'X';
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(game.board, player1));
        assertEquals(-Game.INF, game.evaluatePosition(game.board, player2));

        
        game = new Game();
        game.board[0] = 'X'; game.board[1] = 'O'; game.board[2] = 'X';
        game.board[3] = 'O'; game.board[4] = 'X'; game.board[5] = 'O';
        game.board[6] = 'O'; game.board[7] = 'X'; game.board[8] = 'O';
        assertEquals(0, game.evaluatePosition(game.board, player1));
    }

    @Test
    void testMiniMax() {
        int move = game.MiniMax(game.board, player1);
        assertTrue(move >= 1 && move <= 9);
        
        game = new Game();
        game.board[0] = 'X'; game.board[1] = 'O'; game.board[2] = 'X';
        game.board[3] = 'O'; game.board[4] = 'X'; game.board[5] = 'O';
        game.board[6] = 'O'; game.board[7] = 'X';
        move = game.MiniMax(game.board, player2);
        assertEquals(9, move);
    }

    @Test
    void testPlayerProperties() {
        assertFalse(player1.selected);
        assertFalse(player1.win);
        assertEquals(0, player1.move);
        
        player1.selected = true;
        player1.win = true;
        player1.move = 5;
        
        assertTrue(player1.selected);
        assertTrue(player1.win);
        assertEquals(5, player1.move);
    }

    @Test
    void testGameStateTransitions() {
    
        assertEquals(State.PLAYING, game.state);
        
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.board[2] = 'X';
        game.symbol = 'X';
        game.state = game.checkState(game.board);
        assertEquals(State.XWIN, game.state);
        
        game = new Game();
        game.board[0] = 'O';
        game.board[1] = 'O';
        game.board[2] = 'O';
        game.symbol = 'O';
        game.state = game.checkState(game.board);
        assertEquals(State.OWIN, game.state);
        
        game = new Game();
        game.board[0] = 'X'; game.board[1] = 'O'; game.board[2] = 'X';
        game.board[3] = 'O'; game.board[4] = 'X'; game.board[5] = 'O';
        game.board[6] = 'O'; game.board[7] = 'X'; game.board[8] = 'O';
        game.state = game.checkState(game.board);
        assertEquals(State.DRAW, game.state);
    }

    @Test
    void testEvaluatePositionEdgeCases() {
        
        assertEquals(-1, game.evaluatePosition(game.board, player1));
        
        game.board[0] = 'X';
        game.board[1] = 'X';
        game.symbol = 'X';
        assertEquals(-1, game.evaluatePosition(game.board, player1));
        
        game = new Game();
        game.board[0] = 'O';
        game.board[1] = 'O';
        game.board[2] = 'O';
        game.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(game.board, player2));
        assertEquals(-Game.INF, game.evaluatePosition(game.board, player1));
    }

    @Test
    void testMinMaxEdgeCases() {
        game.board[0] = 'X'; game.board[1] = 'O'; game.board[2] = 'X';
        game.board[3] = 'O'; game.board[4] = 'X'; game.board[5] = 'O';
        game.board[6] = 'O'; game.board[7] = 'X';
        int move = game.MiniMax(game.board, player2);
        assertEquals(9, move); 
        
        game = new Game();
        game.board[0] = 'X'; game.board[1] = 'X';
        game.symbol = 'X';
        move = game.MiniMax(game.board, player1);
        assertTrue(move >= 1 && move <= 9); 
        
        game = new Game();
        game.board[0] = 'O'; game.board[1] = 'O';
        game.symbol = 'O';
        move = game.MiniMax(game.board, player1);
        assertTrue(move >= 1 && move <= 9); 
    }

    @Test
    void testGenerateMovesEdgeCases() {
        ArrayList<Integer> moves = new ArrayList<>();
        
        for (int i = 0; i < 9; i++) {
            game.board[i] = 'X';
        }
        game.generateMoves(game.board, moves);
        assertEquals(0, moves.size());
        
        game = new Game();
        for (int i = 0; i < 8; i++) {
            game.board[i] = 'X';
        }
        moves.clear();
        game.generateMoves(game.board, moves);
        assertEquals(1, moves.size());
        assertEquals(8, moves.get(0));
    }

    @Test
    void testGameReset() {
        
        for (int i = 0; i < 9; i++) {
            game.board[i] = 'X';
        }
        game.state = State.XWIN;
        
        game = new Game();
        
        assertEquals(State.PLAYING, game.state);
        for (int i = 0; i < 9; i++) {
            assertEquals(' ', game.board[i]);
        }
    }
}
