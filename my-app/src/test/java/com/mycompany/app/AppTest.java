package com.mycompany.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.awt.*;
import javax.swing.*;
import java.util.Arrays;

class AppTest {


    @Test
    void checkState_XWinsHorizontal_ReturnsXWin() {
        Game game = new Game();
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void checkState_OWinsVertical_ReturnsOWin() {
        Game game = new Game();
        char[] board = {'O', ' ', ' ', 'O', ' ', ' ', 'O', ' ', ' '};
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void checkState_Draw_ReturnsDraw() {
        Game game = new Game();
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.symbol = 'X';
        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    void evaluatePosition_XWins_ReturnsInf() {
        Game game = new Game();
        char[] board = {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        game.symbol = 'X';
        int result = game.evaluatePosition(board, game.player1);
        assertEquals(Game.INF, result);
    }

    @Test
    void generateMoves_FullBoard_ReturnsEmptyList() {
        Game game = new Game();
        char[] board = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'X'};
        ArrayList<Integer> moves = new ArrayList<>();
        game.generateMoves(board, moves);
        assertTrue(moves.isEmpty());
    }

    @Test
    void checkState_ContinuePlaying_ReturnsPlaying() {
        Game game = new Game();
        char[] board = {
                'X', 'O', ' ',
                ' ', 'X', ' ',
                ' ', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    void evaluatePosition_Draw_ReturnsZero() {
        Game game = new Game();
        char[] board = {
                'X', 'O', 'X',
                'X', 'O', 'O',
                'O', 'X', 'X'
        };
        game.symbol = 'X';
        int result = game.evaluatePosition(board, game.player1);
        assertEquals(0, result);
    }

    @Test
    void MaxMove_ReturnsOptimalValue() {
        Game game = new Game();
        char[] board = {
                'X', ' ', ' ',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        game.symbol = 'X';
        int result = game.MaxMove(board, game.player1);
        assertEquals(Game.INF, result);
    }

    // Тесты для Game initialization
    @Test
    void gameInitialization_DefaultValues() {
        Game game = new Game();
        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
        assertTrue(game.board.length == 9);
    }

    // Тесты для проверки победы по диагонали
    @Test
    void checkState_OWinsDiagonal_ReturnsOWin() {
        Game game = new Game();
        char[] board = {
                'O', ' ', ' ',
                ' ', 'O', ' ',
                ' ', ' ', 'O'
        };
        game.symbol = 'O';
        assertEquals(State.OWIN, game.checkState(board));
    }

    // Тест для незавершенной игры
    @Test
    void checkState_PlayingWhenEmptyCellsExist() {
        Game game = new Game();
        char[] board = {
                'X', 'O', ' ',
                ' ', 'X', ' ',
                ' ', ' ', ' '
        };
        game.symbol = 'X';
        assertEquals(State.PLAYING, game.checkState(board));
    }

    // Тест оценки незавершенной позиции
    @Test
    void evaluatePosition_UnfinishedGame_ReturnsMinusOne() {
        Game game = new Game();
        char[] board = {
                'X', 'O', ' ',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        game.symbol = 'X';
        int result = game.evaluatePosition(board, game.player1);
        assertEquals(-1, result);
    }

    // Тест оптимального хода для победы
    @Test
    void MaxMove_ReturnsWinningMoveValue() {
        Game game = new Game();
        char[] board = {
                'X', 'X', ' ',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        game.symbol = 'X';
        int result = game.MaxMove(board, game.player1);
        assertEquals(Game.INF, result);
    }

    // Тест состояния ячейки после хода
    @Test
    void TicTacToeCell_SetMarker_DisablesButtonAndSetsText() {
        TicTacToeCell cell = new TicTacToeCell(0, 0, 0);
        cell.setMarker("X");
        assertFalse(cell.isEnabled());
        assertEquals('X', cell.getMarker());
    }

    // Дополнения к классу AppTest
    @Test
    void TicTacToeCell_GettersReturnCorrectValues() {
        TicTacToeCell cell = new TicTacToeCell(5, 2, 1);
        assertEquals(5, cell.getNum());
        assertEquals(2, cell.getCol());
        assertEquals(1, cell.getRow());
        assertEquals(' ', cell.getMarker());
    }

    @Test
    void TicTacToePanel_InitializationCreates9Cells() {
        TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
        assertEquals(9, panel.getComponentCount());
        for (Component comp : panel.getComponents()) {
            assertTrue(comp instanceof TicTacToeCell);
        }
    }

    @Test
    void Game_MinMove_ReturnsCorrectValue() {
        Game game = new Game();
        char[] board = {
                'X', ' ', ' ',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };
        game.symbol = 'X';
        int result = game.MinMove(board, game.player1);
        assertTrue(result < Game.INF);
    }

    // Тесты для Program (интеграционные)
    @Test
    void Program_MainStartsWithoutErrors() {
        assertDoesNotThrow(() -> Program.main(new String[0]));
    }

    // Дополнения для PlayerTest
    @Test
    void Player_SetSymbolChangesValue() {
        Player player = new Player();
        player.symbol = 'Z';
        assertEquals('Z', player.symbol);
    }

}

class PlayerTest {
    @Test
    void playerInitialization_DefaultValues() {
        Player player = new Player();
        assertFalse(player.selected);
        assertFalse(player.win);
        assertEquals(0, player.move);
    }
}