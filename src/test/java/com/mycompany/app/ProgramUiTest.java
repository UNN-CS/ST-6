package com.mycompany.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import org.junit.Test;

public class ProgramUiTest {

  @Test
  public void createGamePanelBuildsPanelWithNineCells() {
    TicTacToePanel panel = Program.createGamePanel();

    assertNotNull(panel);
    assertEquals(9, panel.getComponentCount());
    assertEquals(State.PLAYING, panel.getGame().state);
  }

  @Test
  public void ticTacToeCellStoresCoordinatesAndMarker() {
    TicTacToeCell cell = new TicTacToeCell(5, 2, 1);

    assertEquals(5, cell.getNum());
    assertEquals(1, cell.getRow());
    assertEquals(2, cell.getCol());
    assertEquals(' ', cell.getMarker());

    cell.setMarker("X");

    assertEquals('X', cell.getMarker());
    assertEquals("X", cell.getText());
    assertFalse(cell.isEnabled());
  }

  @Test
  public void utilityPrintMethodsWriteExpectedContent() {
    PrintStream originalOut = System.out;
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    System.setOut(new PrintStream(output));
    try {
      Utility.print(new char[] {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '});
      Utility.print(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
      ArrayList<Integer> moves = new ArrayList<>();
      moves.add(0);
      moves.add(4);
      moves.add(8);
      Utility.print(moves);
    } finally {
      System.setOut(originalOut);
    }

    String text = output.toString();
    assertTrue(text.contains("X-O-X- - - - - - -"));
    assertTrue(text.contains("1-2-3-4-5-6-7-8-9-"));
    assertTrue(text.contains("0-4-8-"));
  }

  @Test
  public void panelHandlesRegularMoveAndAiResponse() {
    TestableTicTacToePanel panel = new TestableTicTacToePanel();

    panel.actionPerformed(new ActionEvent(panel.getCell(0), ActionEvent.ACTION_PERFORMED, "click"));

    assertEquals('X', panel.getCell(0).getMarker());
    assertTrue(countMarker(panel, 'O') >= 1);
    assertEquals(State.PLAYING, panel.getGame().state);
    assertEquals(panel.getGame().player1, panel.getGame().cplayer);
  }

  @Test
  public void panelReportsXWin() {
    TestableTicTacToePanel panel = new TestableTicTacToePanel();
    seedBoard(panel, new char[] {'X', 'X', ' ', 'O', 'O', ' ', ' ', ' ', ' '});

    panel.actionPerformed(new ActionEvent(panel.getCell(2), ActionEvent.ACTION_PERFORMED, "click"));

    assertEquals(State.XWIN, panel.finishedState);
    assertEquals("X wins", panel.getResultMessage(State.XWIN));
  }

  @Test
  public void panelReportsOWin() {
    TestableTicTacToePanel panel = new TestableTicTacToePanel();
    seedBoard(panel, new char[] {'O', 'O', ' ', 'X', 'X', ' ', ' ', ' ', ' '});

    panel.actionPerformed(new ActionEvent(panel.getCell(8), ActionEvent.ACTION_PERFORMED, "click"));

    assertEquals(State.OWIN, panel.finishedState);
    assertEquals("O wins", panel.getResultMessage(State.OWIN));
  }

  @Test
  public void panelReportsDraw() {
    TestableTicTacToePanel panel = new TestableTicTacToePanel();
    seedBoard(panel, new char[] {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', ' '});

    panel.actionPerformed(new ActionEvent(panel.getCell(8), ActionEvent.ACTION_PERFORMED, "click"));

    assertEquals(State.DRAW, panel.finishedState);
    assertEquals("Draw", panel.getResultMessage(State.DRAW));
  }

  private void seedBoard(TestableTicTacToePanel panel, char[] board) {
    for (int i = 0; i < board.length; i++) {
      if (board[i] != ' ') {
        panel.getCell(i).setMarker(Character.toString(board[i]));
      }
      panel.getGame().board[i] = board[i];
    }
    panel.getGame().cplayer = panel.getGame().player1;
    panel.getGame().state = State.PLAYING;
  }

  private int countMarker(TicTacToePanel panel, char marker) {
    int count = 0;
    for (int i = 0; i < 9; i++) {
      if (panel.getCell(i).getMarker() == marker) {
        count++;
      }
    }
    return count;
  }

  private static class TestableTicTacToePanel extends TicTacToePanel {
    private State finishedState;

    TestableTicTacToePanel() {
      super(new GridLayout(3, 3));
    }

    @Override
    protected void finishGame(State state) {
      finishedState = state;
      getGame().state = state;
    }
  }
}
