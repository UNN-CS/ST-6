package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.awt.GridLayout;

/**
 * Unit test for simple App.
 */
public class AppTest {

    /**
     * Rigorous Test :-)
     */

    protected Game game;
    protected Player player;



    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }


    @BeforeEach
    public void setUp() {
        game = new Game();
        player = new Player();
    }


    @Test
    public void testCheckStateDRAW() {
        Game game = new Game();
        char[] board = {
                'O', 'X', 'X',
                'X', 'O', 'O',
                'O', 'O', 'X'
        };
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    public void testCheckStatePLAYING() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {
                ' ', ' ', ' ',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };

        assertEquals(State.PLAYING, game.checkState(board));
    }


    @Test
    public void testCheckStateXWIN() {

        Game game = new Game();
        game.symbol = 'X';
        ArrayList<char[]> board_all = new ArrayList<>();
        char[] board_tem = new char[9];

        for (int i = 0; i < 9; i++) {
            board_tem[i] = (i % 3 == 0) ? 'X' : ' ';
        }
        board_all.add(board_tem.clone());

        for (int i = 0; i < 9; i++) {
            board_tem[i] = (i % 3 == 1) ? 'X' : ' ';
        }
        board_all.add(board_tem.clone());

        for (int i = 0; i < 9; i++) {
            board_tem[i] = (i % 3 == 2) ? 'X' : ' ';
        }
        board_all.add(board_tem.clone());

        for (int i = 0; i < 9; i++) {
            board_tem[i] = (i < 3) ? 'X' : ' ';
        }
        board_all.add(board_tem.clone());

        for (int i = 0; i < 9; i++) {
            board_tem[i] = ((i < 6) && (i > 2)) ? 'X' : ' ';
        }
        board_all.add(board_tem.clone());

        for (int i = 0; i < 9; i++) {
            board_tem[i] = ((i < 9) && (i > 5)) ? 'X' : ' ';
        }
        board_all.add(board_tem.clone());

        char[] board_diagonal_l = {
                'X', ' ', ' ',
                ' ', 'X', ' ',
                ' ', ' ', 'X'
        };
        board_all.add(board_diagonal_l);

        char[] board_diagonal_r = {
                ' ', ' ', 'X',
                ' ', 'X', ' ',
                'X', ' ', ' '
        };
        board_all.add(board_diagonal_r);

        for (char[] b : board_all) {
            assertEquals(State.XWIN, game.checkState(b));
        }
    }
    @Test
    public void testCheckRandomBestXWIN() {

        Game game = new Game();
        game.symbol = 'X';
        char[] board = {
                'X', ' ', ' ',
                ' ', 'X', ' ',
                'X', ' ', ' '
        };
        int ans = game.MiniMax(board, game.player1);
        assertTrue(ans<10);

    }
    @Test
    public void testCheckStateOWIN() {

        Game game = new Game();
        game.symbol = 'O';
        ArrayList<char[]> board_all = new ArrayList<>();
        char[] board_tem = new char[9];

        for (int i = 0; i < 9; i++) {
            board_tem[i] = (i % 3 == 0) ? 'O' : ' ';
        }
        board_all.add(board_tem.clone());

        for (int i = 0; i < 9; i++) {
            board_tem[i] = (i % 3 == 1) ? 'O' : ' ';
        }
        board_all.add(board_tem.clone());

        for (int i = 0; i < 9; i++) {
            board_tem[i] = (i % 3 == 2) ? 'O' : ' ';
        }
        board_all.add(board_tem.clone());

        for (int i = 0; i < 9; i++) {
            board_tem[i] = (i < 3) ? 'O' : ' ';
        }
        board_all.add(board_tem.clone());

        for (int i = 0; i < 9; i++) {
            board_tem[i] = ((i < 6) && (i > 2)) ? 'O' : ' ';
        }
        board_all.add(board_tem.clone());

        for (int i = 0; i < 9; i++) {
            board_tem[i] = ((i < 9) && (i > 5)) ? 'O' : ' ';
        }
        board_all.add(board_tem.clone());

        char[] board_diagonal_l = {
                'O', ' ', ' ',
                ' ', 'O', ' ',
                ' ', ' ', 'O'
        };
        board_all.add(board_diagonal_l);

        char[] board_diagonal_r = {
                ' ', ' ', 'O',
                ' ', 'O', ' ',
                'O', ' ', ' '
        };
        board_all.add(board_diagonal_r);

        for (char[] b : board_all) {
            assertEquals(State.OWIN, game.checkState(b));
        }
    }

    @Test
    public void testCheckStateDraw() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {
                'X', 'O', 'X',
                'X', 'X', 'O',
                'O', 'X', 'O'
        };
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    public  void testCheckStatePlaying() {
        Game game = new Game();
        game.symbol = 'X';
        char[] board = {
                'X', 'O', 'X',
                ' ', 'X', 'O',
                'O', ' ', ' '
        };
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    public  void testGenerateMoves() {
        Game game = new Game();
        char[] board = {
                'X', 'O', 'X',
                ' ', 'X', 'O',
                'O', ' ', ' '
        };
        ArrayList<Integer> moveList = new ArrayList<>();
        game.generateMoves(board, moveList);
        assertEquals(3, moveList.size());
        assertTrue(moveList.contains(3));
        assertTrue(moveList.contains(7));
        assertTrue(moveList.contains(8));
    }

    @Test
    public  void testEvaluatePositionXWin() {
        Game game = new Game();
        char[] board = {
                'X', 'X', 'X',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        game.symbol ='X';
        assertEquals(game.INF, game.evaluatePosition(board, game.player1));
    }

    @Test
    public  void testEvaluatePositionOWin() {
        Game game = new Game();
        char[] board = {
                'O', 'O', 'O',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        game.symbol ='O';
        assertEquals(game.INF, game.evaluatePosition(board, game.player2));
    }

    @Test
    public  void testEvaluatePositionDraw() {
        Game game = new Game();
        char[] board = {
                'X', 'O', 'X',
                'X', 'X', 'O',
                'O', 'X', 'O'
        };
        assertEquals(0, game.evaluatePosition(board, game.player1));
    }

    @Test
    public   void testMinMax() {
        Game game = new Game();
        char[] board = {
                'X', 'O', 'X',
                ' ', 'X', 'O',
                'O', ' ', ' '
        };
        game.symbol = 'X';
        int bestMove = game.MiniMax(board, game.player1);
        assertTrue(bestMove >= 0);
        assertTrue(bestMove <= 9);
    }

    @Test
    public  void testMinMove() {
        Game game = new Game();
        char[] board = {
                'X', 'O', 'X',
                'O', 'X', 'O',
                ' ', ' ', ' '
        };
        game.symbol = 'O';
        int bestValue = game.MinMove(board, game.player2);
        assertTrue(bestValue <= Game.INF && bestValue >= -Game.INF);
    }

    @Test
    public  void testMaxMove() {
        Game game = new Game();
        char[] board = {
                'X', 'O', 'X',
                ' ', 'X', 'O',
                'O', ' ', ' '
        };
        game.symbol = 'X';
        int bestValue = game.MaxMove(board, game.player1);
        assertTrue(bestValue <= Game.INF && bestValue >= -Game.INF);
    }



    @Test
    public void ticTacToeCellTest() {
        TicTacToeCell t = new TicTacToeCell(1, 2, 3);
        assertNotNull(t);
        assertEquals(1, t.getNum());
        assertEquals(2, t.getCol());
        assertEquals(3, t.getRow());
        assertEquals(' ', t.getMarker());
        t.setMarker("X");
        assertEquals('X', t.getMarker());
        t.setMarker("O");
        assertEquals('O', t.getMarker());
    }

    @Test
    public void UtilityTest() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ans_out = System.out;
        System.setOut(new PrintStream(out));
        char[] cout0 ={'X', 'O', 'X',
                'X', 'O', 'O',
                'O', 'X', 'X'};

        Utility.print(cout0);
        String expectedOutput = "X-O-X-X-O-O-O-X-X-";
        assertTrue(out.toString().contains(expectedOutput));
        int[] cout1 = { 0, 1, 2, 3, 4, 5, 6, 7, 8};
        Utility.print(cout1);
        expectedOutput = "0-1-2-3-4-5-6-7-8-";
        assertTrue(out.toString().contains(expectedOutput));
        ArrayList<Integer> cout2 = new ArrayList<>(Arrays.asList(1,1,1,1));
        Utility.print(cout2);
        expectedOutput = "1-1-1-1-";
        assertTrue(out.toString().contains(expectedOutput));
        System.setOut(ans_out);
    }

    @Test
    public void TicTacToePanelTest() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(10, 10));
        assertNotNull(panel);

    }

}
