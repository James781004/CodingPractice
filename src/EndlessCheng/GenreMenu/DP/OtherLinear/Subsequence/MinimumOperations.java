package EndlessCheng.GenreMenu.DP.OtherLinear.Subsequence;

import java.util.Arrays;
import java.util.List;

public class MinimumOperations {

    // https://leetcode.cn/problems/sorting-three-groups/solutions/2396466/liang-chong-fei-bao-li-zuo-fa-liszhuang-38zac/
    public int minimumOperations(List<Integer> nums) {
        int[] f = new int[4]; // 表示以元素 x 結尾的合法子序列的最長長度/個數/元素和，從子序列的倒數第二個數轉移過來
        for (int x : nums) {
            int mx = 0;
            for (int j = 1; j <= x; j++) {
                mx = Math.max(mx, f[j]);
            }
            f[x] = mx + 1; // +1 表示在以 j 結尾的子序列的末尾添加一個 x，得到以 x 結尾的子序列
        }
        return nums.size() - Arrays.stream(f).max().getAsInt();
    }


}
