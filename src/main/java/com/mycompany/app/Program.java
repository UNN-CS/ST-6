package com.mycompany.app;

import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

enum State {
    PLAYING, OWIN, XWIN, DRAW
}

class Player {
    final char symbol;
    int move;
    boolean selected;
    boolean win;

    Player(char symbol) {
        this.symbol = symbol;
    }
}

class Game {
    static final int INF = 100;

    final Player player1;
    final Player player2;
    Player cplayer;
    State state;
    int nmove;
    char symbol;
    int q;
    final char[] board;

    Game() {
        player1 = new Player('X');
        player2 = new Player('O');
        cplayer = player1;
        state = State.PLAYING;
        board = new char[9];
        Arrays.fill(board, ' ');
    }

    State checkState(char[] board) {
        if (hasWinningLine(board, 'X')) {
            return State.XWIN;
        }
        if (hasWinningLine(board, 'O')) {
            return State.OWIN;
        }
        return hasEmptyCell(board) ? State.PLAYING : State.DRAW;
    }

    void generateMoves(char[] board, List<Integer> moveList) {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                moveList.add(i);
            }
        }
    }

    int evaluatePosition(char[] board, Player player) {
        State positionState = checkState(board);
        if (positionState == State.DRAW) {
            return 0;
        }
        if (positionState == State.XWIN) {
            return player.symbol == 'X' ? INF : -INF;
        }
        if (positionState == State.OWIN) {
            return player.symbol == 'O' ? INF : -INF;
        }
        return -1;
    }

    int MiniMax(char[] board, Player player) {
        List<Integer> moveList = new ArrayList<>();
        generateMoves(board, moveList);
        if (moveList.isEmpty()) {
            return 0;
        }

        int bestVal = -INF;
        List<Integer> bestMoves = new ArrayList<>();

        for (int move : moveList) {
            board[move] = player.symbol;
            symbol = player.symbol;
            int val = MinMove(board, player);
            board[move] = ' ';

            if (val > bestVal) {
                bestVal = val;
                bestMoves.clear();
                bestMoves.add(move);
            } else if (val == bestVal) {
                bestMoves.add(move);
            }
        }

        int chosenMove = bestMoves.get(new Random().nextInt(bestMoves.size()));
        return chosenMove + 1;
    }

    int MinMove(char[] board, Player player) {
        int positionValue = evaluatePosition(board, player);
        if (positionValue != -1) {
            return positionValue;
        }

        q++;
        int bestVal = INF;
        List<Integer> moveList = new ArrayList<>();
        generateMoves(board, moveList);
        char opponent = opponentOf(player.symbol);

        for (int move : moveList) {
            board[move] = opponent;
            int val = MaxMove(board, player);
            board[move] = ' ';
            if (val < bestVal) {
                bestVal = val;
            }
        }

        return bestVal;
    }

    int MaxMove(char[] board, Player player) {
        int positionValue = evaluatePosition(board, player);
        if (positionValue != -1) {
            return positionValue;
        }

        q++;
        int bestVal = -INF;
        List<Integer> moveList = new ArrayList<>();
        generateMoves(board, moveList);

        for (int move : moveList) {
            board[move] = player.symbol;
            int val = MinMove(board, player);
            board[move] = ' ';
            if (val > bestVal) {
                bestVal = val;
            }
        }

        return bestVal;
    }

    private boolean hasWinningLine(char[] board, char target) {
        return matches(board, target, 0, 1, 2)
                || matches(board, target, 3, 4, 5)
                || matches(board, target, 6, 7, 8)
                || matches(board, target, 0, 3, 6)
                || matches(board, target, 1, 4, 7)
                || matches(board, target, 2, 5, 8)
                || matches(board, target, 0, 4, 8)
                || matches(board, target, 2, 4, 6);
    }

    private boolean matches(char[] board, char target, int a, int b, int c) {
        return board[a] == target && board[b] == target && board[c] == target;
    }

    private boolean hasEmptyCell(char[] board) {
        for (char cell : board) {
            if (cell == ' ') {
                return true;
            }
        }
        return false;
    }

    private char opponentOf(char current) {
        return current == 'X' ? 'O' : 'X';
    }
}

public class Program {
    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless()) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Demo");
            frame.add(new TicTacToePanel(new GridLayout(3, 3)));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setBounds(5, 5, 500, 500);
            frame.setVisible(true);
        });
    }
}

class TicTacToeCell extends JButton {
    private final int num;
    private final int row;
    private final int col;
    private char marker;

    TicTacToeCell(int num, int x, int y) {
        this.num = num;
        this.row = y;
        this.col = x;
        this.marker = ' ';
        setText(Character.toString(marker));
        setFont(getFont().deriveFont(40f));
    }

    void setMarker(String value) {
        marker = value.charAt(0);
        setText(value);
        setEnabled(false);
    }

    char getMarker() {
        return marker;
    }

    int getRow() {
        return row;
    }

    int getCol() {
        return col;
    }

    int getNum() {
        return num;
    }
}

class TicTacToePanel extends JPanel implements ActionListener {
    final Game game;
    final TicTacToeCell[] cells;

    TicTacToePanel(GridLayout layout) {
        super(layout);
        game = new Game();
        cells = new TicTacToeCell[9];
        createCell(0, 0, 0);
        createCell(1, 1, 0);
        createCell(2, 2, 0);
        createCell(3, 0, 1);
        createCell(4, 1, 1);
        createCell(5, 2, 1);
        createCell(6, 0, 2);
        createCell(7, 1, 2);
        createCell(8, 2, 2);
    }

    private void createCell(int num, int x, int y) {
        cells[num] = new TicTacToeCell(num, x, y);
        cells[num].addActionListener(this);
        add(cells[num]);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        int clickedIndex = -1;
        for (int i = 0; i < cells.length; i++) {
            if (event.getSource() == cells[i]) {
                clickedIndex = i;
                break;
            }
        }

        if (clickedIndex < 0 || !cells[clickedIndex].isEnabled()) {
            return;
        }

        playMove(clickedIndex, game.cplayer.symbol);
        if (finishIfGameOver()) {
            return;
        }

        if (game.cplayer == game.player1) {
            game.player2.move = game.MiniMax(game.board, game.player2);
            game.nmove = game.player2.move;
            game.symbol = game.player2.symbol;
            if (game.player2.move > 0) {
                playMove(game.player2.move - 1, game.player2.symbol);
            }
            game.cplayer = game.player1;
        } else {
            game.nmove = game.player1.move;
            game.symbol = game.player1.symbol;
            game.cplayer = game.player1;
        }

        finishIfGameOver();
    }

    private void playMove(int index, char marker) {
        cells[index].setMarker(Character.toString(marker));
        syncBoard();
    }

    private void syncBoard() {
        for (int i = 0; i < cells.length; i++) {
            game.board[i] = cells[i].getMarker();
        }
    }

    private boolean finishIfGameOver() {
        game.state = game.checkState(game.board);
        if (game.state == State.PLAYING) {
            return false;
        }

        String message;
        if (game.state == State.XWIN) {
            message = "X wins";
        } else if (game.state == State.OWIN) {
            message = "O wins";
        } else {
            message = "Draw";
        }

        JOptionPane.showMessageDialog(null, message, "Result", JOptionPane.WARNING_MESSAGE);
        System.exit(0);
        return true;
    }
}
