package com.mycompany.app;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

enum State { PLAYING, OWIN, XWIN, DRAW }

class Player {
  public char symbol;
  public int move;
  public boolean selected;
  public boolean win;
}

class Game {
  public static final int INF = 100;

  public State state;
  public Player player1;
  public Player player2;
  public Player cplayer;
  public int nmove;
  public char symbol;
  public int q;
  public char[] board;

  public Game() {
    player1 = new Player();
    player2 = new Player();
    player1.symbol = 'X';
    player2.symbol = 'O';
    state = State.PLAYING;
    board = new char[9];
    for (int i = 0; i < board.length; i++) {
      board[i] = ' ';
    }
  }

  private boolean hasLine(char[] currentBoard, char marker) {
    return (currentBoard[0] == marker && currentBoard[1] == marker && currentBoard[2] == marker)
        || (currentBoard[3] == marker && currentBoard[4] == marker && currentBoard[5] == marker)
        || (currentBoard[6] == marker && currentBoard[7] == marker && currentBoard[8] == marker)
        || (currentBoard[0] == marker && currentBoard[3] == marker && currentBoard[6] == marker)
        || (currentBoard[1] == marker && currentBoard[4] == marker && currentBoard[7] == marker)
        || (currentBoard[2] == marker && currentBoard[5] == marker && currentBoard[8] == marker)
        || (currentBoard[0] == marker && currentBoard[4] == marker && currentBoard[8] == marker)
        || (currentBoard[2] == marker && currentBoard[4] == marker && currentBoard[6] == marker);
  }

  public State checkState(char[] currentBoard) {
    if (hasLine(currentBoard, 'X')) {
      return State.XWIN;
    }
    if (hasLine(currentBoard, 'O')) {
      return State.OWIN;
    }
    for (char cell : currentBoard) {
      if (cell == ' ') {
        return State.PLAYING;
      }
    }
    return State.DRAW;
  }

  void generateMoves(char[] currentBoard, ArrayList<Integer> moveList) {
    for (int i = 0; i < currentBoard.length; i++) {
      if (currentBoard[i] == ' ') {
        moveList.add(i);
      }
    }
  }

  int evaluatePosition(char[] currentBoard, Player player) {
    State currentState = checkState(currentBoard);
    if (currentState == State.XWIN || currentState == State.OWIN || currentState == State.DRAW) {
      if ((currentState == State.XWIN && player.symbol == 'X')
          || (currentState == State.OWIN && player.symbol == 'O')) {
        return INF;
      }
      if ((currentState == State.XWIN && player.symbol == 'O')
          || (currentState == State.OWIN && player.symbol == 'X')) {
        return -INF;
      }
      return 0;
    }
    return -1;
  }

  int MiniMax(char[] currentBoard, Player player) {
    ArrayList<Integer> moveList = new ArrayList<>();
    generateMoves(currentBoard, moveList);
    if (moveList.isEmpty()) {
      return 0;
    }

    int bestVal = -INF;
    int[] bestMoves = new int[moveList.size()];
    int bestCount = 0;

    for (int move : moveList) {
      currentBoard[move] = player.symbol;
      int val = MinMove(currentBoard, player);
      if (val > bestVal) {
        bestVal = val;
        bestMoves[0] = move + 1;
        bestCount = 1;
      } else if (val == bestVal) {
        bestMoves[bestCount++] = move + 1;
      }
      currentBoard[move] = ' ';
    }

    if (bestCount == 1) {
      q = 0;
      return bestMoves[0];
    }

    Random random = new Random();
    int selectedMove = bestMoves[random.nextInt(bestCount)];
    q = 0;
    return selectedMove;
  }

  int MinMove(char[] currentBoard, Player player) {
    int posValue = evaluatePosition(currentBoard, player);
    if (posValue != -1) {
      return posValue;
    }

    q++;
    int bestVal = INF;
    ArrayList<Integer> moveList = new ArrayList<>();
    generateMoves(currentBoard, moveList);

    for (int move : moveList) {
      char enemySymbol = player.symbol == 'X' ? 'O' : 'X';
      currentBoard[move] = enemySymbol;
      int val = MaxMove(currentBoard, player);
      if (val < bestVal) {
        bestVal = val;
      }
      currentBoard[move] = ' ';
    }
    return bestVal;
  }

