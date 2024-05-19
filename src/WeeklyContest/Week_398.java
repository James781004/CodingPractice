package WeeklyContest;

import java.util.HashMap;
import java.util.Map;

public class Week_398 {
    // https://leetcode.cn/problems/special-array-i/solutions/2782869/bian-li-pan-duan-xiang-lin-yuan-su-pytho-is9h/
    public boolean isArraySpecial(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] % 2 == nums[i] % 2) {
                return false;
            }
        }
        return true;
    }


    // https://leetcode.cn/problems/special-array-ii/solutions/2782830/qian-zhui-he-wei-yun-suan-pythonjavacgo-3pvya/
    public boolean[] isArraySpecial(int[] nums, int[][] queries) {
        int[] s = new int[nums.length];
        for (int i = 1; i < nums.length; i++) {
            s[i] = s[i - 1] + ((nums[i] ^ nums[i - 1] ^ 1) & 1);
        }
        boolean[] ans = new boolean[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            ans[i] = s[q[0]] == s[q[1]];
        }
        return ans;
    }


    // https://leetcode.cn/problems/sum-of-digit-differences-of-all-pairs/solutions/2782811/chai-wei-suan-gong-xian-yi-ci-bian-li-by-46rf/
    // 每一位可以拆開分別計算，此時問題變成：
    // 給你 n 個 0 到 9 的數字，其中有多少個不同的數對？
    public long sumDigitDifferences(int[] nums) {
        long ans = 0;
        int[][] cnt = new int[Integer.toString(nums[0]).length()][10];
        for (int k = 0; k < nums.length; k++) {
            int x = nums[k];
            for (int i = 0; x > 0; x /= 10, i++) {
                int d = x % 10;
                ans += k - cnt[i][d]++; // 前面有這麼多個不同於 d 的數
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/find-number-of-ways-to-reach-the-k-th-stair/solutions/2782792/liang-chong-fang-fa-ji-yi-hua-sou-suo-zu-j227/
    private final Map<Long, Integer> memo = new HashMap<>();

    public int waysToReachStair(int k) {
        return dfs(1, 0, 0, k);
    }

    // 根據題意，需要三個參數來表示當前的狀態：
    // i：當前位於台階 i。
    // j：已經使用了 j 次第二種操作。
    // preDown：上一次操作是否使用了操作一。
    // 將其定義為 dfs(i,j,preDown)，表示在該狀態下，有多少種方案可以到達台階 k。
    // 枚舉當前使用哪個操作：
    // 使用操作一：前提是 preDown=false 且 i>0。使用操作一後，要解決的子問題是 dfs(i−1,j,true)，加入返回值中。
    // 使用操作二：要解決的子問題是 dfs(i+2^j,j+1,false)，加入返回值中。
    // 此外，如果 i=k，可以把返回值加一。
    private int dfs(int i, int j, int preDown, int k) {
        if (i > k + 1) {
            return 0;
        }
        long p = ((long) i << 32) | j << 1 | preDown; // 用一個 long 表示狀態
        if (memo.containsKey(p)) { // 之前算過了
            return memo.get(p);
        }
        int res = (i == k) ? 1 : 0;
        res += dfs(i + (1 << j), j + 1, 0, k); // 操作二
        if (preDown == 0 && i > 0) {
            res += dfs(i - 1, j, 1, k); // 操作一
        }
        memo.put(p, res); // 記憶化
        return res;
    }

}


