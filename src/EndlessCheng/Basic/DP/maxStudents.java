package EndlessCheng.Basic.DP;

import java.util.Arrays;

public class maxStudents {

    // https://leetcode.cn/problems/maximum-students-taking-exam/solutions/2580043/jiao-ni-yi-bu-bu-si-kao-dong-tai-gui-hua-9y5k/
    public int maxStudents(char[][] seats) {
        int m = seats.length;
        int n = seats[0].length;
        int[] a = new int[m]; // a[i] 是第 i 排可用椅子的下標集合
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (seats[i][j] == '.') {
                    a[i] |= 1 << j;
                }
            }
        }

        int[][] memo = new int[m][1 << n];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // -1 表示沒有計算過
        }
        return dfs(m - 1, a[m - 1], memo, a);
    }

    private int dfs(int i, int j, int[][] memo, int[] a) {
        if (memo[i][j] != -1) { // 之前計算過
            return memo[i][j];
        }
        if (i == 0) {
            if (j == 0) { // 遞歸邊界
                return 0;
            }
            int lb = j & -j;
            return memo[i][j] = dfs(i, j & ~(lb * 3), memo, a) + 1; // 記憶化
        }
        int res = dfs(i - 1, a[i - 1], memo, a); // 第 i 排空著
        for (int s = j; s > 0; s = (s - 1) & j) { // 枚舉 j 的子集 s
            if ((s & (s >> 1)) == 0) { // s 沒有連續的 1
                int t = a[i - 1] & ~(s << 1 | s >> 1); // 去掉不能坐人的位置
                res = Math.max(res, dfs(i - 1, t, memo, a) + Integer.bitCount(s));
            }
        }
        return memo[i][j] = res; // 記憶化
    }


}
