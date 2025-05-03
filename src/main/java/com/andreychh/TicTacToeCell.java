package com.andreychh;

import javax.swing.*;
import java.awt.*;

public class TicTacToeCell extends JButton {
    private final int num;
    private final int row;
    private final int col;
    private boolean isFill;
    private char marker;

    public TicTacToeCell(int num, int x, int y) {
        this.num = num;
        row = y;
        col = x;
        marker = ' ';
        setText(Character.toString(marker));
        setFont(new Font("Arial", Font.PLAIN, 40));
    }

    public char getMarker() {
        return marker;
    }

    public void setMarker(String m) {
        marker = m.charAt(0);
        setText(m);
        setEnabled(false);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getNum() {
        return num;
    }

}
