package com.mycompany.app;

public class Program {
    public static final int EMPTY = 0;
    public static final int X = 1;
    public static final int O = 2;
    
    private int[][] board = new int[3][3];
    
    public Program() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
            }
        }
    }
    
    public boolean makeMove(int player, int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != EMPTY) {
            return false;
        }
        board[row][col] = player;
        return true;
    }
    
    public int checkWinner() {
        // Проверка строк
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != EMPTY && board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                return board[i][0];
            }
        }
        
        // Проверка столбцов
        for (int j = 0; j < 3; j++) {
            if (board[0][j] != EMPTY && board[0][j] == board[1][j] && board[0][j] == board[2][j]) {
                return board[0][j];
            }
        }
        
        // Проверка диагоналей
        if (board[0][0] != EMPTY && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            return board[0][0];
        }
        if (board[0][2] != EMPTY && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            return board[0][2];
        }
        
        // Проверка на ничью
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    return EMPTY;
                }
            }
        }
        
        return -1;
    }
    
    public int[] findBestMove() {
        int bestVal = Integer.MIN_VALUE;
        int[] bestMove = new int[]{-1, -1};
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = O;
                    int moveVal = minimax(0, false);
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
    
    private int minimax(int depth, boolean isMax) {
        int score = evaluate();
        
        if (score == 10) return score - depth;
        if (score == -10) return score + depth;
        if (isBoardFull()) return 0;
        
        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = O;
                        best = Math.max(best, minimax(depth + 1, false));
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
                        board[i][j] = X;
                        best = Math.min(best, minimax(depth + 1, true));
                        board[i][j] = EMPTY;
                    }
                }
            }
            return best;
        }
    }
    
    // Изменено с private на public для тестирования
    public int evaluate() {
        int winner = checkWinner();
        if (winner == O) return 10;
        if (winner == X) return -10;
        return 0;
    }
    
    // Изменено с private на public для тестирования
    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public int getCell(int row, int col) {
        return board[row][col];
    }
}