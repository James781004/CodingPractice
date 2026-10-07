package EndlessCheng.GenreMenu.DP.Other;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GetKth {

    // https://leetcode.cn/problems/sort-integers-by-the-power-value/solutions/3011028/bing-bao-cai-xiang-ji-yi-hua-sou-suo-pyt-q5iz/
    private static final Map<Integer, Integer> memo = new HashMap<>();

    public int getKth(int lo, int hi, int k) {
        Integer[] nums = new Integer[hi - lo + 1];
        Arrays.setAll(nums, i -> i + lo);
        Arrays.sort(nums, (x, y) -> dfs(x) - dfs(y)); // 穩定排序
        return nums[k - 1];
    }

    private int dfs(int i) {
        if (i == 1) {
            return 0;
        }
        if (memo.containsKey(i)) { // 之前計算過
            return memo.get(i);
        }
        if (i % 2 == 1) {
            memo.put(i, dfs((i * 3 + 1) / 2) + 2);
        } else {
            memo.put(i, dfs(i / 2) + 1);
        }
        return memo.get(i);
    }

}
