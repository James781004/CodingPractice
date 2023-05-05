package WeeklyContest;

import java.util.*;

public class Week_301 {
    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2335.Minimum%20Amount%20of%20Time%20to%20Fill%20Cups/README.md
    public int fillCups(int[] amount) {
        int ans = 0;

        // 每次貪心地選擇其中較大的兩個數進行減一操作（最多減為 0 ），直至所有數變為 0。
        while (amount[0] + amount[1] + amount[2] > 0) {
            Arrays.sort(amount);
            ans++;
            amount[2]--;
            amount[1] = Math.max(0, amount[1] - 1);
        }
        return ans;
    }


    // 分類討論
    public int fillCups2(int[] amount) {
        Arrays.sort(amount);
        if (amount[0] + amount[1] <= amount[2]) {
            return amount[2];  // 最多兩倍amount[2]杯子要裝，可以在amount[2]時間完成
        }

        // 每一次操作都可以將其中兩個數減一，
        // 最終匹配完，或者剩下最後一個數（取決於總和是偶數還是奇數）
        return (amount[0] + amount[1] + amount[2] + 1) / 2;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2336.Smallest%20Number%20in%20Infinite%20Set/README.md
    class SmallestInfiniteSet {
        private PriorityQueue<Integer> pq = new PriorityQueue<>();
        private Set<Integer> s = new HashSet<>();

        public SmallestInfiniteSet() {
            for (int i = 1; i < 1010; ++i) {
                pq.offer(i);
                s.add(i);
            }
        }

        public int popSmallest() {
            int ans = pq.poll();
            s.remove(ans);
            return ans;
        }

        public void addBack(int num) {
            if (!s.contains(num)) {
                s.add(num);
                pq.offer(num);
            }
        }
    }


    // https://leetcode.cn/problems/move-pieces-to-obtain-a-string/solution/nao-jin-ji-zhuan-wan-pythonjavacgo-by-en-9sqt/
    // 首先，無論怎麼移動，由於 L 和 R 無法互相穿過對方，那麼去掉 _ 後的剩余字符應該是相同的，否則返回 false。
    // 用雙指針遍歷 start[i] 和 target[j]，分類討論：
    // 1. 如果當前字符為 L 且 i<j，那麼這個 L 由於無法向右移動，返回 false；
    // 2. 如果當前字符為 R 且 i>j，那麼這個 R 由於無法向左移動，返回 false。
    // 遍歷完，若中途沒有返回 false 就返回 true。
    public boolean canChange(String start, String target) {
        if (!start.replaceAll("_", "").equals(target.replaceAll("_", ""))) return false;
        int n = start.length();
        int i = 0, j = 0;
        while (true) {
            while (i < n && start.charAt(i) == '_') {
                ++i;
            }
            while (j < n && target.charAt(j) == '_') {
                ++j;
            }
            if (i == n && j == n) {
                return true;
            }
            if (i == n || j == n || start.charAt(i) != target.charAt(j)) {
                return false;
            }
            if (start.charAt(i) == 'L' && i < j || start.charAt(i) == 'R' && i > j) {
                return false;
            }
            ++i;
            ++j;
        }
    }

    public boolean canChange2(String start, String target) {
        if (!start.replaceAll("_", "").equals(target.replaceAll("_", ""))) return false;
        for (int i = 0, j = 0; i < start.length(); i++) {
            if (start.charAt(1) == '_') continue;
            while (target.charAt(j) == '_') j++;
            if (i != j && (start.charAt(i) == 'L') == (i < j)) return false;
            j++;
        }
        return true;
    }


    // https://www.bilibili.com/video/BV1aU4y1q7BA/?t=7m29s
    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2338.Count%20the%20Number%20of%20Ideal%20Arrays/README.md
    private int[][] f;
    private int[][] c; // 組合數
    private int n;
    private int m;
    private static final int MOD = (int) 1e9 + 7;

    public int idealArrays(int n, int maxValue) {
        this.n = n;
        this.m = maxValue;
        this.f = new int[maxValue + 1][16];
        for (int[] row : f) {
            Arrays.fill(row, -1);
        }
        c = new int[n][16];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i && j < 16; j++) {
                c[i][j] = j == 0 ? 1 : (c[i - 1][j] + c[i - 1][j - 1]) % MOD;
            }
        }

        int ans = 0;
        for (int i = 1; i <= m; ++i) {
            ans = (ans + dfs(i, 1)) % MOD;
        }
        return ans;
    }

    private int dfs(int i, int cnt) {
        if (f[i][cnt] != -1) {
            return f[i][cnt];
        }

        int res = c[n - 1][cnt - 1];
        if (cnt < n) {
            for (int k = 2; k * i <= m; ++k) {
                res = (res + dfs(k * i, cnt + 1)) % MOD;
            }
        }
        f[i][cnt] = res;
        return res;
    }


    public int idealArraysDP(int n, int maxValue) {
        int[][] c = new int[n][16];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j <= i && j < 16; ++j) {
                c[i][j] = j == 0 ? 1 : (c[i - 1][j] + c[i - 1][j - 1]) % MOD;
            }
        }
        long[][] dp = new long[maxValue + 1][16];
        for (int i = 1; i <= maxValue; ++i) {
            dp[i][1] = 1;
        }
        for (int j = 1; j < 15; ++j) {
            for (int i = 1; i <= maxValue; ++i) {
                int k = 2;
                for (; k * i <= maxValue; ++k) {
                    dp[k * i][j + 1] = (dp[k * i][j + 1] + dp[i][j]) % MOD;
                }
            }
        }
        long ans = 0;
        for (int i = 1; i <= maxValue; ++i) {
            for (int j = 1; j < 16; ++j) {
                ans = (ans + dp[i][j] * c[n - 1][j - 1]) % MOD;
            }
        }
        return (int) ans;
    }


    // 數學做法
    List<Integer> primes = new ArrayList<>();

    void initPrimes(int limit) {
        // 質數的篩法
        boolean[] used = new boolean[limit + 1];
        for (int i = 2; i <= limit; i++) {
            if (!used[i]) {
                primes.add(i);
                for (int j = i * i; j <= limit; j += i) {
                    used[j] = true;
                }
            }
        }
    }

    // 提前計算組合數, k個球， 在N個槽位，投擲
    long[][] gOpt;

    void initOpt(int n, int k) {
        gOpt = new long[n + 1][k + 1];
        gOpt[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            long d = 0;
            for (int j = 0; j <= k; j++) {
                d = (d + gOpt[i - 1][j]) % MOD;
                gOpt[i][j] = (gOpt[i][j] + d) % MOD;
            }
        }
    }

    // 就是枚舉最終的值（1~MaxValue）
    // 然後分別求V對應的解答數，先因數分解，然後整合
    long solve(int v, int n) {
        long ans = 1l;
        for (int i = 0; v > 1 && i < primes.size(); i++) {
            int cnt = 0, d = primes.get(i);
            while (v != 1 && v % d == 0) {
                cnt++;
                v /= d;
            }
            if (cnt > 0) {
                ans = (ans * gOpt[n][cnt]) % MOD;
            }
        }
        return ans;
    }

    public int idealArrays3(int n, int maxValue) {

        initPrimes(maxValue);

        initOpt(n, (int) Math.max(Math.log(maxValue) / Math.log(2) + 1, 1));

        long ans = 0;
        for (int i = 1; i <= maxValue; i++) {
            long d = solve(i, n);
            ans = (ans + d) % MOD;
        }
        return (int) ans;
    }
}
