package EndlessCheng.GenreMenu.DataStructure.PrefixSum.HashMap;

import java.util.HashMap;

public class CheckSubarraySum {

    // https://leetcode.cn/problems/continuous-subarray-sum/solutions/562196/de-liao-wo-ba-qian-zhui-he-miao-de-gan-g-c8kp/
    public boolean checkSubarraySum(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int presum = 0;
        for (int i = 0; i < nums.length; ++i) {
            presum += nums[i];
            // 防止 k 為 0 的情況
            int key = k == 0 ? presum : presum % k;
            if (map.containsKey(key)) {
                if (i - map.get(key) >= 2) {
                    return true;
                }
                //因為我們需要保存最小索引，當已經存在時則不用再次存入，不然會更新索引值
                continue;
            }
            map.put(key, i);
        }
        return false;
    }

}
