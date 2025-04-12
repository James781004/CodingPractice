package EndlessCheng.GenreMenu.Graph.Floyd;

import java.util.Arrays;

public class FindTheCity {

    // https://leetcode.cn/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/solutions/2525946/dai-ni-fa-ming-floyd-suan-fa-cong-ji-yi-m8s51/
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] w = new int[n][n];
        for (int[] row : w) {
            Arrays.fill(row, Integer.MAX_VALUE / 2); // 防止加法溢出
        }
        for (int[] e : edges) {
            int x = e[0], y = e[1], wt = e[2];
            w[x][y] = w[y][x] = wt;
        }
        int[][][] memo = new int[n][n][n];

        int ans = 0;
        int minCnt = n;
        for (int i = 0; i < n; i++) {
            int cnt = 0;
            for (int j = 0; j < n; j++) {
                if (j != i && dfs(n - 1, i, j, memo, w) <= distanceThreshold) {
                    cnt++;
                }
            }
            if (cnt <= minCnt) { // 相等時取最大的 i
                minCnt = cnt;
                ans = i;
            }
        }
        return ans;
    }

    // 表示從 i 到 j 的最短路長度，並且這條最短路的中間節點編號都 <= k
    // 注意中間節點不包含 i 和 j
    private int dfs(int k, int i, int j, int[][][] memo, int[][] w) {
        // 遞歸邊界：dfs(−1,i,j)=w[i][j]。k=−1 表示 i 和 j 之間沒有任何中間節點，
        // 此時最短路長度只能是連接 i 和 j 的邊的邊權，即 w[i][j]。
        // 如果沒有連接 i 和 j 的邊，則 w[i][j]=∞
        if (k < 0) {
            return w[i][j];
        }
        if (memo[k][i][j] != 0) { // 之前計算過
            return memo[k][i][j];
        }

        // 1. 不選 k，那麼中間節點的編號都 ≤k−1，即 dfs(k,i,j)=dfs(k−1,i,j)
        // 2. 選 k，問題分解成從 i 到 k 的最短路，以及從 k 到 j 的最短路
        // 由於這兩條最短路的中間節點都不包含 k，所以中間節點的編號都 ≤k−1，
        // 故得到 dfs(k,i,j)=dfs(k−1,i,k)+dfs(k−1,k,j)
        return memo[k][i][j] = Math.min(dfs(k - 1, i, j, memo, w),
                dfs(k - 1, i, k, memo, w) + dfs(k - 1, k, j, memo, w));
    }

}
