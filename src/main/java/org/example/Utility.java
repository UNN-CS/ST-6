package org.example;

import java.util.ArrayList;

class Utility {

    public static void print(char[] board) {
        System.out.println();
        for (int j = 0; j < 9; j++) {
            System.out.print(board[j] + "-");
        }
        System.out.println();
    }

    public static void print(int[] board) {
        System.out.println();
        for (int j = 0; j < 9; j++) {
            System.out.print(board[j] + "-");
        }
        System.out.println();
    }

    public static void print(ArrayList<Integer> moves) {
        System.out.println();
        for (int j = 0; j < moves.size(); j++) {
            System.out.print(moves.get(j) + "-");
        }
        System.out.println();
    }
}
