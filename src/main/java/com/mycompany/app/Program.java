package com.mycompany.app;

// Реализация игры "Крестики-нолики" (3x3)
// Минимаксный алгоритм

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

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
      player1=new Player();
      player2=new Player();
      player1.symbol='X';
      player2.symbol='O';
      state=State.PLAYING; 
      board=new char[9];   // текущая доска в игре  
      for(int i=0;i<9;i++)
        board[i]=' ';
    }

    public State checkState(char[] board) {
      State result = State.PLAYING;
      if ((board[0] == symbol && board[1] == symbol && board[2] == symbol) ||
          (board[3] == symbol && board[4] == symbol && board[5] == symbol) ||
          (board[6] == symbol && board[7] == symbol && board[8] == symbol) ||
          (board[0] == symbol && board[3] == symbol && board[6] == symbol) ||
          (board[1] == symbol && board[4] == symbol && board[7] == symbol) ||
          (board[2] == symbol && board[5] == symbol && board[8] == symbol) ||
          (board[0] == symbol && board[4] == symbol && board[8] == symbol) ||
          (board[2] == symbol && board[4] == symbol && board[6] == symbol)) {
        if (symbol == 'X')
            result = State.XWIN;
        else if (symbol == 'O')
            result = State.OWIN;
      } else {
        result = State.DRAW;
        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                result = State.PLAYING;
                break;
            }
        }
      }
      return result;
    }
     // сгенерировать возможные ходы
   void generateMoves(char[] board, ArrayList<Integer> move_list) {
    for (int i = 0; i < 9; i++) 
        if (board[i] == ' ') 
            move_list.add(i);
   }

   int evaluatePosition(char[] board, Player player) {
    State result = checkState(board);
    if (result == State.XWIN || result == State.OWIN || result == State.DRAW) {
        if ((result == State.XWIN && player.symbol == 'X') || (result == State.OWIN && player.symbol == 'O'))
            return +Game.INF;
        else if ((result == State.XWIN && player.symbol == 'O') || (result == State.OWIN && player.symbol == 'X'))
            return -Game.INF;
        else if (result == State.DRAW)
            return 0;
    }
    return -1;
   }

   int MiniMax(char[] board, Player player) {
    int bestScore = -Game.INF, bestIdx = 0;
    ArrayList<Integer> moves = new ArrayList<>();
    int[] bestMoves = new int[9];

    generateMoves(board, moves);

    while (moves.size() != 0) {
        board[moves.get(0)] = player.symbol;
        symbol = player.symbol;

        int val = MinMove(board, player);

        if (val > bestScore) {
            bestScore = val;
            bestIdx = 0;
            bestMoves[bestIdx] = moves.get(0) + 1;
        } else if (val == bestScore) {
            bestMoves[++bestIdx] = moves.get(0) + 1;
        }

        System.out.printf("\nminimax: %3d(%1d) ", 1 + moves.get(0), val);
        board[moves.get(0)] = ' ';
        moves.remove(0);
    }
    if (bestIdx > 0) {
        Random r = new Random();
        bestIdx = r.nextInt(bestIdx);
    }

    System.out.printf("\nminimax best: %3d(%1d) ", bestMoves[bestIdx], bestScore);
    System.out.printf("Steps counted: %d", q);
    q = 0;
    return bestMoves[bestIdx];
  }
  
  int MinMove(char[] board, Player player) {
    int posScore = evaluatePosition(board, player);
    if (posScore != -1)
        return posScore;
    q++;
    int minScore = +Game.INF;
    ArrayList<Integer> moves = new ArrayList<>();

    generateMoves(board, moves);

    while (moves.size() != 0) {
        symbol = (player.symbol == 'X') ? 'O' : 'X';
        board[moves.get(0)] = symbol;

        int val = MaxMove(board, player);

        if (val < minScore) {
            minScore = val;
        }
        board[moves.get(0)] = ' ';
        moves.remove(0);
    }
    return minScore;
  }

  int MaxMove(char[] board, Player player) {
    int posScore = evaluatePosition(board, player);
    if (posScore != -1)
        return posScore;
    q++;
    int maxScore = -Game.INF;
    ArrayList<Integer> moves = new ArrayList<>();
    generateMoves(board, moves);
    while (moves.size() != 0) {
        symbol = (player.symbol == 'X') ? 'X' : 'O';
        board[moves.get(0)] = symbol;
        int val = MinMove(board, player);
        if (val > maxScore) {
            maxScore = val;
        }
        board[moves.get(0)] = ' ';
        moves.remove(0);
    }
    return maxScore;
  }
}

