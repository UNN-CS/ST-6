package com.mycompany.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

  private Game game;

  @BeforeEach
  public void setUp() {
    game = new Game();
    game.cplayer = game.player1;
  }

  @Test
  public void testInitialState() {
    assertEquals(State.PLAYING, game.state);
  }

  @Test
  public void testInitialBoard() {
    for (char c : game.board) assertEquals(' ', c);
  }

  @Test
  public void testPlayer1IsX() {
    assertEquals('X', game.player1.symbol);
  }

  @Test
  public void testPlayer2IsO() {
    assertEquals('O', game.player2.symbol);
  }

  @Test
  public void testCheckStatePlayingEmpty() {
    game.symbol = 'X';
    assertEquals(State.PLAYING, game.checkState(game.board));
  }

  @Test
  public void testCheckStateXWinRow() {
    game.board[0] = 'X'; game.board[1] = 'X'; game.board[2] = 'X';
    game.symbol = 'X';
    assertEquals(State.XWIN, game.checkState(game.board));
  }

  @Test
  public void testCheckStateOWinRow() {
    game.board[3] = 'O'; game.board[4] = 'O'; game.board[5] = 'O';
    game.symbol = 'O';
    assertEquals(State.OWIN, game.checkState(game.board));
  }

  @Test
  public void testCheckStateXWinCol() {
    game.board[0] = 'X'; game.board[3] = 'X'; game.board[6] = 'X';
    game.symbol = 'X';
    assertEquals(State.XWIN, game.checkState(game.board));
  }

  @Test
  public void testCheckStateXWinDiag() {
    game.board[0] = 'X'; game.board[4] = 'X'; game.board[8] = 'X';
    game.symbol = 'X';
    assertEquals(State.XWIN, game.checkState(game.board));
  }

  @Test
  public void testCheckStateXWinAntiDiag() {
    game.board[2] = 'X'; game.board[4] = 'X'; game.board[6] = 'X';
    game.symbol = 'X';
    assertEquals(State.XWIN, game.checkState(game.board));
  }

  @Test
  public void testCheckStateDraw() {
    game.board = new char[]{'X','O','X','X','O','X','O','X','O'};
    game.symbol = 'X';
    assertEquals(State.DRAW, game.checkState(game.board));
  }

  @Test
  public void testGenerateMovesEmpty() {
    ArrayList<Integer> moves = new ArrayList<>();
    game.generateMoves(game.board, moves);
    assertEquals(9, moves.size());
  }

  @Test
  public void testGenerateMovesPartial() {
    game.board[0] = 'X'; game.board[4] = 'O';
    ArrayList<Integer> moves = new ArrayList<>();
    game.generateMoves(game.board, moves);
    assertEquals(7, moves.size());
  }

  @Test
  public void testGenerateMovesFull() {
    game.board = new char[]{'X','O','X','X','O','X','O','X','O'};
    ArrayList<Integer> moves = new ArrayList<>();
    game.generateMoves(game.board, moves);
    assertEquals(0, moves.size());
  }

  @Test
  public void testEvaluatePositionXWins() {
    game.board[0] = 'X'; game.board[1] = 'X'; game.board[2] = 'X';
    game.symbol = 'X';
    assertEquals(Game.INF, game.evaluatePosition(game.board, game.player1));
  }

  @Test
  public void testEvaluatePositionOWins() {
    game.board[0] = 'O'; game.board[3] = 'O'; game.board[6] = 'O';
    game.symbol = 'O';
    assertEquals(-Game.INF, game.evaluatePosition(game.board, game.player1));
  }

  @Test
  public void testEvaluatePositionDraw() {
    game.board = new char[]{'X','O','X','X','O','X','O','X','O'};
    game.symbol = 'X';
    assertEquals(0, game.evaluatePosition(game.board, game.player1));
  }

  @Test
  public void testEvaluatePositionPlaying() {
    game.symbol = 'X';
    assertEquals(-1, game.evaluatePosition(game.board, game.player1));
  }

  @Test
  public void testMiniMaxReturnsValidMove() {
    game.symbol = 'X';
    int move = game.MiniMax(game.board, game.player2);
    assertTrue(move >= 1 && move <= 9);
  }

  @Test
  public void testMiniMaxBlocksWin() {
    // X has two in a row, O should block
    game.board[0] = 'X'; game.board[1] = 'X';
    game.symbol = 'O';
    int move = game.MiniMax(game.board, game.player2);
    assertEquals(3, move); // block at position 3 (index 2, 1-based = 3)
  }

  @Test
  public void testMinMoveOnTerminal() {
    game.board[0] = 'X'; game.board[1] = 'X'; game.board[2] = 'X';
    game.symbol = 'X';
    int val = game.MinMove(game.board, game.player1);
    assertEquals(Game.INF, val);
  }

  @Test
  public void testMaxMoveOnTerminal() {
    game.board[0] = 'O'; game.board[3] = 'O'; game.board[6] = 'O';
    game.symbol = 'O';
    int val = game.MaxMove(game.board, game.player1);
    assertEquals(-Game.INF, val);
  }

  @Test
  public void testFullGameNoDraw() {
    game.symbol = 'X';
    char current = 'X';
    while (true) {
      game.symbol = current;
      State s = game.checkState(game.board);
      if (s != State.PLAYING) break;
      Player p = (current == 'X') ? game.player1 : game.player2;
      int move = game.MiniMax(game.board, p);
      game.board[move - 1] = current;
      current = (current == 'X') ? 'O' : 'X';
    }
    game.symbol = 'X';
    State finalState = game.checkState(game.board);
    assertTrue(finalState != State.PLAYING);
  }
}
