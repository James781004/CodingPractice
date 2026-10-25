package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Advance;

public class CountCompleteDayPairs {

    // https://leetcode.cn/problems/count-pairs-that-form-a-complete-day-ii/solutions/2812385/tao-lu-mei-ju-you-wei-hu-zuo-pythonjavac-3vhv/
    public long countCompleteDayPairs(int[] hours) {
        final int H = 24;
        long ans = 0;
        int[] cnt = new int[H];
        for (int t : hours) {
            // 先查詢 cnt，再更新 cnt，因為題目要求 i < j
            // 如果先更新，再查詢，就把 i = j 的情況也考慮進去了
            ans += cnt[(H - t % H) % H];
            cnt[t % H]++;
        }
        return ans;
    }

}
