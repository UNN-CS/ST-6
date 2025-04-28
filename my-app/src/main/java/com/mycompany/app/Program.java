package com.mycompany.app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

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

    // возвращаем состояние игры
    State checkState(char[] board) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i*3] != ' ' && board[i*3] == board[i*3+1] && board[i*3] == board[i*3+2]) {
                return board[i*3] == 'X' ? State.XWIN : State.OWIN;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (board[i] != ' ' && board[i] == board[i+3] && board[i] == board[i+6]) {
                return board[i] == 'X' ? State.XWIN : State.OWIN;
            }
        }

        // Check diagonals
        if (board[0] != ' ' && board[0] == board[4] && board[0] == board[8]) {
            return board[0] == 'X' ? State.XWIN : State.OWIN;
        }
        if (board[2] != ' ' && board[2] == board[4] && board[2] == board[6]) {
            return board[2] == 'X' ? State.XWIN : State.OWIN;
        }

        // Check for draw
        for (char c : board) {
            if (c == ' ') return State.PLAYING;
        }
        return State.DRAW;
    }
    // сгенерировать возможные ходы
    void generateMoves(char[] board, ArrayList<Integer> move_list) {
        for (int i = 0; i < 9; i++)
            if (board[i] == ' ')
                move_list.add(i);
    }

    // оценка позиции
    int evaluatePosition(char[] board, Player player) {
        State state = checkState(board);
        if (state == State.XWIN) {
            return player.symbol == 'X' ? INF : -INF;
        } else if (state == State.OWIN) {
            return player.symbol == 'O' ? INF : -INF;
        } else if (state == State.DRAW) {
            return 0;
        }

        // Heuristic evaluation
        int score = 0;

        // Check all possible lines (rows, columns, diagonals)
        int[][] lines = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
                {0, 4, 8}, {2, 4, 6}             // diagonals
        };

        for (int[] line : lines) {
            int xCount = 0, oCount = 0;
            for (int pos : line) {
                if (board[pos] == 'X') xCount++;
                if (board[pos] == 'O') oCount++;
            }

            // Score based on potential winning lines
            if (player.symbol == 'X') {
                if (xCount == 2 && oCount == 0) score += 10;
                if (xCount == 1 && oCount == 0) score += 1;
                if (oCount == 2 && xCount == 0) score -= 8; // block opponent
            } else {
                if (oCount == 2 && xCount == 0) score += 10;
                if (oCount == 1 && xCount == 0) score += 1;
                if (xCount == 2 && oCount == 0) score -= 8; // block opponent
            }
        }

        // Prefer center and corners
        if (board[4] == player.symbol) score += 3;  // center
        int[] corners = {0, 2, 6, 8};
        for (int corner : corners) {
            if (board[corner] == player.symbol) score += 2;
        }

        return score;
    }

    int MiniMax(char[] board, Player player) {
        // Check for terminal states first
        State state = checkState(board);
        if (state != State.PLAYING) {
            return evaluatePosition(board, player);
        }

        ArrayList<Integer> moves = new ArrayList<>();
        generateMoves(board, moves);

        int bestMove = -1;
        int bestValue = (player == player1) ? -INF : INF;

        for (int move : moves) {
            board[move] = player.symbol;

            int currentValue;
            if (player == player1) {
                currentValue = MiniMax(board, player2);
                if (currentValue > bestValue) {
                    bestValue = currentValue;
                    bestMove = move;
                }
            } else {
                currentValue = MiniMax(board, player1);
                if (currentValue < bestValue) {
                    bestValue = currentValue;
                    bestMove = move;
                }
            }

            board[move] = ' '; // Undo move
        }

        return bestMove;
    }

    int MinMove(char[] board, Player player)  {

        int pos_value = evaluatePosition(board, player);
        if (pos_value != -1)
            return pos_value;
        q++;
        int best_val = +Game.INF;
        ArrayList<Integer> move_list=new ArrayList<>();

        generateMoves(board, move_list);

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
        generateMoves(board, move_list);
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
        if (marker == ' ') {
            marker = m.charAt(0);
            setText(m);
            setEnabled(false);
        }
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
        for(int j=0; j < board.length; j++) {
            System.out.print(board[j] + (j < board.length-1 ? "-" : ""));
        }
        System.out.println();
    }

    public static void print(int[] board) {
        System.out.println();
        for(int j=0; j < board.length; j++) {
            System.out.print(board[j] + (j < board.length-1 ? "-" : ""));
        }
        System.out.println();
    }

    public static void print(ArrayList<Integer> moves) {
        System.out.println();
        for(int j=0; j < moves.size(); j++) {
            System.out.print(moves.get(j) + (j < moves.size()-1 ? "-" : ""));
        }
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

    public Game getGame() {
        return this.game;
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
        // Update board state
        for (int i = 0; i < cells.length; i++) {
            game.board[i] = cells[i].getMarker();
        }

        // Human move
        if (ae.getSource() instanceof TicTacToeCell) {
            TicTacToeCell cell = (TicTacToeCell) ae.getSource();
            if (cell.getMarker() == ' ') {
                cell.setMarker(String.valueOf(game.cplayer.symbol));
                game.board[cell.getNum()] = game.cplayer.symbol;
            }
        }

        game.state = game.checkState(game.board);
        if (game.state != State.PLAYING) {
            showGameResult();
            return;
        }

        // AI move
        if (game.cplayer == game.player1) {
            game.player2.move = game.MiniMax(game.board, game.player2);
            if (game.player2.move >= 0 && game.player2.move < cells.length) {
                cells[game.player2.move].setMarker(String.valueOf(game.player2.symbol));
                game.board[game.player2.move] = game.player2.symbol;
            }
            game.cplayer = game.player2;
        } else {
            game.cplayer = game.player1;
        }

        game.state = game.checkState(game.board);
        if (game.state != State.PLAYING) {
            showGameResult();
        }
    }

    private void showGameResult() {
        if (game.state == State.XWIN) {
            JOptionPane.showMessageDialog(null, "X wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        } else if (game.state == State.OWIN) {
            JOptionPane.showMessageDialog(null, "O wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Draw!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
//        System.exit(0);
    }
}
