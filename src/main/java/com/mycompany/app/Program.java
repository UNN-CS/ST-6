package com.mycompany.app;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

enum State {
    PLAYING,
    OWIN,
    XWIN,
    DRAW
}

class Player {
    public char symbol;
    public int move;
    public boolean selected;
    public boolean win;
}

class Game {
    public static final int INF = 100;
    private static final int BOARD_SIZE = 9;
    private static final int[][] WINNING_LINES = {
        {0, 1, 2},
        {3, 4, 5},
        {6, 7, 8},
        {0, 3, 6},
        {1, 4, 7},
        {2, 5, 8},
        {0, 4, 8},
        {2, 4, 6}
    };

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
        cplayer = player1;
        state = State.PLAYING;
        board = new char[BOARD_SIZE];
        resetBoard(board);
    }

    public State checkState(char[] board) {
        if (hasLine(board, 'X')) {
            return State.XWIN;
        }
        if (hasLine(board, 'O')) {
            return State.OWIN;
        }
        for (char cell : board) {
            if (cell == ' ') {
                return State.PLAYING;
            }
        }
        return State.DRAW;
    }

    void generateMoves(char[] board, ArrayList<Integer> moveList) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i] == ' ') {
                moveList.add(i);
            }
        }
    }

    int evaluatePosition(char[] board, Player player) {
        State currentState = checkState(board);
        if (currentState == State.PLAYING) {
            return -1;
        }
        if (currentState == State.DRAW) {
            return 0;
        }
        boolean winnerIsPlayer = (currentState == State.XWIN && player.symbol == 'X')
                || (currentState == State.OWIN && player.symbol == 'O');
        return winnerIsPlayer ? INF : -INF;
    }

    int MiniMax(char[] board, Player player) {
        ArrayList<Integer> moveList = new ArrayList<>();
        generateMoves(board, moveList);
        if (moveList.isEmpty()) {
            return 0;
        }

        int bestValue = -INF;
        int bestMove = moveList.get(0);

        for (int move : moveList) {
            board[move] = player.symbol;
            int value = MinMove(board, player);
            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
            board[move] = ' ';
        }

        return bestMove + 1;
    }

    int MinMove(char[] board, Player player) {
        return minimax(board, player.symbol, false, 0, -INF, INF);
    }

    int MaxMove(char[] board, Player player) {
        return minimax(board, player.symbol, true, 0, -INF, INF);
    }

    private int minimax(char[] board, char rootSymbol, boolean maximizing, int depth, int alpha, int beta) {
        q++;
        State currentState = checkState(board);
        if (currentState != State.PLAYING) {
            return scoreState(currentState, rootSymbol, depth);
        }

        ArrayList<Integer> moveList = new ArrayList<>();
        generateMoves(board, moveList);
        char currentSymbol = maximizing ? rootSymbol : opponent(rootSymbol);

        if (maximizing) {
            int bestValue = -INF;
            for (int move : moveList) {
                board[move] = currentSymbol;
                int value = minimax(board, rootSymbol, false, depth + 1, alpha, beta);
                board[move] = ' ';
                bestValue = Math.max(bestValue, value);
                alpha = Math.max(alpha, bestValue);
                if (beta <= alpha) {
                    break;
                }
            }
            return bestValue;
        }

        int bestValue = INF;
        for (int move : moveList) {
            board[move] = currentSymbol;
            int value = minimax(board, rootSymbol, true, depth + 1, alpha, beta);
            board[move] = ' ';
            bestValue = Math.min(bestValue, value);
            beta = Math.min(beta, bestValue);
            if (beta <= alpha) {
                break;
            }
        }
        return bestValue;
    }

    private int scoreState(State currentState, char rootSymbol, int depth) {
        if (currentState == State.DRAW) {
            return 0;
        }
        boolean rootWins = (currentState == State.XWIN && rootSymbol == 'X')
                || (currentState == State.OWIN && rootSymbol == 'O');
        return rootWins ? INF - depth : depth - INF;
    }

    private char opponent(char symbol) {
        return symbol == 'X' ? 'O' : 'X';
    }

    private boolean hasLine(char[] board, char target) {
        for (int[] line : WINNING_LINES) {
            if (board[line[0]] == target && board[line[1]] == target && board[line[2]] == target) {
                return true;
            }
        }
        return false;
    }

    private void resetBoard(char[] board) {
        for (int i = 0; i < board.length; i++) {
            board[i] = ' ';
        }
    }
}

public class Program {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Demo");
        frame.add(new TicTacToePanel(new GridLayout(3, 3)));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(5, 5, 500, 500);
        frame.setVisible(true);
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

    public void setMarker(String markerText) {
        marker = markerText.charAt(0);
        setText(markerText);
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
        for (int i = 0; i < 9; i++) {
            System.out.print(board[i] + "-");
        }
        System.out.println();
    }

    public static void print(int[] board) {
        System.out.println();
        for (int i = 0; i < 9; i++) {
            System.out.print(board[i] + "-");
        }
        System.out.println();
    }

    public static void print(List<Integer> moves) {
        System.out.println();
        for (int i = 0; i < moves.size(); i++) {
            System.out.print(moves.get(i) + "-");
        }
        System.out.println();
    }
}

class TicTacToePanel extends JPanel implements ActionListener {
    private final Game game;
    private final TicTacToeCell[] cells = new TicTacToeCell[9];

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

    Game game() {
        return game;
    }

    State gameState() {
        return game.state;
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

    protected void finishGame(State result) {
        if (result == State.XWIN) {
            JOptionPane.showMessageDialog(null, "Выиграли крестики", "Результат", JOptionPane.WARNING_MESSAGE);
        } else if (result == State.OWIN) {
            JOptionPane.showMessageDialog(null, "Выиграли нолики", "Результат", JOptionPane.WARNING_MESSAGE);
        } else if (result == State.DRAW) {
            JOptionPane.showMessageDialog(null, "Ничья", "Результат", JOptionPane.WARNING_MESSAGE);
        }
        System.exit(0);
    }
}
