package PointToOffer.DP;

public class Q10_3_JumpFloor {

    // 跳 n 阶台阶，可以先跳 1 阶台阶，再跳 n-1 阶台阶；
    // 或者先跳 2 阶台阶，再跳 n-2 阶台阶。
    // 而 n-1 和 n-2 阶台阶的跳法可以看成子问题
    public int JumpFloor(int n) {
        if (n <= 2) return n;
        int a = 1, b = 2;
        int result = 0;
        for (int i = 2; i < n; i++) {
            result = a + b;
            a = b;
            b = result;
        }
        return result;
    }
}
