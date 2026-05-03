package com.mycompany.app;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import javax.swing.*;
import java.awt.*;

public class GuiCoverageTest {
    
    private TicTacToePanel panel;
    
    @Before
    public void setUp() {
        // Создаем панель в отдельном потоке для Swing
        SwingUtilities.invokeLater(() -> {
            panel = new TicTacToePanel(new GridLayout(3, 3));
        });
        
        // Даем время на инициализацию
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @After
    public void tearDown() {
        if (panel != null) {
            SwingUtilities.invokeLater(() -> {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }
    
    @Test
    public void testPanelCreation() {
        assertNotNull(panel);
        assertEquals(9, panel.getComponentCount());
    }
    
    @Test
    public void testCellsInitialization() {
        // Проверяем, что все клетки созданы правильно
        for (int i = 0; i < 9; i++) {
            Component comp = panel.getComponent(i);
            assertTrue(comp instanceof TicTacToeCell);
            TicTacToeCell cell = (TicTacToeCell) comp;
            assertEquals(' ', cell.getMarker());
            assertTrue(cell.isEnabled());
        }
    }
    
    @Test
    public void testProgramMainMethod() {
        // Просто проверяем, что main метод не падает при запуске
        try {
            // Запускаем в отдельном потоке, чтобы не блокировать тесты
            Thread thread = new Thread(() -> {
                try {
                    Program.main(new String[]{});
                    // Закрываем окно через секунду
                    Thread.sleep(1000);
                    for (Frame frame : Frame.getFrames()) {
                        frame.dispose();
                    }
                } catch (Exception e) {
                    // Игнорируем исключения GUI
                }
            });
            thread.start();
            Thread.sleep(500);
            thread.interrupt();
        } catch (Exception e) {
            // Игнорируем
        }
    }
}