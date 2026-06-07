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

    private boolean checkWin(char[] field, char ch) {
        return (field[0] == ch && field[1] == ch && field[2] == ch) ||
                (field[3] == ch && field[4] == ch && field[5] == ch) ||
                (field[6] == ch && field[7] == ch && field[8] == ch) ||
                (field[0] == ch && field[3] == ch && field[6] == ch) ||
                (field[1] == ch && field[4] == ch && field[7] == ch) ||
                (field[2] == ch && field[5] == ch && field[8] == ch) ||
                (field[0] == ch && field[4] == ch && field[8] == ch) ||
                (field[2] == ch && field[4] == ch && field[6] == ch);
    }

    public State checkState(char[] field) {
        if (checkWin(field, 'X')) return State.XWIN;
        if (checkWin(field, 'O')) return State.OWIN;
        for (char c : field) {
            if (c == ' ') return State.PLAYING;
        }
        return State.DRAW;
    }

    public void generateMoves(char[] field, ArrayList<Integer> moves) {
        moves.clear();
        for (int i = 0; i < 9; i++) {
            if (field[i] == ' ') moves.add(i);
        }
    }

    public int evaluatePosition(char[] field, Player p) {
        if (checkWin(field, 'X') && p.symbol == 'X') return INF;
        if (checkWin(field, 'O') && p.symbol == 'O') return INF;
        if (checkWin(field, 'X') && p.symbol == 'O') return -INF;
        if (checkWin(field, 'O') && p.symbol == 'X') return -INF;

        boolean full = true;
        for (char c : field) {
            if (c == ' ') {
                full = false;
                break;
            }
        }
        if (full) return 0;
        return -1;
    }

    public int MiniMax(char[] field, Player p) {
        q = 0;
        ArrayList<Integer> available = new ArrayList<>();
        generateMoves(field, available);
        if (available.isEmpty()) return 0;

        int bestValue = -INF - 1;
        int bestPosition = -1;

        for (int pos : available) {
            field[pos] = p.symbol;
            int value = minMove(field, p);
            field[pos] = ' ';

            if (value > bestValue) {
                bestValue = value;
                bestPosition = pos;
            }
        }
        return bestPosition + 1;
    }

    private int minMove(char[] field, Player p) {
        int terminal = evaluatePosition(field, p);
        if (terminal != -1) return terminal;
        q++;

        int lowest = INF;
        ArrayList<Integer> available = new ArrayList<>();
        generateMoves(field, available);

        for (int pos : available) {
            char next = (p.symbol == 'X') ? 'O' : 'X';
            field[pos] = next;
            int value = maxMove(field, p);
            if (value < lowest) lowest = value;
            field[pos] = ' ';
        }
        return lowest;
    }

    private int maxMove(char[] field, Player p) {
        int terminal = evaluatePosition(field, p);
        if (terminal != -1) return terminal;
        q++;

        int highest = -INF;
        ArrayList<Integer> available = new ArrayList<>();
        generateMoves(field, available);

        for (int pos : available) {
            char next = (p.symbol == 'X') ? 'X' : 'O';
            field[pos] = next;
            int value = minMove(field, p);
            if (value > highest) highest = value;
            field[pos] = ' ';
        }
        return highest;
    }
}

class Utility {
    public static void print(char[] data) {
        System.out.println();
        for (char c : data) System.out.print(c + "-");
        System.out.println();
    }
    public static void print(int[] data) {
        System.out.println();
        for (int v : data) System.out.print(v + "-");
        System.out.println();
    }
    public static void print(ArrayList<Integer> data) {
        System.out.println();
        for (int v : data) System.out.print(v + "-");
        System.out.println();
    }
}

class TicTacToeCell extends JButton {
    private int id;
    private int rowPos;
    private int colPos;
    private char mark;

    public TicTacToeCell(int num, int x, int y) {
        this.id = num;
        this.rowPos = y;
        this.colPos = x;
        mark = ' ';
        setText(" ");
        setFont(new Font("Arial", Font.PLAIN, 40));
    }

    public void setMarker(String m) {
        mark = m.charAt(0);
        setText(m);
        setEnabled(false);
    }

    public char getMarker() { return mark; }
    public int getRow() { return rowPos; }
    public int getCol() { return colPos; }
    public int getNum() { return id; }
}

class TicTacToePanel extends JPanel implements ActionListener {
    private Game gameLogic;
    private TicTacToeCell[] cells = new TicTacToeCell[9];

    public TicTacToePanel(GridLayout layout) {
        super(layout);
        buildCells();
        gameLogic = new Game();
        gameLogic.cplayer = gameLogic.player1;
    }

    private void buildCells() {
        int idx = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                cells[idx] = new TicTacToeCell(idx, c, r);
                cells[idx].addActionListener(this);
                add(cells[idx]);
                idx++;
            }
        }
    }

    private void syncField() {
        for (int i = 0; i < cells.length; i++) {
            gameLogic.board[i] = cells[i].getMarker();
        }
    }

    private void showResult() {
        String msg = "";
        if (gameLogic.state == State.XWIN) msg = "Крестики победили!";
        else if (gameLogic.state == State.OWIN) msg = "Нолики победили!";
        else if (gameLogic.state == State.DRAW) msg = "Ничья!";
        JOptionPane.showMessageDialog(this, msg, "Итог", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private void performComputerTurn() {
        int computerChoice = gameLogic.MiniMax(gameLogic.board, gameLogic.player2);
        if (computerChoice >= 1 && computerChoice <= 9 && gameLogic.board[computerChoice - 1] == ' ') {
            TicTacToeCell target = cells[computerChoice - 1];
            target.setMarker(Character.toString(gameLogic.player2.symbol));
            syncField();
            gameLogic.state = gameLogic.checkState(gameLogic.board);
            if (gameLogic.state != State.PLAYING) {
                showResult();
            }
            gameLogic.cplayer = gameLogic.player1;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameLogic.state != State.PLAYING) return;

        TicTacToeCell clickedCell = null;
        for (TicTacToeCell cell : cells) {
            if (e.getSource() == cell) {
                clickedCell = cell;
                break;
            }
        }
        if (clickedCell == null) return;

        if (gameLogic.cplayer == gameLogic.player1 && clickedCell.getMarker() == ' ') {
            clickedCell.setMarker(Character.toString(gameLogic.player1.symbol));
            syncField();
            gameLogic.state = gameLogic.checkState(gameLogic.board);
            if (gameLogic.state != State.PLAYING) {
                showResult();
                return;
            }
            gameLogic.cplayer = gameLogic.player2;
            performComputerTurn();
        }
    }
}

public class Program {
    public static void main(String[] args) {
        JFrame window = new JFrame("Крестики-нолики");
        window.add(new TicTacToePanel(new GridLayout(3, 3)));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(50, 50, 500, 500);
        window.setVisible(true);
    }
}