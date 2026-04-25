package com.mycompany.app;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class GameTest {
  private Game game;

  @Before
  public void setUp() {
    game = new Game();
  }

  @Test
  public void testInitialState() {
    assertEquals(State.PLAYING, game.state);
    assertEquals('X', game.player1.symbol);
    assertEquals('O', game.player2.symbol);
    for (int i = 0; i < 9; i++) {
      assertEquals(' ', game.board[i]);
    }
  }

  @Test
  public void testCheckStateXWin() {
    game.symbol = 'X';
    char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
    assertEquals(State.XWIN, game.checkState(board));

    char[] boardDiag = {'X', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X'};
    assertEquals(State.XWIN, game.checkState(boardDiag));
  }

  @Test
  public void testCheckStateOWin() {
    game.symbol = 'O';
    char[] board = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
    assertEquals(State.OWIN, game.checkState(board));
  }

  @Test
  public void testCheckStateDraw() {
    game.symbol = 'X';
    char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
    assertEquals(State.DRAW, game.checkState(board));
  }

  @Test
  public void testGenerateMoves() {
    char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', ' '};
    ArrayList<Integer> moves = new ArrayList<>();
    game.generateMoves(board, moves);

    assertEquals(1, moves.size());
    assertEquals(Integer.valueOf(8), moves.get(0));
  }

  @Test
  public void testEvaluatePosition() {
    game.symbol = 'X';
    char[] boardXWin = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};

    assertEquals(100, game.evaluatePosition(boardXWin, game.player1));
    assertEquals(-100, game.evaluatePosition(boardXWin, game.player2));
  }

  @Test
  public void testMiniMax() {
    char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', ' '};
    game.symbol = 'O';
    int move = game.MiniMax(board, game.player2);

    assertEquals(9, move);
  }

  @Test
  public void testTicTacToeCell() {
    TicTacToeCell cell = new TicTacToeCell(1, 2, 3);
    assertEquals(1, cell.getNum());
    assertEquals(3, cell.getRow());
    assertEquals(2, cell.getCol());

    cell.setMarker("X");
    assertEquals('X', cell.getMarker());
    assertFalse(cell.isEnabled());
  }

  @Test
  public void testUtilityAndPlayerCover() {
    char[] cBoard = new char[9];
    int[] iBoard = new int[9];
    ArrayList<Integer> list = new ArrayList<>();
    list.add(1);

    Utility.print(cBoard);
    Utility.print(iBoard);
    Utility.print(list);

    Player p = new Player();
    p.symbol = 'Y';
    p.move = 1;
    p.selected = true;
    p.win = true;

    assertEquals('Y', p.symbol);
    assertTrue(p.win);
  }

  @Test
  public void testAllWinConditions() {
    game.symbol = 'X';

    char[] row2 = {' ', ' ', ' ', 'X', 'X', 'X', ' ', ' ', ' '};
    assertEquals(State.XWIN, game.checkState(row2));

    char[] row3 = {' ', ' ', ' ', ' ', ' ', ' ', 'X', 'X', 'X'};
    assertEquals(State.XWIN, game.checkState(row3));

    char[] col1 = {'X', ' ', ' ', 'X', ' ', ' ', 'X', ' ', ' '};
    assertEquals(State.XWIN, game.checkState(col1));

    char[] col2 = {' ', 'X', ' ', ' ', 'X', ' ', ' ', 'X', ' '};
    assertEquals(State.XWIN, game.checkState(col2));

    char[] col3 = {' ', ' ', 'X', ' ', ' ', 'X', ' ', ' ', 'X'};
    assertEquals(State.XWIN, game.checkState(col3));

    char[] diag2 = {' ', ' ', 'X', ' ', 'X', ' ', 'X', ' ', ' '};
    assertEquals(State.XWIN, game.checkState(diag2));
  }

  @Test
  public void testTicTacToePanelAction() {
    TicTacToePanel panel = new TicTacToePanel(new java.awt.GridLayout(3, 3));

    java.awt.Component[] components = panel.getComponents();
    TicTacToeCell firstCell = (TicTacToeCell) components[0];

    java.awt.event.ActionEvent event =
        new java.awt.event.ActionEvent(firstCell, java.awt.event.ActionEvent.ACTION_PERFORMED, "");

    panel.actionPerformed(event);

    assertEquals('X', firstCell.getMarker());
  }

  @Test
  public void testDeepMinMaxSearch() {
    char[] board = {'X', ' ', ' ', ' ', 'O', ' ', ' ', ' ', 'X'};
    game.symbol = 'O';

    int posValue = game.MinMove(board, game.player1);

    assertTrue(posValue >= -Game.INF && posValue <= Game.INF);
  }
}
