package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;

enum GameState {
    DRAW,
    O_WON,
    PLAYING,
    X_WON
}

final class Move {
    private final int col;
    private final int row;

    Move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    int getRow() {
        return row;
    }

    int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Move)) {
            return false;
        }
        Move that = (Move) obj;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return row * 31 + col;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}

class Game {
    static final char X = 'X';
    static final char O = 'O';
    static final char EMPTY = ' ';
    static final int SIZE = 3;

    private static final int WIN_SCORE = 10;

    private final char[][] cells;
    private char activeMark;

    Game() {
        cells = new char[SIZE][SIZE];
        reset();
    }

    void reset() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                cells[r][c] = EMPTY;
            }
        }
        activeMark = X;
    }

    char getCurrentPlayer() {
        return activeMark;
    }

    char getCell(int row, int col) {
        ensureInside(row, col);
        return cells[row][col];
    }

    boolean isEmptyCell(int row, int col) {
        return inBounds(row, col) && cells[row][col] == EMPTY;
    }

    boolean isValidMove(int row, int col) {
        return inBounds(row, col) && cells[row][col] == EMPTY && !isGameOver();
    }

    boolean placeMove(int row, int col) {
        if (!isValidMove(row, col)) {
            return false;
        }
        cells[row][col] = activeMark;
        if (!isGameOver()) {
            toggleActiveMark();
        }
        return true;
    }

    void setCellForTest(int row, int col, char player) {
        ensureInside(row, col);
        if (player != X && player != O && player != EMPTY) {
            throw new IllegalArgumentException("Unknown player");
        }
        cells[row][col] = player;
    }

    void setCurrentPlayerForTest(char player) {
        if (player != X && player != O) {
            throw new IllegalArgumentException("Unknown player");
        }
        activeMark = player;
    }

    void switchPlayer() {
        toggleActiveMark();
    }

    Character getWinner() {
        if (markHasLine(X)) {
            return X;
        }
        if (markHasLine(O)) {
            return O;
        }
        return null;
    }

    GameState getState() {
        Character winner = getWinner();
        if (winner != null) {
            return winner == X ? GameState.X_WON : GameState.O_WON;
        }
        if (noEmptyCells()) {
            return GameState.DRAW;
        }
        return GameState.PLAYING;
    }

    boolean isGameOver() {
        return getState() != GameState.PLAYING;
    }

    boolean isFullBoard() {
        return noEmptyCells();
    }

    List<Move> getAvailableMoves() {
        List<Move> result = new ArrayList<Move>();
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (cells[r][c] == EMPTY) {
                    result.add(new Move(r, c));
                }
            }
        }
        return result;
    }

    Move findBestMove(char aiPlayer) {
        if (boardIsBlank() && isEmptyCell(1, 1)) {
            return new Move(1, 1);
        }

        Move chosen = null;
        int topScore = Integer.MIN_VALUE;
        for (Move candidate : getAvailableMoves()) {
            applyMark(candidate, aiPlayer);
            int rating = minimax(false, aiPlayer);
            clearCell(candidate);
            if (rating > topScore) {
                topScore = rating;
                chosen = candidate;
            }
        }
        return chosen;
    }

    int minimax(boolean aiTurn, char aiPlayer) {
        GameState outcome = getState();
        if (outcome != GameState.PLAYING) {
            return rateOutcome(outcome, aiPlayer);
        }

        char side = aiTurn ? aiPlayer : otherMark(aiPlayer);
        int bound = aiTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (Move step : getAvailableMoves()) {
            applyMark(step, side);
            int child = minimax(!aiTurn, aiPlayer);
            clearCell(step);
            bound = aiTurn ? Math.max(bound, child) : Math.min(bound, child);
        }
        return bound;
    }

    String boardToString() {
        StringBuilder text = new StringBuilder();
        for (int r = 0; r < SIZE; r++) {
            if (r > 0) {
                text.append('\n');
            }
            for (int c = 0; c < SIZE; c++) {
                if (c > 0) {
                    text.append('|');
                }
                char value = cells[r][c];
                text.append(value == EMPTY ? '-' : value);
            }
        }
        return text.toString();
    }

    private int rateOutcome(GameState outcome, char aiPlayer) {
        if ((outcome == GameState.X_WON && aiPlayer == X)
                || (outcome == GameState.O_WON && aiPlayer == O)) {
            return WIN_SCORE;
        }
        if ((outcome == GameState.X_WON && aiPlayer == O)
                || (outcome == GameState.O_WON && aiPlayer == X)) {
            return -WIN_SCORE;
        }
        return 0;
    }

    private boolean markHasLine(char mark) {
        for (int i = 0; i < SIZE; i++) {
            boolean rowFull = cells[i][0] == mark && cells[i][1] == mark && cells[i][2] == mark;
            boolean colFull = cells[0][i] == mark && cells[1][i] == mark && cells[2][i] == mark;
            if (rowFull || colFull) {
                return true;
            }
        }
        boolean mainDiag = cells[0][0] == mark && cells[1][1] == mark && cells[2][2] == mark;
        boolean antiDiag = cells[0][2] == mark && cells[1][1] == mark && cells[2][0] == mark;
        return mainDiag || antiDiag;
    }

    private boolean inBounds(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    private void ensureInside(int row, int col) {
        if (!inBounds(row, col)) {
            throw new IllegalArgumentException("Cell is outside the board");
        }
    }

    private boolean noEmptyCells() {
        return getAvailableMoves().isEmpty();
    }

    private boolean boardIsBlank() {
        return getAvailableMoves().size() == SIZE * SIZE;
    }

    private char otherMark(char mark) {
        return mark == X ? O : X;
    }

    private void applyMark(Move move, char mark) {
        cells[move.getRow()][move.getCol()] = mark;
    }

    private void clearCell(Move move) {
        cells[move.getRow()][move.getCol()] = EMPTY;
    }

    private void toggleActiveMark() {
        activeMark = activeMark == X ? O : X;
    }
}

public class Program {
    public static void main(String[] args) {
        Game session = new Game();
        session.placeMove(0, 0);

        Move reply = session.findBestMove(Game.O);
        if (reply != null) {
            session.placeMove(reply.getRow(), reply.getCol());
        }

        System.out.println("Current board:");
        System.out.println(session.boardToString());
        System.out.println("State: " + session.getState());
    }
}
