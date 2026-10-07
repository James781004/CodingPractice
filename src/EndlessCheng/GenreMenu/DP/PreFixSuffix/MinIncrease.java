package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class MinIncrease {

    // https://leetcode.cn/problems/minimum-increase-to-maximize-special-indices/solutions/3944988/qian-hou-zhui-fen-jie-pythonjavacgo-by-e-8tcq/
    public long minIncrease(int[] nums) {
        int n = nums.length;
        long suf = 0; // suf[i] 表示 [i+3,n−2] 中的偶數下標的總操作次數
        for (int i = n - 2; i > 0; i -= 2) {
            // 對於要增大的數 nums[i]，必須增大到 max(nums[i−1],nums[i+1])+1，
            // 操作次數為 max(nums[i−1],nums[i+1])−nums[i]+1
            // 如果 i 已經是特殊下標，則上式 ≤0，所以還要把上式與 0 取最大值。
            suf += Math.max(Math.max(nums[i - 1], nums[i + 1]) - nums[i] + 1, 0);
        }

        if (n % 2 > 0) {
            // 修改所有奇數下標
            return suf;
        }

        long ans = suf; // 修改 [2,n-2] 中的所有偶數下標
        long pre = 0;
        // 枚舉修改 [1,i] 中的奇數下標，以及 [i+3,n-2] 中的偶數下標
        for (int i = 1; i < n - 1; i += 2) {
            pre += Math.max(Math.max(nums[i - 1], nums[i + 1]) - nums[i] + 1, 0);
            suf -= Math.max(Math.max(nums[i], nums[i + 2]) - nums[i + 1] + 1, 0); // 撤銷 i+1，撤銷後 suf 對應 [i+3,n-2]
            ans = Math.min(ans, pre + suf);
        }

        return ans;
    }

}
