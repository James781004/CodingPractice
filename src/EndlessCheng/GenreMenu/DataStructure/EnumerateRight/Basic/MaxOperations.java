package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

import java.util.HashMap;

public class MaxOperations {

    // https://leetcode.cn/problems/max-number-of-k-sum-pairs/solutions/2701378/on-yi-ci-bian-li-pythonjavacgojsrust-by-r7obc/
    public int maxOperations(int[] nums, int k) {
        HashMap<Integer, Integer> cnt = new HashMap<>();
        int ans = 0;
        for (int x : nums) {
            int c = cnt.getOrDefault(k - x, 0);
            if (c > 0) {
                cnt.put(k - x, c - 1);
                ans++;
            } else {
                cnt.merge(x, 1, Integer::sum);
            }
        }
        return ans;
    }

}
