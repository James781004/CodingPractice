package GuChengAlgorithm.Ch01_BasicDataStructure.Pow;

public class Q01_Pow {
    // 基本原理
    // https://docs.google.com/presentation/d/1g0woDoyfjysHLRiF7zW-T_r9hbHJdUv7SJ-mBimYTMU/edit#slide=id.g2053c6c87be_0_16


    // https://docs.google.com/presentation/d/1g0woDoyfjysHLRiF7zW-T_r9hbHJdUv7SJ-mBimYTMU/edit#slide=id.g2053c6c87be_0_1

    class Pow {
        public double myPow(double x, int n) {
            long N = n;
            if (N < 0) {
                x = 1 / x;
                N = -N;
            }
            double ans = 1;
            double currentProduct = x;
            for (long i = N; i > 0; i /= 2) {
                if ((i % 2) == 1) {
                    ans = ans * currentProduct;
                }
                currentProduct = currentProduct * currentProduct;
            }
            return ans;
        }


        public double myPow2(double x, int n) {
            long N = n;
            if (N < 0) {
                x = 1 / x;
                N = -N;
            }
            return fastPow(x, n);
        }


        private double fastPow(double x, int n) {
            if (n == 0) return 1.0;
            double half = fastPow(x, n / 2);
            if (n % 2 == 0) {
                return half * half;
            } else {
                return half * half * x;
            }
        }
    }


    // https://docs.google.com/presentation/d/1g0woDoyfjysHLRiF7zW-T_r9hbHJdUv7SJ-mBimYTMU/edit#slide=id.g2053c6c87be_0_45
    class MonkeyMove {
        int MOD = (int) 1e9 + 7;

        public int monkeyMove(int n) {
            long r = pow(n);
            return (int) ((r + MOD - 2) % MOD);
        }

        private long pow(int n) {
            if (n == 1) return 2L;
            long y = pow(n / 2);
            if (n % 2 == 0) return (y * y) % MOD;
            else return (((y * y) % MOD) * 2) % MOD;
        }

        public int monkeyMove2(int n) {
            long res = 1, base = 2, mod = (long) 1e9 + 7;
            while (n > 0) {
                if (n % 2 == 1) {
                    res = res * base % mod;
                }
                base = base * base % mod;
                n /= 2;
            }
            return (int) ((res - 2 + mod) % mod);
        }
    }

}
