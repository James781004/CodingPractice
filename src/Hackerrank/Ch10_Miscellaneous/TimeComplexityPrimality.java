package Hackerrank.Ch10_Miscellaneous;

public class TimeComplexityPrimality {
    public static String primality(int n) {
        if (n < 2) {
            return "Not prime";
        }
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return "Not prime";
            }
        }
        return "Prime";
    }
}
