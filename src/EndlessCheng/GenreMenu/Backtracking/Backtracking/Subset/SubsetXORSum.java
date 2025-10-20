package EndlessCheng.GenreMenu.Backtracking.Backtracking.Subset;

public class SubsetXORSum {

    // https://leetcode.cn/problems/sum-of-all-subset-xor-totals/solutions/3640634/python3javacgotypescript-yi-ti-shuang-ji-8ig6/
    private int ans;
    private int[] nums;

    public int subsetXORSum(int[] nums) {
        this.nums = nums;
        dfs(0, 0);
        return ans;
    }

    private void dfs(int i, int s) {
        if (i >= nums.length) {
            ans += s;
            return;
        }
        dfs(i + 1, s); // 將 nums 的第 i 個元素不加入當前子集
        dfs(i + 1, s ^ nums[i]); // 將 nums 的第 i 個元素加入當前子集
    }


}
