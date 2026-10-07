package EndlessCheng.GenreMenu.DP.OtherLinear.Subsequence;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SumOfPowers {

    // https://leetcode.cn/problems/find-the-sum-of-subsequence-powers/solutions/2717262/ji-yi-hua-sou-suo-xuan-huo-bu-xuan-by-sp-hmc1/
    int MOD = (int) 1e9 + 7;
    Map<String, Long> memo;

    public int sumOfPowers(int[] nums, int k) {
        memo = new HashMap<>();

        // 數組元素順序不影響最後選擇方案的結果，
        // 將數組排序後， 每個選擇的方案的最小值一定是相鄰的元素
        Arrays.sort(nums);

        // 遞歸入口 ， pre 和 min 取Integer.MAX_VALUE / 2 ，防止計算負數時溢出
        return (int) dfs(nums.length - 1, k, Integer.MAX_VALUE / 2, Integer.MAX_VALUE / 2, nums);
    }

    // 定義以下幾個參數表示當前狀態 ：
    // i - 從後往前枚舉 ， 枚舉到nums第i個元素，
    // rest - 要求選擇k個 ， 還剩下多少個沒選
    // pre - 前一個選擇的元素
    // min - 當前選擇方案的最小值
    long dfs(int i, int rest, int pre, int min, int[] nums) {
        // 當前剩余i + 1 個數字， 剩余數字小於還要選擇的數字， 怎麼選都不能夠選出k個數 ， 返回0
        if (i + 1 < rest) {
            return 0;
        }

        // 選夠了
        if (rest == 0) {
            return min;
        }

        // 把當前狀態 映射為字符串
        String key = i + "#" + rest + "#" + pre + "#" + min;

        // 如果之前計算過
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        // 不選 當前元素
        long res1 = dfs(i - 1, rest, pre, min, nums);

        // 選 當前元素
        long res2 = dfs(i - 1, rest - 1, nums[i], Math.min(min, pre - nums[i]), nums);
        memo.put(key, (res1 + res2) % MOD);
        return (res1 + res2) % MOD;
    }


}
