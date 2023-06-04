package WeeklyContest;

import java.util.*;

class Week_347 {
    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2710.Remove%20Trailing%20Zeros%20From%20a%20String/README.md
    public String removeTrailingZeros(String num) {
        int i = num.length() - 1;
        while (num.charAt(i) == '0') {
            --i;
        }
        return num.substring(0, i + 1);
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2711.Difference%20of%20Number%20of%20Distinct%20Values%20on%20Diagonals/README.md
    public int[][] differenceOfDistinctValues(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] ans = new int[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                int x = i, y = j;
                Set<Integer> s = new HashSet<>();
                while (x > 0 && y > 0) {  // top left
                    s.add(grid[--x][--y]);
                }
                int tl = s.size();
                x = i;
                y = j;
                s.clear();
                while (x < m - 1 && y < n - 1) {  // bottom right
                    s.add(grid[++x][++y]);
                }
                int br = s.size();
                ans[i][j] = Math.abs(tl - br);
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2712.Minimum%20Cost%20to%20Make%20All%20Characters%20Equal/README.md
    // 貪心，每次都貪心左邊，或者貪心右邊
    // 局部最優也就是全局最優
    public long minimumCost(String s) {
        long ans = 0;
        int n = s.length();
        for (int i = 1; i < n; ++i) {
            // 如果這兩個字符都相等了，那麼就不用翻轉了
            // 如果要翻轉，可以想像成只有一列的「類黑白棋遊戲」，只是它只能一次選一邊全部換色
            // 如果取前者，就是翻轉左側  注意，是翻轉[0:i-1]
            // 如果取後者，就是翻轉右側   翻轉[i:len-1]
            if (s.charAt(i) != s.charAt(i - 1)) {
                ans += Math.min(i, n - i);
            }
        }
        return ans;
    }


    // https://www.bilibili.com/video/BV1fo4y1T7MQ/?spm_id_from=333.999.list.card_archive.click
    // https://leetcode.cn/problems/maximum-strictly-increasing-cells-in-a-matrix/solution/dong-tai-gui-hua-you-hua-pythonjavacgo-b-axv0/
    public int maxIncreasingCells(int[][] mat) {
        Map<Integer, List<int[]>> g = new TreeMap<>();
        int m = mat.length, n = mat[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // mat[i][j] 值相同元素放在同一組，記錄位置
                g.computeIfAbsent(mat[i][j], k -> new ArrayList<>()).add(new int[]{i, j});
            }
        }

        int ans = 0;
        int[] rowMax = new int[m], colMax = new int[n];
        for (List<int[]> pos : g.values()) {  // TreeMap 自動從小到大遍歷
            int[] mx = new int[pos.size()];  // 先把最大值算出來，再根據先前保存的(i, j)更新 rowMax 和 colMax
            for (int i = 0; i < pos.size(); i++) {
                mx[i] = Math.max(rowMax[pos.get(i)[0]], colMax[pos.get(i)[1]]) + 1;
                ans = Math.max(ans, mx[i]);
            }
            for (int k = 0; k < pos.size(); k++) {
                int i = pos.get(k)[0], j = pos.get(k)[1];
                rowMax[i] = Math.max(rowMax[i], mx[k]); // 更新第 i 行的最大 f 值
                colMax[j] = Math.max(colMax[j], mx[k]); // 更新第 j 列的最大 f 值
            }
        }

        return ans;
    }
}

