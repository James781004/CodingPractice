package PointToOffer.BitManipulation;

public class Q15_NumberOf1 {
    public int NumberOf1(int n) {
        int cnt = 0;

        // n&(n-1) 位运算可以将 n 的位级表示中最低的那一位 1 设置为 0。
        // 不断将 1 设置为 0，直到 n 为 0。时间复杂度：O(M)，其中 M 表示 1 的个数。
        while (n != 0) {
            cnt++;
            n &= (n - 1);
        }

        return cnt;
    }
}
