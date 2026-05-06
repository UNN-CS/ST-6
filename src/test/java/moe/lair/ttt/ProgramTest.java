package moe.lair.ttt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ProgramTest {
    @Test
    void createPanelReturnsReadyToUsePanel() {
        TicTacToePanel panel = Program.createPanel();

        assertNotNull(panel);
        assertEquals(9, panel.getComponentCount());
    }
}
