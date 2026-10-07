package EndlessCheng.GenreMenu.DP.Enhance.Other;

import java.util.Arrays;

public class SplitArray {

    // https://leetcode.cn/problems/qie-fen-shu-zu/solutions/1908167/by-huanglin-6arw/
    static int[] PRIME_MULTI = new int[500_000];
    static int[] map = new int[500_000];

    static {
        for (int i = 3; i < 1_000; i += 2) {
            if (PRIME_MULTI[i >> 1] > 0) {
                continue;
            }
            PRIME_MULTI[i >> 1] = i;
            for (int k = i * i; k < 1_000_000; k += i << 1) {
                PRIME_MULTI[k >> 1] = i;
            }
        }
    }

    public int splitArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 1;
        }
        Arrays.fill(map, 0);
        int count = 0;
        int tmpV;

        for (int num : nums) {
            count++;
            int minCount = count;

            //check prime 2
            if ((num & 0x1) == 0) {
                if (map[0] == 0 || map[0] > count) {
                    map[0] = count;
                } else {
                    minCount = Math.min(minCount, map[0]);
                }
                num >>= 1;
                while ((num & 1) == 0) {
                    num >>= 1;
                }
            }
            //check others
            while (num > 1) {
                int prime = PRIME_MULTI[num >> 1];
                if (prime == 0) {
                    prime = num;
                }
                if (map[prime >> 1] == 0 || map[prime >> 1] >= count) {
                    map[prime >> 1] = count;
                } else {
                    minCount = Math.min(minCount, map[prime >> 1]);
                }

                while (num % prime == 0) {
                    num /= prime;
                }
            }
            count = minCount;
        }
        return count;
    }


}
