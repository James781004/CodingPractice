package EndlessCheng.GenreMenu.SlidingWindow.SubArray.Longest;

public class CountSubarrays {

    // https://leetcode.cn/problems/count-subarrays-where-max-element-appears-at-least-k-times/solutions/2560940/hua-dong-chuang-kou-fu-ti-dan-pythonjava-xvwg/
    public long countSubarrays(int[] nums, int k) {
        int mx = 0;
        for (int x : nums) {
            mx = Math.max(mx, x);
        }

        long ans = 0;
        int cntMx = 0, left = 0;
        for (int x : nums) {
            if (x == mx) {
                cntMx++;
            }
            while (cntMx == k) {
                if (nums[left++] == mx) { // 不斷右移左指針 left，直到窗口內的 mx 的出現次數小於 k 為止
                    cntMx--;
                }
            }
            ans += left; // 對於右端點為 right 且左端點小於 left 的子數組，mx 的出現次數都至少為 k，把答案增加 left
        }
        return ans;
    }


}
