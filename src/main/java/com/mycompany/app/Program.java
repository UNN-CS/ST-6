package com.mycompany.app;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.play();
    }
}

class TicTacToe {
    private char[][] board;
    private char currentPlayer;

    public TicTacToe() {
        board = new char[3][3];
        currentPlayer = 'X';
        initializeBoard();
    }
	
	protected TicTacToe(char[][] board) {
    this.board = board;
    currentPlayer = 'X';
}

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }
	public char[][] getBoard() {
        return board;
    }
    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (!isGameOver()) {
            printBoard();
            if (currentPlayer == 'X') {
                System.out.println("Player X's turn:");
                System.out.print("Row (0-2): ");
                int row = scanner.nextInt();
                System.out.print("Column (0-2): ");
                int col = scanner.nextInt();
                if (isValidMove(row, col)) {
                    board[row][col] = 'X';
                    currentPlayer = 'O';
                } else {
                    System.out.println("Invalid move!");
                }
            } else {
                System.out.println("Computer's turn (O):");
                int[] bestMove = findBestMove();
                board[bestMove[0]][bestMove[1]] = 'O';
                currentPlayer = 'X';
            }
        }
        printBoard();
        scanner.close();
        printResult();
    }

    public int[] findBestMove() {
        int[] bestMove = new int[]{-1, -1};
        int bestScore = Integer.MIN_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = 'O';
                    int score = minimax(board, 0, false);
                    board[i][j] = '-';
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        return bestMove;
    }

    int minimax(char[][] board, int depth, boolean isMaximizing) {
        if (hasWon('O')) return 1;
        if (hasWon('X')) return -1;
        if (isBoardFull()) return 0;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = 'O';
                        int score = minimax(board, depth + 1, false);
                        board[i][j] = '-';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = 'X';
                        int score = minimax(board, depth + 1, true);
                        board[i][j] = '-';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    public boolean isGameOver() {
        return hasWon('X') || hasWon('O') || isBoardFull();
    }

    boolean hasWon(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
        return false;
    }

    boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') return false;
            }
        }
        return true;
    }

    boolean isValidMove(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '-';
    }

    private void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void printResult() {
        if (hasWon('X')) System.out.println("Player X wins!");
        else if (hasWon('O')) System.out.println("Computer O wins!");
        else System.out.println("It's a draw!");
    }
}