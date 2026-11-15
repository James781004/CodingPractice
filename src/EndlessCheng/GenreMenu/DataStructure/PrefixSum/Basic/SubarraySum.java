package EndlessCheng.GenreMenu.DataStructure.PrefixSum.Basic;

public class SubarraySum {

    // https://leetcode.cn/problems/sum-of-variable-length-subarrays/solutions/3051553/liang-chong-fang-fa-bao-li-chai-fen-shu-iuwyi/
    public int subarraySum(int[] nums) {
        int n = nums.length;
        int[] s = new int[n + 1];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + nums[i];
            ans += s[i + 1] - s[Math.max(i - nums[i], 0)];
        }
        return ans;
    }

}
