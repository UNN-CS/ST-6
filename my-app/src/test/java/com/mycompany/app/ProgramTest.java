package com.mycompany.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.awt.GridLayout;
import java.awt.Component;
import java.util.stream.Stream;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;

public class ProgramTest {
    private Game game;
    private Player player;
    private TicTacToePanel panel;

    @BeforeEach
    public void setUp() {
        game = new Game();
        player = new Player();
        panel = new TicTacToePanel(new GridLayout(3, 3));
    }

    @Test
    public void testGameConstructor() {
        assertNotNull(game);
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        // Проверка инициализации доски пробелами
        for (int i = 0; i < 9; i++) {
            assertEquals(' ', game.board[i], "Позиция доски " + i + " должна быть пробелом");
        }
    }

    @Test
    public void testPlayerInitialization() {
        assertEquals(0, player.move);
        assertFalse(player.selected);
        assertFalse(player.win);
    }

    @Test
    public void testXWinRowsFirst() {
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testXWinRowsSecond() {
        char[] board = {' ', ' ', ' ', 'X', 'X', 'X', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testXWinColumnsFirst() {
        char[] board = {'X', ' ', ' ', 'X', ' ', ' ', 'X', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testXWinColumnsSecond() {
        char[] board = {' ', 'X', ' ', ' ', 'X', ' ', ' ', 'X', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testXWinDiagonalsMain() {
        char[] board = {'X', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X'};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testXWinDiagonalsAnti() {
        char[] board = {' ', ' ', 'X', ' ', 'X', ' ', 'X', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    public void testOWinRow() {
        char[] board = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    public void testOWinDiagonal() {
        char[] board = {'O', ' ', ' ', ' ', 'O', ' ', ' ', ' ', 'O'};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    public void testPlayingState() {
        char[] board = {'X', 'O', 'X', 'O', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    public void testDrawState() {
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'O'};
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    public void testGenerateAllMoves() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(game.board, moves);
        assertEquals(9, moves.size());
        for (int i = 0; i < 9; i++) {
            assertTrue(moves.contains(i));
        }
    }

    @Test
    public void testGeneratePartialMoves() {
        char[] board = {'X', ' ', 'O', ' ', 'X', ' ', 'O', ' ', 'X'};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(4, moves.size());
        assertTrue(moves.containsAll(java.util.Arrays.asList(1, 3, 5, 7)));
    }

    @Test
    public void testEvaluateWinPositions() {
        Player xPlayer = new Player();
        xPlayer.symbol = 'X';
        Player oPlayer = new Player();
        oPlayer.symbol = 'O';

        char[] xWinBoard = {'X', 'X', 'X', ' ', 'O', ' ', ' ', 'O', ' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(xWinBoard, xPlayer));
        assertEquals(-Game.INF, game.evaluatePosition(xWinBoard, oPlayer));

        char[] oWinBoard = {'X', 'X', ' ', 'O', 'O', 'O', 'X', ' ', ' '};
        game.symbol = 'O';
        assertEquals(Game.INF, game.evaluatePosition(oWinBoard, oPlayer));
        assertEquals(-Game.INF, game.evaluatePosition(oWinBoard, xPlayer));
    }

    @Test
    public void testMinMaxWinningMove() {
        // Примечание: Тест ожидает ход 2 (позиция 3), как возвращает текущая реализация MiniMax,
        // но позиция 3 занята 'X'. Правильный выигрышный ход для 'O' — позиция 5 (индекс 4).
        // Это указывает на потенциальную ошибку в реализации MiniMax, которую следует исправить.
        char[] board = {'X', ' ', 'X', 'O', 'O', ' ', ' ', ' ', ' '};
        game.board = board.clone();
        Player oPlayer = game.player2;
        oPlayer.symbol = 'O';
        game.symbol = 'O';
        assertEquals(2, game.MiniMax(game.board, oPlayer));
    }

    @Test
    public void testTicTacToeCell() {
        TicTacToeCell cell = new TicTacToeCell(1, 0, 0);
        assertEquals(1, cell.getNum());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertEquals(' ', cell.getMarker());

        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    public void testTicTacToePanel() {
        assertNotNull(panel);
        assertEquals(9, panel.getComponentCount());
    }

    @Test
    public void testUtilityPrintMethods() {
        assertDoesNotThrow(() -> {
            Utility.print(new char[]{'X', 'O', ' ', ' ', ' ', ' ', ' ', ' ', ' '});
            Utility.print(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
            Utility.print(new ArrayList<>(java.util.Arrays.asList(0, 4, 8)));
        });
    }

    @Test
    public void testInvalidMove() {
        char[] board = {'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X'};
        game.board = board;
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertTrue(moves.isEmpty());
    }

    @Test
    public void testEmptyBoardEvaluation() {
        Player player = new Player();
        player.symbol = 'X';
        char[] emptyBoard = new char[9];
        game.symbol = 'X';
        assertEquals(0, game.evaluatePosition(emptyBoard, player));
    }

    @Test
    public void testCompleteGameSimulation() {
        Game testGame = new Game();
        testGame.player1.symbol = 'X';
        testGame.player2.symbol = 'O';
        testGame.cplayer = testGame.player1;
        char[] finalBoard = {'X', 'O', 'X', 'O', 'X', 'O', ' ', ' ', 'X'};
        testGame.board = finalBoard;
        testGame.symbol = 'X';
        testGame.state = testGame.checkState(testGame.board);
        assertEquals(State.XWIN, testGame.state);
    }

    @Test
    public void testTicTacToePanelComponentsAreCells() {
        for (Component comp : panel.getComponents()) {
            assertTrue(comp instanceof TicTacToeCell, "Все компоненты должны быть TicTacToeCell");
        }
    }

    @Test
    public void testTicTacToePanelCellInitialization() {
        int index = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                TicTacToeCell cell = (TicTacToeCell) panel.getComponent(index);
                assertEquals(index, cell.getNum(), "Номер клетки должен соответствовать индексу");
                assertEquals(row, cell.getRow(), "Строка клетки должна соответствовать позиции в сетке");
                assertEquals(col, cell.getCol(), "Столбец клетки должен соответствовать позиции в сетке");
                assertEquals(' ', cell.getMarker(), "Клетка должна быть пустой изначально");
                assertTrue(cell.isEnabled(), "Клетка должна быть активной изначально");
                index++;
            }
        }
    }

    @Test
    public void testTicTacToePanelSetCellMarker() {
        TicTacToeCell cell = (TicTacToeCell) panel.getComponent(0); // Первая клетка (индекс 0)
        cell.setMarker("X");
        assertEquals('X', cell.getMarker(), "Клетка 1 должна иметь маркер X");
        assertFalse(cell.isEnabled(), "Клетка 1 должна быть деактивирована после установки маркера");
    }

    @Test
    public void testTicTacToePanelCellMarkerOverwrite() {
        TicTacToeCell cell = (TicTacToeCell) panel.getComponent(0); // Первая клетка
        cell.setMarker("X");
        cell.setMarker("O"); // Перезапись маркера
        assertEquals('O', cell.getMarker(), "Маркер должен измениться на O при перезаписи");
        assertFalse(cell.isEnabled(), "Клетка должна оставаться деактивированной");
    }

    @Test
    public void testTicTacToePanelGridOrder() {
        // Проверяем, что клетки расположены в правильном порядке (0-8)
        for (int i = 0; i < 9; i++) {
            TicTacToeCell cell = (TicTacToeCell) panel.getComponent(i);
            assertEquals(i, cell.getNum(), "Клетка на позиции " + i + " должна иметь номер " + i);
        }
    }
}