package PointToOffer.DP;

public class Q10_1_Fibonacci {
    public int Fibonacci(int n) {
        if (n <= 1) return n;
        int one = 0, two = 1, res = 0;
        for (int i = 2; i <= n; i++) {
            res = one + two;
            one = two;
            two = res;
        }

        return res;
    }
}
