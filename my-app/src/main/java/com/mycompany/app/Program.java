package com.mycompany.app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

enum State { PLAYING, XWIN, OWIN, DRAW }

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

    static final int[][] WINNING_COMBINATIONS = {
        {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
        {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
        {0, 4, 8}, {2, 4, 6}
    };

    public Game() {
        player1 = new Player();
        player2 = new Player();
        player1.symbol = 'X';
        player2.symbol = 'O';
        state = State.PLAYING;
        board = new char[9];
        for (int boardIndex = 0; boardIndex < 9; boardIndex++)
            board[boardIndex] = ' ';
    }

    public State checkState(char[] board) {
        for (int[] winningCombo : WINNING_COMBINATIONS) {
            boolean firstMatches  = board[winningCombo[0]] == symbol;
            boolean secondMatches = board[winningCombo[1]] == symbol;
            boolean thirdMatches  = board[winningCombo[2]] == symbol;
            if (firstMatches && secondMatches && thirdMatches) {
                return symbol == 'X' ? State.XWIN : State.OWIN;
            }
        }
        for (char cellValue : board) {
            if (cellValue == ' ') return State.PLAYING;
        }
        return State.DRAW;
    }

    void generateMoves(char[] board, ArrayList<Integer> availableMoveList) {
        for (int cellIndex = 0; cellIndex < board.length; cellIndex++) {
            if (board[cellIndex] == ' ')
                availableMoveList.add(cellIndex);
        }
    }

    int evaluatePosition(char[] board, Player player) {
        State currentBoardState = checkState(board);
        if (currentBoardState == State.PLAYING) return -1;
        if (currentBoardState == State.DRAW) return 0;
        boolean currentPlayerIsWinner =
                (currentBoardState == State.XWIN && player.symbol == 'X') ||
                (currentBoardState == State.OWIN && player.symbol == 'O');
        return currentPlayerIsWinner ? INF : -INF;
    }

    int MiniMax(char[] board, Player player) {
        ArrayList<Integer> availablePositions = new ArrayList<>();
        generateMoves(board, availablePositions);

        int bestScoreFound = -INF;
        int[] candidateMoves = new int[9];
        int candidateCount = 0;

        for (int emptyPosition : availablePositions) {
            board[emptyPosition] = player.symbol;
            symbol = player.symbol;

            int positionScore = MinMove(board, player);
            System.out.printf("\nminimax: %3d(%1d) ", emptyPosition + 1, positionScore);
            board[emptyPosition] = ' ';

            if (positionScore > bestScoreFound) {
                bestScoreFound = positionScore;
                candidateCount = 0;
                candidateMoves[candidateCount++] = emptyPosition + 1;
            } else if (positionScore == bestScoreFound) {
                candidateMoves[candidateCount++] = emptyPosition + 1;
            }
        }

        int selectedMove = (candidateCount > 1)
                ? candidateMoves[new Random().nextInt(candidateCount)]
                : candidateMoves[0];
        System.out.printf("\nminimax best: %3d(%1d) ", selectedMove, bestScoreFound);
        System.out.printf("Steps counted: %d", q);
        q = 0;
        return selectedMove;
    }

    int MinMove(char[] board, Player player) {
        int evaluatedScore = evaluatePosition(board, player);
        if (evaluatedScore != -1) return evaluatedScore;
        q++;

        char opponentSymbol = (player.symbol == 'X') ? 'O' : 'X';
        ArrayList<Integer> emptyPositions = new ArrayList<>();
        generateMoves(board, emptyPositions);

        int lowestScoreFound = INF;
        for (int currentPosition : emptyPositions) {
            symbol = opponentSymbol;
            board[currentPosition] = opponentSymbol;
            int moveScore = MaxMove(board, player);
            board[currentPosition] = ' ';
            if (moveScore < lowestScoreFound) lowestScoreFound = moveScore;
        }
        return lowestScoreFound;
    }

    int MaxMove(char[] board, Player player) {
        int evaluatedScore = evaluatePosition(board, player);
        if (evaluatedScore != -1) return evaluatedScore;
        q++;

        ArrayList<Integer> emptyPositions = new ArrayList<>();
        generateMoves(board, emptyPositions);

        int highestScoreFound = -INF;
        for (int currentPosition : emptyPositions) {
            symbol = player.symbol;
            board[currentPosition] = player.symbol;
            int moveScore = MinMove(board, player);
            board[currentPosition] = ' ';
            if (moveScore > highestScoreFound) highestScoreFound = moveScore;
        }
        return highestScoreFound;
    }
}

public class Program {
    public static void main(String[] args) {
        TicTacToePanel gamePanel = new TicTacToePanel(new GridLayout(3, 3));
        JFrame gameWindow = new JFrame("Tic-Tac-Toe");
        gameWindow.setContentPane(gamePanel);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setSize(500, 500);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
    }
}

class TicTacToeCell extends JButton {
    private int num;
    private int row;
    private int col;
    private char marker;

    public TicTacToeCell(int num, int x, int y) {
        this.num = num;
        col = x;
        row = y;
        marker = ' ';
        setText(String.valueOf(marker));
        setFont(new Font("Arial", Font.PLAIN, 40));
    }

    public void setMarker(String markerValue) {
        marker = markerValue.charAt(0);
        setText(markerValue);
        setEnabled(false);
    }

    public char getMarker() { return marker; }
    public int getRow()     { return row; }
    public int getCol()     { return col; }
    public int getNum()     { return num; }
}

class Utility {
    public static void print(char[] board) {
        StringBuilder boardOutput = new StringBuilder("\n");
        for (char cellSymbol : board) boardOutput.append(cellSymbol).append('-');
        System.out.println(boardOutput);
    }

    public static void print(int[] arr) {
        StringBuilder arrayOutput = new StringBuilder("\n");
        for (int numberValue : arr) arrayOutput.append(numberValue).append('-');
        System.out.println(arrayOutput);
    }

    public static void print(ArrayList<Integer> list) {
        StringBuilder listOutput = new StringBuilder("\n");
        for (int elementValue : list) listOutput.append(elementValue).append('-');
        System.out.println(listOutput);
    }
}

class TicTacToePanel extends JPanel implements ActionListener {
    private Game game;
    private TicTacToeCell[] cells = new TicTacToeCell[9];

    TicTacToePanel(GridLayout layout) {
        super(layout);
        int cellNumber = 0;
        for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
            for (int colIndex = 0; colIndex < 3; colIndex++) {
                cells[cellNumber] = new TicTacToeCell(cellNumber, colIndex, rowIndex);
                cells[cellNumber].addActionListener(this);
                add(cells[cellNumber]);
                cellNumber++;
            }
        }
        game = new Game();
        game.cplayer = game.player1;
    }

    public void actionPerformed(ActionEvent ae) {
        game.player1.move = -1;
        game.player2.move = -1;

        int boardPosition = 0;
        for (TicTacToeCell currentCell : cells) {
            if (ae.getSource() == currentCell) {
                currentCell.setMarker(String.valueOf(game.cplayer.symbol));
            }
            game.board[boardPosition++] = currentCell.getMarker();
        }

        if (game.cplayer == game.player1) {
            game.player2.move = game.MiniMax(game.board, game.player2);
            game.nmove = game.player2.move;
            game.symbol = game.player2.symbol;
            game.cplayer = game.player2;
            if (game.player2.move > 0)
                cells[game.player2.move - 1].doClick();
        } else {
            game.nmove = game.player1.move;
            game.symbol = game.player1.symbol;
            game.cplayer = game.player1;
        }

        game.state = game.checkState(game.board);

        if (game.state == State.XWIN) {
            JOptionPane.showMessageDialog(null, "Выиграли крестики", "Результат", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        } else if (game.state == State.OWIN) {
            JOptionPane.showMessageDialog(null, "Выиграли нолики", "Результат", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        } else if (game.state == State.DRAW) {
            JOptionPane.showMessageDialog(null, "Ничья", "Результат", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
    }
}
