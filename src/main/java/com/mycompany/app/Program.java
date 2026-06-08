package com.mycompany.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

enum GamePhase {
    IN_PROCESS, X_VICTORY, O_VICTORY, DEAD_HEAT
}

class PlayerData {
    public char sign;
    public int lastMove;
    public boolean selected;
    public boolean isWinner;

    PlayerData(char s) {
        sign = s;
        lastMove = -1;
        selected = false;
        isWinner = false;
    }
}

class TicTacEngine {
    public GamePhase phase;
    public PlayerData playerX, playerO;
    public PlayerData current;
    public int lastNmove;
    public char lastSymbol;
    public static final int MAX_SCORE = 100;
    public int positionsChecked;
    public char[] cells;

    public TicTacEngine() {
        playerX = new PlayerData('X');
        playerO = new PlayerData('O');
        phase = GamePhase.IN_PROCESS;
        cells = new char[9];
        for (int i = 0; i < 9; i++) cells[i] = ' ';
        current = playerX;
        positionsChecked = 0;
    }

    // Определение состояния по доске (без передачи последнего хода)
    public GamePhase evaluateBoard(char[] board) {
        // Проверка победы X
        boolean xWin = (board[0]=='X' && board[1]=='X' && board[2]=='X') ||
                       (board[3]=='X' && board[4]=='X' && board[5]=='X') ||
                       (board[6]=='X' && board[7]=='X' && board[8]=='X') ||
                       (board[0]=='X' && board[3]=='X' && board[6]=='X') ||
                       (board[1]=='X' && board[4]=='X' && board[7]=='X') ||
                       (board[2]=='X' && board[5]=='X' && board[8]=='X') ||
                       (board[0]=='X' && board[4]=='X' && board[8]=='X') ||
                       (board[2]=='X' && board[4]=='X' && board[6]=='X');
        if (xWin) return GamePhase.X_VICTORY;
        // Проверка победы O
        boolean oWin = (board[0]=='O' && board[1]=='O' && board[2]=='O') ||
                       (board[3]=='O' && board[4]=='O' && board[5]=='O') ||
                       (board[6]=='O' && board[7]=='O' && board[8]=='O') ||
                       (board[0]=='O' && board[3]=='O' && board[6]=='O') ||
                       (board[1]=='O' && board[4]=='O' && board[7]=='O') ||
                       (board[2]=='O' && board[5]=='O' && board[8]=='O') ||
                       (board[0]=='O' && board[4]=='O' && board[8]=='O') ||
                       (board[2]=='O' && board[4]=='O' && board[6]=='O');
        if (oWin) return GamePhase.O_VICTORY;
        // Проверка ничьи
        for (char c : board) if (c == ' ') return GamePhase.IN_PROCESS;
        return GamePhase.DEAD_HEAT;
    }

    // Сбор свободных клеток
    void collectFreeCells(char[] board, ArrayList<Integer> moves) {
        moves.clear();
        for (int i = 0; i < 9; i++) if (board[i] == ' ') moves.add(i);
    }

    // Оценка позиции для игрока
    int assessPosition(char[] board, PlayerData p) {
        GamePhase st = evaluateBoard(board);
        if (st == GamePhase.X_VICTORY) {
            return (p.sign == 'X') ? +MAX_SCORE : -MAX_SCORE;
        } else if (st == GamePhase.O_VICTORY) {
            return (p.sign == 'O') ? +MAX_SCORE : -MAX_SCORE;
        } else if (st == GamePhase.DEAD_HEAT) {
            return 0;
        }
        return -1;
    }

    // Рекурсивный минимум (ход противника)
    int minSearch(char[] board, PlayerData p) {
        int val = assessPosition(board, p);
        if (val != -1) return val;
        positionsChecked++;
        int best = +MAX_SCORE;
        ArrayList<Integer> moves = new ArrayList<>();
        collectFreeCells(board, moves);
        for (int cell : moves) {
            char nextSign = (p.sign == 'X') ? 'O' : 'X';
            board[cell] = nextSign;
            int cur = maxSearch(board, p);
            if (cur < best) best = cur;
            board[cell] = ' ';
        }
        return best;
    }

    // Рекурсивный максимум (ход текущего игрока)
    int maxSearch(char[] board, PlayerData p) {
        int val = assessPosition(board, p);
        if (val != -1) return val;
        positionsChecked++;
        int best = -MAX_SCORE;
        ArrayList<Integer> moves = new ArrayList<>();
        collectFreeCells(board, moves);
        for (int cell : moves) {
            board[cell] = p.sign;
            int cur = minSearch(board, p);
            if (cur > best) best = cur;
            board[cell] = ' ';
        }
        return best;
    }

