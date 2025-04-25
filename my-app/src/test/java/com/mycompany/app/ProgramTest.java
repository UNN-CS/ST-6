package com.mycompany.app;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;


class ProgramTest {

    private Game game;
    private Player player1;
    private Player player2;
    private char[] board;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private TicTacToePanel panel;

    @BeforeEach
    void setUp() throws Exception {
        game = new Game();
        player1 = game.player1;
        player2 = game.player2;
        board = new char[9];
        for (int i = 0; i < 9; i++) {
            board[i] = ' ';
        }
        System.setOut(new PrintStream(outContent));

        // Инициализация панели для тестирования
        panel = new TicTacToePanel(new GridLayout(3, 3));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testGameInitialization() {
        assertNotNull(game);
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', player1.symbol);
        assertEquals('O', player2.symbol);
    }

    @Test
    void testCheckStateXWin() {
        // Horizontal win
        char[] xWinBoard = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(xWinBoard));

        // Vertical win
        xWinBoard = new char[]{'X', ' ', ' ', 'X', ' ', ' ', 'X', ' ', ' '};
        assertEquals(State.XWIN, game.checkState(xWinBoard));

        // Diagonal win
        xWinBoard = new char[]{'X', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X'};
        assertEquals(State.XWIN, game.checkState(xWinBoard));
    }

    @Test
    void testCheckStateOWin() {
        // Horizontal win
        char[] oWinBoard = {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(oWinBoard));

        // Vertical win
        oWinBoard = new char[]{'O', ' ', ' ', 'O', ' ', ' ', 'O', ' ', ' '};
        assertEquals(State.OWIN, game.checkState(oWinBoard));

        // Diagonal win
        oWinBoard = new char[]{'O', ' ', ' ', ' ', 'O', ' ', ' ', ' ', 'O'};
        assertEquals(State.OWIN, game.checkState(oWinBoard));
    }

    @Test
    void testCheckStateDraw() {
        char[] drawBoard = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(drawBoard));
    }

    @Test
    void testCheckStatePlaying() {
        char[] playingBoard = {'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(playingBoard));
    }

    @Test
    void testGenerateMoves() {
        board[0] = 'X';
        board[4] = 'O';
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertEquals(7, moves.size());
        assertFalse(moves.contains(0));
        assertFalse(moves.contains(4));
    }

    @Test
    void testEvaluatePositionWin() {
        char[] winBoard = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(Game.INF, game.evaluatePosition(winBoard, player1));
        assertEquals(-Game.INF, game.evaluatePosition(winBoard, player2));
    }

    @Test
    void testEvaluatePositionDraw() {
        char[] drawBoard = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(0, game.evaluatePosition(drawBoard, player1));
        assertEquals(0, game.evaluatePosition(drawBoard, player2));
    }

    @Test
    void testEvaluatePositionPlaying() {
        char[] playingBoard = {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        assertEquals(-1, game.evaluatePosition(playingBoard, player1));
    }

    @Test
    void testTicTacToeCell() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        assertEquals(' ', cell.getMarker());
        assertEquals(0, cell.getNum());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());

        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertFalse(cell.isEnabled());
    }

