package moe.lair.ttt;

// Реализация игры "Крестики-нолики" (3x3)
// Минимаксный алгоритм

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/** Состояние партии в крестики-нолики. */
enum State { PLAYING, OWIN, XWIN, DRAW }

/** Игрок: его символ и несколько полей состояния, оставленных из исходной программы. */
class Player {
    public char symbol;
    public int move;
    public boolean selected;
    public boolean win;
}

/** Игровая логика и минимакс для поля 3x3. */
class Game {
    public State state;
    public Player player1, player2;
    public Player cplayer; // текущий игрок
    public int nmove;      // последний шаг сделанный действующим игроком
    public char symbol;
    public static final int INF = 100;
    public int q;
    public char[] board;

    public Game() {
        player1 = new Player();
        player2 = new Player();
        player1.symbol = 'X';
        player2.symbol = 'O';
        cplayer = player1;
        state = State.PLAYING;
        board = new char[9];
        for (int i = 0; i < 9; i++) {
            board[i] = ' ';
        }
    }

    /** Возвращает состояние игры для текущего проверяемого символа. */
    public State checkState(char[] board) {
        State result = State.PLAYING;
        if ((board[0] == symbol && board[1] == symbol && board[2] == symbol)
                || (board[3] == symbol && board[4] == symbol && board[5] == symbol)
                || (board[6] == symbol && board[7] == symbol && board[8] == symbol)
                || (board[0] == symbol && board[3] == symbol && board[6] == symbol)
                || (board[1] == symbol && board[4] == symbol && board[7] == symbol)
                || (board[2] == symbol && board[5] == symbol && board[8] == symbol)
                || (board[0] == symbol && board[4] == symbol && board[8] == symbol)
                || (board[2] == symbol && board[4] == symbol && board[6] == symbol)) {
            if (symbol == 'X') {
                result = State.XWIN;
            } else if (symbol == 'O') {
                result = State.OWIN;
            }
        } else {
            result = State.DRAW;
            for (int i = 0; i < 9; i++) {
                if (board[i] == ' ') {
                    result = State.PLAYING;
                    break;
                }
            }
        }
        return result;
    }

    /** Сгенерировать возможные ходы. */
    void generateMoves(char[] board, ArrayList<Integer> moveList) {
        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                moveList.add(i);
            }
        }
    }

    /** Оценка позиции относительно переданного игрока. */
    int evaluatePosition(char[] board, Player player) {
        State checkedState = checkState(board);
        if (checkedState == State.XWIN || checkedState == State.OWIN || checkedState == State.DRAW) {
            if ((checkedState == State.XWIN && player.symbol == 'X')
                    || (checkedState == State.OWIN && player.symbol == 'O')) {
                return +Game.INF;
            } else if ((checkedState == State.XWIN && player.symbol == 'O')
                    || (checkedState == State.OWIN && player.symbol == 'X')) {
                return -Game.INF;
            } else if (checkedState == State.DRAW) {
                return 0;
            }
        }
        return -1;
    }

    /** Выбор наилучшего хода. Номер хода возвращается в диапазоне 1..9. */
    int MiniMax(char[] board, Player player) {
        int bestVal = -Game.INF - 1;
        int index = 0;
        ArrayList<Integer> moveList = new ArrayList<Integer>();
        int[] bestMoves = new int[9];

        generateMoves(board, moveList);
        while (moveList.size() != 0) {
            int move = moveList.get(0);
            board[move] = player.symbol;
            symbol = player.symbol;

            int val = MinMove(board, player);

            if (val > bestVal) {
                bestVal = val;
                index = 0;
                bestMoves[index] = move + 1;
            } else if (val == bestVal) {
                bestMoves[++index] = move + 1;
            }

            System.out.printf("%nminimax: %3d(%1d) ", 1 + move, val);
            board[move] = ' ';
            moveList.remove(0);
        }

        System.out.printf("%nminimax best: %3d(%1d) ", bestMoves[0], bestVal);
        System.out.printf("Steps counted: %d", q);
        q = 0;
        return bestMoves[0];
    }

    int MinMove(char[] board, Player player) {
        int posValue = evaluatePosition(board, player);
        if (posValue != -1) {
            return posValue;
        }
        q++;
        int bestVal = +Game.INF;
        ArrayList<Integer> moveList = new ArrayList<Integer>();

        generateMoves(board, moveList);
        while (moveList.size() != 0) {
            symbol = (player.symbol == 'X') ? 'O' : 'X';
            int move = moveList.get(0);
            board[move] = symbol;

            int val = MaxMove(board, player);
            if (val < bestVal) {
                bestVal = val;
            }
            board[move] = ' ';
            moveList.remove(0);
        }
        return bestVal;
    }

    int MaxMove(char[] board, Player player) {
        int posValue = evaluatePosition(board, player);
        if (posValue != -1) {
            return posValue;
        }
        q++;
        int bestVal = -Game.INF;
        ArrayList<Integer> moveList = new ArrayList<Integer>();
        generateMoves(board, moveList);
        while (moveList.size() != 0) {
            symbol = (player.symbol == 'X') ? 'X' : 'O';
            int move = moveList.get(0);
            board[move] = symbol;
            int val = MinMove(board, player);
            if (val > bestVal) {
                bestVal = val;
            }
            board[move] = ' ';
            moveList.remove(0);
        }
        return bestVal;
    }
}

