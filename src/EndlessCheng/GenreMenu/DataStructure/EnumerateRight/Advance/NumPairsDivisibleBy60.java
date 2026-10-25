package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Advance;

public class NumPairsDivisibleBy60 {

    // https://leetcode.cn/problems/pairs-of-songs-with-total-durations-divisible-by-60/solutions/2259343/liang-shu-zhi-he-de-ben-zhi-shi-shi-yao-bd0r1/
    public int numPairsDivisibleBy60(int[] time) {
        int ans = 0;
        var cnt = new int[60];
        for (int t : time) {
            // 先查詢 cnt，再更新 cnt，因為題目要求 i<j
            // 如果先更新，再查詢，就把 i=j 的情況也考慮進去了
            ans += cnt[(60 - t % 60) % 60];
            cnt[t % 60]++;
        }
        return ans;
    }

}
