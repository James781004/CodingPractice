package WeeklyContest;

import java.util.Arrays;

public class Week_329 {
    // https://www.bilibili.com/video/BV1Gv4y1y753/
    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2544.Alternating%20Digit%20Sum/README.md
    public int alternateDigitSum(int n) {
        int res = 0, sign = 1;
        for (char c : String.valueOf(n).toCharArray()) {
            int x = c - '0';
            res += x * sign;
            sign *= -1;
        }
        return res;
    }

    // O(1)空間
    public int alternateDigitSum2(int n) {
        int res = 0, sign = 1;
        while (n > 0) {
            res += n % 10 * sign;
            n /= 10;
            sign *= -1;
        }
        return res * -sign;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2545.Sort%20the%20Students%20by%20Their%20Kth%20Score/README.md
    public int[][] sortTheStudents(int[][] score, int k) {
        // 將 score 按照第 k 列的分數從大到小排序，然後返回即可。
        Arrays.sort(score, (a, b) -> b[k] - a[k]);
        return score;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2546.Apply%20Bitwise%20Operations%20to%20Make%20Strings%20Equal/README.md
    public boolean makeStringsEqual(String s, String target) {
        // 注意到 1 其實是數字轉換的“工具”，
        // 當有1的時候，可以把1變0或者0變1
        // 因此只要兩個字符串中都有 1 或者都沒有 1 ，
        // 那麼就可以通過操作使得兩個字符串相等。
        return s.contains("1") == target.contains("1");
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2547.Minimum%20Cost%20to%20Split%20an%20Array/README.md
    class minCost {
        private Integer[] f;
        private int[] nums;
        private int n, k;

        public int minCost(int[] nums, int k) {
            n = nums.length;
            this.k = k;
            this.nums = nums;
            f = new Integer[n];
            return dfs(0);
        }

        // 下標 i 開始拆分的最小代價
        private int dfs(int i) {
            if (i >= n) return 0;  // 已經拆分到了數組末尾，此時返回0
            if (f[i] != null) return f[i];
            int[] cnt = new int[n];  // 統計子數組中每個數字出現的次數
            int one = 0;  // 統計子數組中出現次數為 1 的數字的個數
            int ans = Integer.MAX_VALUE;
            for (int j = i; j < n; j++) {  // 枚舉子數組的末尾j
                int x = ++cnt[nums[j]];
                if (x == 1) { // 首次出現
                    one++;
                } else if (x == 2) {  // 出現次數超過2就不再處理
                    one--;
                }

                // 子數組長度為j - i + 1
                // 子數組的重要性就是 k + j - i + 1 - one (k加上子數組長度減去只出現一次的數字們)
                // 拆分的代價就是 k + j - i + 1 - one + dfs(j + 1)
                ans = Math.min(ans, k + j - i + 1 - one + dfs(j + 1));
            }
            return f[i] = ans;
        }


        // https://leetcode.cn/problems/minimum-cost-to-split-an-array/solution/by-endlesscheng-05s0/
        public int minCostDP(int[] nums, int k) {
            int n = nums.length;
            int[] f = new int[n + 1];  // f[i+1] 表示劃分 nums 的前 i 個數的最小代價
            byte[] state = new byte[n];  // 維護每個元素的出現次數
            for (int i = 0; i < n; i++) {
                Arrays.fill(state, (byte) 0);
                int unique = 0, mn = Integer.MAX_VALUE;
                for (int j = i; j >= 0; j--) {
                    int x = nums[j];
                    if (state[x] == 0) { // 首次出現
                        state[x] = 1;
                        unique++;
                    } else if (state[x] == 1) { // 不再唯一
                        state[x] = 2;
                        unique--;
                    }
                    mn = Math.min(mn, f[j] - unique);
                }
                f[i + 1] = k + mn;
            }
            return f[n] + n;
        }


        // 線段樹優化
        public int minCostST(int[] nums, int k) {
            int n = nums.length, ans = 0;
            mn = new int[n * 4];
            todo = new int[n * 4];
            int[] last = new int[n], last2 = new int[n];
            for (int i = 1; i <= n; ++i) {
                int x = nums[i - 1];
                update(1, 1, n, i, i, ans); // 相當於設置 f[i+1] 的值
                update(1, 1, n, last[x] + 1, i, -1);
                if (last[x] > 0) update(1, 1, n, last2[x] + 1, last[x], 1);
                ans = k + query(1, 1, n, 1, i);
                last2[x] = last[x];
                last[x] = i;
            }
            return ans + n;
        }

        // Lazy 線段樹模板（區間加，查詢區間最小）
        private int[] mn, todo;

        private void do_(int o, int v) {
            mn[o] += v;
            todo[o] += v;
        }

        private void spread(int o) {
            int v = todo[o];
            if (v != 0) {
                do_(o * 2, v);
                do_(o * 2 + 1, v);
                todo[o] = 0;
            }
        }

        // 區間 [L,R] 內的數都加上 v   o,l,r=1,1,n
        private void update(int o, int l, int r, int L, int R, int v) {
            if (L <= l && r <= R) {
                do_(o, v);
                return;
            }
            spread(o);
            int m = (l + r) / 2;
            if (m >= L) update(o * 2, l, m, L, R, v);
            if (m < R) update(o * 2 + 1, m + 1, r, L, R, v);
            mn[o] = Math.min(mn[o * 2], mn[o * 2 + 1]);
        }

        // 查詢區間 [L,R] 的最小值   o,l,r=1,1,n
        private int query(int o, int l, int r, int L, int R) {
            if (L <= l && r <= R)
                return mn[o];
            spread(o);
            int m = (l + r) / 2;
            if (m >= R) return query(o * 2, l, m, L, R);
            if (m < L) return query(o * 2 + 1, m + 1, r, L, R);
            return Math.min(query(o * 2, l, m, L, R), query(o * 2 + 1, m + 1, r, L, R));
        }
    }
}
