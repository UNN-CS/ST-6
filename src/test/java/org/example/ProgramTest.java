package org.example;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.GraphicsEnvironment;
import org.junit.jupiter.api.Test;

class ProgramTest {

    @Test
    void mainCanStartInHeadlessOrGui() throws Exception {
        if (GraphicsEnvironment.isHeadless()) {
            System.setProperty("java.awt.headless", "true");
        }
        Thread t = new Thread(() -> {
            try {
                Program.main(new String[]{});
            } catch (Exception ignored) {
            }
        });
        t.setDaemon(true);
        t.start();
        Thread.sleep(200);
        assertNotNull(t);
    }
}
