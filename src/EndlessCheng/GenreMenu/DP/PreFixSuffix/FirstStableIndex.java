package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class FirstStableIndex {

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

}
