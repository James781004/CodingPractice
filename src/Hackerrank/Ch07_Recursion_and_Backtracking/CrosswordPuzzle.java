package Hackerrank.Ch07_Recursion_and_Backtracking;

import java.util.ArrayList;
import java.util.List;

public class CrosswordPuzzle {
    static List<String> ans;

    public static List<String> crosswordPuzzle(List<String> crossword, String words) {
        // Write your code here
        ans = new ArrayList<>();
        char[][] croswrd = new char[10][10];
        int i = 0;
        for (String s : crossword) {
            int j = 0;
            for (char ch : s.toCharArray()) {
                croswrd[i][j] = ch;
                j++;
            }
            i++;
        }
        helper(croswrd, words.split(";"), 0);
        return ans;
    }

    public static void helper(char[][] c, String[] words, int vidx) {
        if (vidx == words.length) {
            List<String> temp = new ArrayList<>();
            for (char[] str : c) {
                String s = "";
                for (char ch : str) {
                    s += ch;
                }
                temp.add(s);
            }
            ans = temp;
            return;
        }
        String word = words[vidx];
        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                if (canPlaceHori(c, word, i, j)) {
                    boolean[][] vis = placeHori(c, word, i, j);
                    helper(c, words, vidx + 1);
                    unplaceHori(c, word, vis, i, j);
                }

                if (canPlaceVert(c, word, i, j)) {
                    boolean[][] vis = placeVert(c, word, i, j);
                    helper(c, words, vidx + 1);
                    unplaceVert(c, word, vis, i, j);
                }
                // print(c);
                // System.out.println();
            }
        }
    }

    public static void unplaceHori(char[][] c, String word, boolean[][] vis, int i, int j) {
        for (int k = 0; k < word.length(); k++) {
            if (vis[i][j + k] == true) {
                c[i][j + k] = '-';
                vis[i][j + k] = false;
            }
        }
    }

    public static void unplaceVert(char[][] c, String word, boolean[][] vis, int i, int j) {
        for (int k = 0; k < word.length(); k++) {
            if (vis[i + k][j] == true) {
                c[i + k][j] = '-';
                vis[i + k][j] = false;
            }
        }
    }

    public static boolean[][] placeHori(char[][] c, String word, int i, int j) {
        boolean[][] res = new boolean[10][10];
        for (int k = 0; k < word.length(); k++) {
            if (j + k < 10 && (c[i][j + k] == '-')) {
                c[i][j + k] = word.charAt(k);
                res[i][j + k] = true;
            }
        }
        return res;
    }

    public static boolean[][] placeVert(char[][] c, String word, int i, int j) {
        boolean[][] res = new boolean[10][10];
        for (int k = 0; k < word.length(); k++) {
            if (i + k < 10 && (c[i + k][j] == '-')) {
                c[i + k][j] = word.charAt(k);
                res[i + k][j] = true;
            }
        }
        return res;
    }

    public static boolean canPlaceHori(char[][] c, String word, int i, int j) {
        for (int k = 0; k < word.length(); k++) {
            if (j + k < 10 && (c[i][j + k] == '-' || c[i][j + k] == word.charAt(k))) {

            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean canPlaceVert(char[][] c, String word, int i, int j) {
        for (int k = 0; k < word.length(); k++) {
            if (i + k < 10 && (c[i + k][j] == '-' || c[i + k][j] == word.charAt(k))) {
            } else {
                return false;
            }
        }
        return true;
    }

    public static void print(char[][] c) {
        for (char[] cc : c) {
            for (char ccc : cc) {
                System.out.print(ccc);
            }
            System.out.println();
        }
    }

}
