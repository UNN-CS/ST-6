package org.example;



import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest 
{
    @Test
    public void PlayerTest() {
        Player player = new Player();
        player.symbol='X';
        player.move=1;
        player.selected=false;
        player.win=false;
        assertEquals('X', player.symbol);
        assertEquals(1, player.move);
        assertEquals(false, player.selected);
        assertEquals(false, player.win);
    }

    @Test
    public void GameTest() {
        Game game = new Game();
        game.state = State.PLAYING;
        game.cplayer=game.player1;
        game.nmove=1;
        game.symbol=' ';
        game.q=1;
        assertEquals(State.PLAYING, game.state);
        assertEquals(game.player1, game.cplayer);
        assertEquals(1, game.nmove);
        assertEquals(' ', game.symbol);
        assertEquals(1, game.q);
        for(int i=0;i<9;i++)
            assertEquals(' ', game.board[i]);
    }

    @Test
    public void checkStateXWIN() {
        Game game = new Game();
        game.symbol = 'X';
        game.board = new char[]{'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        State expectedState = State.XWIN;
        State actualState = game.checkState(game.board);
        assertEquals(expectedState, actualState);
    }

    @Test
    public void checkStateOWIN() {
        Game game = new Game();
        game.symbol = 'O';
        game.board = new char[]{'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        State expectedState = State.OWIN;
        State actualState = game.checkState(game.board);
        assertEquals(expectedState, actualState);
    }

    @Test
    public void checkStateDRAW() {
        Game game = new Game();
        game.symbol = 'X';
        game.board = new char[]{'O', 'X', 'O', 'X', 'X', 'O', 'X', 'O', 'X'};
        State expectedState = State.DRAW;
        State actualState = game.checkState(game.board);
        assertEquals(expectedState, actualState);
    }

    @Test
    public void checkStatePLAYING() {
        Game game = new Game();
        game.symbol = 'X';
        game.board = new char[]{'X', 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        State expectedState = State.PLAYING;
        State actualState = game.checkState(game.board);
        assertEquals(expectedState, actualState);
    }
    @Test
    public void generateMoves() {
        Game game = new Game();
        ArrayList<Integer> move_list = new ArrayList<>();
        game.board = new char[]{'X', 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        game.generateMoves(game.board, move_list);
        ArrayList<Integer> expectedMoves = new ArrayList<>();
        expectedMoves.add(2);
        expectedMoves.add(3);
        expectedMoves.add(4);
        expectedMoves.add(5);
        expectedMoves.add(6);
        expectedMoves.add(7);
        expectedMoves.add(8);
        assertEquals(expectedMoves, move_list);
    }
    @Test
    public void evaluatePositionXWIN() {
        Game game = new Game();
        game.symbol = 'X';
        game.board = new char[]{'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '};
        int expectedValue = Game.INF;
        int actualValue = game.evaluatePosition(game.board, game.player1);
        assertEquals(expectedValue, actualValue);
    }
    @Test
    public void evaluatePositionOWIN() {
        Game game = new Game();
        game.symbol = 'O';
        game.board = new char[]{'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '};
        int expectedValue = Game.INF;
        int actualValue = game.evaluatePosition(game.board, game.player2);
        assertEquals(expectedValue, actualValue);
    }
    @Test
    public void evaluatePositionDRAW() {
        Game game = new Game();
        game.symbol = 'X';
        game.board = new char[]{'O', 'X', 'O', 'X', 'X', 'O', 'X', 'O', 'X'};
        int expectedValue = 0;
        int actualValue = game.evaluatePosition(game.board, game.player1);
        assertEquals(expectedValue, actualValue);
    }
    @Test
    public void evaluatePositionPLAYING() {
        Game game = new Game();
        game.symbol = 'X';
        game.board = new char[]{'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        int expectedValue = -1;
        int actualValue = game.evaluatePosition(game.board, game.player1);
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void TicTacToeCellTest() {
        TicTacToeCell cell = new TicTacToeCell(1,1,1);
        assertEquals(1, cell.getNum());
        assertEquals(1, cell.getRow());
        assertEquals(1, cell.getCol());
        assertEquals(' ', cell.getMarker());
    }
    @Test
    public void setMarkerTest() {
        TicTacToeCell cell = new TicTacToeCell(1, 1, 1);
        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
    }
    @Test
    public void printBoardTest() {
        char[] testBoard = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        Utility.print(testBoard);
    }

    @Test
    public void printMovesTest() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(2);
        moves.add(3);
        Utility.print(moves);
    }

}
