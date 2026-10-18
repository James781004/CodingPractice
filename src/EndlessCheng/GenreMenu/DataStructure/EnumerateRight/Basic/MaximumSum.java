package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

import java.util.Arrays;

public class MaximumSum {

    // https://leetcode.cn/problems/max-sum-of-a-pair-with-equal-sum-of-digits/solutions/2531487/mei-ju-you-wei-hu-zuo-pythonjavacgojsrus-eoys/
    public int maximumSum(int[] nums) {
        int ans = -1;
        int[] mx = new int[82]; // 至多 9 個 9 相加
        Arrays.fill(mx, Integer.MIN_VALUE);
        for (int num : nums) { // 枚舉 num = nums[j]
            int s = 0; // num 的數位和
            for (int x = num; x > 0; x /= 10) { // 枚舉 num 的每個數位
                s += x % 10;
            }
            ans = Math.max(ans, mx[s] + num); // 左邊找一個數位和也為 s 的最大的 nums[i]
            mx[s] = Math.max(mx[s], num); // 維護數位和等於 s 的最大元素
        }
        return ans;
    }

}
