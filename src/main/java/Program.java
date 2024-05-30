// Copyright 2024 by Nedelin Dmitry

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

enum Condition { PLAYING, OWIN, XWIN, DRAW };


class Player {
  public char symbol;
  public int move;
  public boolean selected;
  public boolean win;
}

class Game {
    public Condition condition;
    public Player PC1, PC2;
    public Player rn_player;
    public int last_rnmove;
    public char symbol;
    public static final int INF = 100;
    public int q;
    public char[] field;


    public Game() {
      PC1 =new Player();
      PC2 =new Player();
      PC1.symbol='X';
      PC2.symbol='O';
      condition = Condition.PLAYING;
      field =new char[9];
      for(int i=0;i<9;i++)
        field[i]=' ';
    }

    // возвращаем состояние игры
    public Condition checkState(char[] board)
    {
      //char symbol=game.symbol;//cplayer.symbol;
      Condition condition = Condition.PLAYING;
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
            condition = Condition.XWIN;
        else if (symbol == 'O')  
            condition = Condition.OWIN;
      }
      else {
        condition = Condition.DRAW;
        for (int i = 0; i < 9; i++) 
        {
            if (board[i] == ' ') {
                condition = Condition.PLAYING;
                break;
            }
        }
    }
    return condition;
  }

   void MovesGenerator(char[] board, ArrayList<Integer> move_list) {
    for (int i = 0; i < 9; i++) 
        if (board[i] == ' ') 
            move_list.add(i);
   }

   int evaluatePosition(char[] board, Player player)
   {
    Condition condition =checkState(board);
    if ((condition == Condition.XWIN || condition == Condition.OWIN || condition == Condition.DRAW))
    {
        if ((condition == Condition.XWIN && player.symbol == 'X') || (condition == Condition.OWIN && player.symbol == 'O'))
            return +Game.INF;
        else if ((condition == Condition.XWIN && player.symbol == 'O') || (condition == Condition.OWIN && player.symbol == 'X'))
            return -Game.INF;
        else if (condition == Condition.DRAW)
            return 0;
    }
    return -1;
   }

   int MinMaxMove(char[] board, Player player)
   {
    int best_val = -Game.INF, index = 0;
    ArrayList<Integer> move_list=new ArrayList<>();
    int[] best_moves = new int[9];
 
    MovesGenerator(board, move_list);

    while (move_list.size()!=0) { 
        board[move_list.get(0)] = player.symbol; 
        symbol = player.symbol;

        int val = MinMove(board, player); 

        if (val > best_val) { 
            best_val = val;
            index = 0;
            best_moves[index] = move_list.get(0)+1; 
        }
        else if (val == best_val)
            best_moves[++index] = move_list.get(0)+1; 
 
        System.out.printf("\nMinMaxMove: %3d(%1d) ", 1 + move_list.get(0), val);
        board[move_list.get(0)] = ' '; 
        move_list.remove(0);
    }
    if (index > 0)  {
      Random r = new Random();
      index = r.nextInt(index);
    }
   
    System.out.printf("\nMinMaxMove best: %3d(%1d) ", best_moves[index], best_val);
    System.out.printf("Steps count: %d", q);
    q = 0;
    return best_moves[index];
  }
  
  int MinMove(char[] board, Player player)  {

    int positionValue = evaluatePosition(board, player);
    if (positionValue != -1)
      return positionValue;
    q++;
    int best_val = +Game.INF;
    ArrayList<Integer> move_list=new ArrayList<>();
    
    MovesGenerator(board, move_list);

    while (move_list.size()!=0) { 
        symbol= (player.symbol == 'X') ? 'O' : 'X'; 
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

  int MaxMove(char[] board, Player player) {
    int pos_value = evaluatePosition(board, player);
    if (pos_value != -1) 
      return pos_value;
    q++;
    int best_val = -Game.INF;
    ArrayList<Integer> move_list=new ArrayList<>();
    MovesGenerator(board, move_list);
    while (move_list.size()!=0) {
        symbol=(player.symbol == 'X') ? 'X' : 'O'; 
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
}

public class Program {

    public static FileWriter fileWriter;
    public static PrintWriter printWriter;
    public static void main(String[] args) throws IOException {
       JFrame frame = new JFrame("Demo");
       frame.add(new TicTacToePanel(new GridLayout(3,3)));
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setBounds(5, 5, 500, 500);
       frame.setVisible(true);
    }
}

class TicTacToeCell extends JButton {
    private boolean isFill;
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
       game.rn_player =game.PC1;
   }

   public void actionPerformed(ActionEvent ae) {
      game.PC1.move = -1;
      game.PC2.move = -1;
      //System.out.println(game.cplayer.symbol);
      //System.out.println(((TicTacToeCell)(ae.getSource())).getNum());


      int i=0;
      for(TicTacToeCell jb: cells) {
         if(ae.getSource()==jb) {
            jb.setMarker(Character.toString(game.rn_player.symbol));
         }
         game.field[i++]=jb.getMarker();
      }
      if(game.rn_player ==game.PC1) {

         game.PC2.move = game.MinMaxMove(game.field, game.PC2);
         game.last_rnmove = game.PC2.move;
         game.symbol = game.PC2.symbol;
         game.rn_player = game.PC2;
         if(game.PC2.move>0)
            cells[game.PC2.move-1].doClick();
       }
       else
       {
         game.last_rnmove = game.PC1.move;
         game.symbol = game.PC1.symbol;
         game.rn_player = game.PC1;
       }

      game.condition =game.checkState(game.field);


      if(game.condition == Condition.XWIN) {
        JOptionPane.showMessageDialog(null,"Выиграли крестики","Результат", JOptionPane.WARNING_MESSAGE);
        System.exit(0);

      }
      else if(game.condition == Condition.OWIN) {
        JOptionPane.showMessageDialog(null,"Выиграли нолики","Результат", JOptionPane.WARNING_MESSAGE);
        System.exit(0);
      }
      else if(game.condition == Condition.DRAW) {
        JOptionPane.showMessageDialog(null,"Ничья","Результат", JOptionPane.WARNING_MESSAGE);
        System.exit(0);
      }
   }
}


