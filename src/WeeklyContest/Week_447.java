package WeeklyContest;

import java.util.*;

public class Week_447 {

    // https://leetcode.cn/problems/count-covered-buildings/solutions/3663296/pai-xu-er-fen-cha-zhao-pythonjavacgo-by-z2c5d/
    public int countCoveredBuildings(int n, int[][] buildings) {
        Map<Integer, List<Integer>> row = new HashMap<>();
        Map<Integer, List<Integer>> col = new HashMap<>();
        for (int[] p : buildings) {
            int x = p[0], y = p[1];
            row.computeIfAbsent(x, k -> new ArrayList<>()).add(y);
            col.computeIfAbsent(y, k -> new ArrayList<>()).add(x);
        }

        // 其實只需要求最小值和最大值就行
        for (List<Integer> a : row.values()) {
            Collections.sort(a);
        }
        for (List<Integer> a : col.values()) {
            Collections.sort(a);
        }

        int ans = 0;
        for (int[] p : buildings) {
            int x = p[0], y = p[1];
            if (isInner(row.get(x), y) && isInner(col.get(y), x)) {
                ans++;
            }
        }
        return ans;
    }

    private boolean isInner(List<Integer> a, int x) {
        return a.get(0) < x && x < a.get(a.size() - 1); // 左右都有建築
    }


    // https://leetcode.cn/problems/path-existence-queries-in-a-graph-i/solutions/3663278/er-fen-cha-zhao-jian-ji-xie-fa-pythonjav-0he5/
    public boolean[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        List<Integer> idx = new ArrayList<>();
        for (int i = 0; i < n - 1; i++) {
            if (nums[i + 1] - nums[i] > maxDiff) {
                idx.add(i); // 間斷點
            }
        }

        boolean[] ans = new boolean[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            ans[i] = binarySearch(idx, q[0]) == binarySearch(idx, q[1]);
        }
        return ans;
    }

    private int binarySearch(List<Integer> idx, int target) {
        // idx 中沒有重復元素，可以用庫函數二分
        int i = Collections.binarySearch(idx, target);
        return i < 0 ? ~i : i;
    }

    // https://leetcode.cn/problems/concatenated-divisibility/solutions/3663246/quan-pai-lie-bao-sou-pythonjavacgo-by-en-l4zv/
    public int[] concatenatedDivisibility(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        int[] pow10 = new int[n];
        for (int i = 0; i < n; i++) {
            pow10[i] = (int) Math.pow(10, Integer.toString(nums[i]).length());
        }

        int[] ans = new int[n];
        boolean[][] vis = new boolean[1 << n][k];
        if (!dfs((1 << n) - 1, 0, nums, pow10, k, vis, ans)) {
            return new int[]{};
        }
        return ans;
    }

    private boolean dfs(int s, int x, int[] nums, int[] pow10, int k, boolean[][] vis, int[] ans) {
        if (s == 0) {
            return x == 0;
        }
        if (vis[s][x]) {
            return false;
        }
        vis[s][x] = true;
        // 枚舉在 s 中的下標 i
        for (int i = 0; i < nums.length; i++) {
            if ((s & (1 << i)) > 0 && dfs(s ^ (1 << i), (x * pow10[i] + nums[i]) % k, nums, pow10, k, vis, ans)) {
                ans[nums.length - Integer.bitCount(s)] = nums[i];
                return true;
            }
        }
        return false;
    }


    // https://leetcode.cn/problems/path-existence-queries-in-a-graph-ii/solutions/3663266/pai-xu-shuang-zhi-zhen-bei-zeng-pythonja-ckht/
    public int[] pathExistenceQueriesII(int n, int[] nums, int maxDiff, int[][] queries) {
        Integer[] idx = new Integer[n];
        Arrays.setAll(idx, i -> i);
        Arrays.sort(idx, (i, j) -> nums[i] - nums[j]);

        // rank[i] 表示 nums[i] 是 nums 中的第幾小，或者說節點 i 在 idx 中的下標
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            rank[idx[i]] = i;
        }

        // 雙指針，從第 i 小的數開始，向左一步，最遠能跳到第 left 小的數
        int mx = 32 - Integer.numberOfLeadingZeros(n);
        int[][] pa = new int[n][mx];
        int left = 0;
        for (int i = 0; i < n; i++) {
            while (nums[idx[i]] - nums[idx[left]] > maxDiff) {
                left++;
            }
            pa[i][0] = left;
        }

        // 倍增
        for (int i = 0; i < mx - 1; i++) {
            for (int x = 0; x < n; x++) {
                int p = pa[x][i];
                pa[x][i + 1] = pa[p][i];
            }
        }

        int[] ans = new int[queries.length];
        for (int qi = 0; qi < queries.length; qi++) {
            int l = queries[qi][0];
            int r = queries[qi][1];
            if (l == r) { // 不用跳
                continue;
            }
            l = rank[l];
            r = rank[r];
            if (l > r) {
                int tmp = l;
                l = r;
                r = tmp; // 保證 l 在 r 左邊
            }
            // 從 r 開始，向左跳到 l
            int res = 0;
            for (int k = mx - 1; k >= 0; k--) {
                if (pa[r][k] > l) {
                    res |= 1 << k;
                    r = pa[r][k];
                }
            }
            ans[qi] = pa[r][0] > l ? -1 : res + 1; // 再跳一步就能到 l
        }
        return ans;
    }

    
}









