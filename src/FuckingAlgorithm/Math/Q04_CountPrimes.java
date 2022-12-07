package FuckingAlgorithm.Math;

import java.util.Arrays;

public class Q04_CountPrimes {
//    https://leetcode.cn/problems/count-primes/
//    204. 計數質數
//    給定整數 n ，返回 所有小於非負整數 n 的質數的數量 。

    // 用排除法，把所有非質數都排除，剩下的就是質數。
    public int countPrimes(int n) {
        boolean[] isPrime = new boolean[n];
        Arrays.fill(isPrime, true);
        for (int i = 2; i * i < n; i++) {
            if (isPrime[i]) { //  i 是一個質數, i 的倍數不可能是質數了
                for (int j = i * i; j < n; j += i) {
                    isPrime[j] = false;
                }
            }

        }


        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime[i]) count++;
        }

        return count;
    }
}
