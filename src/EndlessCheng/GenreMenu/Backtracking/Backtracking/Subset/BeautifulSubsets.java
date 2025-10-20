package EndlessCheng.GenreMenu.Backtracking.Backtracking.Subset;

import java.util.HashMap;
import java.util.Map;

public class BeautifulSubsets {

    // https://leetcode.cn/problems/the-number-of-beautiful-subsets/solutions/2177818/tao-lu-zi-ji-xing-hui-su-pythonjavacgo-b-fcgs/
    public int beautifulSubsets(int[] nums, int k) {
        Map<Integer, Integer> cnt = new HashMap<>();
        dfs(0, nums, k, cnt);
        return ans;
    }

    private int ans = -1; // 去掉空集

    // nums[i] 選或不選
    private void dfs(int i, int[] nums, int k, Map<Integer, Integer> cnt) {
        if (i == nums.length) {
            ans++;
            return;
        }
        dfs(i + 1, nums, k, cnt); // 不選
        int x = nums[i];
        if (cnt.getOrDefault(x - k, 0) == 0 && cnt.getOrDefault(x + k, 0) == 0) { // 可以選
            cnt.merge(x, 1, Integer::sum); // 選
            dfs(i + 1, nums, k, cnt); // 討論 nums[i+1] 選或不選
            cnt.merge(x, -1, Integer::sum); // 撤銷，恢復現場
        }
    }


}
