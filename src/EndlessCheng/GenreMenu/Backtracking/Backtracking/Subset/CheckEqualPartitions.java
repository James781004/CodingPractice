package EndlessCheng.GenreMenu.Backtracking.Backtracking.Subset;

public class CheckEqualPartitions {

    // https://leetcode.cn/problems/partition-array-into-two-equal-product-subsets/solutions/3690735/er-jin-zhi-mei-ju-pythonjavacgo-by-endle-w78j/
    public boolean checkEqualPartitions(int[] nums, long target) {
        return dfs(0, 1, 1, nums, target);
    }

    private boolean dfs(int i, long mul1, long mul2, int[] nums, long target) {
        if (mul1 > target || mul2 > target) {
            return false;
        }
        if (i == nums.length) {
            return mul1 == target && mul2 == target;
        }
        return dfs(i + 1, mul1 * nums[i], mul2, nums, target) ||
                dfs(i + 1, mul1, mul2 * nums[i], nums, target);
    }


}
