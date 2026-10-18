package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

public class MinAbsoluteDifference {

    // https://leetcode.cn/problems/minimum-absolute-difference-between-two-values/solutions/3939418/on-zuo-fa-mei-ju-you-wei-hu-zuo-pythonja-4kxq/
    public int minAbsoluteDifference(int[] nums) {
        int n = nums.length;
        int ans = n;
        // last[x] 表示 x+1 上一次出現的位置
        int[] last = {-n, -n}; // i - (-n) >= n，不會讓 ans 變小

        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            if (x > 0) {
                // 如果 x 是 1，那麼找上一個 2 的位置
                // 如果 x 是 2，那麼找上一個 1 的位置
                x--;
                ans = Math.min(ans, i - last[x ^ 1]);
                last[x] = i;
            }
        }

        return ans == n ? -1 : ans;
    }

}
