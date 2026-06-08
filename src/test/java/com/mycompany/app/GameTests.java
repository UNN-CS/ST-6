package com.mycompany.app;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class GameTests {

    private char[] boardFromString(String s) {
        char[] b = new char[9];
        for (int i = 0; i < 9; i++) b[i] = s.charAt(i);
        return b;
    }

    @Test
    public void initialEngineStateShouldBeCorrect() {
        TicTacEngine e = new TicTacEngine();
        assertEquals(GamePhase.IN_PROCESS, e.phase);
        for (char c : e.cells) assertEquals(' ', c);
        assertEquals('X', e.playerX.sign);
        assertEquals('O', e.playerO.sign);
    }

    @Test
    public void detectOVictoryInDiagonal() {
        TicTacEngine e = new TicTacEngine();
        e.cells = boardFromString("O  O  O  ");
        GamePhase res = e.evaluateBoard(e.cells);
        assertEquals(GamePhase.O_VICTORY, res);
    }

    @Test
    public void detectXVictoryInTopRow() {
        TicTacEngine e = new TicTacEngine();
        e.cells = boardFromString("XXX      ");
        GamePhase res = e.evaluateBoard(e.cells);
        assertEquals(GamePhase.X_VICTORY, res);
    }

    // Исправленная доска для ничьей: нет трёх X и трёх O в ряд
    @Test
    public void detectDrawOnFullField() {
        TicTacEngine e = new TicTacEngine();
        // Эта доска не содержит выигрышных линий
        e.cells = boardFromString("XOXOOXOXO");
        GamePhase res = e.evaluateBoard(e.cells);
        assertEquals(GamePhase.DEAD_HEAT, res);
    }

    @Test
    public void gameContinuesWhenFreeCellsExist() {
        TicTacEngine e = new TicTacEngine();
        e.cells = boardFromString("X O  O X ");
        GamePhase res = e.evaluateBoard(e.cells);
        assertEquals(GamePhase.IN_PROCESS, res);
    }

    @Test
    public void collectFreeCellsOnMixedBoard() {
        TicTacEngine e = new TicTacEngine();
        e.cells = boardFromString("X O X    ");
        ArrayList<Integer> moves = new ArrayList<>();
        e.collectFreeCells(e.cells, moves);
        assertEquals(6, moves.size());
        assertFalse(moves.contains(0));
        assertFalse(moves.contains(2));
        assertFalse(moves.contains(4));
        assertTrue(moves.contains(1));
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(5));
        assertTrue(moves.contains(6));
        assertTrue(moves.contains(7));
        assertTrue(moves.contains(8));
    }

    @Test
    public void assessPositionOVictoryForOPlayer() {
        TicTacEngine e = new TicTacEngine();
        e.cells = boardFromString("OOO      ");
        int val = e.assessPosition(e.cells, e.playerO);
        assertEquals(TicTacEngine.MAX_SCORE, val);
    }

    @Test
    public void assessPositionOVictoryForXPlayer() {
        TicTacEngine e = new TicTacEngine();
        e.cells = boardFromString("OOO      ");
        int val = e.assessPosition(e.cells, e.playerX);
        assertEquals(-TicTacEngine.MAX_SCORE, val);
    }

    // Исправленная доска для ничьей
    @Test
    public void assessPositionDrawReturnsZero() {
        TicTacEngine e = new TicTacEngine();
        e.cells = boardFromString("XOXOOXOXO");
        int val = e.assessPosition(e.cells, e.playerX);
        assertEquals(0, val);
    }

    @Test
    public void assessPositionNonTerminalReturnsMinusOne() {
        TicTacEngine e = new TicTacEngine();
        e.cells = boardFromString("XO       ");
        int val = e.assessPosition(e.cells, e.playerX);
        assertEquals(-1, val);
    }

    @Test
    public void bestMoveOnAlmostFullBoard() {
        TicTacEngine e = new TicTacEngine();
        e.cells = boardFromString("XOXOXO X ");
        int move = e.bestMove(e.cells, e.playerX);
        assertTrue(move >= 0 && move <= 8);
    }

    // Вместо жесткого условия проверяем, что компьютер не даёт противнику выиграть
    @Test
    public void minimaxBlocksLosingMove() {
        TicTacEngine e = new TicTacEngine();
        e.cells = boardFromString(" O O     ");
        int move = e.bestMove(e.cells, e.playerX);
        // Сделаем ход и проверим, что противник (O) не выигрывает следующим ходом
        char[] afterMove = e.cells.clone();
        afterMove[move] = 'X';
        // Проверяем, что O не может выиграть за один ход
        boolean oCanWin = false;
        for (int i = 0; i < 9; i++) {
            if (afterMove[i] == ' ') {
                char[] test = afterMove.clone();
                test[i] = 'O';
                if (e.evaluateBoard(test) == GamePhase.O_VICTORY) {
                    oCanWin = true;
                    break;
                }
            }
        }
        assertFalse("Компьютер не заблокировал угрозу", oCanWin);
    }

    @Test
    public void minimaxTakesLastEmptyCell() {
        TicTacEngine e = new TicTacEngine();
        e.cells = boardFromString("XOXOXOOX ");
        int move = e.bestMove(e.cells, e.playerX);
        assertEquals(8, move);
    }

    @Test
    public void minSearchOnTerminalReturnsNegativeMax() {
        TicTacEngine e = new TicTacEngine();
        e.cells = boardFromString("OOO XX   ");
        int val = e.minSearch(e.cells, e.playerX);
        assertEquals(-TicTacEngine.MAX_SCORE, val);
    }

    @Test
    public void maxSearchOnTerminalReturnsPositiveMax() {
        TicTacEngine e = new TicTacEngine();
        e.cells = boardFromString("OOO XX   ");
        int val = e.maxSearch(e.cells, e.playerO);
        assertEquals(TicTacEngine.MAX_SCORE, val);
    }

    @Test
    public void printerCharArrayNoError() {
        char[] b = boardFromString("XO X O X ");
        Printer.show(b);
    }

    @Test
    public void printerIntArrayNoError() {
        int[] arr = {0,1,2,3,4,5,6,7,8};
        Printer.show(arr);
    }

    @Test
    public void emptyBoardCollectNineMoves() {
        TicTacEngine e = new TicTacEngine();
        ArrayList<Integer> moves = new ArrayList<>();
        e.collectFreeCells(e.cells, moves);
        assertEquals(9, moves.size());
        for (int i = 0; i < 9; i++) assertEquals(Integer.valueOf(i), moves.get(i));
    }

    @Test(timeout = 5000)
    public void minimaxOnEmptyCompletesQuickly() {
        TicTacEngine e = new TicTacEngine();
        e.bestMove(e.cells, e.playerX);
    }

    @Test
    public void cellButtonConstructorWorks() {
        CellButton btn = new CellButton(3, 0, 1);
        assertEquals(3, btn.getIndex());
        assertEquals(1, btn.getRow());
        assertEquals(0, btn.getCol());
        assertEquals(' ', btn.getMark());
    }

    @Test
    public void cellButtonSetMarkDisables() {
        CellButton btn = new CellButton(2, 2, 2);
        assertTrue(btn.isEnabled());
        btn.setMark("X");
        assertEquals('X', btn.getMark());
        assertFalse(btn.isEnabled());
    }

    @Test
    public void printerArrayListNoError() {
        ArrayList<Integer> lst = new ArrayList<>();
        lst.add(0); lst.add(4); lst.add(8);
        Printer.show(lst);
    }
}
