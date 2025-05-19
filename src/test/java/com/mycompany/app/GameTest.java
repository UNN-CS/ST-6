package com.mycompany.app;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class GameTest {
    
    @Test
    public void testGameInitialization() {
        Game game = new Game();
        
        assertEquals(State.PLAYING, game.state);
        assertNotNull(game.player1);
        assertNotNull(game.player2);
        assertEquals(9, game.board.length);
        
        for(char c : game.board) {
            assertEquals(' ', c);
        }
    }
    
    @Test
    public void testCheckStateXWin() {
        Game game = new Game();
        char[] board = {
            'X', 'X', 'X',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        game.symbol = 'X';
        
        assertEquals(State.XWIN, game.checkState(board));
    }
    
    @Test
    public void testCheckStateOWin() {
        Game game = new Game();
        char[] board = {
            'O', ' ', ' ',
            'O', ' ', ' ',
            'O', ' ', ' '
        };
        game.symbol = 'O';
        
        assertEquals(State.OWIN, game.checkState(board));
    }
    
    @Test
    public void testCheckStateDraw() {
        Game game = new Game();
        char[] board = {
            'X', 'O', 'X',
            'X', 'O', 'O',
            'O', 'X', 'X'
        };
        game.symbol = 'X';
        
        assertEquals(State.DRAW, game.checkState(board));
    }
    
    @Test
    public void testCheckStatePlaying() {
        Game game = new Game();
        char[] board = {
            'X', 'O', 'X',
            'X', ' ', 'O',
            'O', 'X', 'X'
        };
        game.symbol = 'X';
        
        assertEquals(State.PLAYING, game.checkState(board));
    }
    
    @Test
    public void testGenerateMoves() {
        Game game = new Game();
        char[] board = {
            'X', ' ', ' ',
            ' ', 'O', ' ',
            ' ', ' ', 'X'
        };
        ArrayList<Integer> moves = new ArrayList<>();
        
        game.generateMoves(board, moves);
        
        assertEquals(6, moves.size());
        assertTrue(moves.contains(1));
        assertTrue(moves.contains(2));
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(5));
        assertTrue(moves.contains(6));
        assertTrue(moves.contains(7));
    }
    
    @Test
    public void testEvaluatePositionXWin() {
        Game game = new Game();
        char[] board = {
            'X', 'X', 'X',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        game.symbol = 'X';
        
        assertEquals(Game.INF, game.evaluatePosition(board, game.player1));
        assertEquals(-Game.INF, game.evaluatePosition(board, game.player2));
    }
    
    @Test
    public void testEvaluatePositionOWin() {
        Game game = new Game();
        char[] board = {
            'O', ' ', ' ',
            'O', ' ', ' ',
            'O', ' ', ' '
        };
        game.symbol = 'O';
        
        assertEquals(Game.INF, game.evaluatePosition(board, game.player2));
        assertEquals(-Game.INF, game.evaluatePosition(board, game.player1));
    }
    
    @Test
    public void testEvaluatePositionDraw() {
        Game game = new Game();
        char[] board = {
            'X', 'O', 'X',
            'X', 'O', 'O',
            'O', 'X', 'X'
        };
        game.symbol = 'X';
        
        assertEquals(0, game.evaluatePosition(board, game.player1));
        assertEquals(0, game.evaluatePosition(board, game.player2));
    }
    
    @Test
    public void testEvaluatePositionPlaying() {
        Game game = new Game();
        char[] board = {
            'X', 'O', 'X',
            'X', ' ', 'O',
            'O', 'X', 'X'
        };
        game.symbol = 'X';
        
        assertEquals(-1, game.evaluatePosition(board, game.player1));
        assertEquals(-1, game.evaluatePosition(board, game.player2));
    }
    
    
    @Test
    public void testMiniMaxWinsWhenPossible() {
        Game game = new Game();
        char[] board = {
            'O', 'O', ' ',
            'X', 'X', ' ',
            ' ', ' ', ' '
        };
        game.board = board.clone();
        
        int bestMove = game.MiniMax(board, game.player2);
        
        assertEquals(3, bestMove); // Должен завершить победу O в позиции 3 (индекс 2)
    }
}
