package com.mycompany.app;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays; 

public class ProgramTest {
    private Program program;

    @Before
    public void setUp() {
        program = new Program();
    }

    @Test
    public void testInitialBoardIsEmpty() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(Program.EMPTY, program.getBoard()[i][j]);
            }
        }
    }

    @Test
    public void testValidPlayerMove() {
        program.makePlayerMove(0, 0);
        assertEquals(Program.PLAYER, program.getBoard()[0][0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPlayerMove() {
        program.makePlayerMove(3, 3); // Выход за границы
    }

    @Test
    public void testComputerMove() {
        program.makePlayerMove(0, 0); // Игрок делает ход
        program.makeComputerMove();    // Компьютер отвечает
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (program.getBoard()[i][j] == Program.COMPUTER) count++;
            }
        }
        assertEquals(1, count);
    }

    @Test
    public void testCheckWinPlayer() {
        program.makePlayerMove(0, 0);
        program.makePlayerMove(0, 1);
        program.makePlayerMove(0, 2);
        assertTrue(program.checkWin(Program.PLAYER));
    }

    @Test
    public void testCheckWinComputer() {
        program.makeComputerMove();
        program.makeComputerMove();
        program.makeComputerMove();
        assertTrue(program.checkWin(Program.COMPUTER));
    }

    @Test
    public void testIsBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                program.makePlayerMove(i, j);
            }
        }
        assertTrue(program.isBoardFull());
    }
}
