package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class Utility {
    public static void print(char[] board) {
        System.out.println();
        for(int j = 0; j < board.length; j++) {
            System.out.print(board[j] + (j < board.length - 1 ? "-" : ""));
        }
        System.out.println();
    }

    public static void print(int[] board) {
        System.out.println();
        for(int j = 0; j < board.length; j++) {
            System.out.print(board[j] + (j < board.length - 1 ? "-" : ""));
        }
        System.out.println();
    }

    public static void print(ArrayList<Integer> moves) {
        System.out.println();
        for(int j = 0; j < moves.size(); j++) {
            System.out.print(moves.get(j) + (j < moves.size() - 1 ? "-" : ""));
        }
        System.out.println();
    }
}