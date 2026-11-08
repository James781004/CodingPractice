package EndlessCheng.GenreMenu.DataStructure.EnumerateMiddle.Advance;

public class CountQuadruplets {

    // https://leetcode.cn/problems/count-increasing-quadruplets/solutions/2080632/you-ji-qiao-de-mei-ju-yu-chu-li-pythonja-exja/
    public long countQuadruplets(int[] nums) {
        int n = nums.length;
        int[][] great = new int[n][n + 1];
        for (int k = n - 2; k > 0; k--) {
            great[k] = great[k + 1].clone();
            for (int x = 1; x < nums[k + 1]; x++) {
                great[k][x]++;
            }
        }

        long ans = 0;
        for (int j = 1; j < n - 2; j++) {
            for (int k = j + 1; k < n - 1; k++) {
                int x = nums[k];
                if (nums[j] > x) {
                    ans += (long) (x - n + 1 + j + great[j][x]) * great[k][nums[j]];
                }
            }
        }
        return ans;
    }

}
