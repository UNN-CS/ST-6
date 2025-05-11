package com.mycompany.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class GameTest {
    private Game game;

    @BeforeEach
    void init() {
        game = new Game();
    }

    @Test
    void Game_Constructor() {
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertEquals(State.PLAYING, game.state);
        for (int i = 0; i < 9; i++) {
            assertEquals(game.board[i], ' ');
        }
    }

    @Test
    void checkState_EmptyBoard() {
        for (int i = 0; i < 9; i++) { 
            game.board[i] = ' ';
        }
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(game.board));
    }

    @Test
    void checkState_XWins() {
        game.board = new char[] {
            'X', 'X', 'X',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(game.board));
    }

    @Test
    void checkState_OWins() {
        game.board = new char[] {
            'O', ' ', ' ',
            'O', ' ', ' ',
            'O', ' ', ' '
        };
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(game.board));
    }

    @Test
    void checkState_Draw() {
        game.board = new char[] {
            'X', 'O', 'X',
            'X', 'O', 'O',
            'O', 'X', 'X'
        };
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(game.board));
    }

    @Test
    void generateMoves_ForEmptyBoard() {
        ArrayList<Integer> moves = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            game.board[i] = ' ';
        }    
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size());
    }

    @Test
    void generateMoves_ForNotEmptyBoard() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.board = new char[] {
            'X', ' ', 'O',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        game.generateMoves(game.board, moves);
        assertFalse(moves.contains(0));
        assertFalse(moves.contains(2));
        assertEquals(7, moves.size());
    }

    @Test
    void evaluatePosition_Win() {
        game.board = new char[] {
            'X', 'X', 'X',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(game.board, game.player1));
    }

    @Test
    void evaluatePosition_Lose() {
        game.board = new char[] {
            'O', 'O', 'O',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        game.symbol = 'O';
        assertEquals(-Game.INF, game.evaluatePosition(game.board, game.player1));
    }

    @Test
    void evaluatePosition_Draw() {
        game.board = new char[] {
            'X', 'O', 'X',
            'X', 'O', 'O',
            'O', 'X', 'X'
        };
        game.symbol = 'X';
        assertEquals(0, game.evaluatePosition(game.board, game.player1));
        assertEquals(0, game.evaluatePosition(game.board, game.player2));
    }

    @Test
    void evaluatePosition_Playing() {
        game.board = new char[] {
            'X', 'O', ' ',
            ' ', ' ', ' ',
            ' ', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(-1, game.evaluatePosition(game.board, game.player1));
        assertEquals(-1, game.evaluatePosition(game.board, game.player2));
    }

    @Test
    void MiniMax_RowWin() {
        Game game = new Game();
        game.board = new char[] {
            'X', 'X', ' ',
            ' ', ' ', ' ',
            'O', ' ', 'O'
        };
        int move = game.MiniMax(game.board, game.player1);
        assertEquals(3, move);
    }

    @Test
    void MiniMax_Column() {
        Game game = new Game();
        game.board = new char[] {
            'X', ' ', 'O',
            'X', ' ', ' ',
            ' ', 'O', ' '
        };
        int move = game.MiniMax(game.board, game.player1);
        assertEquals(7, move);
    }

    @Test
    void MiniMax_Diagonal() {
        Game game = new Game();
        game.board = new char[] {
            'X', ' ', 'O',
            ' ', 'X', 'O',
            ' ', ' ', ' '
        };
        int move = game.MiniMax(game.board, game.player1);
        assertEquals(9, move);
    }
}
