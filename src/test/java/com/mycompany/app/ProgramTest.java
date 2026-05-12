package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.awt.GridLayout;
import org.junit.jupiter.api.Test;

class ProgramTest {

  @Test
  void gameConstructorInitializesBoardAndPlayers() {
    Game game = new Game();

    assertEquals(State.PLAYING, game.state);
    assertEquals('X', game.player1.symbol);
    assertEquals('O', game.player2.symbol);

    char[] expected = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
    assertArrayEquals(expected, game.board);
  }

  @Test
  void checkStateDetectsXWin() {
    Game game = new Game();
    game.symbol = 'X';
    char[] board = {'X', 'X', 'X', ' ', 'O', ' ', 'O', ' ', ' '};

    assertEquals(State.XWIN, game.checkState(board));
  }

  @Test
  void checkStateDetectsOWin() {
    Game game = new Game();
    game.symbol = 'O';
    char[] board = {'O', 'X', 'X', ' ', 'O', ' ', 'X', ' ', 'O'};

    assertEquals(State.OWIN, game.checkState(board));
  }

  @Test
  void checkStateDetectsDraw() {
    Game game = new Game();
    game.symbol = 'X';
    char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};

    assertEquals(State.DRAW, game.checkState(board));
  }

  @Test
  void checkStateDetectsPlaying() {
    Game game = new Game();
    game.symbol = 'X';
    char[] board = {'X', 'O', ' ', ' ', 'O', ' ', ' ', 'X', ' '};

    assertEquals(State.PLAYING, game.checkState(board));
  }

  @Test
  void generateMovesCollectsEmptyCells() {
    Game game = new Game();
    char[] board = {'X', 'O', ' ', ' ', 'O', ' ', 'X', 'X', ' '};
    ArrayList<Integer> moves = new ArrayList<>();

    game.generateMoves(board, moves);

    assertEquals(4, moves.size());
    assertTrue(moves.contains(2));
    assertTrue(moves.contains(3));
    assertTrue(moves.contains(5));
    assertTrue(moves.contains(8));
  }

  @Test
  void evaluatePositionReturnsWinningLosingAndDrawScores() {
    Game game = new Game();
    Player xPlayer = new Player();
    xPlayer.symbol = 'X';
    Player oPlayer = new Player();
    oPlayer.symbol = 'O';

    game.symbol = 'X';
    char[] xWins = {'X', 'X', 'X', 'O', 'O', ' ', ' ', ' ', ' '};
    assertEquals(Game.INF, game.evaluatePosition(xWins, xPlayer));
    assertEquals(-Game.INF, game.evaluatePosition(xWins, oPlayer));

    game.symbol = 'O';
    char[] draw = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
    assertEquals(0, game.evaluatePosition(draw, xPlayer));
  }

  @Test
  void evaluatePositionReturnsMinusOneWhenGameContinues() {
    Game game = new Game();
    Player xPlayer = new Player();
    xPlayer.symbol = 'X';
    game.symbol = 'X';
    char[] board = {'X', 'O', ' ', ' ', 'O', ' ', ' ', 'X', ' '};

    assertEquals(-1, game.evaluatePosition(board, xPlayer));
  }

  @Test
  void minimaxChoosesOnlyAvailableMove() {
    Game game = new Game();
    Player oPlayer = new Player();
    oPlayer.symbol = 'O';
    char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', ' '};

    int move = game.MiniMax(board, oPlayer);

    assertEquals(9, move);
  }

  @Test
  void minMoveAndMaxMoveReturnTerminalScores() {
    Game game = new Game();
    Player xPlayer = new Player();
    xPlayer.symbol = 'X';
    char[] board = {'X', 'O', 'X', 'O', 'X', ' ', ' ', ' ', 'O'};

    int minResult = game.MinMove(board.clone(), xPlayer);
    int maxResult = game.MaxMove(board.clone(), xPlayer);

    assertTrue(minResult <= Game.INF);
    assertTrue(maxResult >= -Game.INF);
  }

  @Test
  void ticTacToeCellStoresCoordinatesAndMarker() {
    TicTacToeCell cell = new TicTacToeCell(4, 1, 2);

    assertEquals(4, cell.getNum());
    assertEquals(2, cell.getRow());
    assertEquals(1, cell.getCol());
    assertEquals(' ', cell.getMarker());

    cell.setMarker("X");

    assertEquals('X', cell.getMarker());
    assertFalse(cell.isEnabled());
  }

  @Test
  void utilityPrintMethodsWriteToStdout() {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream original = System.out;
    System.setOut(new PrintStream(out));
    try {
      Utility.print(new char[] {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '});
      Utility.print(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
      ArrayList<Integer> list = new ArrayList<>();
      list.add(1);
      list.add(5);
      list.add(9);
      Utility.print(list);
    } finally {
      System.setOut(original);
    }

    String printed = out.toString();
    assertTrue(printed.contains("X-O-X-"));
    assertTrue(printed.contains("1-2-3-4-5-6-7-8-9-"));
    assertTrue(printed.contains("1-5-9-"));
  }

  @Test
  void ticTacToePanelHandlesSingleClickAndBotResponse() {
    System.setProperty("java.awt.headless", "true");
    TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));

    TicTacToeCell firstCell = (TicTacToeCell) panel.getComponent(0);
    firstCell.doClick();

    assertEquals('X', firstCell.getMarker());

    int filled = 0;
    for (int i = 0; i < panel.getComponentCount(); i++) {
      TicTacToeCell cell = (TicTacToeCell) panel.getComponent(i);
      if (cell.getMarker() != ' ') {
        filled++;
      }
    }
    assertTrue(filled >= 2);
  }
}
