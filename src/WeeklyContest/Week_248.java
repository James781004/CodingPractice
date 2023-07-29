package WeeklyContest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class Week_248 {
    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1920.Build%20Array%20from%20Permutation/README.md
    public int[] buildArray(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = nums[nums[i]];
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1921.Eliminate%20Maximum%20Number%20of%20Monsters/README.md
    public int eliminateMaximum(int[] dist, int[] speed) {
        int n = dist.length;
        int[] times = new int[n]; // 記錄每個怪物最晚可被消滅的時間
        for (int i = 0; i < n; ++i) {
            times[i] = (dist[i] - 1) / speed[i];
        }

        // times 升序排列，然後遍歷
        Arrays.sort(times);
        for (int i = 0; i < n; ++i) {
            // 如果 times[i] >= i，說明第 i 個怪物可以被消滅，
            // 否則說明第 i 個怪物無法被消滅，直接返回 i 即可。
            if (times[i] < i) {
                return i;
            }
        }
        return n;
    }


    // https://leetcode.cn/problems/count-good-numbers/solutions/857923/ke-e-de-kuai-su-mi-shuang-100-by-yi-xin-wy61b/
    // 思路： https://leetcode.cn/problems/count-good-numbers/solutions/857968/tong-ji-hao-shu-zi-de-shu-mu-by-leetcode-53jj/
    public int countGoodNumbers(long n) {
        long base = 20, res = n % 2 == 0 ? 1 : 5, power = n / 2;
        int mod = 1000000007;
        while (power > 0) {
            if (power % 2 == 1) {
                res = res * base % mod;
            }
            power /= 2;
            base = (base * base) % mod;
        }
        return (int) res;
    }

    // https://leetcode.cn/problems/count-good-numbers/solutions/857723/kuai-su-mi-by-quruijie-ps8r/
    public int countGoodNumbers2(long n) {
        int N = (int) Math.pow(10, 9) + 7;
        // 如果是奇數,還得乘個5
        int cheng = 1;
        if (n % 2 == 1) {
            cheng = 5;
            // n變為偶數
            n -= 1;
        }
        // n中有多少個偶數
        long o = n / 2;
        // 4*5 的 偶數 次方
        long a = myPow(20, o, N);
        long ans = a * cheng;
        return (int) (ans % N);
    }

    // 快速冪 (記得要取余N,不只是結果取余,每次乘機也要取余)
    public long myPow(long x, long n, int N) {
        if (n == 0) {
            return 1;
        }
        long m = n;
        long sum = 1;
        while (m != 0) {
            if ((m & 1) == 1) {
                sum *= x;
                sum %= N;
            }
            x *= x;
            x %= N;
            m >>= 1;
        }
        return sum;
    }


    // https://leetcode.cn/problems/longest-common-subpath/solutions/1409976/-by-yu-niang-niang-21o4/
    class LongestCommonSubpath {
        public int longestCommonSubpath(int n, int[][] paths) {
            int right = Integer.MAX_VALUE;
            int m = paths.length;
            for (int[] path : paths) {
                right = Math.min(right, path.length);
            }

            int left = 0;
            Random r = new Random();
            while (left < right) {
                int mid = (right - left + 1) / 2 + left;
                long mod1 = (long) (r.nextInt((int) Math.pow(10, 14)) + Math.pow(10, 13));
                long mod2 = (long) (r.nextInt((int) Math.pow(10, 14)) + Math.pow(10, 13));
                if (checkPassed(paths, mid, 100000, mod1) && checkPassed(paths, mid, 100000, mod2)) {
                    left = mid;
                } else {
                    right = mid - 1;
                }
            }
            return left;
        }

        private boolean checkPassed(int[][] paths, int len, long mul, long mod) {
            int m = paths.length;
            Set<Long> set = new HashSet<>();

            long base = 1L;
            for (int i = 0; i < len - 1; i++) {
                base = base * mul % mod;
            }

            long hash = 0;
            for (int i = 0; i < paths[0].length; i++) {
                if (i >= len) {
                    hash -= ((paths[0][i - len] + 1) * base) % mod;
                    hash += mod;
                }
                hash = (hash * mul + paths[0][i] + 1) % mod;

                if (i >= len - 1) {
                    set.add(hash);
                }
            }

            for (int[] path : paths) {
                hash = 0;
                Set<Long> curr = new HashSet<>();
                for (int i = 0; i < path.length; i++) {
                    if (i >= len) {
                        hash -= ((path[i - len] + 1) * base) % mod;
                        hash += mod;
                    }
                    hash = (hash * mul + path[i] + 1) % mod;

                    if (hash < 0) {
                        System.out.println("");
                    }
                    if (i >= len - 1 && set.contains(hash)) {
                        curr.add(hash);
                    }
                }
                set = curr;
                if (set.isEmpty())
                    break;

            }
            return set.size() > 0;
        }
    }

}


