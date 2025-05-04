package com.mycompany.app;

public class Program {
    public static final char HUMAN = 'X';
    public static final char AI = 'O';
    public static final char EMPTY = '-';

    public static void main(String[] args) {
        char[][] board = initializeBoard();
        playGame(board);
    }

    public static char[][] initializeBoard() {
        return new char[][]{
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY}
        };
    }

    public static void playGame(char[][] board) {
        while (true) {
            printBoard(board);
            humanMove(board);
            if (gameOver(board)) break;
            
            aiMove(board);
            if (gameOver(board)) break;
        }
    }

    public static void aiMove(char[][] board) {
        int[] bestMove = findBestMove(board);
        board[bestMove[0]][bestMove[1]] = AI;
    }

    public static int[] findBestMove(char[][] board) {
        int bestVal = Integer.MIN_VALUE;
        int[] bestMove = new int[]{-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = AI;
                    int moveVal = minimax(board, 0, false);
                    board[i][j] = EMPTY;

                    if (moveVal > bestVal) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

    public static int minimax(char[][] board, int depth, boolean isMaximizing) {
        int score = evaluateBoard(board);

        if (score == 10) return 10 - depth;
        if (score == -10) return -10 + depth;
        if (!isMovesLeft(board)) return 0;

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = AI;
                        best = Math.max(best, minimax(board, depth + 1, false));
                        board[i][j] = EMPTY;
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = HUMAN;
                        best = Math.min(best, minimax(board, depth + 1, true));
                        board[i][j] = EMPTY;
                    }
                }
            }
            return best;
        }
    }

    public static int evaluateBoard(char[][] board) {
        if (checkWin(board, AI)) return 10;
        if (checkWin(board, HUMAN)) return -10;
        return 0;
    }

    public static boolean checkWin(char[][] board, char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
        return false;
    }

    public static boolean isMovesLeft(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == EMPTY) return true;
            }
        }
        return false;
    }

    public static void humanMove(char[][] board) {
        board[0][0] = HUMAN; // Заглушка для тестов
    }

    public static void printBoard(char[][] board) {
        // Заглушка для вывода доски
    }

    public static boolean gameOver(char[][] board) {
        return checkWin(board, HUMAN) || checkWin(board, AI) || !isMovesLeft(board);
    }
}