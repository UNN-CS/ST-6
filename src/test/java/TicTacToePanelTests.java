import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TicTacToePanelTests {

    @Test
    public void TicTacToePanelTest(){
        TicTacToePanel tttPanel = new TicTacToePanel(new GridLayout(3, 3));
        assertNotNull(tttPanel);
    }
}