  int MaxMove(char[] currentBoard, Player player) {
    int posValue = evaluatePosition(currentBoard, player);
    if (posValue != -1) {
      return posValue;
    }

    q++;
    int bestVal = -INF;
    ArrayList<Integer> moveList = new ArrayList<>();
    generateMoves(currentBoard, moveList);

    for (int move : moveList) {
      currentBoard[move] = player.symbol;
      int val = MinMove(currentBoard, player);
      if (val > bestVal) {
        bestVal = val;
      }
      currentBoard[move] = ' ';
    }
    return bestVal;
  }
}

public class Program {
  public static FileWriter fileWriter;
  public static PrintWriter printWriter;

  public static TicTacToePanel createGamePanel() {
    return new TicTacToePanel(new GridLayout(3, 3));
  }

  public static JFrame createFrame() {
    JFrame frame = new JFrame("Demo");
    frame.add(createGamePanel());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(5, 5, 500, 500);
    return frame;
  }

  public static void main(String[] args) throws IOException {
    SwingUtilities.invokeLater(() -> createFrame().setVisible(true));
  }
}

class TicTacToeCell extends JButton {
  private final int num;
  private final int row;
  private final int col;
  private char marker;

  public TicTacToeCell(int num, int x, int y) {
    this.num = num;
    row = y;
    col = x;
    marker = ' ';
    setText(Character.toString(marker));
    setFont(new Font("Arial", Font.PLAIN, 40));
  }

  public void setMarker(String value) {
    marker = value.charAt(0);
    setText(value);
    setEnabled(false);
  }

  public char getMarker() {
    return marker;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public int getNum() {
    return num;
  }
}

class Utility {
  public static void print(char[] board) {
    System.out.println();
    for (char cell : board) {
      System.out.print(cell + "-");
    }
    System.out.println();
  }

  public static void print(int[] board) {
    System.out.println();
    for (int cell : board) {
      System.out.print(cell + "-");
    }
    System.out.println();
  }

  public static void print(ArrayList<Integer> moves) {
    System.out.println();
    for (int move : moves) {
      System.out.print(move + "-");
    }
    System.out.println();
  }
}

class TicTacToePanel extends JPanel implements ActionListener {
  private final TicTacToeCell[] cells = new TicTacToeCell[9];
  private final Game game;

  TicTacToePanel(GridLayout layout) {
    super(layout);
    createCell(0, 0, 0);
    createCell(1, 1, 0);
    createCell(2, 2, 0);
    createCell(3, 0, 1);
    createCell(4, 1, 1);
    createCell(5, 2, 1);
    createCell(6, 0, 2);
    createCell(7, 1, 2);
    createCell(8, 2, 2);
    game = new Game();
    game.cplayer = game.player1;
  }

  private void createCell(int num, int x, int y) {
    cells[num] = new TicTacToeCell(num, x, y);
    cells[num].addActionListener(this);
    add(cells[num]);
  }

  Game getGame() {
    return game;
  }

  TicTacToeCell getCell(int index) {
    return cells[index];
  }

  protected void finishGame(State state) {
    JOptionPane.showMessageDialog(null, getResultMessage(state), "Result", JOptionPane.WARNING_MESSAGE);
    System.exit(0);
  }

  String getResultMessage(State state) {
    if (state == State.XWIN) {
      return "X wins";
    }
    if (state == State.OWIN) {
      return "O wins";
    }
    return "Draw";
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    game.player1.move = -1;
    game.player2.move = -1;

    int index = 0;
    for (TicTacToeCell cell : cells) {
      if (actionEvent.getSource() == cell) {
        cell.setMarker(Character.toString(game.cplayer.symbol));
      }
      game.board[index++] = cell.getMarker();
    }

    if (game.cplayer == game.player1) {
      game.player2.move = game.MiniMax(game.board, game.player2);
      game.nmove = game.player2.move;
      game.symbol = game.player2.symbol;
      game.cplayer = game.player2;
      if (game.player2.move > 0) {
        cells[game.player2.move - 1].doClick();
      }
    } else {
      game.nmove = game.player1.move;
      game.symbol = game.player1.symbol;
      game.cplayer = game.player1;
    }

    game.state = game.checkState(game.board);
    if (game.state != State.PLAYING) {
      finishGame(game.state);
    }
  }
}
