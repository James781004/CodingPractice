package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class SumOfBeauties {

    // https://leetcode.cn/problems/sum-of-beauty-in-the-array/solutions/1006001/qian-zhui-zui-da-zhi-hou-zhui-zui-xiao-z-h9qz/
    public int sumOfBeauties(int[] nums) {
        int n = nums.length;
        int[] sufMin = new int[n]; // 後綴最小值
        sufMin[n - 1] = nums[n - 1];
        for (int i = n - 2; i > 1; i--) {
            sufMin[i] = Math.min(sufMin[i + 1], nums[i]);
        }

        int ans = 0;
        int preMax = nums[0]; // 前綴最大值
        for (int i = 1; i < n - 1; i++) {
            int x = nums[i];
            // 此時 preMax 表示 [0, i-1] 中的最大值
            if (preMax < x && x < sufMin[i + 1]) {
                ans += 2;
            } else if (nums[i - 1] < x && x < nums[i + 1]) {
                ans++;
            }
            // 更新後 preMax 表示 [0, i] 中的最大值
            preMax = Math.max(preMax, x);
        }
        return ans;
    }

}
