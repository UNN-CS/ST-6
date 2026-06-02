package com.dura1;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
    private static final int BOARD_SIZE = 9;
    private static final char EMPTY = ' ';

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
        cplayer = player1;
        state = State.PLAYING;
        symbol = 'X';
        board = new char[BOARD_SIZE];
        Arrays.fill(board, EMPTY);
    }

    public State checkState(char[] currentBoard) {
        char winner = winnerOf(currentBoard);
        if (winner == 'X') {
            return State.XWIN;
        }
        if (winner == 'O') {
            return State.OWIN;
        }
        return hasEmptyCell(currentBoard) ? State.PLAYING : State.DRAW;
    }

    void generateMoves(char[] currentBoard, ArrayList<Integer> moveList) {
        moveList.clear();
        for (int i = 0; i < currentBoard.length; i++) {
            if (currentBoard[i] == EMPTY) {
                moveList.add(i);
            }
        }
    }

    int evaluatePosition(char[] currentBoard, Player player) {
        char winner = winnerOf(currentBoard);
        if (winner == player.symbol) {
            return INF;
        }
        if (winner != EMPTY) {
            return -INF;
        }
        if (!hasEmptyCell(currentBoard)) {
            return 0;
        }
        return -1;
    }

    int MiniMax(char[] currentBoard, Player player) {
        return chooseBestMove(currentBoard, player);
    }

    int chooseBestMove(char[] currentBoard, Player player) {
        int bestMove = -1;
        int bestScore = -INF - 1;
        ArrayList<Integer> moves = new ArrayList<>();
        generateMoves(currentBoard, moves);

        for (Integer move : moves) {
            currentBoard[move] = player.symbol;
            int score = scoreAfterOpponentMove(currentBoard, player);
            currentBoard[move] = EMPTY;
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove < 0 ? 0 : bestMove + 1;
    }

    int MinMove(char[] currentBoard, Player player) {
        return scoreAfterOpponentMove(currentBoard, player);
    }

    int scoreAfterOpponentMove(char[] currentBoard, Player player) {
        int score = evaluatePosition(currentBoard, player);
        if (score != -1) {
            return score;
        }

        q++;
        int bestScore = INF + 1;
        char opponent = opposite(player.symbol);
        ArrayList<Integer> moves = new ArrayList<>();
        generateMoves(currentBoard, moves);

        for (Integer move : moves) {
            currentBoard[move] = opponent;
            bestScore = Math.min(bestScore, scoreAfterComputerMove(currentBoard, player));
            currentBoard[move] = EMPTY;
        }
        return bestScore;
    }

    int MaxMove(char[] currentBoard, Player player) {
        return scoreAfterComputerMove(currentBoard, player);
    }

    int scoreAfterComputerMove(char[] currentBoard, Player player) {
        int score = evaluatePosition(currentBoard, player);
        if (score != -1) {
            return score;
        }

        q++;
        int bestScore = -INF - 1;
        ArrayList<Integer> moves = new ArrayList<>();
        generateMoves(currentBoard, moves);

        for (Integer move : moves) {
            currentBoard[move] = player.symbol;
            bestScore = Math.max(bestScore, scoreAfterOpponentMove(currentBoard, player));
            currentBoard[move] = EMPTY;
        }
        return bestScore;
    }

    private static char opposite(char marker) {
        return marker == 'X' ? 'O' : 'X';
    }

    private static boolean hasEmptyCell(char[] currentBoard) {
        for (char cell : currentBoard) {
            if (cell == EMPTY) {
                return true;
            }
        }
        return false;
    }

    private static char winnerOf(char[] currentBoard) {
        int[][] lines = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
        };

        for (int[] line : lines) {
            char marker = currentBoard[line[0]];
            if (marker != EMPTY && marker == currentBoard[line[1]]
                    && marker == currentBoard[line[2]]) {
                return marker;
            }
        }
        return EMPTY;
    }
}

public class Program {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tic Tac Toe");
            frame.add(new TicTacToePanel(new GridLayout(3, 3)));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(420, 420);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

class TicTacToeCell extends JButton {
    private final int num;
    private final int row;
    private final int col;
    private char marker;

    public TicTacToeCell(int num, int x, int y) {
        this.num = num;
        this.col = x;
        this.row = y;
        this.marker = ' ';
        setText(" ");
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
        StringBuilder line = new StringBuilder();
        for (char cell : board) {
            line.append(cell).append('-');
        }
        System.out.println(line);
    }

    public static void print(int[] board) {
        StringBuilder line = new StringBuilder();
        for (int cell : board) {
            line.append(cell).append('-');
        }
        System.out.println(line);
    }

    public static void print(ArrayList<Integer> moves) {
        StringBuilder line = new StringBuilder();
        for (Integer move : moves) {
            line.append(move).append('-');
        }
        System.out.println(line);
    }
}

class TicTacToePanel extends JPanel implements ActionListener {
    private Game game;
    private TicTacToeCell[] cells = new TicTacToeCell[9];

    TicTacToePanel(GridLayout layout) {
        super(layout);
        game = new Game();
        for (int i = 0; i < cells.length; i++) {
            createCell(i, i % 3, i / 3);
        }
    }

    private void createCell(int num, int x, int y) {
        cells[num] = new TicTacToeCell(num, x, y);
        cells[num].addActionListener(this);
        add(cells[num]);
    }

    public void actionPerformed(ActionEvent event) {
        TicTacToeCell source = (TicTacToeCell) event.getSource();
        if (source.getMarker() != ' ' || game.state != State.PLAYING) {
            return;
        }

        source.setMarker(Character.toString(game.cplayer.symbol));
        syncBoardFromCells();
        game.state = game.checkState(game.board);
        if (finishIfNeeded()) {
            return;
        }

        if (game.cplayer == game.player1) {
            int move = game.chooseBestMove(game.board, game.player2);
            game.nmove = move;
            game.cplayer = game.player2;
            if (move > 0 && cells[move - 1].getMarker() == ' ') {
                cells[move - 1].setMarker(Character.toString(game.player2.symbol));
            }
            syncBoardFromCells();
            game.state = game.checkState(game.board);
            game.cplayer = game.player1;
            finishIfNeeded();
        }
    }

    private void syncBoardFromCells() {
        for (int i = 0; i < cells.length; i++) {
            game.board[i] = cells[i].getMarker();
        }
    }

    private boolean finishIfNeeded() {
        if (game.state == State.PLAYING) {
            return false;
        }

        if (!java.awt.GraphicsEnvironment.isHeadless()) {
            JOptionPane.showMessageDialog(this, game.state.toString(), "Result",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        return true;
    }
}
