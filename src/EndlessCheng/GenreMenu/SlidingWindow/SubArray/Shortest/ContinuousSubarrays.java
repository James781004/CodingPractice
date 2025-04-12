package EndlessCheng.GenreMenu.SlidingWindow.SubArray.Shortest;

import java.util.TreeMap;

public class ContinuousSubarrays {

    // https://leetcode.cn/problems/continuous-subarrays/solutions/2327219/shuang-zhi-zhen-ping-heng-shu-ha-xi-biao-4frl/
    public long continuousSubarrays(int[] nums) {
        long ans = 0;
        var t = new TreeMap<Integer, Integer>(); // 絕對差至多為 2，所以用平衡樹或者哈希表維護都行
        int left = 0;
        for (int right = 0; right < nums.length; right++) {
            t.merge(nums[right], 1, Integer::sum);
            while (t.lastKey() - t.firstKey() > 2) { // 最大值與最小值的差大於 2，就不斷移動左端點 left，減少窗口內的數字
                int y = nums[left++];
                if (t.get(y) == 1) t.remove(y);
                else t.merge(y, -1, Integer::sum);
            }
            ans += right - left + 1;
        }
        return ans;
    }


}
