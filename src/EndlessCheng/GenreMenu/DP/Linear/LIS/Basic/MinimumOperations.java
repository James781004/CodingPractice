package EndlessCheng.GenreMenu.DP.Linear.LIS.Basic;

import java.util.List;

public class MinimumOperations {

    // https://leetcode.cn/problems/sorting-three-groups/solutions/2396466/liang-chong-fei-bao-li-zuo-fa-liszhuang-38zac/
    public int minimumOperations(List<Integer> nums) {
        int n = nums.size();
        int[][] f = new int[n + 1][4];
        for (int i = 0; i < n; i++) {
            int x = nums.get(i);
            for (int j = 1; j <= 3; j++) {
                if (j < x) {
                    f[i + 1][j] = f[i][j];
                } else {
                    f[i + 1][j] = Math.max(f[i][j], f[i][x] + 1);
                }
            }
        }
        return n - f[n][3];
    }


}
