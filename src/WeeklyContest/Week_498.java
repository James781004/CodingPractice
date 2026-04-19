package WeeklyContest;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Week_498 {

    // https://leetcode.cn/problems/smallest-stable-index-ii/solutions/3954673/qian-hou-zhui-fen-jie-pythonjavacgo-by-e-by77/
    public int firstStableIndex(int[] nums, int k) {
        int n = nums.length;
        int[] sufMin = new int[n]; // 後綴最小值
        sufMin[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            sufMin[i] = Math.min(sufMin[i + 1], nums[i]);
        }

        int preMax = 0; // 前綴最大值
        for (int i = 0; i < n; i++) {
            preMax = Math.max(preMax, nums[i]);
            if (preMax - sufMin[i] <= k) {
                return i;
            }
        }
        return -1;
    }


    // https://leetcode.cn/problems/multi-source-flood-fill/solutions/3954669/pai-xu-zhi-hou-bfspythonjavacgo-by-endle-4mp0/
    private static final int[][] DIRS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}}; // 左右上下

    public int[][] colorGrid(int n, int m, int[][] sources) {
        Arrays.sort(sources, (a, b) -> b[2] - a[2]);

        int[][] ans = new int[n][m];
        Queue<int[]> q = new ArrayDeque<>();
        for (int[] p : sources) {
            ans[p[0]][p[1]] = p[2]; // 初始顏色
            q.offer(p);
        }

        while (!q.isEmpty()) {
            int[] p = q.poll();
            int i = p[0];
            int j = p[1];
            int c = p[2];
            for (int[] dir : DIRS) { // 向四個方向擴散
                int x = i + dir[0];
                int y = j + dir[1];
                if (0 <= x && x < n && 0 <= y && y < m && ans[x][y] == 0) { // (x, y) 未著色
                    ans[x][y] = c; // 著色
                    q.offer(new int[]{x, y, c}); // 繼續擴散
                }
            }
        }

        return ans;
    }


    // https://leetcode.cn/problems/count-good-integers-on-a-grid-path/solutions/3954671/shang-xia-jie-shu-wei-dppythonjavacgo-by-feyc/
    public long countGoodIntegersOnPath(long l, long r, String directions) {
        char[] lowS = String.valueOf(l).toCharArray();
        char[] highS = String.valueOf(r).toCharArray();
        int n = highS.length;

        boolean[] inPath = new boolean[n];
        int pos = n - 16; // 右下角是下標 n-1，那麼左上角是下標 n-16
        for (char d : directions.toCharArray()) {
            if (pos >= 0) { // 只需要對網格圖中的後 n 個格子做標記
                inPath[pos] = true; // 標記在路徑中的格子
            }
            pos += d == 'R' ? 1 : 4; // 往下相當於往右數 4 個位置
        }
        inPath[n - 1] = true; // 終點一定在路徑中

        long[][] memo = new long[n][10];
        for (long[] row : memo) {
            Arrays.fill(row, -1);
        }

        return dfs(0, 0, true, true, lowS, highS, inPath, memo);
    }

    // dfs(i,pre,limitLow,limitHigh) 表示構造第 i 位及其之後數位的合法方案數
    // pre 表示前一個在路徑中的數字，初始值為 0
    // limitHigh 表示當前是否受到了 high 的約束（當前要填的數字不能超過 high）
    // limitLow 表示當前是否受到了 low 的約束（當前要填的數字不能低於 low）
    private long dfs(int i, int pre, boolean limitLow, boolean limitHigh, char[] lowS, char[] highS, boolean[] inPath, long[][] memo) {
        if (i == highS.length) { // 成功到達終點
            return 1; // 找到了一個好整數
        }

        if (!limitLow && !limitHigh && memo[i][pre] >= 0) {
            return memo[i][pre];
        }

        int diff = highS.length - lowS.length;
        int lo = limitLow && i >= diff ? lowS[i - diff] - '0' : 0;
        int hi = limitHigh ? highS[i] - '0' : 9;

        long res = 0;
        // 如果當前位置在路徑中，那麼當前位置填的數必須 >= pre
        int start = inPath[i] ? Math.max(lo, pre) : lo;

        // 狀態轉移：枚舉第 i 位填數字 d=lo,lo+1…,hi。
        // 繼續遞歸，把 i 加一，如果 i 在路徑中，那麼把 pre 置為 d，否則 pre 不變
        for (int d = start; d <= hi; d++) {
            res += dfs(i + 1,
                    inPath[i] ? d : pre,
                    limitLow && d == lo,
                    limitHigh && d == hi,
                    lowS, highS, inPath, memo);
        }

        if (!limitLow && !limitHigh) {
            memo[i][pre] = res;
        }
        return res;
    }


}









