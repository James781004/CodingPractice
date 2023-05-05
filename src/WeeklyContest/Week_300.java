package WeeklyContest;

import java.util.Arrays;

public class Week_300 {
    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2325.Decode%20the%20Message/README.md
    public String decodeMessage(String key, String message) {
        char[] d = new char[128];
        d[' '] = ' ';
        for (int i = 0, j = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (d[c] == 0) {
                d[c] = (char) ('a' + j++);
            }
        }

        char[] ans = message.toCharArray();
        for (int i = 0; i < ans.length; ++i) {
            ans[i] = d[ans[i]];
        }
        return String.valueOf(ans);
    }


    // https://leetcode.cn/problems/spiral-matrix-iv/solution/by-endlesscheng-4dwy/
    public int[][] spiralMatrix(int m, int n, ListNode head) {
        int[][] res = new int[m][n];
        for (int[] r : res) {
            Arrays.fill(r, -1);
        }
        int x = 0, y = 0;
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // 右下左上 ,"螺旋順序"
        int i = 0; // 控制方向，取值 0，1，2，3
        while (head != null) {
            res[x][y] = head.val;
            head = head.next;
            // 算下一步要到達的位置，判斷需不需要轉彎
            int nx = x + dirs[i][0];
            int ny = y + dirs[i][1];
            if (nx < 0 || ny < 0 || nx >= m || ny >= n || res[nx][ny] != -1) {
                i = (i + 1) % 4;
                nx = x + dirs[i][0];
                ny = y + dirs[i][1];
            }
            x = nx;
            y = ny;
        }
        return res;
    }


    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }
    }


    // https://leetcode.cn/problems/number-of-people-aware-of-a-secret/solution/by-endlesscheng-2x0z/
    class peopleAwareOfSecret {
        int n, delay, forget;
        int MOD = (int) 1e9 + 7;
        int[] memo;

        public int peopleAwareOfSecret(int _n, int _delay, int _forget) {
        /*
            記憶化搜索:
            我們記dfs(i)為第i天發現的秘密的人包含自己在內一共可以使得後面多少人知道秘密
            i從i+delay天起,到i+forget-1天都是可以將秘密散播出去的
            也就是[min(i+delay,n),min(i+forget-1,n)]=[a,b]這個時間段是i的傳播階段
            此時知道秘密的人數有1+∑dfs(a,b)
            同時應該注意知道了秘密的人會忘記秘密,因此也會有一個期限
            這裡由於子問題的出現可以使用記憶化減少搜索次數
         */
            n = _n;
            delay = _delay;
            forget = _forget;
            memo = new int[n + 1];
            return dfs(1);
        }


        private int dfs(int i) {
            // 在第n天之後才能傳播,說明只有自己知道
            if (i + delay > n) return 1;

            // 已經搜索過直接返回
            if (memo[i] != 0) return memo[i];

            // i傳播的范圍為[min(i+delay,n),min(i+forget-1,n)]=[a,b]
            int a = i + delay, b = Math.min(i + forget - 1, n);

            // 自身到[i+forget]就忘記了,在n天內忘記了取0,反之取1
            long res = i + forget <= n ? 0 : 1;

            // 合法的傳播范圍為[a,b]
            for (int j = a; j <= b; j++) {
                int t = dfs(j);
                memo[j] = t;    // 標記
                res = (res + t) % MOD;
            }
            return (int) res;
        }


        public int peopleAwareOfSecretDP(int n, int delay, int forget) {
            int[] f = new int[n];
            f[0] = 1;
            int cntB = 0;  // 表示知道祕密但不能分享的人數，本來是用f[i][0]表示，但其實用一個數字統計第 n 天的總人數就可以
            for (int i = 0; i < n; i++) {
                if (i + delay >= n) cntB = (cntB + f[i]) & MOD;  // 統計第 n 天的總人數

                // i 經過 delay (就是 i + delay) 後，以及i到達 forget (就是 i + forget) 時間之前的區段為有效區
                // 所以 i 傳播的範圍為[min(i+delay,n),min(i+forget-1,n)]=[a,b]
                for (int j = i + delay; j < Math.min(i + forget, n); j++) {
                    f[j] = (f[j] + f[i]) % MOD;  // [a,b]範圍內可以分享祕密的人數
                }
            }

            return (f[n - 1] + cntB) % MOD;  // 答案是兩種人數之和
        }

        // 差分數組
        public int peopleAwareOfSecret2(int n, int delay, int forget) {
            int[] diff = new int[n];
            diff[0] = 1; // f[0] = 1，相當於 diff[0] = 1, diff[1] = -1
            diff[1] = MOD - 1;
            int f = 0, cntB = 0;
            for (int i = 0; i < n; ++i) {
                f = (f + diff[i]) % MOD;
                if (i + delay >= n) cntB = (cntB + f) % MOD;
                else {
                    diff[i + delay] = (diff[i + delay] + f) % MOD;
                    if (i + forget < n) diff[i + forget] = (diff[i + forget] - f + MOD) % MOD; // +MOD 是為了保證結果不會出現負數
                }
            }
            return (f + cntB) % MOD;
        }


        // 填表法（用其它狀態計算當前狀態）
        public int peopleAwareOfSecret3(int n, int delay, int forget) {
            int[] sum = new int[n + 1];  // 用前綴和優化，表示第i天的新增人數前綴和
            sum[1] = 1;
            for (int i = 2; i <= n; i++) {
                // 第i天的新增人數可以從有效範圍的和獲取，這邊用前綴和相減求出有效區間和
                int f = (sum[Math.max(i - delay, 0)] - sum[Math.max(i - forget, 0)]) % MOD;
                sum[i] = (sum[i - 1] + f) % MOD;  // sum[i] == 有效區間和加上前面的sum[i-1]前綴和
            }
            return ((sum[n] - sum[Math.max(n - forget, 0)]) % MOD + MOD) % MOD; // 防止結果為負數
        }
    }


    // https://leetcode.cn/problems/number-of-increasing-paths-in-a-grid/solution/ji-yi-hua-sou-suo-pythonjavacgo-by-endle-xecc/
    class countPaths {
        int[][] memo, grid;
        int m, n;
        int MOD = (int) 1e9 + 7;
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        public int countPaths(int[][] _grid) {
        /*
        dfs記憶化搜索:
        從每個格子出發搜索遞增的路徑數有多少
        有上下左右4個方向,合法的方向是比之前格子嚴格大的
        另外用一個memo[i][j]保存從grid[i][j]出發的遞增路徑數
        dfs(i,j)主邏輯:grid[i][j]出發的遞增路徑數=本身自成1條路徑+上下左右出發嚴格遞增路徑數之和
         */
            grid = _grid;
            m = grid.length;
            n = grid[0].length;
            memo = new int[m][n];
            long res = 0;
            // 統計每一個出發點的遞增路徑
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    res = (res + dfs(i, j)) % MOD;
                }
            }
            return (int) res;
        }

        private int dfs(int i, int j) {
            // 已經搜索過了,直接返回其數值
            if (memo[i][j] != 0) return memo[i][j];
            long res = 1;   // 本身自成一條嚴格遞增路徑
            // 一共有4個搜索方向
            for (int[] dir : dirs) {
                int newI = i + dir[0], newJ = j + dir[1];
                // 越界或者大小不符合要求就跳過
                // 這裡可以省略掉visited數組，因為這裡的嚴格遞增的剪枝就保證了不可能會走重複的路徑
                if (newI < 0 || newI >= m || newJ < 0 || newJ >= n || grid[newI][newJ] <= grid[i][j])
                    continue;
                int t = dfs(newI, newJ);    // 下一點出發點路徑數
                res = (res + t) % MOD;  // 累加結果
            }
            memo[i][j] = (int) res; // 標記grid[i][j]出發的路徑數
            return (int) res;
        }
    }
}
