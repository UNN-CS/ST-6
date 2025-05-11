package com.mycompany.app;

import java.util.Scanner;

class Game {
    public static void main(String[] args) {
        Program game = new Program();
        game.start();
    }
}

class Program {
    private char[][] board = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
    private char currentPlayer = 'X';
    private boolean gameOver = false;
    private Scanner scanner = new Scanner(System.in);

    public char[][] getBoard() {
        return board;
    }

    public void start() {
        System.out.println("Welcome to the Tic-Tac-Toe game!");
        System.out.println("You play as X, the computer as O");

        while (!gameOver) {
            printBoard();

            if (currentPlayer == 'X') {
                playerMove();
            } else {
                System.out.println("Computer turn...");
                computerMove();
            }

            if (isWin(currentPlayer)) {
                printBoard();
                System.out.println(currentPlayer == 'X' ? "You've won!" : "PC won!");
                gameOver = true;
            } else if (isDraw()) {
                printBoard();
                System.out.println("Draw!");
                gameOver = true;
            }
        }
        scanner.close();
    }

    void printBoard() {
        System.out.println("\n  0 1 2");
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void playerMove() {
        while (true) {
            System.out.print("Enter the coordinates (row column): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (isValidMove(row, col)) {
                makeMove(row, col);
                break;
            } else {
                System.out.println("Wrong move! Try again");
            }
        }
    }

    private void computerMove() {
        int[] bestMove = findBestMove();
        makeMove(bestMove[0], bestMove[1]);
    }

    public int[] findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = 'O';
                    int score = minimax(0, false);
                    board[i][j] = ' ';

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

    private int minimax(int depth, boolean isMaximizing) {
        if (isWin('O')) return 10 - depth;
        if (isWin('X')) return depth - 10;
        if (isDraw()) return 0;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'O';
                        int score = minimax(depth + 1, false);
                        board[i][j] = ' ';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'X';
                        int score = minimax(depth + 1, true);
                        board[i][j] = ' ';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    public boolean isWin(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                gameOver = true;
                return true;
            }
        }

        for (int j = 0; j < 3; j++) {
            if (board[0][j] == player && board[1][j] == player && board[2][j] == player) {
                gameOver = true;
                return true;
            }
        }

        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            gameOver = true;
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            gameOver = true;
            return true;
        }

        return false;
    }

    public boolean isDraw() {
        if (isWin('X') || isWin('O')) {
            return false;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        gameOver = true;
        return true;
    }

    public void makeMove(int row, int col) {
        if (gameOver || !isValidMove(row, col)) {
            return;
        }
        board[row][col] = currentPlayer;
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public boolean isValidMove(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ';
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }
}