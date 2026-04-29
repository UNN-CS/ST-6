package com.mycompany.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Test;

public class GameTest {

  @Test
  public void constructorInitializesBoardAndPlayers() {
    Game game = new Game();

    assertEquals(State.PLAYING, game.state);
    assertEquals('X', game.player1.symbol);
    assertEquals('O', game.player2.symbol);
    for (char cell : game.board) {
      assertEquals(' ', cell);
    }
  }

  @Test
  public void checkStateDetectsAllStates() {
    Game game = new Game();

    assertEquals(State.XWIN, game.checkState(new char[] {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '}));
    assertEquals(State.OWIN, game.checkState(new char[] {'O', ' ', ' ', 'O', ' ', ' ', 'O', ' ', ' '}));
    assertEquals(State.DRAW, game.checkState(new char[] {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'}));
    assertEquals(State.PLAYING, game.checkState(new char[] {'X', 'O', ' ', ' ', 'O', ' ', ' ', 'X', ' '}));
  }

  @Test
  public void generateMovesReturnsOnlyEmptyCells() {
    Game game = new Game();
    ArrayList<Integer> moves = new ArrayList<>();

    game.generateMoves(new char[] {'X', 'O', ' ', ' ', 'O', ' ', 'X', 'X', ' '}, moves);

    assertEquals(4, moves.size());
    assertEquals(Integer.valueOf(2), moves.get(0));
    assertEquals(Integer.valueOf(3), moves.get(1));
    assertEquals(Integer.valueOf(5), moves.get(2));
    assertEquals(Integer.valueOf(8), moves.get(3));
  }

  @Test
  public void evaluatePositionScoresWinsLossesAndDraw() {
    Game game = new Game();

    assertEquals(Game.INF, game.evaluatePosition(
        new char[] {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '}, game.player1));
    assertEquals(-Game.INF, game.evaluatePosition(
        new char[] {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '}, game.player2));
    assertEquals(0, game.evaluatePosition(
        new char[] {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'}, game.player1));
    assertEquals(-1, game.evaluatePosition(
        new char[] {'X', 'O', ' ', ' ', 'O', ' ', ' ', 'X', ' '}, game.player2));
  }

  @Test
  public void miniMaxChoosesImmediateWinningMove() {
    Game game = new Game();
    char[] board = {'O', 'O', ' ', 'X', 'X', ' ', ' ', ' ', ' '};

    int move = game.MiniMax(board, game.player2);

    assertEquals(3, move);
  }

  @Test
  public void miniMaxReturnsZeroWhenBoardIsFull() {
    Game game = new Game();

    int move = game.MiniMax(new char[] {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'}, game.player2);

    assertEquals(0, move);
  }

  @Test
  public void minAndMaxMoveResolveTerminalPositions() {
    Game game = new Game();

    assertEquals(Game.INF,
        game.MaxMove(new char[] {'O', 'O', 'O', 'X', 'X', ' ', ' ', ' ', ' '}, game.player2));
    assertEquals(-Game.INF,
        game.MinMove(new char[] {'X', 'X', 'X', 'O', ' ', ' ', ' ', ' ', ' '}, game.player2));
  }

  @Test
  public void minimaxExploresTreeAndResetsCounter() {
    Game game = new Game();
    char[] board = {'X', 'O', 'X', ' ', 'O', ' ', ' ', ' ', ' '};

    int move = game.MiniMax(board, game.player2);

    assertTrue(move > 0);
    assertEquals(0, game.q);
  }
}
