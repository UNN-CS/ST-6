package com.mycompany.app;

public class Program {
    public static class Game {
        public char[] board = new char[9];

        public Game() {
            for (int i = 0; i < 9; i++) board[i] = ' ';
        }

        public boolean isFull() {
            for (char c : board) if (c == ' ') return false;
            return true;
        }

        public char checkWinner() {
            int[][] wins = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
            for (int[] w : wins) {
                if (board[w[0]] != ' ' && board[w[0]] == board[w[1]] && board[w[1]] == board[w[2]]) {
                    return board[w[0]];
                }
            }
            return ' ';
        }

        public int evaluate() {
            char w = checkWinner();
            if (w == 'X') return 10;
            if (w == 'O') return -10;
            return 0;
        }

        public int minimax(int depth, boolean isMax) {
            int score = evaluate();
            if (score == 10) return score - depth;
            if (score == -10) return score + depth;
            if (isFull()) return 0;

            if (isMax) {
                int best = -1000;
                for (int i = 0; i < 9; i++) {
                    if (board[i] == ' ') {
                        board[i] = 'X';
                        best = Math.max(best, minimax(depth + 1, false));
                        board[i] = ' ';
                    }
                }
                return best;
            } else {
                int best = 1000;
                for (int i = 0; i < 9; i++) {
                    if (board[i] == ' ') {
                        board[i] = 'O';
                        best = Math.min(best, minimax(depth + 1, true));
                        board[i] = ' ';
                    }
                }
                return best;
            }
        }

        public int findBestMove() {
            int bestVal = -1000;
            int bestMove = -1;
            for (int i = 0; i < 9; i++) {
                if (board[i] == ' ') {
                    board[i] = 'X';
                    int moveVal = minimax(0, false);
                    board[i] = ' ';
                    if (moveVal > bestVal) {
                        bestMove = i;
                        bestVal = moveVal;
                    }
                }
            }
            return bestMove;
        }
        
        public void printBoard() {
            System.out.println(board[0] + "|" + board[1] + "|" + board[2]);
            System.out.println(board[3] + "|" + board[4] + "|" + board[5]);
            System.out.println(board[6] + "|" + board[7] + "|" + board[8]);
        }
    }

    public static void main(String[] args) {
        Game g = new Game();
        g.board[0] = 'X';
        g.board[1] = 'X';
        int move = g.findBestMove();
        System.out.println("Best move: " + move);
        g.printBoard();
    }
}
