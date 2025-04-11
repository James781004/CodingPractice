package EndlessCheng.Basic.Math;

public class myPow {

    // https://leetcode.cn/problems/powx-n/solutions/2858114/tu-jie-yi-zhang-tu-miao-dong-kuai-su-mi-ykp3i/
    public double myPow(double x, int N) {
        double ans = 1;
        long n = N;
        if (n < 0) { // x^-n = (1/x)^n
            n = -n;
            x = 1 / x;
        }
        while (n != 0) { // 從低到高枚舉 n 的每個比特位
            if ((n & 1) == 1) { // 這個比特位是 1
                ans *= x; // 把 x 乘到 ans 中
            }
            x *= x; // x 自身平方
            n >>= 1; // 繼續枚舉下一個比特位
        }
        return ans;
    }


}
