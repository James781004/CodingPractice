package WeeklyContest;

import java.util.Arrays;

public class Week_467 {

    // https://leetcode.cn/problems/earliest-time-to-finish-one-task/solutions/3781353/bian-li-pythonjavacgo-by-endlesscheng-urq7/
    public int earliestTime(int[][] tasks) {
        int ans = Integer.MAX_VALUE;
        for (int[] t : tasks) {
            ans = Math.min(ans, t[0] + t[1]);
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximize-sum-of-at-most-k-distinct-elements/solutions/3781350/pai-xu-qu-zhong-pythonjavacgo-by-endless-toja/
    public int[] maxKDistinct(int[] nums, int k) {
        Arrays.sort(nums);

        int uniques = removeDuplicates(nums);
        int size = Math.min(uniques, k);

        int[] ans = new int[size];
        for (int i = 0; i < size; i++) {
            ans[i] = nums[uniques - 1 - i]; // 題目要求從大到小
        }
        return ans;
    }

    // 26. 刪除有序數組中的重復項
    private int removeDuplicates(int[] nums) {
        int k = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) { // nums[i] 不是重復項
                nums[k++] = nums[i]; // 保留 nums[i]
            }
        }
        return k;
    }


    // https://leetcode.cn/problems/subsequence-sum-after-capping-elements/solutions/3781344/0-1-bei-bao-shuang-zhi-zhen-pythonjavacg-j4ca/
    public boolean[] subsequenceSumAfterCapping(int[] nums, int k) {
        Arrays.sort(nums);

        int n = nums.length;
        boolean[] ans = new boolean[n];
        boolean[] f = new boolean[k + 1];
        f[0] = true; // 不選元素，和為 0

        int i = 0;
        for (int x = 1; x <= n; x++) {
            // 增量地考慮所有等於 x 的數
            while (i < n && nums[i] == x) {
                for (int j = k; j >= nums[i]; j--) {
                    f[j] = f[j] || f[j - nums[i]]; // 0-1 背包：不選 or 選
                }
                i++;
            }

            // 枚舉（從大於 x 的數中）選了 j 個 x
            for (int j = 0; j <= Math.min(n - i, k / x); j++) {
                if (f[k - j * x]) {
                    ans[x - 1] = true;
                    break;
                }
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/number-of-stable-subsequences/solutions/3781347/he-fa-zi-xu-lie-dppythonjavacgo-by-endle-7sz0/
    public int countStableSubsequences(int[] nums) {
        final int MOD = 1_000_000_007;
        long[][] f = new long[2][2];
        for (int x : nums) {
            x %= 2;
            f[x][1] = (f[x][1] + f[x][0]) % MOD;
            f[x][0] = (f[x][0] + f[x ^ 1][0] + f[x ^ 1][1] + 1) % MOD;
        }
        return (int) ((f[0][0] + f[0][1] + f[1][0] + f[1][1]) % MOD);
    }


}









