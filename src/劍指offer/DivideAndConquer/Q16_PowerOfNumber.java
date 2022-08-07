package 劍指offer.DivideAndConquer;

public class Q16_PowerOfNumber {


    public double Power(double x, int n) {
        boolean isNegative = false;
        if (n < 0) {
            n = -n;
            isNegative = true;
        }
        double res = pow(x, n);
        return isNegative ? 1 / res : res;
    }

    private double pow(double x, int n) {
        if (n == 0) return 1;
        if (n == 1) return x;
        double res = pow(x, n / 2);
        res = res * res;
        if (n % 2 != 0) res *= x;
        return res;
    }
}
