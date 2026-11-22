package EndlessCheng.GenreMenu.DataStructure.PrefixSum.HashMap;

import java.util.HashMap;
import java.util.Map;

public class MaxBalancedSubarray {

    // https://leetcode.cn/problems/find-maximum-balanced-xor-subarray-length/solutions/3839716/qian-zhui-he-yu-ha-xi-biao-pythonjavacgo-2504/
    public int maxBalancedSubarray(int[] nums) {
        int n = nums.length;
        int ans = 0;
        int xor = 0;
        int diff = n; // 保證 diff 非負
        Map<Long, Integer> pos = new HashMap<>(n + 1, 1); // 預分配空間
        // 把 xor 和 diff 合並為一個 long
        pos.put((long) xor << 20 | diff, -1); // 空前綴的位置視作 -1
        for (int i = 0; i < n; i++) {
            xor ^= nums[i];
            diff += nums[i] % 2 != 0 ? 1 : -1;
            // 把 xor 和 diff 合並為一個 long
            long key = (long) xor << 20 | diff;
            Integer j = pos.get(key);
            if (j != null) {
                ans = Math.max(ans, i - j);
            } else {
                pos.put(key, i);
            }
        }
        return ans;
    }

}
