// Крестики-нолики 3x3 — алгоритм минимакс

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

enum Outcome { PLAYING, OWIN, XWIN, DRAW }

class Participant {
    char mark;
    int lastIndex;
    boolean picked;
    boolean victorious;
}

class MatchEngine {
    Outcome outcome;
    Participant humanSide, cpuSide;
    Participant activeSide;
    int recentStep;
    char lastMark;
    static final int BOUND = 100;
    int stepCounter;
    char[] grid;

    MatchEngine() {
        humanSide = new Participant();
        cpuSide = new Participant();
        humanSide.mark = 'X';
        cpuSide.mark = 'O';
        outcome = Outcome.PLAYING;
        grid = new char[9];
        for (int idx = 0; idx < 9; idx++) {
            grid[idx] = ' ';
        }
    }

    Outcome resolveOutcome(char[] cells) {
        Outcome result = Outcome.PLAYING;
        if (lineComplete(cells, lastMark)) {
            result = (lastMark == 'X') ? Outcome.XWIN : Outcome.OWIN;
        } else {
            result = Outcome.DRAW;
            for (int idx = 0; idx < 9; idx++) {
                if (cells[idx] == ' ') {
                    result = Outcome.PLAYING;
                    break;
                }
            }
        }
        return result;
    }

    private boolean lineComplete(char[] cells, char mark) {
        int[][] lines = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
        };
        for (int[] line : lines) {
            if (cells[line[0]] == mark && cells[line[1]] == mark && cells[line[2]] == mark) {
                return true;
            }
        }
        return false;
    }

    void collectFreeSlots(char[] cells, ArrayList<Integer> slots) {
        for (int idx = 0; idx < 9; idx++) {
            if (cells[idx] == ' ') {
                slots.add(idx);
            }
        }
    }

    int scoreBoard(char[] cells, Participant side) {
        Outcome state = resolveOutcome(cells);
        if (state == Outcome.XWIN || state == Outcome.OWIN || state == Outcome.DRAW) {
            if ((state == Outcome.XWIN && side.mark == 'X')
                    || (state == Outcome.OWIN && side.mark == 'O')) {
                return +MatchEngine.BOUND;
            }
            if ((state == Outcome.XWIN && side.mark == 'O')
                    || (state == Outcome.OWIN && side.mark == 'X')) {
                return -MatchEngine.BOUND;
            }
            if (state == Outcome.DRAW) {
                return 0;
            }
        }
        return -1;
    }

    int pickOptimalMove(char[] cells, Participant side) {
        int peak = -MatchEngine.BOUND;
        int tieCount = 0;
        ArrayList<Integer> options = new ArrayList<>();
        int[] tied = new int[9];

        collectFreeSlots(cells, options);

        while (!options.isEmpty()) {
            int slot = options.get(0);
            cells[slot] = side.mark;
            lastMark = side.mark;

            int rating = minLayer(cells, side);

            if (rating > peak) {
                peak = rating;
                tieCount = 0;
                tied[tieCount] = slot + 1;
            } else if (rating == peak) {
                tied[++tieCount] = slot + 1;
            }

            System.out.printf("\nminimax: %3d(%1d) ", slot + 1, rating);
            cells[slot] = ' ';
            options.remove(0);
        }

        if (tieCount > 0) {
            tieCount = new Random().nextInt(tieCount);
        }

        System.out.printf("\nminimax best: %3d(%1d) ", tied[tieCount], peak);
        System.out.printf("Steps counted: %d", stepCounter);
        stepCounter = 0;
        return tied[tieCount];
    }

    int minLayer(char[] cells, Participant side) {
        int rated = scoreBoard(cells, side);
        if (rated != -1) {
            return rated;
        }
        stepCounter++;
        int floor = +MatchEngine.BOUND;
        ArrayList<Integer> options = new ArrayList<>();
        collectFreeSlots(cells, options);

        while (!options.isEmpty()) {
            lastMark = (side.mark == 'X') ? 'O' : 'X';
            int slot = options.get(0);
            cells[slot] = lastMark;
            floor = Math.min(floor, maxLayer(cells, side));
            cells[slot] = ' ';
            options.remove(0);
        }
        return floor;
    }

    int maxLayer(char[] cells, Participant side) {
        int rated = scoreBoard(cells, side);
        if (rated != -1) {
            return rated;
        }
        stepCounter++;
        int ceiling = -MatchEngine.BOUND;
        ArrayList<Integer> options = new ArrayList<>();
        collectFreeSlots(cells, options);

        while (!options.isEmpty()) {
            lastMark = (side.mark == 'X') ? 'X' : 'O';
            int slot = options.get(0);
            cells[slot] = lastMark;
            ceiling = Math.max(ceiling, minLayer(cells, side));
            cells[slot] = ' ';
            options.remove(0);
        }
        return ceiling;
    }
}