    // Поиск лучшего хода
    int bestMove(char[] board, PlayerData p) {
        positionsChecked = 0;
        int bestVal = -MAX_SCORE;
        int bestCell = -1;
        ArrayList<Integer> candidates = new ArrayList<>();
        ArrayList<Integer> moves = new ArrayList<>();
        collectFreeCells(board, moves);
        for (int cell : moves) {
            board[cell] = p.sign;
            int cur = minSearch(board, p);
            board[cell] = ' ';
            if (cur > bestVal) {
                bestVal = cur;
                bestCell = cell;
                candidates.clear();
                candidates.add(cell);
            } else if (cur == bestVal) {
                candidates.add(cell);
            }
        }
        if (candidates.size() > 1) {
            Random r = new Random();
            bestCell = candidates.get(r.nextInt(candidates.size()));
        }
        System.out.printf("Лучший ход: %d (оценка %d), позиций: %d\n", bestCell+1, bestVal, positionsChecked);
        return bestCell;
    }
}

class CellButton extends JButton {
    private int index;
    private int row;
    private int col;
    private char mark;

    CellButton(int idx, int x, int y) {
        index = idx;
        row = y;
        col = x;
        mark = ' ';
        setText(" ");
        setFont(new Font("Arial", Font.PLAIN, 40));
    }
    void setMark(String m) {
        mark = m.charAt(0);
        setText(m);
        setEnabled(false);
    }
    char getMark() { return mark; }
    int getRow() { return row; }
    int getCol() { return col; }
    int getIndex() { return index; }
}

class Printer {
    static void show(char[] arr) {
        System.out.println();
        for (char c : arr) System.out.print(c + "-");
        System.out.println();
    }
    static void show(int[] arr) {
        System.out.println();
        for (int v : arr) System.out.print(v + "-");
        System.out.println();
    }
    static void show(ArrayList<Integer> list) {
        System.out.println();
        for (int v : list) System.out.print(v + "-");
        System.out.println();
    }
}

class BoardPanel extends JPanel implements ActionListener {
    private TicTacEngine engine;
    private CellButton[] btns = new CellButton[9];

    private void buildCell(int num, int x, int y) {
        btns[num] = new CellButton(num, x, y);
        btns[num].addActionListener(this);
        add(btns[num]);
    }

    BoardPanel(GridLayout layout) {
        super(layout);
        for (int i = 0; i < 9; i++) buildCell(i, i%3, i/3);
        engine = new TicTacEngine();
        engine.current = engine.playerX;
    }

    private void updateFromModel() {
        for (int i = 0; i < 9; i++) {
            char modelMark = engine.cells[i];
            if (btns[i].getMark() != modelMark) {
                btns[i].setMark(String.valueOf(modelMark));
            }
        }
    }

    private void checkTerminal() {
        engine.phase = engine.evaluateBoard(engine.cells);
        if (engine.phase != GamePhase.IN_PROCESS) {
            String msg = "";
            switch (engine.phase) {
                case X_VICTORY: msg = "Выиграли крестики!"; break;
                case O_VICTORY: msg = "Выиграли нолики!"; break;
                case DEAD_HEAT: msg = "Ничья!"; break;
            }
            JOptionPane.showMessageDialog(this, msg, "Результат", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Ход человека (крестик)
        CellButton src = (CellButton) e.getSource();
        if (src.getMark() != ' ') return;

        src.setMark("X");
        engine.cells[src.getIndex()] = 'X';
        engine.current = engine.playerO;
        updateFromModel();
        checkTerminal();

        // Ход компьютера (нолик)
        SwingUtilities.invokeLater(() -> {
            if (engine.phase == GamePhase.IN_PROCESS) {
                int move = engine.bestMove(engine.cells, engine.playerO);
                if (move >= 0) {
                    engine.cells[move] = 'O';
                    engine.current = engine.playerX;
                    updateFromModel();
                    checkTerminal();
                }
            }
        });
    }
}

public class Program {
    public static void main(String[] args) {
        JFrame window = new JFrame("Крестики-нолики (минимакс)");
        window.add(new BoardPanel(new GridLayout(3,3)));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(50, 50, 450, 450);
        window.setVisible(true);
    }
}
