package com.mycompany.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

enum State {
    PLAYING, OWIN, XWIN, DRAW
}

class Player {
    public char symbol;
    public int move;
    public boolean selected;
    public boolean win;
}

class Game {
    public State state;
    public Player player1, player2;
    public Player cplayer;
    public int nmove;
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
        board = new char[9];
        for (int i = 0; i < 9; i++) board[i] = ' ';
        q = 0;
    }

    private boolean isWin(char[] b, char sym) {
        int[][] lines = {
                {0,1,2}, {3,4,5}, {6,7,8},
                {0,3,6}, {1,4,7}, {2,5,8},
                {0,4,8}, {2,4,6}
        };
        for (int[] line : lines) {
            if (b[line[0]] == sym && b[line[1]] == sym && b[line[2]] == sym)
                return true;
        }
        return false;
    }

    public State checkState(char[] board) {
        if (isWin(board, 'X')) return State.XWIN;
        if (isWin(board, 'O')) return State.OWIN;
        for (char c : board) {
            if (c == ' ') return State.PLAYING;
        }
        return State.DRAW;
    }

    public void generateMoves(char[] board, ArrayList<Integer> moveList) {
        moveList.clear();
        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') moveList.add(i);
        }
    }

    public int evaluatePosition(char[] board, Player p) {
        boolean xWins = isWin(board, 'X');
        boolean oWins = isWin(board, 'O');

        if (xWins && p.symbol == 'X') return +INF;
        if (oWins && p.symbol == 'O') return +INF;
        if (xWins && p.symbol == 'O') return -INF;
        if (oWins && p.symbol == 'X') return -INF;

        boolean full = true;
        for (char c : board) if (c == ' ') { full = false; break; }
        if (full) return 0;
        return -1;
    }

    public int MiniMax(char[] board, Player player) {
        q = 0;
        ArrayList<Integer> moves = new ArrayList<>();
        generateMoves(board, moves);
        if (moves.isEmpty()) return 0;

        int bestVal = Integer.MIN_VALUE;
        int[] candidates = new int[9];
        int candCount = 0;

        for (int pos : moves) {
            board[pos] = player.symbol;
            int val = minMove(board, player);
            board[pos] = ' ';

            if (val > bestVal) {
                bestVal = val;
                candCount = 0;
                candidates[candCount++] = pos;
            } else if (val == bestVal) {
                candidates[candCount++] = pos;
            }
        }
        Random rnd = new Random();
        int chosen = (candCount > 1) ? rnd.nextInt(candCount) : 0;
        return candidates[chosen] + 1;
    }

    private int minMove(char[] board, Player p) {
        int terminal = evaluatePosition(board, p);
        if (terminal != -1) return terminal;
        q++;

        int best = +INF;
        ArrayList<Integer> moves = new ArrayList<>();
        generateMoves(board, moves);

        for (int pos : moves) {
            char nextSym = (p.symbol == 'X') ? 'O' : 'X';
            board[pos] = nextSym;
            int val = maxMove(board, p);
            if (val < best) best = val;
            board[pos] = ' ';
        }
        return best;
    }

    private int maxMove(char[] board, Player p) {
        int terminal = evaluatePosition(board, p);
        if (terminal != -1) return terminal;
        q++;

        int best = -INF;
        ArrayList<Integer> moves = new ArrayList<>();
        generateMoves(board, moves);

        for (int pos : moves) {
            char nextSym = (p.symbol == 'X') ? 'X' : 'O';
            board[pos] = nextSym;
            int val = minMove(board, p);
            if (val > best) best = val;
            board[pos] = ' ';
        }
        return best;
    }
}

class Utility {
    public static void print(char[] board) {
        System.out.println();
        for (char c : board) System.out.print(c + "-");
        System.out.println();
    }
    public static void print(int[] board) {
        System.out.println();
        for (int v : board) System.out.print(v + "-");
        System.out.println();
    }
    public static void print(ArrayList<Integer> moves) {
        System.out.println();
        for (int m : moves) System.out.print(m + "-");
        System.out.println();
    }
}

class TicTacToeCell extends JButton {
    private boolean isFill;
    private int num;
    private int row;
    private int col;
    private char marker;

    public TicTacToeCell(int num, int x, int y) {
        this.num = num;
        this.row = y;
        this.col = x;
        marker = ' ';
        setText(" ");
        setFont(new Font("Arial", Font.PLAIN, 40));
    }

    public void setMarker(String m) {
        marker = m.charAt(0);
        setText(m);
        setEnabled(false);
    }

    public char getMarker() { return marker; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public int getNum() { return num; }
}

class TicTacToePanel extends JPanel implements ActionListener {
    private Game game;
    private TicTacToeCell[] cells = new TicTacToeCell[9];

    public TicTacToePanel(GridLayout layout) {
        super(layout);
        createCells();
        game = new Game();
        game.cplayer = game.player1;
    }

    private void createCells() {
        int idx = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                cells[idx] = new TicTacToeCell(idx, col, row);
                cells[idx].addActionListener(this);
                add(cells[idx]);
                idx++;
            }
        }
    }

    private void syncBoardFromUI() {
        for (int i = 0; i < cells.length; i++) {
            game.board[i] = cells[i].getMarker();
        }
    }

    private void showResultAndExit() {
        String msg = "";
        if (game.state == State.XWIN) msg = "Выиграли крестики!";
        else if (game.state == State.OWIN) msg = "Выиграли нолики!";
        else if (game.state == State.DRAW) msg = "Ничья!";
        JOptionPane.showMessageDialog(this, msg, "Результат", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private void applyComputerMove() {
        int compMove = game.MiniMax(game.board, game.player2);
        if (compMove >= 1 && compMove <= 9 && game.board[compMove-1] == ' ') {
            TicTacToeCell cell = cells[compMove-1];
            cell.setMarker(Character.toString(game.player2.symbol));
            syncBoardFromUI();
            game.state = game.checkState(game.board);
            if (game.state != State.PLAYING) {
                showResultAndExit();
            }
            game.cplayer = game.player1;
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (game.state != State.PLAYING) return;

        TicTacToeCell clicked = null;
        for (TicTacToeCell cell : cells) {
            if (ae.getSource() == cell) {
                clicked = cell;
                break;
            }
        }
        if (clicked == null) return;

        if (game.cplayer == game.player1 && clicked.getMarker() == ' ') {
            clicked.setMarker(Character.toString(game.player1.symbol));
            syncBoardFromUI();
            game.state = game.checkState(game.board);
            if (game.state != State.PLAYING) {
                showResultAndExit();
                return;
            }
            game.cplayer = game.player2;
            applyComputerMove();
        }
    }
}

public class Program {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Крестики-нолики (минимакс)");
        frame.add(new TicTacToePanel(new GridLayout(3, 3)));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(50, 50, 500, 500);
        frame.setVisible(true);
    }
}



