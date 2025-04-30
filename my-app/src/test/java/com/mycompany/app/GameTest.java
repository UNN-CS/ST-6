package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class GameTest {

    private static final List<Integer> VALID_MOVES = Arrays.asList(1,2,3,4,5,6,7,8,9);

    @Test
    public void testMiniMaxProducesNonLosingMove() {
        Game game = new Game();
        game.player2.symbol = 'O';

        char[] testBoard = {
                'X', 'O', 'X',
                ' ', ' ', ' ',
                ' ', ' ', 'O'
        };
        game.board = Arrays.copyOf(testBoard, 9);

        int move = game.MiniMax(game.board, game.player2);

        assertTrue(VALID_MOVES.contains(move),
                () -> "Ожидался ход от 1 до 9, но вернули " + move);

        assertEquals(' ', testBoard[move - 1],
                "Ход должен быть сделан в пустую клетку");

        // Проверим — есть ли вообще не проигрышный ход?
        boolean hasNonLosing = false;
        for (int i = 0; i < 9; i++) {
            if (testBoard[i] == ' ') {
                char[] next = Arrays.copyOf(testBoard, 9);
                next[i] = game.player2.symbol;
                int eval = game.evaluatePosition(next, game.player2);
                if (eval >= 0) {
                    hasNonLosing = true;
                    break;
                }
            }
        }

        // Если есть не проигрышный — проверим, что выбран именно он
        if (hasNonLosing) {
            char[] next = Arrays.copyOf(testBoard, 9);
            next[move - 1] = game.player2.symbol;
            int eval = game.evaluatePosition(next, game.player2);
            assertTrue(eval >= 0,
                    "Минимакс вернул проигрышный ход (оценка " + eval + ")");
        }
    }




    @Test
    public void testMiniMaxWinningMove() {
        Game game = new Game();
        game.player2.symbol = 'O';

        // O может выиграть, поставив в ячейку 3
        char[] testBoard = {
                'O', 'O', ' ',
                'X', 'X', ' ',
                ' ', ' ', ' '
        };
        game.board = Arrays.copyOf(testBoard, 9);

        int move = game.MiniMax(game.board, game.player2);
        assertEquals(3, move, "O должен победить, поставив в ячейку 3");
    }

    @Test
    public void testMiniMaxBlocksImmediateLoss() {
        Game game = new Game();
        game.player2.symbol = 'O';

        // X почти выиграл в верхнем ряду — O должен блокировать в ячейке 3
        char[] testBoard = {
                'X', 'X', ' ',
                ' ', 'O', ' ',
                ' ', ' ', ' '
        };
        game.board = Arrays.copyOf(testBoard, 9);

        int move = game.MiniMax(game.board, game.player2);
        assertEquals(3, move, "O должен заблокировать победу X в ячейке 3");
    }

    @Test
    public void testCheckStateXWin() {
        Game game = new Game();
        // Симулируем победу X на главной диагонали
        char[] diag = {'X',' ',' ', ' ','X',' ', ' ',' ','X'};
        game.symbol = 'X';
        State result = game.checkState(diag);
        assertEquals(State.XWIN, result);
    }

    @Test
    public void testEvaluatePositionDraw() {
        Game game = new Game();
        // Ничейная доска
        char[] draw = {
                'X','O','X',
                'X','O','O',
                'O','X','X'
        };
        game.symbol = 'X';
        int val = game.evaluatePosition(draw, game.player1);
        assertEquals(0, val, "Ничья должна оцениваться как 0");
    }
    @Test
    public void testMiniMaxOnEmptyBoard() {
        Game game = new Game();
        game.player2.symbol = 'O';

        char[] empty = {
                ' ', ' ', ' ',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        game.board = Arrays.copyOf(empty, 9);

        int move = game.MiniMax(game.board, game.player2);
        assertTrue(VALID_MOVES.contains(move), "Ход должен быть от 1 до 9");
    }
    @Test
    public void testMiniMaxWithOneMoveLeft() {
        Game game = new Game();
        game.player2.symbol = 'O';

        char[] almostFull = {
                'X','O','X',
                'X','O','O',
                'O','X',' '
        };
        game.board = Arrays.copyOf(almostFull, 9);

        int move = game.MiniMax(game.board, game.player2);
        assertEquals(9, move, "Единственный возможный ход — 9");
    }
    @Test
    public void testCheckStateDraw() {
        Game game = new Game();
        char[] draw = {
                'X','O','X',
                'X','O','O',
                'O','X','X'
        };
        game.symbol = 'X';
        State result = game.checkState(draw);
        assertEquals(State.DRAW, result);
    }


}