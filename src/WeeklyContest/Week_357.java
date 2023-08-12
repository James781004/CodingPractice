package WeeklyContest;

import java.util.*;

public class Week_357 {
    // https://github.com/doocs/leetcode/blob/main/solution/2800-2899/2810.Faulty%20Keyboard/README.md
    public String finalString(String s) {
        StringBuilder t = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c == 'i') {
                t.reverse();
            } else {
                t.append(c);
            }
        }
        return t.toString();
    }


    // https://leetcode.cn/problems/check-if-it-is-possible-to-split-array/solutions/2375178/nao-jin-ji-zhuan-wan-by-endlesscheng-0l19/
    public boolean canSplitArray(List<Integer> nums, int m) {
        int n = nums.size();
        if (n <= 2) return true; // 先特判 n <= 2 的情況，這是滿足要求的。

        // 對於 n >= 3 的情況，無論按照何種方式分割，一定會在某個時刻，分割出一個長為 2 的子數組。
        // 如果 nums 中任何長為 2 的子數組的元素和都小於 mmm，那麼無法滿足要求。
        // 否則，可以用這個子數組作為「核心」，像剝洋蔥一樣，一個一個地去掉 nums 的首尾元素，
        // 最後得到這個子數組。由於子數組的元素和大於或等於 m，所以每次分割出一個元素時，
        // 剩余的子數組的元素和也必然是大於或等於 m 的，滿足要求。
        // 所以問題變成：判斷數組中是否有兩個相鄰數字和大於或等於 m。
        for (int i = 1; i < n; i++)
            if (nums.get(i - 1) + nums.get(i) >= m)
                return true;
        return false;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2800-2899/2811.Check%20if%20it%20is%20Possible%20to%20Split%20Array/README.md
    // 記憶化搜索
    private Boolean[][] f;
    private int[] s;
    private int m;

    public boolean canSplitArrayDP(List<Integer> nums, int m) {
        int n = nums.size();
        f = new Boolean[n][n];
        s = new int[n + 1];
        for (int i = 1; i <= n; ++i) {
            s[i] = s[i - 1] + nums.get(i - 1);
        }
        this.m = m;
        return dfs(0, n - 1);
    }

    private boolean dfs(int i, int j) {
        if (i == j) {
            return true;
        }
        if (f[i][j] != null) {
            return f[i][j];
        }
        for (int k = i; k < j; ++k) {
            boolean a = k == i || s[k + 1] - s[i] >= m;
            boolean b = k == j - 1 || s[j + 1] - s[k + 1] >= m;
            if (a && b && dfs(i, k) && dfs(k + 1, j)) {
                return f[i][j] = true;
            }
        }
        return f[i][j] = false;
    }


    // https://leetcode.cn/problems/find-the-safest-path-in-a-grid/solutions/2375565/jie-jin-on2-de-zuo-fa-duo-yuan-bfsdao-xu-r5um/
    private final static int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int maximumSafenessFactor(List<List<Integer>> grid) {
        int n = grid.size();
        List<int[]> q = new ArrayList<int[]>();
        int[][] dis = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid.get(i).get(j) > 0) {
                    q.add(new int[]{i, j});
                } else {
                    dis[i][j] = -1;
                }
            }
        }

        List<List<int[]>> groups = new ArrayList<>();
        groups.add(q); // 開頭加上空數組便於距離計算
        while (!q.isEmpty()) { // 多源 BFS，從所有 1 出發
            List<int[]> tmp = q;
            q = new ArrayList<>();
            for (int[] p : tmp) {
                for (int[] d : DIRS) {
                    int x = p[0] + d[0], y = p[1] + d[1];
                    if (0 <= x && x < n && 0 <= y && y < n && dis[x][y] < 0) {
                        q.add(new int[]{x, y});
                        dis[x][y] = groups.size();
                    }
                }
            }
            groups.add(q); // 相同 dis 分組記錄，注意尾部會多一個空數組 (因為越界所以沒有加入新元素)
        }

        // 並查集
        fa = new int[n * n];
        for (int i = 0; i < n * n; i++) fa[i] = i;

        // 答案不會超過 dis[i][j] 的最大值，可以倒序枚舉答案
        // 注意是從 groups.size() - 2 走到 1 ，因為之前頭尾有加上空數組
        for (int ans = groups.size() - 2; ans > 0; ans--) {
            for (int[] p : groups.get(ans)) {
                int i = p[0], j = p[1];

                // 假設答案為 d，可以把所有 dis[i][j] = d 的格子與其四周 >= d 的格子用並查集連起來，
                // 在答案為 d 的情況下，這些格子之間是可以互相到達的。
                for (int[] d : DIRS) {
                    int x = i + d[0], y = j + d[1];
                    if (0 <= x && x < n && 0 <= y && y < n && dis[x][y] >= dis[i][j])
                        fa[find(x * n + y)] = find(i * n + j);
                }
            }
            if (find(0) == find(n * n - 1)) // 寫這裡判斷更快些，判斷 (0,0) 和 (n−1,n−1) 是否連通
                return ans;
        }
        return 0;
    }

    // 並查集模板
    private int[] fa;

    private int find(int x) {
        if (fa[x] != x) fa[x] = find(fa[x]);
        return fa[x];
    }


    // https://www.youtube.com/watch?v=ZVDYtw6Fh4o
    // https://leetcode.cn/problems/maximum-elegance-of-a-k-length-subsequence/solutions/2375128/fan-hui-tan-xin-pythonjavacgo-by-endless-v2w1/
    public long findMaximumElegance(int[][] items, int k) {
        // 把利潤從大到小排序
        Arrays.sort(items, (a, b) -> b[0] - a[0]);
        long ans = 0, totalProfit = 0;
        Set<Integer> vis = new HashSet<>();
        Deque<Integer> duplicate = new ArrayDeque<>(); // 重複類別的利潤
        for (int i = 0; i < items.length; i++) {
            int profit = items[i][0], category = items[i][1];
            if (i < k) {
                totalProfit += profit;
                if (!vis.add(category)) // 出現重複類別，加入 duplicate
                    duplicate.push(profit);
            } else if (!duplicate.isEmpty() && vis.add(category)) {
                totalProfit += profit - duplicate.pop(); // 選一個重複類別中的最小利潤替換（即 duplicate 棧頂）
            } // else：比前面的利潤小，而且類別還重複了，選它只會讓 totalProfit 變小，vis.size() 不變，優雅度不會變大
            ans = Math.max(ans, totalProfit + (long) vis.size() * vis.size()); // 注意 1e5*1e5 會溢出
        }
        return ans;
    }
}
