package WeeklyContest;

import java.util.*;

public class Week_340 {
    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2614.Prime%20In%20Diagonal/README.md
    public int diagonalPrime(int[][] nums) {
        int n = nums.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (isPrime(nums[i][i])) {
                ans = Math.max(ans, nums[i][i]);
            }

            if (isPrime(nums[i][n - i - 1])) {
                ans = Math.max(ans, nums[i][n - i - 1]);
            }
        }
        return ans;
    }

    private boolean isPrime(int x) {
        if (x < 2) {
            return false;
        }
        for (int i = 2; i <= x / i; ++i) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }


    // https://leetcode.cn/problems/sum-of-distances/solution/zhao-ban-2602-ti-by-endlesscheng-6pbi/
    public long[] distance(int[] nums) {
        int n = nums.length;

        Map<Integer, List<Integer>> groups = new HashMap<>();
        for (int i = 0; i < n; ++i) // 相同元素分到同一組，記錄下標
            groups.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);

        long[] ans = new long[n];
        long[] s = new long[n + 1];
        for (List<Integer> a : groups.values()) {
            int m = a.size();
            for (int i = 0; i < m; ++i)
                s[i + 1] = s[i] + a.get(i); // 前綴和
            for (int i = 0; i < m; ++i) {
                int target = a.get(i);
                long left = (long) target * i - s[i]; // 藍色面積
                long right = s[m] - s[i] - (long) target * (m - i); // 綠色面積
                ans[target] = left + right;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimize-the-maximum-difference-of-pairs/solution/er-fen-da-an-tan-xin-by-endlesscheng-dlxv/
    // 看到「最大化最小值」或者「最小化最大值」就要想到二分答案，這是一個固定的套路。
    // 為什麼？一般來說，二分的值越大，越能/不能滿足要求；二分的值越小，越不能/能滿足要求，有單調性，可以二分。
    // 二分數對中的最大差值 mx。
    //
    // 由於下標和答案無關，可以先排序。為了讓差值最小，儘量選相鄰的元素。
    //
    // 算一算最多能匹配多少個數對：
    //
    // 1. 如果可以選 nums[0] 和 nums[1]，那麼答案等於「n−2 個數的最多數對個數」+1。
    // 2. 如果不選 nums[0]，那麼答案等於「n−1 個數的最多數對個數」，
    //    但這不會超過「n−3 個數的最多數對個數」+1。這裡 +1 表示選 nums[1] 和 nums[2]。
    // 3. 由於「n−2 個數的最多數對個數 ≥ n−3 個數的最多數對個數」，所以如果可以選 nums[0] 和 nums[1]，那麼直接選就行。
    // 4. 依此類推，不斷縮小問題規模。
    public int minimizeMax(int[] nums, int p) {
        Arrays.sort(nums);
        int n = nums.length, left = -1, right = nums[n - 1] - nums[0]; // 開區間
        while (left + 1 < right) { // 開區間
            int mid = (left + right) >>> 1, cnt = 0;
            for (int i = 0; i < n - 1; ++i)
                if (nums[i + 1] - nums[i] <= mid) { // 如果符合條件，兩數都選，然後跳兩格
                    ++cnt;
                    ++i;
                }
            if (cnt >= p) right = mid;
            else left = mid;
        }
        return right;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2617.Minimum%20Number%20of%20Visited%20Cells%20in%20a%20Grid/README.md
    public int minimumVisitedCells(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[] row = new int[m];
        int[] col = new int[n];
        Deque<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{1, 0, 0});
        while (!q.isEmpty()) {
            int[] p = q.poll();
            int i = p[1], j = p[2], dist = p[0];
            if (i == m - 1 && j == n - 1) {  // 抵達終點
                return dist;
            }
            int k = Math.max(row[i], j) + 1;
            for (; k < Math.min(n, j + grid[i][j] + 1); ++k) {
                q.offer(new int[]{dist + 1, i, k});
                row[i] = k;
            }
            k = Math.max(col[j], i) + 1;
            for (; k < Math.min(m, i + grid[i][j] + 1); ++k) {
                q.offer(new int[]{dist + 1, k, j});
                col[j] = k;
            }
        }
        return -1;
    }

    // https://leetcode.cn/problems/minimum-number-of-visited-cells-in-a-grid/solution/dan-diao-zhan-you-hua-dp-by-endlesscheng-mc50/
    // https://www.bilibili.com/video/BV1iN411w7my/
    public int minimumVisitedCellsDP(int[][] grid) {
        int m = grid.length, n = grid[0].length, mn = 0;
        List<int[]>[] colSt = new ArrayList[n]; // 每列的單調棧
        Arrays.setAll(colSt, e -> new ArrayList<int[]>());
        for (int i = m - 1; i >= 0; --i) {
            List<int[]> st = new ArrayList<>(); // 當前行的單調棧
            for (int j = n - 1; j >= 0; --j) {
                List<int[]> st2 = colSt[j];
                mn = Integer.MAX_VALUE;
                int g = grid[i][j];
                if (i == m - 1 && j == n - 1) // 特殊情況：已經是終點
                    mn = 0;
                else if (g > 0) {
                    // 在單調棧上二分
                    int k = search(st, j + g);
                    if (k < st.size()) mn = Math.min(mn, st.get(k)[0]);
                    k = search(st2, i + g);
                    if (k < st2.size()) mn = Math.min(mn, st2.get(k)[0]);
                }
                if (mn == Integer.MAX_VALUE) continue;

                ++mn; // 加上 (i,j) 這個格子
                // 插入單調棧
                while (!st.isEmpty() && mn <= st.get(st.size() - 1)[0])
                    st.remove(st.size() - 1);
                st.add(new int[]{mn, j});
                while (!st2.isEmpty() && mn <= st2.get(st2.size() - 1)[0])
                    st2.remove(st2.size() - 1);
                st2.add(new int[]{mn, i});
            }
        }
        return mn < Integer.MAX_VALUE ? mn : -1;
    }

    // 見 https://www.bilibili.com/video/BV1AP41137w7/
    private int search(List<int[]> st, int target) {
        int left = -1, right = st.size(); // 開區間 (left, right)
        while (left + 1 < right) { // 區間不為空
            int mid = (left + right) >>> 1;
            if (st.get(mid)[1] > target) left = mid; // 範圍縮小到 (mid, right)
            else right = mid; // 範圍縮小到 (left, mid)
        }
        return right;
    }
}
