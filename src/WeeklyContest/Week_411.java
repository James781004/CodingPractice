package WeeklyContest;

public class Week_411 {
    // https://leetcode.cn/problems/count-substrings-that-satisfy-k-constraint-i/solutions/2884495/on-hua-dong-chuang-kou-pythonjavacgo-by-keubv/
    public int countKConstraintSubstrings(String S, int k) {
        char[] s = S.toCharArray();
        int ans = 0;
        int left = 0;
        int[] cnt = new int[2];
        for (int i = 0; i < s.length; i++) {
            cnt[s[i] & 1]++;
            while (cnt[0] > k && cnt[1] > k) {
                cnt[s[left++] & 1]--;
            }
            ans += i - left + 1;
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-energy-boost-from-two-drinks/solutions/2884478/jiao-ni-yi-bu-bu-si-kao-dpcong-ji-yi-hua-iebb/
    public long maxEnergyBoost(int[] a, int[] b) {
        int n = a.length;
        int[][] c = {a, b}; // 把 a 和 b 加到一個長為 2 的二維數組 c 中
        long[][] memo = new long[n][2];

        // 遞歸入口：枚舉最後一個數選的是 a[n−1] 還是 b[n−1]，取最大值
        return Math.max(dfs(n - 1, 0, c, memo), dfs(n - 1, 1, c, memo));
    }


    // (i,j) 表示從下標 [0,i] 中選數字，且最後選的是 a[i] 或 b[i] 的情況下，所選元素之和的最大值
    // 狀態定義 dfs(i,j)。其中 j=0,1，分別表示最後選的是 a[i] 還是 b[i]。
    private long dfs(int i, int j, int[][] c, long[][] memo) {
        if (i < 0) {
            return 0;
        }
        if (memo[i][j] > 0) { // 之前計算過
            return memo[i][j];
        }

        // 1. 繼續選 c[j] 中的元素，那麼下一個數選 c[j][i−1]，
        // 需要解決的問題為：從下標 [0,i−1] 中選數字，且最後選的是 c[j] 中的元素的情況下，
        // 所選元素之和的最大值，即 dfs(i−1,j)
        // 2. 改成選 c[j^1] 中的元素，那麼下一個數選 c[j^1][i−2]，因為題目規定切換選項需要cd一小時，
        // 需要解決的問題為：從下標 [0,i−2] 中選數字，且最後選的是 c[j^1] 中的元素的情況下，
        // 所選元素之和的最大值，即 dfs(i−2,j^1)。
        // 其中 ^ 為異或運算，通過異或 1，可以把 0 變成 1，把 1 變成 0。
        return memo[i][j] = Math.max(dfs(i - 1, j, c, memo), dfs(i - 2, j ^ 1, c, memo)) + c[j][i];
    }


    // 1:1 翻譯成遞推
    public long maxEnergyBoostDP(int[] a, int[] b) {
        int n = a.length;

        // f 表示從下標 [0,i] 中選數字，且最後選的是 a[i] 或 b[i] 的情況下，所選元素之和的最大值
        // 這裡 +2 是為了把 dfs(−2,j) 和 dfs(−1,j) 這兩個狀態也翻譯過來，作為 f 數組的初始值
        // 初始值 f[0][j]=f[1][j]=0，翻譯自遞歸邊界 dfs(−2,j)=dfs(−1,j)=0。
        long[][] f = new long[n + 2][2];

        for (int i = 0; i < n; i++) {
            f[i + 2][0] = Math.max(f[i + 1][0], f[i][1]) + a[i];
            f[i + 2][1] = Math.max(f[i + 1][1], f[i][0]) + b[i];
        }

        // 答案為 max(f[n+1][0],f[n+1][1])，翻譯自遞歸入口 max(dfs(n−1,0),dfs(n−1,1))
        return Math.max(f[n + 1][0], f[n + 1][1]);
    }


    // https://leetcode.cn/problems/find-the-largest-palindrome-divisible-by-k/solutions/2884548/tong-yong-zuo-fa-jian-tu-dfsshu-chu-ju-t-m3pu/
    public String largestPalindrome(int n, int k) {
        int[] pow10 = new int[n];
        pow10[0] = 1;
        for (int i = 1; i < n; i++) {
            pow10[i] = pow10[i - 1] * 10 % k;
        }

        char[] ans = new char[n];
        int m = (n + 1) / 2;
        boolean[][] vis = new boolean[m + 1][k];
        dfs(0, 0, n, k, m, pow10, ans, vis);
        return new String(ans);
    }

    private boolean dfs(int i, int j, int n, int k, int m, int[] pow10, char[] ans, boolean[][] vis) {
        if (i == m) {
            return j == 0;
        }
        vis[i][j] = true;
        for (int d = 9; d >= 0; d--) { // 貪心：從大到小枚舉
            int j2;
            if (n % 2 == 1 && i == m - 1) { // 正中間
                j2 = (j + d * pow10[i]) % k;
            } else {
                j2 = (j + d * (pow10[i] + pow10[n - 1 - i])) % k;
            }
            if (!vis[i + 1][j2] && dfs(i + 1, j2, n, k, m, pow10, ans, vis)) {
                ans[i] = ans[n - 1 - i] = (char) ('0' + d);
                return true;
            }
        }
        return false;
    }


    // https://leetcode.cn/problems/count-substrings-that-satisfy-k-constraint-ii/solutions/2884463/hua-dong-chuang-kou-qian-zhui-he-er-fen-jzo25/
    public long[] countKConstraintSubstrings(String S, int k, int[][] queries) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[] left = new int[n];
        long[] sum = new long[n + 1];
        int[] cnt = new int[2];
        int l = 0;

        // 滑動窗口
        for (int i = 0; i < n; i++) {
            cnt[s[i] & 1]++;
            while (cnt[0] > k && cnt[1] > k) {
                cnt[s[l++] & 1]--;
            }
            left[i] = l;
            // 計算 i-left[i]+1 的前綴和
            sum[i + 1] = sum[i] + i - l + 1;
        }

        long[] ans = new long[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int ql = queries[i][0];
            int qr = queries[i][1];
            int j = lowerBound(left, ql - 1, qr + 1, ql);
            ans[i] = sum[qr + 1] - sum[j] + (long) (j - ql + 1) * (j - ql) / 2;
        }
        return ans;
    }

    // 開區間 (left, right) 找第一個 >= target 的數
    // 如果沒有這樣的數，返回 right
    // 原理見 https://www.bilibili.com/video/BV1AP41137w7/
    private int lowerBound(int[] nums, int left, int right, int target) {
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // nums[left] < target
            // nums[right] >= target
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid; // 范圍縮小到 (mid, right)
            } else {
                right = mid; // 范圍縮小到 (left, mid)
            }
        }
        return right;
    }


}


