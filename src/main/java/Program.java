import java.util.ArrayList;
import java.util.List;

public class Program {

    public static void main(String[] args) {
        Game game = new Game();
        int bestMove = game.findBestMove('X');
        System.out.println("Best opening move for X: " + bestMove);
    }
}

enum State {
    PLAYING,
    OWIN,
    XWIN,
    DRAW
}

class Player {
    final char symbol;

    Player(char symbol) {
        this.symbol = symbol;
    }
}

class Game {
    static final int INF = 100;

    private final char[] board = new char[9];

    Game() {
        clearBoard();
    }

    char[] getBoardCopy() {
        return board.clone();
    }

    void clearBoard() {
        for (int i = 0; i < board.length; i++) {
            board[i] = ' ';
        }
    }

    void setCell(int index, char symbol) {
        board[index] = symbol;
    }

    State checkState(char[] currentBoard) {
        if (isWin(currentBoard, 'X')) {
            return State.XWIN;
        }
        if (isWin(currentBoard, 'O')) {
            return State.OWIN;
        }
        for (char cell : currentBoard) {
            if (cell == ' ') {
                return State.PLAYING;
            }
        }
        return State.DRAW;
    }

    List<Integer> generateMoves(char[] currentBoard) {
        List<Integer> moves = new ArrayList<>();
        for (int i = 0; i < currentBoard.length; i++) {
            if (currentBoard[i] == ' ') {
                moves.add(i);
            }
        }
        return moves;
    }

    int evaluatePosition(char[] currentBoard, Player player) {
        State state = checkState(currentBoard);
        if (state == State.DRAW) {
            return 0;
        }
        if (state == State.XWIN) {
            return player.symbol == 'X' ? INF : -INF;
        }
        if (state == State.OWIN) {
            return player.symbol == 'O' ? INF : -INF;
        }
        return -1;
    }

    int findBestMove(char symbol) {
        Player player = new Player(symbol);
        int bestValue = -INF;
        int bestMove = -1;
        List<Integer> moves = generateMoves(board);

        for (int move : moves) {
            board[move] = symbol;
            int value = minMove(board, player);
            board[move] = ' ';

            if (value > bestValue || (value == bestValue && move < bestMove)) {
                bestValue = value;
                bestMove = move;
            }
        }

        return bestMove >= 0 ? bestMove + 1 : -1;
    }

    int miniMaxScore(char[] currentBoard, Player player) {
        return maxMove(currentBoard, player);
    }

    private int minMove(char[] currentBoard, Player player) {
        int positionValue = evaluatePosition(currentBoard, player);
        if (positionValue != -1) {
            return positionValue;
        }

        int bestValue = INF;
        char opponent = opposite(player.symbol);
        for (int move : generateMoves(currentBoard)) {
            currentBoard[move] = opponent;
            int value = maxMove(currentBoard, player);
            currentBoard[move] = ' ';
            if (value < bestValue) {
                bestValue = value;
            }
        }
        return bestValue;
    }

    private int maxMove(char[] currentBoard, Player player) {
        int positionValue = evaluatePosition(currentBoard, player);
        if (positionValue != -1) {
            return positionValue;
        }

        int bestValue = -INF;
        for (int move : generateMoves(currentBoard)) {
            currentBoard[move] = player.symbol;
            int value = minMove(currentBoard, player);
            currentBoard[move] = ' ';
            if (value > bestValue) {
                bestValue = value;
            }
        }
        return bestValue;
    }

    private boolean isWin(char[] currentBoard, char symbol) {
        return (currentBoard[0] == symbol && currentBoard[1] == symbol && currentBoard[2] == symbol)
                || (currentBoard[3] == symbol && currentBoard[4] == symbol && currentBoard[5] == symbol)
                || (currentBoard[6] == symbol && currentBoard[7] == symbol && currentBoard[8] == symbol)
                || (currentBoard[0] == symbol && currentBoard[3] == symbol && currentBoard[6] == symbol)
                || (currentBoard[1] == symbol && currentBoard[4] == symbol && currentBoard[7] == symbol)
                || (currentBoard[2] == symbol && currentBoard[5] == symbol && currentBoard[8] == symbol)
                || (currentBoard[0] == symbol && currentBoard[4] == symbol && currentBoard[8] == symbol)
                || (currentBoard[2] == symbol && currentBoard[4] == symbol && currentBoard[6] == symbol);
    }

    private char opposite(char symbol) {
        return symbol == 'X' ? 'O' : 'X';
    }
}