    @Test
    void testUtilityMethods() {
        // Test with arrays that match the expected size in Utility class
        char[] testBoard = new char[9];
        Utility.print(testBoard);
        assertTrue(outContent.toString().contains("-"));

        int[] testIntArray = new int[9];
        Utility.print(testIntArray);
        assertTrue(outContent.toString().contains("-"));

        ArrayList<Integer> testList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            testList.add(i);
        }
        Utility.print(testList);
        assertTrue(outContent.toString().contains("-"));
    }

    @Test
    void testMiniMax() {
        // Test that minimax returns a valid move (1-9) for empty board
        int move = game.MiniMax(board, player1);
        assertTrue(move >= 1 && move <= 9);

        // Test corner case - when only one move left
        char[] almostFull = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', ' '};
        game.board = almostFull;
        assertEquals(9, game.MiniMax(almostFull, player1));
    }

    @Test
    void testMinMove() {
        // Test min move returns -INF when X can win
        char[] xCanWin = {'X', 'X', ' ', 'O', 'O', ' ', ' ', ' ', ' '};
        game.board = xCanWin;
        game.symbol = 'X';
        assertEquals(-Game.INF, game.MinMove(xCanWin, player1));
    }

    @Test
    void testMaxMove() {
        // Test max move returns INF when O can win
        char[] oCanWin = {'O', 'O', ' ', 'X', 'X', ' ', ' ', ' ', ' '};
        game.board = oCanWin;
        game.symbol = 'O';
        assertEquals(Game.INF, game.MaxMove(oCanWin, player2));
    }

    @Test
    void testTicTacToePanelInitialization() throws Exception {
        // Проверка инициализации панели
        Field cellsField = TicTacToePanel.class.getDeclaredField("cells");
        cellsField.setAccessible(true);
        TicTacToeCell[] cells = (TicTacToeCell[]) cellsField.get(panel);

        assertEquals(9, cells.length);
        for (int i = 0; i < 9; i++) {
            assertNotNull(cells[i]);
            assertEquals(i, cells[i].getNum());
        }
    }

    @Test
    void testPanelCellClickHandling() throws Exception {
        // Проверка обработки кликов по клеткам
        Field cellsField = TicTacToePanel.class.getDeclaredField("cells");
        cellsField.setAccessible(true);
        TicTacToeCell[] cells = (TicTacToeCell[]) cellsField.get(panel);

        // Имитируем клик по первой клетке
        cells[0].doClick();

        // Проверяем что клетка изменилась
        assertEquals('X', cells[0].getMarker());
        assertFalse(cells[0].isEnabled());
    }

    @Test
    void testGameStateAfterMoves() throws Exception {
        // Проверка состояния игры после ходов
        Field cellsField = TicTacToePanel.class.getDeclaredField("cells");
        cellsField.setAccessible(true);
        TicTacToeCell[] cells = (TicTacToeCell[]) cellsField.get(panel);

        // Получаем доступ к game из панели
        Field gameField = TicTacToePanel.class.getDeclaredField("game");
        gameField.setAccessible(true);
        Game panelGame = (Game) gameField.get(panel);

        // Делаем несколько ходов
        cells[0].doClick(); // X
        cells[1].doClick(); // O (компьютер)

        // Проверяем состояние игры
        assertEquals(State.PLAYING, panelGame.state);
    }

    @Test
    void testPanelIntegrationWithGame() throws Exception {
        // Проверка интеграции панели с игровой логикой
        Field cellsField = TicTacToePanel.class.getDeclaredField("cells");
        cellsField.setAccessible(true);
        TicTacToeCell[] cells = (TicTacToeCell[]) cellsField.get(panel);

        Field gameField = TicTacToePanel.class.getDeclaredField("game");
        gameField.setAccessible(true);
        Game panelGame = (Game) gameField.get(panel);

        // Проверяем начальное состояние
        assertEquals(State.PLAYING, panelGame.state);
        assertEquals('X', panelGame.player1.symbol);
        assertEquals('O', panelGame.player2.symbol);

        // Делаем ход
        cells[0].doClick();

        // Проверяем что состояние игры обновилось
        assertEquals('X', panelGame.board[0]);
        assertEquals(State.PLAYING, panelGame.state);
    }

    @Test
    void testTicTacToePanelInFrame() {
        // Интеграционный тест с JFrame
        JFrame frame = new JFrame();
        frame.add(panel);
        frame.setSize(300, 300);
        frame.setVisible(true);

        // Простая проверка что компоненты отображаются
        assertTrue(panel.isVisible());
        assertEquals(9, panel.getComponentCount());

        frame.dispose();
    }
}