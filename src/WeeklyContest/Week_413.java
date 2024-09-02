package WeeklyContest;

import java.util.*;

public class Week_413 {
    // https://leetcode.cn/problems/check-if-two-chessboard-squares-have-the-same-color/solutions/2900047/jian-ji-xie-fa-pythonjavacgo-by-endlessc-n9bf/
    public boolean checkTwoChessboards(String s, String t) {
        int a = (s.charAt(0) + s.charAt(1)) % 2;
        int b = (t.charAt(0) + t.charAt(1)) % 2;
        return a == b;
    }


    // https://leetcode.cn/problems/k-th-nearest-obstacle-queries/solutions/2900006/java-da-gen-dui-by-fy2m91yr4c-8la7/
    public int[] resultsArray(int[][] queries, int k) {
        //使用大根堆保存前K近的距离，这样堆顶就是第k近的距离，复杂度O(nlongn)
        int n = queries.length;

        // 创建一个大根堆，使用自定义比较器
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        int[] ans = new int[n];
        Arrays.fill(ans, -1);

        for (int i = 0; i < n; i++) {
            int x = queries[i][0];
            int y = queries[i][1];
            int distance = Math.abs(x) + Math.abs(y);
            // 大顶堆需要对所有元素进行排序
            maxHeap.add(distance);
            while (maxHeap.size() > k) {
                maxHeap.poll();
            }
            if (maxHeap.size() == k) {
                ans[i] = maxHeap.peek();
            }
        }

        return ans;
    }


    // https://leetcode.cn/problems/select-cells-in-grid-with-maximum-score/solutions/2899994/zhi-yu-zhuang-ya-dppythonjavacgo-by-endl-x27y/
    public int maxScore(List<List<Integer>> grid) {
        Map<Integer, Integer> pos = new HashMap<>();
        int m = grid.size();
        for (int i = 0; i < m; i++) {
            for (int x : grid.get(i)) {
                // 保存所有包含 x 的行號（壓縮成二進制數）
                pos.merge(x, 1 << i, (a, b) -> a | b);
            }
        }

        List<Integer> allNums = new ArrayList<>(pos.keySet());
        int n = allNums.size();
        int[][] memo = new int[n][1 << m];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(n - 1, 0, pos, allNums, memo);
    }

    private int dfs(int i, int j, Map<Integer, Integer> pos, List<Integer> allNums, int[][] memo) {
        if (i < 0) {
            return 0;
        }
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        // 不選 x
        int res = dfs(i - 1, j, pos, allNums, memo);
        // 枚舉選第 k 行的 x
        int x = allNums.get(i);
        for (int t = pos.get(x), lb; t > 0; t ^= lb) {
            lb = t & -t; // lb = 1<<k，其中 k 是行號
            if ((j & lb) == 0) { // 沒選過第 k 行的數
                res = Math.max(res, dfs(i - 1, j | lb, pos, allNums, memo) + x);
            }
        }
        return memo[i][j] = res; // 記憶化
    }


    public int maxScoreDP(List<List<Integer>> grid) {
        Map<Integer, Integer> pos = new HashMap<>();
        int m = grid.size();
        for (int i = 0; i < m; i++) {
            for (int x : grid.get(i)) {
                // 保存所有包含 x 的行號（壓縮成二進制數）
                pos.merge(x, 1 << i, (a, b) -> a | b);
            }
        }

        List<Integer> allNums = new ArrayList<>(pos.keySet());
        int u = 1 << m;
        int[][] f = new int[allNums.size() + 1][u];
        for (int i = 0; i < allNums.size(); i++) {
            int x = allNums.get(i);
            int posMask = pos.get(x);
            for (int j = 0; j < u; j++) {
                f[i + 1][j] = f[i][j]; // 不選 x
                for (int t = posMask, lb; t > 0; t ^= lb) {
                    lb = t & -t; // lb = 1<<k，其中 k 是行號
                    if ((j & lb) == 0) { // 沒選過第 k 行的數
                        f[i + 1][j] = Math.max(f[i + 1][j], f[i][j | lb] + x); // 選第 k 行的 x
                    }
                }
            }
        }
        return f[allNums.size()][0];
    }


    // https://leetcode.cn/problems/maximum-xor-score-subarray-queries/solutions/2899932/qu-jian-dp-tao-qu-jian-dppythonjavacgo-b-w4be/
    public int[] maximumSubarrayXor(int[] nums, int[][] queries) {
        int n = nums.length;
        int[][] f = new int[n][n];
        int[][] mx = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            f[i][i] = nums[i];
            mx[i][i] = nums[i];
            for (int j = i + 1; j < n; j++) {
                f[i][j] = f[i][j - 1] ^ f[i + 1][j];
                mx[i][j] = Math.max(f[i][j], Math.max(mx[i + 1][j], mx[i][j - 1]));
            }
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            ans[i] = mx[queries[i][0]][queries[i][1]];
        }
        return ans;
    }


}


