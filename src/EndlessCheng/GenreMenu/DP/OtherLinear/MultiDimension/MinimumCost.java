package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

public class MinimumCost {

    // https://leetcode.cn/problems/minimum-cost-for-cutting-cake-i/solutions/2843069/fei-bao-li-tan-xin-zuo-fa-pythonjavacgo-mae7e/
    public int minimumCost(int m, int n, int[] horizontalCut, int[] verticalCut) {
        int[][][][] memo = new int[m + 1][n + 1][m + 1][n + 1];
        return dfs(0, 0, m, n, horizontalCut, verticalCut, memo);
    }

    private int dfs(int sr, int sc, int er, int el, int[] horizontalCut, int[] verticalCut, int[][][][] memo) {
        if (sr + 1 == er && sc + 1 == el) {
            return 0;
        }
        if (memo[sr][sc][er][el] > 0) {
            return memo[sr][sc][er][el];
        }
        int res = Integer.MAX_VALUE / 2;
        for (int i = sr + 1; i <= er - 1; i++) {
            res = Math.min(res, dfs(sr, sc, i, el, horizontalCut, verticalCut, memo)
                    + dfs(i, sc, er, el, horizontalCut, verticalCut, memo) + horizontalCut[i - 1]);
        }
        for (int i = sc + 1; i <= el - 1; i++) {
            res = Math.min(res, dfs(sr, sc, er, i, horizontalCut, verticalCut, memo)
                    + dfs(sr, i, er, el, horizontalCut, verticalCut, memo) + verticalCut[i - 1]);
        }
        return memo[sr][sc][er][el] = res;
    }

}
