package com.mycompany.app;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ProgramTest {
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }
	
	@Test
    public void test_State() {
        assertEquals(4, State.values().length);
        assertEquals(State.PLAYING, State.valueOf("PLAYING"));
        assertEquals(State.XWIN, State.valueOf("XWIN"));
        assertEquals(State.OWIN, State.valueOf("OWIN"));
        assertEquals(State.DRAW, State.valueOf("DRAW"));
    }

	@Test
	public void testPlayerClass() {
		Player p = new Player();
		p.symbol = 'X';
		p.move = -1;
		p.selected = true;
		p.win = false;

		assertEquals(p.symbol, 'X');
		assertEquals(p.move, -1);
		assertEquals(p.selected, true);
		assertEquals(p.win, false);
	}

	@Test
	public void test_Utility_init() {
		assertDoesNotThrow(() -> {
			Utility utility = new Utility();
		});
	}

	@Test
	public void test_Utility_printCharBoard() {
		ByteArrayOutputStream testStream = new ByteArrayOutputStream();
		PrintStream printStream = System.out;
		System.setOut(new PrintStream(testStream));
		
		char[] board = {'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', ' '};
		Utility.print(board);
		String output = testStream.toString();
		assertDoesNotThrow(() -> {
			assertEquals(output.charAt(18), board[8]);
		});
		assertEquals(output.charAt(0), '\r');
		assertEquals(output.charAt(1), '\n');
		assertEquals(output.charAt(2), board[0]);
		assertEquals(output.charAt(3), '-');
		assertEquals(output.charAt(4), board[1]);

		System.setOut(new PrintStream(printStream));
	}

	@Test
	public void test_Utility_printIntBoard() {
		ByteArrayOutputStream testStream = new ByteArrayOutputStream();
		PrintStream printStream = System.out;
		System.setOut(new PrintStream(testStream));

		int[] board = {1, 4, 2, 9, 8, 7, 3, 5, 6};
		Utility.print(board);
		String output = testStream.toString();
		assertDoesNotThrow(() -> {
			assertEquals(output.charAt(18), Integer.toString(board[8]).charAt(0));
		});
		assertEquals(output.charAt(0), '\r');
		assertEquals(output.charAt(1), '\n');
		assertEquals(output.charAt(2), Integer.toString(board[0]).charAt(0));
		assertEquals(output.charAt(3), '-');
		assertEquals(output.charAt(4), Integer.toString(board[1]).charAt(0));

		System.setOut(new PrintStream(printStream));
	}

	@Test
	public void test_Utility_printMoves() {
		ByteArrayOutputStream testStream = new ByteArrayOutputStream();
		PrintStream printStream = System.out;
		System.setOut(new PrintStream(testStream));

		ArrayList<Integer> moves = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			moves.add(i);
		}
		Utility.print(moves);
		String output = testStream.toString();
		for (int i = 0; i < 10; i++) {
			assertEquals(output.charAt(i*2+2), Integer.toString(moves.get(i)).charAt(0));
		}
		System.setOut(new PrintStream(printStream));
	}

	@Test
	public void test_Program() {
		assertDoesNotThrow(() -> {
			Program p = new Program();
			p.main(new String[]{});
		});
	}

	@Test
	public void test_TicTacToeCell() {
		TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
		cell.setMarker("X");


		assertEquals(0, cell.getNum());
		assertEquals(0, cell.getRow());
		assertEquals(0, cell.getCol());
		assertEquals('X', cell.getMarker());
		assertEquals("X", cell.getText());
	}

	@Test
	public void test_TicTacToePanel() {
		TicTacToePanel panel = new TicTacToePanel(new GridLayout(3,3));

		assertDoesNotThrow(() -> {
			ActionEvent ae = new ActionEvent(new Object(), 0, "");
			panel.actionPerformed(ae);
		});
	}

	@Test
	public void test_checkState_XWIN() {
		Game game = new Game();
		game.player1.symbol = 'X';
		game.player2.symbol = 'O';
		game.cplayer = game.player1;
        game.board = new char[]{'X', 'O', 'X', 'O', 'X', ' ', 'X', 'O', ' '};
		game.symbol = 'X';
		game.state = game.checkState(game.board);
		assertEquals(State.XWIN, game.state);

		TicTacToePanel panel = new TicTacToePanel(new GridLayout(3,3));
		assertDoesNotThrow(() -> {
			ActionEvent ae = new ActionEvent(new Object(), 0, "");
			panel.actionPerformed(ae);
		});
	}

	@Test
	public void test_checkState_DRAW() {
		Game game = new Game();
		game.player1.symbol = 'X';
		game.player2.symbol = 'O';
		game.cplayer = game.player1;
		game.board = new char[]{'O', 'X', 'X', 'X', 'O', 'O', 'O', 'O', 'X'};
		game.symbol = 'X';
		game.state = game.checkState(game.board);
		assertEquals(State.DRAW, game.state);
		Player p = new Player();
		p.symbol = 'X';

		assertEquals(game.evaluatePosition(game.board, p), 0);
	}

	@Test
	public void test_checkState_OWIN() {
		Game game = new Game();
		game.player1.symbol = 'X';
		game.player2.symbol = 'O';
		game.cplayer = game.player1;
		game.board = new char[]{' ', ' ', ' ', ' ', ' ', ' ', 'O', 'O', 'O'};
		game.symbol = 'O';
		game.state = game.checkState(game.board);
		assertEquals(State.OWIN, game.state);
		Player p = new Player();
		p.symbol = 'X';
		assertEquals(game.evaluatePosition(game.board, p), -100);
	}

	@Test
	public void test_Game_MinMove() {
		Game game = new Game();
		game.board = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
		Player p = new Player();
		p.symbol = 'X';
		assertEquals(game.MinMove(game.board, p), 0);
	}
}
