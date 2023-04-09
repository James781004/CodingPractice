package WeeklyContest;

import java.util.*;

public class Week_338 {
    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2600.K%20Items%20With%20the%20Maximum%20Sum/README.md
    public int kItemsWithMaximumSum(int numOnes, int numZeros, int numNegOnes, int k) {
        if (numOnes >= k) return numOnes;
        k -= numOnes;
        if (numZeros >= k) return numOnes;
        k -= numZeros;
        return numOnes - k;  // k 剩下 -1 的配額，與 1 的數量相減即可
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2601.Prime%20Subtraction%20Operation/README.md
    class PrimeSubOperation {
        public boolean primeSubOperation(int[] nums) {

            // 預處理質數
            List<Integer> p = new ArrayList<>();
            for (int i = 2; i <= 1000; ++i) {
                boolean ok = true;
                for (int j : p) {
                    if (i % j == 0) {
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    p.add(i);
                }
            }

            //  二分查找
            int n = nums.length;

            for (int i = n - 2; i >= 0; --i) {
                if (nums[i] < nums[i + 1]) {
                    continue;  // 符合遞增規則，直接跳過
                }
                int j = search(p, nums[i] - nums[i + 1]);  // 找最接近差值的質數
                if (j == p.size() || p.get(j) >= nums[i]) {
                    return false;  // 找不到符合條件的質數
                }
                nums[i] -= p.get(j);
            }
            return true;
        }

        private int search(List<Integer> nums, int x) {
            int l = 0, r = nums.size();
            while (l < r) {
                int mid = (l + r) >> 1;
                if (nums.get(mid) > x) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }
            return l;
        }
    }


    // https://www.bilibili.com/video/BV11o4y1p7Ci/?vd_source=3ffe88901c9b0b355ae9becd01f3e4bf
    // https://leetcode.cn/problems/minimum-operations-to-make-all-array-elements-equal/solution/yi-tu-miao-dong-pai-xu-qian-zhui-he-er-f-nf55/
    public List<Long> minOperations(int[] nums, int[] queries) {
        Arrays.sort(nums);
        int n = nums.length;
        long[] sum = new long[n + 1]; // 前綴和
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }

        List<Long> ans = new ArrayList<>();
        for (int q : queries) {
            int j = lowerBound(nums, q);
            long left = (long) q * j - sum[j]; // 藍色面積
            long right = sum[n] - sum[j] - (long) q * (n - j); // 綠色面積
            ans.add(left + right);
        }
        return ans;
    }

    // 見 https://www.bilibili.com/video/BV1AP41137w7/
    private int lowerBound(int[] nums, int target) {
        int left = -1, right = nums.length; // 開區間 (left, right)
        while (left + 1 < right) { // 區間不為空
            // 循環不變量：
            // nums[left] < target
            // nums[right] >= target
            int mid = left + (right - left) / 2;
            if (nums[mid] < target)
                left = mid; // 范圍縮小到 (mid, right)
            else
                right = mid; // 范圍縮小到 (left, mid)
        }
        return right;
    }


    // https://leetcode.cn/problems/collect-coins-in-a-tree/solution/tuo-bu-pai-xu-ji-lu-ru-dui-shi-jian-pyth-6uli/
    public int collectTheCoins(int[] coins, int[][] edges) {
        int n = coins.length;
        List<Integer> g[] = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        int[] deg = new int[n];
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x); // 建圖
            ++deg[x];  // 計算入度
            ++deg[y];
        }

        // 用拓撲排序「剪枝」：去掉沒有金幣的子樹
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; ++i)
            if (deg[i] == 1 && coins[i] == 0) // 無金幣葉子
                q.add(i);
        while (!q.isEmpty()) {
            int x = q.peek();
            q.pop();
            for (int y : g[x])
                if (--deg[y] == 1 && coins[y] == 0)
                    q.add(y);
        }

        // 再次拓撲排序
        for (int i = 0; i < n; ++i)
            if (deg[i] == 1 && coins[i] == 1) // 有金幣葉子
                q.add(i);
        if (q.size() <= 1) return 0; // 至多一個有金幣的葉子，直接收集
        int[] time = new int[n];
        while (!q.isEmpty()) {
            int x = q.peek();
            q.pop();
            for (int y : g[x])
                if (--deg[y] == 1) {
                    time[y] = time[x] + 1; // 記錄有金幣的葉子入隊時間
                    q.add(y);
                }
        }

        // 利用有金幣的葉子入隊時間記錄統計答案
        // 只要走到 time[x]=2 的節點 x，就能收集到在葉子上的金幣。
        // (假設有一個節點的time是大於等於2的，那麼它首先有一條到金幣的路徑；
        // 假設它沒有任何到達其它金幣的路徑了，那麼它會在第一步剪枝的時候就被剪掉了。
        // 所以某個節點的time是大於等於2的話，它一定至少有兩條邊聯通了兩個金幣。)
        //
        // 遍歷所有邊 x−y，如果滿足 time[x]≥2 且 time[y]≥2，
        // 那麼這條邊需要恰好經過 2 次（因為需要回到出發點），答案加 2；
        // 如果不滿足，則無需經過。
        int ans = 0;
        for (int[] e : edges)
            if (time[e[0]] >= 2 && time[e[1]] >= 2)
                ans += 2;
        return ans;
    }
}
