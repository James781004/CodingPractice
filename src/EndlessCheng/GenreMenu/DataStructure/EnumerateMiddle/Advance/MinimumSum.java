package EndlessCheng.GenreMenu.DataStructure.EnumerateMiddle.Advance;

public class MinimumSum {

    // https://leetcode.cn/problems/minimum-sum-of-mountain-triplets-ii/solutions/2493548/mei-ju-numsj-qian-hou-zhui-fen-jie-pytho-tskf/
    public int minimumSum(int[] nums) {
        int n = nums.length;
        int[] suf = new int[n]; // 後綴最小值
        suf[n - 1] = nums[n - 1];
        for (int i = n - 2; i > 1; i--) {
            suf[i] = Math.min(suf[i + 1], nums[i]);
        }

        int ans = Integer.MAX_VALUE;
        int pre = nums[0]; // 前綴最小值
        for (int j = 1; j < n - 1; j++) {
            if (pre < nums[j] && nums[j] > suf[j + 1]) { // 山形
                ans = Math.min(ans, pre + nums[j] + suf[j + 1]); // 更新答案
            }
            pre = Math.min(pre, nums[j]);
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

}
