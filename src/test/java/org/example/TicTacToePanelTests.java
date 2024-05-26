package  org.example;
import org.example.TicTacToePanel;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static junit.framework.Assert.assertNotNull;

public class TicTacToePanelTests {

    @Test
    public void TicTacToePanelTest(){
        TicTacToePanel tttPanel = new TicTacToePanel(new GridLayout(3, 3));
        assertNotNull(tttPanel);
    }
}