public class Program {

    public static void main(String[] args) {
       JFrame frame = new JFrame("Demo");
       frame.add(new TicTacToePanel(new GridLayout(3,3)));
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setBounds(5, 5, 500, 500);
       frame.setVisible(true);
    }
}

class TicTacToeCell extends JButton {
    private int num;
    private int row;
    private int col;
    private char marker;

    public TicTacToeCell(int num,int x,int y) {
        this.num=num;
        row=y;
        col=x;
        marker=' ';
        setText(Character.toString(marker));
        setFont(new Font("Arial", Font.PLAIN, 40));
    }
    public void setMarker(String m) {
        marker=m.charAt(0);
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

class Utility {

  public static void print(char[] board) {
    System.out.println();
        for(int j=0;j<9;j++)
          System.out.print(board[j]+"-");
        System.out.println();
  }
  public static void print(int[] board) {
    System.out.println();
        for(int j=0;j<9;j++)
          System.out.print(board[j]+"-");
        System.out.println();
  }  
  public static void print(ArrayList<Integer> moves) {
    System.out.println();
        for(int j=0;j<moves.size();j++)
          System.out.print(moves.get(j)+"-");
        System.out.println();
  }  
}

class TicTacToePanel extends JPanel implements ActionListener {

   private Game game;

   private void createCell(int num,int x,int y) {
       cells[num]=new TicTacToeCell(num,x,y);
       cells[num].addActionListener(this);
       add(cells[num]);

   }

   private TicTacToeCell[] cells = new TicTacToeCell[9];
   TicTacToePanel(GridLayout layout) {
       super(layout);
       createCell(0,0,0);
       createCell(1,1,0);
       createCell(2,2,0);
       createCell(3,0,1);
       createCell(4,1,1);
       createCell(5,2,1);
       createCell(6,0,2);
       createCell(7,1,2); 
       createCell(8,2,2);
       game=new Game();
       game.cplayer=game.player1;
   }

   public void actionPerformed(ActionEvent ae) {
      game.player1.move = -1;
      game.player2.move = -1;
      //System.out.println(game.cplayer.symbol);
      //System.out.println(((TicTacToeCell)(ae.getSource())).getNum());


      int i=0;
      for(TicTacToeCell jb: cells) {
         if(ae.getSource()==jb) {
            jb.setMarker(Character.toString(game.cplayer.symbol));
         }
         game.board[i++]=jb.getMarker();
      }
      if(game.cplayer==game.player1) {

         game.player2.move = game.MiniMax(game.board, game.player2);
         game.nmove = game.player2.move;
         game.symbol = game.player2.symbol;
         game.cplayer = game.player2;
         if(game.player2.move>0)
            cells[game.player2.move-1].doClick();
       }
       else
       {
         game.nmove = game.player1.move;
         game.symbol = game.player1.symbol;
         game.cplayer = game.player1;
       }

      game.state=game.checkState(game.board);


      if(game.state==State.XWIN) {
        JOptionPane.showMessageDialog(null,"Выиграли крестики","Результат", JOptionPane.WARNING_MESSAGE);
        System.exit(0);

      }
      else if(game.state==State.OWIN) {
        JOptionPane.showMessageDialog(null,"Выиграли нолики","Результат", JOptionPane.WARNING_MESSAGE);
        System.exit(0);
      }
      else if(game.state==State.DRAW) {
        JOptionPane.showMessageDialog(null,"Ничья","Результат", JOptionPane.WARNING_MESSAGE);
        System.exit(0);
      } 




   }
}
