package WeeklyContest;

public class Week_522 {

    // https://leetcode.cn/problems/minimum-rotations-to-dial-a-number-i/solutions/4038390/bian-li-pythonjavacgo-by-endlesscheng-7bkz/
    public int minRotations(String s) {
        int ans = 0;
        char pre = '0';
        for (char ch : s.toCharArray()) {
            int d = Math.abs(ch - pre);
            ans += Math.min(d, 10 - d);
            pre = ch;
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-rotations-to-dial-a-number-ii/solutions/4038389/qian-hou-zhui-fen-jie-o1-kong-jian-pytho-w3pz/
    public int minRotations(int n, String S) {
        char[] s = S.toCharArray();

        int suf = 0;
        for (int i = 1; i < n; i++) {
            suf += rotate(s[i], s[i - 1]);
        }

        int ans = rotate('0', s[n - 1]) + suf; // k=0 的情況

        int pre = rotate('0', s[0]);
        for (int k = 1; k < n; k++) {
            int op = rotate(s[k], s[k - 1]);
            suf -= op; // 撤銷
            ans = Math.min(ans, pre + rotate(s[k - 1], s[n - 1]) + suf);
            pre += op;
        }
        return ans;
    }

    // 指針從數字 x 旋轉到數字 y 的最少旋轉次數
    private int rotate(char x, char y) {
        int d = Math.abs(x - y);
        return Math.min(d, 10 - d);
    }


    // https://leetcode.cn/problems/maximum-alternating-subarray-sum-with-one-deletion/solutions/4038399/zhuang-tai-ji-dppythonjavacgo-by-endless-zdcg/
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;
        long[][][] memo = new long[n][2][2];
        for (long[][] mat : memo) {
            mat[0][0] = mat[0][1] = mat[1][0] = mat[1][1] = Long.MIN_VALUE;
        }

        long ans = Long.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, Math.max(dfs(i, 0, 0, nums, memo), dfs(i, 1, 0, nums, memo)));
        }
        return ans;
    }

    // 只需在 1186 的基礎上增加參數 rev
    private long dfs(int i, int j, int rev, int[] nums, long[][][] memo) {
        if (i == nums.length) { // 子數組至少要有一個數，不合法
            return Long.MIN_VALUE / 2; // 除 2 防止負數相加溢出
        }
        if (memo[i][j][rev] != Long.MIN_VALUE) {
            return memo[i][j][rev]; // 之前計算過
        }

        int x = rev == 0 ? nums[i] : -nums[i];
        if (j == 0) {
            return memo[i][j][rev] = Math.max(dfs(i + 1, 0, rev ^ 1, nums, memo), 0) + x;
        }
        return memo[i][j][rev] = Math.max(dfs(i + 1, 1, rev ^ 1, nums, memo) + x, dfs(i + 1, 0, rev, nums, memo));
    }


    // https://leetcode.cn/problems/count-good-strings/solutions/4038387/fei-bo-na-qi-shu-lie-ju-zhen-kuai-su-mi-yvn8b/
    private static final int MOD = 1_000_000_007;

    public int countGoodStrings(long n) {
        long[][] m = {
                {1, 1},
                {1, 0},
        };
        long[][] f1 = {{2}, {0}}; // 這裡初始化成 2，就不用把答案乘以 2 了
        long[][] fn = powMul(m, n - 1, f1);
        return (int) fn[0][0];
    }

    // a^n * f
    private long[][] powMul(long[][] a, long n, long[][] f) {
        long[][] res = f;
        while (n > 0) {
            if ((n & 1) > 0) {
                res = mul(a, res);
            }
            a = mul(a, a);
            n >>= 1;
        }
        return res;
    }

    // 返回矩陣 a 和矩陣 b 相乘的結果
    private long[][] mul(long[][] a, long[][] b) {
        long[][] c = new long[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int k = 0; k < a[i].length; k++) {
                if (a[i][k] == 0) {
                    continue;
                }
                for (int j = 0; j < b[k].length; j++) {
                    c[i][j] = (c[i][j] + a[i][k] * b[k][j]) % MOD;
                }
            }
        }
        return c;
    }


}