public class Program {
    public static FileWriter fileWriter;
    public static PrintWriter printWriter;

    public static TicTacToePanel createPanel() {
        return new TicTacToePanel(new GridLayout(3, 3));
    }

    public static JFrame createFrame() {
        JFrame frame = new JFrame("Demo");
        frame.add(createPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(5, 5, 500, 500);
        return frame;
    }

    public static void main(String[] args) throws IOException {
        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("Cannot show GUI in a headless environment.");
            return;
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = createFrame();
                frame.setVisible(true);
            }
        });
    }
}

class TicTacToeCell extends JButton {
    private int num;
    private int row;
    private int col;
    private char marker;

    public TicTacToeCell(int num, int x, int y) {
        this.num = num;
        row = y;
        col = x;
        marker = ' ';
        setText(Character.toString(marker));
        setFont(new Font("Arial", Font.PLAIN, 40));
    }

    public void setMarker(String m) {
        marker = m.charAt(0);
        setText(m);
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
        System.out.println();
        for (int j = 0; j < 9; j++) {
            System.out.print(board[j] + "-");
        }
        System.out.println();
    }

    public static void print(int[] board) {
        System.out.println();
        for (int j = 0; j < 9; j++) {
            System.out.print(board[j] + "-");
        }
        System.out.println();
    }

    public static void print(ArrayList<Integer> moves) {
        System.out.println();
        for (int j = 0; j < moves.size(); j++) {
            System.out.print(moves.get(j) + "-");
        }
        System.out.println();
    }
}

class TicTacToePanel extends JPanel implements ActionListener {
    private Game game;
    private TicTacToeCell[] cells = new TicTacToeCell[9];

    private void createCell(int num, int x, int y) {
        cells[num] = new TicTacToeCell(num, x, y);
        cells[num].addActionListener(this);
        add(cells[num]);
    }

    TicTacToePanel(GridLayout layout) {
        super(layout);
        createCell(0, 0, 0);
        createCell(1, 1, 0);
        createCell(2, 2, 0);
        createCell(3, 0, 1);
        createCell(4, 1, 1);
        createCell(5, 2, 1);
        createCell(6, 0, 2);
        createCell(7, 1, 2);
        createCell(8, 2, 2);
        game = new Game();
        game.cplayer = game.player1;
    }

    Game getGame() {
        return game;
    }

    TicTacToeCell getCell(int index) {
        return cells[index];
    }

    TicTacToeCell[] getCells() {
        return cells.clone();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        game.player1.move = -1;
        game.player2.move = -1;

        int i = 0;
        for (TicTacToeCell jb : cells) {
            if (ae.getSource() == jb) {
                jb.setMarker(Character.toString(game.cplayer.symbol));
            }
            game.board[i++] = jb.getMarker();
        }
        if (game.cplayer == game.player1) {
            game.player2.move = game.MiniMax(game.board, game.player2);
            game.nmove = game.player2.move;
            game.symbol = game.player2.symbol;
            game.cplayer = game.player2;
            if (game.player2.move > 0) {
                cells[game.player2.move - 1].doClick();
            }
        } else {
            game.nmove = game.player1.move;
            game.symbol = game.player1.symbol;
            game.cplayer = game.player1;
        }

        game.state = game.checkState(game.board);
        if (game.state == State.XWIN) {
            finishGame(State.XWIN);
        } else if (game.state == State.OWIN) {
            finishGame(State.OWIN);
        } else if (game.state == State.DRAW) {
            finishGame(State.DRAW);
        }
    }

    protected void finishGame(State finalState) {
        if (finalState == State.XWIN) {
            JOptionPane.showMessageDialog(null, "Выиграли крестики", "Результат", JOptionPane.WARNING_MESSAGE);
        } else if (finalState == State.OWIN) {
            JOptionPane.showMessageDialog(null, "Выиграли нолики", "Результат", JOptionPane.WARNING_MESSAGE);
        } else if (finalState == State.DRAW) {
            JOptionPane.showMessageDialog(null, "Ничья", "Результат", JOptionPane.WARNING_MESSAGE);
        }
        System.exit(0);
    }
}
