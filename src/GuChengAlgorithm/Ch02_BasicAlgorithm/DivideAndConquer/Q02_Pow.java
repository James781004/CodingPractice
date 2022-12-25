package GuChengAlgorithm.Ch02_BasicAlgorithm.DivideAndConquer;

public class Q02_Pow {
    // https://docs.google.com/presentation/d/1L0GLS7C6-pRcutYbQ1_2fOII9luGESWyHxovM8pFu5g/edit#slide=id.g53d11f0dc9_0_32
    public double myPow(double x, int n) {
        if (x == 0 || x == 1) return x;
        if (n < 0) return 1 / pow(x, -n);
        return pow(x, n);
    }

    private double pow(double x, int n) {
        if (n == 0) return 1;
        double y = pow(x, n / 2);
        if (n % 2 == 0) return y * y;
        else return y * y * x;
    }
}
