package com.mycompany.app;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

enum State { PLAYING, OWIN, XWIN, DRAW };

class Player {
  public char symbol;
  public int move;
  public boolean selected;
  public boolean win;
}

class Game {
    public State state;
    public Player player1, player2;
    public Player cplayer; // текущий игрок
    public int nmove;  // последний шаг сделанный действующим игроком 
    public char symbol;
    public static final int INF = 100;
    public int q;
    public char[] board;

    public Game() {
      player1 = new Player();
      player2 = new Player();
      player1.symbol = 'X';
      player2.symbol = 'O';
      state = State.PLAYING; 
      board = new char[9];   // текущая доска в игре  
      for(int i = 0; i < 9; i++)
        board[i] = ' ';
    }

    // возвращаем состояние игры
    public State checkState(char[] board) 
    {
      State state = State.PLAYING;
      if ((board[0] == symbol && board[1] == symbol && board[2] == symbol) ||
          (board[3] == symbol && board[4] == symbol && board[5] == symbol) ||
          (board[6] == symbol && board[7] == symbol && board[8] == symbol) ||
          (board[0] == symbol && board[3] == symbol && board[6] == symbol) ||
          (board[1] == symbol && board[4] == symbol && board[7] == symbol) ||
          (board[2] == symbol && board[5] == symbol && board[8] == symbol) ||
          (board[0] == symbol && board[4] == symbol && board[8] == symbol) ||
          (board[2] == symbol && board[4] == symbol && board[6] == symbol)) 
      {
        if (symbol == 'X')   
            state = State.XWIN;
        else if (symbol == 'O')  
            state = State.OWIN;
      }
      else {
        state = State.DRAW;
        for (int i = 0; i < 9; i++) 
        {
            if (board[i] == ' ') {
                state = State.PLAYING;
                break;
            }
        }
      }
      return state;
    }
    
    // сгенерировать возможные ходы
    public void generateMoves(char[] board, ArrayList<Integer> move_list) {
      for (int i = 0; i < 9; i++) 
          if (board[i] == ' ') 
              move_list.add(i);
    }

    // оценка позиции
    public int evaluatePosition(char[] board, Player player)  
    {
      State state = checkState(board);
      if ((state == State.XWIN || state == State.OWIN || state == State.DRAW)) 
      {
          if ((state == State.XWIN && player.symbol == 'X') || (state == State.OWIN && player.symbol == 'O')) 
              return +Game.INF;
          else if ((state == State.XWIN && player.symbol == 'O') || (state == State.OWIN && player.symbol == 'X')) 
              return -Game.INF;
          else if (state == State.DRAW) 
              return 0;
      }
      return -1;
    }

    public int MiniMax(char[] board, Player player) // выбор наилучшего хода
    {
      int best_val = -Game.INF, index = 0;
      ArrayList<Integer> move_list = new ArrayList<>();
      int[] best_moves = new int[9];
  
      generateMoves(board, move_list); 

      while (move_list.size() != 0) { 
          board[move_list.get(0)] = player.symbol; 
          symbol = player.symbol;
  
          int val = MinMove(board, player); 

          if (val > best_val) { 
              best_val = val;
              index = 0;
              best_moves[index] = move_list.get(0) + 1; 
          }
          else if (val == best_val)
              best_moves[++index] = move_list.get(0) + 1; 
  
          board[move_list.get(0)] = ' '; 
          move_list.remove(0);
      }
      if (index > 0) {
        Random r = new Random();
        index = r.nextInt(index + 1);
      }
    
      q = 0;
      return best_moves[index];
    }
    
    public int MinMove(char[] board, Player player) {
      int pos_value = evaluatePosition(board, player); 
      if (pos_value != -1) 
        return pos_value;
      q++;
      int best_val = +Game.INF;
      ArrayList<Integer> move_list = new ArrayList<>();
      
      generateMoves(board, move_list); 

      while (move_list.size() != 0) { 
          symbol = (player.symbol == 'X') ? 'O' : 'X'; 
          board[move_list.get(0)] = symbol; 

          int val = MaxMove(board, player); 
          
          if (val < best_val) {
              best_val = val;  
          }
          board[move_list.get(0)] = ' ';
          move_list.remove(0);
      }
      return best_val;
    }

