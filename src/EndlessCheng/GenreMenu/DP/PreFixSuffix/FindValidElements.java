package EndlessCheng.GenreMenu.DP.PreFixSuffix;

import java.util.ArrayList;
import java.util.List;

public class FindValidElements {

    // https://leetcode.cn/problems/valid-elements-in-an-array/solutions/3959262/on-zuo-fa-pythonjavacgo-by-endlesscheng-ddxo/
    public List<Integer> findValidElements(int[] nums) {
        // 標記嚴格大於其右側所有元素的元素
        int n = nums.length;
        boolean[] rightValid = new boolean[n];
        int mx = 0;
        for (int i = n - 1; i >= 0; i--) {
            int x = nums[i];
            rightValid[i] = x > mx;
            mx = Math.max(mx, x);
        }

        List<Integer> ans = new ArrayList<>();
        mx = 0;
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            if (x > mx || rightValid[i]) {
                ans.add(x);
            }
            mx = Math.max(mx, x);
        }
        return ans;
    }

}
