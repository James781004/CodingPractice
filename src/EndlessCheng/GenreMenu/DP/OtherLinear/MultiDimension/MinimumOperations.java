package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.List;

public class MinimumOperations {

    // https://leetcode.cn/problems/sorting-three-groups/solutions/2396466/liang-chong-fei-bao-li-zuo-fa-liszhuang-38zac/
    public int minimumOperations(List<Integer> nums) {
        int n = nums.size();
        int[][] f = new int[n + 1][4]; // 定義 f[i+1][j] 表示 nums[0] 到 nums[i] 的最長非遞減子序列的長度，其中子序列最後一個數 ≤j
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


    public int minimumOperations2(List<Integer> nums) {
        int[] f = new int[4];
        for (int x : nums) {
            f[x]++;
            f[2] = Math.max(f[2], f[1]);
            f[3] = Math.max(f[3], f[2]);
        }
        return nums.size() - f[3];
    }


}
