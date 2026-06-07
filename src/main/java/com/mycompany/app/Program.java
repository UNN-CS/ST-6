package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

enum GameState {
    PLAYING,
    X_WON,
    O_WON,
    DRAW
}

final class Move {
    private final int rowIndex;
    private final int columnIndex;

    Move(int row, int col) {
        this.rowIndex = row;
        this.columnIndex = col;
    }

    int getRow() {
        return rowIndex;
    }

    int getCol() {
        return columnIndex;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Move)) {
            return false;
        }
        Move that = (Move) other;
        return rowIndex == that.rowIndex && columnIndex == that.columnIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowIndex, columnIndex);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", rowIndex, columnIndex);
    }
}

class Game {
    static final char X = 'X';
    static final char O = 'O';
    static final char EMPTY = ' ';
    static final int SIZE = 3;

    private static final int TERMINAL_SCORE = 10;

    private final char[][] grid;
    private char turn;

    Game() {
        grid = new char[SIZE][SIZE];
        reset();
    }

    void reset() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                grid[row][col] = EMPTY;
            }
        }
        turn = X;
    }

    char getCurrentPlayer() {
        return turn;
    }

    char getCell(int row, int col) {
        checkCoordinates(row, col);
        return grid[row][col];
    }

    boolean isEmptyCell(int row, int col) {
        return withinBoard(row, col) && grid[row][col] == EMPTY;
    }

    boolean isValidMove(int row, int col) {
        return withinBoard(row, col) && grid[row][col] == EMPTY && !isGameOver();
    }

    boolean placeMove(int row, int col) {
        if (!isValidMove(row, col)) {
            return false;
        }
        grid[row][col] = turn;
        if (!isGameOver()) {
            swapTurn();
        }
        return true;
    }

    void setCellForTest(int row, int col, char player) {
        checkCoordinates(row, col);
        if (player != X && player != O && player != EMPTY) {
            throw new IllegalArgumentException("Unknown player");
        }
        grid[row][col] = player;
    }

    void setCurrentPlayerForTest(char player) {
        if (player != X && player != O) {
            throw new IllegalArgumentException("Unknown player");
        }
        turn = player;
    }

    void switchPlayer() {
        swapTurn();
    }

    Character getWinner() {
        if (hasWinningLine(X)) {
            return X;
        }
        if (hasWinningLine(O)) {
            return O;
        }
        return null;
    }

    GameState getState() {
        Character winner = getWinner();
        if (winner != null) {
            if (winner == X) {
                return GameState.X_WON;
            }
            return GameState.O_WON;
        }
        if (boardIsFull()) {
            return GameState.DRAW;
        }
        return GameState.PLAYING;
    }

    boolean isGameOver() {
        return getState() != GameState.PLAYING;
    }

    boolean isFullBoard() {
        return boardIsFull();
    }

    List<Move> getAvailableMoves() {
        List<Move> moves = new ArrayList<>();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (grid[row][col] == EMPTY) {
                    moves.add(new Move(row, col));
                }
            }
        }
        return moves;
    }

    Move findBestMove(char aiPlayer) {
        if (isCompletelyEmpty() && isEmptyCell(1, 1)) {
            return new Move(1, 1);
        }

        Move best = null;
        int bestValue = Integer.MIN_VALUE;
        for (Move option : getAvailableMoves()) {
            writeMove(option, aiPlayer);
            int value = minimax(false, aiPlayer);
            eraseMove(option);
            if (value > bestValue) {
                bestValue = value;
                best = option;
            }
        }
        return best;
    }

    int minimax(boolean maximizing, char aiPlayer) {
        GameState status = getState();
        if (status != GameState.PLAYING) {
            return scoreTerminal(status, aiPlayer);
        }

        char actingPlayer = maximizing ? aiPlayer : opponentOf(aiPlayer);
        int accumulator = maximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (Move option : getAvailableMoves()) {
            writeMove(option, actingPlayer);
            int childValue = minimax(!maximizing, aiPlayer);
            eraseMove(option);
            if (maximizing) {
                accumulator = Math.max(accumulator, childValue);
            } else {
                accumulator = Math.min(accumulator, childValue);
            }
        }
        return accumulator;
    }

    String boardToString() {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < SIZE; row++) {
            if (row != 0) {
                builder.append('\n');
            }
            for (int col = 0; col < SIZE; col++) {
                if (col != 0) {
                    builder.append('|');
                }
                char symbol = grid[row][col];
                builder.append(symbol == EMPTY ? '-' : symbol);
            }
        }
        return builder.toString();
    }

    private int scoreTerminal(GameState status, char aiPlayer) {
        boolean aiWon = (status == GameState.X_WON && aiPlayer == X)
                || (status == GameState.O_WON && aiPlayer == O);
        boolean aiLost = (status == GameState.X_WON && aiPlayer == O)
                || (status == GameState.O_WON && aiPlayer == X);
        if (aiWon) {
            return TERMINAL_SCORE;
        }
        if (aiLost) {
            return -TERMINAL_SCORE;
        }
        return 0;
    }

    private boolean hasWinningLine(char mark) {
        for (int index = 0; index < SIZE; index++) {
            if (lineIsComplete(grid[index][0], grid[index][1], grid[index][2], mark)) {
                return true;
            }
            if (lineIsComplete(grid[0][index], grid[1][index], grid[2][index], mark)) {
                return true;
            }
        }
        return lineIsComplete(grid[0][0], grid[1][1], grid[2][2], mark)
                || lineIsComplete(grid[0][2], grid[1][1], grid[2][0], mark);
    }

    private boolean lineIsComplete(char a, char b, char c, char mark) {
        return a == mark && b == mark && c == mark;
    }

    private boolean withinBoard(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    private void checkCoordinates(int row, int col) {
        if (!withinBoard(row, col)) {
            throw new IllegalArgumentException("Cell is outside the board");
        }
    }

    private boolean boardIsFull() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (grid[row][col] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isCompletelyEmpty() {
        return getAvailableMoves().size() == SIZE * SIZE;
    }

    private char opponentOf(char mark) {
        return mark == X ? O : X;
    }

    private void writeMove(Move move, char mark) {
        grid[move.getRow()][move.getCol()] = mark;
    }

    private void eraseMove(Move move) {
        grid[move.getRow()][move.getCol()] = EMPTY;
    }

    private void swapTurn() {
        turn = turn == X ? O : X;
    }
}

public class Program {
    public static void main(String[] args) {
        Game match = new Game();
        match.placeMove(0, 0);

        Move response = match.findBestMove(Game.O);
        if (response != null) {
            match.placeMove(response.getRow(), response.getCol());
        }

        System.out.println("Current board:");
        System.out.println(match.boardToString());
        System.out.println("State: " + match.getState());
    }
}
