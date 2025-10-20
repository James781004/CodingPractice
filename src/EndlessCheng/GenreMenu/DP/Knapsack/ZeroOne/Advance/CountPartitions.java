package EndlessCheng.GenreMenu.DP.Knapsack.ZeroOne.Advance;

public class CountPartitions {

    // https://leetcode.cn/problems/number-of-great-partitions/solutions/2032009/ni-xiang-si-wei-01-bei-bao-fang-an-shu-p-v47x/
    private static final int MOD = (int) 1e9 + 7;

    public int countPartitions(int[] nums, int k) {
        var sum = 0L;
        for (var x : nums) sum += x;
        if (sum < k * 2) return 0; // 如果 nums 的所有元素之和小於 2k，則不存在好分區
        var ans = 1;
        var f = new int[k]; // 第一個組的元素和小於 k 的方案數。根據對稱性，只需要計算第一個組的元素和小於 k 的方案數，然後乘 2 即可
        f[0] = 1;
        for (var x : nums) {
            ans = ans * 2 % MOD; // 所有分區的數目，即 2^n，這裡 n 為 nums 的長度
            for (var j = k - 1; j >= x; --j)
                f[j] = (f[j] + f[j - x]) % MOD;
        }

        // 答案為所有分區的數目減去壞分區的數目，即 2^n−bad，這裡 n 為 nums 的長度
        for (var x : f)
            ans = (ans - x * 2 % MOD + MOD) % MOD; // 保證答案非負
        return ans;
    }


}