    public int MaxMove(char[] board, Player player) {
      int pos_value = evaluatePosition(board, player);
      if (pos_value != -1) 
        return pos_value;
      q++;
      int best_val = -Game.INF;
      ArrayList<Integer> move_list = new ArrayList<>();
      generateMoves(board, move_list);
      while (move_list.size() != 0) {
          symbol = (player.symbol == 'X') ? 'X' : 'O'; 
          board[move_list.get(0)] = symbol;
          int val = MinMove(board, player);
          if (val > best_val) {
              best_val = val;
          }
          board[move_list.get(0)] = ' ';
          move_list.remove(0);
      }
      return best_val;
    }
    
    // Делает ход и возвращает новое состояние игры
    public State makeMove(int position, char playerSymbol) {
        if (position < 0 || position >= 9 || board[position] != ' ') {
            return state; // Недопустимый ход
        }
        
        board[position] = playerSymbol;
        symbol = playerSymbol;
        state = checkState(board);
        return state;
    }
    
    // Получить текущую доску
    public char[] getBoard() {
        return board;
    }
    
    // Сбросить игру
    public void resetGame() {
        state = State.PLAYING;
        for(int i = 0; i < 9; i++) {
            board[i] = ' ';
        }
    }
}

class TicTacToeCell extends JButton {
    private int num;
    private int row;
    private int col;
    private char marker;

    public TicTacToeCell(int num, int x, int y) {
        this.num = num;
        row = y;
        col = x;
        marker = ' ';
        setText(Character.toString(marker));
        setFont(new Font("Arial", Font.PLAIN, 40));
    }
    public void setMarker(String m) {
        marker = m.charAt(0);
        setText(m);
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

class TicTacToePanel extends JPanel implements ActionListener {
    private Game game;
    private boolean gameOver;
    private boolean computerTurn;
    
    private void createCell(int num, int x, int y) {
        cells[num] = new TicTacToeCell(num, x, y);
        cells[num].addActionListener(this);
        add(cells[num]);
    }
    
    private TicTacToeCell[] cells = new TicTacToeCell[9];
    
    TicTacToePanel(GridLayout layout) {
        game = new Game();
        gameOver = false;
        computerTurn = false;
        setLayout(layout);
        
        int num = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                createCell(num, j, i);
                num++;
            }
        }
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (gameOver || computerTurn) 
            return;
            
        TicTacToeCell cell = (TicTacToeCell)ae.getSource();
        int cellNum = cell.getNum();
        
        // Ход игрока
        State state = game.makeMove(cellNum, 'X');
        cell.setMarker("X");
        
        // Проверяем состояние игры
        if (state != State.PLAYING) {
            gameOver = true;
            showGameResult(state);
            return;
        }
        
        // Ход компьютера
        computerTurn = true;
        
        // Создаем final копию переменной state для использования в лямбда-выражении
        final State currentState = state;
        
        Timer timer = new Timer(500, e -> {
            // Находим лучший ход для компьютера
            int computerMove = game.MiniMax(game.board, game.player2) - 1;
            
            // Делаем ход
            State newState = game.makeMove(computerMove, 'O');
            cells[computerMove].setMarker("O");
            
            // Проверяем состояние игры
            if (newState != State.PLAYING) {
                gameOver = true;
                showGameResult(newState);
            }
            
            computerTurn = false;
            ((Timer)e.getSource()).stop();
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void showGameResult(State state) {
        String message = "";
        switch (state) {
            case XWIN:
                message = "Вы выиграли!";
                break;
            case OWIN:
                message = "Компьютер выиграл!";
                break;
            case DRAW:
                message = "Ничья!";
                break;
            default:
                return;
        }
        
        // Показываем результат и предлагаем новую игру
        int option = JOptionPane.showConfirmDialog(this, 
            message + "\nХотите сыграть еще раз?", 
            "Игра окончена", 
            JOptionPane.YES_NO_OPTION);
            
        if (option == JOptionPane.YES_OPTION) {
            resetGame();
        }
    }
    
    private void resetGame() {
        game.resetGame();
        gameOver = false;
        computerTurn = false;
        
        for (TicTacToeCell cell : cells) {
            cell.setText(" ");
            cell.setMarker(" ");
            cell.setEnabled(true);
        }
    }
}

public class Program {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Крестики-Нолики");
            frame.add(new TicTacToePanel(new GridLayout(3, 3)));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setBounds(5, 5, 500, 500);
            frame.setVisible(true);
        });
    }
} 