public class Program {

    public static FileWriter fileWriter;
    public static PrintWriter printWriter;

    public static void main(String[] args) throws IOException {
        JFrame window = new JFrame("Крестики-нолики");
        window.add(new BoardPanel(new GridLayout(3, 3, 6, 6)));
        window.getContentPane().setBackground(new Color(30, 30, 46));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(100, 80, 520, 560);
        window.setResizable(false);
        window.setVisible(true);
    }
}

class GridButton extends JButton {
    private int index;
    private int rowPos;
    private int colPos;
    private char content;

    GridButton(int index, int col, int row) {
        this.index = index;
        rowPos = row;
        colPos = col;
        content = ' ';
        setText(" ");
        setFont(new Font("Segoe UI", Font.BOLD, 48));
        setBackground(new Color(49, 50, 68));
        setForeground(new Color(205, 214, 244));
        setFocusPainted(false);
        setBorder(BorderFactory.createLineBorder(new Color(88, 91, 112), 2));
    }

    void placeMark(String text) {
        content = text.charAt(0);
        setText(text);
        setEnabled(false);
        setForeground(content == 'X'
            ? new Color(243, 139, 168)
            : new Color(137, 180, 250));
    }

    char readMark() {
        return content;
    }

    int getRowPos() {
        return rowPos;
    }

    int getColPos() {
        return colPos;
    }

    int getIndex() {
        return index;
    }
}

class DebugPrinter {
    static void dump(char[] cells) {
        System.out.println();
        for (int j = 0; j < 9; j++) {
            System.out.print(cells[j] + "-");
        }
        System.out.println();
    }

    static void dump(int[] cells) {
        System.out.println();
        for (int j = 0; j < 9; j++) {
            System.out.print(cells[j] + "-");
        }
        System.out.println();
    }

    static void dump(ArrayList<Integer> moves) {
        System.out.println();
        for (int j = 0; j < moves.size(); j++) {
            System.out.print(moves.get(j) + "-");
        }
        System.out.println();
    }
}

class BoardPanel extends JPanel implements ActionListener {

    private final MatchEngine engine;
    private final GridButton[] slots = new GridButton[9];

    BoardPanel(GridLayout layout) {
        super(layout);
        setBackground(new Color(30, 30, 46));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        spawnSlot(0, 0, 0);
        spawnSlot(1, 1, 0);
        spawnSlot(2, 2, 0);
        spawnSlot(3, 0, 1);
        spawnSlot(4, 1, 1);
        spawnSlot(5, 2, 1);
        spawnSlot(6, 0, 2);
        spawnSlot(7, 1, 2);
        spawnSlot(8, 2, 2);
        engine = new MatchEngine();
        engine.activeSide = engine.humanSide;
    }

    private void spawnSlot(int num, int x, int y) {
        slots[num] = new GridButton(num, x, y);
        slots[num].addActionListener(this);
        add(slots[num]);
    }

    public void actionPerformed(ActionEvent event) {
        engine.humanSide.lastIndex = -1;
        engine.cpuSide.lastIndex = -1;

        int pos = 0;
        for (GridButton btn : slots) {
            if (event.getSource() == btn) {
                btn.placeMark(Character.toString(engine.activeSide.mark));
            }
            engine.grid[pos++] = btn.readMark();
        }

        if (engine.activeSide == engine.humanSide) {
            engine.cpuSide.lastIndex = engine.pickOptimalMove(engine.grid, engine.cpuSide);
            engine.recentStep = engine.cpuSide.lastIndex;
            engine.lastMark = engine.cpuSide.mark;
            engine.activeSide = engine.cpuSide;
            if (engine.cpuSide.lastIndex > 0) {
                slots[engine.cpuSide.lastIndex - 1].doClick();
            }
        } else {
            engine.recentStep = engine.humanSide.lastIndex;
            engine.lastMark = engine.humanSide.mark;
            engine.activeSide = engine.humanSide;
        }

        engine.outcome = engine.resolveOutcome(engine.grid);

        if (engine.outcome == Outcome.XWIN) {
            JOptionPane.showMessageDialog(null, "Победа крестиков!", "Итог", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } else if (engine.outcome == Outcome.OWIN) {
            JOptionPane.showMessageDialog(null, "Победа ноликов!", "Итог", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } else if (engine.outcome == Outcome.DRAW) {
            JOptionPane.showMessageDialog(null, "Ничья!", "Итог", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
}
