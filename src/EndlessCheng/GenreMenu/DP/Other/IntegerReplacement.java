package EndlessCheng.GenreMenu.DP.Other;

import java.util.HashMap;
import java.util.Map;

public class IntegerReplacement {

    // https://leetcode.cn/problems/integer-replacement/solutions/1110235/tong-ge-lai-shua-ti-la-yi-ti-san-jie-bao-u05f/
    public int integerReplacement(int n) {
        Map<Long, Integer> memo = new HashMap<>();
        return dfs(n, memo);
    }

    private int dfs(long n, Map<Long, Integer> memo) {
        if (n == 1) return 0;

        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        int ans = 0;

        if (n % 2 == 0) {
            ans = dfs(n / 2, memo) + 1;
        } else {
            ans = Math.min(dfs(n + 1, memo), dfs(n - 1, memo)) + 1;
        }

        memo.put(n, ans);

        return ans;
    }


}
