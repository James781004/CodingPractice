package WeeklyContest;

import java.util.Arrays;

public class Week_435 {

    // https://leetcode.cn/problems/maximum-difference-between-even-and-odd-frequency-i/solutions/3061766/tong-ji-mei-chong-zi-mu-de-chu-xian-ci-s-3cre/
    public int maxDifference(String s) {
        int[] cnt = new int[26];
        for (char b : s.toCharArray()) {
            cnt[b - 'a']++;
        }

        int max1 = 0;
        int min0 = Integer.MAX_VALUE;
        for (int c : cnt) {
            if (c % 2 > 0) {
                max1 = Math.max(max1, c);
            } else if (c > 0) {
                min0 = Math.min(min0, c);
            }
        }
        return max1 - min0;
    }


    // https://leetcode.cn/problems/maximum-manhattan-distance-after-k-changes/solutions/3061765/heng-zong-zuo-biao-fen-bie-ji-suan-tan-x-lhhi/
    private int left;

    public int maxDistance(String s, int k) {
        int ans = 0;
        int[] cnt = new int['Y'];
        for (char ch : s.toCharArray()) {
            cnt[ch]++;
            left = k;
            ans = Math.max(ans, f(cnt['N'], cnt['S']) + f(cnt['E'], cnt['W']));
        }
        return ans;
    }

    private int f(int a, int b) {
        int d = Math.min(Math.min(a, b), left);
        left -= d;
        return Math.abs(a - b) + d * 2;
    }


    // https://leetcode.cn/problems/minimum-increments-for-target-multiples-in-an-array/solutions/3061806/zi-ji-zhuang-ya-dpji-yi-hua-sou-suo-di-t-aeaj/
    public int minimumIncrements(int[] nums, int[] target) {
        int n = nums.length;
        int m = target.length;

        // 預處理 target 的所有子集的 LCM
        long[] lcms = new long[1 << m];
        Arrays.fill(lcms, 1);
        for (int i = 0; i < m; i++) {
            int bit = 1 << i;
            for (int mask = 0; mask < bit; mask++) {
                lcms[bit | mask] = lcm(target[i], lcms[mask]);
            }
        }

        long[][] memo = new long[n][1 << m];
        for (long[] row : memo) {
            Arrays.fill(row, -1);
        }
        return (int) dfs(n - 1, (1 << m) - 1, nums, lcms, memo);
    }

    private long dfs(int i, int j, int[] nums, long[] lcms, long[][] memo) {
        if (j == 0) {
            return 0;
        }
        if (i < 0) { // 不能有剩余元素
            return Integer.MAX_VALUE / 2;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        long res = dfs(i - 1, j, nums, lcms, memo); // 不修改 nums[i]
        for (int sub = j; sub > 0; sub = (sub - 1) & j) { // 枚舉 j 的所有非空子集
            long l = lcms[sub];
            res = Math.min(res, dfs(i - 1, j ^ sub, nums, lcms, memo) + (l - nums[i] % l) % l);
        }
        return memo[i][j] = res;
    }

    private long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }


    // https://leetcode.cn/problems/maximum-difference-between-even-and-odd-frequency-ii/solutions/3061845/mei-ju-qian-zhui-he-hua-dong-chuang-kou-6cwsm/
    public int maxDifferenceII(String S, int k) {
        final int INF = Integer.MAX_VALUE / 2;
        char[] s = S.toCharArray();
        int ans = -INF;
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                if (y == x) {
                    continue;
                }
                int[] curS = new int[5];
                int[] preS = new int[5];
                int[][] minS = {{INF, INF}, {INF, INF}};
                int left = 0;
                for (int i = 0; i < s.length; i++) {
                    curS[s[i] - '0']++;
                    int r = i + 1;
                    while (r - left >= k && curS[x] > preS[x] && curS[y] > preS[y]) {
                        int p = preS[x] & 1;
                        int q = preS[y] & 1;
                        minS[p][q] = Math.min(minS[p][q], preS[x] - preS[y]);
                        preS[s[left] - '0']++;
                        left++;
                    }
                    ans = Math.max(ans, curS[x] - curS[y] - minS[curS[x] & 1 ^ 1][curS[y] & 1]);
                }
            }
        }
        return ans;
    }


}









