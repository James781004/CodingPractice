package Grind.Ch17_Math;

public class Q03_Pow {
    // https://leetcode.cn/problems/powx-n/solutions/241471/50-powx-n-kuai-su-mi-qing-xi-tu-jie-by-jyd/
    public double myPow(double x, int n) {
        if (x == 0.0f) return 0.0d;
        long b = n;
        double res = 1.0;
        if (b < 0) {
            x = 1 / x;
            b = -b;
        }
        while (b > 0) {
            if ((b & 1) == 1) res *= x;
            x *= x;
            b >>= 1;
        }
        return res;
    }


    // 記憶化遞歸
    public double myPow2(double x, int n) {
        /*
        遞歸解法
        由公式可知有以下關系
        1.n為偶數時，x^n=(x^(n/2))^2
        2.n為奇數時，x^n=(x^(n/2))^2*x
        3.n為負數時，x^n=1/x^(-n)，此時-n就是正數，可按照上面方法進行求解
        base case
        當n==1時，x^n=x
        當n==0時，x^n=1
        時間復雜度:O(logN) 空間復雜度:O(1)
         */
        // 這裡-2^31直接變為正數會溢出，因此重載一個n類型為long的方法
        return pow(x, n);
    }

    private double pow(double x, long n) {
        if (n == 0) return 1.0;
        if (n == 1) return x;
        if (n < 0) return 1.0 / pow(x, -n);
        // 為了避免遞歸過程中重復運算因此先算出來
        double v = pow(x, n / 2);
        return (n & 1) == 0 ? v * v : v * v * x;
    }
}
