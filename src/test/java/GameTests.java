// Copyright 2024 by Nedelin Dmitry
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class GameTests {
    Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testConditions() {
        assertEquals(Condition.PLAYING, game.condition);
        assertEquals('X', game.PC1.symbol);
        assertEquals('O', game.PC2.symbol);
        assertEquals(' ', game.field[0]);
    }

    @Test
    public void testCheckConditions() {
        Condition condition = game.checkState(game.field);
        assertEquals(Condition.PLAYING, condition);
    }

    @Test
    public void testCheckConditionXWin() {
        game.symbol = 'X';
        char[] field = {'X', 'X', 'X', 'O', 'O', ' ', ' ', ' '};
        Condition condition = game.checkState(field);
        assertEquals(Condition.XWIN, condition);
    }

    @Test
    public void testCheckConditionOWin() {
        game.symbol = 'O';
        char[] field = {'O', 'O', 'O', 'X', 'X', ' ', 'X', 'X', ' '};
        Condition condition = game.checkState(field);
        assertEquals(Condition.OWIN, condition);
    }

    @Test
    public void testCheckConditionDrawing() {
        game.symbol = 'X';
        char[] field = {'X', 'X', 'O', 'O', 'O', 'X', 'O', 'X', 'O'};
        Condition condition = game.checkState(field);
        assertEquals(Condition.DRAW, condition);
    }

    @Test
    public void testMovesGenerator() {
        ArrayList<Integer> moves = new ArrayList<>();
        game.MovesGenerator(game.field, moves);
        assertEquals(9, moves.size());
    }

    @Test
    public void testEvaluateFirstPCWin() {
        game.symbol = 'X';
        for (int i = 0; i < 3; i++) {
            game.field[i] = game.symbol;
        }

        int result = game.evaluatePosition(game.field, game.PC1);
        assertEquals(Game.INF, result);
        result = game.evaluatePosition(game.field, game.PC2);
        assertEquals(-Game.INF, result);
    }

    @Test
    public void testEvaluateSecondPlayerWin() {
        game.symbol = 'O';
        for (int i = 0; i < 3; i++) {
            game.field[i] = game.symbol;
        }

        int result = game.evaluatePosition(game.field, game.PC1);
        assertEquals(-Game.INF, result);
        result = game.evaluatePosition(game.field, game.PC2);
        assertEquals(Game.INF, result);
    }

    @Test
    public void testEvaluatePositionDraw() {
        game.symbol = 'X';
        game.field = new char[] {'X', 'X', 'O', 'O', 'O', 'X', 'O', 'X', 'O'};
        int result = game.evaluatePosition(game.field, game.PC1);
        assertEquals(0, result);
    }

    @Test
    public void testMinimax() {
        game.symbol = 'X';
        game.field = new char[] {'X', ' ', 'O', ' ', ' ', 'X', ' ', 'X', 'O'};
        int move = game.MinMaxMove(game.field, game.PC2);
        assertTrue(move >= 1 && move <= 9);
    }
}
