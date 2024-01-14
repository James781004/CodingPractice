package Hackerrank.Ch07_Recursion_and_Backtracking;

public class RecursiveDigitSum {
    public static int superDigit(String n, int k) {
        if (n.length() == 1) {
            return Integer.valueOf(n);
        } else {
            long value = 0;
            for (char ch : n.toCharArray()) {
                value += ch - '0';
            }
            value *= k;
            return superDigit(value + "", 1);
        }
    }
}
