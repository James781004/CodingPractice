package WeeklyContest;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Week_443 {

    // https://leetcode.cn/problems/minimum-cost-to-reach-every-position/solutions/3633601/ben-zhi-shi-qian-zhui-zui-xiao-zhi-pytho-anvw/\
    public int[] minCosts(int[] cost) {
        for (int i = 1; i < cost.length; i++) {
            cost[i] = Math.min(cost[i], cost[i - 1]);
        }
        return cost;
    }


    // https://leetcode.cn/problems/longest-palindrome-after-substring-concatenation-ii/solutions/3633596/mei-ju-hui-wen-zhong-xin-dppythonjavacgo-kpxn/

    /**
     * 計算可以構造的最長回文串長度
     * 這裡會考慮兩種情況：
     * 1. 直接使用 s 和 t 計算最長回文
     * 2. 將 s 和 t 反轉後再計算（因為回文具有對稱性）
     *
     * @param s 字串 s
     * @param t 字串 t
     * @return 最長回文串的長度
     */
    public int longestPalindrome(String s, String t) {
        // 反轉字串，確保回文的對稱性
        String rs = new StringBuilder(s).reverse().toString();
        String rt = new StringBuilder(t).reverse().toString();

        // 計算兩種可能的最大值
        return Math.max(calc(s, t), calc(rt, rs));
    }

    /**
     * 計算從 S 和 T 組成的最長回文串長度
     * 主要有兩個步驟：
     * 1. 計算 S 和 T 之間的最長公共子序列長度，這些字元可以作為回文的對稱部分。
     * 2. 使用「中心擴展法」來找 S 本身可以形成的回文，並結合上一步的結果。
     *
     * @param S 字串 S
     * @param T 字串 T
     * @return 最長回文串的長度
     */
    private int calc(String S, String T) {
        int ans = 0; // 儲存最長回文的長度
        char[] s = S.toCharArray(); // 把字串轉成字元陣列，方便處理
        char[] t = T.toCharArray();
        int n = s.length;
        int m = t.length;

        int[] mx = new int[n + 1]; // mx[i] 存儲 S[0...i-1] 與 T 中最大匹配的長度
        int[][] f = new int[n + 1][m + 1]; // 動態規劃陣列

        // 計算 S 和 T 的最大公共子序列長度
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (s[i] == t[j]) {
                    f[i + 1][j] = f[i][j + 1] + 1;
                    mx[i + 1] = Math.max(mx[i + 1], f[i + 1][j]);
                }
            }
            ans = Math.max(ans, mx[i + 1] * 2); // |x| = |y| 的情況，當回文的兩邊長度相等時
        }

        // 計算 |x| > |y| 的情況
        // 使用「中心擴展法」尋找 S 本身的回文結構
        for (int i = 0; i < 2 * n - 1; i++) {
            int l = i / 2, r = (i + 1) / 2;
            while (l >= 0 && r < n && s[l] == s[r]) {
                l--;
                r++;
            }
            if (l + 1 <= r - 1) { // 如果擴展後至少有一個回文子串
                ans = Math.max(ans, r - l - 1 + mx[l + 1] * 2);
            }
        }
        return ans;
    }

    // https://leetcode.cn/problems/minimum-operations-to-make-elements-within-k-subarrays-equal/solutions/3633641/hua-dong-chuang-kou-zhong-wei-shu-hua-fe-e9cn/
    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length; // 獲取數組的長度

        // 計算每個長度為 x 的子數組變成相同數字的最小操作數
        long[] dis = medianSlidingWindow(nums, x);

        // 定義 DP 陣列，f[i][j] 表示 nums[0:j] 內有 i 個長度為 x 的子數組時的最小操作數
        long[][] f = new long[k + 1][n + 1];

        for (int i = 1; i <= k; i++) {
            f[i][i * x - 1] = Long.MAX_VALUE; // 避免無效狀態

            for (int j = i * x; j <= n - (k - i) * x; j++) { // 確保能留足夠空間給剩餘子數組
                // j-x 為新子數組的起始位置，取最小操作數
                f[i][j] = Math.min(f[i][j - 1], f[i - 1][j - x] + dis[j - x]);
            }
        }

        return f[k][n]; // 返回最終結果
    }

    // 計算每個長度為 k 的子數組變成相同數字的最小操作數
    private long[] medianSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        long[] ans = new long[n - k + 1]; // 存儲每個窗口的結果

        // 使用最大堆與最小堆來維護中位數
        LazyHeap left = new LazyHeap((a, b) -> Integer.compare(b, a)); // 最大堆
        LazyHeap right = new LazyHeap(Integer::compare); // 最小堆

        for (int i = 0; i < n; i++) {
            int in = nums[i]; // 新加入的數字

            // 平衡兩個堆，使得左側堆的大小不小於右側堆
            if (left.size() == right.size()) {
                left.push(right.pushPop(in));
            } else {
                right.push(left.pushPop(in));
            }

            int l = i + 1 - k; // 窗口的左邊界
            if (l < 0) continue; // 窗口大小不足 k，跳過計算

            // 計算中位數對窗口內所有數的距離和
            long v = left.top();
            long s1 = v * left.size() - left.sum();
            long s2 = right.sum() - v * right.size();
            ans[l] = s1 + s2;

            // 移除即將離開窗口的數字
            int out = nums[l];
            if (out <= left.top()) {
                left.remove(out);
                if (left.size() < right.size()) {
                    left.push(right.pop());
                }
            } else {
                right.remove(out);
                if (left.size() > right.size() + 1) {
                    right.push(left.pop());
                }
            }
        }

        return ans; // 返回所有子數組的操作數
    }

    // 懶刪除的最小堆 / 最大堆類
    class LazyHeap extends PriorityQueue<Integer> {
        private final Map<Integer, Integer> removeCnt = new HashMap<>(); // 記錄待刪除的次數
        private int size = 0; // 記錄有效大小
        private long sum = 0; // 記錄堆中元素總和

        public LazyHeap(Comparator<Integer> comparator) {
            super(comparator);
        }

        public int size() {
            return size;
        }

        public long sum() {
            return sum;
        }

        // 懶刪除：標記 x 為待刪除
        public void remove(int x) {
            removeCnt.merge(x, 1, Integer::sum);
            size--;
            sum -= x;
        }

        // 真正執行刪除操作
        private void applyRemove() {
            while (removeCnt.getOrDefault(peek(), 0) > 0) {
                removeCnt.merge(poll(), -1, Integer::sum);
            }
        }

        // 取得堆頂元素
        public int top() {
            applyRemove();
            return peek();
        }

        // 彈出堆頂元素
        public int pop() {
            applyRemove();
            size--;
            sum -= peek();
            return poll();
        }

        // 插入新元素
        public void push(int x) {
            int c = removeCnt.getOrDefault(x, 0);
            if (c > 0) {
                removeCnt.put(x, c - 1);
            } else {
                offer(x);
            }
            size++;
            sum += x;
        }

        // 插入 x 並彈出堆頂
        public int pushPop(int x) {
            applyRemove();
            sum += x;
            offer(x);
            sum -= peek();
            return poll();
        }
    }


}









