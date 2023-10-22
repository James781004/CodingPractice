package Grind.Ch01_Array;

import java.util.HashMap;

public class Q17_SubarraySumEquals {
    // https://leetcode.cn/problems/subarray-sum-equals-k/solutions/562174/de-liao-yi-wen-jiang-qian-zhui-he-an-pai-yhyf/
    // 類題：724, 560, 1248, 974, 523, 930
    // 前綴和 + 哈希表
    public int subarraySum(int[] nums, int k) {
        int ans = 0;
        int sum = 0;

        // 建立 HashMap 的目的是為了可以快速的查找 sum-k 是否存在
        HashMap<Integer, Integer> map = new HashMap<>();

        // 哨兵節點，當 sum 第一次出現剛好為 k 的情況，就可以進行初始化
        // 這裡需要預存前綴和為 0 的情況，會漏掉前幾位就滿足的情況
        // 例如輸入[1,1,0]，k = 2 如果沒有這行代碼，則會返回0,漏掉了1+1=2，和1+1+0=2的情況
        // 輸入：[3,1,1,0] k = 2時則不會漏掉
        // 因為presum[3] - presum[0]表示前面 3 位的和，所以需要map.put(0,1),墊下底
        map.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            int diff = sum - k;

            // //當前前綴和已知，判斷是否含有 presum - k的前綴和，那麼我們就知道某一區間的和為 k 了。
            if (map.containsKey(diff)) {
                ans += map.get(diff);
            }

            // 更新
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return ans;
    }
}
