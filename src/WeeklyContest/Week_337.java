package WeeklyContest;

public class Week_337 {
    // https://leetcode.cn/problems/number-of-even-and-odd-bits/solution/er-jin-zhi-ji-ben-cao-zuo-pythonjavacgo-o82o2/
    public int[] evenOddBit(int n) {
        int[] res = new int[2];
        for (int i = 0; n > 0; n >>= 1, i ^= 1) { // i ^= 1 可以在0和1之間來回切換
            res[i] += n & 1;  // n & 1 取最低bit位，取完後 n >>= 1 右移一位
        }
        return res;
    }


    // https://leetcode.cn/problems/check-knight-tour-configuration/solution/an-ti-yi-mo-ni-by-endlesscheng-ldwj/
    class CheckValidGrid {
        public boolean checkValidGrid(int[][] grid) {
            if (grid[0][0] != 0) return false;
            int n = grid.length;
            int[][] pos = new int[n * n][2];  // pos數組記錄騎士訪問的每個格子的坐標

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    pos[grid[i][j]] = new int[]{i, j};
                }
            }

            // 遍歷pos數組
            // 檢查相鄰兩個格子的坐標差是否為 (1, 2) 或者 (2, 1)
            for (int i = 1; i < n * n; i++) {
                int[] p1 = pos[i - 1];
                int[] p2 = pos[i];
                int dx = Math.abs(p1[0] - p2[0]);
                int dy = Math.abs(p1[1] - p2[1]);
                boolean flag = (dx == 1 && dy == 2) || (dx == 2 && dy == 1);
                if (!flag) return false;
            }

            return true;
        }


        // 空間優化
        public boolean checkValidGrid2(int[][] grid) {
            if (grid[0][0] != 0) return false;
            int len = grid.length;
            int[] map = new int[len * len];   // [idx, x * len + y]
            for (int i = 0; i < len; ++i) {
                for (int j = 0; j < len; ++j) {
                    int idx = grid[i][j];
                    map[idx] = i * len + j;
                }
            }
            int former = map[0];
            if (former != 0) return false;
            for (int i = 1; i < len * len; ++i) {
                int cur = map[i];
                if (!valid(former, cur, len)) {
                    return false;
                }
                former = cur;
            }
            return true;
        }

        // 前面座標在數組位置表示為[idx, x * len + y]
        // 這邊轉換就要進行操作
        // x = 數組值 / len;
        // y = 數組值 % len;
        private boolean valid(int a, int b, int len) {
            int ax = a / len;
            int ay = a % len;
            int bx = b / len;
            int by = b % len;
            int absx = Math.abs(ax - bx);
            int absy = Math.abs(ay - by);
            if (absx == 2 && absy == 1) return true;
            if (absx == 1 && absy == 2) return true;
            return false;
        }
    }


    // https://leetcode.cn/problems/the-number-of-beautiful-subsets/solution/tao-lu-zi-ji-xing-hui-su-pythonjavacgo-b-fcgs/
    class BeautifulSubsets {
        private int[] nums, cnt;
        private int k, ans = -1; // 去掉空集

        public int beautifulSubsets(int[] nums, int k) {
            this.nums = nums;
            this.k = k;
            cnt = new int[k * 2 + 1001]; // 用數組實現比哈希表更快
            dfs(0);
            return ans;
        }

        private void dfs(int i) {
            if (i == nums.length) {
                ans++;
                return;
            }

            dfs(i + 1); // 不選，直接遞歸下一個
            int x = nums[i] + k; // 避免負數下標

            // 在枚舉 78. 子集 的基礎上加個判斷。
            // x = nums[i]的時候，如果之前選過x - k以及x + k，則不能選，否則可以選。
            if (cnt[x - k] == 0 && cnt[x + k] == 0) {
                cnt[x]++; // 選
                dfs(i + 1);
                cnt[x]--;  // 回溯
            }
        }

        // 寫法二：枚舉選哪個
        private void dfs2(int i) {
            ans++; // 合法子集
            if (i == nums.length) return;

            for (int j = 0; j < nums.length; j++) { // 枚舉選哪個
                int x = nums[i] + k; // 避免負數下標

                // 在枚舉 78. 子集 的基礎上加個判斷。
                // x = nums[i]的時候，如果之前選過x - k以及x + k，則不能選，否則可以選。
                if (cnt[x - k] == 0 && cnt[x + k] == 0) {
                    cnt[x]++; // 選
                    dfs2(j + 1);
                    cnt[x]--;  // 回溯
                }
            }
        }


        // 處理負數下標的另一種方式
        private void dfs3(int i) {
            if (i >= nums.length) {
                ++ans;
                return;
            }
            dfs3(i + 1);
            boolean ok1 = nums[i] + k >= cnt.length || cnt[nums[i] + k] == 0;
            boolean ok2 = nums[i] - k < 0 || cnt[nums[i] - k] == 0;
            if (ok1 && ok2) {
                ++cnt[nums[i]];
                dfs3(i + 1);
                --cnt[nums[i]];
            }
        }
    }


    // https://leetcode.cn/problems/smallest-missing-non-negative-integer-after-operations/solution/tong-yu-pythonjavacgo-by-endlesscheng-qoan/
    public int findSmallestInteger(int[] nums, int value) {
        int[] cnt = new int[value];
        for (int x : nums) ++cnt[(x % value + value) % value];
//        for (int i = 0; ; ++i) {
//            if (cnt[i % value]-- == 0) {
//                return i;
//            }
//        }

        int mex = 0;
        while (cnt[mex % value] > 0) {
            cnt[mex % value]--;
            mex++;
        }

        return mex;
    }
}
