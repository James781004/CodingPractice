package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class CountQuadruplets {

    // https://leetcode.cn/problems/count-increasing-quadruplets/solutions/2080632/you-ji-qiao-de-mei-ju-yu-chu-li-pythonja-exja/
    public long countQuadruplets(int[] nums) {
        int n = nums.length;
        int[][] great = new int[n][n + 1]; // 在 k 右側的比 x=nums[j] 大的元素個數，記作 great[k][x]
        for (int k = n - 2; k >= 2; k--) {
            great[k] = great[k + 1].clone();
            for (int x = 1; x < nums[k + 1]; x++) {
                great[k][x]++;
            }
        }

        long ans = 0;
        int[] less = new int[n + 1]; // 在 j 左側的比 x=nums[k] 小的元素個數，記作 less[j][x]
        for (int j = 1; j < n - 2; j++) {
            for (int x = nums[j - 1] + 1; x <= n; x++) {
                less[x]++;
            }
            for (int k = j + 1; k < n - 1; k++) {
                if (nums[j] > nums[k]) {
                    ans += less[nums[k]] * great[k][nums[j]];
                }
            }
        }
        return ans;
    }

}
