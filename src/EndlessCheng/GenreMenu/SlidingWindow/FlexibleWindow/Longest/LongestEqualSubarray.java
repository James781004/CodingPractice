package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LongestEqualSubarray {

    // https://leetcode.cn/problems/find-the-longest-equal-subarray/solutions/2396401/fen-zu-shuang-zhi-zhen-pythonjavacgo-by-lqqau/
    public int longestEqualSubarray(List<Integer> nums, int k) {
        int n = nums.size();
        List<Integer>[] posLists = new ArrayList[n + 1];
        Arrays.setAll(posLists, i -> new ArrayList<>());
        for (int i = 0; i < n; i++) { // 相同元素分組，相同元素的下標記錄到 posLists 中
            int x = nums.get(i);
            posLists[x].add(i - posLists[x].size());
        }

        int ans = 0;
        for (List<Integer> pos : posLists) { // 遍歷 posLists 中的每個下標列表 pos
            if (pos.size() <= ans) {
                continue; // 無法讓 ans 變得更大
            }
            int left = 0;
            for (int right = 0; right < pos.size(); right++) {
                while (pos.get(right) - pos.get(left) > k) { // 要刪除的數太多了
                    left++;
                }
                ans = Math.max(ans, right - left + 1);
            }
        }
        return ans;
    }


}
