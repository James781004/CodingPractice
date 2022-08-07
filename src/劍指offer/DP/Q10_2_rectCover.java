package 劍指offer.DP;

public class Q10_2_rectCover {
    // 要覆盖 2*n 的大矩形，可以先覆盖 2*1 的矩形，再覆盖 2*(n-1) 的矩形；
    // 或者先覆盖 2*2 的矩形，再覆盖 2*(n-2) 的矩形。
    // 而覆盖 2*(n-1) 和 2*(n-2) 的矩形可以看成子问题。
    public int rectCover(int n) {
        if (n <= 2) return n;
        int a = 1, b = 2, res = 0;
        for (int i = 3; i <= n; i++) {
            res = a + b;
            a = b;
            b = res;
        }

        return res;
    }
}
